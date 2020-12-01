package com.flashex.tripplanningmicroservice.lib.model.bingdm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flashex.tripplanningmicroservice.lib.ORTools.bingsupport.services.BingServices;
import com.flashex.tripplanningmicroservice.lib.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


@Getter
@Setter
public class DataModel {

    static VehicleList[] vehicleList = new VehicleList[3];
    static int avgVehicleSpeed = 40 * 60;  //should be km/min
    static int scaleFactor = 1000;

    Shipment shipment;
    int depot;
    long[][] distanceMatrix;
    long[][] timeMatrix;


    public DataModel(Shipment shipment, int depot) throws JsonProcessingException {
        this.shipment = shipment;
        this.depot = depot;
//        this.distanceMatrix = generateDistanceMatrix();
//        this.timeMatrix = generateTimeMatrix();
        ArrayList<long[][]> result = generateDistanceAndTimeMatrix(shipment);
        this.distanceMatrix = result.get(0);
        this.timeMatrix = result.get(1);
    }

    public long[] getDemands() {
        long[] demands = new long[shipment.getPacketList().size()+1];
        demands[0] = 0;
        for(int i=0; i<shipment.getPacketList().size(); i++) {
            Packet packet = shipment.getPacketList().get(i);
            demands[i+1] = (long) (packet.getHeight() * packet.getBreadth() * packet.getLength());
        }
        return demands;
    }

    public String[] getAddresses() {
        String[] addresses = new String[shipment.getPacketList().size()+1];
        addresses[0] = shipment.getOriginAddress().getAddressLine1();
        for(int i=0; i<shipment.getPacketList().size(); i++){
            Packet packet = shipment.getPacketList().get(i);
            addresses[i+1] = packet.getDeliveryAddress().getAddressLine1();
        }
        return addresses;
    }

    public long[] getVehicleCapacity(int algoIndex) {
        long[] capacities = new long[vehicleList[algoIndex].getListofvehicle().size()];
        for(int i=0; i<vehicleList[algoIndex].getListofvehicle().size(); i++){
            Vehicle vehicle = vehicleList[algoIndex].getListofvehicle().get(i);
            capacities[i] = vehicle.getVehicleVolume();
        }
        return capacities;
    }

    public ArrayList<long[][]> generateDistanceAndTimeMatrix (Shipment shipment) throws JsonProcessingException {
        List<DeliveryAddress> origins = new ArrayList<>();
        List<DeliveryAddress> destinations = new ArrayList<>();
        origins.add(shipment.getOriginAddress());
        destinations.add(shipment.getOriginAddress());
        for(int i=0;i<shipment.getPacketList().size(); i++) {
            origins.add(shipment.getPacketList().get(i).getDeliveryAddress());
            destinations.add(shipment.getPacketList().get(i).getDeliveryAddress());
        }
        Logger.getLogger(DataModel.class.getName()).info("Origin and Destination addresses --------------------> " + origins + "-------"+ destinations);
        ArrayList<long[][]> distAndTimeMat =  BingServices.requestDistanceAndTimeMatrix(origins,destinations);
        Logger.getLogger(DataModel.class.getName()).info("DistanceMatrix --------------------> " + Arrays.deepToString(distAndTimeMat.get(0)));
        Logger.getLogger(DataModel.class.getName()).info("TimeMatrix --------------------> " + Arrays.deepToString(distAndTimeMat.get(1)));
        return distAndTimeMat;
    }

    public static void setVehicleList(VehicleList newVehicleList, int algoIndex) {
        vehicleList[algoIndex] = newVehicleList;
    }

    public static VehicleList getVehicleList(int algoIndex) {
        return vehicleList[algoIndex];
    }

    public long[][] createTimeWindow(int startTime, int endTime) {
        long[][] timeWindow = new long[shipment.getPacketList().size()+1][2];
        for(int i=0; i<shipment.getPacketList().size()+1; i++) {
            timeWindow[i][0] = startTime;
            timeWindow[i][1] = endTime;
        }
        return timeWindow;
    }

    public static int getScaleFactor() {
        return scaleFactor;
    }

    public static void setScaleFactor(int scaleFactor) {
        DataModel.scaleFactor = scaleFactor;
    }

    public static int getAvgVehicleSpeed() {
        return avgVehicleSpeed;
    }

    public static void setAvgVehicleSpeed(int avgVehicleSpeed) {
        DataModel.avgVehicleSpeed = avgVehicleSpeed;
    }
}
