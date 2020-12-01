package com.flashex.shipmentmicroservice.workerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.flashex.shipmentmicroservice")
@RestController
@EnableDiscoveryClient
@EnableScheduling
public class ShipmentGeneratorApp {



    public static void main(String[] args) {
        SpringApplication.run(ShipmentGeneratorApp.class, args);
    }



}
