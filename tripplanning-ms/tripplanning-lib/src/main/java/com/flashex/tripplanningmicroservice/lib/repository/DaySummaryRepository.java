package com.flashex.tripplanningmicroservice.lib.repository;

import com.flashex.tripplanningmicroservice.lib.model.DaySummary;
import com.flashex.tripplanningmicroservice.lib.model.TripItinerary;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Date;
import java.util.List;

public interface DaySummaryRepository extends CassandraRepository<DaySummary,String> {
    @AllowFiltering
    List<DaySummary> findAllBySummaryDateBetween(Date fromDate, Date toDate);
}
