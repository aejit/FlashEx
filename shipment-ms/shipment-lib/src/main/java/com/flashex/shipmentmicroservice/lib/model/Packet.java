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
@Table("packet")
@UserDefinedType("packet")
public class Packet {

    /** String variables **/

    @PrimaryKey
    @PrimaryKeyColumn(name = "packetId",  ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    @Indexed
    private String packetId;
    @CassandraType(type = DataType.Name.TEXT)
    private String productId;
    @CassandraType(type = DataType.Name.TEXT)
    private String packetDescription;
    @CassandraType(type = DataType.Name.TEXT)
    private String deliveryDescription;
    @CassandraType(type = DataType.Name.TEXT)
    private String packetType;
    @CassandraType(type = DataType.Name.TEXT)
    private String priority;
//    /** Float variables **/

    @CassandraType(type = DataType.Name.FLOAT)
    private float weight;
    @CassandraType(type = DataType.Name.FLOAT)
    private float length;
    @CassandraType(type = DataType.Name.FLOAT)
    private float breadth;
    @CassandraType(type = DataType.Name.FLOAT)
    private float height;
    @CassandraType(type = DataType.Name.FLOAT)
    private float costOfPacket;


    /** Date type variables**/
//    @CassandraType(type = DataType.Name.TIMESTAMP)
//    private Date receivedDate;
//
//    //to be updated by Trip Planning microservice
    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Date estimatedDeliveryDate;
//
//    //to be updated by Trip Itinerary microservice
//    private Date actualDeliveryDate;
//
//    /** Enum type variables **/
//

    /** Objects of same package**/
    @CassandraType(type = DataType.Name.UDT, userTypeName = "deliveryAddress")
    private DeliveryAddress deliveryAddress;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "customer")
    private Customer customer;


    @CassandraType(type = DataType.Name.LIST, typeArguments = { DataType.Name.UDT }, userTypeName = "status")
    private List<Status> statusList = new ArrayList<>();

    // public Date receivedDate(){
    //     return this.statusList.get(0).getTimeStamp();
    // }


    @JsonIgnore
    public Date receivedDate(){
        return this.statusList.get(0).getTimeStamp();
    }
}
