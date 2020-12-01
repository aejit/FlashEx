package com.flashex.tripplanningmicroservice.lib.getjsonserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashex.tripplanningmicroservice.lib.ORTools.VrpWithCapacityConstraint;
import com.flashex.tripplanningmicroservice.lib.ORTools.genmatrix.Urllib;
import com.flashex.tripplanningmicroservice.lib.model.Vehicle;
import com.flashex.tripplanningmicroservice.lib.model.VehicleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

@Service
public class GetJsonServerData {

    private String inline;
    final Logger logger = Logger.getLogger(GetJsonServerData.class.getName());

    @Autowired
    Urllib urllib;

    public List<Vehicle> getAvailableVehicles() throws IOException {

        String request = "http://vehicle-json-server:3000/vehicles";
        String inline = Urllib.urlopen(request);
        //         get data from json server
        logger.info("Recieved vehicle data-----------------------> "+inline);
        List<Vehicle> fleetdetails = new ObjectMapper().readValue(inline, new TypeReference<List<Vehicle>>(){});
        logger.info("Fleet details after parsing ----------------> "+ fleetdetails);
        List<Vehicle> availableVehicles = new ArrayList<Vehicle>();
        for(int i=0; i<fleetdetails.size(); i++) {
            if(Boolean.parseBoolean(fleetdetails.get(i).getAvailability())) {
                availableVehicles.add(fleetdetails.get(i));
                logger.info("Available vehicle -------------------> "+ fleetdetails.get(i));
            }
        }
        logger.info("No of available vehicles ------> "+availableVehicles.size());
        return availableVehicles;
    }

    public List<Vehicle> getAllVehicles() throws IOException {

        String request = "http://vehicle-json-server:3000/vehicles";
        String inline = Urllib.urlopen(request);
        //         get data from json server
        logger.info("Recieved vehicle data-----------------------> "+inline);
        List<Vehicle> fleetdetails = new ObjectMapper().readValue(inline, new TypeReference<List<Vehicle>>(){});
        logger.info("Fleet details after parsing ----------------> "+ fleetdetails);

        return fleetdetails;
    }

    public void postListData(List<Vehicle> vehicles) throws IOException {

        String request = "http://vehicle-json-server:3000/vehicles";
        urllib.post(request, vehicles.toString());
    }
}
