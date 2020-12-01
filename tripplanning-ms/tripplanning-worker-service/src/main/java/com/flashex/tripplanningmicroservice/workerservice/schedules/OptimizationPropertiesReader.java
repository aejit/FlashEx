package com.flashex.tripplanningmicroservice.workerservice.schedules;

import com.flashex.tripplanningmicroservice.lib.model.OptimizationProperties;
import com.flashex.tripplanningmicroservice.lib.services.OptimizationPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class OptimizationPropertiesReader {

    private static OptimizationProperties latestOptimizationProperties;

    Logger logger = Logger.getLogger(OptimizationPropertiesReader.class.getName());

    @Autowired
    OptimizationPropertiesService propertiesService;

//    @Scheduled(cron = "2 * * * * ?")
    @Scheduled(cron = "0 * 6 * * *")
    public void optimizationPropsFetcher () {
        logger.info("Triggered Time ===============|> "+ LocalDateTime.now());
        if(propertiesService.getAllProperties().size() != 0) {
            latestOptimizationProperties = propertiesService.getAllProperties().get(0);
        } else {
            latestOptimizationProperties = new OptimizationProperties("1", "VRP with Capacity Constraint using Bing", Timestamp.valueOf(LocalDateTime.now()));
            propertiesService.saveOptimizationProperties(latestOptimizationProperties);
        }
        logger.info("Optimization Property ===============|> "+ latestOptimizationProperties.getAlgorithmSelected() + "LastUpdated ========|>" + latestOptimizationProperties.getLastUpdated());
    }

    public static OptimizationProperties getLatestOptimizationProperties() {
        return latestOptimizationProperties;
    }

    public static void setLatestOptimizationProperties(OptimizationProperties latestOptimizationProperties) {
        OptimizationPropertiesReader.latestOptimizationProperties = latestOptimizationProperties;
    }
}
