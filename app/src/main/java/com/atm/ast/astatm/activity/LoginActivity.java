package com.atm.ast.astatm.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.ASTUtil;
import com.atm.ast.astatm.utils.GCM_Registration;


public class LoginActivity extends AppCompatActivity {

    Button btnLogIn;
    EditText etUserName, etPassword;
    RelativeLayout relativeLayout;
    SharedPreferences pref;
    GCM_Registration gcmClass;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (ASTUtil.isNetworkAvailable(this)) {
            gcmClass = new GCM_Registration();
            gcmClass.getRegId(getApplicationContext());
        }
        loadView();
        datatoView();
    }

    //get view ids
    public void loadView() {
        btnLogIn = findViewById(R.id.btnLogIn);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPass);
        relativeLayout = findViewById(R.id.relativeLayout);
    }

    //get data from UI
    public void datatoView() {
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                String lat = "23.30";
                String lon = "23.30";
                String osVersion = ASTUIUtil.getOsVersion();
                String versionName = ASTUIUtil.getAppVersionName(LoginActivity.this);
                Boolean connected = ASTUIUtil.checkNetwork(LoginActivity.this);
                if (userName.equals("")) {
                    ASTUIUtil.showToast(LoginActivity.this, "Please Provide Username");
                } else if (password.equals("")) {
                    ASTUIUtil.showToast(LoginActivity.this, "Please Provide Password");
                } else {
                    String serviceURL = Contants.BASE_URL + Contants.LOGIN_URL;
                    serviceURL += "&username=" + userName + "&password=" + password + "&version=" + osVersion + "&rid="
                            + gcmClass.regid + "&AndroidAppVersionNo=" + versionName
                            + "&lat=" + lat + "&lon=" + lon;
                    login(LoginActivity.this, serviceURL, userName);
                }
            }
        });
    }


    /*
     *
     * call Login Services
     */
    public void login(Context context1, String serviceUrl, String uID) {
        if (ASTUIUtil.isOnline(this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(LoginActivity.this);
            dotDialog.show();
            gcmClass = new GCM_Registration();
            if (gcmClass.regid.equals("")) {
                gcmClass.getRegId(this.getApplicationContext());
            }
            ServiceCaller serviceCaller = new ServiceCaller(this);
            String serviceURL = serviceUrl;
            serviceCaller.CallCommanServiceMethod(serviceURL, "login", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        parseLoginServiceData(result);
                    } else {
                        ASTUIUtil.alertForErrorMessage(Contants.Error, LoginActivity.this);
                    }
                    if (dotDialog.isShowing()) {
                        dotDialog.dismiss();
                    }
                }
            });
        } else {
            ASTUIUtil.alertForErrorMessage(Contants.OFFLINE_MESSAGE, this);//off line msg....
        }
    }

    /*
     *
     * Parse and Validate Login Service Data
     */
    private void parseLoginServiceData(String result) {
        if (result != null) {
            try {
                pref = this.getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                userId = pref.getString("userId", "");
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("1")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String uid = jsonObject.optString("uid").toString();
                        String un = jsonObject.optString("un").toString();
                        String ue = jsonObject.optString("ue").toString();
                        String rid = jsonObject.optString("rid").toString();
                        String ua = jsonObject.optString("ua").toString();
                        String rl = jsonObject.optString("rl").toString();
                        String callTrack = jsonObject.optString("CallTrack").toString();
                        String emailId = jsonObject.optString("Email").toString();
                        String mobileNum = jsonObject.optString("Mobile").toString();
                        String menuId = jsonObject.optString("MenuIds").toString();
                        SharedPreferences.Editor editor = pref.edit();
                        //------------------ Storing data as KEY/VALUE pair ------------
                        editor.putString("userId", uid);
                        editor.putString("roleId", rid);
                        editor.putString("userAccess", ua);
                        editor.putString("r1", rl);
                        editor.putString("userName", un);
                        editor.putString("CallTracker", callTrack);
                        editor.putString("EmailId", emailId);
                        editor.putString("MobileNum", mobileNum);
                        editor.putString("MenuId", menuId);
                        editor.commit();
                        Intent intentHomeScreen = new Intent();
                        intentHomeScreen.setClass(this, MainActivity.class);
                        startActivity(intentHomeScreen);
                    }
                } else {
                    ASTUIUtil.showToast(LoginActivity.this, "Please Provide Correct Credentials");
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        }

    }

}
