package com.atm.ast.astatm.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.SyncFormDataWithServerIntentService;
import com.atm.ast.astatm.adapter.CircleParentFilterAdapter;
import com.atm.ast.astatm.adapter.PlannedActivityListAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.ActivityListDataModel;
import com.atm.ast.astatm.model.ActivityListSheetDataModel;
import com.atm.ast.astatm.model.CircleDisplayDataModel;
import com.atm.ast.astatm.model.PlannedActivityListModel;
import com.atm.ast.astatm.model.ReasonListDataModel;
import com.atm.ast.astatm.model.TaskListDataModel;
import com.atm.ast.astatm.model.newmodel.Activity;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Header;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FilterPopupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PlannedActivityListFragment extends MainFragment {
    ListView lvActivityList;
    ImageView btnAddNewActivity;
    // public static int[] checkListActivity;
    //public static int[] checkListCircle;
    //String[] arrActivities;
    //String[] arrCircles;
    String first = "";
    //public String[] arrFeName;
    AtmDatabase atmDatabase;
    //public static Boolean[] arrSelectedFilterOne;
    //public static Boolean[] arrSelectedFilterTwo;
    RadioButton rbNoFilter, rbActivity, rbCircle;
    TextView tvFilter;
    TextView tvPlannedExecuted, tvOnTheWay, tvReachedSite, tvLeftSite, tvUnknown, tvCircleName, tvAttendance;
    View viewVertical5;
    AutoCompleteTextView etSearch;
    ImageView imgRefresh;
    String filterType;
    //ArrayList<ActivityListDataModel> arrActivityList;
    //TaskListDataModel taskListDataModel;
    //  ActivityListSheetDataModel activityListSheetDataModel;
    // ReasonListDataModel reasonListDataModel;
    //ArrayList<TaskListDataModel> taskList;
    // ArrayList<ReasonListDataModel> reasonList;
    //ArrayList<ActivityListSheetDataModel> activityDetailArrayList;
    // ArrayList<ActivityListSheetDataModel> allActivityDetailArrayList;
    // ArrayList<ActivityListDataModel> arrActivityListFilter;
    //public static List<CircleDisplayDataModel> circleViewResDataList = new ArrayList<CircleDisplayDataModel>();
    CharSequence[] items = null;
    AlertDialog alert;
    //Shared Prefrences
    SharedPreferences pref;
    String userId, userRole, userAccess, r1;
    String userName = "";
    //-----------------
    String lat = "23.23";
    String lon = "23.23";
    PopupWindow popup = null;
    PlannedActivityListAdapter plannedActivtyListAdapter;
    ASTUIUtil commonFunctions;
    boolean activityRefreshFlag = false;
    TextView appversion_number;
    private ATMDBHelper atmdbHelper;

    ArrayList<Data> planActivityList;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_planned_activity_list;
    }

    @Override
    protected void getArgs() {
        activityRefreshFlag = this.getArguments().getBoolean("ActivityRefreshFlag", false);
    }

    @Override
    protected void loadView() {
        rbCircle = findViewById(R.id.rbCircle);
        rbNoFilter = findViewById(R.id.rbNoFilter);
        rbActivity = findViewById(R.id.rbActivity);
        tvFilter = findViewById(R.id.tvFilter);
        tvPlannedExecuted = findViewById(R.id.tvPlannedExecuted);
        tvOnTheWay = findViewById(R.id.tvOnTheWay);
        tvReachedSite = findViewById(R.id.tvReachedSite);
        tvLeftSite = findViewById(R.id.tvLeftSite);
        tvUnknown = findViewById(R.id.tvUnknown);
        tvCircleName = findViewById(R.id.tvCircleName);
        tvAttendance = findViewById(R.id.tvAttendance);
        viewVertical5 = findViewById(R.id.viewVertical5);
        etSearch = findViewById(R.id.etSearch);
        imgRefresh = findViewById(R.id.imgRefresh);
        appversion_number = findViewById(R.id.tvVersionNumber);
        lvActivityList = findViewById(R.id.lvActivityList);
        btnAddNewActivity = findViewById(R.id.btnAddNewActivity);
    }

    @Override
    protected void setClickListeners() {
        btnAddNewActivity.setOnClickListener(this);
        rbActivity.setOnClickListener(this);
        rbNoFilter.setOnClickListener(this);
        rbCircle.setOnClickListener(this);
        tvFilter.setOnClickListener(this);
        imgRefresh.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    /**
     * get Save Shared Pref Data
     */
    public void getSharedPrefData() {
        //Shared Prefrences
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        userId = pref.getString("userId", "");
        userName = pref.getString("userName", "");
        userAccess = pref.getString("userAccess", "");
        userRole = pref.getString("roleId", "");
    }

    @Override
    protected void dataToView() {
        atmdbHelper = new ATMDBHelper(getContext());
        popup = new PopupWindow(getContext());
        atmDatabase = new AtmDatabase(getContext());
        //  circleViewResDataList = atmDatabase.getAllCircleData("NAME");
        commonFunctions = new ASTUIUtil();
        //reasonList = new ArrayList<>();
        //  taskList = new ArrayList<>();
        // activityDetailArrayList = new ArrayList<>();
        //allActivityDetailArrayList = new ArrayList<>();
        // arrActivityListFilter = new ArrayList<ActivityListDataModel>();
        //ArrayList<PlannedActivityListModel> arrPendingActivityData;
        getSharedPrefData();
        getActivityDropdownList();
        //--------------Start Background Service--------------------------------
        Intent intentService = new Intent(getContext(), SyncFormDataWithServerIntentService.class);
        getContext().startService(intentService);
        String appversionName = ASTUIUtil.getAppVersionName(getContext());
        if (appversionName != null) {
            appversion_number.setText(appversionName);
        }
        if (activityRefreshFlag) {
            getActivityList();
        }
        getActivityList();
        setAdaptor();
        setHeaderDetail();
        lvActivityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (planActivityList != null && planActivityList.size() > 0) {
                    if (userAccess.equals("fe")) {
                       /* ActivitySheetFragment activitySheetFragment = new ActivitySheetFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("headerTxt", "Activity Form");
                        bundle.putBoolean("showMenuButton", false);
                        bundle.putString("SITE_ID", planActivityList.get(position).getSiteId() + "");
                        bundle.putString("SITE_NAME", planActivityList.get(position).getSiteName());
                        bundle.putString("SITE_CUSTOMER_SITE_ID", planActivityList.get(position).getCustomerSiteId());
                      *//*  if (first.equals("First")) {
                            bundle.putString("SITE_CUSTOMER_SITE_ID", arrActivityList.get(position).getCustomerSiteId());
                        } else {
                            bundle.putString("SITE_CUSTOMER_SITE_ID", arrActivityList.get(position).getSiteId());
                        }*//*
                        bundle.putString("ACTIVITY_ID", planActivityList.get(position).getActivityId() + "");
                        bundle.putString("PLAN_ID", planActivityList.get(position).getPlanId() + "");
                        bundle.putString("TASK_ID", planActivityList.get(position).getTaskId() + "");
                        bundle.putString("COMPLAINT_MESSAGE", planActivityList.get(position).getComplaintMessage());
                        getHostActivity().updateFragment(activitySheetFragment, bundle);*/

                        openActivitySheetFragment(planActivityList.get(position).getSiteId(), planActivityList.get(position).getSiteName(), planActivityList.get(position).getCustomerSiteId(), planActivityList.get(position).getActivityId(), planActivityList.get(position).getPlanId(), planActivityList.get(position).getTaskId(), planActivityList.get(position).getComplaintMessage(), false);
                    }
                }
            }
        });
           /* arrPendingActivityData = new ArrayList<>();
            arrPendingActivityData = atmDatabase.getPendingActivityData();
            String lastUpdatedDate = new SimpleDateFormat("MMM d, yyyy").format(atmDatabase.getPendingActivityLastUpdatedDate());
            String currentDate = new SimpleDateFormat("MMM d, yyyy").format(System.currentTimeMillis());
            if (arrPendingActivityData.size() <= 0 || !lastUpdatedDate.equals(currentDate)) {
                getActivityList();
            } else {
                arrActivityList = new ArrayList<>();
                tvPlannedExecuted.setText("Planned/Executed: " + arrPendingActivityData.get(0).getExecuted());
                tvOnTheWay.setText("On The Way: " + arrPendingActivityData.get(0).getOnTheWay());
                tvReachedSite.setText("Reached Site: " + arrPendingActivityData.get(0).getReachedSite());
                tvLeftSite.setText("Left Site: " + arrPendingActivityData.get(0).getLeftSite());
                tvUnknown.setText("Unknown: " + arrPendingActivityData.get(0).getUnknown());
                //tvCircleName.setText("Circle Name: " + );
                tvAttendance.setText("Attendance/Leave: " + arrPendingActivityData.get(0).getAttendance() + "/" + arrPendingActivityData.get(0).getLeave());
                tvCircleName.setVisibility(View.GONE);
                for (int i = 0; i < arrPendingActivityData.size(); i++) {
                    ActivityListDataModel activityListDataModel = new ActivityListDataModel();
                    activityListDataModel.setPlanID(arrPendingActivityData.get(i).getPlanId());
                    activityListDataModel.setPlanDate(arrPendingActivityData.get(i).getPlannedDate());
                    activityListDataModel.setSiteId(arrPendingActivityData.get(i).getSiteId());
                    activityListDataModel.setCustomerSiteId(arrPendingActivityData.get(i).getSiteNumId());
                    activityListDataModel.setSiteName(arrPendingActivityData.get(i).getSiteName());
                    activityListDataModel.setActivityId(arrPendingActivityData.get(i).getActivityId());
                    activityListDataModel.setActivityName(arrPendingActivityData.get(i).getPlannedActivity());
                    activityListDataModel.setDateMilli(Long.parseLong(arrPendingActivityData.get(i).getDateMilli()));
                    activityListDataModel.setMarketingDistributor(arrPendingActivityData.get(i).getMarketingDistributor());
                    activityListDataModel.setTaskId(arrPendingActivityData.get(i).getTaskId());
                    activityListDataModel.setCircleId(arrPendingActivityData.get(i).getCircleId());
                    activityListDataModel.setCircleName(arrPendingActivityData.get(i).getCircleName());
                    activityListDataModel.setFeId(arrPendingActivityData.get(i).getFeId());
                    activityListDataModel.setFeName(arrPendingActivityData.get(i).getFeName());
                    activityListDataModel.setComplaint(arrPendingActivityData.get(i).getComplaintMessages());
                    activityListDataModel.setSubmittedOffline((arrPendingActivityData.get(i).getSubmittedOffline()));
                    arrActivityList.add(activityListDataModel);
                }
                arrFeName = new String[arrActivityList.size()];
                ArrayList<String> arrListFeName = new ArrayList<>();
                for (int i = 0; i < arrFeName.length; i++) {
                    arrListFeName.add(arrActivityList.get(i).getFeName());
                }
                setAdaptor(arrActivityList, "");
                setFeNameAdapter(arrListFeName);
            }*/

        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
               /* arrActivityListFilter = arrActivityList;
                ArrayList<ActivityListDataModel> arrActivityListFilterTemp = new ArrayList();
                ArrayList<ActivityListDataModel> arrActivityListFilterTemp1 = new ArrayList();
                for (int j = 0; j < arrActivities.length; j++) {
                    if (PlannedActivityListFragment.arrSelectedFilterOne[j] == true) {
                        for (int i = 0; i < arrActivityListFilter.size(); i++) {
                            if (arrActivities[j].equals(arrActivityListFilter.get(i).getActivityName())) {
                                arrActivityListFilterTemp.add(arrActivityListFilter.get(i));
                            }
                        }
                    }
                }
                for (int i = 0; i < arrCircles.length; i++) {
                    if (PlannedActivityListFragment.arrSelectedFilterTwo[i] == true) {
                        if (arrActivityListFilterTemp.size() > 1) {
                            for (int j = 0; j < arrActivityListFilterTemp.size(); j++) {
                                if (arrActivityListFilterTemp.get(j).getCircleName().equals(arrCircles[i])) {
                                    arrActivityListFilterTemp1.add(arrActivityListFilterTemp.get(j));
                                }
                            }
                        } else {
                            for (int j = 0; j < arrActivityListFilter.size(); j++) {
                                if (arrCircles[i].equals(arrActivityListFilter.get(j).getCircleName())) {
                                    arrActivityListFilterTemp.add(arrActivityListFilter.get(j));
                                }
                            }
                        }
                    }
                }
                if (arrActivityListFilterTemp1.size() > 0) {
                    arrActivityListFilterTemp = arrActivityListFilterTemp1;
                }
                if (arrActivityListFilterTemp.size() > 0) {
                    lvActivityList.setEmptyView(lvActivityList);
                    lvActivityList.setAdapter(new PlannedActivityListAdapter(getContext(), arrActivityListFilterTemp, "", userId, userAccess));
                } else {
                    ASTUIUtil.showToast("Data Not Found");
                }*/
            }
        });
    }

    /*
     *
     * Calling Web Service to get Activty List Data-
     */
    private void getActivityList() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.PLANNED_ACTIVITIES_URL;
        serviceURL += "&uid=" + userId + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getActivityList", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveActivityListData(result);
                } else {
                    ASTUIUtil.showToast("Activity Data Not Avilable!");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     *
     * Parse and save ActivityListData
     */

    public void parseandsaveActivityListData(String result) {
        if (result != null) {
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {
                    if (serviceData.getHeader() != null) {
                        atmdbHelper.deleteAllRows("PlanActivityHeaderDetails");
                        for (Header header : serviceData.getHeader()) {
                            atmdbHelper.insertPlanActivityHeaderData(header);
                        }
                        setHeaderDetail();
                    }
                    if (serviceData.getData() != null) {
                        for (Data data : serviceData.getData()) {
                            atmdbHelper.upsertPlanActivityData(data);
                        }
                        setAdaptor();
                    }
                }
            }

           /* try {
                arrActivityList = new ArrayList<>();
                ArrayList<PlannedActivityListModel> arrPendingActivityData = new ArrayList<>();
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                String planned = "";
                String executed = "";
                String onTheWay = "";
                String reachedSite = "";
                String leftSite = "";
                String unknown = "";
                String circle = "";
                String attendanceCount = "";
                String workingCount = "";
                String leaveCount = "";
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArrayHeader = jsonRootObject.optJSONArray("header");
                    for (int i = 0; i < jsonArrayHeader.length(); i++) {
                        JSONObject jsonObject = jsonArrayHeader.getJSONObject(i);
                        planned = jsonObject.optString("Planned").toString();
                        executed = jsonObject.optString("Executed").toString();
                        onTheWay = jsonObject.optString("OnTheWay").toString();
                        reachedSite = jsonObject.optString("ReachedSite").toString();
                        leftSite = jsonObject.optString("LeftSite").toString();
                        unknown = jsonObject.optString("Unknown").toString();
                        circle = jsonObject.optString("Circle").toString();
                        attendanceCount = jsonObject.optString("AttendanceCount").toString();
                        workingCount = jsonObject.optString("WorkingCount").toString();
                        leaveCount = jsonObject.optString("LeaveCount").toString();
                        tvPlannedExecuted.setText("Planned/Executed: " + planned + "/" + executed);
                        tvOnTheWay.setText("On The Way: " + onTheWay);
                        tvReachedSite.setText("Reached Site: " + reachedSite);
                        tvLeftSite.setText("Left Site: " + leftSite);
                        tvUnknown.setText("Unknown: " + unknown);
                        //tvCircleName.setText("Circle Name: " + );
                        tvAttendance.setText("Attendance/Leave: " + attendanceCount + "/" + leaveCount);
                        tvCircleName.setVisibility(View.GONE);
                        viewVertical5.setVisibility(View.GONE);
                        if (onTheWay.equals("")) {
                            tvOnTheWay.setText("N/A");
                        }
                        if (reachedSite.equals("")) {
                            tvReachedSite.setText("N/A");
                        }
                        if (leftSite.equals("")) {
                            tvLeftSite.setText("N/A");
                        }
                        if (unknown.equals("")) {
                            tvUnknown.setText("N/A");
                        }
                    }
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    arrFeName = new String[jsonArray.length()];
                    ArrayList<String> arrListFeName = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String planId = jsonObject.optString("PlanId").toString();
                        String planDate = jsonObject.optString("PlanDate").toString();
                        String siteId = jsonObject.optString("SiteId").toString();
                        String customerSiteId = jsonObject.optString("CustomerSiteId").toString();
                        String siteName = jsonObject.optString("SiteName").toString();
                        String activityId = jsonObject.optString("ActivityId").toString();
                        String activityName = jsonObject.optString("ActivityName").toString();
                        String marketingDistributor = jsonObject.optString("md").toString();
                        String taskId = jsonObject.optString("TaskId").toString();
                        String circleName = jsonObject.optString("Circle").toString();
                        String circleId = jsonObject.optString("CircleId").toString();
                        String feId = jsonObject.optString("FEId").toString();
                        String feName = jsonObject.optString("FEName").toString();
                        String complaintMessage = jsonObject.optString("ComplaintMessage").toString();
                        if (!arrListFeName.contains(feName)) {
                            arrListFeName.add(feName);
                        }
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                        long plannedActivityMillisecond = 0;
                        Date convertedDate = new Date();
                        try {
                            convertedDate = dateFormat.parse(planDate);
                            plannedActivityMillisecond = convertedDate.getTime();
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ActivityListDataModel activityListDataModel = new ActivityListDataModel();
                        activityListDataModel.setPlanID(planId);
                        activityListDataModel.setPlanDate(planDate);
                        activityListDataModel.setSiteId(siteId);
                        activityListDataModel.setCustomerSiteId(customerSiteId);
                        activityListDataModel.setSiteName(siteName);
                        activityListDataModel.setActivityId(activityId);
                        activityListDataModel.setActivityName(activityName);
                        activityListDataModel.setDateMilli(plannedActivityMillisecond);
                        activityListDataModel.setMarketingDistributor(marketingDistributor);
                        activityListDataModel.setTaskId(taskId);
                        activityListDataModel.setCircleId(circleId);
                        activityListDataModel.setCircleName(circleName);
                        activityListDataModel.setFeId(feId);
                        activityListDataModel.setFeName(feName);
                        activityListDataModel.setComplaint(complaintMessage);
                        activityListDataModel.setSubmittedOffline("0");
                        PlannedActivityListModel plannedActivityListModel = new PlannedActivityListModel();
                        plannedActivityListModel.setExecuted(planned + "/" + executed);
                        plannedActivityListModel.setOnTheWay(onTheWay);
                        plannedActivityListModel.setReachedSite(reachedSite);
                        plannedActivityListModel.setLeftSite(leftSite);
                        plannedActivityListModel.setUnknown(unknown);
                        plannedActivityListModel.setAttendance(attendanceCount);
                        plannedActivityListModel.setSiteName(siteName);
                        plannedActivityListModel.setSiteNumId(siteId);
                        plannedActivityListModel.setPlannedDate(planDate);
                        plannedActivityListModel.setSiteId(customerSiteId);
                        plannedActivityListModel.setPlannedActivity(activityName);
                        plannedActivityListModel.setFeName(feName);
                        plannedActivityListModel.setLastUpdatedDate(String.valueOf(System.currentTimeMillis()));
                        plannedActivityListModel.setPlanId(planId);
                        plannedActivityListModel.setActivityId(activityId);
                        plannedActivityListModel.setMarketingDistributor(marketingDistributor);
                        plannedActivityListModel.setTaskId(taskId);
                        plannedActivityListModel.setCircleName(circleName);
                        plannedActivityListModel.setCircleId(circleId);
                        plannedActivityListModel.setFeId(feId);
                        plannedActivityListModel.setComplaintMessages(complaintMessage);
                        plannedActivityListModel.setDateMilli(String.valueOf(plannedActivityMillisecond));
                        plannedActivityListModel.setLeave(leaveCount);
                        plannedActivityListModel.setSubmittedOffline("0");
                        int offlineDataCount = atmDatabase.getAllRowCount("activty_form_data");
                        if (offlineDataCount > 0) {
                            String[] arrFormData = atmDatabase.getFormData();
                            if (arrFormData[34].equalsIgnoreCase(plannedActivityListModel.getPlanId().toString())) {
                                Intent intentService = new Intent(getContext(), SyncFormDataWithServerIntentService.class);
                                getContext().startService(intentService);
                            }
                        } else {
                            arrPendingActivityData.add(plannedActivityListModel);
                            arrActivityList.add(activityListDataModel);
                        }
                    }
                    atmDatabase.deleteAllRows("planned_activity");
                    atmDatabase.addPendingActivityData(arrPendingActivityData);
                    for (int i = 0; i < arrListFeName.size(); i++) {
                        arrFeName[i] = arrListFeName.get(i);
                    }
                    setAdaptor(arrActivityList, "First");
                    setFeNameAdapter(arrListFeName);
                    first = "First";
                } else if (jsonStatus.equals("0")) {
                    atmDatabase.deleteAllRows("planned_activity");
                    ASTUIUtil.showToast("Data is not available");
                    lvActivityList.setEmptyView(lvActivityList);

                }
                Intent intentService = new Intent(getContext(), SyncFormDataWithServerIntentService.class);
                getContext().startService(intentService);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }*/
        }
    }

    //set header value
    private void setHeaderDetail() {
        ArrayList<Header> headerList = atmdbHelper.getAllPlanActivityHeaderData();
        if (headerList != null && headerList.size() > 0) {
            for (Header header : headerList) {
                String onTheWay = header.getOnTheWay();
                String reachedSite = header.getReachedSite();
                String leftSite = header.getLeftSite();
                String unknown = header.getUnknown();

                tvPlannedExecuted.setText("Planned/Executed: " + header.getPlanned() + "/" + header.getExecuted());
                tvOnTheWay.setText("On The Way: " + onTheWay);
                tvReachedSite.setText("Reached Site: " + reachedSite);
                tvLeftSite.setText("Left Site: " + leftSite);
                tvUnknown.setText("Unknown: " + unknown);
                //tvCircleName.setText("Circle Name: " + );
                tvAttendance.setText("Attendance/Leave: " + header.getAttendanceCount() + "/" + header.getLeaveCount());
                tvCircleName.setVisibility(View.GONE);
                viewVertical5.setVisibility(View.GONE);
                if (onTheWay.equals("")) {
                    tvOnTheWay.setText("N/A");
                }
                if (reachedSite.equals("")) {
                    tvReachedSite.setText("N/A");
                }
                if (leftSite.equals("")) {
                    tvLeftSite.setText("N/A");
                }
                if (unknown.equals("")) {
                    tvUnknown.setText("N/A");
                }
            }
        }
    }

    //Set Adapter for Planned Activity List Grid Data
    private void setAdaptor() {
        planActivityList = atmdbHelper.getAllPlanActivityData();
        if (planActivityList.size() == 0) {
            lvActivityList.setEmptyView(lvActivityList);
        } else {
            lvActivityList.setEmptyView(lvActivityList);
            PlannedActivityListAdapter mAdapter = new PlannedActivityListAdapter(getContext(), planActivityList, "", userId, userAccess);
            lvActivityList.setAdapter(mAdapter);

            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //after the change calling the method and passing the search input
                    mAdapter.getFilter().filter(editable.toString());
                }
            });
        }
    }

    /*
     *
     * Calling Web Service to get TaskList
     */
    private void getActivityDropdownList() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.ALL_TASK_LIST_URL;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getActivityDropdownList", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseAndSaveActivityDropdownListData(result);
                } else {
                    ASTUIUtil.showToast("Activity Dropdown List Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }


    /*
     *
     * Parse and Save Task List Data
     */

    public void parseAndSaveActivityDropdownListData(String result) {
        if (result != null) {
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {
                    if (serviceData.getData() != null) {
                        for (Data data : serviceData.getData()) {
                            atmdbHelper.upsertActivityDropdownData(data);
                        }
                    }
                }
            }

              /*  try {
                    JSONObject jsonRootObject = new JSONObject(result);
                    String jsonStatus = jsonRootObject.optString("status").toString();
                    atmDatabase.deleteAllRows("activity_search_details");
                    atmDatabase.deleteAllRows("activty_task");
                    atmDatabase.deleteAllRows("activty_reason");
                    if (jsonStatus.equals("2")) {
                        JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                        atmDatabase.deleteAllRows("activty_task");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String taskId = jsonObject.optString("TaskId").toString();
                            String taskName = jsonObject.optString("TaskName").toString();
                            taskListDataModel = new TaskListDataModel();
                            taskListDataModel.setTaskId(taskId);
                            taskListDataModel.setTaskName(taskName);
                            taskList.add(taskListDataModel);
                            JSONArray activtyJsonArray = jsonObject.optJSONArray("Activity");
                            for (int j = 0; j < activtyJsonArray.length(); j++) {
                                JSONObject activityJsonObject = activtyJsonArray.getJSONObject(j);
                                String activityId = activityJsonObject.optString("ActivityId").toString();
                                String activityName = activityJsonObject.optString("ActivityName").toString();
                                activityListSheetDataModel = new ActivityListSheetDataModel();
                                activityListSheetDataModel.setActivityId(activityId);
                                activityListSheetDataModel.setActivityName(activityName);
                                activityListSheetDataModel.setTaskId(taskId);
                                activityDetailArrayList.add(activityListSheetDataModel);
                                allActivityDetailArrayList.add(activityListSheetDataModel);
                                JSONArray reasonJsonArray = activityJsonObject.optJSONArray("Reason");
                                if (reasonJsonArray.length() > 0) {
                                    for (int k = 0; k < reasonJsonArray.length(); k++) {
                                        JSONObject reasonJsonObject = reasonJsonArray.getJSONObject(k);
                                        String reasonName = reasonJsonObject.optString("Text").toString();
                                        String reasonId = reasonJsonObject.optString("Id").toString();
                                        reasonListDataModel = new ReasonListDataModel();
                                        reasonListDataModel.setReasonName(reasonName);
                                        reasonListDataModel.setReasonId(reasonId);
                                        reasonListDataModel.setActivityId(activityId);
                                        reasonList.add(reasonListDataModel);
                                    }
                                }
                                atmDatabase.addReasonData(reasonList);
                                reasonList.clear();
                            }
                            atmDatabase.addActivityData(activityDetailArrayList);
                            activityDetailArrayList.clear();
                        }
                        atmDatabase.addTaskData(taskList);
                    } else if (jsonStatus.equals("0")) {
                        ASTUIUtil.showToast("Data is not available");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }*/
        }
    }

/*
    public void filterPopup(ArrayList<ActivityListSheetDataModel> activityDetailArrayList, String title) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.activity_filter_popup);
        dialog.setTitle(title);
        ListView lvfilterPopup = dialog.findViewById(R.id.lvFilter);
        Button btnDone = dialog.findViewById(R.id.btnDone);
        final List<String> listActivities = new ArrayList<>();
        final List<String> listCircles = new ArrayList<>();
        checkListCircle = new int[0];
        checkListActivity = new int[0];
        if (title.equals("Select Circle")) {
            //circleViewResDataList
            for (int i = 0; i < circleViewResDataList.size(); i++) {
                listCircles.add(circleViewResDataList.get(i).getCircleName());
            }
            checkListCircle = new int[listCircles.size()];
            lvfilterPopup.setAdapter(new CircleParentFilterAdapter(getContext(), listCircles, "Circle"));
        } else {
            for (int i = 0; i < allActivityDetailArrayList.size(); i++) {
                listActivities.add(allActivityDetailArrayList.get(i).getActivityName());
            }
            checkListActivity = new int[listActivities.size()];
            lvfilterPopup.setAdapter(new CircleParentFilterAdapter(getContext(), listActivities, "Activity"));
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ActivityListDataModel> arrActivityListFilterTemp = new ArrayList<ActivityListDataModel>();
                if (checkListCircle.length > 1) {
                    arrActivityListFilterTemp = arrActivityListFilter;
                } else if (checkListActivity.length > 1) {
                    arrActivityListFilterTemp = arrActivityListFilter;
                }
                arrActivityListFilter = new ArrayList<ActivityListDataModel>();
                if (checkListActivity.length > 0) {
                    for (int i = 0; i < checkListActivity.length; i++) {
                        if (checkListActivity[i] == 1) {
                            if (filterType.equals("Activity")) {
                                String filterValue = listActivities.get(i);
                                if (arrActivityListFilterTemp.size() > 0) {
                                    for (int j = 0; j < arrActivityListFilterTemp.size(); j++) {
                                        if (arrActivityListFilterTemp.get(j).getActivityName().equals(filterValue)) {
                                            arrActivityListFilter.add(arrActivityListFilterTemp.get(j));
                                        }
                                    }
                                } else {
                                    for (int j = 0; j < arrActivityList.size(); j++) {

                                        if (arrActivityList.get(j).getActivityName().equals(filterValue)) {
                                            arrActivityListFilter.add(arrActivityList.get(j));
                                        }

                                    }
                                }

                            }
                        }
                    }
                } else if (checkListCircle.length > 0) {
                    for (int i = 0; i < checkListCircle.length; i++) {
                        if (checkListCircle[i] == 1) {
                            if (filterType.equals("Circle")) {
                                String filterValue = listCircles.get(i);
                                if (arrActivityListFilterTemp.size() > 0) {
                                    for (int j = 0; j < arrActivityListFilterTemp.size(); j++) {
                                        if (arrActivityListFilterTemp.get(j).getCircleName().equals(filterValue)) {
                                            arrActivityListFilter.add(arrActivityListFilterTemp.get(j));
                                        }
                                    }
                                } else {
                                    for (int j = 0; j < arrActivityList.size(); j++) {
                                        if (arrActivityList.get(j).getCircleName().equals(filterValue)) {
                                            arrActivityListFilter.add(arrActivityList.get(j));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                lvActivityList.setAdapter(new PlannedActivityListAdapter(getContext(), arrActivityListFilter, "", userId, userAccess));
                String strFilteredCircles = "";
                for (int i = 0; i < arrActivityListFilter.size(); i++) {
                    if (!strFilteredCircles.contains(arrActivityListFilter.get(i).getCircleName())) {
                        strFilteredCircles += arrActivityListFilter.get(i).getCircleName() + "/";
                    }
                    //strFilteredCircles
                }

                tvCircleName.setVisibility(View.VISIBLE);
                viewVertical5.setVisibility(View.VISIBLE);
                tvCircleName.setText("Circle: " + strFilteredCircles);
                dialog.dismiss();
                if (strFilteredCircles.length() < 1) {
                    tvCircleName.setVisibility(View.GONE);
                    viewVertical5.setVisibility(View.GONE);
                }
            }
        });

        dialog.show();
    }*/

  /*  public void genrateFilterList() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setIcon(R.drawable.ic_launcher);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Remove Filter");
        arrayAdapter.add("Activity");
        arrayAdapter.add("Circle");
        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        if (position == 1) {
                            filterType = "Activity";
                            filterPopup(activityDetailArrayList, "Select Activity");
                            tvCircleName.setVisibility(View.GONE);
                            viewVertical5.setVisibility(View.GONE);
                        } else if (position == 0) {
                            setAdaptor(arrActivityList, "");
                            tvCircleName.setVisibility(View.GONE);
                            viewVertical5.setVisibility(View.GONE);
                            checkListCircle = new int[0];
                            checkListActivity = new int[0];
                        } else if (position == 2) {
                            filterType = "Circle";
                            filterPopup(activityDetailArrayList, "Select Circle");
                        }
                        dialog.dismiss();
                    }
                });
        builderSingle.show();
    }*/

    /* *//**
     * @param arrListFeName set Name Adapter
     *//*
    private void setFeNameAdapter(ArrayList<String> arrListFeName) {
        ArrayAdapter<String> adapterSiteName = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrListFeName);
        etSearch.setAdapter(adapterSiteName);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = etSearch.getText().toString();
                if (searchText.length() < 2) {
                    lvActivityList.setEmptyView(lvActivityList);
                    lvActivityList.setAdapter(new PlannedActivityListAdapter(getContext(), arrActivityList, "", userId, userAccess));
                }
            }
        });
        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchString = etSearch.getText().toString();
                ArrayList<ActivityListDataModel> arrFilteredActivityList = new ArrayList<ActivityListDataModel>();
                for (int i = 0; i < arrActivityList.size(); i++) {
                    if (searchString.equalsIgnoreCase(arrActivityList.get(i).getFeName())) {
                        arrFilteredActivityList.add(arrActivityList.get(i));
                    }
                }
                lvActivityList.setEmptyView(lvActivityList);
                lvActivityList.setAdapter(new PlannedActivityListAdapter(getContext(), arrFilteredActivityList, "", userId, userAccess));
            }
        });


    }*/

    /*  */

    /**
     * show popup for save offline activity
     *//*
    public void alertForOfflineData() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.setMessage("This activity save in offline mode!");
        dialog.setTitle("Executed Delivery Pending Alert");
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Refresh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                getActivityList();
            }
        });
        dialog.show();
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
    }*/
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAddNewActivity) {
            // openActivitySheetFragment(0, "", "", 0, 0, 0, "", true);
        } else if (view.getId() == R.id.rbActivity) {
            if (rbActivity.isChecked()) {
            }
        } else if (view.getId() == R.id.rbNoFilter) {
            if (rbNoFilter.isChecked()) {
                //setAdaptor(arrActivityList, "");
            }
        } else if (view.getId() == R.id.rbCircle) {
            if (rbCircle.isChecked()) {
            }
        } else if (view.getId() == R.id.tvFilter) {
            openFilterList();
        } else if (view.getId() == R.id.imgRefresh) {
            getActivityList();
        }
    }

    /**
     * open Activity Sheet Fragment
     */
    public void openActivitySheetFragment(long SiteId, String siteName, String custmoerSiteId, long activityId, long planId, long taskId, String ComplaintMessage, boolean addNewActivityFlage) {
        ActivitySheetFragment activitySheetFragment = new ActivitySheetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "Activity Form");
        bundle.putBoolean("showMenuButton", false);
        bundle.putString("SITE_ID", SiteId + "");
        bundle.putString("SITE_NAME", siteName);
        bundle.putString("SITE_CUSTOMER_SITE_ID", custmoerSiteId);
                      /*  if (first.equals("First")) {
                            bundle.putString("SITE_CUSTOMER_SITE_ID", arrActivityList.get(position).getCustomerSiteId());
                        } else {
                            bundle.putString("SITE_CUSTOMER_SITE_ID", arrActivityList.get(position).getSiteId());
                        }*/
        bundle.putString("ACTIVITY_ID", activityId + "");
        bundle.putString("PLAN_ID", planId + "");
        bundle.putString("TASK_ID", taskId + "");
        bundle.putString("COMPLAINT_MESSAGE", ComplaintMessage);
        getHostActivity().updateFragment(activitySheetFragment, bundle);

    }

    /**
     * get Filter List  and show FilterPopup
     */
    public void openFilterList() {
        String[] arrParentData = new String[2];
        arrParentData[0] = "Activity";
        arrParentData[1] = "Circle";
        String[] arrFilteredIdData = {"Battery", "NoComm", "INV"};

        /*arrActivities = new String[allActivityDetailArrayList.size()];
        arrCircles = new String[circleViewResDataList.size()];
        checkListCircle = new int[0];
        checkListActivity = new int[0];
        for (int i = 0; i < circleViewResDataList.size(); i++) {
            arrCircles[i] = circleViewResDataList.get(i).getCircleName();
        }
        for (int i = 0; i < allActivityDetailArrayList.size(); i++) {
            arrActivities[i] = allActivityDetailArrayList.get(i).getActivityName();
        }
        arrSelectedFilterOne = new Boolean[arrActivities.length];
        arrSelectedFilterTwo = new Boolean[arrCircles.length];
        for (int i = 0; i < arrActivities.length; i++) {
            arrSelectedFilterOne[i] = false;
        }
        for (int i = 0; i < arrCircles.length; i++) {
            arrSelectedFilterTwo[i] = false;
        }*/
        FilterPopupActivity filterPopup = new FilterPopupActivity();
        filterPopup.getFilterPopup(popup, getContext(), arrParentData, arrFilteredIdData, "planned_activity");
    }


}