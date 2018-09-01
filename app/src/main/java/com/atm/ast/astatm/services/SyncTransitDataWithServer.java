package com.atm.ast.astatm.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.SyncFormDataWithServerIntentService;
import com.atm.ast.astatm.adapter.TodayPlanListAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.model.TransitDataModel;
import com.atm.ast.astatm.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by AST on 1/29/2016.
 */
public class SyncTransitDataWithServer extends IntentService {

    AtmDatabase atmDatabase;
    private boolean isRunning = false;
    String serviceURL = "";
    SharedPreferences pref;
    ArrayList<TransitDataModel> arrTransitData;
    String userName = "";
    String uid = "";
    ASTUIUtil commonFunctions;
    private LocationManager lm;

    public SyncTransitDataWithServer() {
        super(SyncFormDataWithServerIntentService.class.getName());
        atmDatabase = new AtmDatabase(this);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            commonFunctions = new ASTUIUtil();
            Log.v("Service Started", "Service Started");
            //Shared Prefrences
            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            uid = pref.getString("userId", "");
            userName = pref.getString("userName", "");
            for (int i = 0; i < 200000000; i++) {
                try {
                    int pendingEntries = atmDatabase.getAllRowCount("transit");
                    if (pendingEntries > 0 && commonFunctions.isOnline(this)) {
                        arrTransitData = new ArrayList<>();
                        arrTransitData = atmDatabase.getTransitData("0");
                        if (arrTransitData.size() > 0 && isRunning == false) {
                            isRunning = true;
                            saveTransitData();
                        }
                    }

                    Thread.sleep(1000 * 60);

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

                }
            }).start();

        } catch (Exception ex) {
        }
        //Stop service once it finishes its task
        stopSelf();

    }

    /**
     * Save Transit Service Call
     */

    private void saveTransitData() {
        // TODO Auto-generated method stub
        String userId = arrTransitData.get(0).getUserId();
        String siteNumId = arrTransitData.get(0).getSiteId();
        String transitType = arrTransitData.get(0).getType();
        String date = arrTransitData.get(0).getDateTime();
        String lat = arrTransitData.get(0).getLatitude();
        String lon = arrTransitData.get(0).getLongitude();
        String calculatedDistance = arrTransitData.get(0).getCalcilatedDistance();
        String calculatedAmount = arrTransitData.get(0).getCalculatedAmount();
        String address = arrTransitData.get(0).getAddress();
        String actualDistance = arrTransitData.get(0).getActualKms();
        String actualAmount = arrTransitData.get(0).getActualAmt();
        String remarks = arrTransitData.get(0).getRemarks();
        String hotelExpenses = arrTransitData.get(0).getHotelExpense();
        hotelExpenses = hotelExpenses.replace("Rs.", "0");
        String actualHotelExpenses = arrTransitData.get(0).getActualHotelExpense();
        if (Double.parseDouble(calculatedAmount) > 1000) {
            calculatedDistance = "0";
            calculatedAmount = "0";
        }

        if (calculatedDistance.equals("") || calculatedDistance.equals(null)) {
            calculatedDistance = "0";
        }
        if (calculatedAmount.equals("") || calculatedAmount.equals(null)) {
            calculatedAmount = "0";
        }
        if (address.equals("") || address.equals(null)) {
            address = "NA";
        }
        if (actualDistance.equals("") || actualDistance.equals(null)) {
            actualDistance = "0";
        }
        if (actualAmount.equals("") || actualAmount.equals(null)) {
            actualAmount = "0";
        }
        if (remarks.equals("") || remarks.equals(null)) {
            remarks = "NA";
        }


        ServiceCaller serviceCaller = new ServiceCaller(ApplicationHelper.application().getContext());
        serviceURL = Contants.BASE_URL + Contants.ADD_TRANSIT_URL;
        serviceURL += "&uid=" + userId + "&sid=" + siteNumId + "&btnid=" + transitType + "&transittime=" + date +
                "&distance=" + calculatedDistance + "&address=" + address + "&calculatedamt=" + calculatedAmount + "&actualtravelamt=" + actualAmount + "&remark=" + remarks +
                "&actualkms=" + actualDistance + "&actualhotelprice=" + hotelExpenses + "&hotelprice=" + actualHotelExpenses + "&lat=" + lat + "&lon=" + lon;
        serviceURL = serviceURL.replace(" ", "^^");
        serviceCaller.CallCommanServiceMethod(serviceURL, "getEbrhData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveTransitData(result);
                } else {
                    ASTUIUtil.showToast("Transit Data Not Saved");
                }
            }
        });
    }

    /*
     *
     * Parse and Save Transit Data
     */

    public void parseandsaveTransitData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    String id = arrTransitData.get(0).getId();
                    atmDatabase.deleteSelectedRows("transit", "transit_id", id);
                    ASTUIUtil.showToast("Saved on Server");
                    isRunning = false;
                } else if (jsonStatus.equals("0")) {
                    isRunning = false;
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }
    }

}
