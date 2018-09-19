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

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.ComplaintDataModel;
import com.atm.ast.astatm.model.ContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.GCM_Registration;
import com.google.gson.Gson;

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
        getCurrentVersion();
        ApplicationHelper.application().initIcons();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String userId = pref.getString("userId", "");
        atmdbHelper = new ATMDBHelper(SplashScreen.this);
        ArrayList<ComplaintDataModel> complaintArrayList = atmdbHelper.getComplaintData();
        if (complaintArrayList != null && complaintArrayList.size() > 0) {
            for (ComplaintDataModel dataModel : complaintArrayList) {
                saveComplainData(dataModel);
            }
        }

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

    //send offline save complain data to server
    private void saveComplainData(ComplaintDataModel dataModel) {
        ServiceCaller serviceCaller = new ServiceCaller(SplashScreen.this);
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("CustomerCode", dataModel.getClientName());
            mainObj.put("SiteID", dataModel.getSiteID());
            mainObj.put("Name", dataModel.getName());
            mainObj.put("Mobile", dataModel.getMobile());
            mainObj.put("Email", dataModel.getEmailId());
            mainObj.put("ComplaintType", dataModel.getType());
            mainObj.put("Priority", dataModel.getPriority());
            mainObj.put("Remarks", dataModel.getDescription());
            mainObj.put("IsProposedPlan", dataModel.getProposePlan());
            mainObj.put("UserId", dataModel.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String serviceURL = "";
        serviceURL = Contants.BASE_URL_API + Contants.SAVE_COMPLAINT_URL;
        serviceCaller.CallCommanServiceMethod(serviceURL, mainObj, "ComplaintDescriptionData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveComlaintData(result, dataModel.getId());
                } else {
                    ASTUIUtil.showToast("Your ticket not submitted successfully!");
                }
            }
        });
    }

    /*
     *
     * Parse and save Comlaint Data
     */
    public void parseandsaveComlaintData(String result, String id) {
        if (result != null) {
            try {
                ContentData data = new Gson().fromJson(result, ContentData.class);
                if (data.getStatus() == 2) {
                    ASTUIUtil.showToast("Your ticket has been submitted successfully");
                    atmdbHelper.deleteComplaintData(Integer.valueOf(id));
                } else {
                    ASTUIUtil.showToast(data.getMessage());
                }
            } catch (Exception e) {
            }
        }
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


    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;
        new GetLatestVersion().execute();
    }

    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=com.atm.ast.astatm";
                Document doc = Jsoup.connect(urlOfAppFromPlayStore).get();
                latestVersion = doc.getElementsByClass("htlgb").get(6).text();
            } catch (Exception e) {
                e.printStackTrace();

            }

            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    if (!isFinishing()) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
                        AstAppUgradeDlgActivity fnAppUgradeDlgActivity = new AstAppUgradeDlgActivity(SplashScreen.this) {
                            @Override
                            public void onSkip() {
                                ASTUIUtil.showToast("Please Update your App");
                            }
                        };
                        fnAppUgradeDlgActivity.show();
                    }
                }
            } else
                super.onPostExecute(jsonObject);
        }
    }

}
