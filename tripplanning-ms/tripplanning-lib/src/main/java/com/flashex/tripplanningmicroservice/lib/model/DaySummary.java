package com.flashex.tripplanningmicroservice.lib.model;


import com.datastax.driver.core.DataType;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("summary")
public class DaySummary {

    @PrimaryKeyColumn(name = "summaryId",  ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    String summaryId;

    // date for which summary is made
    @CassandraType(type = DataType.Name.TIMESTAMP)
    Date summaryDate;

    // list of algorithms for which summary is made
    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.TEXT)
    List<String> algorithms;

    // list of distance summary in the order mentioned in algorithms in km
    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.FLOAT)
    List<Float> distanceSummary;

    // list of time summary in the order mentioned in algorithms in hours
    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.FLOAT)
    List<Float> timeSummary;

    // list of cost summary in the order mentioned in algorithms
    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.FLOAT)
    List<Float> costSummary;

    // list of dropped nodes percentage in the order mentioned in algorithms
    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.INT)
    List<Integer> droppedPacektsSummary;

    // Number of trips generated in the order mentioned in algorithms
    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.INT)
    List<Integer> nTrips;

    // vehicle usage for each algorithm
    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.FLOAT)
    List<Float> volumeOccupancySummary;
}
