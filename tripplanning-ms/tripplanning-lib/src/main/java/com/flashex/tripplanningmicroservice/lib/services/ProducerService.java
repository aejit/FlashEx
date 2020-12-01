package com.flashex.tripplanningmicroservice.lib.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashex.tripplanningmicroservice.lib.model.TripItinerary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static final String TOPIC = "TripItinerary";
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplateString;

    @Autowired
    private KafkaTemplate<Object, String> kafkaTemplateJSON;

    public void sendMessage(String message){
        logger.info(String.format("$$ -> Producing message --> %s",message));
        this.kafkaTemplateString.send(TOPIC,message);
    }

    public void sendMessageJSON(TripItinerary message) throws JsonProcessingException {
        logger.info(String.format("$$ -> Producing message --> %s",message));
        this.kafkaTemplateJSON.send(TOPIC, new ObjectMapper().writeValueAsString(message));
    }
}