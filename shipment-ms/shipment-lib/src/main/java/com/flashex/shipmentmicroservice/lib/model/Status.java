package com.flashex.shipmentmicroservice.lib.model;

import com.datastax.driver.core.DataType;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@UserDefinedType("status")
public class Status {

    @CassandraType(type = DataType.Name.TEXT)
    private String statusValue;
    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Date timeStamp;

}
