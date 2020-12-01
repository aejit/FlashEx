package com.flashex.triptrackingmicroservice.workerservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashex.triptrackingmicroservice.lib.model.*;
import com.flashex.triptrackingmicroservice.lib.services.OrderStatusService;
import com.flashex.triptrackingmicroservice.lib.services.TripLogService;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    TripLogService tripLogService;

    @Autowired
    OrderStatusService orderStatusService;

    @KafkaListener(topics = "TripItinerary", groupId = "group_id")
    public void consume(String message) throws JsonProcessingException {
        logger.info(String.format("$$ -> Consumed Message -> %s",message));
        logger.info(String.format("$$ -> Consumed Message -> %s",new ObjectMapper().readValue(message, TripItinerary.class)));
        TripItinerary tripItinerary = new ObjectMapper().readValue(message, TripItinerary.class);

        //updating scheduled delivery status
       orderStatusService.scheduledOrder(tripItinerary.getPackets());

       // create a log
        TripLog tripLog = new TripLog();
        tripLog.setTripItineraryId(tripItinerary.getTripItineraryId());
        tripLog.setOriginAddress(tripItinerary.getOriginAddress());
        tripLog.setPlannedStartTime(tripItinerary.getPlannedStartTime());
        tripLog.setPlannedEndTime(tripItinerary.getPlannedEndTime());

        System.out.println("********************");
        System.out.println(tripLog);
        System.out.println("**************");

        // extract data from packets to packetlogs
        List<PacketLog> packetLogs = new ArrayList<>();
        for(int i=0; i<tripItinerary.getPackets().size(); i++){
            PacketLog packetLog = new PacketLog();
            packetLog.setPacketId(tripItinerary.getPackets().get(i).getPacketId());
            packetLog.setDeliveryAddress(tripItinerary.getPackets().get(i).getDeliveryAddress());
            packetLog.setDeliveryDescription(tripItinerary.getPackets().get(i).getDeliveryDescription());
            packetLog.setPacketStatus("Dispatached");
            packetLog.setCustomerName(tripItinerary.getPackets().get(i).getCustomer().firstName + ' '+ tripItinerary.getPackets()
            .get(i).getCustomer().middleName + ' ' + tripItinerary.getPackets().get(i).getCustomer().lastName);
            packetLog.setPhoneNumber(tripItinerary.getPackets().get(i).getCustomer().phoneNumber);
            packetLogs.add(packetLog);


        }
        tripLog.setPacketLogs(packetLogs);

        // save to database
        tripLogService.saveTripLogs(Collections.singletonList((tripLog)));

    }
}
