package com.flashex.shipmentmicroservice.lib.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashex.shipmentmicroservice.lib.model.KafkaStatusMessage;
import com.flashex.shipmentmicroservice.lib.model.Shipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    private static final Logger logger = LoggerFactory.getLogger(MessagingService.class);

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplateString;

    @Autowired
    private KafkaTemplate<Object, String> kafkaTemplateJSON;

    public void sendShipment(Shipment message) throws JsonProcessingException {
        logger.info(String.format("$$ -> Producing message --> %s",message));
        if(message.getPacketList().size() != 0){
            logger.info("Sufficient orders available to be shipped ------------- {}", new ObjectMapper().writeValueAsString(message));
            this.kafkaTemplateJSON.send("Batches", new ObjectMapper().writeValueAsString(message));
        }
    }

    public void sendUpdates(KafkaStatusMessage message) throws JsonProcessingException {
        logger.info(String.format("$$ -> Producing message --> %s",message));
        this.kafkaTemplateJSON.send("DeliveryStatus", new ObjectMapper().writeValueAsString(message));

    }
}
