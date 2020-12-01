package com.flashex.tripplanningmicroservice.lib.ORTools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flashex.tripplanningmicroservice.lib.ORTools.genmatrix.Data;
import com.flashex.tripplanningmicroservice.lib.ORTools.genmatrix.GenerateMatrix;
import com.flashex.tripplanningmicroservice.lib.getjsonserver.GetJsonServerData;
import com.flashex.tripplanningmicroservice.lib.model.*;
import com.flashex.tripplanningmicroservice.lib.services.ProducerService;
import com.flashex.tripplanningmicroservice.lib.services.TripItineraryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.constraintsolver.main;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@EnableAutoConfiguration
public class VrpWithCapacityConstraint {

    @Autowired
    private TripItineraryService tripItineraryService;

    @Autowired
    private ProducerService producerService;

    /** Minimal VRP.*/
    private static final Logger logger = Logger.getLogger(VrpWithCapacityConstraint.class.getName());

    static class DataModel {

//        Distance matrix

//        public final long[][] distanceMatrix = {
//
//        {0,24357,33350,14928,31957,32166,19319,28392,15621,21404,28731,34765,35142,10523,26727,26303},
//        {25244,0,8314,11334,5869,7131,10678,3270,11311,7873,11350,9729,10107,19122,12350,13609},
//        {34062,8491,0,14384,4086,1651,11008,4239,14501,9627,7179,1864,925,27939,9730,9997},
//        {15494,10806,14024,0,11065,12841,4047,11458,635,5226,10788,15681,16059,6012,9180,9507},
//        {33351,5882,4096,11348,0,2671,7364,4363,11118,6736,3619,5108,5485,20768,6649,7076},
//        {32878,7307,1651,13200,2902,0,9824,3840,13318,8444,7203,2748,2269,26756,9086,9462},
//        {19361,10678,11017,4038,7398,9834,0,9159,3808,2809,7099,10921,13146,9186,5491,5812},
//        {29097,3270,4257,11458,4350,3848,9159,0,11228,6354,9303,5359,5151,22975,10620,11562},
//        {15873,10762,13795,635,10836,12611,3817,11228,0,4997,10559,15452,15829,5496,8950,9278},
//        {21831,7873,9492,5226,6282,8308,2809,6354,4997,0,6967,11149,11526,10374,5119,6383},
//        {28822,11931,6831,10681,3305,5965,6999,10627,10451,7159,0,5693,6341,18297,3267,4068},
//        {35172,9601,1824,14537,4703,2861,10856,5438,14308,10803,5964,0,1392,29095,8515,8782},
//        {36058,10487,927,16446,5590,2257,11253,5134,16564,11689,7277,1512,0,29982,9828,10095},
//        {11333,19346,28339,6012,20917,27155,9194,23381,5496,10374,18568,29458,29835,0,16564,16140},
//        {27151,11444,9719,10131,6656,9092,5640,10421,9901,6417,3335,8581,9228,16626,0,1264},
//        {27191,14469,10406,10534,7093,9676,5879,13164,10304,6422,3933,9268,9915,16666,1288,0}
//        };

//        Data d = (new Data());
//        public final String[] addresses = d.getAddr();


      /*  GenerateMatrix matGenerator = new GenerateMatrix();
        Data d = matGenerator.createData();
        public final int[][] distanceMatrix = matGenerator.createDistanceMatrix(d);
        public final String[] addresses = Data.addr;
        public final String Key = d.API_Key;
        public final int[][] distmat = matGenerator.createDistanceMatrix(d);
        public final int[][] timemat = matGenerator.createTimeTravelMatrix(d);*/ // This is working in, when we use real addresses array

//        public final long[] demands = {0, 1, 1, 2, 4, 2, 4, 8, 8, 1, 2, 1, 2, 4, 4, 8, 8};

        //        public final long[] vehicleCapacities = vehicleList.vehicleCapacity();
//        public final long[] vehicleCapacities = {20,20,20,20};
//        public final int vehicleNumber = vehicleList.getNoOfVehicle();
//        public final int vehicleNumber = 4;

//        public final int depot = 0;

        DataModel() throws ParseException, IOException {
        }

        Data d = (new Data());

        GenerateMatrix generateMatrix = new GenerateMatrix();

        //      Generates Displacement matrix and take inputs from dbs

        public final long[][] distanceMatrix = generateMatrix.createDisplacementMatrix(d.getShipment());

        public final String[] addresses = d.getShipment().getAllDeliveryAddresses();

        public final long[] demands = d.createDemandArray(d.getShipment());

        public final long[] vehicleCapacities = d.getAlgosVehicleList()[0].vehicleCapacity();

        public final int vehicleNumber = d.getAlgosVehicleList()[0].getNoOfVehicle();
        public final int depot = 0;

        public final Shipment shipment = d.getShipment();
        public VehicleList vehicleList = d.getAlgosVehicleList()[0];

        public void setAlgosVehicle(VehicleList vehicleList) {
            d.setAlgoVehicles(vehicleList, 0);
        }

    }

    /// @brief Print the solution.
     public List<TripItinerary> printSolution(
            DataModel data, RoutingModel routing, RoutingIndexManager manager, Assignment solution, String[] address, ArrayList<Packet> packets) throws Exception {

        String[] addr = address;
//        HashMap<String, Set<String>> Locationcord = new HashMap();

        Shipment shipment = data.shipment;
//        Vehicle vehicle = new Vehicle(); // delete it this temp

//      Setting vehicle details
        VehicleList vehicleList = data.vehicleList;
//        logger.info((""+ vehicleList.listofvehicle));

        // Inspect solution.
        long totalDistance = 0;
        long totalLoad = 0;

        List<TripItinerary> trips = new ArrayList<TripItinerary>();
        List<Vehicle> updatedVehicles = new ArrayList<>(vehicleList.getListofvehicle());

        for (int i = 0; i < data.vehicleNumber; ++i) {

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
                routeLoad += data.demands[(int) nodeIndex];

                long vehiclecapacity = data.vehicleCapacities[i]; // Total capacity of a vehicle
                long occupiedvolume = (((vehiclecapacity - routeLoad)*100)/vehiclecapacity);
                tripItinerary.setOccupiedVolume(occupiedvolume); // setting occupied volume

                route += nodeIndex + " Load(" + routeLoad + ")  -> " + "Address" + addr[(int) nodeIndex] + "-->";
//                response = geocode(addr[(int) nodeIndex],data.Key);
//                latlongarr.add(response);

//                logger.info("nodeIndex ------------> "+ nodeIndex);

                if( nodeIndex != 0 )
                {
                    PacketArray.add(packets.get((int) nodeIndex - 1));
                }

                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i) / GenerateMatrix.scaleFactor;

                tripItinerary.setPlannedTotalDistance(routeDistance); // set route distance
                long mileage = 21;
                long fuelcost = 70 ;
                long tripexpense = (routeDistance*fuelcost)/mileage;
                tripItinerary.setTripExpense(tripexpense);

            }
            tripItinerary.setPackets(PacketArray);
            tripItinerary.setAlgorithm("Vrp with Capacity Constraint");
//            tripItinerary.setOriginAddress("117,Above SBI, Opposite Raheja Arcade,7th Block,Koramangala,Bengaluru,Karnataka,560095");
            logger.info("Origin Address ----------------------> Shipment: "+shipment.getOriginAddress()+" Data: "+data.d.getShipment().getOriginAddress());
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

        data.setAlgosVehicle(new VehicleList(updatedVehicles));
        updatedVehicles.forEach(vehicle -> {
            logger.info("Updated vehicle list ----------------> "+vehicle.getVehicleId());
        });

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


    public List<TripItinerary> FinalResult(ArrayList<Packet> packets) throws Exception {
        // Instantiate the data problem.
        final DataModel data = new DataModel();

        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(data.distanceMatrix.length, data.vehicleNumber, data.depot);

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.distanceMatrix[fromNode][toNode];
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Add Capacity constraint.
        final int demandCallbackIndex = routing.registerUnaryTransitCallback((long fromIndex) -> {
            // Convert from routing variable Index to user NodeIndex.
            int fromNode = manager.indexToNode(fromIndex);
            return data.demands[fromNode];
        });
        routing.addDimensionWithVehicleCapacity(demandCallbackIndex, 0, // null capacity slack
                data.vehicleCapacities, // vehicle maximum capacities
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

        // Print solution on console.
        return printSolution(data, routing, manager, solution, data.addresses, packets);

//    //    Prints distance and time matrices when using generate matrix to generate distance and time-travel matrix
//        matPrint(data.distanceMatrix, new GenerateMatrix().createTimeMatrix(data.shipment, 21) , data.addresses);
    }

}