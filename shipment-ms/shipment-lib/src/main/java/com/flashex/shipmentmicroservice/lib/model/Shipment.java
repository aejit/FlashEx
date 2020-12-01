package com.flashex.shipmentmicroservice.lib.model;

import com.datastax.driver.core.DataType;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;

/*
 * The following class is responsible for creating Shipments of Orders based on sorting,
 * filtering, grouping and MAX_SHIPMENT_SIZE criteria
 *
 * Batch -1---has---*-->Shipments
 * Shipment--1--has---*-->Orders
 *
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("shipment")
public class Shipment {

    /** String variables **/

    @PrimaryKeyColumn(name = "shipmentId",  ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    public String shipmentId;

    /** Date variables **/
    @CassandraType(type = DataType.Name.TIMESTAMP)
    public Date shipmentDate;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "deliveryAddress")
    public DeliveryAddress originAddress;

    /** Objects from local package **/
    @CassandraType(type = DataType.Name.LIST, typeArguments = { DataType.Name.UDT }, userTypeName = "packet")
    public List<Packet> packetList;

}
