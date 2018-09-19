package com.atm.ast.astatm.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.SiteGridAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Header;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.ASTUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SiteFragment extends MainFragment {
    GridView siteViewGrid;
    SiteGridAdapter siteGridAdapter;
    public ArrayList<Data> siteViewResDataList;
    PopupWindow popup = null;
    SharedPreferences pref;
    String userId;
    AutoCompleteTextView etSearch;
    TextView tvSort, tvLastUpdated, tvClearFilter;
    TextView tvTotalSites, tvTotalAlarmSites, tvTotalNonComm, tvTotalInvAlarm, tvTotalLowBattery, tvNsmSites;
    ImageView imgRefresh;
    TextView tvFilter;
    int refresh = 0;
    String userName = "";
    String uid = "";
    String did = "0";
    String ctid = "NA";
    String lat = "25.23";
    String lon = "25.23";
    String districtName = "";
    ATMDBHelper atmdbHelper;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_site;
    }


    @Override
    protected void getArgs() {
        did = this.getArguments().getString("CLUSTER_ID");
        districtName = this.getArguments().getString("CLUSTER_NAME");
        CircleFragment.filterString = this.getArguments().getString("ALARM_FILTER");
        CircleFragment.ctid = this.getArguments().getString("CUSTOMER_FILTER");
    }


    @Override
    protected void loadView() {
        siteViewGrid = findViewById(R.id.siteViewGrid);
        imgRefresh = findViewById(R.id.imgRefresh);
        tvLastUpdated = findViewById(R.id.tvLastUpdated);
        tvSort = findViewById(R.id.tvSort);
        etSearch = findViewById(R.id.etSearch);
        tvTotalSites = findViewById(R.id.tvTotalSites);
        tvTotalAlarmSites = findViewById(R.id.tvTotalAlarmSites);
        tvTotalNonComm = findViewById(R.id.tvTotalNonComm);
        tvTotalInvAlarm = findViewById(R.id.tvTotalInvAlarm);
        tvTotalLowBattery = findViewById(R.id.tvTotalLowBattery);
        tvNsmSites = findViewById(R.id.tvNsmSites);
        tvFilter = findViewById(R.id.tvFilter);
        tvClearFilter = findViewById(R.id.tvClearFilter);
    }

    @Override
    protected void setClickListeners() {
        tvSort.setOnClickListener(this);
        imgRefresh.setOnClickListener(this);
        tvFilter.setOnClickListener(this);
        tvClearFilter.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }


    @Override
    protected void dataToView() {
        atmdbHelper = new ATMDBHelper(getContext());
        popup = new PopupWindow(getContext());
        getSharedprefSData();
        siteViewGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openSiteDetailScreen(position);
            }
        });
        //getSiteData();
        populateSiteGrid();
       /* int siteSearchCount = atmDatabase.getCircleCount("site_search_details", "", "");
        if (siteSearchCount > 0) {
            siteDetailArrayList = atmDatabase.getFilteredData("site_search_name", "", "");
            arrSiteName = new String[siteDetailArrayList.size()];
            arrSiteId = new String[siteDetailArrayList.size()];
            for (int i = 0; i < siteDetailArrayList.size(); i++) {
                arrSiteName[i] = siteDetailArrayList.get(i).getSiteName();
                arrSiteId[i] = siteDetailArrayList.get(i).getSiteId();
            }
            setSiteNameAdapter();
        } else {
            //getSiteSearchData();
        }*/
        popup.setOnDismissListener(() -> {
            if (!CircleFragment.filterString.equals("") || !CircleFragment.filterString.equals("NA")) {
                getSiteData(CircleFragment.filterString);
            }
            if (!CircleFragment.ctid.equals("NA") || !CircleFragment.filterString.equals("NA")) {
                tvClearFilter.setVisibility(View.VISIBLE);
            } else {
                tvClearFilter.setVisibility(View.GONE);
            }
        });
    }

    private void openSiteDetailScreen(int position) {
          /*  stv-siteVoltage
        stn-siteName
        ca-siteNameHindi
        stc-siteId
        cln-clientName
        std-siteStatus
        co-colorCode
        stp-siteHeadContact
        it-latestTiming
        sid-siteNumId*/
        SiteDetailFragment siteDetailFragment = new SiteDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "Site Details");
        bundle.putBoolean("showMenuButton", false);
        bundle.putString("SITE_ID_NUM", siteViewResDataList.get(position).getStc());
        bundle.putString("SITE_NAME", siteViewResDataList.get(position).getStn());
        bundle.putString("SITE_ID", siteViewResDataList.get(position).getSid() + "");
        getHostActivity().updateFragment(siteDetailFragment, bundle);
    }

    /**
     * get Svae Shared Pref Data
     */
    public void getSharedprefSData() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        uid = pref.getString("userId", "");
        userName = pref.getString("userName", "");
    }

    /**
     * Calling Web Service to get Site Data
     */
    private void getSiteData(String alarmTypes) {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        if (CircleFragment.ctid.equals("")) {
            CircleFragment.ctid = "NA";
        }
        if (alarmTypes.equals("")) {
            alarmTypes = "NA";
        }
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.SITE_DATA_NEW_URL;
        serviceURL += "&uid=" + uid + "&did=" + did + "&ctids=" + CircleFragment.ctid + "&alarmtypes=" + alarmTypes + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getSiteData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSiteData(result);
                } else {
                    ASTUIUtil.showToast("Site Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }


    /* *
     * Parse and Save  SiteData
     */

    public void parseandsaveSiteData(String result) {
        if (result != null) {
            boolean msgFlag = true;
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {
                    atmdbHelper.deleteAllRows("SiteDetails");
                    atmdbHelper.insertSiteData(serviceData);
                    msgFlag = false;
                    setAdapter();
                }
            }
            if (msgFlag) {
                ASTUIUtil.showToast("Site Data not available.");
            }
        }

    }

    public void setAdapter() {
        tvLastUpdated.setText("Last Updated: " + ASTUIUtil.formatDate(String.valueOf(System.currentTimeMillis())));

        ATMDBHelper atmdbHelper = new ATMDBHelper(getContext());
        List<ServiceContentData> contentData = atmdbHelper.getAllSiteData();
        if (contentData != null && contentData.size() > 0) {
            for (ServiceContentData data : contentData) {
                if (data != null) {
                    setHeaderData(data.getHeader());
                    Data[] clusterData = data.getData();
                    if (clusterData != null) {
                        siteViewResDataList = new ArrayList<>(Arrays.asList(clusterData));
                        if (siteViewResDataList == null && siteViewResDataList.size() == 0) {
                            siteViewGrid.setEmptyView(siteViewGrid);
                        } else {
                            siteGridAdapter = new SiteGridAdapter(getContext(), siteViewResDataList);
                            siteViewGrid.setEmptyView(siteViewGrid);
                            siteViewGrid.setAdapter(siteGridAdapter);
                            doSortingSiteList("dn", true);//dn means clusterName
                        }
                    }
                }
            }
        }
    }

    /**
     * set Header Data on View
     *
     * @param headerData
     */
    private void setHeaderData(Header[] headerData) {
        for (Header header : headerData) {
            tvTotalSites.setText("Total Sites: " + header.getTotalSites());
            tvTotalAlarmSites.setText("Total Alarm Sites: " + header.getAlarmSites());
            tvTotalNonComm.setText("Total Non Comm: " + header.getNoncomSites());
            tvTotalInvAlarm.setText("Total INV Alarm: " + header.getINVSites());
            tvTotalLowBattery.setText("Total Low Battery: " + header.getLowBatterySies());
            tvNsmSites.setText("NSM Sites: " + header.getNSMSies());
        }
    }

    /**
     * do sorting method
     *
     * @param sortType
     */
    public void doSortingSiteList(String sortType, boolean isAsc) {
        if (siteViewResDataList != null && siteViewResDataList.size() > 0) {
            ASTUtil.sortArray(siteViewResDataList, sortType, isAsc);
            siteGridAdapter.notifyDataSetChanged();
        }
    }

    /**
     * populate Site Drid data into DB
     */

    public void populateSiteGrid() {
        setSiteNameListIntoSearch();
        if (ASTUIUtil.isOnline(getContext())) {
            getSiteData("NA");
        } else {
            setAdapter();
        }
    }

    /**
     * generate Sorting List Data
     */

    public void genrateSortList() {
          /*  stv-siteVoltage
        stn-siteName
        ca-siteNameHindi
        stc-siteId
        cln-clientName
        std-siteStatus
        co-colorCode
        stp-siteHeadContact
        it-latestTiming
        sid-siteNumId*/
        final CharSequence[] items = {
                "A-Z", "Z-A", "SITE_VOLTAGE ↑", "SITE_VOLTAGE ↓"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        doSortingSiteList("stn", true);//dn means siteName
                        break;
                    case 1:
                        doSortingSiteList("stn", false);//dn means siteName
                        break;
                    case 2:
                        doSortingSiteList("stv", false);//dv means clusterAlarmSites
                        break;
                    case 3:
                        doSortingSiteList("stv", true);//dv means clusterAlarmSites
                        break;
                    default:
                        break;
                }
            }
        });
        AlertDialog sortAlert = builder.create();
        sortAlert.show();
    }
    /**
     * set Site Search Name Adapter and openClusterScreen SiteDetail Fragment with search Data
     */
    private void setSiteNameListIntoSearch() {
        List<Data> siteList = atmdbHelper.getAllSiteListData();
        if (siteList != null && siteList.size() > 0) {
            String[] arrSearchData = new String[siteList.size()];
            for (int i = 0; i < siteList.size(); i++) {
                arrSearchData[i] = siteList.get(i).getSiteName();
            }
            ArrayAdapter<String> adapterSiteName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrSearchData);
            etSearch.setAdapter(adapterSiteName);
            etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String searchString = etSearch.getText().toString();
                    if (searchString != null && !searchString.equals("")) {
                        getSiteDetailAndOpenSiteDetailScreen(searchString);
                    }
                }
            });
        }
    }

    //get site detail based on search value form db and open site detail screen
    private void getSiteDetailAndOpenSiteDetailScreen(String searchString) {
        Data siteData = atmdbHelper.getSiteDataBySiteName(searchString);
        if (siteData != null) {
            SiteDetailFragment siteDetailFragment = new SiteDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("headerTxt", "Site Details");
            bundle.putBoolean("showMenuButton", false);
            bundle.putString("SITE_ID_NUM", siteData.getCustomerSiteId());
            bundle.putString("SITE_NAME", siteData.getSiteName());
            bundle.putString("SITE_ID", String.valueOf(siteData.getSiteId()));
            getHostActivity().updateFragment(siteDetailFragment, bundle);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvSort) {
            genrateSortList();
        } else if (view.getId() == R.id.imgRefresh) {
            refresh = 1;
            CircleFragment.ctid = "NA";
            CircleFragment.filterString = "NA";
            getSiteData("NA");
        } else if (view.getId() == R.id.tvFilter) {
            getFiterData();
        } else if (view.getId() == R.id.tvClearFilter) {
            ctid = "NA";
            CircleFragment.filterString = "NA";
            getSiteData(CircleFragment.filterString);
            tvClearFilter.setVisibility(View.GONE);
        }
    }

    /**
     * get Filter Data List and shoe Filterpop dialog
     */

    public void getFiterData() {
    }
}
