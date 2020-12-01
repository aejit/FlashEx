package com.flashex.triptrackingmicroservice.lib.repository;

import com.flashex.triptrackingmicroservice.lib.model.TripLog;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface TripLogRepository extends CassandraRepository<TripLog, String> {

}
