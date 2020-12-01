package com.flashex.triptrackingmicroservice.lib.model;
import com.datastax.driver.core.DataType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("tripitinerary")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripItinerary {
    @PrimaryKeyColumn(name = "tripItineraryId",  ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    private String tripItineraryId;

    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.UDT, userTypeName = "packet")
    private List<Packet> packets;

    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Date plannedStartTime;

    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Date plannedEndTime;

    @CassandraType(type = DataType.Name.FLOAT)
    private float plannedTotalDistance;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "vehicle")
    private Vehicle vehicle;

    @CassandraType(type = DataType.Name.FLOAT)
    private float tripExpense;

    @CassandraType(type = DataType.Name.FLOAT)
    private float occupiedVolume;
    @CassandraType(type = DataType.Name.UDT, userTypeName = "deliveryAddress")
    private DeliveryAddress originAddress;

    @CassandraType(type = DataType.Name.TEXT)
    private String algorithm;

    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.UDT, userTypeName = "packet")
    private List<Packet> droppedpackets;
}