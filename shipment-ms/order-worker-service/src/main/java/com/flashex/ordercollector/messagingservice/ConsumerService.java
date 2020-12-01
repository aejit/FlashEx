package com.flashex.ordercollector.messagingservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashex.shipmentmicroservice.lib.model.Packet;
import com.flashex.shipmentmicroservice.lib.model.PacketDummy;
import com.flashex.shipmentmicroservice.lib.model.Status;
import com.flashex.shipmentmicroservice.lib.services.PacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ConsumerService {
    private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    private PacketService packetService;

    @KafkaListener(topics = "Order", groupId = "ordercollector")
    public void consume(String message) throws JsonProcessingException {
        logger.info(String.format("$$ -> Consumed Message -> %s",message));
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
        packet.setPacketDescription(packetDummy.getPacketDescription());
        packet.setPriority(packetDummy.getPriority());
        packetService.savePackets(Collections.singletonList(packet));
    }
}
