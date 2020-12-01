package com.flashex.triptrackingmicroservice.lib.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashex.triptrackingmicroservice.lib.model.KafkaStatusMessage;
import com.flashex.triptrackingmicroservice.lib.model.TripItinerary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class MessagingService {

    private static final String TOPIC = "DeliveryStatus";
    private static final Logger logger = LoggerFactory.getLogger(MessagingService.class);

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplateString;

    @Autowired
    private KafkaTemplate<Object, String> kafkaTemplateJSON;


    public void sendMessageJSON(KafkaStatusMessage message) throws JsonProcessingException {
        logger.info(String.format("$$ -> Producing message --> %s",message));
        this.kafkaTemplateJSON.send(TOPIC, new ObjectMapper().writeValueAsString(message));
    }

}
