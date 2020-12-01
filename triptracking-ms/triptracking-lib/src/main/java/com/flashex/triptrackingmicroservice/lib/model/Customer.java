package com.flashex.triptrackingmicroservice.lib.model;

import com.datastax.driver.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("customer")
public class Customer {

    /** String variables **/
    @PrimaryKeyColumn(name = "customerfirstname",  ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.TEXT)
    public String firstName;
    @CassandraType(type = DataType.Name.TEXT)
    public String middleName;
    @CassandraType(type = DataType.Name.TEXT)
    public String lastName;
    @CassandraType(type = DataType.Name.TEXT)
    public String emailId;

    /** Integer variables **/
    @CassandraType(type = DataType.Name.BIGINT)
    public long phoneNumber;

}

