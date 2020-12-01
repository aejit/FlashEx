package com.flashex.tripplanningmicroservice.webservice;

import com.flashex.tripplanningmicroservice.lib.services.ORService;
import com.flashex.tripplanningmicroservice.lib.services.SampleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.flashex.tripplanningmicroservice")
@RestController
public class TripPlanningWebServ {

//    static {
//        Logger logger = Logger.getLogger(TripPlanningWebServ.class.getName());
//        Path workingDirectory=Paths.get("tripplanning-web-service/src").toAbsolutePath();
//        logger.info("Current-path:" + workingDirectory);
//        logger.info(String.format(String.valueOf(workingDirectory)));
//        Runtime.getRuntime().load(workingDirectory+"/main/resources/extjars/libjniortools.so");
//    }

    static {
        System.loadLibrary("jniortools");
    }

    public static void main(String[] args) {
        SpringApplication.run(TripPlanningWebServ.class, args);
    }
}
