package com.flashex.shipmentmicroservice.workerservice.schedules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flashex.shipmentmicroservice.lib.services.BinningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ShipmentGeneration {
    private static final Logger log = LoggerFactory.getLogger(ShipmentGeneration.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    BinningService binningService;

    @Scheduled(cron = "2 * * * * ?")
    public void generateShipment() throws JsonProcessingException {
        log.info("$$ Shipment generation triggered at ----------->{}", dateFormat.format(new Date()));
        binningService.generateFixedShipments();
        log.info("$$ Shipments scheduled job executed -----------> ");
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void updateConfig(){
        binningService.updateConfig();
    }

}
