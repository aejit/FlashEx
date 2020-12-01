package com.flashex.tripplanningmicroservice.lib.ORTools;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.flashex.tripplanningmicroservice.lib.ORTools.genmatrix.Data;
import com.flashex.tripplanningmicroservice.lib.ORTools.genmatrix.GenerateMatrix;
import com.flashex.tripplanningmicroservice.lib.getjsonserver.GetJsonServerData;
import com.flashex.tripplanningmicroservice.lib.model.*;
import com.flashex.tripplanningmicroservice.lib.services.ORService;
import com.flashex.tripplanningmicroservice.lib.services.ProducerService;
import com.flashex.tripplanningmicroservice.lib.services.TripItineraryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.IntVar;
import com.google.ortools.constraintsolver.RoutingDimension;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.constraintsolver.main;
import com.google.protobuf.Duration;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;


public class TimeWindowDelivery {


    @Autowired
    private TripItineraryService tripItineraryService;

    @Autowired
    private ProducerService producerService;


    /** Minimal VRPTW.*/

        private static final Logger logger = Logger.getLogger(TimeWindowDelivery.class.getName());

        static class DataModel {


//            public final long[][] timeMatrix =
//                {
//                {0, 22, 28, 17, 27, 27, 22, 23, 18, 21, 25, 28, 27, 13, 22, 23},
//                {22, 0, 11, 15, 10, 9, 14, 5, 15, 10, 13, 13, 10, 17, 16, 15},
//                {28, 11, 0, 22, 7, 4, 17, 7, 21, 14, 11, 7, 3, 23, 14, 15},
//                {17, 15, 21, 0, 15, 19, 5, 15, 1, 8, 16, 22, 20, 8, 12, 13},
//                {27, 10, 8, 16, 0, 6, 11, 7, 16, 10, 7, 9, 7, 20, 10, 10},
//                {27, 10, 4, 21, 6, 0, 16, 8, 21, 13, 11, 9, 6, 22, 15, 13},
//                {22, 14, 17, 6, 11, 16, 0, 12, 5, 4, 12, 20, 16, 13, 8, 9},
//                {23, 5, 8, 15, 7, 8, 12, 0, 15, 8, 12, 12, 9, 18, 13, 14},
//                {18, 16, 21, 1, 15, 19, 5, 15, 0, 8, 16, 22, 20, 8, 12, 13},
//                {21, 10, 14, 7, 10, 13, 4, 8, 7, 0, 11, 15, 13, 15, 7, 9},
//                {25, 13, 11, 16, 7, 11, 11, 12, 16, 10, 0, 12, 10, 17, 5, 6},
//                {28, 11, 6, 23, 8, 8, 18, 10, 22, 14, 10, 0, 4, 23, 13, 14},
//                {27, 10, 3, 20, 7, 6, 17, 10, 20, 13, 10, 6, 0, 21, 12, 13},
//                {13, 18, 24, 9, 21, 22, 13, 19, 9, 15, 18, 25, 22, 0, 15, 16},
//                {22, 16, 15, 13, 10, 15, 9, 14, 13, 9, 6, 15, 14, 15, 0, 1},
//                {23, 16, 15, 14, 11, 13, 10, 14, 14, 10, 6, 16, 15, 15, 1, 0}
//        };

//            Data d = (new Data());
//            public final String[] addresses = d.getAddr();

//            ArrayList<Packet> templist = orService.getListofPackets();
//            Shipment shipment = (new Shipment());
//            ArrayList<Packet> templist = shipment.getPacketList();


       /* GenerateMatrix matGenerator = new GenerateMatrix();
        Data d = matGenerator.createData();
        public final int[][] timeMatrix = matGenerator.createTimeTravelMatrix(d);
        public final String[] addresses = d.addr;
        public final String Key = d.API_Key;
        public final int[][] distmat = matGenerator.createDistanceMatrix(d);
        public final int[][] timemat = matGenerator.createTimeTravelMatrix(d);*/

//        private final GetJsonServerData getJsonServerData = new GetJsonServerData();
//        VehicleList vehicleList = getJsonServerData.processJsonData();

//        public final long[] demands = {0, 1, 1, 2, 4, 2, 4, 8, 8, 1, 2, 1, 2, 4, 4, 8};
//        public final long[] demands = {0, 8, 8, 2, 4};


//        public final long[][] timeWindows = {
//                {0, 50},    // depot
//                {0, 50},   // 1
//                {0, 50},// 2
//                {0, 50},   // 3
//                {0, 50},   // 4
//                {0, 50},    // 5
//                {0, 50},   // 6
//                {0, 50},   // 7
//                {0, 50},   // 8
//                {0, 50},    // 9
//                {0, 50},  // 10
//                {0, 50},  // 11
//                {0, 50},    // 12
//                {0, 50},   // 13
//                {0, 50},   // 14
//                {0, 50},  // 15
//            };


//            public final int  vehicleNumber = 6;
//            public final int vehicleNumber = vehicleList.getNoOfVehicle();
//            public final long[] vehicleCapacities = vehicleList.vehicleCapacity();
//            public final long[] vehicleCapacities = {60, 60, 40, 50, 50 , 50};
//            public final int  depot = 0;

            DataModel() throws ParseException, JsonProcessingException {
            }


            Data d = (new Data());

            GenerateMatrix generateMatrix = new GenerateMatrix();

            //      Generates Displacement matrix and take inputs from dbs

            public final long[][] distanceMatrix = generateMatrix.createDisplacementMatrix(d.getShipment());

            //      Generates Time matrix and take inputs from dbs
            public final long[][] timeMatrix = generateMatrix.createTimeMatrix(d.getShipment(), 900);

            public final String[] addresses = d.getShipment().getAllDeliveryAddresses();

            public final long[] demands = d.createDemandArray(d.getShipment());

            public final long[] vehicleCapacities = d.getAlgosVehicleList()[2].vehicleCapacity();

            public final int vehicleNumber = d.getAlgosVehicleList()[2].getNoOfVehicle();
            public final int depot = 0;

            public final Shipment shipment = d.getShipment();
            public VehicleList vehicleList = d.getAlgosVehicleList()[2];

            public void setAlgosVehicle(VehicleList vehicleList) {
                d.setAlgoVehicles(vehicleList, 2);
            }

            // Generates the Time Window for each order in Shipment
            public final long[][] timeWindows = d.createTimeWindow(0,120);


        }

        /// @brief Print the solution.
         public List<TripItinerary> printSolution(
            DataModel data, RoutingModel routing, RoutingIndexManager manager, Assignment solution,String[] address, ArrayList<Packet> packets) throws Exception {

            RoutingDimension timeDimension = routing.getMutableDimension("Time");

            String[] addr = address; // use when using gentmat to run
//        HashMap<String, Set<String> > Locationcord = new HashMap();
            Shipment shipment = data.shipment;
//        Vehicle vehicle = new Vehicle(); // delete it this temp

//      Setting vehicle details
            VehicleList vehicleList = data.vehicleList;
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
                        droppedPackets.add(packets.get(manager.indexToNode(node) - 1));
                    }
                }
            }
            logger.info("Array of dropped nodes :" + String.valueOf(droppedPackets));

            logger.info(droppedNodes);
            long routeDistance = 0;
            long routeLoad = 0;
            long totalTime = 0;

            for (int i = 0; i < data.vehicleNumber; ++i) {
                TripItinerary tripItinerary = new TripItinerary();
                tripItinerary.setTripItineraryId(String.format("%035d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)));
                tripItinerary.setPlanGeneratedTime(Timestamp.valueOf(LocalDateTime.now()));
                tripItinerary.setPlannedStartTime(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(9,0))));
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
                    routeLoad += data.demands[(int) nodeIndex]; // wasnot here before I put it here for calculating occupied vehicle volume

                    route += manager.indexToNode(index) + " Time(" + solution.min(timeVar)*100 + ","
                            + solution.max(timeVar)*100 + ") -> " + "Address" + addr[(int) nodeIndex] + "-->";

                    if(nodeIndex != 0)
                    {
                        PacketArray.add(packets.get((int) (nodeIndex) - 1));
                    }

                    long vehiclecapacity = data.vehicleCapacities[i]; // Total capacity of a vehicle
                    long occupiedvolume = (((vehiclecapacity - routeLoad)*100)/vehiclecapacity); // gives occupied volume in percentage
                    tripItinerary.setOccupiedVolume(occupiedvolume); // setting occupied volume

//                response = geocode(addr[(int) nodeIndex],data.Key);  // appliacble when using google api to give lat long
//                System.out.println(latlongarr.size()); // To print size of latlongarrray
//                latlongarr.add(response);

                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i) / GenerateMatrix.scaleFactor * Data.getAvgVehicleSpeed();
                    long mileage = 21;
                    long fuelcost  = 70 ;
                    long tripexpense = (avgVechiclespeed*solution.min(timeVar)*fuelcost)/(mileage);
                tripItinerary.setTripExpense(tripexpense); // set total expense
                }

                tripItinerary.setPackets(PacketArray);
                tripItinerary.setAlgorithm("Vrp with Time Window Delivery");
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

            data.setAlgosVehicle(new VehicleList(updatedVehicles));

            logger.info("Total time of all routes: " + totalTime*100 + "m");
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


        public List<TripItinerary> FinalResult(ArrayList<Packet> packets, long setPenalty) throws Exception {
            // Instantiate the data problem.
            final DataModel data = new DataModel();

            RoutingIndexManager manager =
                    new RoutingIndexManager(data.timeMatrix.length, data.vehicleNumber, data.depot);

            RoutingModel routing = new RoutingModel(manager);
            final int transitCallbackIndex =
                    routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                        // Convert from routing variable Index to user NodeIndex.
                        int fromNode = manager.indexToNode(fromIndex);
                        int toNode = manager.indexToNode(toIndex);
                        return data.timeMatrix[fromNode][toNode];
                    });
            routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

            routing.addDimensionWithVehicleCapacity(transitCallbackIndex, // transit callback
                    30, // allow waiting time
                    data.vehicleCapacities, // vehicle maximum capacities
                    false, // start cumul to zero
                    "Time");
            RoutingDimension timeDimension = routing.getMutableDimension("Time");
            // Add time window constraints for each location except depot.
            for (int i = 1; i < data.timeWindows.length; ++i) {
                long index = manager.nodeToIndex(i);
                timeDimension.cumulVar(index).setRange(data.timeWindows[i][0], data.timeWindows[i][1]);
            }
            // Add time window constraints for each vehicle start node.
            for (int i = 0; i < data.vehicleNumber; ++i) {
                long index = routing.start(i);
                timeDimension.cumulVar(index).setRange(data.timeWindows[0][0], data.timeWindows[0][1]);
            }
            for (int i = 0; i < data.vehicleNumber; ++i) {
                routing.addVariableMinimizedByFinalizer(timeDimension.cumulVar(routing.start(i)));
                routing.addVariableMinimizedByFinalizer(timeDimension.cumulVar(routing.end(i)));
            }

//            adding disjunction
//            long penalty = 50;
            long penalty = setPenalty;
            for (int i = 1; i < data.timeMatrix.length; ++i) {
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
            matPrint(data.distanceMatrix , new GenerateMatrix().createTimeMatrix(data.shipment, 900),data.addresses);  // use with google api only

            return printSolution(data, routing, manager, solution,data.addresses,packets);

        }

    }