package com.flashex.tripplanningmicroservice.lib.ORTools.genmatrix;

import com.flashex.tripplanningmicroservice.lib.model.Packet;
import com.flashex.tripplanningmicroservice.lib.model.Shipment;
import com.flashex.tripplanningmicroservice.lib.model.VehicleList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Data {
        public String API_Key = "xyz";
        public static String[] addr = new String[]{};

        private static Shipment shipment;

        private static VehicleList allVehicleList;

        private static int avgVehicleSpeed = 35 * 60; //speed in km/min

        private static VehicleList[] algosVehicleList = new VehicleList[3];

//        private long[] demands;

        public ArrayList<Packet> packets;

        public String getAPI_Key() {
                return API_Key;
        }

        public static String[] getAddr() {
                return addr;
        }

        public void setAPI_Key(String API_Key) {
                this.API_Key = API_Key;
        }

        public static void setAddr(String[] addr) {
                Data.addr = addr;
        }

        public static Shipment getShipment() {
                return shipment;
        }

        public static void setShipment(Shipment shipment) {
                Data.shipment = shipment;
        }


        public static VehicleList getAllVehicleList() {
                return allVehicleList;
        }

        public static void setAllVehicleList(VehicleList allVehicleList) {
                Data.allVehicleList = allVehicleList;
        }

        public long[] createDemandArray(Shipment shipment) {

                long[] demandArray = new long[shipment.getPacketList().size()+1];
                for (int i = 1; i<shipment.getPacketList().size()+1; i++) {
                        demandArray[i] = (long) (shipment.getPacketList().get(i-1).getHeight() * shipment.getPacketList().get(i-1).getBreadth() * shipment.getPacketList().get(i-1).getLength());
                }
                demandArray[0] = 0;
                return demandArray;
        }

        public long[][] createTimeWindow(int startTime, int endTime) {
                long[][] timeWindow = new long[shipment.getPacketList().size()+1][2];
                for(int i=0; i<shipment.getPacketList().size()+1; i++) {
                        timeWindow[i][0] = startTime;
                        timeWindow[i][1] = endTime;
                }
                return timeWindow;
        }

        public static VehicleList[] getAlgosVehicleList() {
                return algosVehicleList;
        }

        public static void setAlgosVehicleList(VehicleList[] algosVehicleList) {
                Data.algosVehicleList = algosVehicleList;
        }

        public static void setAlgoVehicles(VehicleList vehicleList, int index){
                Data.algosVehicleList[index] = vehicleList;
        }

        public static int getAvgVehicleSpeed() {
                return avgVehicleSpeed;
        }

        public static void setAvgVehicleSpeed(int avgVehicleSpeed) {
                Data.avgVehicleSpeed = avgVehicleSpeed;
        }
}
