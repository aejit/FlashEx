package com.flashex.tripplanningmicroservice.lib.model;

import com.datastax.driver.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table("optimizationProperties")
public class OptimizationProperties {

    @PrimaryKey
    @PrimaryKeyColumn(name = "propertiesId",  ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    private String propertiesId;

    @CassandraType(type = DataType.Name.TEXT)
    private String algorithmSelected;

    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Date lastUpdated;

//    @CassandraType(type = DataType.Name.INT)
//    private int maxNoOfElementsInDistMat;
//
//    @CassandraType(type = DataType.Name.INT)
//    private int noOfDepot;

//    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.TEXT)
//    private List<String> homeTime;
//
//    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.TEXT)
//    private List<String> officeTime;

//    @CassandraType(type = DataType.Name.INT)
//    private int solverTimeLimit;
//
//    @CassandraType(type = DataType.Name.DOUBLE)
//    private double distancePenalty;
//
//    @CassandraType(type = DataType.Name.DOUBLE)
//    private double durationPenalty;
}
