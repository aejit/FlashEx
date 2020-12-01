package com.flashex.triptrackingmicroservice.lib.model;

import com.datastax.driver.core.DataType;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;

@UserDefinedType("packetlog")
@ToString
public class PacketLog {

    @CassandraType(type = DataType.Name.TEXT)
    private String packetId;

    /** Objects of same package**/
    @CassandraType(type = DataType.Name.UDT, userTypeName = "deliveryAddress")
    private DeliveryAddress deliveryAddress;

    private String deliveryDescription;

    private String packetStatus;
    private String customerName;
    private long phoneNumber;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPacketId() {
        return packetId;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryDescription() {
        return deliveryDescription;
    }

    public String getPacketStatus() {
        return packetStatus;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setDeliveryDescription(String deliveryDescription) {
        this.deliveryDescription = deliveryDescription;
    }

    public void setPacketStatus(String packetStatus) {
        this.packetStatus = packetStatus;
    }
}
