package com.flashex.tripplanningmicroservice.lib.services;

import com.flashex.tripplanningmicroservice.lib.ORTools.TimeWindowDelivery;
import com.flashex.tripplanningmicroservice.lib.ORTools.VrpWithCapacityConstraint;
import com.flashex.tripplanningmicroservice.lib.ORTools.VrpWithDroppingVisit;
import com.flashex.tripplanningmicroservice.lib.ORTools.genmatrix.Data;
import com.flashex.tripplanningmicroservice.lib.model.Packet;
import com.flashex.tripplanningmicroservice.lib.model.Shipment;
import com.flashex.tripplanningmicroservice.lib.model.TripItinerary;
import com.flashex.tripplanningmicroservice.lib.model.VehicleList;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//  This service belongs to VRP problem with capacity constraint

@Service
public class ORService {

    private static final Logger logger = Logger.getLogger(ORService.class.getName());


    private ServiceProperties serviceProperties;
    private VrpWithCapacityConstraint vrpWithCapacityConstraint;
    private VrpWithDroppingVisit vrpWithDroppingVisit;
    private TimeWindowDelivery timeWindowDelivery;
    private Data data;


    public Data getData() {
        return this.data;
    }


    public ORService(ServiceProperties serviceProperties, VrpWithCapacityConstraint vrpWithCapacityConstraint, VrpWithDroppingVisit vrpWithDroppingVisit, TimeWindowDelivery timeWindowDelivery, Data data) {
        this.serviceProperties = serviceProperties;
        this.vrpWithCapacityConstraint = vrpWithCapacityConstraint;
        this.vrpWithDroppingVisit = vrpWithDroppingVisit;
        this.timeWindowDelivery = timeWindowDelivery;
        this.data = data;
    }

    public String message() {
        return this.serviceProperties.getMessage();
    }

    //    Send array of address
    public void settingAddressArray(String[] address){
        data.setAddr(address);
    }

    //  Sends the shipment data to the algorithms
    public void settingShipment (Shipment shipment) {
        data.setShipment(shipment);
    }
    // Sets the vehicle list from the json server
    public void settingVehicleDetails (VehicleList vehicleList) {
        data.setAllVehicleList(vehicleList);
        data.setAlgosVehicleList(new VehicleList[]{vehicleList, vehicleList, vehicleList});
    }

//    VRP with capacity constraint function
    public List<TripItinerary> VrpfunctionWithCapCons(ArrayList<Packet> packets) throws Exception {
        return vrpWithCapacityConstraint.FinalResult(packets);
    }

//    VRP with Dropping nodes function
    public List<TripItinerary> VrpfuncWithDropNode(ArrayList<Packet> packets, long penalty) throws Exception {
        return vrpWithDroppingVisit.FinalResult(packets, penalty);
    }

//    VRP with Time window constraint
    public List<TripItinerary> TimeWindowConsFunction(ArrayList<Packet> packets, long penalty) throws Exception {
        return timeWindowDelivery.FinalResult(packets, penalty);
    }

}
