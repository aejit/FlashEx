package com.flashex.shipmentmicroservice.lib.model;


import com.datastax.driver.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

/**
 * The following class stores the data of Delivery address, it's geo-location
 * **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("deliveryAddress")
public class DeliveryAddress {

    /** String variables **/
    @CassandraType(type = DataType.Name.TEXT)
    public String addressLine1;
    @CassandraType(type = DataType.Name.TEXT)
    public String city;
    @CassandraType(type = DataType.Name.TEXT)
    public String state;

    /** Double variables **/
    @CassandraType(type = DataType.Name.DOUBLE)
    public double latitude;
    @CassandraType(type = DataType.Name.DOUBLE)
    public double longitude;

    /** Integer variables **/
    @CassandraType(type = DataType.Name.INT)
    public long pincode;


}
