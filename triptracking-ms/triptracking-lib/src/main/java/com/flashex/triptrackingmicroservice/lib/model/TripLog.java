package com.flashex.triptrackingmicroservice.lib.model;

import com.datastax.driver.core.DataType;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("tripslog")
public class TripLog {

    @PrimaryKey
    @PrimaryKeyColumn(name = "tripItineraryId",  ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    private String tripItineraryId;

    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Date tripStart;

    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Date tripEnd;

    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.UDT, userTypeName = "packetlog")
    private List<PacketLog> packetLogs;

    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Date plannedStartTime;

    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Date plannedEndTime;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "deliveryAddress")
    private DeliveryAddress originAddress;

}
