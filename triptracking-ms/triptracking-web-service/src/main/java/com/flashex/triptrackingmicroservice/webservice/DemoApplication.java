package com.flashex.triptrackingmicroservice.webservice;

import com.flashex.triptrackingmicroservice.lib.services.SampleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.flashex.triptrackingmicroservice")
@RestController
public class DemoApplication {

    private final SampleService sampleService;

    public DemoApplication(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/home")
    public String home() {
        return sampleService.message();
    }
    @GetMapping("/")
    public String home1() {
        return "BLANK";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
