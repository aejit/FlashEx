package com.flashex.tripplanningmicroservice.lib.ORTools.genmatrix;

import com.flashex.tripplanningmicroservice.lib.model.DeliveryAddress;
import com.flashex.tripplanningmicroservice.lib.model.Packet;
import com.flashex.tripplanningmicroservice.lib.model.Shipment;
import com.flashex.tripplanningmicroservice.lib.services.ORService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class GenerateMatrix {

    private static final Logger logger = Logger.getLogger(GenerateMatrix.class.getName());
    @Autowired
    private ORService orService;

    public static int scaleFactor = 1000;

//    static Data d
    public Data createData() {
        Data d = (new Data());
        ArrayData data = new ArrayData();
        data.API_Key = d.getAPI_Key();
        data.addr = d.getAddr();

       /* d.addr = new String[]{
                "3610+Hacks+Cross+Rd+Memphis+TN",   //depot
                "1921+Elvis+Presley+Blvd+Memphis+TN",
                "149+Union+Avenue+Memphis+TN",
                "1034+Audubon+Drive+Memphis+TN",
                "1532+Madison+Ave+Memphis+TN",
                "706+Union+Ave+Memphis+TN",
                "3641+Central+Ave+Memphis+TN",
                "926+E+McLemore+Ave+Memphis+TN",
                "4339+Park+Ave+Memphis+TN",
                "600+Goodwyn+St+Memphis+TN",
                "2000+North+Pkwy+Memphis+TN",
                "262+Danny+Thomas+Pl+Memphis+TN",
                "125+N+Front+St+Memphis+TN",
                "5959+Park+Ave+Memphis+TN",
                "814+Scott+St+Memphis+TN",
                "1005+Tillman+St+Memphis+TN"
        };*/
        return d;
    };

//	Below code is addition of 2d array

    public static int[] add(int[] first, int[] second)
    {
        int length = first.length < second.length ? first.length : second.length;
        int[] result = new int[length];
        for (int i = 0; i < length; i++)
        {
            result[i] = first[i] + second[i];
        }
        return result;
    }

    //	Get String from array below code

    public String buildAddrStr(String[] addr)
    {
        String address =  new String();
        for(int i=0;i<addr.length-1;i++)
        {
            address = address + addr[i] +"|";
        }
        address = address + addr[addr.length-1];
        return address;
    }

    //	below send request to google maps and get json as a response

    public JSONObject sendRequest(String[] origin_addr, String[] dest_addr, String API_key) throws ParseException {
        String originBuiltAddr = buildAddrStr(origin_addr);
        String destBuiltAddr = buildAddrStr(dest_addr);
        String request = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial" + "&origins=" + originBuiltAddr+"&destinations="+ destBuiltAddr+ "&key="+API_key;
        String inline = Urllib.urlopen(request);
        JSONParser parse = new JSONParser();
        JSONObject jobj = (JSONObject) parse.parse(inline);
        return jobj;
    };

    //	Access the json object and generate distance matrix (we are accessing all the json object below for distance matrix)

    public int[][] buildDistMat(JSONObject jobj,int rows, int columns){
        int dist_mat[][] = new int[rows][columns];
        JSONArray jsonarr_1 = (JSONArray) jobj.get("rows");
        for(int i=0;i<jsonarr_1.size();i++){
            JSONObject jobj1 = (JSONObject) jsonarr_1.get(i);
            JSONArray jsonarr_2 = (JSONArray) jobj1.get("elements");
            for(int j=0,k=0;j<jsonarr_2.size();j++){
                JSONObject jobj2 = (JSONObject) jsonarr_2.get(j);
                JSONObject jobjdist = (JSONObject) jobj2.get("distance");
                int dist = Integer.parseInt(jobjdist.get("value").toString());
                dist_mat[i][k] = dist;
                k++;
            }
        }
        return dist_mat;
    };


    //	Access the json object and generate distance matrix (we are accessing all the json object below for distance matrix)

    public int[][] buildTimetravelMat(JSONObject jobj,int rows, int columns){
        int time_mat[][] = new int[rows][columns];
        JSONArray jsonarr_1 = (JSONArray) jobj.get("rows");
        for(int i=0;i<jsonarr_1.size();i++){
            JSONObject jobj1 = (JSONObject) jsonarr_1.get(i);
            JSONArray jsonarr_2 = (JSONArray) jobj1.get("elements");
            for(int j=0,k=0;j<jsonarr_2.size();j++){
                JSONObject jobj2 = (JSONObject) jsonarr_2.get(j);
                JSONObject jobjdist = (JSONObject) jobj2.get("duration");
                int time = Integer.parseInt(jobjdist.get("value").toString());
                time_mat[i][k] = time/100;
                k++;
            }
        }
        return time_mat;
    };

    // Below code generates distance matrix

    public int[][] createDistanceMatrix(Data d) throws ParseException {
        int max_elements = d.addr.length;
        int num_addr = d.addr.length;
        int max_rows = 100/max_elements;   //Fix it to 6 , for this array of address we have to send only 6 rows at a time
        int q = num_addr/max_rows;
        int r = num_addr%max_rows;
        String[] dest_addr = d.addr;
        int[][] dist_mat = new int[max_elements][max_elements];

        for(int i=0;i<q;i++){
            String[] origin_addr = new String[max_rows];
            for(int j=i*max_rows, k=0;j<(i+1)*max_rows;j++,k++){
                System.out.println(j+" "+i+" "+k+" "+d.addr.length);
                origin_addr[k] = d.addr[j];
            }

            JSONObject jobj = sendRequest(origin_addr, dest_addr, d.API_Key);
            int[][] temp = buildDistMat(jobj,max_rows,max_elements);
            for(int j=i*max_rows,k=0;j<(i+1)*max_rows;j++,k++){
                dist_mat[j] = add(temp[k], dist_mat[j]);

            }
        }

        if (r>0){
            String[] origin_addr = new String[r];
            for(int j=q*max_rows, k=0;j<q*max_rows+r;j++,k++){
                origin_addr[k] = d.addr[j];
            }

            JSONObject jobj = sendRequest(origin_addr, dest_addr, d.API_Key);
            int[][] temp = buildDistMat(jobj,max_rows,max_elements);

            for(int j=q*max_rows, k=0;j<q*max_rows+r;j++,k++){
                dist_mat[j] = add(temp[k], dist_mat[j]);

            }
        }

        return dist_mat;
    };

    //    Below function generates time travel matrix


    public int[][] createTimeTravelMatrix(Data d) throws ParseException {
        int max_elements = d.addr.length;
        int num_addr = d.addr.length;
        int max_rows = 100/max_elements;//100/max_elements;   //Fix it to 6 , for this array of address we have to send only 6 rows at a time
        int q = num_addr/max_rows;
        int r = num_addr%max_rows;
        String[] dest_addr = d.addr;
        int[][] time_mat = new int[max_elements][max_elements];

        for(int i=0;i<q;i++){
            String[] origin_addr = new String[max_rows];
            for(int j=i*max_rows, k=0;j<(i+1)*max_rows;j++,k++){
                System.out.println(j+" "+i+" "+k+" "+d.addr.length);
                origin_addr[k] = d.addr[j];
            }

            JSONObject jobj = sendRequest(origin_addr, dest_addr, d.API_Key);
            int[][] temp = buildTimetravelMat(jobj,max_rows,max_elements);
            for(int j=i*max_rows,k=0;j<(i+1)*max_rows;j++,k++){
                time_mat[j] = add(temp[k], time_mat[j]);

            }
        }

        if (r>0){
            String[] origin_addr = new String[r];
            for(int j=q*max_rows, k=0;j<q*max_rows+r;j++,k++){
                origin_addr[k] = d.addr[j];
            }

            JSONObject jobj = sendRequest(origin_addr, dest_addr, d.API_Key);
            int[][] temp = buildTimetravelMat(jobj,max_rows,max_elements);

            for(int j=q*max_rows, k=0;j<q*max_rows+r;j++,k++){
                time_mat[j] = add(temp[k], time_mat[j]);

            }
        }

        return time_mat;
    };

    
    // Returns the displacement between two lat longs in Kilometers
    public double displacementCalculator(double lat1, double lon1, double lat2, double lon2) {

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            if (dist < 0) {
                dist = -dist;
            }
            return dist;
        }
    }

    //This code generates the displacement matrix for the orders based on the latitude and longitude
    public long[][] createDisplacementMatrix(Shipment shipment) {

        logger.info("Shipments inside Displacement matrix: ---------------------------------> "+ shipment);

        int noOfPackets = shipment.getPacketList().size();
        logger.info("noOfPackets --------------------> "+noOfPackets);
        ArrayList<Packet> packetList = shipment.getPacketList();
        long[][] dispMatrix = new long[noOfPackets+1][noOfPackets+1];

        logger.info("Shipment origin address: ---------------------------------> "+ shipment.getOriginAddress());

        DeliveryAddress originAddress = shipment.getOriginAddress();

        double[] lats = new double[noOfPackets+1];
        double[] longs = new double[noOfPackets+1];

        for (int i=0; i<noOfPackets+1; i++) {
            if (i == 0) {
                lats[i] = originAddress.getLatitude();
                longs[i] = originAddress.getLongitude();
            } else {
                lats[i] = packetList.get(i-1).getDeliveryAddress().getLatitude();
                longs[i] = packetList.get(i-1).getDeliveryAddress().getLongitude();
            }
        }

        logger.info("lats and longs --------------------------> "+lats+"--------"+longs+"lenght"+lats.length);

        for (int i=0; i<lats.length; i++) {
            for (int j=i; j<longs.length; j++) {
                dispMatrix[i][j] = (long)(displacementCalculator(lats[i], longs[i], lats[j], longs[j]) * scaleFactor);
                dispMatrix[j][i] = (long)(displacementCalculator(lats[i], longs[i], lats[j], longs[j]) * scaleFactor);
            }
        }

        for (int i =0; i<dispMatrix.length; i++ )
        {
            logger.info("Displacement matrix  "+i+" -------------------------------------> : "+ dispMatrix[i].toString());
        }
        return dispMatrix;

    }

    // Creates timeMatrix based on the average Speed of a vehicle
    public long[][] createTimeMatrix (Shipment shipment, int avgSpeed) {
        long[][] timeMatrixFromDispMatrix = createDisplacementMatrix(shipment);

        for(int i=0; i<timeMatrixFromDispMatrix[0].length; i++) {
            for(int j=i; j<timeMatrixFromDispMatrix[0].length; j++) {
                timeMatrixFromDispMatrix[i][j] = timeMatrixFromDispMatrix[i][j] / avgSpeed;
                timeMatrixFromDispMatrix[j][i] = timeMatrixFromDispMatrix[i][j];
            }
        }
        return timeMatrixFromDispMatrix;
    }

}
