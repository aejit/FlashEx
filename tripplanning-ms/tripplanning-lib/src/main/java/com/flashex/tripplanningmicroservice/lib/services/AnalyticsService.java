package com.flashex.tripplanningmicroservice.lib.services;

import com.flashex.tripplanningmicroservice.lib.model.DaySummary;
import com.flashex.tripplanningmicroservice.lib.model.TripItinerary;
import com.flashex.tripplanningmicroservice.lib.repository.DaySummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AnalyticsService {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);

    @Autowired
    TripItineraryService tripItineraryService;

    @Autowired
    DaySummaryRepository daySummaryRepository;

    @Scheduled(cron="0 0 11 * * *")
    public void scheduledSummaryCreation(){
        LocalDateTime today = LocalDateTime.now();
        createSummary(today.getYear(), today.getMonthValue(),today.getDayOfMonth());
    }

    public DaySummary createSummary(int year, int month, int day){

        logger.info("Creating summary fo {}/{}/{}>>>>>>>>>>>>>>", day,month,year);
        List<TripItinerary> trips = tripItineraryService.getTripsByDay(year,month,day);

        logger.info("Found these trips>>>>>>>>>>>>>>>");
//        logger.info("Trips -------------> {}", trips);
        DaySummary daySummary = new DaySummary();
        daySummary.setSummaryDate(new Date());
        daySummary.setSummaryId(UUID.randomUUID().toString());

        // set algorithms
        List<String> algorithms = new ArrayList<>();
        algorithms.add("Vrp with Time Window Delivery");
        algorithms.add("Vrp with Time Window Delivery using Bing");
        algorithms.add("Vrp with Capacity Constraint");
        algorithms.add("Vrp with Capacity Constraint using Bing");
        algorithms.add("Vrp with Dropping Visit");
        algorithms.add("Vrp with Dropping Visit using Bing");
        daySummary.setAlgorithms(algorithms);

        // get number of trips for each
        daySummary.setNTrips(getNumberOfTrips(trips, algorithms));

        // get distance summary for each
        daySummary.setDistanceSummary(getDistanceSummary(trips,algorithms));

        // get total time summary for each
        daySummary.setTimeSummary(getTimeSummary(trips,algorithms));

        // get cost summary
        daySummary.setCostSummary(getCostSummary(trips,algorithms));

        // get dropped packets
        daySummary.setDroppedPacektsSummary(getDroppedPacketSummary(trips,algorithms));

        // get volume occupancy
        daySummary.setVolumeOccupancySummary(getOccupancySummary(trips,algorithms));

        daySummaryRepository.save(daySummary);
        return daySummary;
    }

    public List<Integer> getOrderSummary(List<TripItinerary> tripItineraries, List<String> algorithms){
        List<Integer> orderSummary = new ArrayList<>();
        for(int i=0; i<algorithms.size(); i++){
            orderSummary.add(0);
        }

        for(int i=0; i<tripItineraries.size(); i++){
            for(int algoIndex=0; algoIndex<algorithms.size(); algoIndex++){
                // increment if a match is found
                if(tripItineraries.get(i).getAlgorithm().equals(algorithms.get(algoIndex))){
                    int currentOrders = orderSummary.get(algoIndex);
                        orderSummary.set(algoIndex,currentOrders+tripItineraries.get(i).getPackets().size());

                }
            }
        }

        logger.info("Calculated Processed Packets summary -----------> {}", orderSummary);
        return orderSummary;
    }

    public List<Float> getOccupancySummary(List<TripItinerary> tripItineraries, List<String> algorithms){
        List<Float> occupancySummary = new ArrayList<>();
        for(int i=0; i<algorithms.size(); i++){
            occupancySummary.add((float) 0);
        }

        List<Float> netVehicleCapacity = new ArrayList<>();
        for(int i=0; i<algorithms.size(); i++){
            netVehicleCapacity.add((float) 0);
        }

        List<Float> occupiedVolume = new ArrayList<>();
        for(int i=0; i<algorithms.size(); i++){
            occupiedVolume.add((float) 0);
        }

        for(int i=0; i<tripItineraries.size(); i++){
            for(int algoIndex=0; algoIndex<algorithms.size(); algoIndex++){
                // increment if a match is found
                if(tripItineraries.get(i).getAlgorithm().equals(algorithms.get(algoIndex))){
                    // get vehicle capacity
                    float netVehicleCapacity_ = netVehicleCapacity.get(algoIndex);
                    netVehicleCapacity.set(algoIndex, netVehicleCapacity_+tripItineraries.get(i).getVehicle().getVehicleVolume());
                    //get occupied volume
                    float occupiedVolume_ = occupiedVolume.get(algoIndex);
                    occupiedVolume.set(algoIndex,occupiedVolume_+tripItineraries.get(i).getOccupiedVolume());
                }
            }
        }
        for(int i=0; i<algorithms.size(); i++){
            float occupancy = occupiedVolume.get(i)/netVehicleCapacity.get(i);
            if(!Float.isNaN(occupancy)){
                occupancySummary.set(i, occupancy);
            }
        }

        logger.info("Calculated Occupancy Summay ----------> {}", occupancySummary);
        return occupancySummary;
    }

    public List<Integer> getDroppedPacketSummary(List<TripItinerary> tripItineraries, List<String> algorithms){
        // initialize
        List<Integer> droppedPacketsSummary = new ArrayList<>();
        for(int i=0; i<algorithms.size(); i++){
            droppedPacketsSummary.add(0);
        }
        for(int i=0; i<tripItineraries.size(); i++){
            for(int algoIndex=0; algoIndex<algorithms.size(); algoIndex++){
                // increment if a match is found
//                logger.info("Trying to match ----> {} with {}------> {}",tripItineraries.get(i).getAlgorithm(),algorithms.get(algoIndex),tripItineraries.get(i).getAlgorithm().equals(algorithms.get(algoIndex)));
                if(tripItineraries.get(i).getAlgorithm().equals(algorithms.get(algoIndex))){
                    int currentDropped = droppedPacketsSummary.get(algoIndex);
                    if(tripItineraries.get(i).getDroppedpackets() != null){
                        droppedPacketsSummary.set(algoIndex,currentDropped+tripItineraries.get(i).getDroppedpackets().size());
                    }
                }
            }
        }

        logger.info("Calculated Dropped Packets summary -----------> {}", droppedPacketsSummary);
        return droppedPacketsSummary;
    }

    public List<Float> getCostSummary(List<TripItinerary> tripItineraries, List<String> algorithms){
        // initialize
        List<Float> costSummary = new ArrayList<>();
        for(int i=0; i<algorithms.size(); i++){
            costSummary.add((float) 0.0);
        }
        for(int i=0; i<tripItineraries.size(); i++){
            for(int algoIndex=0; algoIndex<algorithms.size(); algoIndex++){
                // increment if a match is found
                if(tripItineraries.get(i).getAlgorithm().equals(algorithms.get(algoIndex))){
                    float currentTime = costSummary.get(algoIndex);
                    costSummary.set(algoIndex,currentTime+tripItineraries.get(i).getTripExpense());
                }
            }
        }
        logger.info("Calculated Cost Summary ------> {}", costSummary );
        return costSummary;
    }


    public List<Float> getTimeSummary(List<TripItinerary> tripItineraries, List<String> algorithms){
        // initialize
        List<Float> timeSummary = new ArrayList<>();
        for(int i=0; i<algorithms.size(); i++){
            timeSummary.add((float) 0.0);
        }

        for(int i=0; i<tripItineraries.size(); i++){
            for(int algoIndex=0; algoIndex<algorithms.size(); algoIndex++){
                // increment if a match is found
                if(tripItineraries.get(i).getAlgorithm().equals(algorithms.get(algoIndex))){
                    float currentTime = timeSummary.get(algoIndex);
                    // get planned start and end times
                    Date startTime = tripItineraries.get(i).getPlannedStartTime();
                    Date endTime = tripItineraries.get(i).getPlannedEndTime();
                    // find the difference in hours
                    logger.info("Start and end time {} , {}, {}, {}",startTime,startTime.getTime(),endTime,endTime.getTime());
                    long diff = Math.abs(startTime.getTime()-endTime.getTime());
                    float hours = TimeUnit.MILLISECONDS.toHours(diff);
                    timeSummary.set(algoIndex,currentTime+hours);
                }
            }
        }
        logger.info("Calculated Time Summary -------------> {}", timeSummary);
        return timeSummary;

    }

    public List<Float> getDistanceSummary(List<TripItinerary> tripItineraries, List<String> algorithms){
        // initialize
        List<Float> distanceSummary = new ArrayList<>();
        for(int i=0; i<algorithms.size(); i++){
            distanceSummary.add((float) 0.0);
        }

        for(int i=0; i<tripItineraries.size(); i++){
            for(int algoIndex=0; algoIndex<algorithms.size(); algoIndex++){
                // increment if a match is found
                if(tripItineraries.get(i).getAlgorithm().equals(algorithms.get(algoIndex))){
                    float currentDistance = distanceSummary.get(algoIndex);
                    distanceSummary.set(algoIndex,currentDistance+tripItineraries.get(i).getPlannedTotalDistance());
                }
            }
        }
        logger.info("Calculated Distance Summary ------> {}", distanceSummary);
        return distanceSummary;
    }

    public List<Integer> getNumberOfTrips(List<TripItinerary> tripItineraries, List<String> algorithms){

        // initialize
        List<Integer> nTrips = new ArrayList<>();
        for(int i=0; i<algorithms.size(); i++){
            nTrips.add(0);
        }

        // loop through each itinerary
        for(int i=0; i<tripItineraries.size(); i++){
            for(int algoIndex=0; algoIndex<algorithms.size(); algoIndex++){
                // increment if a match is found
                if(tripItineraries.get(i).getAlgorithm().equals(algorithms.get(algoIndex))){
                    int currentCount = nTrips.get(algoIndex);
                    nTrips.set(algoIndex,currentCount+1);
                }
            }
        }
        logger.info("Calculated Total trips count summary -------->{}", nTrips);
        return nTrips;
    }

    public DaySummary getSummaryForDay(String date){
        //String date = "13-08-2016";
        String[] values = date.split("-");
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);

        LocalDateTime localDateTime1 = LocalDateTime.of(year,month,day,0,0);
        LocalDateTime localDateTime2 = LocalDateTime.of(year,month,day+1,0,0);

        Date date1 = Date.from( localDateTime1.atZone( ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from( localDateTime2.atZone( ZoneId.systemDefault()).toInstant());
        return daySummaryRepository.findAllBySummaryDateBetween(date1, date2).get(0);
    }
}
