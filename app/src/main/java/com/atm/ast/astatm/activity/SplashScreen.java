package com.atm.ast.astatm.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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
import com.atm.ast.astatm.utils.UpdateAppFromPlayStore;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;


/**
 * Created by Narayan Semwal on 12-09-2017.
 */

public class SplashScreen extends AppCompatActivity {
    private String gcmRegId;
    private SharedPreferences pref;
    private GCM_Registration gcmClass;
    private ATMDBHelper atmdbHelper;
    private String currentVersion, latestVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        // checkForceUpdateStatus();
        UpdateAppFromPlayStore appFromPlayStore = new UpdateAppFromPlayStore(SplashScreen.this);
        appFromPlayStore.execute();
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




 /*   //check app need force update or not
    @SuppressLint("StaticFieldLeak")
    private void checkForceUpdateStatus() {
        final String cureentVersion = ASTUIUtil.getAppVersionName(this);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String latestVersion = null;
                try {
                    String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=com.atm.ast.astatm";
                    org.jsoup.nodes.Document doc = Jsoup.connect(urlOfAppFromPlayStore).get();
                    latestVersion = doc.getElementsByAttributeValue("itemprop", "softwareVersion").first().text();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return latestVersion;
            }

            @Override
            protected void onPostExecute(String latestVersion) {
                super.onPostExecute(latestVersion);
                if (latestVersion != null) {
                    if (!cureentVersion.equals(latestVersion)) {
                        AstAppUgradeDlgActivity fnAppUgradeDlgActivity = new AstAppUgradeDlgActivity(SplashScreen.this) {
                            @Override
                            public void onSkip() {
                                ASTUIUtil.showToast("Please Update your App");
                            }
                        };
                        fnAppUgradeDlgActivity.show();
                    }
                }

            }
        }.execute(null, null, null);
    }
*/



}
