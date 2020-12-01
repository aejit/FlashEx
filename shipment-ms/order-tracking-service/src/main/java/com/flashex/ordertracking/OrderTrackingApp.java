package com.flashex.ordertracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.flashex.shipmentmicroservice","com.flashex.ordertracking"})
@EnableDiscoveryClient
public class OrderTrackingApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderTrackingApp.class, args);
    }


}
