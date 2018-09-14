package com.atm.ast.astatm.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.SiteDetailAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Datasummary;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SiteDetailFragment extends MainFragment implements AbsListView.OnScrollListener {
    LinearLayout llSiteDetailTable;
    // ProgressDialog progressbar;
    ASTUIUtil commonFunction;
    int position;
    String uptime, visit, ebRunHour;
    ArrayList<Data> siteDetailViewResDataList;
    SiteDetailAdapter siteDetailAdapter;
    //SiteDetailsDisplayModel siteDetailsDisplayModel;
    TextView tvSiteName;
    TextView tvUptime, tvVisit, tvEBRunHour;
    TextView tvLastUpdated;
    ListView lvSiteDetails;
    String uid = "";
    String userName = "";
    String customerSiteId = "";
    String lat = "25.23";
    String lon = "25.23";
    String siteName, siteId;
    SharedPreferences pref;
    String userId;
    // String userRole, userAccess, r1;
    //private int visibleThreshold = 5;
    private int currentPage = 1;
    // private int previousTotal = 0;
    //private boolean loading = true;
    //  private int selectedPosition = 1;
    boolean loadingMore = false;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_site_detail;
    }

    @Override
    protected void getArgs() {
        customerSiteId = this.getArguments().getString("SITE_ID_NUM");
        siteName = this.getArguments().getString("SITE_NAME");
        siteId = this.getArguments().getString("SITE_ID");
 /*siteDisplayDataModel.setSiteId(customerSiteId);
                    siteDisplayDataModel.setSiteNumId(siteId);

                    need to change this */

    }

    @Override
    protected void loadView() {
        llSiteDetailTable = findViewById(R.id.llSiteDetailTable);
        tvSiteName = findViewById(R.id.tvSiteName);
        tvSiteName.setText(siteName + "(" + customerSiteId + ")");
        tvUptime = findViewById(R.id.tvUptime);
        tvVisit = findViewById(R.id.tvVisit);
        tvEBRunHour = findViewById(R.id.tvEBRunHour);
        tvLastUpdated = findViewById(R.id.tvLastUpdated);
        lvSiteDetails = findViewById(R.id.lvSiteDetails);
    }

    @Override
    protected void setClickListeners() {
        tvUptime.setOnClickListener(this);
        tvVisit.setOnClickListener(this);
        tvEBRunHour.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        commonFunction = new ASTUIUtil();
        pref = getContext().getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        uid = pref.getString("userId", "");
        userName = pref.getString("userName", "");
        siteDetailViewResDataList = new ArrayList<Data>();
        //siteDetailsDisplayModel = new SiteDetailsDisplayModel();
        siteDetailViewResDataList.clear();
        populateSiteData();
        lvSiteDetails.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView lw, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        int lastInScreen = firstVisibleItem + visibleItemCount;
        if ((lastInScreen == totalItemCount) && !(loadingMore)) {
            currentPage = currentPage + 1;
            getSiteDetailsData(currentPage = currentPage);
        }
    }

    public void populateSiteData() {
      /*  Boolean connected = ASTUtil.isNetworkAvailable(getContext());
        try {
            if (!siteId.equalsIgnoreCase("0") && siteId != null) {
                String lastUpdatedDate = "";
                if (atmDatabase.getCircleCount("site_details", "site_detail_num_id", siteId) > 1) {
                    lastUpdatedDate = atmDatabase.getLastUpdatedDate("site_details", "site_detail_last_updated", "site_detail_id", customerSiteId);
                }
                if (lastUpdatedDate.equalsIgnoreCase("")) {
                    lastUpdatedDate = String.valueOf(System.currentTimeMillis());
                }
                atmDatabase.deleteAllRows("site_details");
                siteDetailViewResDataList = atmDatabase.getAllSiteDetailData(siteId);
                siteDetailAdapter = new SiteDetailAdapter(getContext(), siteDetailViewResDataList);
                lvSiteDetails.setAdapter(siteDetailAdapter);
                int siteDetailCount = atmDatabase.getCircleCount("site_details", "site_detail_num_id", siteId);
                if ((connected == true && siteDetailCount == 0) || lastUpdatedDate.equals("")) {
                    getSiteDetailsData(1);
                    long time = System.currentTimeMillis();
                } else if (connected == true && Long.parseLong(lastUpdatedDate + (10 * 60 * 1000)) >= System.currentTimeMillis()) {
                    getSiteDetailsData(1);
                    lastUpdatedDate = String.valueOf(System.currentTimeMillis());
                } else {
                    siteDetailViewResDataList = atmDatabase.getAllSiteDetailData(siteId);
                }
                lastUpdatedDate = commonFunction.formatDate(lastUpdatedDate);
                tvLastUpdated.setText("Last Updated: " + lastUpdatedDate);
            }
        } catch (Exception ex) {

        }*/
        siteDetailAdapter = new SiteDetailAdapter(getContext(), siteDetailViewResDataList);
        lvSiteDetails.setAdapter(siteDetailAdapter);
        String lastUpdatedDate = commonFunction.formatDate(String.valueOf(System.currentTimeMillis()));
        tvLastUpdated.setText("Last Updated: " + lastUpdatedDate);
        if (ASTUIUtil.isOnline(getContext())) {
            getSiteDetailsData(1);
        }
    }

    /*
     *
     * Calling Web Service to get Site Details Data
     */
    private void getSiteDetailsData(int pageNumber) {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        loadingMore = true;
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.SITE_DETAIL_DATA_URL;
        serviceURL += "&uid=" + uid + "&sid=" + siteId + "&page=" + pageNumber + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getSiteDetailsData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSiteDetailData(result);
                } else {
                    ASTUIUtil.showToast("Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     * Parse and Save  Site Details Data
     */

    public void parseandsaveSiteDetailData(String result) {
        if (result != null) {
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {
                    if (serviceData.getDatasummary() != null) {
                        for (Datasummary datasummary : serviceData.getDatasummary()) {
                            uptime = String.valueOf(datasummary.getUt());
                            visit = String.valueOf(datasummary.getVt());
                            ebRunHour = String.valueOf(datasummary.getEb());
                            tvUptime.setText("Uptime: " + uptime + "%");
                            tvVisit.setText("Visit: " + visit);
                            tvEBRunHour.setText("EB Run Hr: " + ebRunHour + " RH");
                        }
                    }
                    if (serviceData.getData() != null) {
                        for (Data data : serviceData.getData()) {
                            siteDetailViewResDataList.add(data);
                        }
                        siteDetailAdapter.notifyDataSetChanged();
                        loadingMore = false;
                    }
                }
            }

            /*try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                String dataSummaryString = jsonRootObject.optString("datasummary").toString();
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArrayDataSummary = jsonRootObject.optJSONArray("datasummary");
                    if (!dataSummaryString.equals("null")) {
                        for (int i = 0; i < jsonArrayDataSummary.length(); i++) {
                            JSONObject jsonObject = jsonArrayDataSummary.getJSONObject(i);
                            uptime = jsonObject.optString("ut").toString();
                            visit = jsonObject.optString("vt").toString();
                            ebRunHour = jsonObject.optString("eb").toString();
                            tvUptime.setText("Uptime: " + uptime + "%");
                            tvVisit.setText("Visit: " + visit);
                            tvEBRunHour.setText("EB Run Hr: " + ebRunHour + " RH");
                        }
                    }
                }
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    int length = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        position = i;
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String date = jsonObject.optString("dt").toString();
                        String colorCode = jsonObject.optString("co").toString();
                        String batteryVoltage = jsonObject.optString("btv").toString();
                        String siteStatus = jsonObject.optString("sst").toString();
                        String inputOutVoltage = jsonObject.optString("iov").toString();
                        String solarCurrent = jsonObject.optString("soa").toString();
                        String batteryChargeCurrent = jsonObject.optString("bta").toString();
                        String batteryDischargeCurrent = jsonObject.optString("bda").toString();
                        String currentAlarm = jsonObject.optString("ca").toString();
                        String loadCurrent = jsonObject.optString("la").toString();
                        siteDetailsDisplayModel = new SiteDetailsDisplayModel();
                        siteDetailsDisplayModel.setDate(date);
                        siteDetailsDisplayModel.setColorCode(colorCode);
                        siteDetailsDisplayModel.setBatteryVoltage(batteryVoltage);
                        siteDetailsDisplayModel.setSiteStatus(siteStatus);
                        siteDetailsDisplayModel.setInputOutVoltage(inputOutVoltage);
                        siteDetailsDisplayModel.setSolarCurrent(solarCurrent);
                        siteDetailsDisplayModel.setBatteryChargeCurrent(batteryChargeCurrent);
                        siteDetailsDisplayModel.setBatteryDischargeCurrent(batteryDischargeCurrent);
                        siteDetailsDisplayModel.setCurrentAlarm(currentAlarm);
                        siteDetailsDisplayModel.setLoadCurrent(loadCurrent);
                        siteDetailViewResDataList.add(siteDetailsDisplayModel);
                    }
                    siteDetailAdapter.notifyDataSetChanged();
                    loadingMore = false;
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }*/
        }

    }

    private void setAdapter() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvUptime) {
            openUpTimeFragment();
        } else if (view.getId() == R.id.tvVisit) {
            openVisitFragment();
        } else if (view.getId() == R.id.tvEBRunHour) {
            openEBRunHourFragmen();
        }

    }


    /**
     * open UpTime Fragment
     */
    public void openUpTimeFragment() {
        UptimeFragment uptimeFragment = new UptimeFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("showMenuButton", false);
        bundle.putString("headerTxt", "Uptime");
        getHostActivity().updateFragment(uptimeFragment, bundle);

    }

    /**
     * open VisitFragment
     */

    public void openVisitFragment() {
        SiteVisitFragment siteVisitFragment = new SiteVisitFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "Site Visit");
        bundle.putBoolean("showMenuButton", false);
        bundle.putString("SITE_ID", siteId);
        bundle.putString("SITE_NAME", siteName);
        bundle.putString("SITE_NUM_ID", customerSiteId);
        bundle.putString("USER_ID", uid);
        getHostActivity().updateFragment(siteVisitFragment, bundle);
    }

    /**
     * open EBRunHourFragment
     */
    public void openEBRunHourFragmen() {
        EBRunHourFragment ebRunHourFragment = new EBRunHourFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "EBRunHour ");
        bundle.putBoolean("showMenuButton", false);
        bundle.putString("SITE_ID", siteId);
        bundle.putString("SITE_NAME", siteName);
        bundle.putString("SITE_NUM_ID", customerSiteId);
        bundle.putString("USER_ID", uid);
        getHostActivity().updateFragment(ebRunHourFragment, bundle);
    }

}
