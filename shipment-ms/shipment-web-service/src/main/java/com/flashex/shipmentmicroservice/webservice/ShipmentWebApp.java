package com.flashex.shipmentmicroservice.webservice;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;


@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.flashex.shipmentmicroservice")
@RestController
public class ShipmentWebApp {
    public static void main(String[] args) {
        SpringApplication.run(ShipmentWebApp.class, args);
    }
}
