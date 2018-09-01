package com.atm.ast.astatm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.model.FillSiteActivityModel;
import com.atm.ast.astatm.utils.ASTUIUtil;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author AST Inc.
 */
public class SyncSiteAddressDataWithServer extends IntentService {

    AtmDatabase atmDatabase;
    private boolean isRunning = false;
    String serviceURL = "";
    SharedPreferences pref;
    String siteAddressId = "";
    ArrayList<FillSiteActivityModel> arrSiteAddressData;
    String userName = "";
    String uid = "";
    ASTUIUtil commonFunctions;
    private LocationManager lm;
    public SyncSiteAddressDataWithServer() {
        super(SyncFormDataWithServerIntentService.class.getName());
        //context = this.context;
        atmDatabase = new AtmDatabase(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            commonFunctions = new ASTUIUtil();
            Log.v("Service Started", "Service Started");
            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            uid = pref.getString("userId", "");
            userName = pref.getString("userName", "");
            for (int i = 0; i < 2000000000; i++) {
                try {
                    int pendingEntries = atmDatabase.getAllRowCount("fill_site_data");
                    if (pendingEntries > 0 && commonFunctions.checkNetwork(this)) {
                        arrSiteAddressData = new ArrayList<>();
                        arrSiteAddressData = atmDatabase.getSiteAddress();
                        if (arrSiteAddressData.size() > 0 && isRunning == false) {
                            isRunning = true;
                            saveSiteAddressData();
                        }
                    }
                    Thread.sleep(1000 * 60 * 1);
                } catch (Exception e) {
                    String ex = e.getLocalizedMessage();
                    ex = e.getMessage();
                }
                if (isRunning) {
                    //Log.i(TAG, "Service running");
                }
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                }
            }).start();

        } catch (Exception ex) {
        }
        stopSelf();

    }

    //---------------------------Save Transit Service Call-------------------------------------

    private void saveSiteAddressData() {
        // TODO Auto-generated method stub
        String siteId = arrSiteAddressData.get(0).getSiteId();
        //String customerSiteId = arrSiteAddressData.get(0).getCustomerSiteId();
        //String siteName = arrSiteAddressData.get(0).getSiteName();
        String branchName = arrSiteAddressData.get(0).getBranchName();
        String branchCode = arrSiteAddressData.get(0).getBranchCode();
        String onOffSite = arrSiteAddressData.get(0).getOnOffSite();
        String address1 = arrSiteAddressData.get(0).getAddress();
        String city = arrSiteAddressData.get(0).getCity();
        String circleId = arrSiteAddressData.get(0).getCircleId();
        String districtId = arrSiteAddressData.get(0).getDistrictId();
        String tehsilId = arrSiteAddressData.get(0).getTehsilId();
        String pincode = arrSiteAddressData.get(0).getPincode();
        String lat = arrSiteAddressData.get(0).getLat();
        String lon = arrSiteAddressData.get(0).getLon();
        String startTime = arrSiteAddressData.get(0).getFunctionalFromTime();
        String endTime = arrSiteAddressData.get(0).getFunctionalToTime();
        siteAddressId = arrSiteAddressData.get(0).getSiteAddressId();
        if (lat == null) {
            lat = "0.000000";
        } else if (lon == null) {
            lon = "0.000000";
        }
        if (siteId.equals("") || siteId.equals(null)) {
            siteId = "000000";
        }
        if (branchName.equals("") || branchName.equals(null)) {
            branchName = "NA";
        }
        if (branchCode.equals("") || branchCode.equals(null)) {
            branchCode = "000000";
        }
        if (onOffSite.equals("") || onOffSite.equals(null)) {
            onOffSite = "NA";
        }
        if (address1.equals("") || address1.equals(null)) {
            address1 = "NA";
        }
        if (city.equals("") || city.equals(null)) {
            city = "NA";
        }
        if (circleId.equals("0") || circleId.equals(null)) {
            circleId = "000000";
        }
        if (districtId.equals("0") || districtId.equals(null)) {
            districtId = "000000";
        }
        if (tehsilId.equals("0") || tehsilId.equals(null)) {
            tehsilId = "000000";
        }
        if (pincode.equals("") || pincode.equals(null)) {
            pincode = "000000";
        }
        if (lat.equals("") || lat.equals(null)) {
            lat = "0";
        }
        if (lon.equals("") || lon.equals(null)) {
            lon = "0";
        }

        address1.replace("", "^^");

        serviceURL = Contants.BASE_URL + Contants.ADD_SITE_ADDRESS;

       /* serviceURL += "&uid=" + userId + "&sid=" + siteNumId + "&btnid=" + transitType + "&transittime=" + date +
                "&lat=" + lat + "&lon=" + lon;*/
        serviceURL += "&sid=" + siteId + "&branchname=" + branchName + "&branchcode=" + branchCode + "&city=" + city +
                "&pincode=" + pincode + "&onoffsite=" + onOffSite + "&address=" + address1 + "&circleid=" + circleId +
                "&districtid=" + districtId + "&tehsilid=" + tehsilId + "&lat=" + lat + "&lon=" + lon +
                "&fnhrfromtime=" + startTime + "&fnhrtotime=" + endTime;
        serviceURL = serviceURL.replace(" ", "^^");

        Log.v("URL", serviceURL);

        //      RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqSuccessListener(),
                reqErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
               /* params.put("uid", userId);
                params.put("sid", siteNumericId);
                params.put("tid", taskId);
                params.put("aid", activityId);
                params.put("lon", lon);*/

                return params;
            }

            ;
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
        //queue.add(myReq);
    }

    private Response.Listener<String> reqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressbar.dismiss();
                if (response != null) {
                    try {
                        JSONObject jsonRootObject = new JSONObject(response);
                        String jsonStatus = jsonRootObject.optString("status").toString();

                        if (jsonStatus.equals("2")) {
                            Toast.makeText(SyncSiteAddressDataWithServer.this, "Site Address Data Saved on Server.", Toast.LENGTH_SHORT).show();
                            atmDatabase.deleteSelectedRows("fill_site_data", "id", siteAddressId);
                            Log.v("URL", "Success");
                        } else if (jsonStatus.equals("0")) {
                            isRunning = false;
                            //Toast.makeText(SyncTransitDataWithServer.this, "Server Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private Response.ErrorListener reqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorText = error.getLocalizedMessage();
                String errorText2 = error.getLocalizedMessage();
                isRunning = false;
                //commonFunctions.SendMessage(SyncTransitDataWithServer.this, "9911012740", serviceURL + "  ---  " + errorText);

                //  Toast.makeText(SyncTransitDataWithServer.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        };
    }


}

