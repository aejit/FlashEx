package com.flashex.triptrackingmicroservice.lib.services;

import com.flashex.triptrackingmicroservice.lib.model.TripLog;
import com.flashex.triptrackingmicroservice.lib.repository.TripLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TripLogService {

    @Autowired
    private TripLogRepository tripLogRepository;

    @Autowired
    private OrderStatusService orderStatusService;



    public List<TripLog> getAllTripLog(){
        return tripLogRepository.findAll();
    }

    public TripLog getTripLog(String id){
        return tripLogRepository.findById(id).orElse(null);
    }


    public List<TripLog> saveTripLogs(List<TripLog> tripLogs){
        List<TripLog> savedTripLogs = new ArrayList<>();

        for (TripLog tripLog : tripLogs) {
//            tripLog.setTripItineraryId(UUID.randomUUID().toString());
            savedTripLogs.add(this.tripLogRepository.save(tripLog));
        }
        return savedTripLogs;
    }
    public TripLog updateTripLog(String id, TripLog tripLog) {
        TripLog tripLog1 = tripLogRepository.findById(id).orElse(null);
        // triplog1 - from db -
        
//        if (tripLog1.getTripStart() == null && tripLog1.getTripEnd()== null){
//            orderStatusService.dispatchedOrder(tripLog1.getPacketLogs());
//        }

        tripLog1.setTripStart(tripLog.getTripStart());
        tripLog1.setTripEnd(tripLog.getTripEnd());
        TripLog updateTripLog = tripLogRepository.save(tripLog1);

        return updateTripLog;

    }
    public TripLog updatePacketLog(String id, TripLog tripLog, String tripPacketId) {
        TripLog tripLog1 = tripLogRepository.findById(id).orElse(null);
        tripLog1.setTripStart(tripLog.getTripStart());
        tripLog1.setTripEnd(tripLog.getTripEnd());
        // extract data from packets to packetlogs
        for(int i=0; i<tripLog1.getPacketLogs().size(); i++){
            if(tripPacketId.equals(tripLog1.getPacketLogs().get(i).getPacketId())){
                String status=  tripLog.getPacketLogs().get(0).getPacketStatus();
                tripLog1.getPacketLogs().get(i).setPacketStatus(status);
                if (status.equals("Delivered")) {
                    orderStatusService.deliveredOrder(tripPacketId);
                }
                else if (status.equals("Undelivered")){
                    orderStatusService.unDeliveredOrder(tripPacketId);

                }
            }

        }
        TripLog updateTripLog = tripLogRepository.save(tripLog1);
        return updateTripLog;
    }
}
