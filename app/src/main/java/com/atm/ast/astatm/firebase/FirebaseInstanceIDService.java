package com.atm.ast.astatm.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * @author AST
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    public static String regid = "";

    @Override
    public void onTokenRefresh() {
        regid = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + regid);

    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }

    public String getRegId(){
        regid = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + regid);
        return  regid;
    }
}
