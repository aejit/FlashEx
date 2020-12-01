package com.flashex.tripplanningmicroservice.lib.ORTools.bingsupport.algorithms;

import com.flashex.tripplanningmicroservice.lib.model.*;
import com.flashex.tripplanningmicroservice.lib.model.bingdm.DataModel;
import com.flashex.tripplanningmicroservice.lib.services.ProducerService;
import com.flashex.tripplanningmicroservice.lib.services.TripItineraryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.ortools.constraintsolver.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class VrpWithCapacityConstraintWithBing {


    @Autowired
    private TripItineraryService tripItineraryService;

    @Autowired
    private ProducerService producerService;

    /** Minimal VRP.*/
    private static final Logger logger = Logger.getLogger(VrpWithCapacityConstraintWithBing.class.getName());

    /// @brief Print the solution.
    public List<TripItinerary> printSolution(
            DataModel data, RoutingModel routing, RoutingIndexManager manager, Assignment solution, String[] address) throws Exception {

        Shipment shipment = data.getShipment();
//        Vehicle vehicle = new Vehicle(); // delete it this temp

//      Setting vehicle details
        VehicleList vehicleList = DataModel.getVehicleList(0);
//        logger.info((""+ vehicleList.listofvehicle));

        // Inspect solution.
        long totalDistance = 0;
        long totalLoad = 0;

        List<TripItinerary> trips = new ArrayList<TripItinerary>();
        List<Vehicle> updatedVehicles = new ArrayList<>(vehicleList.getListofvehicle());

        for (int i = 0; i < DataModel.getVehicleList(0).getListofvehicle().size(); ++i) {

            TripItinerary tripItinerary = new TripItinerary();
//            tripItinerary.setTripItineraryId(UUID.randomUUID().toString());
            tripItinerary.setTripItineraryId(String.format("%035d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)));
//            tripItinerary.setPlannedStartTime(new Date(2019, 9, 04, 9, 00,00));
            tripItinerary.setPlanGeneratedTime(Timestamp.valueOf(LocalDateTime.now()));
            tripItinerary.setPlannedStartTime(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(9,0))));
//            tripItinerary.setPlannedEndTime(new Date(2019, 9, 04, 17, 00,00));
            tripItinerary.setPlannedEndTime(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(13,0))));

            long index = routing.start(i);
            logger.info("Route for Vehicle " + i + ":");

            tripItinerary.setVehicle(vehicleList.listofvehicle.get(i));  // set vehicle object
//            tripItinerary.setVehicle(vehicle); // temporary only

            long routeDistance = 0;
            long routeLoad = 0;
            String route = "";
//            String response = "";
//            Set<String> latlongarr = new HashSet<String>();
            ArrayList<Packet> PacketArray = new ArrayList();

            while (!routing.isEnd(index)) {
                long nodeIndex = manager.indexToNode(index);
                routeLoad += data.getDemands()[(int) nodeIndex];

                long vehiclecapacity = data.getVehicleCapacity(0)[i]; // Total capacity of a vehicle
                long occupiedvolume = (((vehiclecapacity - routeLoad)*100)/vehiclecapacity);
                tripItinerary.setOccupiedVolume(occupiedvolume); // setting occupied volume

                route += nodeIndex + " Load(" + routeLoad + ")  -> " + "Address" + address[(int) nodeIndex] + "-->";
//                response = geocode(addr[(int) nodeIndex],data.Key);
//                latlongarr.add(response);

//                logger.info("nodeIndex ------------> "+ nodeIndex);

                if( nodeIndex != 0 )
                {
                    PacketArray.add(data.getShipment().getPacketList().get((int) nodeIndex - 1));
                }

                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i)/DataModel.getScaleFactor();

                tripItinerary.setPlannedTotalDistance(routeDistance); // set route distance
                long mileage = 21;
                long fuelcost = 70 ;
                long tripexpense = (routeDistance*fuelcost)/mileage;
                tripItinerary.setTripExpense(tripexpense);

            }
            tripItinerary.setPackets(PacketArray);
            tripItinerary.setAlgorithm("Vrp with Capacity Constraint using Bing");
//            tripItinerary.setOriginAddress("117,Above SBI, Opposite Raheja Arcade,7th Block,Koramangala,Bengaluru,Karnataka,560095");
            logger.info("Origin Address ----------------------> Shipment: "+shipment.getOriginAddress()+" Data: "+data.getShipment().getOriginAddress());
            tripItinerary.setOriginAddress(shipment.getOriginAddress());

//            Locationcord.put("Vehicle:" + i,latlongarr);

            route += manager.indexToNode(routing.end(i));
            logger.info(route);
            logger.info("Distance of the route: " + routeDistance + "m");
            totalDistance += routeDistance;
            totalLoad += routeLoad;
//            logger.info("Array of lat & long" + latlongarr);
//            logger.info("Key value" + Locationcord);

            if(tripItinerary.getPackets().size() != 0) {
                tripItineraryService.saveTripItinerary(tripItinerary);
                trips.add(tripItinerary);
                vehicleList.getListofvehicle().forEach(vehicle -> {
                    if(vehicle.getVehicleId().equals(tripItinerary.getVehicle().getVehicleId())) {
                        updatedVehicles.remove(vehicle);
                    }
                });
            }

        }

        DataModel.setVehicleList(new VehicleList(updatedVehicles), 0);
        updatedVehicles.forEach(vehicle -> logger.info("Updated vehicle list ----------------> "+vehicle.getVehicleId()));

        logger.info("Total distance of all routes: " + totalDistance + "m");
        logger.info("Total load of all routes: " + totalLoad);
        return trips;
    }

    static void matPrint(long[][] distmat, long[][] timemat, String[] address) {

//        long[][] dist_mat = new long[distmat.length][distmat.length];
//        long[][] time_mat = new long[timemat.length][timemat.length];

        for (int i = 0; i < distmat.length; i++) {
            System.out.println(distmat[i].length + " Distance Matrix " + Arrays.toString(distmat[i]) + "\n" +
                    timemat[i].length + " Time Travel " + Arrays.toString(timemat[i]) + "\n");
        }
        System.out.println(Arrays.toString(address));
    }

    private static String geocode(String address, String KEY) throws Exception {

        final GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(KEY)
                .build();
        final GeocodingResult[] results;
        results = GeocodingApi.geocode(context, address).await();
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(results[0].geometry.location);
    }


    public List<TripItinerary> FinalResult(DataModel data) throws Exception {
        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(data.getDistanceMatrix().length, DataModel.getVehicleList(0).getListofvehicle().size(), data.getDepot());

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.getDistanceMatrix()[fromNode][toNode];
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Add Capacity constraint.
        final int demandCallbackIndex = routing.registerUnaryTransitCallback((long fromIndex) -> {
            // Convert from routing variable Index to user NodeIndex.
            int fromNode = manager.indexToNode(fromIndex);
            return data.getDemands()[fromNode];
        });
        routing.addDimensionWithVehicleCapacity(demandCallbackIndex, 0, // null capacity slack
                data.getVehicleCapacity(0), // vehicle maximum capacities
                true, // start cumul to zero
                "Capacity");

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

//    //    Prints distance and time matrices when using generate matrix to generate distance and time-travel matrix
        matPrint(data.getDistanceMatrix(), data.getTimeMatrix(), data.getAddresses());

        // Print solution on console.
        return printSolution(data, routing, manager, solution, data.getAddresses());

    }


}
