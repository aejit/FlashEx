package com.flashex.shipmentmicroservice.lib.model;

import com.datastax.driver.core.DataType;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PacketDummy {

    /** String variables **/

    private String packetId;
    private String productId;
    private String packetDescription;
    private String deliveryDescription;
    private String packetType;
    private String priority;
//    /** Float variables **/

    private float weight;
    private float length;
    private float breadth;
    private float height;
    private float costOfPacket;


    //    //to be updated by Trip Planning microservice
    private Date estimatedDeliveryDate;
//
//    //to be updated by Trip Itinerary microservice
//    private Date actualDeliveryDate;
//
//    /** Enum type variables **/
//

    /** Objects of same package**/
    private List<DeliveryAddress> deliveryAddress;

    private Customer customer;


    private List<Status> statusList = new ArrayList<>();

}
