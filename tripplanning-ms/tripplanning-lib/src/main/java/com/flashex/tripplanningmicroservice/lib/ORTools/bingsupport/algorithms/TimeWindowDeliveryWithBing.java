package com.flashex.tripplanningmicroservice.lib.ORTools.bingsupport.algorithms;

import com.flashex.tripplanningmicroservice.lib.ORTools.TimeWindowDelivery;
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
import com.google.protobuf.Duration;
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
public class TimeWindowDeliveryWithBing {

    @Autowired
    private TripItineraryService tripItineraryService;

    @Autowired
    private ProducerService producerService;


    /** Minimal VRPTW.*/

    private static final Logger logger = Logger.getLogger(TimeWindowDelivery.class.getName());

    /// @brief Print the solution.
    public List<TripItinerary> printSolution(
            DataModel data, RoutingModel routing, RoutingIndexManager manager, Assignment solution, String[] address) throws Exception {

        RoutingDimension timeDimension = routing.getMutableDimension("Time");

        Shipment shipment = data.getShipment();
//        Vehicle vehicle = new Vehicle(); // delete it this temp

//      Setting vehicle details
        VehicleList vehicleList = DataModel.getVehicleList(2);
//        logger.info((""+ vehicleList.listofvehicle));

        ArrayList<Packet> droppedPackets = new ArrayList();

        List<TripItinerary> trips = new ArrayList<TripItinerary>();
        List<Vehicle> updatedVehicles = new ArrayList<>(vehicleList.getListofvehicle());

        String droppedNodes = "Dropped nodes:";
        for (int node = 0; node < routing.size(); ++node) {
            if (routing.isStart(node) || routing.isEnd(node)) {
                continue;
            }
            if (solution.value(routing.nextVar(node)) == node) {
                droppedNodes += " " + manager.indexToNode(node);
                if(manager.indexToNode(node) != 0)
                {
                    droppedPackets.add(data.getShipment().getPacketList().get(manager.indexToNode(node) - 1));
                }
            }
        }
        logger.info("Array of dropped nodes :" + String.valueOf(droppedPackets));

        logger.info(droppedNodes);
        long routeDistance = 0;
        long routeLoad = 0;
        long totalTime = 0;

        for (int i = 0; i < DataModel.getVehicleList(2).getListofvehicle().size(); ++i) {
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
//                tripItinerary.setVehicle(vehicle); // temporary only

            String route = "";
//                String response = "";
//                Set<String> latlongarr = new HashSet<String>();
            ArrayList<Packet> PacketArray = new ArrayList();
            long avgVechiclespeed = 40 ;

            while (!routing.isEnd(index)) {
                IntVar timeVar = timeDimension.cumulVar(index);
                long nodeIndex = manager.indexToNode(index);
                routeLoad += data.getDemands()[(int) nodeIndex]; // wasnot here before I put it here for calculating occupied vehicle volume

                route += manager.indexToNode(index) + " Time(" + solution.min(timeVar)*100 + ","
                        + solution.max(timeVar)*100 + ") -> " + "Address" + address[(int) nodeIndex] + "-->";

                if(nodeIndex != 0)
                {
                    PacketArray.add(data.getShipment().getPacketList().get((int) (nodeIndex) - 1));
                }

                long vehiclecapacity = data.getVehicleCapacity(2)[i]; // Total capacity of a vehicle
                long occupiedvolume = (((routeLoad - vehiclecapacity)*100)/vehiclecapacity); // gives occupied volume in percentage
                tripItinerary.setOccupiedVolume(occupiedvolume); // setting occupied volume

//                response = geocode(addr[(int) nodeIndex],data.Key);  // appliacble when using google api to give lat long
//                System.out.println(latlongarr.size()); // To print size of latlongarrray
//                latlongarr.add(response);

                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i) * DataModel.getAvgVehicleSpeed() / DataModel.getScaleFactor();
                long mileage = 21;
                long fuelcost  = 70 ;
                long tripexpense = (avgVechiclespeed*solution.min(timeVar)*fuelcost)/(mileage);
                tripItinerary.setTripExpense(tripexpense); // set total expense
            }

            tripItinerary.setPackets(PacketArray);
            tripItinerary.setAlgorithm("Vrp with Time Window Delivery using Bing");
//                tripItinerary.setOriginAddress("117,Above SBI, Opposite Raheja Arcade,7th Block,Koramangala,Bengaluru,Karnataka,560095");
            tripItinerary.setOriginAddress(shipment.getOriginAddress());
//                Locationcord.put("Vehicle:" + i,latlongarr);

            IntVar timeVar = timeDimension.cumulVar(index);

            route += manager.indexToNode(index) + " Time(" + solution.min(timeVar) + ","
                    + solution.max(timeVar) + ")";

            logger.info(route);
            logger.info("Time of the route: " + solution.min(timeVar) + "m");



            totalTime += solution.min(timeVar);
            tripItinerary.setPlannedTotalDistance((avgVechiclespeed*totalTime)/60); // set route distance


//                logger.info("Array of lat & long" + latlongarr);
//                logger.info("Key value" + Locationcord);


            tripItinerary.setDroppedpackets(droppedPackets);

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

        DataModel.setVehicleList(new VehicleList(updatedVehicles),2);

        logger.info("Total time of all routes: " + totalTime + "mins");
        return trips;
    }

    static void matPrint(long[][] distmat, long[][] timemat, String[] address) {

        //        long[][] dist_mat = new long[distmat.length][distmat.length];
        //        long[][] time_mat = new long[timemat.length][timemat.length];

        for (int i = 0; i < distmat.length; i++) {
            System.out.println(distmat[i].length + " Distance Matrix " + Arrays.toString(distmat[i]) + "\n" +
                    timemat[i].length + " Time Travel " + Arrays.toString(timemat[i]) + "\n");
        }
        String[] addr = address;
        System.out.println(Arrays.toString(addr));
    }

    private static String geocode(String address, String KEY) throws Exception {

        final GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(KEY)
                .build();
        final GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, address).await();
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String response = gson.toJson(results[0].geometry.location);

            return response;
        } catch (final Exception e) {
            throw e;
        }
    }


    public List<TripItinerary> FinalResult(DataModel data, long setPenalty) throws Exception {

        RoutingIndexManager manager =
                new RoutingIndexManager(data.getTimeMatrix().length, DataModel.getVehicleList(2).getListofvehicle().size(), data.getDepot());

        RoutingModel routing = new RoutingModel(manager);
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.getTimeMatrix()[fromNode][toNode];
                });
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        routing.addDimensionWithVehicleCapacity(transitCallbackIndex, // transit callback
                30, // allow waiting time
                data.getVehicleCapacity(2), // vehicle maximum capacities
                false, // start cumul to zero
                "Time");
        RoutingDimension timeDimension = routing.getMutableDimension("Time");
        // Add time window constraints for each location except depot.
        long[][] timeWindow = data.createTimeWindow(0,30);
        for (int i = 1; i < timeWindow.length; ++i) {
            long index = manager.nodeToIndex(i);
            timeDimension.cumulVar(index).setRange(timeWindow[i][0], timeWindow[i][1]);
        }
        // Add time window constraints for each vehicle start node.
        for (int i = 0; i < DataModel.getVehicleList(2).getListofvehicle().size(); ++i) {
            long index = routing.start(i);
            timeDimension.cumulVar(index).setRange(timeWindow[0][0], timeWindow[0][1]);
        }
        for (int i = 0; i < DataModel.getVehicleList(2).getListofvehicle().size(); ++i) {
            routing.addVariableMinimizedByFinalizer(timeDimension.cumulVar(routing.start(i)));
            routing.addVariableMinimizedByFinalizer(timeDimension.cumulVar(routing.end(i)));
        }

//            adding disjunction
//            long penalty = 50;
        long penalty = setPenalty;
        for (int i = 1; i < data.getTimeMatrix().length; ++i) {
            routing.addDisjunction(new long[] {manager.nodeToIndex(i)}, penalty);
        }

        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setTimeLimit(Duration.newBuilder().setSeconds(30).build())
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();
        Assignment solution = routing.solveWithParameters(searchParameters);

//                Prints distance and time matrices
        matPrint(data.getDistanceMatrix(), data.getTimeMatrix(), data.getAddresses());  // use with google api only

        return printSolution(data, routing, manager, solution,data.getAddresses());

    }


}
