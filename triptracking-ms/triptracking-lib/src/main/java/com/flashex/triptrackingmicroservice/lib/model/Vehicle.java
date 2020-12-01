package com.flashex.triptrackingmicroservice.lib.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("vehicle")
public class Vehicle {

    private String vehicleId;
    private String vehicleType;
    private int vehicleVolume;
    private String availability;
    private  String vehicleCrew;
    private  String vehicleImage;

}
