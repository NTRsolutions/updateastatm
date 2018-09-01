package com.atm.ast.astatm.firebase;

/**
 * @author AST
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.activity.MainActivity;
import com.atm.ast.astatm.fragment.CircleFragment;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class ASTFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private static int notificationId = 0;

    //String mobNumber;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        String notificationBody = remoteMessage.getNotification().getBody();

        if (remoteMessage.getData().size() > 0) {
            Map title = remoteMessage.getData();
        }


        String getFrom = remoteMessage.getFrom();

        //Calling method to generate notification
        //sendNotification(remoteMessage.getNotification().getBody());

        //mobNumber = remoteMessage.getNotification().getBody();
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ATM App")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());




        //callMobile(mobNumber);
    }
    /*public void callMobile(String number) {
        try {
            // set the data
            //String uri = "tel:" + number;
            String uri = "tel:" + "9899155788";
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            HomeActivity.homeContext.startActivity(callIntent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Your call has failed...",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }*/

    /*private void getCallDetails() {

        StringBuffer sb = new StringBuffer();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
*//* Query the CallLog Content Provider *//*
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, strOrder);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Log :");
        {
            managedCursor.moveToFirst();
            String phNum = managedCursor.getString(number);
            String callTypeCode = managedCursor.getString(type);
            String strcallDate = managedCursor.getString(date);
            Date callDate = new Date(Long.valueOf(strcallDate));
            String callDuration = managedCursor.getString(duration);
            String callType = null;
            int callcode = Integer.parseInt(callTypeCode);
            switch (callcode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callType = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = "Missed";
                    break;
            }
            sb.append("\nPhone Number:--- " + phNum + " \nCall Type:--- "
                    + callType + " \nCall Date:--- " + callDate
                    + " \nCall duration in sec :--- " + callDuration);
            //  sb.append("\n----------------------------------");
        }
        managedCursor.close();
    }
*/
}
