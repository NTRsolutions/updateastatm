package com.atm.ast.astatm.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.SyncFECallTrackerIntentService;
import com.atm.ast.astatm.adapter.UnsyncedActivityFormAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.ActivitySheetModel;
import com.atm.ast.astatm.model.newmodel.Activity;
import com.atm.ast.astatm.model.newmodel.ContentLocalData;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.FieldEngineer;
import com.atm.ast.astatm.model.newmodel.NOCEngineer;
import com.atm.ast.astatm.model.newmodel.Reason;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.utils.CustomDialog;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.LogAnalyticsHelper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ActivitySheetFragment extends MainFragment {
    Spinner spActivities, spZone, spNOC, spStatus, spReason, spTask, spMaterialStatus, spDaysTaken;
    FloatingActionButton btnSyncData;
    EditText etRemarks, etComplaintMessage, etOtherExpenses;
    ImageView imgRefresh, imgDaChart;
    TextView tvCurrentDate;
    PopupWindow siteNotFoundPopupWindow;
    private GoogleApiClient client;
    Dialog daRateChartDialog = null;
    AutoCompleteTextView tvSiteName, tvSiteId;
    Button btnsave;
    Boolean isPlan = false;
    ASTUIUtil commonFunction;
    CustomDialog customDialog;
    ATMDBHelper atmdbHelper;
    List<Data> siteDetailArrayList;
    ArrayList<NOCEngineer> nocEnggList;
    ArrayList<FieldEngineer> FieldEngineerList;
    ArrayList<Data> taskList;
    ArrayList<Reason> reasonList;
    ArrayList<Activity> activityDetailArrayList;
    SharedPreferences pref;
    String userId, userRole, userAccess, r1;
    String userName = "";
    String siteId = "";
    String siteName;
    String siteCustomerSiteId;
    String activityId = "";
    String complaintMessage;
    String lat = "23.23";
    String lon = "23.23";
    String[] arrSiteName, arrSiteId;
    Dialog unsyncedDialog = null;
    long plannedDate;
    String planId;
    String taskId;
    LogAnalyticsHelper analyticsHelper = null;
    boolean engineerFlage = false;
    boolean pmChecklistFlag = false;
    private String strIsPm;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_activity_sheet;
    }

    @Override
    protected void getArgs() {
        siteId = this.getArguments().getString("SITE_ID");
        siteName = this.getArguments().getString("SITE_NAME");
        siteCustomerSiteId = this.getArguments().getString("SITE_CUSTOMER_SITE_ID");
        activityId = this.getArguments().getString("ACTIVITY_ID");
        planId = this.getArguments().getString("PLAN_ID");
        taskId = this.getArguments().getString("TASK_ID");
        complaintMessage = this.getArguments().getString("COMPLAINT_MESSAGE");


        /*708	Survey   nm     nm
        710	Civil  nm          nm
        711	Electrical   M     m
        712	Material Shifting WH to Site  nm       nm
        713	Dismantling nm         nm
        714	Sign Off     m         nm
        715	Material Shifting Site to WH nm  nm
        716	Others   nm     nm
        878	Rework (Civil and Electrical)    m     m
        885	Site Upgrade     m     m
        890	Dismantling and Reinstallation    m    m
        1168 Electrical+Civil     m         m
        1179 Material Shifting Site to Site    nm     nm
        1282 Commisioning Rework    m    nm
        1371 Long Distance Travel    nm   nm
        1380 System Upgrade    m        m

        845	Other     nm    nm
        858	Leave    nm     nm
        859	Absent   nm     nm
        860	No Plan   nm    nm
        730	Joint Visit  nm    nm
        749	PM    m     nm
        750	CM     m    m
        886	Recivil Repair   nm    nm
        1180 Special Project    m    nm
        1238 Material Shifting WH to Site    nm     nm
        1239 Material Shifting Site to WH  nm     nm
        1281 WH  nm     nm
        1303 Material Shifting Site to Site   nm    nm
        1372 Long Distance Travel       nm     nm*/


    }

    @Override
    protected void loadView() {
        this.spActivities = this.findViewById(R.id.spActivities);
        this.spActivities.setEnabled(false);
        this.spZone = this.findViewById(R.id.spZone);
        this.spNOC = this.findViewById(R.id.spNOC);
        this.spStatus = this.findViewById(R.id.spStatus);
        this.spReason = this.findViewById(R.id.spReason);
        this.spMaterialStatus = findViewById(R.id.spMaterialStatus);
        this.tvSiteName = this.findViewById(R.id.tvSiteName);
        this.tvSiteId = this.findViewById(R.id.tvSiteId);
        this.btnsave = this.findViewById(R.id.btnsave);
        this.spTask = this.findViewById(R.id.spTask);
        this.spDaysTaken = this.findViewById(R.id.spDaysTaken);
        this.etRemarks = this.findViewById(R.id.etRemarks);
        this.etComplaintMessage = this.findViewById(R.id.etComplaintMessage);
        this.imgRefresh = this.findViewById(R.id.imgRefresh);
        this.btnSyncData = this.findViewById(R.id.btnSyncData);
        this.tvCurrentDate = this.findViewById(R.id.tvCurrentDate);
        this.imgDaChart = this.findViewById(R.id.imgDaChart);
        this.etOtherExpenses = this.findViewById(R.id.etDaAmount);
    }

    @Override
    protected void setClickListeners() {
        this.imgRefresh.setOnClickListener(this);
        this.btnSyncData.setOnClickListener(this);
        this.btnsave.setOnClickListener(this);

    }

    @Override
    protected void setAccessibility() {

    }

    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        userId = pref.getString("userId", "");
        userName = pref.getString("userName", "");
        userAccess = pref.getString("userAccess", "");
    }

    @Override
    protected void dataToView() {
        if (activityId.equals("711") || activityId.equals("714") || activityId.equals("878") || activityId.equals("885") || activityId.equals("890") || activityId.equals("1168") || activityId.equals("1282") || activityId.equals("1380") || activityId.equals("749") || activityId.equals("750") || activityId.equals("1180")) {//PM check list mandatory for all these activitys
            btnsave.setText("Next");
            pmChecklistFlag = true;
        } else {
            btnsave.setText("Submit");
            pmChecklistFlag = false;
        }

        siteNotFoundPopupWindow = new PopupWindow(getContext());
        atmdbHelper = new ATMDBHelper(getContext());
        logScreen();
        //--------------Start Background Service--------------------------------
        Intent intentService = new Intent(getContext(), SyncFECallTrackerIntentService.class);
        getContext().startService(intentService);
        getSharedPrefData();
        activityDetailArrayList = new ArrayList<>();
        nocEnggList = new ArrayList<>();
        FieldEngineerList = new ArrayList<>();
        reasonList = new ArrayList<>();
        taskList = new ArrayList<>();

        daRateChartDialog = new Dialog(getContext());
        commonFunction = new ASTUIUtil();
        String formattedDate = commonFunction.getCurrentDate();
        String appversionName = ASTUIUtil.getAppVersionName(getContext());
        if (appversionName != null) {
            tvCurrentDate.setText(formattedDate + " : " + appversionName);
        }
        if (complaintMessage != null && !complaintMessage.equals("")) {
            etComplaintMessage.setText(complaintMessage);
        } else {
            etComplaintMessage.setVisibility(View.GONE);
        }
        tvSiteName.setEnabled(false);
        tvSiteId.setEnabled(false);
        imgDaChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateDaRateChart();
            }
        });

        unsyncedDialog = new Dialog(getContext());
        setAllSpinnner();
        spTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                             @Override
                                             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                 if (position > 0) {
                                                     if (spTask.getSelectedItem().toString().equals("CAD")) {
                                                         spZone.setEnabled(false);
                                                         spZone.setSelection(0);
                                                         spActivities.setEnabled(true);
                                                     } else if (spTask.getSelectedItem().toString().equals("CM")) {
                                                         spZone.setEnabled(true);
                                                         spActivities.setEnabled(true);
                                                     } else {
                                                         spZone.setEnabled(true);
                                                         spActivities.setEnabled(true);
                                                     }

                                                     if (!planId.equals("0")) {
                                                         spActivities.setEnabled(false);
                                                     }
                                                 } else {
                                                     spActivities.setEnabled(false);
                                                 }
                                             }

                                             @Override
                                             public void onNothingSelected(AdapterView<?> parentView) {
                                                 // your code here
                                             }

                                         }

        );
        spDaysTaken.setEnabled(false);
        spActivities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                                                              int position, long id) {
                                                       if (spActivities.getSelectedItemPosition() > 0) {
                                                           long activityId = getActivityId();
                                                           setReasonListAdapter(activityId);
                                                           spReason.setEnabled(true);
                                                           spDaysTaken.setEnabled(true);
                                                       } else {
                                                           spReason.setEnabled(false);
                                                       }
                                                   }

                                                   @Override
                                                   public void onNothingSelected(AdapterView<?> parentView) {
                                                       // your code here
                                                   }
                                               }
        );
        spDaysTaken.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                   if (spStatus.getSelectedItemPosition() == 2) {
                                                   }
                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> parentView) {
                                                   // your code here
                                               }
                                           }

        );
        getAllActivity();
        setSiteNameAdapter();
        if (ASTUIUtil.isOnline(getContext())) {
            getNocEnggListData();
        } else {
            setNocEnggAdapter();
        }
    }

    //set all spinner value
    private void setAllSpinnner() {
        final ArrayList<String> zoneList = new ArrayList<>();
        zoneList.add("--Select Zone--");
        zoneList.add("In Zone");
        zoneList.add("Out Zone with Bill");
        zoneList.add("Out Zone without Bill");
        zoneList.add("Nil");
        final ArrayList<String> arrDaysTaken = new ArrayList<>();
        arrDaysTaken.add("--Select Days--");
        arrDaysTaken.add("Day 1");
        arrDaysTaken.add("Day 2");
        arrDaysTaken.add("Day 3");
        arrDaysTaken.add("Day 4");
        arrDaysTaken.add("Day 5");
        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("--Select Status--");
        statusList.add("Completed");
        statusList.add("Pending");
        statusList.add("Customer Issue");
        ArrayList<String> MaterialStatusList = new ArrayList<>();
        MaterialStatusList.add("--Select Material Status--");
        MaterialStatusList.add("Material at Site");
        MaterialStatusList.add("Material Moved");
       /* ArrayList<String> reasonList = new ArrayList<>();
        reasonList.add("--Select Reason--");
        reasonList.add("NC, Non Comm");
        reasonList.add("ERT, Earthing Issue");
        reasonList.add("INV, Inverter Problem");
        reasonList.add("MCB, MCB Trip/Faulty");
        reasonList.add("CP, Control Panel fault");
        reasonList.add("MPP, MPPT Faulty");
        reasonList.add("BTY, Battery Faulty");
        reasonList.add("SO, Solar panel Collapse");
        reasonList.add("CA, Cable Burnt");
        reasonList.add("PM, PM Visit");
        reasonList.add("DCM, DC Meter Faulty");
        reasonList.add("MOU, Module Upgrade");
        reasonList.add("SPU, Solar Panel Change/Upgrade");
        reasonList.add("TI, Timer Installation/Rectification");
        reasonList.add("INU, Inverter Upgrade");
        reasonList.add("SPH, Survey photos pending");
        reasonList.add("IPH, Installation Photo pending");
        reasonList.add("PM, PM Visit");
        reasonList.add("DCM, DC Meter Faulty");
        reasonList.add("MOU, Module Upgrade");
        reasonList.add("SPU, Solar Panel Change/Upgrade");
        reasonList.add("TI, Timer Installation/Rectification");
        reasonList.add("INU, Inverter Upgrade");
        reasonList.add("SPH, Survey photos pending");
        reasonList.add("IPH, Installation Photo pending");
        reasonList.add("NWC, Network Equipment Connection");
        reasonList.add("BNK, Bank Issue");
        reasonList.add("CON, Connection issue");
        reasonList.add("MOF, Module Faulty");
        reasonList.add("CHF, Charger Faulty");
        reasonList.add("CHU, Charger Upgrade");
        reasonList.add("PCU, Solar PCU Upgrade");
        reasonList.add("PCF, Solar PCU faulty");
        reasonList.add("OVF, OVCD  Faulty");
        reasonList.add("OVU, OVCD Upgrade");
        Collections.sort(reasonList);*/

        //-------------------------------ZOne Spinner-----------------------------------
        // Creating adapter for activity spinner
        ArrayAdapter<String> dataAdapterZone = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, zoneList);
        dataAdapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spZone.setAdapter(dataAdapterZone);
        // ---------------------------------Days Spinner------------------------------------
        ArrayAdapter<String> dataAdapterDays = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrDaysTaken);
        dataAdapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDaysTaken.setAdapter(dataAdapterDays);

        //-------------------------------Status Spinner-----------------------------------
        ArrayAdapter<String> dataAdapterStatus = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, statusList);
        dataAdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(dataAdapterStatus);

        ArrayAdapter<String> dataAdapterMaterialStatus = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, MaterialStatusList);
        dataAdapterMaterialStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaterialStatus.setAdapter(dataAdapterMaterialStatus);
    }

    //------------log Activity----------
    private void logScreen() {
        analyticsHelper = new LogAnalyticsHelper(getContext());
        Bundle bundle = new Bundle();
        bundle.putString("User Name", userName);
        bundle.putString("siteId", siteId);
        bundle.putString("siteName", siteName);
        bundle.putString("siteCustomerSiteId", siteCustomerSiteId);
        bundle.putString("activityId", activityId);
        bundle.putString("planId", planId);
        bundle.putString("taskId", taskId);
        analyticsHelper.logEvent("Activity Sheet screen :", bundle);
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
        serviceURL += "&uid=" + userId + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getSiteSearchData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSiteSearchData(result);
                } else {
                    ASTUIUtil.showToast("Site Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     *
     * Parse and Save getSiteSearchData
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
                                setSiteNameAdapter();
                            }
                        }
                    }.execute();
                } else {
                    ASTUIUtil.showToast("Site Data Not Avilable");
                }
            }
        }
    }

    //get all task list data
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
                    ASTUIUtil.showToast("Task List Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }

    public void parseAndSaveActivityDropdownListData(String result) {

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
                        if (serviceData.getStatus() == 2) {
                            if (serviceData.getData() != null) {
                                for (Data data : serviceData.getData()) {
                                    atmdbHelper.upsertActivityDropdownData(data);
                                }
                                flag = true;
                            }
                        }
                    }
                }

                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                setTaskListAdapter();
                if (_progrssBar.isShowing()) {
                    _progrssBar.dismiss();
                }
            }
        }.execute();
    }

    /*
     *
     *  Calling Web Service to get NOC Engineer List
     */
    private void getNocEnggListData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = "";
        serviceURL = Contants.BASE_URL + Contants.ALL_NOC_ENGG_LIST_URL;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getNocEnggListData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveNocEnggListData(result);
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     * Parse and Save getNocEnggListData
     */
    public void parseandsaveNocEnggListData(String result) {

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
                        if (serviceData.getStatus() == 2) {
                            if (serviceData.getNOCEngineer() != null) {
                                for (NOCEngineer data : serviceData.getNOCEngineer()) {
                                    atmdbHelper.upsertNOCEngineerData(data);
                                }
                            }
                            if (serviceData.getFieldEngineer() != null) {
                                for (FieldEngineer data : serviceData.getFieldEngineer()) {
                                    atmdbHelper.upsertFieldEngineerData(data);
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
                setNocEnggAdapter();
                if (_progrssBar.isShowing()) {
                    _progrssBar.dismiss();
                }
            }
        }.execute();
    }

    //get all site name and site id
    private void getSiteNameAndSiteID() {
        siteDetailArrayList = atmdbHelper.getAllSiteListData();
        arrSiteName = new String[siteDetailArrayList.size()];
        arrSiteId = new String[siteDetailArrayList.size()];
        for (int i = 0; i < siteDetailArrayList.size(); i++) {
            arrSiteName[i] = siteDetailArrayList.get(i).getSiteName();
            arrSiteId[i] = String.valueOf(siteDetailArrayList.get(i).getCustomerSiteId());//siteDetailArrayList.get(i).getSiteId()
        }
    }

    //set site name and site id
    private void setSiteNameAdapter() {
        getSiteNameAndSiteID();
        if (siteId != null && !siteId.equals("")) {
            tvSiteName.setText(siteName);
            tvSiteId.setText(siteCustomerSiteId);
        }
        ArrayAdapter<String> adapterSiteName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrSiteName);
        tvSiteName.setAdapter(adapterSiteName);
        tvSiteName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String siteName = tvSiteName.getText().toString();
                for (int i = 0; i <= arrSiteName.length - 1; i++) {
                    if (siteName.equalsIgnoreCase(arrSiteName[i])) {
                        tvSiteId.setText(arrSiteId[i]);
                    }
                }
            }
        });

        ArrayAdapter<String> adapterSiteId = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrSiteId);
        tvSiteId.setAdapter(adapterSiteId);
        tvSiteId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String siteId = tvSiteId.getText().toString();
                for (int i = 0; i <= arrSiteId.length - 1; i++) {
                    if (siteId.equalsIgnoreCase(arrSiteId[i])) {
                        tvSiteName.setText(arrSiteName[i]);
                    }
                }
            }
        });
    }

    //get all activity list
    private void getAllActivity() {
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
                ArrayList<Data> dataList = atmdbHelper.getAllActivityDropdownData();
                activityDetailArrayList = new ArrayList<Activity>();
                if (dataList != null && dataList.size() > 0) {
                    for (Data data : dataList) {
                        taskList.add(data);//add task lsit data
                        for (Activity activity : data.getActivity()) {
                            activityDetailArrayList.add(activity);
                        }
                    }
                    flag = true;
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                setActivityListAdapter();
                setTaskListAdapter();
                if (_progrssBar.isShowing()) {
                    _progrssBar.dismiss();
                }
            }
        }.execute();
    }

    //set activity spinner
    private void setActivityListAdapter() {
        int selectionIndex = 0;
        String[] arrActivityList = new String[activityDetailArrayList.size() + 1];
        arrActivityList[0] = "--Select Activity--";
        for (int i = 1; i <= activityDetailArrayList.size(); i++) {
            arrActivityList[i] = activityDetailArrayList.get(i - 1).getActivityName();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrActivityList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (!activityId.equals("0") && !activityId.equals("")) {
            spActivities.setEnabled(false);
            for (int i = 0; i < activityDetailArrayList.size(); i++) {
                if (activityDetailArrayList.get(i).getActivityId() == Long.parseLong(activityId)) {
                    selectionIndex = i + 1;//Select Activity based on planned activity
                }
            }
        }
        spActivities.setAdapter(dataAdapter);
        spActivities.setSelection(selectionIndex);
    }

    //set noc engg name list in array
    private void setNocEnggAdapter() {
        String[] arrEnggList = null;
        if (engineerFlage) { //FieldEngineer
            FieldEngineerList = atmdbHelper.getAllFieldEngineerData();
            if (FieldEngineerList != null && FieldEngineerList.size() > 0) {
                arrEnggList = new String[FieldEngineerList.size() + 2];
                arrEnggList[0] = "--Select Engineer--";
                arrEnggList[1] = "--No Engineer--";
                for (int i = 2; i < FieldEngineerList.size() + 2; i++) {
                    arrEnggList[i] = FieldEngineerList.get(i - 2).getFieldEngName();
                }
            }
        } else {  //NOCEngineer
            nocEnggList = atmdbHelper.getAllNOCEngineerData();
            if (nocEnggList != null && nocEnggList.size() > 0) {
                arrEnggList = new String[nocEnggList.size() + 2];
                arrEnggList[0] = "--Select Engineer--";
                arrEnggList[1] = "--No Engineer--";
                for (int i = 2; i < nocEnggList.size() + 2; i++) {
                    arrEnggList[i] = nocEnggList.get(i - 2).getNocEngName();
                }
            }
        }
        if (arrEnggList != null && arrEnggList.length > 0) {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrEnggList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spNOC.setAdapter(dataAdapter);
        }
    }

    //set reason list data
    private void setReasonListAdapter(long activityId) {
        for (Activity activity : activityDetailArrayList) {
            if (activity.getActivityId() == activityId) {
                for (Reason reason : activity.getReason()) {
                    reasonList.add(reason);
                }
            }
        }
        if (reasonList.size() > 0) {
            String[] arrReasonList = new String[reasonList.size() + 1];
            arrReasonList[0] = "--Select Reason--";
            for (int i = 0; i < reasonList.size(); i++) {
                arrReasonList[i + 1] = reasonList.get(i).getText();
            }
            ArrayAdapter<String> dataAdapter =
                    new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrReasonList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spReason.setAdapter(dataAdapter);
        } else {
            String[] arrReasonList = new String[1];
            arrReasonList[0] = "--Select Reason--";
            //spReason.setEnabled(false);
            ArrayAdapter<String> dataAdapter =
                    new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrReasonList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spReason.setAdapter(dataAdapter);
        }
    }

    //set task list
    private void setTaskListAdapter() {
        String[] arrTaskList = new String[taskList.size() + 1];
        arrTaskList[0] = "--Select Task--";
        int selectionIndex = 0;
        for (int i = 1; i < taskList.size() + 1; i++) {
            arrTaskList[i] = taskList.get(i - 1).getTaskName();
        }
        //---------Select Task based on planned activity-------------
        if (!activityId.equals("0") && !activityId.equals("")) {
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.get(i).getTaskId() == Long.parseLong(taskId)) {
                    selectionIndex = i + 1;
                }
            }
        }
        spTask.setEnabled(false);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrTaskList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTask.setAdapter(dataAdapter);
        spTask.setSelection(selectionIndex);
    }

    //get activity id from activityDetailArrayList
    private long getActivityId() {
        long activityId = 0;
        for (int i = 0; i < activityDetailArrayList.size(); i++) {
            String activityName = spActivities.getSelectedItem().toString();
            if (spActivities.getSelectedItem().toString().equals(activityDetailArrayList.get(i).getActivityName())) {
                activityId = activityDetailArrayList.get(i).getActivityId();
            }
        }
        return activityId;
    }

    public void SaveDataToServer(String NocEnggId, String NocEnggContact, String remarks, int zoneId, int materialStatus, int statusId, int reason) throws ParseException {
        String isPlanned = "1";  // change to pm checklist activity type 1

        String circleName = "NA", clientName = "NA";
        for (int i = 0; i < siteDetailArrayList.size(); i++) {
            if (siteDetailArrayList.get(i).getSiteId() == Long.parseLong(siteId)) {//siteDetailArrayList.get(i).getCustomerSiteId().equals(CustomerSiteId) may be change this
                siteName = siteDetailArrayList.get(i).getSiteName();
                // siteId = siteDetailArrayList.get(i).getSiteId() + "";
                clientName = siteDetailArrayList.get(i).getClient();
                circleName = siteDetailArrayList.get(i).getCircle();
            }
        }
        if (remarks.equals("")) {
            remarks = "NA";
        }
        if (spTask.getSelectedItem().toString().equals("CAD")) {
            spZone.setEnabled(false);
            spZone.setSelection(0);
            zoneId = 0;
        }
        // 745, 746 Material Status
        if (materialStatus == 1) {
            materialStatus = 745;// Material at site id
        } else if (materialStatus == 2) {
            materialStatus = 746;// Material moved id
        }
        //set status id value
        if (statusId == 1) {
            statusId = 737;// completed
        } else if (statusId == 2) {
            statusId = 738;// pending
        } else if (statusId == 3) {
            statusId = 739;// coustomer issue
        }


//get reason id from reason list
        long reasonId = 0;
        if (reason > 0) {
            reasonId = reasonList.get(reason - 1).getId();
        } else if (reason == -1) {
            reasonId = 0;
        }

        Location location = ASTUIUtil.getLocation(getContext());
        ActivitySheetModel sheetModel = new ActivitySheetModel();
        sheetModel.setActivityId(activityId);
        sheetModel.setNocEnggId(NocEnggId);
        sheetModel.setReasonId(String.valueOf(reasonId));
        sheetModel.setSiteId(siteId);
        sheetModel.setStatusId(String.valueOf(statusId));
        sheetModel.setUserId(userId);
        sheetModel.setZoneId(String.valueOf(zoneId));
        sheetModel.setTaskId(taskId);
        sheetModel.setMaterialStatus(String.valueOf(materialStatus));
        sheetModel.setRemarks(remarks);
        sheetModel.setNocEnggContact(NocEnggContact);
        sheetModel.setCircleName(circleName);
        sheetModel.setClientName(clientName);
        sheetModel.setIsPlanned(String.valueOf(isPlanned));
        sheetModel.setPlannedDate(String.valueOf(plannedDate));
        sheetModel.setPlanId(planId);
        if (location == null) {
            sheetModel.setLatitude("26.5");
            sheetModel.setLongitude("26.5");
        } else {
            sheetModel.setLatitude(String.valueOf(location.getLongitude()));
            sheetModel.setLongitude(String.valueOf(location.getLongitude()));
        }
        String otherExpenses = etOtherExpenses.getText().toString();
        String daysTaken = spDaysTaken.getSelectedItem().toString();
        if (otherExpenses.equals("")) {
            otherExpenses = "0";
        }
        sheetModel.setTaskName(spTask.getSelectedItem().toString());
        sheetModel.setActivityName(spActivities.getSelectedItem().toString());
        sheetModel.setStatusName(spStatus.getSelectedItem().toString());
        sheetModel.setSiteName(tvSiteName.getText().toString());
        //sheetModel.setSiteId(tvSiteId.getText().toString());
        sheetModel.setOtherExpenses(otherExpenses);
        sheetModel.setDaysTaken(daysTaken);
        if (pmChecklistFlag) {
            strIsPm = "1";
        } else {
            strIsPm = "0";
        }
        sheetModel.setIsPm(strIsPm);
        long submitTime = System.currentTimeMillis();
        sheetModel.setSubmitDateTime(submitTime);
        // atmDatabase.addFormData(arrSaveData, strIsPm);
        // atmDatabase.updatePendingActivity(planId);

        sendMessage(clientName, circleName, NocEnggContact);
        if (pmChecklistFlag) {
            saveActivityFormDataIntoDb(sheetModel);
            openPMCheckListFragment();
        } else {
            SharedPreferences prefs = getContext().getSharedPreferences("ReachedSitePreferences", Context.MODE_PRIVATE);
            if (prefs != null) {
                boolean reachedOrNotFlag = prefs.getBoolean("reachedOrNotFlag", false);
                String transitSiteId = prefs.getString("siteId", "");
                if (siteId.equals(transitSiteId)) {
                    if (reachedOrNotFlag) {
                        if (ASTUIUtil.isOnline(getContext())) {
                            activityFormDataServiceCall(sheetModel);
                        } else {
                            saveActivityFormDataIntoDb(sheetModel);
                            openPlannedActivityListTabScreen();
                        }
                    } else {
                        reachedSiteFirstAlertMessage();
                    }
                } else {
                    reachedSiteFirstAlertMessage();
                }
            }

        }

    }

    //save activity data into db
    private void saveActivityFormDataIntoDb(ActivitySheetModel sheetModel) {
        String activityData = new Gson().toJson(sheetModel);
        ContentLocalData localData = new ContentLocalData();
        localData.setPlanId(planId);
        localData.setActivityFormData(activityData);
        atmdbHelper.upsertActivtyFormData(localData);
        ASTUIUtil.showToast("Activity Data is Saved Locally");
    }

    public void openPMCheckListFragment() {
        PMCheckLIstFragment pmCheckLIstFragment = new PMCheckLIstFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "PM CheckList");
        bundle.putString("PlanId", planId);
        bundle.putString("activityId", activityId);
        bundle.putString("siteId", siteId);
        getHostActivity().updateFragment(pmCheckLIstFragment, bundle);
    }

    //send message
    private void sendMessage(String clientName, String circleName, String NocEnggContact) {
        String currentDateandTime = DateFormat.getDateTimeInstance().format(new Date());
        String nocEnggName = "";
        if (spNOC.getSelectedItemPosition() > 1) {
            nocEnggName = spNOC.getSelectedItem().toString();
        }
        String messageBody = currentDateandTime + "  " + userName + "  " + nocEnggName + "  " + siteName +
                "  " + circleName + " " + clientName;

        if (spNOC.getSelectedItemPosition() == 1) {
            if (spTask.getSelectedItem().toString().equalsIgnoreCase("Project")) {
                ASTUIUtil.SendMessage(getContext(), "9540994567", messageBody);
            } else {
                ASTUIUtil.SendMessage(getContext(), "8882412950", messageBody);
            }
        } else if (spNOC.getSelectedItemPosition() > 1) {
            ASTUIUtil.SendMessage(getContext(), NocEnggContact, messageBody);
        }
    }

    public void genrateUnsyncedList() {
        unsyncedDialog.setContentView(R.layout.activity_transit_entries);
        unsyncedDialog.setTitle("Unsynced Entries");
        unsyncedDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        unsyncedDialog.setCanceledOnTouchOutside(false);
        ListView lvTransit;
        Button btnSyncData;
        ArrayList<ActivitySheetModel> activityFormList = new ArrayList<>();
        ArrayList<ContentLocalData> contentLocalData = atmdbHelper.getAllActivtyFormData();
        lvTransit = unsyncedDialog.findViewById(R.id.lvTransit);
        btnSyncData = unsyncedDialog.findViewById(R.id.btnSyncData);
        Button cancel = unsyncedDialog.findViewById(R.id.cancel);
        if (contentLocalData != null && contentLocalData.size() > 0) {
            for (int i = 0; i < contentLocalData.size(); i++) {
                String activityFormStr = contentLocalData.get(i).getActivityFormData();
                if (activityFormStr != null) {
                    ActivitySheetModel activityData = new Gson().fromJson(activityFormStr, new TypeToken<ActivitySheetModel>() {
                    }.getType());
                    activityFormList.add(activityData);
                }
            }
            lvTransit.setAdapter(new UnsyncedActivityFormAdapter(getContext(), activityFormList));
        }
        btnSyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsyncedDialog.dismiss();
                if (ASTUIUtil.isOnline(getContext())) {
                    for (ActivitySheetModel sheetModel : activityFormList) {
                        activityFormDataServiceCall(sheetModel);
                    }
                    ASTUIUtil.showToast("Activity Form Data Syncing with server.");
                } else {
                    ASTUIUtil.showToast(Contants.OFFLINE_MESSAGE);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsyncedDialog.dismiss();
            }
        });
        unsyncedDialog.show();
    }

    public void generateDaRateChart() {
        daRateChartDialog.setContentView(R.layout.da_rate_chart);
        daRateChartDialog.setTitle("Hotel Expense Chart");
        daRateChartDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        daRateChartDialog.setCanceledOnTouchOutside(false);
        Button btnClose;
        btnClose = (Button) daRateChartDialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daRateChartDialog.dismiss();
            }
        });

        daRateChartDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgRefresh) {
            if (ASTUIUtil.isOnline(getContext())) {
                getSiteSearchData();
                getActivityDropdownList();//get all task
                getNocEnggListData();
                getEquipListData();
            } else {
                setNocEnggAdapter();
                ASTUIUtil.showToast(Contants.OFFLINE_MESSAGE);
            }
        } else if (view.getId() == R.id.btnSyncData) {
            ArrayList<ContentLocalData> contentLocalData = atmdbHelper.getAllActivtyFormData();
            if (contentLocalData != null && contentLocalData.size() > 0) {
                genrateUnsyncedList();
            } else {
                btnSyncData.setVisibility(View.GONE);
                ASTUIUtil.showToast("No Activity form data Pending");
            }
        } else if (view.getId() == R.id.btnsave) {
            checkAllDataValidation();
        }

    }

    //---------------------Calling Web Service to get Equipment List Data--------------------------
    public void getEquipListData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL_API + Contants.EQUIPMENT_LIST_URL;
        serviceCaller.callgetEquipListDataServices(serviceURL, new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                } else {

                }
                _progrssBar.dismiss();
            }
        });
    }

    //check all validation
    private void checkAllDataValidation() {
        String siteName = tvSiteName.getText().toString();
        String custSiteId = tvSiteId.getText().toString();
        int zoneId = spZone.getSelectedItemPosition();
        int statusId = spStatus.getSelectedItemPosition();
        int reason = spReason.getSelectedItemPosition();
        String remarks = etRemarks.getText().toString();
        int materialStatus = spMaterialStatus.getSelectedItemPosition();
        //geting eng details
        String NocEnggId = null;
        String NocEnggContact = null;
        if (spNOC.getSelectedItemPosition() == 1) {
            NocEnggId = "0";
        } else {
            if (nocEnggList != null && nocEnggList.size() > 0 && spNOC.getSelectedItemPosition() > 1) {
                NocEnggId = nocEnggList.get(spNOC.getSelectedItemPosition() - 2).getNocEngId() + "";
                NocEnggContact = nocEnggList.get(spNOC.getSelectedItemPosition() - 2).getContactNo();
            }
        }

        if (siteName.equalsIgnoreCase("")) {
            ASTUIUtil.showToast("Please Select Site Name");
        } else if (siteId.equalsIgnoreCase("")) {
            ASTUIUtil.showToast("Please Select Site ID");
        } else if (spNOC.getSelectedItemPosition() == 0) {
            ASTUIUtil.showToast("Please Select NOC Engineer");
        } else if (spTask.getSelectedItemPosition() == 0) {
            ASTUIUtil.showToast("Please Select Task");
        } else if (spActivities.getSelectedItemPosition() == 0) {
            ASTUIUtil.showToast("Please Select Activity");
        } else if (spNOC.getSelectedItemPosition() == 0) {
            ASTUIUtil.showToast("Please Select NOC Engineer");
            ASTUIUtil.showToast("Site ID not found, please press Refresh button");
        } else if (NocEnggId == null || NocEnggId == "" || NocEnggId.equalsIgnoreCase("--Select Engineer--")) {
            ASTUIUtil.showToast("Please Select Correct Engg Name");
        } else if (materialStatus == 0) {
            ASTUIUtil.showToast("Please Select Material Status");
        } else if (statusId == 0) {
            ASTUIUtil.showToast("Please Select Status");
        } else if (statusId == 2 && reason == 0) {
            ASTUIUtil.showToast("Please Select Reason");
        } else {
            try {
                SaveDataToServer(NocEnggId, NocEnggContact, remarks, zoneId,
                        materialStatus, statusId, reason);
            } catch (ParseException e) {
                e.printStackTrace();
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
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        serviceCaller.CallCommanServiceMethod(serviceURL, "activityFormDataServiceCall", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseActivityFormData(result, sheetModel.getPlanId());
                } else {
                    ASTUIUtil.showToast("Server Side error");
                }
                _progrssBar.dismiss();
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
                   /* for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                    }*/
                    atmdbHelper.deleteActivtyFormDataByPlanId(savePlanId);
                    ASTUIUtil.showToast("Activity Form Data Saved on Server");
                    openPlannedActivityListTabScreen();
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

    //open openPlannedActivityListTabfragment screen
    private void openPlannedActivityListTabScreen() {
        //clear reachedSiteprefs after click on reached site for submit activity form
        SharedPreferences reachedSiteprefs = getContext().getSharedPreferences("ReachedSitePreferences", Context.MODE_PRIVATE);
        reachedSiteprefs.edit().clear().commit();
        ASTUIUtil.savePlanDetail(getContext(), siteId, true);// for transit left side
        PlannedActivityListTabFragment plannedActivityListTabFragment = new PlannedActivityListTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "Activity Monitor");
        getHostActivity().updateFragment(plannedActivityListTabFragment, bundle);
    }

    // reached site first alert (without reached site you can not fill activity form)
    public void reachedSiteFirstAlertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        openTransitScreenScreen();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please first Reached Site in Transit screen after that you can fill Activity form!")
                .setPositiveButton("Fill Form", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    //open transit screen
    private void openTransitScreenScreen() {
        TransitFragment transitFragment = new TransitFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "Transit");
        getHostActivity().updateFragment(transitFragment, bundle);
    }

}
