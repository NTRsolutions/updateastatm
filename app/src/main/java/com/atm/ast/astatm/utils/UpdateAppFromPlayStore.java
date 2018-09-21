package com.atm.ast.astatm.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.widget.Toast;


import com.atm.ast.astatm.activity.AstAppUgradeDlgActivity;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UpdateAppFromPlayStore extends AsyncTask<String, String, JSONObject> {
    private Context mContext;
    private String currentVersion, latestVersion;

    public UpdateAppFromPlayStore(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            getCurrentVersion();
            String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=" + mContext.getPackageName();
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
                AstAppUgradeDlgActivity fnAppUgradeDlgActivity = new AstAppUgradeDlgActivity(mContext) {
                    @Override
                    public void onSkip() {
                        Toast.makeText(mContext, "Please Update your App", Toast.LENGTH_LONG).show();
                    }
                };
                fnAppUgradeDlgActivity.show();
            }
        } else {
            super.onPostExecute(jsonObject);
        }
    }

    private void getCurrentVersion() {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;
    }
}

