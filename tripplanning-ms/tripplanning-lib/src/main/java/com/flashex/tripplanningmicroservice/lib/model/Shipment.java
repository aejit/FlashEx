package com.flashex.tripplanningmicroservice.lib.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {

    /** String variables **/
    public String shipmentId;

    /** Date variables **/
    public Date shipmentDate;

    public DeliveryAddress originAddress;

    /** Objects from local package **/
    public ArrayList<Packet> packetList;


    public String[] getAllDeliveryAddresses(){

        int packetlistsize = this.packetList.size();
        String[] address = new String[packetlistsize+1];

//        for(int i=1; i<=packetlistsize; i++){
//            address[i] = this.packetList.get(i-1).getDeliveryAddress().getAddressLine1().replaceAll(", ","+").replaceAll(" ","+") + "+" +
//                    this.packetList.get(i-1).getDeliveryAddress().getCity()+ "+" +
//                    this.packetList.get(i-1).getDeliveryAddress().getState() + "+" +
//                    this.packetList.get(i-1).getDeliveryAddress().getPincode();
//        }

        address[0] = this.originAddress.getAddressLine1()+","+
                this.originAddress.getCity()+","+this.originAddress.getState()+","+
                this.originAddress.getState()+","+this.originAddress.getPincode();

        for(int i=1; i<packetlistsize+1; i++){
            address[i] = this.packetList.get(i-1).getDeliveryAddress().getAddressLine1() + "," +
                    this.packetList.get(i-1).getDeliveryAddress().getCity()+ "," +
                    this.packetList.get(i-1).getDeliveryAddress().getState() + "," +
                    this.packetList.get(i-1).getDeliveryAddress().getPincode();
        }
        return address;
    }
}