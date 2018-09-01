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

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.SyncSiteAddressDataWithServer;
import com.atm.ast.astatm.adapter.CircleGridAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.firebase.FirebaseInstanceIDService;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.CircleDisplayDataModel;
import com.atm.ast.astatm.model.CustomerListDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Header;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.reciver.SendLocationToServerSideReciver;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.ASTUtil;
import com.atm.ast.astatm.utils.FilterPopupCircle;
import com.atm.ast.astatm.utils.LogAnalyticsHelper;
import com.atm.ast.astatm.utils.TooltipWindow;

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
    int batteryLowFilterstatus = 0, noCommLowFilterstatus = 0, INVFilterstatus = 0;
    int refresh = 0;
    public static String filterString = "NA";
    AtmDatabase atmDatabase;
    // String customerList;
    //String[][] arrCustomerList;
    // CharSequence[] items = null;
    AlertDialog alert;
    public static Boolean[] arrSelectedFilterOne;
    public static Boolean[] arrSelectedFilterTwo;
    public List<Data> circleViewResDataList;
    CircleGridAdapter circleGridAdapter;
    CircleDisplayDataModel circleDataModel;
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
    // ArrayList<SiteDisplayDataModel> siteDetailArrayList;
    boolean[] selectedCustomerFilter = null;
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
        Intent intentSiteAddressService = new Intent(getContext(), SyncSiteAddressDataWithServer.class);
        getContext().startService(intentSiteAddressService);
        getSharePrefData();
        populateCircleDataToShowCircle();
        atmdbHelper = new ATMDBHelper(getContext());
        ArrayList<Data> dataArrayList = atmdbHelper.getAllStateDetailListData();
        if (dataArrayList.size() <= 0) {
            getStateData();
        }
        //check latest app available or not
        if (ASTUIUtil.isOnline(getContext())) {
            getLatestAppName();
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
       /* if (atmDatabase.getCircleCount("customer_data", "", "") > 0) {
            ArrayList<CustomerListDataModel> arrCustomerDBList = new ArrayList<>();
            arrCustomerDBList = atmDatabase.getCustomerData();
            //  items = new CharSequence[arrCustomerDBList.size() + 1];
            // arrCustomerList = new String[2][arrCustomerDBList.size() + 1];
            // arrCustomerList[0][0] = "All";
            // arrCustomerList[1][0] = "0";
            //  items[0] = "All";
              *//*  for (int i = 0; i < arrCustomerDBList.size(); i++) {
                    if (i == 0) {
                        customerList += arrCustomerDBList.get(i).getCustomerName();
                    } else {
                        customerList += "," + arrCustomerDBList.get(i).getCustomerName();
                    }
                    //arrCustomerList[0][i + 1] = arrCustomerDBList.get(i).getCustomerName();
                  //  arrCustomerList[1][i + 1] = arrCustomerDBList.get(i).getCustomerId();
                   // items[i + 1] = arrCustomerDBList.get(i).getCustomerName();
                }*//*
        } else {
            getCustomerData();
        }*/
        //geting customer list
        if (ASTUIUtil.isOnline(getContext())) {
            getCustomerData();
        }
       /* int siteSearchCount = atmDatabase.getCircleCount("site_search_details", "", "Survey");
        if (siteSearchCount > 0) {
            siteDetailArrayList = atmDatabase.getFilteredData("site_search_name", "", "Survey");
            arrSiteName = new String[siteDetailArrayList.size()];
            arrSiteId = new String[siteDetailArrayList.size()];
            for (int i = 0; i < siteDetailArrayList.size(); i++) {
                arrSiteName[i] = siteDetailArrayList.get(i).getSiteName();
                arrSiteId[i] = siteDetailArrayList.get(i).getSiteId();
            }
            setSiteNameListIntoSearch();
        } else {
            getSiteSearchData();
        }*/
        //geting all site list
        if (ASTUIUtil.isOnline(getContext())) {
            getSiteSearchData();
        } else {
            setSiteNameListIntoSearch();
        }

     /*   ArrayList<EquipListDataModel> arrayEquipListEnggData = new ArrayList<>();
        // need to refactor in new db
        arrayEquipListEnggData = atmDatabase.getEquipDataData("1381");
        if (arrayEquipListEnggData.size() <=0) {
            getEquipListData(getContext());
        }*/
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("firstTimeLoad", "1");
        editor.commit();
        logAnalytics();
        startLocationAlarmService();
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
              /*  // JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArrayHeader = jsonRootObject.optJSONArray("header");
                    for (int i = 0; i < jsonArrayHeader.length(); i++) {
                        JSONObject jsonObject = jsonArrayHeader.getJSONObject(i);
                        totalAlarmSites = jsonObject.optString("TotalSites").toString();
                        alarmSites = jsonObject.optString("AlarmSites").toString();
                        nonComSites = jsonObject.optString("NoncomSites").toString();
                        invSites = jsonObject.optString("INVSites").toString();
                        lowBattertSites = jsonObject.optString("LowBatterySies").toString();
                        nmsSites = jsonObject.optString("NSMSies").toString();
                        tvTotalSites.setText("Total Sites: " + totalAlarmSites);
                        tvTotalAlarmSites.setText("Total Alarm Sites: " + alarmSites);
                        tvTotalNonComm.setText("Total Non Comm: " + nonComSites);
                        tvTotalInvAlarm.setText("Total INV Alarm: " + invSites);
                        tvTotalLowBattery.setText("Total Low Battery: " + lowBattertSites);
                        tvNSMSites.setText("NMS Sites: " + nmsSites);
                    }
                } else {
                    ASTUIUtil.showToast("Data not available.");
                }
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cin = jsonObject.optString("cin").toString();
                        String cd = jsonObject.optString("cd").toString();
                        String civ = jsonObject.optString("civ").toString();
                        String co = jsonObject.optString("co").toString();
                        String ciid = jsonObject.optString("ciid").toString();
                        String chp = jsonObject.optString("chp").toString();
                        circleDataModel = new CircleDisplayDataModel();
                        circleDataModel.setCircleName(cin);
                        circleDataModel.setTotalSites(cd);
                        circleDataModel.setTotalAlarmSites(civ.trim());
                        circleDataModel.setColorCode(co);
                        circleDataModel.setCircleId(ciid);
                        circleDataModel.setCircleHeadContact(chp);
                        circleDataModel.setHeaderAlarmSites(alarmSites);
                        circleDataModel.setHeaderTotalSites(totalAlarmSites);
                        circleDataModel.setHeaderInvAlarm(invSites);
                        circleDataModel.setHeaderLowBattery(lowBattertSites);
                        circleDataModel.setHeaderNmsSites(nmsSites);
                        circleDataModel.setHeaderNonComm(nonComSites);
                        circleViewResDataList.add(circleDataModel);
                    }
                    if (ctid.equalsIgnoreCase("NA")) {
                        atmDatabase.deleteAllRows("circle");
                        atmDatabase.addCircleData(circleViewResDataList);
                    }
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("LAST_CONNECTED", String.valueOf(System.currentTimeMillis()));
                    editor.commit();
                    setAdaptor(circleViewResDataList);
                }*/
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
            /*try {
                ArrayList<CustomerListDataModel> arrayListCustomerData = new ArrayList<>();
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("1")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    //  arrCustomerList = new String[2][jsonArray.length() + 1];
                    //  items = new CharSequence[jsonArray.length() + 1];
                    // arrCustomerList[0][0] = "All";
                    // arrCustomerList[1][0] = "0";
                    // items[0] = "All";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        CustomerListDataModel customerListDataModel = new CustomerListDataModel();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String customerName = jsonObject.optString("ctn").toString();
                        String customerId = jsonObject.optString("ct").toString();
                        customerListDataModel.setCustomerName(customerName);
                        customerListDataModel.setCustomerId(customerId);
                        arrayListCustomerData.add(customerListDataModel);

                       *//* if (i == 0) {
                            customerList += customerName;
                        } else {
                            customerList += "," + customerName;
                        }*//*

                        // arrCustomerList[0][i + 1] = customerName;
                        // arrCustomerList[1][i + 1] = customerId;

                        // items[i + 1] = customerName;
                    }

                    atmDatabase.addCustomerData(arrayListCustomerData);

                }
                // progressbar.dismiss();

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }*/
        }
    }
  /*  //------------------Generate Customer List-------------------------------------------
    public void genrateCustomerList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Customer");
        final ArrayList<CustomerListDataModel> arrCustomerDataFiltered = new ArrayList<CustomerListDataModel>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_multichoice);
        ArrayList<CustomerListDataModel> arrCustomerData = new ArrayList<>();
        if (atmDatabase.getCircleCount("customer_data", "", "") > 0) {
            selectedCustomerFilter = new boolean[atmDatabase.getCircleCount("customer_data", "", "") + 1];
            arrCustomerData = atmDatabase.getCustomerData();
        } else {
            selectedCustomerFilter = new boolean[0];
        }
        final String[] arrFilteredData = new String[arrCustomerData.size()];
        builder.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                alert.cancel();
            }
        });

        builder.setMultiChoiceItems(items, selectedCustomerFilter, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialogInterface, int item, boolean b) {
                //Log.d("Myactivity", String.format("%s: %s", items[item], b));
                selectedCustomerFilter[item] = true;
                if (b == true) {
                    arrFilteredData[item] = "true";
                } else {
                    arrFilteredData[item] = "false";
                }
            }
        });

        alert = builder.create();
        alert.show();
    }*/

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
          /*  try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                siteDetailArrayList = new ArrayList<>();
                atmDatabase.deleteAllRows("site_search_details");
                //ArrayList<SiteDisplayDataModel> siteDetailArrayList = new ArrayList<>(SiteDisplayDataModel);
                //circleViewResDataList.clear();
                if (jsonStatus.equals("2")) {
                    //atmDatabase.deleteSiteSearchData();
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    arrSiteName = new String[jsonArray.length()];
                    arrSiteId = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String siteId = jsonObject.optString("SiteId").toString();
                        String customerSiteId = jsonObject.optString("sid").toString();
                        String siteName = jsonObject.optString("SiteName").toString();
                        String siteLat = jsonObject.optString("Lat").toString();
                        String siteLong = jsonObject.optString("Lon").toString();
                        String distanceFromBaseLocation = jsonObject.optString("BaseDistance").toString();
                        String clientName = jsonObject.optString("Client").toString();
                        String clientId = jsonObject.optString("ClientId").toString();
                        SiteDisplayDataModel siteDisplayDataModel = new SiteDisplayDataModel();
                        siteDisplayDataModel.setSiteId(customerSiteId);
                        siteDisplayDataModel.setSiteName(siteName);
                        siteDisplayDataModel.setSiteNumId(siteId);
                        siteDisplayDataModel.setSiteLat(siteLat);
                        siteDisplayDataModel.setSiteLong(siteLong);
                        siteDisplayDataModel.setBaseDistance(distanceFromBaseLocation);
                        siteDisplayDataModel.setClientName(clientName);
                        siteDisplayDataModel.setCircleId(clientId);
                        siteDetailArrayList.add(siteDisplayDataModel);
                        arrSiteName[i] = String.valueOf(siteName);
                        arrSiteId[i] = String.valueOf(customerSiteId);
                    }
                  *//*  if (siteDetailArrayList != null && siteDetailArrayList.size() > 0) {
                        SiteDetailAsyncTask siteDetailAsyncTask = new SiteDetailAsyncTask(siteDetailArrayList);
                        siteDetailAsyncTask.execute();
                    }*//*

                } else if (jsonStatus.equals("0")) {
                    ASTUIUtil.showToast("Site Data is not available!");
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }*/
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
                   /* int arraylistPosition = 0;
                    for (int i = 0; i < arrSiteId.length; i++) {
                        if (arrSiteId[i].equals(searchString)) {
                            arraylistPosition = i;
                        }
                        if (arrSiteName[i].equals(searchString)) {
                            arraylistPosition = i;
                        }
                    }*/
                    /*siteDisplayDataModel.setSiteId(customerSiteId);
                    siteDisplayDataModel.setSiteNumId(siteId);*/
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

   /* public void genrateFilterList() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setIcon(R.drawable.ic_launcher);
        //builderSingle.setTitle("Select One Name:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
        //arrayAdapter.add("Remove Filter");
        arrayAdapter.add("Alarm Type");
        arrayAdapter.add("Customer");
        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        if (position == 0) {
                            genrateAlarmFilterList();
                            //filterAlert.dismiss();
                        } else if (position == 1) {
                            genrateCustomerList();
                        }

                    }
                });
        builderSingle.show();
    }*/

  /*  public void genrateAlarmFilterList() {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setIcon(R.drawable.ic_launcher);
        //builderSingle.setTitle("Select One Name:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_multichoice);
        final CharSequence[] items = {"Battery Low", "No Comm", "INV"};
        final boolean[] selected = {false, false, false};
        filterString = "NA";
        builderSingle.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //siteDetailArrayList
                ArrayList<SiteDisplayDataModel> siteDetailArrayListTemp = new ArrayList<SiteDisplayDataModel>();
                if (batteryLowFilterstatus == 1) {
                    if (filterString.equals("")) {
                        filterString = "BL1";
                    }
                }
                if (noCommLowFilterstatus == 1) {
                    if (filterString.equals("")) {
                        filterString = "NSM";
                    } else {
                        filterString = ",NSM";
                    }
                }
                if (INVFilterstatus == 1) {
                    if (filterString.equals("")) {
                        filterString = "INV";
                    } else {
                        filterString = ",INV";
                    }
                }

                getCircleData(filterString);
            }
        });

        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });


        builderSingle.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialogInterface, int item, boolean b) {
                Log.d("Myactivity", String.format("%s: %s", items[item], b));
                switch (item) {
                    case 0:
                        batteryLowFilterstatus = 1;
                        break;
                    case 1:
                        noCommLowFilterstatus = 1;
                        break;
                    case 2:
                        INVFilterstatus = 1;
                        break;
                    default:
                        break;
                }
            }
        });

        builderSingle.show();
    }*/

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

   /* class SiteDetailAsyncTask extends AsyncTask<Void, Void, Void> {
        private final ArrayList<SiteDisplayDataModel> siteDetailArrayList;
        private Exception exception;
        private ProgressDialog progressDialog;

        public SiteDetailAsyncTask(ArrayList<SiteDisplayDataModel> siteDetailArrayList) {
            this.siteDetailArrayList = siteDetailArrayList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            //  progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {site_search_details
                 atmDatabase.addSearchSiteData(siteDetailArrayList);
                //   setSiteNameListIntoSearch();
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            if (exception == null) {
                onSuccessAddSearchSiteData();
            } else {
                onFailAddSearchSiteData(exception);
            }
        }
    }

    private void onSuccessAddSearchSiteData() {
        ASTUIUtil.showToast("Success");
    }

    private void onFailAddSearchSiteData(Exception exception) {
        ASTUIUtil.showToast("Fail");
    }*/


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
        if (view.getId() == R.id.etSearch) {

        } else if (view.getId() == R.id.tvSort) {
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
      /*  ArrayList<CustomerListDataModel> arrCustomerData = new ArrayList<>();
        if (atmDatabase.getCircleCount("customer_data", "", "") > 0) {
            selectedCustomerFilter = new boolean[atmDatabase.getCircleCount("customer_data", "", "") + 1];
            arrCustomerData = atmDatabase.getCustomerData();
        } else {
            selectedCustomerFilter = new boolean[0];
        }
        final String[] arrFilteredData = new String[arrCustomerData.size()];
        final String[] arrFilteredIdData = new String[arrCustomerData.size()];
        for (int i = 0; i < arrCustomerData.size(); i++) {
            arrFilteredData[i] = arrCustomerData.get(i).getCustomerName();
            arrFilteredIdData[i] = arrCustomerData.get(i).getCustomerId();
        }
        String[] arrParentData = new String[2];
        String[] arrAlarmTypes = {"Battery Low", "No Comm", "INV", "NSM"};
        arrParentData[0] = "Alarm Type";
        arrParentData[1] = "Customer";
        arrSelectedFilterOne = new Boolean[arrAlarmTypes.length];
        for (int i = 0; i < arrAlarmTypes.length; i++) {
            arrSelectedFilterOne[i] = false;
        }
        arrSelectedFilterTwo = new Boolean[arrFilteredData.length];
        for (int i = 0; i < arrFilteredData.length; i++) {
            arrSelectedFilterTwo[i] = false;
        }

        FilterPopupCircle filterPopup = new FilterPopupCircle();
        filterPopup.getFilterPopup(popup, getContext(), arrParentData, arrAlarmTypes, arrFilteredData, arrFilteredIdData, "circle");*/
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
}
