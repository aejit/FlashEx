package com.flashex.ordercollector.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flashex.ordercollector.messagingservice.ProducerService;
import com.flashex.shipmentmicroservice.lib.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    private final ProducerService producer;

    @Autowired
    public SampleController(ProducerService producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publishJSON")
    public Shipment sendMessageToKafkaTopicJSON(@RequestBody Shipment message) throws JsonProcessingException {
        this.producer.sendMessage(message);
        return message;
    }
}
