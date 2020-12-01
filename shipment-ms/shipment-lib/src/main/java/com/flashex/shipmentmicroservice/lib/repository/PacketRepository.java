package com.flashex.shipmentmicroservice.lib.repository;

import com.flashex.shipmentmicroservice.lib.model.Packet;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface PacketRepository extends CassandraRepository<Packet,String> {

}
