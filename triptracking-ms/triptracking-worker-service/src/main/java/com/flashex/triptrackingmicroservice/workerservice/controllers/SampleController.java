package com.flashex.triptrackingmicroservice.workerservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flashex.triptrackingmicroservice.lib.model.KafkaStatusMessage;
import com.flashex.triptrackingmicroservice.lib.model.TripItinerary;
import com.flashex.triptrackingmicroservice.workerservice.messaging.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.flashex.triptrackingmicroservice.lib.model.KafkaStatusMessage;


@RestController
@RequestMapping(value = "/kafka")
public class SampleController {
    private final Producer producer;

    @Autowired
    public SampleController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publishString")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message){
        this.producer.sendMessage(message);
    }

    @PostMapping(value = "/publishJSON")
    public void sendMessageToKafkaTopicJSONTest(@RequestBody TripItinerary message) throws JsonProcessingException {
        this.producer.sendMessageJSONTest(message);
    }
}

