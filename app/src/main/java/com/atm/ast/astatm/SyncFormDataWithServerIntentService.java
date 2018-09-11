package com.atm.ast.astatm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.atm.ast.astatm.model.CallTrackerDataModel;
import com.atm.ast.astatm.model.ComplaintDataModel;
import com.atm.ast.astatm.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AST Inc.
 */
public class SyncFormDataWithServerIntentService extends IntentService {

    private boolean isRunning = false;
    String serviceURL = "";
    //int connectingToServer = 0;
    AtmDatabase atmDatabase;
    ArrayList<ComplaintDataModel> complaintArrayList;
    //Context context;
    String lon = "23.23";
    String lan = "23.23";
    int formNumber;
    String[] arrSaveData;
    ASTUIUtil commonFunction;
    //Shared Prefrences
    SharedPreferences pref;
    String userId, userRole, userAccess, r1;
    String userName = "";
    String uid = "";
    String nocEnggNumber;
    public SyncFormDataWithServerIntentService() {
        super(SyncFormDataWithServerIntentService.class.getName());
        //context = this.context;
        atmDatabase = new AtmDatabase(this);
        isRunning = true;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.v("Service Started", "Service Started");
            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            uid = pref.getString("userId", "");
            userName = pref.getString("userName", "");
            ASTUIUtil commonFunctions = new ASTUIUtil();
            for (int i = 0; i < 1; i++) {
                try {
                  //  int pendingEntries = atmDatabase.getCircleCount("activty_form_data", "", "");

                    ArrayList<CallTrackerDataModel> arrCallTrackingData = atmDatabase.getFECallTrackerData();
                    Log.v("Call Tracking Length", String.valueOf(arrCallTrackingData.size()));
                    if (arrCallTrackingData.size() > 0 && commonFunctions.checkNetwork(SyncFormDataWithServerIntentService.this)) {
                        for (int j = 0; j < arrCallTrackingData.size(); j++) {
                            /*Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {*/
                            ArrayList<CallTrackerDataModel> arrCallTrackingDataTemp = atmDatabase.getFECallTrackerData();
                            String serviceURL = "";
                            //serviceURL = Contants.BASE_URL + Contants.GET_SITE_MASTER;
                            serviceURL = Contants.BASE_URL + Contants.SAVE_CALL_TRACKING_URL;
                            serviceURL += "&dialeruserid=" + arrCallTrackingDataTemp.get(j).getDialerUserId() +
                                    "&dialeduserid=" + arrCallTrackingDataTemp.get(j).getDialledEmpId() +
                                    "&duration=" + arrCallTrackingDataTemp.get(j).getDuration() +
                                    "&calltime=" + arrCallTrackingDataTemp.get(j).getCallTime() +
                                    "&calltype=" + arrCallTrackingDataTemp.get(j).getCallType() +
                                    "&dialedmobile=" + arrCallTrackingDataTemp.get(j).getDialledNumber();

                            Log.v("CallTracker", serviceURL);
                            //WebServiceCalls webServiceCalls = new WebServiceCalls();
                            saveCallTrackingData(serviceURL, arrCallTrackingDataTemp.get(j).getId());
                        }
                            /*}, 1000 * 60 * 2);
                        }*/
                    }
                    Thread.sleep(1000);


                } catch (Exception e) {
                    String ex = e.getLocalizedMessage();
                    ex = e.getMessage();
                }

                if (isRunning) {
                    //Log.i(TAG, "Service running");
                }

            }

            //Creating new thread for my service
            //Always write your long running tasks in a separate thread, to avoid ANR
            new Thread(new Runnable() {
                @Override
                public void run() {

                    //Your logic that service will perform will be placed here
                    //In this example we are just looping and waits for 1000 milliseconds in each loop.

                    //Toast.makeText(SyncFormDataWithServerIntentService.this, "Service Started", Toast.LENGTH_SHORT).show();


                }
            }).start();

        } catch (Exception ex) {
        }
        //Stop service once it finishes its task
        stopSelf();

    }
    public class RequestTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            try {

                // Replace it with your own WCF service path
                URL json = new URL(serviceURL);
                URLConnection jc = json.openConnection();

                //Log.v("URL",accountNameServiceUrl);

                //BufferedReader reader = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(serviceURL)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
                BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));

                String line = reader.readLine();
                Log.v("line", line);

                JSONObject jsonResponse = new JSONObject(line);
                String status = jsonResponse.getString("status");
                if (status.equals("2")) {
                    Toast.makeText(SyncFormDataWithServerIntentService.this, "Data is Saved on Server", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                String errorText = e.getLocalizedMessage();
                String errorText1 = e.getMessage();
                //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected void onPostExecute(String result) {

        }
    }

    String callTrackingRowId = "0";

    public void saveCallTrackingData(String serviceUrl, String id) {
        // TODO Auto-generated method stub
        /*progressbar = ProgressDialog.show(CircleActivity.this, "",
                "Please wait while getting data..", true);*/
       /* commentList.clear();
        lvComment.setAdapter(null);*/
        callTrackingRowId = id;

       // RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceUrl, reqSaveCallSuccessListener(),
                reqSaveCallErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
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
      //  queue.add(myReq);
    }

    private Response.Listener<String> reqSaveCallSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressbar.dismiss();
                if (response != null) {
                    try {
                        JSONObject jsonRootObject = new JSONObject(response);

                        String jsonStatus = jsonRootObject.optString("status").toString();

                        if (jsonStatus.equals("2")) {
                            AtmDatabase atmDatabase = new AtmDatabase(SyncFormDataWithServerIntentService.this);
                            atmDatabase.deleteSelectedRows("table_fe_call_tracker", "id", callTrackingRowId);
                        } else {
                            //Toast.makeText(CircleActivity.this, "Data not available.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private Response.ErrorListener reqSaveCallErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressbar.dismiss();
            }
        };
    }
}