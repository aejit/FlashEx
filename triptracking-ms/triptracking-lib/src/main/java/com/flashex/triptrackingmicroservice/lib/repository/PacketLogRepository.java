package com.flashex.triptrackingmicroservice.lib.repository;

import com.flashex.triptrackingmicroservice.lib.model.PacketLog;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface PacketLogRepository extends CassandraRepository<PacketLog, String> {
}
