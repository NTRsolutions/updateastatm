package com.atm.ast.astatm.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.model.CallTrackerDataModel;
import com.atm.ast.astatm.model.NocEnggListDataModel;
import com.atm.ast.astatm.model.newmodel.FieldEngineer;
import com.atm.ast.astatm.model.newmodel.NOCEngineer;

import java.util.ArrayList;
import java.util.Date;


import static android.content.Context.MODE_PRIVATE;


public class CallReceiverBroadcastReciver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            SharedPreferences pref;
            pref = context.getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            String callTracking = pref.getString("CallTracker", "0");
            //String locationTracking = pref.getString("rl", "0");

            Log.v("CallTracker", callTracking);
            if (callTracking.equals("1")) {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    Toast.makeText(context, "Phone Is Ringing", Toast.LENGTH_LONG).show();
                }

                if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    Toast.makeText(context, "Call Recieved", Toast.LENGTH_LONG).show();
                }

                if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    Toast.makeText(context, "Phone Is Idle", Toast.LENGTH_LONG).show();
                    getLastDialledNumber(context);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLastDialledNumber(Context homeContext) {
        StringBuffer sb = new StringBuffer();
        String strOrder = CallLog.Calls.DATE + " DESC";
        /* Query the CallLog Content Provider */
        if (ActivityCompat.checkSelfPermission(homeContext, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return TODO;
        }
        Cursor managedCursor = homeContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, strOrder);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Log :");
        {
            SharedPreferences pref;
            pref = homeContext.getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            String userId = pref.getString("userId", "0000");
            String regMobNum = pref.getString("userMob", "_");

            managedCursor.moveToFirst();
            String phNum = managedCursor.getString(number);
            String callTypeCode = managedCursor.getString(type);
            String strcallDate = managedCursor.getString(date);
            Date callDate = new Date(Long.valueOf(strcallDate));
            String callDuration = managedCursor.getString(duration);
            String callType = null;
            int callcode = Integer.parseInt(callTypeCode);

            switch (callcode) {
                case CallLog.Calls.INCOMING_TYPE:
                    callType = "Incomming";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "Outgoing";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = "Missed";
                    break;
            }

            final ATMDBHelper atmDatabase = new ATMDBHelper(homeContext);

            ArrayList<NOCEngineer> arrNocEngg = new ArrayList<>();
            ArrayList<FieldEngineer> arrFieldEngg = new ArrayList<>();

            arrNocEngg = atmDatabase.getAllNOCEngineerData();
            arrFieldEngg = atmDatabase.getAllFieldEngineerData();

            String dialledEmpId = "0";

            for (int i = 0; i < arrNocEngg.size(); i++) {
                if (arrNocEngg.get(i).getContactNo().equals(phNum)) {
                    dialledEmpId = arrNocEngg.get(i).getNocEngId() + "";
                }
            }
            for (int i = 0; i < arrFieldEngg.size(); i++) {
                if (arrFieldEngg.get(i).getContactNo().equals(phNum)) {
                    dialledEmpId = arrFieldEngg.get(i).getFieldEngId() + "";
                }
            }

            //String dialledEmpId = atmDatabase.getSelectedNocEnggData(phNum).toString();

            ArrayList<CallTrackerDataModel> arrFeCallTrackerData = new ArrayList<>();
            CallTrackerDataModel feCallTrackerDataModel = new CallTrackerDataModel();
            feCallTrackerDataModel.setDuration(callDuration);
            feCallTrackerDataModel.setDialledNumber(phNum);
            feCallTrackerDataModel.setDialledEmpId(dialledEmpId);
            feCallTrackerDataModel.setCallTime(strcallDate);
            feCallTrackerDataModel.setCallType(callType);
            feCallTrackerDataModel.setDialerUserId(userId);

            arrFeCallTrackerData.add(feCallTrackerDataModel);

            atmDatabase.addFeCallTrackerData(arrFeCallTrackerData);

            /*Intent intentService = new Intent(homeContext, SyncFormDataWithServerIntentService.class);
            homeContext.startService(intentService);*/

            /*if (commonFunctions.checkNetwork(homeContext) == true) {
                ArrayList<CallTrackerDataModel> arrCallTrackingData = atmDatabase.getFECallTrackerData();
                if (arrCallTrackingData.size() > 0) {
                    for (int i = 0; i < arrCallTrackingData.size(); i++) {
                        *//*Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {

                            @Override
                            public void run() {*//*
                        ArrayList<CallTrackerDataModel> arrCallTrackingDataTemp = atmDatabase.getFECallTrackerData();

                        String serviceURL = "";
                        //serviceURL = Contants.BASE_URL + Contants.GET_SITE_MASTER;
                        serviceURL = Contants.BASE_URL + Contants.SAVE_CALL_TRACKING_URL;
                        serviceURL += "&dialeruserid=" + arrCallTrackingDataTemp.get(i).getDialerUserId() +
                                "&dialeduserid=" + arrCallTrackingDataTemp.get(i).getDialerUserId() +
                                "&duration=" + arrCallTrackingDataTemp.get(i).getDuration() +
                                "&calltime=" + arrCallTrackingDataTemp.get(i).getCallTime() +
                                "&calltype=" + arrCallTrackingDataTemp.get(i).getCallType() +
                                "dialedmobile=" + arrCallTrackingDataTemp.get(i).getDialledNumber();
                        WebServiceCalls webServiceCalls = new WebServiceCalls();
                        webServiceCalls.saveCallTrackingData(serviceURL, arrCallTrackingDataTemp.get(i).getId());
                    }
                       *//* }, 1000 * 60 * 2);
                    }*//*
                }
            }*/
            managedCursor.close();
            return "";
        }
    }
}