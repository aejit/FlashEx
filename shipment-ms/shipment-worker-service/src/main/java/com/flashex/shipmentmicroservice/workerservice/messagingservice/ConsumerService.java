package com.flashex.shipmentmicroservice.workerservice.messagingservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashex.shipmentmicroservice.lib.model.Packet;
import com.flashex.shipmentmicroservice.lib.model.PacketDummy;
import com.flashex.shipmentmicroservice.lib.services.BinningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.flashex.shipmentmicroservice.lib.services.PacketService;
import java.util.Collections;



@Service
public class ConsumerService {
    private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    BinningService binningService;

    @Autowired
    private PacketService packetService;

    @KafkaListener(topics = "Order", groupId = "shipment_gen")
    public void consume(String message) throws JsonProcessingException {
        logger.info(String.format("$$ ->Successfully parsed received order, binning received order -> %s",message));
        PacketDummy packetDummy = new ObjectMapper().readValue(message, PacketDummy.class);
        Packet packet = new Packet();
        packet.setPacketId(packetDummy.getPacketId());
        packet.setBreadth(packetDummy.getBreadth());
        packet.setDeliveryAddress(packetDummy.getDeliveryAddress().get(0));
        packet.setDeliveryDescription(packetDummy.getDeliveryDescription());
        packet.setHeight(packetDummy.getHeight());
        packet.setWeight(packetDummy.getWeight());
        packet.setCostOfPacket(packetDummy.getCostOfPacket());
        packet.setCustomer(packetDummy.getCustomer());
        packet.setPacketType(packetDummy.getPacketType());
        packet.setLength(packetDummy.getLength());
        packet.setEstimatedDeliveryDate(packetDummy.getEstimatedDeliveryDate());
        packet.setPriority(packetDummy.getPriority());
        binningService.binPacket(packet);
    }
}
