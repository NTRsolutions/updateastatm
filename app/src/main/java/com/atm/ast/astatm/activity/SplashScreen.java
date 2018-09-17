package com.atm.ast.astatm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.UnsyncedActivityFormAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.ComplaintDataModel;
import com.atm.ast.astatm.model.ContentData;
import com.atm.ast.astatm.model.FillSiteActivityModel;
import com.atm.ast.astatm.model.newmodel.ActivitySheetModel;
import com.atm.ast.astatm.model.newmodel.ContentLocalData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.GCM_Registration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Narayan Semwal on 12-09-2017.
 */

public class SplashScreen extends AppCompatActivity {
    String gcmRegId;
    SharedPreferences pref;
    GCM_Registration gcmClass;
    ATMDBHelper atmdbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ApplicationHelper.application().initIcons();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String userId = pref.getString("userId", "");
        atmdbHelper = new ATMDBHelper(SplashScreen.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                String userId = pref.getString("userId", "");
                if (!userId.equals("")) {
                    Intent intentLoggedIn = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intentLoggedIn);
                } else {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 1000);
    }



}
