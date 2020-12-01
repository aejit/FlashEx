package com.flashex.tripplanningmicroservice.workerservice.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final ProcessOnConsumption processOnConsumption;

    public Consumer(ProcessOnConsumption processOnConsumption) {
        this.processOnConsumption = processOnConsumption;
    }

    @KafkaListener(topics = "Batches", groupId = "group_id")
    public void consume(String message) throws Exception {
        processOnConsumption.processData(message);
    }

}
