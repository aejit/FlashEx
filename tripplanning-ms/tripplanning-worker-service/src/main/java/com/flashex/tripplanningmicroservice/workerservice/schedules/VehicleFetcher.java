package com.flashex.tripplanningmicroservice.workerservice.schedules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flashex.tripplanningmicroservice.lib.getjsonserver.GetJsonServerData;
import com.flashex.tripplanningmicroservice.lib.model.Vehicle;
import com.flashex.tripplanningmicroservice.lib.model.VehicleList;
import com.flashex.tripplanningmicroservice.lib.model.bingdm.DataModel;
import com.flashex.tripplanningmicroservice.lib.services.ORService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Component
public class VehicleFetcher {

//    DataModel dataModel = new DataModel();

    @Autowired
    GetJsonServerData getJsonServerData;

    @Autowired
    ORService orService;

    private final Logger logger = Logger.getLogger(VehicleFetcher.class.getName());

    public VehicleFetcher() throws JsonProcessingException {
    }

    //    @Scheduled(cron = "0 0 0/24 ? * * ")
    @Scheduled(cron = "0 * 6 * * *")
    public void fetchVehicles() throws IOException {

        logger.info("Fetch Vehicles Triggered at -----------------> "+ LocalDateTime.now());

        List<Vehicle> vehicles = getJsonServerData.getAvailableVehicles();
        logger.info("Obtained list of vehicles -----------------> "+vehicles.toString());

        VehicleList vehicleList = new VehicleList();
        vehicleList.setListofvehicle(vehicles);
        orService.settingVehicleDetails(vehicleList);
        DataModel.setVehicleList(vehicleList,0);
        DataModel.setVehicleList(vehicleList,1);
        DataModel.setVehicleList(vehicleList,2);

        logger.info("Vehicle List has been updated!");
    }
}
