package com.flashex.tripplanningmicroservice.lib.repository;

import com.flashex.tripplanningmicroservice.lib.model.TripItinerary;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Date;
import java.util.List;

;

public interface TripItineraryRepository extends CassandraRepository<TripItinerary,String> {
    @AllowFiltering
    List<TripItinerary> findAllByPlanGeneratedTimeBetween(Date fromDate, Date toDate);
}
