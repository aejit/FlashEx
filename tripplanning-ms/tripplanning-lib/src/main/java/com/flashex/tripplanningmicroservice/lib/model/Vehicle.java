package com.flashex.tripplanningmicroservice.lib.model;


import com.datastax.driver.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("vehicle")
public class Vehicle {

    @CassandraType(type = DataType.Name.TEXT)
    private String vehicleId;
    @CassandraType(type = DataType.Name.TEXT)
    private String vehicleType;
    @CassandraType(type = DataType.Name.INT)
    private int vehicleVolume;
    @CassandraType(type = DataType.Name.TEXT)
    private String availability;
    @CassandraType(type = DataType.Name.TEXT)
    private String vehicleCrew;
    @CassandraType(type = DataType.Name.TEXT)
    private String vehicleImage;

}
