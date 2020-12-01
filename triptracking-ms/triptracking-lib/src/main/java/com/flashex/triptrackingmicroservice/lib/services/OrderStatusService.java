package com.flashex.triptrackingmicroservice.lib.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flashex.triptrackingmicroservice.lib.model.KafkaStatusMessage;
import com.flashex.triptrackingmicroservice.lib.model.Packet;
import com.flashex.triptrackingmicroservice.lib.model.PacketLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderStatusService {

    private KafkaStatusMessage KafkaStatusMessage;

    @Autowired
    MessagingService messagingService;

    public void scheduledOrder(List<Packet> packets){
        packets.forEach(packet -> {
            KafkaStatusMessage message = new KafkaStatusMessage();
            message.setPacketId(packet.getPacketId());
            message.setTimeStamp(new Date());
            message.setStatusValue("SCHEDULED");
            try {
                messagingService.sendMessageJSON(message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    public void dispatchedOrder(List<PacketLog> packetLog){
        packetLog.forEach(packet -> {
            KafkaStatusMessage message = new KafkaStatusMessage();
            message.setPacketId(packet.getPacketId());
            message.setTimeStamp(new Date());
            message.setStatusValue("DISPATCHED");
            try {
                messagingService.sendMessageJSON(message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
    });
    }

    public void deliveredOrder(String packetId){
            KafkaStatusMessage message = new KafkaStatusMessage();
            message.setPacketId(packetId);
            message.setTimeStamp(new Date());
            message.setStatusValue("DELIVERED");
        try {
            messagingService.sendMessageJSON(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void unDeliveredOrder(String packetId) {
//        packets.forEach(packet -> {
        KafkaStatusMessage message = new KafkaStatusMessage();
        message.setPacketId(packetId);
        message.setTimeStamp(new Date());
        message.setStatusValue("UNDELIVERED");
        try {
            messagingService.sendMessageJSON(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
