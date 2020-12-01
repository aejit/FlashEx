package com.flashex.tripplanningmicroservice.lib.model;

import com.datastax.driver.core.DataType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@UserDefinedType("packet")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Packet {

    /** String variables **/

    @PrimaryKeyColumn(name = "packetId",  ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
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


}
