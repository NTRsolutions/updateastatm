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
                    int pendingEntries = atmDatabase.getCircleCount("activty_form_data", "", "");
                    /*Intent intentComplaintService = new Intent(CircleActivity.this, SyncComplaintDataWithServer.class);
                    startService(intentComplaintService);*/
                    SyncComplaintData();
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

                    //if (pendingEntries > 0 && connectingToServer == 0) {
                    if (pendingEntries > 0) {
                        for (int j = 0; j < pendingEntries; j++) {
                            //connectingToServer = 1;
                            String[] arrFormData = atmDatabase.getFormData();
                            boolean connected = commonFunctions.checkNetwork(SyncFormDataWithServerIntentService.this);
                            nocEnggNumber = arrFormData[11];
                            if (connected == true) {
                                saveFormDataToServer(arrFormData[2], uid, arrFormData[4],
                                        arrFormData[3], Integer.parseInt(arrFormData[7]),
                                        Integer.parseInt(arrFormData[5]), arrFormData[6], arrFormData[8],
                                        arrFormData[9], arrFormData[10], arrFormData[11],
                                        arrFormData[12], arrFormData[13], arrFormData[14], arrFormData[15],
                                        arrFormData[16], arrFormData[17], arrFormData[18], arrFormData[19],
                                        arrFormData[20], arrFormData[21], arrFormData[22], arrFormData[23],
                                        arrFormData[24], arrFormData[25], arrFormData[26], arrFormData[27],
                                        arrFormData[28], arrFormData[29], arrFormData[30], arrFormData[31],
                                        arrFormData[32], arrFormData[33], arrFormData[34], arrFormData[35],
                                        arrFormData[36], arrFormData[37], arrFormData[38], arrFormData[39]);

                                //getNocEnggListData();

                                formNumber = Integer.parseInt(arrFormData[0]);
                            }
                        }
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

    //---------------------------Save Form Service Call-------------------------------------

    private void saveFormDataToServer(final String siteNumericId, final String userId, final String NocEnggId, final String activityId, final int zoneId,
                                      final int statusId, final String reason, final String taskId, final String materialStatus, final String remarks,
                                      final String nocEnggContact, final String earthVolt, final String battTopup, final String battCells,
                                      final String charger, final String inverter, final String ebConnection, final String conn, final String solar,
                                      final String signOff, final String cell1, final String cell2, final String cell3, final String cell4,
                                      final String cell5, final String cell6, final String cell7, final String cell8, String circleName, String clientName,
                                      String isPlanned, String plannedDate, String submitTime, String planId, String longitude, String latitude,
                                      String daAmount, String dayNumber, String isPm) {
        // TODO Auto-generated method stub
              /* commentList.clear();
        lvComment.setAdapter(null);*/

        serviceURL = Contants.BASE_URL + Contants.ADD_NEW_ACTIVITY_NEW_URL;
        //serviceURL = Contants.BASE_URL + Contants.ADD_NEW_ACTIVITY_URL;
        serviceURL += "&uid=" + userId + "&sid=" + siteNumericId + "&tid=" + taskId + "&aid=" + activityId
                + "&neid=" + NocEnggId + "&st=" + statusId + "&reason=" + reason + "&ztype=" + zoneId
                + "&mid=" + materialStatus + "&remarks=" + remarks + "&isplanned=" + isPlanned + "&ispm=" + isPm + "&earthingvoltage=" + earthVolt +
                "&batterytopup=" + battTopup + "&batterycells=" + battCells + "&charger=" + charger + "&inverter=" + inverter +
                "&ebconnection=" + ebConnection + "&connection=" + conn + "&solar=" + solar + "&signoff=" + signOff +
                "&sgc1=" + cell1 + "&sgc2=" + cell2 + "&sgc3=" + cell3 + "&sgc4=" + cell4 + "&sgc5=" + cell5 +
                "&sgc6=" + cell6 + "&sgc7=" + cell7 + "&sgc8=" + cell8 +
                "&SolarStructure=" + "NA" + "&BattTermnialGreas=" + "NA" + "&Photo=" + "NA" + "&ModemConnection=" + "NA" + "&CommStatu=" + "0" + "&SpareReq=" + "NA" +
                "&plandate=" + plannedDate +
                "&planid=" + planId + "&da=" + daAmount + "&androidtime=" + submitTime + "&numberOfDays=" + dayNumber + "&lat=" + lon + "&lon=" + lon;
        /*serviceURL += "&uid=" + userId + "&sid=" + siteNumericId + "&tid=" + taskId + "&aid=" + activityId
                + "&neid=" + NocEnggId + "&st=" + statusId + "&reason=" + reason + "&ztype=" + zoneId
                + "&mid=" + materialStatus + "&remarks=" + remarks + "&isplanned=" + isPlanned + "&ispm=0" + "&plandate=" + plannedDate +
                "&planid=" + planId + "&androidtime="+ submitTime + "&lat=" + latitude + "&lon=" + longitude;*/

        serviceURL = serviceURL.replace(" ", "^^");

        /*Intent activityIntent = new Intent(SyncFormDataWithServerIntentService.this, SyncDataWithServerActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        activityIntent.putExtra("FORM_NUMBER", formNumber);
        activityIntent.putExtra("CLIENT_NAME", clientName);

        startActivity(activityIntent);*/

        //serviceURL = serviceURL.replace(" ", "%20");

       // RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqSuccessListenerSubmitForm(),
                reqErrorListenerSubmitForm()) {

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
      //  queue.add(myReq);
    }

    private Response.Listener<String> reqSuccessListenerSubmitForm() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressbar.dismiss();
                if (response != null) {
                    try {
                        JSONObject jsonRootObject = new JSONObject(response);
                        String jsonStatus = jsonRootObject.optString("status").toString();

                        if (jsonStatus.equals("2")) {
                            //atmDatabase.deleteSiteSearchData();
                            JSONArray jsonArray = jsonRootObject.optJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray
                                        .getJSONObject(i);
                                Toast.makeText(SyncFormDataWithServerIntentService.this, "Data is Saved on Server", Toast.LENGTH_SHORT).show();

                                atmDatabase.deleteFormData(formNumber);
                                //commonFunction.SendMessage(SyncFormDataWithServerIntentService.this, nocEnggNumber, "");

                            }

                            atmDatabase.deleteAllRows("planned_activity");
                            atmDatabase.deleteAllRows("executed_activity");
                        } else if (jsonStatus.equals("0")) {

                        }
                        //connectingToServer = 0;
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        // connectingToServer = 0;
                    }
                }
            }
        };
    }

    private Response.ErrorListener reqErrorListenerSubmitForm() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorText = error.getLocalizedMessage();
                String errorText2 = error.getLocalizedMessage();

                //commonFunction.SendMessage(SyncFormDataWithServerIntentService.this, "9911012740", serviceURL + " -- " + errorText);


                //connectingToServer = 0;
                //new RequestTask().execute();
                //progressbar.dismiss();
            }
        };
    }

    //--------------------------------------------------------------------------------------

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

    /*//---------------------Calling Web Service to get NOC Engineer List-------------------------

    private void getNocEnggListData() {
        // TODO Auto-generated method stub
        *//*progressbar = ProgressDialog.show(ActivitySheetActivity.this, "",
                "Please wait while getting data..", true);*//*
        //commentList.clear();
        //lvComment.setAdapter(null);
        String serviceURL = "";
        serviceURL = Contants.BASE_URL + Contants.ALL_NOC_ENGG_LIST_URL;

        RequestQueue queue = MyVolley.getRequestQueue();


        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqSuccessListenerNOCEnggList(),
                reqErrorListenerNOCEnggList()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", "3494");
                params.put("ctid", "0");
                params.put("lat", "25.23");
                params.put("lon", "25.23");
                params.put("mr", "1");

                return params;
            }

            ;
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
        queue.add(myReq);
    }

    private Response.Listener<String> reqSuccessListenerNOCEnggList() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressbar.dismiss();
                if (response != null) {
                    try {
                        JSONObject jsonRootObject = new JSONObject(response);
                        String jsonStatus = jsonRootObject.optString("status").toString();

                        if (jsonStatus.equals("2")) {
                            atmDatabase.deleteAllRows("noc_engg_details");
                            JSONArray jsonNocEnggArray = jsonRootObject.optJSONArray("NOCEngineer");

                            JSONArray jsonFieldEnggArray = jsonRootObject.optJSONArray("FieldEngineer");

                        } else if (jsonStatus.equals("0")) {
                            Toast.makeText(SyncFormDataWithServerIntentService.this, "Data is not available", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private Response.ErrorListener reqErrorListenerNOCEnggList() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressbar.dismiss();
            }
        };
    }

    //--------------------------------------------------------------------------------------*/

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

    //----------------------------------------------------------------------------------

    //---------------------------------------------SYnc Complaint-------------------------------------
    public void SyncComplaintData() {
        try {
            complaintArrayList = atmDatabase.getComplaintData();
            Log.v("Complaint Count", String.valueOf(complaintArrayList.size()));
            int pendingEntries = complaintArrayList.size();
            //if (pendingEntries > 0 && connectingToServer == 0) {
            if (pendingEntries > 0) {
                for (int j = 0; j < pendingEntries; j++) {
                    //connectingToServer = 1;
                    ASTUIUtil commonFunctions = new ASTUIUtil();
                    boolean connected = commonFunctions.checkNetwork(SyncFormDataWithServerIntentService.this);

                    if (connected == true) {
                        saveComplaintDataToServer();
                    }
                }
            }
            Thread.sleep(1000 * 60 * 1);

        } catch (Exception e) {
            String ex = e.getLocalizedMessage();
            ex = e.getMessage();
        }
    }
//---------------------------Save Form Service Call-------------------------------------

    private void saveComplaintDataToServer() throws JSONException {
        // TODO Auto-generated method stub

        serviceURL = Contants.BASE_URL_API + Contants.SAVE_COMPLAINT_URL;

        JSONObject jsonBody = new JSONObject();
        /*jsonBody.put("firstkey", "firstvalue");
        jsonBody.put("secondkey", "secondobject");*/
        final String mRequestBody = jsonBody.toString();

        //RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqSuccessListenerSubmitComplaint(),
                reqErrorListenerSubmitComplaint()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("CustomerCode", complaintArrayList.get(0).getClientName());
                params.put("SiteID", complaintArrayList.get(0).getSiteID());
                params.put("Name", complaintArrayList.get(0).getName());
                params.put("Mobile", complaintArrayList.get(0).getMobile());
                params.put("Email", complaintArrayList.get(0).getEmailId());
                params.put("ComplaintType", complaintArrayList.get(0).getType());
                params.put("Priority", complaintArrayList.get(0).getPriority());
                params.put("Remarks", complaintArrayList.get(0).getDescription());
                params.put("IsProposedPlan", complaintArrayList.get(0).getProposePlan());
                params.put("UserId", complaintArrayList.get(0).getUserId());

                /*params.put("CustomerCode", "SBI LKO");
                params.put("SiteID", "16947");
                params.put("Name", "TestUser");
                params.put("Mobile", "9899155788");
                params.put("Email", "mohit.chaudhary@appliedsolartechnologies.com");
                params.put("ComplaintType", "1");
                params.put("Priority", "1280");
                params.put("Remarks", "Low Battery Backup");
                params.put("IsProposedPlan", "1");
                params.put("UserId", "35");*/

                return params;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
       // queue.add(myReq);
    }

    /*private void saveFormDataToServer() throws JSONException {
        // TODO Auto-generated method stub

        serviceURL = Contants.BASE_URL_API + Contants.SAVE_COMPLAINT_URL;

        JSONObject jsonBody = new JSONObject();
       *//* jsonBody.put("CustomerCode", "NA");
        jsonBody.put("SiteID", complaintArrayList.get(0).getSiteID());
        jsonBody.put("Name", complaintArrayList.get(0).getName());
        jsonBody.put("Mobile", complaintArrayList.get(0).getMobile());
        jsonBody.put("Email", complaintArrayList.get(0).getEmailId());
        jsonBody.put("ComplaintType", complaintArrayList.get(0).getType());
        jsonBody.put("Priority", complaintArrayList.get(0).getPriority());
        jsonBody.put("Remarks", complaintArrayList.get(0).getDescription());
        jsonBody.put("IsProposedPlan", complaintArrayList.get(0).getProposePlan());
        jsonBody.put("UserId", complaintArrayList.get(0).getUserId());*//*
        jsonBody.put("CustomerCode", "SBI LKO");
        jsonBody.put("SiteID", "16947");
        jsonBody.put("Name", "TestUser");
        jsonBody.put("Mobile", "9899155788");
        jsonBody.put("Email", "mohit.chaudhary@appliedsolartechnologies.com");
        jsonBody.put("ComplaintType", "1");
        jsonBody.put("Priority", "1280");
        jsonBody.put("Remarks", "Low Battery Backup");
        jsonBody.put("IsProposedPlan", "1");
        jsonBody.put("UserId", "35");

        RequestQueue requestQueue = MyVolley.getRequestQueue();

        final String mRequestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serviceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    responseString = String.valueOf(response.statusCode);

                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }
*/
    private Response.Listener<String> reqSuccessListenerSubmitComplaint() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressbar.dismiss();
                if (response != null) {
                    try {
                        JSONObject jsonRootObject = new JSONObject(response);
                        String jsonStatus = jsonRootObject.optString("Status").toString();
                        String jsonMessage = jsonRootObject.optString("Message").toString();

                        if (jsonStatus.equals("2")) {
                            //atmDatabase.deleteSiteSearchData();
                            atmDatabase.deleteComplaintData(Integer.valueOf(complaintArrayList.get(0).getId().toString()));
                        } else if (jsonStatus.equals("0")) {

                        } else {
                            Toast.makeText(SyncFormDataWithServerIntentService.this, jsonMessage, Toast.LENGTH_SHORT).show();
                        }
                        //connectingToServer = 0;
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        // connectingToServer = 0;
                    }
                }
            }
        };
    }

    private Response.ErrorListener reqErrorListenerSubmitComplaint() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorText = error.getLocalizedMessage();
                String errorText2 = error.getLocalizedMessage();

                //connectingToServer = 0;
                //new RequestTask().execute();
                //progressbar.dismiss();
            }
        };
    }
}