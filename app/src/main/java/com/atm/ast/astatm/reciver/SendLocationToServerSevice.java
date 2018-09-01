package com.atm.ast.astatm.reciver;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.model.LocationTrackingDataModel;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.GPSTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * /** * @author AST Inc.  07/04/2016.
 */
public class SendLocationToServerSevice extends Service {

    ProgressDialog progressbar;
    //Shared Prefrences
    SharedPreferences pref;
    String userId, userRole, userAccess, r1;
    String userName = "";
    //-----------------

    AtmDatabase atmDatabase;
    Location location;
    Double lat = 0.0000, lon = 0.0000;
    String address = "";
    public static Double distance = 0.0;

    ASTUIUtil commonFunctions;

    @Override

    public void onCreate() {

// TODO Auto-generated method stub


        // Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();

    }

    @Override
    public IBinder onBind(Intent intent) {

// TODO Auto-generated method stub

        // Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();

        return null;

    }


    @Override

    public void onDestroy() {

// TODO Auto-generated method stub

        super.onDestroy();

        // Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();

    }

    @Override

    public void onStart(Intent intent, int startId) {

// TODO Auto-generated method stub

        super.onStart(intent, startId);
        Log.v("Background_Service", "Background_Service");

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        userId = pref.getString("userId", "");
        userName = pref.getString("userName", "");

        commonFunctions = new ASTUIUtil();

        try {
            location = getLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.v("Location", String.valueOf(location.getLongitude()) + "___" + String.valueOf(location.getLatitude()));

        //distance = commonFunctions.getDistance(28.6664, 77.2073, 28.4986, 77.3999, "K");
        distance = 0.0;

        long currentMilliseconds = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        //calendar.setTimeInMillis(currentMilliseconds);

        long morningEight = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, 20);
        //calendar.setTimeInMillis(currentMilliseconds);
        long eveningEight = calendar.getTimeInMillis();

        if (!address.equals("") && currentMilliseconds > morningEight && currentMilliseconds < eveningEight) {
            if (commonFunctions.checkNetwork(SendLocationToServerSevice.this)) {
                saveLocatonData("");
            } else {
                LocationTrackingDataModel locationTrackingDataModel = new LocationTrackingDataModel();
                ArrayList<LocationTrackingDataModel> locationTrackingDataList = new ArrayList<LocationTrackingDataModel>();

                locationTrackingDataModel.setTime(String.valueOf(System.currentTimeMillis()));
                locationTrackingDataModel.setDistance(String.valueOf(distance));
                locationTrackingDataModel.setAddress(address);
                locationTrackingDataModel.setLat(String.valueOf(location.getLatitude()));
                locationTrackingDataModel.setLon(String.valueOf(location.getLongitude()));
                locationTrackingDataModel.setUserId(userId);

                locationTrackingDataList.add(locationTrackingDataModel);

                atmDatabase.addLocationTrackedData(locationTrackingDataList);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Log.v("Background_Service", "Background_Service");

            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            userId = pref.getString("userId", "");
            userName = pref.getString("userName", "");

            commonFunctions = new ASTUIUtil();

            try {
                location = getLocation();
                if (location != null) {
                    Log.v("Location", String.valueOf(location.getLongitude()) + "___" + String.valueOf(location.getLatitude()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //distance = commonFunctions.getDistance(28.6664, 77.2073, 28.4986, 77.3999, "K");
            distance = 0.0;

            long currentMilliseconds = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            //calendar.setTimeInMillis(currentMilliseconds);

            long morningEight = calendar.getTimeInMillis();

            calendar.set(Calendar.HOUR_OF_DAY, 20);
            //calendar.setTimeInMillis(currentMilliseconds);
            long eveningEight = calendar.getTimeInMillis();

            if (!address.equals("") && currentMilliseconds > morningEight && currentMilliseconds < eveningEight
                    || location != null || !address.equalsIgnoreCase("Unable to connect")) {
                if (location != null) {
                    saveLocatonData("");
                }

            }
        } catch (Exception ex) {
            if (ex != null && commonFunctions.checkNetwork(SendLocationToServerSevice.this)) {
            }
        }
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {

// TODO Auto-generated method stub

        //Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();

        return super.onUnbind(intent);
    }

    //---------------------Calling Web Service to Save Location Data--------------------------
    private void saveLocatonData(String offlineLocationData) {
        String serviceURL = "";
        if (offlineLocationData.equals("")) {
            distance = 0.0;
            if (location == null) {
                try {
                    location = getLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            atmDatabase = new AtmDatabase(SendLocationToServerSevice.this);
            SharedPreferences transitsharedpreferences;
            final String TRANSIT_PREFERENCES = "TransitPrefs";
            transitsharedpreferences = getSharedPreferences(TRANSIT_PREFERENCES, Context.MODE_PRIVATE);
            final String selectedSiteName = transitsharedpreferences.getString("SITE_NAME", "");
            final String selectedSiteId = transitsharedpreferences.getString("SITE_ID", "");
            String distanceFeToSite = "";
            ArrayList<SiteDisplayDataModel> arrFilteredData = atmDatabase.getFilteredData("", "", "Survey");
            if (location != null && !selectedSiteId.equals("") && !selectedSiteId.equals(null)) {
                for (int i = 0; i < arrFilteredData.size(); i++) {
                    if (arrFilteredData.get(i).getSiteId().equalsIgnoreCase(selectedSiteId)) {
                        double lat = 0.0;
                        double lon = 0.0;
                        String kkk = arrFilteredData.get(i).getSiteLat();
                        String klk = arrFilteredData.get(i).getSiteLong();
                        if (!arrFilteredData.get(i).getSiteLat().equalsIgnoreCase("0.0") || !arrFilteredData.get(i).getSiteLong().equalsIgnoreCase("0.0")) {
                            lat = Double.valueOf(arrFilteredData.get(i).getSiteLat());
                            lon = Double.valueOf(arrFilteredData.get(i).getSiteLong());
                            Location locationDestination = new Location("");
                            locationDestination.setLatitude(lat);
                            locationDestination.setLongitude(lon);
                            distance = (double) location.distanceTo(locationDestination) / 1000;
                        }
                    }
                }
            }
            String appversionName = ASTUIUtil.getAppVersionName(SendLocationToServerSevice.this);
        /*serviceURL = Contants.BASE_URL + Contants.LAST_TRACKED_DATA_URL;
        serviceURL += "&uid=" + userId + "&distance=" + String.format("%.0f", distance) + "&address=" + address +
                "&lat=" + location.getLatitude() + "&lon=" + location.getLongitude();*/
            serviceURL = Contants.BASE_URL + Contants.LAST_TRACKED_DATA_URL;
            serviceURL += "&uid=" + userId + "&distance=" + String.format("%.0f", distance) + "&address=" + address +
                    "&versionno=" + appversionName + "&lat=" + location.getLatitude() + "&lon=" + location.getLongitude();

        } else {
            serviceURL = offlineLocationData;
        }

        serviceURL = serviceURL.replace(" ", "^^");
        // RequestQueue queue = MyVolley.getRequestQueue();
        StringRequest myReq = new StringRequest(Request.Method.POST, serviceURL, reqSuccessListenerSiteDetails(),
                reqErrorListenerSiteDetails()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                /*params.put("uid", "3494");
                params.put("ctid", "0");
                params.put("lat", "25.23");
                params.put("lon", "25.23");
                params.put("mr", "1");*/

                return params;
            }

            ;
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
        //      queue.add(myReq);
    }

    private Response.Listener<String> reqSuccessListenerSiteDetails() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressbar.dismiss();
                if (response != null) {
                    try {
                        JSONObject jsonRootObject = new JSONObject(response);
                        String jsonStatus = jsonRootObject.optString("status").toString();
                        if (jsonStatus.equals("2")) {
                            LocationTrackingDataModel locationTrackingDataModel = new LocationTrackingDataModel();
                            ArrayList<LocationTrackingDataModel> locationTrackingDataList = new ArrayList<LocationTrackingDataModel>();

                            locationTrackingDataList = atmDatabase.getTrackedLocationData();
                            String appversionName = ASTUIUtil.getAppVersionName(SendLocationToServerSevice.this);
                            if (locationTrackingDataList.size() > 0) {
                                String offlineServiceURL = "&uid=" + locationTrackingDataList.get(0).getUserId() +
                                        "&distance=" + locationTrackingDataList.get(0).getDistance() +
                                        "&address=" + locationTrackingDataList.get(0).getAddress() +
                                        "&versionno=" + appversionName +
                                        "&lat=" + locationTrackingDataList.get(0).getLat() +
                                        "&lon=" + locationTrackingDataList.get(0).getLon();
                            }
                        }

                        /*if (jsonStatus.equals("2")) {

                        } else if (jsonStatus.equals("0")) {
                            //pxt(SendLocationToServerSevice.this, "No Site Found");
                        }*/

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //progressbar.dismiss();
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private Response.ErrorListener reqErrorListenerSiteDetails() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String strError = error.getMessage();
                String strError1 = error.getLocalizedMessage();
                String sms = "";
                //progressbar.dismiss();
            }
        };
    }

    //----------------------------------------------------------------------------------

    public Location getLocation() throws IOException {
        GPSTracker gpsTracker = new GPSTracker(SendLocationToServerSevice.this);
        location = gpsTracker.getLocation();

        if (location == null) {
            //Toast.makeText(TransitActivity.this, "Location Not Available");
        } else {
            lat = location.getLatitude();
            lon = location.getLongitude();
            //address = "NA";
            address = commonFunctions.getLocationAddress(location, SendLocationToServerSevice.this, "");
            Log.v("Address", address);

        }
        return location;
    }


}