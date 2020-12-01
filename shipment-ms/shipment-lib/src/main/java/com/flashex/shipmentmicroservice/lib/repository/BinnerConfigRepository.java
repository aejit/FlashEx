package com.flashex.shipmentmicroservice.lib.repository;

import com.flashex.shipmentmicroservice.lib.model.BinnerConfig;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface BinnerConfigRepository extends CassandraRepository<BinnerConfig,String> {
}
