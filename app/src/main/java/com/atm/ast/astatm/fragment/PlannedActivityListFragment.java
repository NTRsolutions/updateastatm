package com.atm.ast.astatm.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
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
    RadioButton rbNoFilter, rbActivity, rbCircle;
    TextView tvFilter;
    TextView tvPlannedExecuted, tvOnTheWay, tvReachedSite, tvLeftSite, tvUnknown, tvCircleName, tvAttendance;
    View viewVertical5;
    AutoCompleteTextView etSearch;
    ImageView imgRefresh;
    SharedPreferences pref;
    String userId, userRole, userAccess;
    String userName = "";
    //-----------------
    String lat = "23.23";
    String lon = "23.23";
    ASTUIUtil commonFunctions;
    TextView appversion_number;
    private ATMDBHelper atmdbHelper;

    ArrayList<Data> planActivityList;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_planned_activity_list;
    }

    @Override
    protected void getArgs() {
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
        commonFunctions = new ASTUIUtil();
        getSharedPrefData();
        String appversionName = ASTUIUtil.getAppVersionName(getContext());
        if (appversionName != null) {
            appversion_number.setText(appversionName);
        }
        if (ASTUIUtil.isOnline(getContext())) {
            getActivityList();
            getActivityDropdownList();
        } else {
            setHeaderDetail();
            setAdaptor();
            ASTUIUtil.showToast(Contants.OFFLINE_MESSAGE);
        }
        lvActivityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (planActivityList != null && planActivityList.size() > 0) {
                    if (userAccess.equals("fe")) {
                        openActivitySheetFragment(planActivityList.get(position).getSiteId(), planActivityList.get(position).getSiteName(), planActivityList.get(position).getCustomerSiteId(), planActivityList.get(position).getActivityId(), planActivityList.get(position).getPlanId(), planActivityList.get(position).getTaskId(), planActivityList.get(position).getComplaintMessage(), false);
                    }
                }
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
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                _progrssBar.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                Boolean flag = false;
                if (result != null) {
                    ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
                    if (serviceData != null) {
                        atmdbHelper.deleteAllRows("PlanActivityHeaderDetails");
                        atmdbHelper.deleteAllRows("PlanActivityDetails");
                        if (serviceData.getStatus() == 2) {
                            if (serviceData.getHeader() != null) {
                                for (Header header : serviceData.getHeader()) {
                                    atmdbHelper.insertPlanActivityHeaderData(header);
                                }
                            }
                            if (serviceData.getData() != null) {
                                for (Data data : serviceData.getData()) {
                                    atmdbHelper.upsertPlanActivityData(data);
                                }

                            }
                            flag = true;
                        }
                    }
                }

                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                setHeaderDetail();
                setAdaptor();
                if (_progrssBar.isShowing()) {
                    _progrssBar.dismiss();
                }
            }
        }.execute();
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
        }
    }

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
        bundle.putString("ACTIVITY_ID", activityId + "");
        bundle.putString("PLAN_ID", planId + "");
        bundle.putString("TASK_ID", taskId + "");
        bundle.putString("COMPLAINT_MESSAGE", ComplaintMessage);
        getHostActivity().updateFragment(activitySheetFragment, bundle);

    }


}