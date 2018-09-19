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
    ASTUIUtil commonFunction;
    int position;
    String uptime, visit, ebRunHour;
    ArrayList<Data> siteDetailViewResDataList;
    SiteDetailAdapter siteDetailAdapter;
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
    private int currentPage = 1;
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
        }

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
