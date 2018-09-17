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
import com.atm.ast.astatm.SyncFECallTrackerIntentService;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.LocationTrackingDataModel;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
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
    ATMDBHelper atmdbHelper;
    Location location;
    Double lat = 0.0000, lon = 0.0000;
    String address = "";
    public static Double distance = 0.0;

    ASTUIUtil commonFunctions;

    @Override

    public void onCreate() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }


    @Override

    public void onDestroy() {
        super.onDestroy();

    }

    @Override

    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
        Log.v("Background_Service", "Background_Service");

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        userId = pref.getString("userId", "");
        userName = pref.getString("userName", "");

        commonFunctions = new ASTUIUtil();
        atmdbHelper = new ATMDBHelper(SendLocationToServerSevice.this);
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

                locationTrackingDataModel.setTime(String.valueOf(System.currentTimeMillis()));
                locationTrackingDataModel.setDistance(String.valueOf(distance));
                locationTrackingDataModel.setAddress(address);
                locationTrackingDataModel.setLat(String.valueOf(location.getLatitude()));
                locationTrackingDataModel.setLon(String.valueOf(location.getLongitude()));
                locationTrackingDataModel.setUserId(userId);

                atmdbHelper.insertLocationTracker(locationTrackingDataModel);
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
        return super.onUnbind(intent);
    }

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

            SharedPreferences transitsharedpreferences;
            final String TRANSIT_PREFERENCES = "TransitPrefs";
            transitsharedpreferences = getSharedPreferences(TRANSIT_PREFERENCES, Context.MODE_PRIVATE);
            final String selectedSiteName = transitsharedpreferences.getString("SITE_NAME", "");
            final String selectedSiteId = transitsharedpreferences.getString("SITE_ID", "");
            String distanceFeToSite = "";
            Data siteData = atmdbHelper.getPopulateSiteListDataBySiteID(Long.parseLong(selectedSiteId));
            if (location != null && !selectedSiteId.equals("") && !selectedSiteId.equals(null)) {
                if (siteData != null) {
                    double lat = 0.0;
                    double lon = 0.0;
                    if (siteData.getLat() != 0 || siteData.getLon() != 0) {
                        lat = siteData.getLat();
                        lon = siteData.getLon();
                        Location locationDestination = new Location("");
                        locationDestination.setLatitude(lat);
                        locationDestination.setLongitude(lon);
                        distance = (double) location.distanceTo(locationDestination) / 1000;
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


        ServiceCaller serviceCaller = new ServiceCaller(SendLocationToServerSevice.this);
        serviceCaller.CallCommanServiceMethod(serviceURL, "saveLocatonData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseAndSaveLocatonData(result);
                }
            }
        });
    }

    private void parseAndSaveLocatonData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    LocationTrackingDataModel locationTrackingDataModel = new LocationTrackingDataModel();
                    ArrayList<LocationTrackingDataModel> locationTrackingDataList = new ArrayList<LocationTrackingDataModel>();

                    locationTrackingDataList = atmdbHelper.getAllLocationTrackerData();
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
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //progressbar.dismiss();
                e.printStackTrace();
            }

        }
    }

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