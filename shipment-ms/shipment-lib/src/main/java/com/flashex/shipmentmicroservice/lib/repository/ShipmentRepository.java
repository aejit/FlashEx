package com.flashex.shipmentmicroservice.lib.repository;

import com.flashex.shipmentmicroservice.lib.model.Shipment;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface ShipmentRepository extends CassandraRepository<Shipment, UUID> {

}
