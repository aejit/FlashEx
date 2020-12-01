package com.flashex.ordercollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.flashex.shipmentmicroservice","com.flashex.ordercollector"})
@EnableDiscoveryClient
public class OrderCollectionApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderCollectionApp.class, args);
    }


}
