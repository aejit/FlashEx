package com.flashex.tripplanningmicroservice.workerservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashex.tripplanningmicroservice.lib.ORTools.bingsupport.algorithms.TimeWindowDeliveryWithBing;
import com.flashex.tripplanningmicroservice.lib.ORTools.bingsupport.algorithms.VrpWithCapacityConstraintWithBing;
import com.flashex.tripplanningmicroservice.lib.ORTools.bingsupport.algorithms.VrpWithDroppingVisitWithBing;
import com.flashex.tripplanningmicroservice.lib.model.OptimizationProperties;
import com.flashex.tripplanningmicroservice.lib.model.Shipment;
import com.flashex.tripplanningmicroservice.lib.model.TripItinerary;
import com.flashex.tripplanningmicroservice.lib.model.bingdm.DataModel;
import com.flashex.tripplanningmicroservice.lib.services.ORService;
import com.flashex.tripplanningmicroservice.lib.services.ProducerService;
import com.flashex.tripplanningmicroservice.workerservice.schedules.OptimizationPropertiesReader;
import com.flashex.tripplanningmicroservice.workerservice.schedules.VehicleFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ProcessOnConsumption {

    private final ORService orService;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static int counter = 0;

    @Autowired
    ProducerService producerService;

    @Autowired
    VrpWithCapacityConstraintWithBing vrpWithCapacityConstraintWithBing;

    @Autowired
    VrpWithDroppingVisitWithBing vrpWithDroppingVisitWithBing;

    @Autowired
    TimeWindowDeliveryWithBing timeWindowDeliveryWithBing;

    @Autowired
    VehicleFetcher vehicleFetcher;

    @Autowired
    OptimizationPropertiesReader optReader;

    public ProcessOnConsumption(ORService orService) {
        this.orService = orService;
    }

    public void processData(String message) throws Exception {
//        logger.info(String.format("$$ -> Consumed Message -> %s",message));
        Shipment shipmentReceived = new ObjectMapper().readValue(message, Shipment.class);
//        logger.info("shipmentRecieved -------------------------> "+ shipmentReceived.getPacketList());
//        String[] deliveryAddresses = shipmentReceived.getAllDeliveryAddresses();
//        logger.info("deliveryAddresses -------------------------> "+deliveryAddresses);
        orService.settingShipment(shipmentReceived);
        DataModel dataModel = new DataModel(shipmentReceived, 0);

        if (this.counter == 0){
            vehicleFetcher.fetchVehicles();
            optReader.optimizationPropsFetcher();
            this.counter++;
        }

        logger.info("<<<<<<<<<<<<<<<<<<<<<<Method:1 starts here>>>>>>>>>>>>>>>>>>");
        List<TripItinerary> capConstraintTrips = orService.VrpfunctionWithCapCons(shipmentReceived.getPacketList());
        logger.info("<<<<<<<<<<<<<<<<<<<<<<Method:1 ends here>>>>>>>>>>>>>>>>>>>>>");

        logger.info("<<<<<<<<<<<<<<<<<<<<<<Method:2 starts here>>>>>>>>>>>>>>>>>>>>");
        List<TripItinerary> dropNodesTrips = orService.VrpfuncWithDropNode(shipmentReceived.getPacketList(), 50000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<Method:2 ends here>>>>>>>>>>>>>>>>>>>>>>>");

        logger.info("<<<<<<<<<<<<<<<<<<<<<<Method:3 starts here>>>>>>>>>>>>>>>>>>>>>>");
        List<TripItinerary> timeWindowTrips = orService.TimeWindowConsFunction(shipmentReceived.getPacketList(), 50000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<Method:3 ends here>>>>>>>>>>>>>>>>>>>>>>>>>");

        logger.info("<<<<<<<<<<<<<<<<<<<<<<Capacity Constraint with Bing: Starts here>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<TripItinerary> capConstraintTripsWithBing = vrpWithCapacityConstraintWithBing.FinalResult(dataModel);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<Capacity Constraint with Bing: Ends here>>>>>>>>>>>>>>>>>>>>>>>>>");

        logger.info("<<<<<<<<<<<<<<<<<<<<<<Dropping Visits with Bing: Starts here>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<TripItinerary> dropNodesTripsWithBing = vrpWithDroppingVisitWithBing.FinalResult(dataModel, 50000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<Dropping Visits with Bing: Ends here>>>>>>>>>>>>>>>>>>>>>>>>>");

        logger.info("<<<<<<<<<<<<<<<<<<<<<<Time Window with Bing: Starts here>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<TripItinerary> timeWindowTripsWithBing = timeWindowDeliveryWithBing.FinalResult(dataModel, 50000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<Time Window with Bing: Ends here>>>>>>>>>>>>>>>>>>>>>>>>>");

        OptimizationProperties optProperties = OptimizationPropertiesReader.getLatestOptimizationProperties();

        logger.info("Obtained Optimization properties ---------------> " + optProperties.getAlgorithmSelected());

        switch (optProperties.getAlgorithmSelected()) {
            case "VRP with Capacity Constraint":
                for (int i = 0; i < capConstraintTrips.size(); i++) {
                    producerService.sendMessageJSON(capConstraintTrips.get(i));
                }
                break;
            case "VRP with Dropping Visit":
                for (int i = 0; i < dropNodesTrips.size(); i++) {
                    producerService.sendMessageJSON(dropNodesTrips.get(i));
                }
                break;
            case "Time Window Delivery":
                for (int i = 0; i < timeWindowTrips.size(); i++) {
                    producerService.sendMessageJSON(timeWindowTrips.get(i));
                }
                break;
            case "VRP with Capacity Constraint using Bing":
                logger.info("<|=================Sending VRP with Capacity constraint using Bing to TripTracking=================|>");
                for (int i = 0; i < capConstraintTripsWithBing.size(); i++) {
                    producerService.sendMessageJSON(capConstraintTripsWithBing.get(i));
                }
                break;
            case "VRP with Dropping Visits using Bing":
                for (int i = 0; i < dropNodesTripsWithBing.size(); i++) {
                    producerService.sendMessageJSON(dropNodesTripsWithBing.get(i));
                }
                break;
            case "Time Window Delivery using Bing":
                for (int i = 0; i < timeWindowTripsWithBing.size(); i++) {
                    producerService.sendMessageJSON(timeWindowTripsWithBing.get(i));
                }
                break;
            default:
                logger.info("<|=================Sending Default Algorithm result=================|>");
                for (int i = 0; i < capConstraintTripsWithBing.size(); i++) {
                    producerService.sendMessageJSON(capConstraintTripsWithBing.get(i));
                }
                break;
        }
    }

}
