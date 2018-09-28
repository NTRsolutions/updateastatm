package com.atm.ast.astatm.fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.activity.SplashScreen;
import com.atm.ast.astatm.adapter.CircleGridAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.firebase.FirebaseInstanceIDService;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.CircleDisplayDataModel;
import com.atm.ast.astatm.model.ComplaintDataModel;
import com.atm.ast.astatm.model.ContentData;
import com.atm.ast.astatm.model.FillSiteActivityModel;
import com.atm.ast.astatm.model.newmodel.ActivitySheetModel;
import com.atm.ast.astatm.model.newmodel.ContentLocalData;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Header;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.reciver.SendLocationToServerSideReciver;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.ASTUtil;
import com.atm.ast.astatm.utils.LogAnalyticsHelper;
import com.atm.ast.astatm.utils.TooltipWindow;
import com.atm.ast.astatm.utils.UpdateAppFromPlayStore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CircleFragment extends MainFragment {
    GridView circleViewGrid;
    TooltipWindow tooltipWindow;
    TextView tvClearFilter;
    TextView tvTotalSites, tvTotalAlarmSites, tvTotalNonComm, tvTotalInvAlarm, tvTotalLowBattery, tvNSMSites;
    PopupWindow popup = null;
    String apkName;
    String apkVersionNo = "";
    int refresh = 0;
    public static String filterString = "NA";
    public static Boolean[] arrSelectedFilterOne;
    public static Boolean[] arrSelectedFilterTwo;
    public List<Data> circleViewResDataList;
    CircleGridAdapter circleGridAdapter;
    SharedPreferences pref;
    String userName = "";
    String applicationName = "";
    String firstTimeLoad = "";
    String callTracking = "0";
    String locationTracking = "0";
    String empEmpId = "";
    String empMobile = "";
    String empMenuId = "";
    String fcmRegID = "";
    //  String[] arrSiteName, arrSiteId;
    AutoCompleteTextView etSearch;
    LinearLayout llCircle;
    TextView tvSort;
    TextView tvFilter;
    TextView tvLastUpdated;
    TextView btnUpdateAvailable;
    ImageView imgRefresh;
    String uid = "";
    public static String ctid = "NA";
    String lat = "25.23";
    String lon = "25.23";
    String sid = "";
    LogAnalyticsHelper analyticsHelper = null;
    ATMDBHelper atmdbHelper;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_circle;
    }

    @Override
    protected void loadView() {
        this.etSearch = this.findViewById(R.id.etSearch);
        this.llCircle = this.findViewById(R.id.llCircle);
        this.tvSort = this.findViewById(R.id.tvSort);
        this.tvLastUpdated = this.findViewById(R.id.tvLastUpdated);
        this.imgRefresh = this.findViewById(R.id.imgRefresh);
        this.tvFilter = this.findViewById(R.id.tvFilter);
        this.tvTotalSites = this.findViewById(R.id.tvTotalSites);
        this.tvTotalAlarmSites = this.findViewById(R.id.tvTotalAlarmSites);
        this.tvTotalNonComm = this.findViewById(R.id.tvTotalNonComm);
        this.tvTotalInvAlarm = this.findViewById(R.id.tvTotalInvAlarm);
        this.tvTotalLowBattery = this.findViewById(R.id.tvTotalLowBattery);
        this.tvNSMSites = this.findViewById(R.id.tvNSMSites);
        this.tvClearFilter = this.findViewById(R.id.tvClearFilter);
        this.btnUpdateAvailable = this.findViewById(R.id.btnUpdateAvailable);
        this.circleViewGrid = this.findViewById(R.id.circleViewGrid);
    }

    @Override
    protected void setClickListeners() {
        this.etSearch.setOnClickListener(this);
        this.tvSort.setOnClickListener(this);
        this.tvFilter.setOnClickListener(this);
        this.imgRefresh.setOnClickListener(this);
        this.btnUpdateAvailable.setOnClickListener(this);
        this.tvClearFilter.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getSharePrefData();
        populateCircleDataToShowCircle();
        atmdbHelper = new ATMDBHelper(getContext());
        ArrayList<Data> dataArrayList = atmdbHelper.getAllStateDetailListData();
        if (dataArrayList.size() <= 0) {
            getStateData();
        }
        //check latest app available or not
        if (ASTUIUtil.isOnline(getContext())) {
            //getLatestAppName();
            //UpdateAppFromPlayStore appFromPlayStore = new UpdateAppFromPlayStore(getContext());
            //appFromPlayStore.execute();
        }
        if (fcmRegID.equalsIgnoreCase("")) {
            final FirebaseInstanceIDService fcmClass = new FirebaseInstanceIDService();
            String regId = fcmClass.getRegId();
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("fcmRegID", regId);
            editor.commit();
        }

        circleViewGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openClusterScreen(position);
            }
        });
        gestureDetectorAction();
        popup = new PopupWindow();
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!filterString.equals("") || !filterString.equals("NA")) {
                    getCircleData(filterString);
                }
                if (!ctid.equals("NA") || !filterString.equals("NA")) {
                    tvClearFilter.setVisibility(View.VISIBLE);
                } else {
                    tvClearFilter.setVisibility(View.GONE);
                }

            }
        });

        //-----------------------Set On Touch Listner--------------------
        llCircle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                ASTUIUtil.hideSoftKeyboard(getHostActivity());
                return false;
            }
        });
        //geting customer list
        if (ASTUIUtil.isOnline(getContext())) {
            getCustomerData();
        }
        //geting all site list
        if (ASTUIUtil.isOnline(getContext())) {
            getSiteSearchData();
        } else {
            setSiteNameListIntoSearch();
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("firstTimeLoad", "1");
        editor.commit();
        logAnalytics();
        startLocationAlarmService();
        syncOfflinData();
    }

    //openClusterScreen Cluster screen
    private void openClusterScreen(int position) {
        ClusterFragment clusterFragment = new ClusterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", circleViewResDataList.get(position).getCin() + " " + "District");
        bundle.putBoolean("showMenuButton", false);
        bundle.putString("CIRCLE_ID", String.valueOf(circleViewResDataList.get(position).getCiid()));
        bundle.putString("CIRCLE_NAME", circleViewResDataList.get(position).getCin());
        bundle.putString("ALARM_FILTER", filterString);
        bundle.putString("CUSTOMER_FILTER", ctid);
        ctid = "NA";
        getHostActivity().updateFragment(clusterFragment, bundle);
    }

    //get circle screen logs and send into firebase
    private void logAnalytics() {
        analyticsHelper = new LogAnalyticsHelper(getContext());
        Bundle bundle = new Bundle();
        bundle.putString("User Name", userName);
        bundle.putString("User ID", uid);
        bundle.putString("emp Mobile", empMobile);
        bundle.putString("Emp Id", empEmpId);
        analyticsHelper.logEvent("Circle Activity :", bundle);
    }

    /**
     * All GestureDetectorAction perform
     */
    public void gestureDetectorAction() {
        final GestureDetector gestureDetectorTotalAlarm = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all sites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalSites);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                openSiteHeaderFragment();
            }

        });
        tvTotalSites.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorTotalAlarm.onTouchEvent(event);
                return true;
            }
        });
        final GestureDetector gestureDetectorTotalAlarmSites = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                filterString = "NOCOMM" + ",NSM" + ",INV" + ",BL1";
                getCircleData(filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all AlarmSites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalAlarmSites);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                openSiteHeaderFragment();
            }

        });
        tvTotalAlarmSites.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorTotalAlarmSites.onTouchEvent(event);
                return true;
            }
        });
        final GestureDetector gestureDetectorNonComm = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                filterString = "NOCOMM";
                getCircleData(filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all NonComm sites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalNonComm);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                openSiteHeaderFragment();
            }

        });
        tvTotalNonComm.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorNonComm.onTouchEvent(event);
                return true;
            }
        });

        final GestureDetector gestureDetectorINV = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                filterString = "INV";
                getCircleData(filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all InvAlarm sites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalInvAlarm);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                openSiteHeaderFragment();
            }

        });
        tvTotalInvAlarm.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorINV.onTouchEvent(event);
                return true;
            }
        });

        final GestureDetector gestureDetectorBL = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                filterString = "BL1";
                getCircleData(filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all LowBattery sites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalLowBattery);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                openSiteHeaderFragment();
            }

        });
        tvTotalLowBattery.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorBL.onTouchEvent(event);
                return true;
            }
        });

        final GestureDetector gestureDetectorNSM = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                filterString = "NSM";
                getCircleData(filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all NSMSites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvNSMSites);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                openSiteHeaderFragment();
            }
        });
        tvNSMSites.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorNSM.onTouchEvent(event);
                return true;
            }
        });
    }

    //openClusterScreen site header screen
    private void openSiteHeaderFragment() {
        SiteHeaderFragment siteHeaderFragment = new SiteHeaderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "Site Details");
        bundle.putBoolean("showMenuButton", false);
        bundle.putString("ALARM_TYPE", "NSM,NOCOMM,BL1,BL2,INV");
        bundle.putString("USER_ID", uid);
        bundle.putString("CIRCLE_ID", "0");
        bundle.putString("DISTRICT_ID", "0");
        getHostActivity().updateFragment(siteHeaderFragment, bundle);
    }

    public void getSharePrefData() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        uid = pref.getString("userId", "");
        userName = pref.getString("userName", "");
        applicationName = pref.getString("appName", "");
        firstTimeLoad = pref.getString("firstTimeLoad", "");
        callTracking = pref.getString("CallTrack", "0");
        locationTracking = pref.getString("rl", "0");
        empEmpId = pref.getString("EmailId", "0");
        empMobile = pref.getString("MobileNum", "0");
        empMenuId = pref.getString("MenuId", "0");
        fcmRegID = pref.getString("fcmRegID", "");
    }

    /*
     * Populate circle data to show list of circle
     */
    public void populateCircleDataToShowCircle() {
        String lastUpdatedDate = String.valueOf(System.currentTimeMillis());
        lastUpdatedDate = ASTUIUtil.formatDate(lastUpdatedDate);
        tvLastUpdated.setText("Last Updated: " + lastUpdatedDate);
        if (ASTUIUtil.isOnline(getContext())) {
            getCircleData("");
        } else {
            setAdaptor();
        }
    }

    /**
     * get Circle Data from Data base and set Adapater...
     */
    private void setAdaptor() {
        ATMDBHelper atmdbHelper = new ATMDBHelper(getContext());
        List<ServiceContentData> contentData = atmdbHelper.getAllcircleDetailsData();
        if (contentData != null && contentData.size() > 0) {
            for (ServiceContentData data : contentData) {
                if (data != null) {
                    setHeaderData(data.getHeader());
                    Data[] circleData = data.getData();
                    if (circleData != null) {
                        //circleViewResDataList = ASTGson.store().getList(circleData);
                        circleViewResDataList = new ArrayList<>(Arrays.asList(circleData));
                        if (circleViewResDataList == null && circleViewResDataList.size() == 0) {
                            circleViewGrid.setEmptyView(circleViewGrid);
                        } else {
                            circleGridAdapter = new CircleGridAdapter(getContext(), circleViewResDataList);
                            circleViewGrid.setEmptyView(circleViewGrid);
                            circleViewGrid.setAdapter(circleGridAdapter);
                            doSortingCircleList("cin", true);
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
            tvNSMSites.setText("NMS Sites: " + header.getNSMSies());
        }
    }

    /*
     *
     * Calling Web Service to get Circle Data
     */
    private void getCircleData(String strFilter) {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        if (ctid.equals("")) {
            ctid = "NA";
        }
        if (strFilter.equals("")) {
            strFilter = "NA";
        }
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.CIRCLE_DATA_NEW_URL;
        serviceURL += "&uid=" + uid + "&ctids=" + ctid + "&alarmtypes=" + strFilter + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getCircleData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveCircleData(result);
                } else {
                    ASTUIUtil.showToast("Circle Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     *
     * Parse and Save  Circle Service Data
     */
    public void parseandsaveCircleData(String result) {
        if (result != null) {
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {
                    ATMDBHelper atmdbHelper = new ATMDBHelper(getContext());
                    atmdbHelper.deleteAllRows("circleDetailsData");
                    atmdbHelper.insertCircleDetailsData(serviceData);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("LAST_CONNECTED", String.valueOf(System.currentTimeMillis()));
                    editor.commit();
                    setAdaptor();
                }
            }
        }
    }

    /*
     * Web Service to Call Customer List
     */
    private void getCustomerData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.CLIENT_LIST_DATA_URL;
        serviceURL += "&uid=" + uid + "&sid=" + sid + "&lat=" + lat + "&lon=" + lon;

        serviceCaller.CallCommanServiceMethod(serviceURL, "getCustomerData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveCustomerData(result);
                } else {
                    ASTUIUtil.showToast("Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     *
     * Parse and Save  parseandsaveSiteSearchData
     */
    public void parseandsaveCustomerData(String result) {
        if (result != null) {
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 1) {
                    ATMDBHelper atmdbHelper = new ATMDBHelper(getContext());
                    for (Data data : serviceData.getData()) {
                        atmdbHelper.upsertCustomerData(data);
                    }
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("LAST_CONNECTED", String.valueOf(System.currentTimeMillis()));
                    editor.commit();
                }
            }
        }
    }

    /*
     *
     * Calling Web Service to get Site Data
     */
    private void getSiteSearchData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.ALL_SITE_ID_URL;
        serviceURL += "&uid=" + uid + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getSiteSearchData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSiteSearchData(result);
                } else {
                    ASTUIUtil.showToast("Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     * Parse and Save  SiteSearch Data
     */
    public void parseandsaveSiteSearchData(String result) {
        if (result != null) {
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {

                    new AsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            Boolean flag = false;
                            atmdbHelper.deleteAllRows("SiteListData");
                            for (Data data : serviceData.getData()) {
                                atmdbHelper.upsertSiteListData(data);
                            }
                            flag = true;
                            return flag;
                        }

                        @Override
                        protected void onPostExecute(Boolean flag) {
                            super.onPostExecute(flag);
                            if (flag) {
                                setSiteNameListIntoSearch();
                            }
                        }
                    }.execute();
                }
            }
        }
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

    public void genrateSortList() {
        final CharSequence[] items = {
                "A-Z", "Z-A", "Alarm Count ↑", "Alarm Count ↓"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        doSortingCircleList("cin", true);//cin means circle name
                        break;
                    case 1:
                        doSortingCircleList("cin", false);//cin means circle name
                        break;
                    case 2:
                        doSortingCircleList("cd", false);// cd means TotalSites
                        break;
                    case 3:
                        doSortingCircleList("cd", true);// cd means TotalSites
                        break;
                    default:
                        break;
                }
            }
        });
        AlertDialog sortAlert = builder.create();
        sortAlert.show();
    }

    //shorting circle list and show in ui
    public void doSortingCircleList(String sortType, boolean isAsc) {
        if (circleViewResDataList != null && circleViewResDataList.size() > 0) {
            ASTUtil.sortArray(circleViewResDataList, sortType, isAsc);
            circleGridAdapter.notifyDataSetChanged();
        }
    }

    public void startLocationAlarmService() {
        if (locationTracking.equals("1")) {
            AlarmManager am = (AlarmManager) ApplicationHelper.application().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getHostActivity(), SendLocationToServerSideReciver.class);
            PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 2000, 1000 * 60 * 20, pi);
        }
    }

    /*
     * Calling Web Service to get LatestAppName
     */
    private void getLatestAppName() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.LATEST_APK_NAME_URL;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getLatestAppName", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveLatestAppNameData(result);
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     * Parse and Save  LatestAppNameData
     */
    public void parseandsaveLatestAppNameData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    //atmDatabase.deleteSiteSearchData();
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        apkName = jsonObject.optString("AndroidVersion").toString();
                        String lastUpdatedDate = jsonObject.optString("LastUpdatedDate").toString();
                        String[] newNumbers = apkName.split("ASTATMAppVer");
                        String[] serverApkVersionNo = newNumbers[1].split(".apk");
                        apkVersionNo = ASTUIUtil.getAppVersionName(getContext());
                        if (!apkVersionNo.equals(serverApkVersionNo[0])) {
                            int appresult = compareVersionNames(apkVersionNo, serverApkVersionNo[0]);
                            if (appresult == -1) {
                                ASTUIUtil.showToast("App Update Available");
                                //btnUpdateAvailable.setVisibility(View.VISIBLE);
                                showDialogForUpdateApp();
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }
    }

    //show upate app dialog
    public void showDialogForUpdateApp() {
        Dialog unsyncedDialog = new Dialog(getContext());
        unsyncedDialog.setContentView(R.layout.app_update_layout);
        unsyncedDialog.setTitle("Update Available");
        unsyncedDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        unsyncedDialog.setCanceledOnTouchOutside(false);
        unsyncedDialog.setCancelable(false);
        Button upgrade = (Button) unsyncedDialog.findViewById(R.id.upgrade);

        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsyncedDialog.dismiss();
                new DownloadAsyncTaskRunner().execute();
            }
        });
        unsyncedDialog.show();
    }

    public int compareVersionNames(String oldVersionName, String newVersionName) {
        int res = 0;

        String[] oldNumbers = oldVersionName.split("\\.");
        String[] newNumbers = newVersionName.split("\\.");
        int maxIndex = Math.min(oldNumbers.length, newNumbers.length);
        for (int i = 0; i < maxIndex; i++) {
            int oldVersionPart = Integer.valueOf(oldNumbers[i]);
            int newVersionPart = Integer.valueOf(newNumbers[i]);

            if (oldVersionPart < newVersionPart) {
                res = -1;
                break;
            } else if (oldVersionPart > newVersionPart) {
                res = 1;
                break;
            }
        }

        // If versions are the same so far, but they have different length...
        if (res == 0 && oldNumbers.length != newNumbers.length) {
            res = (oldNumbers.length > newNumbers.length) ? 1 : -1;
        }

        return res;
    }

    private class DownloadAsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            ASTUIUtil.downloadapk(getContext(), apkName);
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        protected void onProgressUpdate(String... text) {
        }
    }
    /*
     *
     * Calling Web Service to Get State, District and Tehsil
     */

    private void getStateData() {
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.GET_STATE_MASTER;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getStateData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsavegetStateData(result);
                }
            }
        });
    }

    /*
     * Parse and Save  StateData in DB
     */
    public void parseandsavegetStateData(String result) {
        if (result != null) {
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {
                    ATMDBHelper atmdbHelper = new ATMDBHelper(getContext());
                    if (serviceData.getData() != null) {
                        for (Data data : serviceData.getData()) {
                            atmdbHelper.upsertStateDetailData(data);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvSort) {
            genrateSortList();
        } else if (view.getId() == R.id.tvFilter) {
            getFIlterData();
        } else if (view.getId() == R.id.imgRefresh) {
            refresh = 1;
            filterString = "NA";
            ctid = "NA";
            populateCircleDataToShowCircle();
        } else if (view.getId() == R.id.btnUpdateAvailable) {
            new DownloadAsyncTaskRunner().execute();
        } else if (view.getId() == R.id.tvClearFilter) {
            ctid = "NA";
            filterString = "NA";
            getCircleData(filterString);
            tvClearFilter.setVisibility(View.GONE);
        }
    }

    /*
     * get Filter Search Data
     */
    public void getFIlterData() {

    }

    /*
     * Calling Web Service to get Equipment List Data
     */
    public void getEquipListData(Context context) {
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL_API + Contants.EQUIPMENT_LIST_URL;
        serviceCaller.callgetEquipListDataServices(serviceURL, new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                } else {

                }
            }
        });
    }

    //sync all offline data
    private void syncOfflinData() {
        if (ASTUIUtil.isOnline(getContext())) {
            ArrayList<ComplaintDataModel> complaintArrayList = atmdbHelper.getComplaintData();
            if (complaintArrayList != null && complaintArrayList.size() > 0) {
                for (ComplaintDataModel dataModel : complaintArrayList) {
                    saveComplainData(dataModel);
                }
            }
            ArrayList<ContentLocalData> contentLocalData = atmdbHelper.getAllActivtyFormData();
            if (contentLocalData != null && contentLocalData.size() > 0) {
                for (int i = 0; i < contentLocalData.size(); i++) {
                    String activityFormStr = contentLocalData.get(i).getActivityFormData();
                    if (activityFormStr != null) {
                        ActivitySheetModel activityData = new Gson().fromJson(activityFormStr, new TypeToken<ActivitySheetModel>() {
                        }.getType());
                        activityFormDataServiceCall(activityData);
                    }
                }
            }
            ArrayList<FillSiteActivityModel> siteList = atmdbHelper.getAllAddSiteAddress();
            for (FillSiteActivityModel siteActivityModel : siteList) {
                saveFillSiteAddress(siteActivityModel);
            }
            ArrayList<ContentLocalData> qrEquipementList = atmdbHelper.getAllQREquipmentData();
            for (ContentLocalData localData : qrEquipementList) {
                saveEquipmentDataService(localData);
            }
        }
    }

    //send offline save complain data to server
    private void saveComplainData(ComplaintDataModel dataModel) {
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
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

    //---------------------Calling Web Service to save activity form data into server--------------------------
    public void activityFormDataServiceCall(ActivitySheetModel sheetModel) {

        String serviceURL = "";
        serviceURL = Contants.BASE_URL + Contants.ADD_NEW_ACTIVITY_NEW_URL;
        serviceURL += "&uid=" + sheetModel.getUserId() + "&sid=" + sheetModel.getSiteId() + "&tid=" + sheetModel.getTaskId() + "&aid=" + sheetModel.getActivityId()
                + "&neid=" + sheetModel.getNocEnggId() + "&st=" + sheetModel.getStatusId() + "&reason=" + sheetModel.getReasonId() + "&ztype=" + sheetModel.getZoneId()
                + "&mid=" + sheetModel.getMaterialStatus() + "&remarks=" + sheetModel.getRemarks() + "&isplanned=" + sheetModel.getIsPlanned() + "&ispm=" + sheetModel.getIsPm() + "&earthingvoltage=" + sheetModel.getEarthVolt() +
                "&batterytopup=" + sheetModel.getBattTopup() + "&batterycells=" + sheetModel.getBattCells() + "&charger=" + sheetModel.getCharger() + "&inverter=" + sheetModel.getInverter() +
                "&ebconnection=" + sheetModel.getEbConn() + "&connection=" + sheetModel.getConn() + "&solar=" + sheetModel.getSolar() + "&signoff=" + sheetModel.getSignOff() +
                "&sgc1=" + sheetModel.getCell1() + "&sgc2=" + sheetModel.getCell2() + "&sgc3=" + sheetModel.getCell3() + "&sgc4=" + sheetModel.getCell4() + "&sgc5=" + sheetModel.getCell5() +
                "&sgc6=" + sheetModel.getCell6() + "&sgc7=" + sheetModel.getCell7() + "&sgc8=" + sheetModel.getCell8() +
                "&SolarStructure=" + sheetModel.getSolarStructureAndPanelTightness() + "&BattTermnialGreas=" + sheetModel.getBatteryTerminalGreasing() + "&Photo=" + sheetModel.getPhotos() + "&ModemConnection=" + sheetModel.getModemConn() + "&CommStatu=" + "0" + "&SpareReq=" + sheetModel.getSpareRequirement() +
                "&plandate=" + sheetModel.getPlannedDate() +
                "&planid=" + sheetModel.getPlanId() + "&da=" + sheetModel.getOtherExpenses() + "&androidtime=" + sheetModel.getSubmitDateTime() + "&numberOfDays=" + sheetModel.getDaysTaken() + "&lat=" + sheetModel.getLatitude() + "&lon=" + sheetModel.getLongitude();
        serviceURL = serviceURL.replace(" ", "^^");

        Log.d(Contants.LOG_TAG, "activityFormDataServiceCall serviceURL***************" + serviceURL);
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        serviceCaller.CallCommanServiceMethod(serviceURL, "activityFormDataServiceCall", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseActivityFormData(result, sheetModel.getPlanId());
                } else {
                    ASTUIUtil.showToast("Server Side error");
                }
            }
        });
    }

    //parse activity form data response
    private void parseActivityFormData(String response, String savePlanId) {
        if (response != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(response);
                String jsonStatus = jsonRootObject.optString("status").toString();

                if (jsonStatus.equals("2")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    atmdbHelper.deleteActivtyFormDataByPlanId(savePlanId);
                    //ASTUIUtil.showToast("Activity Form Data Saved on Server");
                } else if (jsonStatus.equals("0")) {

                }
                //connectingToServer = 0;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                // connectingToServer = 0;
            }
        }
    }

    private void saveFillSiteAddress(FillSiteActivityModel fillSiteActivityModel) {

        String siteId = fillSiteActivityModel.getSiteId();
        //String customerSiteId = fillSiteActivityModel.getCustomerSiteId();
        //String siteName = fillSiteActivityModel.getSiteName();
        String branchName = fillSiteActivityModel.getBranchName();
        String branchCode = fillSiteActivityModel.getBranchCode();
        String onOffSite = fillSiteActivityModel.getOnOffSite();
        String address1 = fillSiteActivityModel.getAddress();
        String city = fillSiteActivityModel.getCity();
        String circleId = fillSiteActivityModel.getCircleId();
        String districtId = fillSiteActivityModel.getDistrictId();
        String tehsilId = fillSiteActivityModel.getTehsilId();
        String pincode = fillSiteActivityModel.getPincode();
        String lat = fillSiteActivityModel.getLat();
        String lon = fillSiteActivityModel.getLon();
        String startTime = fillSiteActivityModel.getFunctionalFromTime();
        String endTime = fillSiteActivityModel.getFunctionalToTime();
        String siteAddressId = fillSiteActivityModel.getSiteAddressId();

        if (lat == null) {
            lat = "0.000000";
        } else if (lon == null) {
            lon = "0.000000";
        }

        if (siteId.equals("") || siteId.equals(null)) {
            siteId = "000000";
        }
       /* if (customerSiteId.equals("") || customerSiteId.equals(null)) {
            customerSiteId = "000000";
        }
        if (siteName.equals("") || siteName.equals(null)) {
            siteName = "NA";
        }*/
        if (branchName.equals("") || branchName.equals(null)) {
            branchName = "NA";
        }
        if (branchCode.equals("") || branchCode.equals(null)) {
            branchCode = "000000";
        }
        if (onOffSite.equals("") || onOffSite.equals(null)) {
            onOffSite = "NA";
        }
        if (address1.equals("") || address1.equals(null)) {
            address1 = "NA";
        }
        if (city.equals("") || city.equals(null)) {
            city = "NA";
        }
        if (circleId.equals("0") || circleId.equals(null)) {
            circleId = "000000";
        }
        if (districtId.equals("0") || districtId.equals(null)) {
            districtId = "000000";
        }
        if (tehsilId.equals("0") || tehsilId.equals(null)) {
            tehsilId = "000000";
        }
        if (pincode.equals("") || pincode.equals(null)) {
            pincode = "000000";
        }
        if (lat.equals("") || lat.equals(null)) {
            lat = "0";
        }
        if (lon.equals("") || lon.equals(null)) {
            lon = "0";
        }

        address1.replace("", "^^");

        String serviceURL = Contants.BASE_URL + Contants.ADD_SITE_ADDRESS;

        serviceURL += "&sid=" + siteId + "&branchname=" + branchName + "&branchcode=" + branchCode + "&city=" + city +
                "&pincode=" + pincode + "&onoffsite=" + onOffSite + "&address=" + address1 + "&circleid=" + circleId +
                "&districtid=" + districtId + "&tehsilid=" + tehsilId + "&lat=" + lat + "&lon=" + lon +
                "&fnhrfromtime=" + startTime + "&fnhrtotime=" + endTime;
        serviceURL = serviceURL.replace(" ", "^^");


        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        serviceCaller.CallCommanServiceMethod(serviceURL, "saveFillSiteAddress", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseAndSaveFillSiteAddress(result);
                }
            }
        });
    }

    private void parseAndSaveFillSiteAddress(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();

                if (jsonStatus.equals("2")) {
                    Toast.makeText(getContext(), "Site Address Data Saved on Server.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //save equipement data into server
    public void saveEquipmentDataService(ContentLocalData localData) {
        JSONObject mainObject = null;
        try {
            mainObject = new JSONObject(localData.getQREquipmentData());
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            String serviceURL = Contants.BASE_URL_API + Contants.InstallEquipment;
            serviceCaller.CallCommanServiceMethod(serviceURL, mainObject, "saveEquipmentDataService", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        JSONObject jsonRootObject = null;
                        try {
                            jsonRootObject = new JSONObject(result);
                            String jsonStatus = jsonRootObject.optString("Status").toString();
                            if (jsonStatus.equals("2")) {
                                atmdbHelper.deleteQREquipmentDataByPlanId(localData.getPlanId());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
