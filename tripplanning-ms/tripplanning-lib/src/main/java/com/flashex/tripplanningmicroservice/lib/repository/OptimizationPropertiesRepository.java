package com.flashex.tripplanningmicroservice.lib.repository;

import com.flashex.tripplanningmicroservice.lib.model.OptimizationProperties;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface OptimizationPropertiesRepository extends CassandraRepository<OptimizationProperties, String> {
}
