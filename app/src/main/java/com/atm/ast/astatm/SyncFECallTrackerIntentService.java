package com.atm.ast.astatm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.CallTrackerDataModel;

import com.atm.ast.astatm.utils.ASTUIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * @author AST Inc.
 */
public class SyncFECallTrackerIntentService extends IntentService {

    String serviceURL = "";
    ATMDBHelper atmDatabase;
    String lon = "23.23";
    String lan = "23.23";
    SharedPreferences pref;
    String userId;
    String userName = "";
    String uid = "";

    public SyncFECallTrackerIntentService() {
        super(SyncFECallTrackerIntentService.class.getName());
        atmDatabase = new ATMDBHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.v("Service Started", "Service Started");
            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            uid = pref.getString("userId", "");
            userName = pref.getString("userName", "");
            ASTUIUtil commonFunctions = new ASTUIUtil();
            try {
                ArrayList<CallTrackerDataModel> arrCallTrackingData = atmDatabase.getFECallTrackerData();
                Log.v("Call Tracking Length", String.valueOf(arrCallTrackingData.size()));
                if (arrCallTrackingData.size() > 0 && commonFunctions.checkNetwork(SyncFECallTrackerIntentService.this)) {
                    for (int j = 0; j < arrCallTrackingData.size(); j++) {

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
                        saveCallTrackingData(serviceURL, arrCallTrackingDataTemp.get(j).getId());
                    }
                }
            } catch (Exception e) {
            }

        } catch (Exception ex) {
        }
        //Stop service once it finishes its task
        stopSelf();

    }

    private void saveCallTrackingData(String serviceUrl, String id) {
        ServiceCaller serviceCaller = new ServiceCaller(SyncFECallTrackerIntentService.this);
        serviceCaller.CallCommanServiceMethod(serviceURL, "saveCallTrackingData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseAndSaveTrackingData(result, id);
                }
            }
        });
    }

    private void parseAndSaveTrackingData(String result, String callTrackingRowId) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);

                String jsonStatus = jsonRootObject.optString("status").toString();

                if (jsonStatus.equals("2")) {
                    atmDatabase.deleteSelectedRows("table_fe_call_tracker", "id", callTrackingRowId);
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}