package com.flashex.tripplanningmicroservice.workerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@SpringBootApplication(scanBasePackages = "com.flashex.tripplanningmicroservice")
@RestController
@EnableDiscoveryClient
@EnableScheduling
public class DemoApplication {

    static {
//        System.setProperty("java.library.path", "/usr/java/packages/lib");
        System.loadLibrary("jniortools");
//        Logger logger = Logger.getLogger(DemoApplication.class.getName());
//
//        Path workingDirectory= Paths.get("target").toAbsolutePath();
//
//        logger.info(String.format(String.valueOf(workingDirectory)));
//        Runtime.getRuntime().load(workingDirectory+"/or-lib/libjniortools.so");
    }



    public static void main(String[] args) {


        SpringApplication.run(DemoApplication.class, args);
    }


}
