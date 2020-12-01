package com.flashex.tripplanningmicroservice.lib.services;

import com.flashex.tripplanningmicroservice.lib.model.TripItinerary;
import com.flashex.tripplanningmicroservice.lib.repository.TripItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class TripItineraryService {

    @Autowired
    private TripItineraryRepository tripItineraryRepository;

    public TripItinerary getSpecificTripItinerary(String id) {
        return this.tripItineraryRepository.findById(id).orElse(null);
    }


    public List<TripItinerary> getAllTripItineraries()
    {
        return this.tripItineraryRepository.findAll();
    }

    public void saveTripItinerary(TripItinerary tripItinerary)
    {
        this.tripItineraryRepository.save(tripItinerary);
    }

    public void deleteTripItinerary(TripItinerary tripItinerary) {

        this.tripItineraryRepository.delete(tripItinerary);
    }

    public List<TripItinerary> getTripsByDay(int year, int month, int day){

        LocalDateTime localDateTime1 = LocalDateTime.of(year,month,day,0,0);
        LocalDateTime localDateTime2 = LocalDateTime.of(year,month,day+1,0,0);

        Date date1 = Date.from( localDateTime1.atZone( ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from( localDateTime2.atZone( ZoneId.systemDefault()).toInstant());
        return tripItineraryRepository.findAllByPlanGeneratedTimeBetween(date1, date2);
    }

}
