package com.flashex.tripplanningmicroservice.lib.services;

import com.datastax.driver.core.*;
import com.flashex.tripplanningmicroservice.lib.model.TripItinerary;
import com.flashex.tripplanningmicroservice.lib.repository.TripItineraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;

@ExtendWith(SpringExtension.class)
public class CassandraTest {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(CassandraTest.class);

    @Mock
    private TripItineraryRepository tripItineraryRepository;

    @InjectMocks
    private TripItineraryService tripItineraryService;

    @Test
    public void getAllEntries() {
        Mockito.when(tripItineraryRepository.findAll()).thenReturn(List.of());
        List<TripItinerary> persons = tripItineraryService.getAllTripItineraries();//tripItineraryRepository.findAll();
        Assertions.assertTrue(persons.isEmpty());
    }

    @Test
    public void getOneEntry(){
        Mockito.when(tripItineraryRepository.findById(Mockito.any(String.class))).thenReturn(Optional.empty());
        Assertions.assertNull(tripItineraryService.getSpecificTripItinerary("01"));
    }

    TripItinerary tripItinerary = new TripItinerary();


    @Test
    public void saveOneEntry(){
        Mockito.when(tripItineraryRepository.save(tripItinerary)).thenReturn(tripItinerary);
        Mockito.when(tripItineraryRepository.findById(Mockito.any(String.class))).thenReturn(Optional.of(tripItinerary));
        Assertions.assertNotNull(tripItineraryService.getSpecificTripItinerary("01"));

    }
    @Test
    public void testMockExample() throws Exception {
        Session session = Mockito.mock(Session.class);

        // Mock a ResultSet that gets returned from ResultSetFuture#get()
        ResultSet result = Mockito.mock(ResultSet.class);
        List<Row> rows = new ArrayList<Row>();
        Row r = Mockito.mock(Row.class);
        Mockito.doReturn(5).when(r).getInt(0);
        rows.add(r);

        Mockito.doReturn(rows).when(result).all();

        // Mock a ResultSetFuture that gets returned from Session#executeAsync()
        ResultSetFuture future = Mockito.mock(ResultSetFuture.class);
        Mockito.doReturn(result).when(future).get();
        Mockito.doReturn(future).when(session).executeAsync(Mockito.anyString());

        // Execute the query and print the 0th column of the first result.
        ResultSetFuture resultF = session.executeAsync("select value from table where key='a'");
        System.out.println(resultF.get().all().iterator().next().getInt(0));
    }

}
