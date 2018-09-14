package com.atm.ast.astatm.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.TodayPlanListAdapter;
import com.atm.ast.astatm.adapter.UnsyncedTransitAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.model.TransitDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.runtimepermission.PermissionUtils;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.CustomDialog;
import com.atm.ast.astatm.utils.GPSTracker;
import com.atm.ast.astatm.utils.SiteNotFoundPopup;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TransitFragment extends MainFragment {
    FloatingActionButton btnSyncData;
    public Context context;
    TextView btnStartHome, btnReachedSite, btnLeftSite, btnReachedHome;
    CardView CardbtnReachedHome, CardbtnLeftSite, CardbtnReachedSite, CardbtnStartHome;
    Button btnAddSiteData;
    TextView tvSelectedSite, tvRemainingDistance;
    TextView tvCurrentDate;
    String networkProvider;
    String serviceURL;
    public double lat = 0.0000, lon = 0.0000;
    ProgressDialog siteprogressbar;
    ArrayList<Data> todayPlannedSiteArrayList;

    SharedPreferences pref;
    String userId;
    String userName = "";
    Location location;
    String selectedButtonType = "";
    String selectedSite;

    public String TRANSIT_PREFERENCES = "TransitPrefs";
    SharedPreferences transitsharedpreferences;
    ATMDBHelper atmdbHelper;
    ImageView startHomeTick, rechedSiteTick, leftSiteTick, reachedHomeTick;
    ASTProgressBar _progrssBar;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_transit;
    }

    @Override
    protected void loadView() {
        context = getContext();
        btnStartHome = findViewById(R.id.btnStartHome);
        btnReachedSite = findViewById(R.id.btnReachedSite);
        btnLeftSite = findViewById(R.id.btnLeftSite);
        btnReachedHome = findViewById(R.id.btnReachedHome);
        btnAddSiteData = findViewById(R.id.btnAddSiteData);

        CardbtnReachedHome = findViewById(R.id.CardbtnReachedHome);
        CardbtnLeftSite = findViewById(R.id.CardbtnLeftSite);
        CardbtnReachedSite = findViewById(R.id.CardbtnReachedSite);
        CardbtnStartHome = findViewById(R.id.CardbtnStartHome);

        btnSyncData = findViewById(R.id.btnSyncData);
        tvSelectedSite = findViewById(R.id.tvSelectedSiteName);
        //tvSelectedSite.setVisibility(View.GONE);
        tvRemainingDistance = findViewById(R.id.tvRemainingDistance);
        tvRemainingDistance.setVisibility(View.GONE);
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        startHomeTick = findViewById(R.id.startHomeTick);
        rechedSiteTick = findViewById(R.id.rechedSiteTick);
        leftSiteTick = findViewById(R.id.leftSiteTick);
        reachedHomeTick = findViewById(R.id.reachedHomeTick);

    }

    @Override
    protected void setClickListeners() {
        btnAddSiteData.setOnClickListener(this);
        btnStartHome.setOnClickListener(this);
        btnReachedSite.setOnClickListener(this);
        btnLeftSite.setOnClickListener(this);
        btnReachedHome.setOnClickListener(this);
        btnAddSiteData.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getUserInfo();
        atmdbHelper = new ATMDBHelper(getContext());
        String appversionName = ASTUIUtil.getAppVersionName(context);
        if (appversionName != null) {
            String formattedDate = ASTUIUtil.getCurrentDate();
            tvCurrentDate.setText(formattedDate + " : " + appversionName);
        }
        todayPlannedSiteArrayList = new ArrayList<>();
        deletePreviousDateSiteFromDB();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAddSiteData) {
            FillSiteAddressFragment fillSiteAddressFragment = new FillSiteAddressFragment();
            Bundle bundle = new Bundle();
            bundle.putString("headerTxt", "Fill Site Data");
            getHostActivity().updateFragment(fillSiteAddressFragment, bundle);
        } else if (view.getId() == R.id.btnStartHome) {
            if (ASTUIUtil.checkGpsEnabled(context)) {
                selectedButtonType = "1";
                getSiteIdPopup();//open site popup
            }
        } else if (view.getId() == R.id.btnLeftSite) {
            getLocation();
            LeftSiteAlertMessage();
        } else if (view.getId() == R.id.btnReachedHome) {
            if (ASTUIUtil.checkGpsEnabled(context)) {
                getLocation();
                String siteId = transitsharedpreferences.getString("SITE_ID", "");

                if (siteId == null || siteId.equals("")) {
                    Toast.makeText(context, "Please Press Reached Site first.", Toast.LENGTH_SHORT).show();
                } else {
                    checkAndSaveSourceAndDestinationLocation();
                    try {
                        generateTravelExpensePopup("ReachedHome");
                    } catch (IOException e) {
                        // e.printStackTrace();
                    }
                    selectedButton();
                }
            }
        } else if (view.getId() == R.id.btnSyncData) {
            List<TransitDataModel> transitDataArrayList = atmdbHelper.getTransitData("1");
            if (transitDataArrayList != null && transitDataArrayList.size() > 0) {
                showUnsyncedTransitDataList(transitDataArrayList);//synce save data
            } else {
                ASTUIUtil.showToast("No Pending Entries");
            }
        } else if (view.getId() == R.id.btnReachedSite) {
            if (ASTUIUtil.checkGpsEnabled(context)) {
                getLocation();
                checkAndSaveSourceAndDestinationLocation();
                try {
                    generateTravelExpensePopup("ReachedSite");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //if previous date site exist in Db then it will delete
    private void deletePreviousDateSiteFromDB() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String currentDateStr = formatter.format(date);
        String preDateStr = ASTUIUtil.getCurrentDateFromPre(context);
        if (preDateStr != null && !preDateStr.equals("")) {
            try {
                Date currentDate = formatter.parse(currentDateStr);
                Date preDate = formatter.parse(preDateStr);
                if (currentDate.after(preDate)) {
                    // Toast.makeText(context, "Pre Date " + preDateStr + "----current Date " + currentDateStr, Toast.LENGTH_SHORT).show();
                    atmdbHelper.deleteAllRows("todaySitePlan");//delete previous date all site
                    ASTUIUtil.saveCurrentDateToPre(currentDateStr, context);
                }
            } catch (ParseException e) {
                //e.printStackTrace();
            }
        } else {
            ASTUIUtil.saveCurrentDateToPre(currentDateStr, context);
        }
    }

    //get user info from share
    private void getUserInfo() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        userId = pref.getString("userId", "");
        userName = pref.getString("userName", "");

        transitsharedpreferences = getContext().getSharedPreferences(TRANSIT_PREFERENCES, Context.MODE_PRIVATE);
        final String selectedButton = transitsharedpreferences.getString("SELECTED_BUTTON", "");
        selectedSite = transitsharedpreferences.getString("SITE_NAME", "");
        if (!selectedSite.equals("")) {
            tvSelectedSite.setText(selectedSite + "(" + transitsharedpreferences.getString("SITE_ID", "") + ")");
            tvRemainingDistance.setText("Distance From Site - 10 KM");
        }
        selectedButtonType = selectedButton;
        getTodayPlannedActivityData();
        selectedButton();
    }

    //open site popup to show plan site data
    public void getSiteIdPopup() {
        Dialog siteIdPopupDialog = new Dialog(getContext());
        siteIdPopupDialog.setContentView(R.layout.select_site_popup);
        siteIdPopupDialog.setTitle("Select Site");
        siteIdPopupDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        siteIdPopupDialog.setCanceledOnTouchOutside(false);

        ListView lvPlannedActivities = (ListView) siteIdPopupDialog.findViewById(R.id.lvPlannedActivities);
        TextView tvTitlePlannedActivity = (TextView) siteIdPopupDialog.findViewById(R.id.tvTitlePlannedActivity);
        ImageView imgRefresh = (ImageView) siteIdPopupDialog.findViewById(R.id.imgRefresh);
        TextView noSiteFound = (TextView) siteIdPopupDialog.findViewById(R.id.noSiteFound);
        todayPlannedSiteArrayList = atmdbHelper.getAllTodaySiteListData();
        siteIdPopupDialog.show();
        if (todayPlannedSiteArrayList != null && todayPlannedSiteArrayList.size() > 0) {
            noSiteFound.setVisibility(View.GONE);
            lvPlannedActivities.setVisibility(View.VISIBLE);
            lvPlannedActivities.setAdapter(new TodayPlanListAdapter(context, todayPlannedSiteArrayList));
            tvTitlePlannedActivity.setVisibility(View.VISIBLE);
        } else {
            noSiteFound.setVisibility(View.VISIBLE);
            lvPlannedActivities.setVisibility(View.GONE);
            siteIdPopupDialog.dismiss();

            SiteNotFoundPopup siteNotFoundPopup = new SiteNotFoundPopup();
            PopupWindow siteNotFoundPopupWindow = new PopupWindow(context);
            Toast.makeText(context, "Site ID not found. please press Refresh button Or Contact NOC", Toast.LENGTH_SHORT).show();
            siteNotFoundPopup.getSitePopup(siteNotFoundPopupWindow, context, "Enter Site Id for sending message to NOC.", "Send", userId, "SMS", userName);

        }
        lvPlannedActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (todayPlannedSiteArrayList != null && todayPlannedSiteArrayList.size() > 0) {
                    String siteId = todayPlannedSiteArrayList.get(position).getSiteId() + "";
                    SharedPreferences.Editor editor = transitsharedpreferences.edit();
                    editor.putString("SITE_NAME", todayPlannedSiteArrayList.get(position).getSiteName());
                    editor.putString("SITE_ID", siteId);
                    editor.putString("SITE_NUM_ID", siteId);
                    editor.commit();

                    tvSelectedSite.setText(todayPlannedSiteArrayList.get(position).getSiteName());
                    String transitAddress = "Unable to connect";
                    transitAddress = getAddressThroughLatLong(lat, lon, "short");
                    if (!siteId.equals("") && selectedButtonType.equals("1")) {
                        getLocation();
                        saveTransitDataIntoDB(transitAddress, siteId, "0", "0", "0", "0", "NA", "0", "0");
                        editor.putString("SOURCE_LAT", String.valueOf(lat));
                        editor.putString("SOURCE_LONG", String.valueOf(lon));
                        editor.putString("DESTINATION_LAT", "");
                        editor.putString("DESTINATION_LONG", "");
                        editor.commit();
                    } else {
                        selectedButtonType = "1";//whene ever change site done
                    }
                    selectedButton();
                }
                siteIdPopupDialog.dismiss();
            }
        });

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siteIdPopupDialog.dismiss();
                getTodayPlannedActivityData();
            }
        });

    }

   /* //save transit data into DB
    private void saveTransitDataIntoDB(String transitAddress, String siteId, String totalTravelCost, String totalDistance, String strActualKms, String strActualTravelCost, String strRemarks, String strHotelExp, String strActualHotelExp) {
        //ArrayList<TransitDataModel> arrTransitData = new ArrayList<>();
        TransitDataModel transitDataModel = new TransitDataModel();
        transitDataModel.setSiteId(siteId);
        transitDataModel.setUserId(userId);
        transitDataModel.setDateTime(String.valueOf(System.currentTimeMillis()));
        transitDataModel.setType(selectedButtonType);
        transitDataModel.setLatitude(String.valueOf(lat));
        transitDataModel.setLongitude(String.valueOf(lon));
        transitDataModel.setCalculatedAmount(totalTravelCost);
        transitDataModel.setCalcilatedDistance(totalDistance);
        transitDataModel.setAddress(transitAddress + " - " + networkProvider);
        transitDataModel.setActualKms(strActualKms);
        transitDataModel.setActualAmt(strActualTravelCost);
        transitDataModel.setRemarks(strRemarks);
        transitDataModel.setHotelExpense(strHotelExp);
        transitDataModel.setActualHotelExpense(strActualHotelExp);

        //arrTransitData.add(transitDataModel);
        atmdbHelper.upsertTransitData(transitDataModel);//save in data base

        if (selectedButtonType.equals("4")) {
            atmdbHelper.updateTodaySiteTransitComplete(1, siteId);//update today planed site status so that it will show only uncomplete site
        }

        Toast.makeText(getContext(), "Your Transit is saved", Toast.LENGTH_LONG).show();
    }*/

    //open Travel Expense Popup and set expense related data
    public void generateTravelExpensePopup(String buttonClick) throws IOException {
        Dialog travelClaimDialog = new Dialog(getContext());
        travelClaimDialog.setContentView(R.layout.travel_expense_form_popup);
        travelClaimDialog.setTitle("Travel Allowance");
        travelClaimDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        travelClaimDialog.setCanceledOnTouchOutside(false);

        Button btnSubmit, btnChangeSite;
        final TextView tvTotalDistance, tvFromCity, tvTotalTravelCost, tvBaseToSiteDistance;
        final EditText etActualTravelCost, etActualDistance, etRemarks, etActualKms, etHotelExp, etActualHotelExp;
        ImageView btnQue;
        final LinearLayout llHotelExp, llActualHotelExp;
        final CheckBox chkHotelExpense;
        final LinearLayout llHotelExpChkBox, llBaseToSiteDistance;
        ImageView btnHotelExpRateChart;

        btnSubmit = travelClaimDialog.findViewById(R.id.btnSubmit);
        btnChangeSite = travelClaimDialog.findViewById(R.id.btnChangeSite);
        tvTotalDistance = travelClaimDialog.findViewById(R.id.tvTotalDistance);
        tvFromCity = travelClaimDialog.findViewById(R.id.tvFromCity);
        tvTotalTravelCost = travelClaimDialog.findViewById(R.id.tvTotalTravelCost);
        tvBaseToSiteDistance = travelClaimDialog.findViewById(R.id.tvBaseToSiteDistance);
        etActualTravelCost = travelClaimDialog.findViewById(R.id.etActualTravelCost);
        etActualDistance = travelClaimDialog.findViewById(R.id.etActualKms);
        etRemarks = travelClaimDialog.findViewById(R.id.etRemarks);
        etActualKms = travelClaimDialog.findViewById(R.id.etActualKms);
        etHotelExp = travelClaimDialog.findViewById(R.id.etHotelExp);
        etActualHotelExp = travelClaimDialog.findViewById(R.id.etActualHotelExp);
        btnHotelExpRateChart = travelClaimDialog.findViewById(R.id.btnHotelExpRateChart);
        llBaseToSiteDistance = travelClaimDialog.findViewById(R.id.llBaseToSiteDistance);
        llHotelExpChkBox = travelClaimDialog.findViewById(R.id.llHotelExpChkBox);
        llHotelExp = travelClaimDialog.findViewById(R.id.llHotelExp);
        llActualHotelExp = travelClaimDialog.findViewById(R.id.llActualHotelExp);
        chkHotelExpense = travelClaimDialog.findViewById(R.id.chkHotelExpense);

        btnHotelExpRateChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    generateHotelExpenseRateChartPopup();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
        });
        btnChangeSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSiteIdPopup();//open site popup
                travelClaimDialog.dismiss();
            }
        });
        //for button reached home
        if (buttonClick.equals("ReachedHome")) {
            btnChangeSite.setVisibility(View.GONE);//show only in reached site
            llHotelExpChkBox.setVisibility(View.VISIBLE);
            //tvBaseToSiteDistance.setVisibility(View.VISIBLE);
            String siteId = transitsharedpreferences.getString("SITE_ID", "");

            if (siteId != null && !siteId.equals("")) {
                SharedPreferences.Editor editor = transitsharedpreferences.edit();
                Data siteData = atmdbHelper.getPopulateSiteListDataBySiteID(Long.parseLong(siteId));
                if (siteData != null) {
                    editor.putString("SITE_BASE_DISTANCE", siteData.getBaseDistance() + "");
                } else {
                    editor.putString("SITE_BASE_DISTANCE", "0");
                }
                editor.commit();
            }
        } else {
            btnChangeSite.setVisibility(View.VISIBLE);//show only in reached site
        }
        tvBaseToSiteDistance.setText(transitsharedpreferences.getString("SITE_BASE_DISTANCE", ""));
        chkHotelExpense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkHotelExpense.isChecked()) {
                    llHotelExp.setVisibility(View.VISIBLE);
                    llActualHotelExp.setVisibility(View.VISIBLE);
                    llBaseToSiteDistance.setVisibility(View.GONE);
                } else {
                    llHotelExp.setVisibility(View.GONE);
                    llActualHotelExp.setVisibility(View.GONE);
                    llBaseToSiteDistance.setVisibility(View.VISIBLE);
                }
            }
        });

        btnQue = (ImageView) travelClaimDialog.findViewById(R.id.btnQue);
        btnQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    generateTravelExpenseRateChartPopup();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        String currentAddress = "Unable to connect";
        currentAddress = getAddressThroughLatLong(lat, lon, "short");

        String finalFullAddress;
        if (currentAddress.equalsIgnoreCase("Unable to connect")) {
            finalFullAddress = "Unable to connect";
        } else {
            finalFullAddress = getAddressThroughLatLong(Double.parseDouble(transitsharedpreferences.getString("SOURCE_LAT", "")), Double.parseDouble(transitsharedpreferences.getString("SOURCE_LONG", "")), "");
        }
        setSourceAndDestinationAddress(currentAddress, tvTotalDistance, tvTotalTravelCost, tvFromCity);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             String strHotelExp = etHotelExp.getText().toString();
                                             String strActualHotelExp = etActualHotelExp.getText().toString();
                                             String strActualTravelCost = etActualTravelCost.getText().toString();
                                             String strActualKms = etActualKms.getText().toString();
                                             String strRemarks = "NA";
                                             strRemarks = etRemarks.getText().toString();
                                             if (strActualKms.equals("")) {
                                                 Toast.makeText(context, "Please Provide Actual KMs", Toast.LENGTH_SHORT).show();
                                             } else if (strActualTravelCost.equals("")) {
                                                 Toast.makeText(context, "Please Provide Actual Travel Cost", Toast.LENGTH_SHORT).show();
                                             } else {
                                                 if (etRemarks.equals("")) {
                                                     strRemarks = "NA";
                                                 }
                                                 if (strActualKms.equals("")) {
                                                     strActualKms = "0";
                                                 }
                                                 if (strHotelExp.equals("") || selectedButtonType.equals("2")) {
                                                     strHotelExp = "0";
                                                 }
                                                 if (strActualHotelExp.equals("")) {
                                                     strActualHotelExp = "0";
                                                 }
                                                 getLocation();
                                                 String siteId = transitsharedpreferences.getString("SITE_ID", "");
                                                 if (buttonClick.equals("ReachedSite")) {
                                                     selectedButtonType = "2";
                                                 }
                                                 if (buttonClick.equals("ReachedHome")) {
                                                     selectedButtonType = "4";
                                                     SharedPreferences.Editor editor = transitsharedpreferences.edit();
                                                     editor.putString("SITE_NAME", "");
                                                     editor.putString("SITE_ID", "");
                                                     editor.putString("SITE_NUM_ID", "");
                                                     editor.putString("SOURCE_LAT", "");
                                                     editor.putString("SOURCE_LONG", "");
                                                     editor.putString("DESTINATION_LAT", "");
                                                     editor.putString("DESTINATION_LONG", "");
                                                     editor.putString("SELECTED_BUTTON", "");//for restart transit
                                                     editor.commit();

                                                     TransitFragment transitFragment = new TransitFragment();
                                                     Bundle bundle = new Bundle();
                                                     bundle.putString("headerTxt", "Transit");
                                                     ApplicationHelper.application().getActivity().updateFragment(transitFragment, bundle);
                                                 }
                                                 String totalDistance = tvTotalDistance.getText().toString();
                                                 saveTransitDataIntoDB(finalFullAddress, siteId, tvTotalTravelCost.getText().toString(), totalDistance.substring(0, totalDistance.length() - 4), strActualKms, strActualTravelCost, strRemarks, strHotelExp, strActualHotelExp);
                                                 travelClaimDialog.dismiss();
                                                 selectedButton();
                                             }
                                         }
                                     }
        );
        travelClaimDialog.show();
    }

    //set Source And Destination Address in UI
    private void setSourceAndDestinationAddress(String currentAddress, TextView tvTotalDistance, TextView tvTotalTravelCost, TextView tvFromCity) {
        String destinationLocationLat = transitsharedpreferences.getString("DESTINATION_LAT", "");
        String destinationLocationLon = transitsharedpreferences.getString("DESTINATION_LONG", "");
        String sourceLocationLat = transitsharedpreferences.getString("SOURCE_LAT", "");
        String sourceLocationLon = transitsharedpreferences.getString("SOURCE_LONG", "");

        double distance = 0;
        distance = ASTUIUtil.getDistance(Double.parseDouble(sourceLocationLat), Double.parseDouble(sourceLocationLon),
                Double.parseDouble(destinationLocationLat), Double.parseDouble(destinationLocationLon), "K");

        String sourceAddress = "";
        if (currentAddress.equalsIgnoreCase("Unable to connect")) {
            sourceAddress = "Unable to connect";
        } else {
            sourceAddress = getAddressThroughLatLong(Double.parseDouble(sourceLocationLat), Double.parseDouble(sourceLocationLon), "locality");
        }

        String destinationAddress = "";
        if (currentAddress.equalsIgnoreCase("Unable to connect")) {
            destinationAddress = "Unable to connect";
        } else {
            destinationAddress = getAddressThroughLatLong(Double.parseDouble(destinationLocationLat), Double.parseDouble(destinationLocationLon), "locality");
        }

        tvTotalDistance.setText(String.valueOf((int) distance) + " Kms");
        double perKmCost = 0;

        if (distance <= 25) {
            perKmCost = 2;
        } else if (distance > 25 && distance <= 50) {
            perKmCost = 1.75;
        } else if (distance > 50 && distance <= 100) {
            perKmCost = 1.5;
        } else if (distance > 100 && distance <= 150) {
            perKmCost = 1.25;
        } else if (distance > 150) {
            perKmCost = 1.25;
        }

        /*tvTotalTravelCost.setText(String.valueOf((int) distance * 2));*/
        tvTotalTravelCost.setText(String.valueOf(perKmCost * distance));
        tvFromCity.setText(sourceAddress + " - " + destinationAddress);

    }

    //open hotel expense rate chart popup to show hotel expense related data
    public void generateHotelExpenseRateChartPopup() throws IOException {
        Dialog hotelClaimRateChartDialog = new Dialog(getContext());
        hotelClaimRateChartDialog.setContentView(R.layout.hotel_allowance_rate_chart);
        hotelClaimRateChartDialog.setTitle("Hotel Expense Chart");
        hotelClaimRateChartDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hotelClaimRateChartDialog.setCanceledOnTouchOutside(false);

        Button btnClose;
        btnClose = (Button) hotelClaimRateChartDialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotelClaimRateChartDialog.dismiss();
            }
        });

        hotelClaimRateChartDialog.show();
    }

    public void generateTravelExpenseRateChartPopup() throws IOException {
        Dialog travelClaimRateChartDialog = new Dialog(getContext());
        travelClaimRateChartDialog.setContentView(R.layout.travel_allowance_rate_chart);
        travelClaimRateChartDialog.setTitle("TA Rate Chart");
        travelClaimRateChartDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        travelClaimRateChartDialog.setCanceledOnTouchOutside(false);

        Button btnClose;
        btnClose = (Button) travelClaimRateChartDialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travelClaimRateChartDialog.dismiss();
            }
        });

        travelClaimRateChartDialog.show();
    }

    //get current location
    public void getLocation() {
        GPSTracker gpsTracker = new GPSTracker(context);
        location = gpsTracker.getLocation();

        if (location == null) {
            //Toast.makeText(TransitActivity.this, "Location Not Available", Toast.LENGTH_SHORT).show();
        } else {
            lat = location.getLatitude();
            lon = location.getLongitude();
            networkProvider = location.getProvider();
        }
    }

    //check destination Location save or not if saved then save source location as destination location and current location as sourse location. otherwise save current location as destination location
    private void checkAndSaveSourceAndDestinationLocation() {
        final String destinationLocationLat = transitsharedpreferences.getString("DESTINATION_LAT", "");
        final String destinationLocationLon = transitsharedpreferences.getString("DESTINATION_LONG", "");
        if (destinationLocationLat.equals("")) {
            SharedPreferences.Editor editor = transitsharedpreferences.edit();
            editor.putString("DESTINATION_LAT", String.valueOf(lat));
            editor.putString("DESTINATION_LONG", String.valueOf(lon));
            editor.commit();
        } else {
            SharedPreferences.Editor editor = transitsharedpreferences.edit();
            editor.putString("SOURCE_LAT", transitsharedpreferences.getString("DESTINATION_LAT", ""));
            editor.putString("SOURCE_LONG", transitsharedpreferences.getString("DESTINATION_LONG", ""));
            editor.putString("DESTINATION_LAT", String.valueOf(lat));
            editor.putString("DESTINATION_LONG", String.valueOf(lon));
            editor.putString("SITE_BASE_DISTANCE", "0");
            editor.commit();
        }
    }

    // left site button click and save all info into DB
    public void LeftSiteAlertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        checkAndSaveSourceAndDestinationLocation();
                        String transitAddress = getAddressThroughLatLong(lat, lon, "short");
                        String siteId = transitsharedpreferences.getString("SITE_ID", "");
                        if (siteId.equals("") || siteId.equals(null)) {
                            Toast.makeText(context, "Please Select a Site", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedButtonType = "3";
                            saveTransitDataIntoDB(transitAddress, siteId, "0", "0", "0", "0", "NA", "0", "0");
                            selectedButton();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure You Want to Submit?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    //get address through lat long
    private String getAddressThroughLatLong(double lat, double lon, String addresType) {
        Location locationTransit = new Location("");
        locationTransit.setLatitude(lat);
        locationTransit.setLongitude(lon);
        String address = "NA";
        try {
            address = ASTUIUtil.getLocationAddress(locationTransit, context, addresType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    //show unsynced transit data which is store in DB
    public void showUnsyncedTransitDataList(List<TransitDataModel> transitDataArrayList) {
        Dialog unsyncedDialog = new Dialog(getContext());
        unsyncedDialog.setContentView(R.layout.activity_transit_entries);
        unsyncedDialog.setTitle("Unsynced Transit Entries");
        unsyncedDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        unsyncedDialog.setCanceledOnTouchOutside(false);
        ListView lvTransit = (ListView) unsyncedDialog.findViewById(R.id.lvTransit);
        Button btnSyncData = (Button) unsyncedDialog.findViewById(R.id.btnSyncData);
        Button cancel = (Button) unsyncedDialog.findViewById(R.id.cancel);

        lvTransit.setAdapter(new UnsyncedTransitAdapter(context,
                transitDataArrayList));
        btnSyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncSaveDataIntoServer(transitDataArrayList);
                unsyncedDialog.dismiss();
                Toast.makeText(context, "Syncing with server.", Toast.LENGTH_SHORT).show();
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

    //show button which is selected
    private void selectedButton() {
        SharedPreferences.Editor editor = transitsharedpreferences.edit();
        switch (selectedButtonType) {
            case "1":
                btnStartHome.setBackgroundColor(Color.parseColor("#FFA500"));
                btnReachedSite.setBackgroundColor(Color.parseColor("#666699"));
                btnLeftSite.setBackgroundColor(Color.parseColor("#666699"));
                btnReachedHome.setBackgroundColor(Color.parseColor("#666699"));

                CardbtnReachedHome.setCardBackgroundColor(Color.parseColor("#666699"));
                CardbtnLeftSite.setCardBackgroundColor(Color.parseColor("#666699"));
                CardbtnReachedSite.setCardBackgroundColor(Color.parseColor("#666699"));
                CardbtnStartHome.setCardBackgroundColor(Color.parseColor("#FFA500"));
                startHomeTick.setVisibility(View.VISIBLE);
                rechedSiteTick.setVisibility(View.GONE);
                leftSiteTick.setVisibility(View.GONE);
                reachedHomeTick.setVisibility(View.GONE);

                editor.putString("SELECTED_BUTTON", "1");
                editor.commit();

                btnReachedHome.setEnabled(false);
                btnReachedSite.setEnabled(true);
                btnLeftSite.setEnabled(false);
                btnStartHome.setEnabled(false);
                break;
            case "2":
                btnStartHome.setBackgroundColor(Color.parseColor("#FFA500"));
                btnReachedSite.setBackgroundColor(Color.parseColor("#078f4b"));
                btnLeftSite.setBackgroundColor(Color.parseColor("#666699"));
                btnReachedHome.setBackgroundColor(Color.parseColor("#666699"));

                CardbtnReachedHome.setCardBackgroundColor(Color.parseColor("#666699"));
                CardbtnLeftSite.setCardBackgroundColor(Color.parseColor("#666699"));
                CardbtnReachedSite.setCardBackgroundColor(Color.parseColor("#078f4b"));
                CardbtnStartHome.setCardBackgroundColor(Color.parseColor("#FFA500"));

                startHomeTick.setVisibility(View.VISIBLE);
                rechedSiteTick.setVisibility(View.VISIBLE);
                leftSiteTick.setVisibility(View.GONE);
                reachedHomeTick.setVisibility(View.GONE);

                editor.putString("SELECTED_BUTTON", "2");
                editor.commit();
                btnReachedHome.setEnabled(false);
                btnReachedSite.setEnabled(false);
                btnLeftSite.setEnabled(true);
                btnStartHome.setEnabled(false);

                break;
            case "3":
                btnStartHome.setBackgroundColor(Color.parseColor("#FFA500"));
                btnReachedSite.setBackgroundColor(Color.parseColor("#078f4b"));
                btnLeftSite.setBackgroundColor(Color.parseColor("#1E90FF"));
                btnReachedHome.setBackgroundColor(Color.parseColor("#666699"));

                CardbtnReachedHome.setCardBackgroundColor(Color.parseColor("#666699"));
                CardbtnLeftSite.setCardBackgroundColor(Color.parseColor("#1E90FF"));
                CardbtnReachedSite.setCardBackgroundColor(Color.parseColor("#078f4b"));
                CardbtnStartHome.setCardBackgroundColor(Color.parseColor("#FFA500"));

                startHomeTick.setVisibility(View.VISIBLE);
                rechedSiteTick.setVisibility(View.VISIBLE);
                leftSiteTick.setVisibility(View.VISIBLE);
                reachedHomeTick.setVisibility(View.GONE);

                editor.putString("SELECTED_BUTTON", "3");
                editor.commit();

                btnReachedHome.setEnabled(true);
                btnReachedSite.setEnabled(true);
                btnLeftSite.setEnabled(false);
                btnStartHome.setEnabled(false);

                break;
            case "4":
                btnStartHome.setBackgroundColor(Color.parseColor("#FFA500"));
                btnReachedSite.setBackgroundColor(Color.parseColor("#078f4b"));
                btnLeftSite.setBackgroundColor(Color.parseColor("#1E90FF"));
                btnReachedHome.setBackgroundColor(Color.parseColor("#FF4500"));

                CardbtnReachedHome.setCardBackgroundColor(Color.parseColor("#FF4500"));
                CardbtnLeftSite.setCardBackgroundColor(Color.parseColor("#1E90FF"));
                CardbtnReachedSite.setCardBackgroundColor(Color.parseColor("#078f4b"));
                CardbtnStartHome.setCardBackgroundColor(Color.parseColor("#FFA500"));
                btnReachedHome.setEnabled(false);
                btnReachedSite.setEnabled(false);
                btnLeftSite.setEnabled(false);
                btnStartHome.setEnabled(true);

                startHomeTick.setVisibility(View.VISIBLE);
                rechedSiteTick.setVisibility(View.VISIBLE);
                leftSiteTick.setVisibility(View.VISIBLE);
                reachedHomeTick.setVisibility(View.VISIBLE);

                //editor.putString("SELECTED_BUTTON", "4");
                //editor.commit();
                break;
            default:
                btnStartHome.setBackgroundColor(Color.parseColor("#666699"));
                btnReachedSite.setBackgroundColor(Color.parseColor("#666699"));
                btnLeftSite.setBackgroundColor(Color.parseColor("#666699"));
                btnReachedHome.setBackgroundColor(Color.parseColor("#666699"));

                CardbtnReachedHome.setCardBackgroundColor(Color.parseColor("#666699"));
                CardbtnLeftSite.setCardBackgroundColor(Color.parseColor("#666699"));
                CardbtnReachedSite.setCardBackgroundColor(Color.parseColor("#666699"));
                CardbtnStartHome.setCardBackgroundColor(Color.parseColor("#666699"));
                btnReachedSite.setEnabled(false);
                btnLeftSite.setEnabled(false);
                btnReachedHome.setEnabled(false);
                btnStartHome.setEnabled(true);

                startHomeTick.setVisibility(View.GONE);
                rechedSiteTick.setVisibility(View.GONE);
                leftSiteTick.setVisibility(View.GONE);
                reachedHomeTick.setVisibility(View.GONE);
                break;
        }
    }

    private void getTodayPlannedActivityData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = "";
        serviceURL = Contants.BASE_URL + Contants.TODAY_PLAN_LIST_URL;
        serviceURL += "&uid=" + userId + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getTodayPlannedActivityData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveTodayPlannedActivityData(result);
                } else {
                    ASTUIUtil.showToast("No Plan find!");
                }
                _progrssBar.dismiss();
            }
        });
    }

    //Parse and Save TodayPlannedActivityData
    public void parseandsaveTodayPlannedActivityData(String result) {
        if (result != null) {
            ServiceContentData contentData = new Gson().fromJson(result, ServiceContentData.class);
            if (contentData != null) {
                if (contentData.getStatus() == 2) {
                    if (contentData.getData() != null) {
                        for (Data data : contentData.getData()) {
                            atmdbHelper.upsertTodaySitePlanData(data);
                        }
                    }

                }

            }
        }
    }

    //sync save transit data into server
    private void syncSaveDataIntoServer(List<TransitDataModel> transitDataArrayList) {
        for (TransitDataModel dataModel : transitDataArrayList) {
            String userId = dataModel.getUserId();
            String siteId = dataModel.getSiteId();
            String transitType = dataModel.getType();
            String date = dataModel.getDateTime();
            String lat = dataModel.getLatitude();
            String lon = dataModel.getLongitude();
            String calculatedDistance = dataModel.getCalcilatedDistance();
            String calculatedAmount = dataModel.getCalculatedAmount();
            String transitAddressStr = dataModel.getAddress();
            String strActualKms = dataModel.getActualKms();
            String strActualTravelCost = dataModel.getActualAmt();
            String remarks = dataModel.getRemarks();
            String hotelExpenses = dataModel.getHotelExpense();
            hotelExpenses = hotelExpenses.replace("Rs.", "0");
            String actualHotelExpenses = dataModel.getActualHotelExpense();

            saveTransitData(userId, siteId, transitType, Double.parseDouble(lat), Double.parseDouble(lon), calculatedDistance, calculatedAmount, transitAddressStr, strActualKms, strActualTravelCost, remarks, hotelExpenses, actualHotelExpenses);
        }
    }

    //save data into db
    private void saveTransitIntoDB(String userId, String siteId, String selectedButtonType, double lat, double lon, String totalDistance, String totalTravelCost, String transitAddress, String strActualKms, String strActualTravelCost, String strRemarks, String strHotelExp, String strActualHotelExp) {
        TransitDataModel transitDataModel = new TransitDataModel();
        transitDataModel.setSiteId(siteId);
        transitDataModel.setUserId(userId);
        transitDataModel.setDateTime(String.valueOf(System.currentTimeMillis()));
        transitDataModel.setType(selectedButtonType);
        transitDataModel.setLatitude(String.valueOf(lat));
        transitDataModel.setLongitude(String.valueOf(lon));
        transitDataModel.setCalculatedAmount(totalTravelCost);
        transitDataModel.setCalcilatedDistance(totalDistance);
        transitDataModel.setAddress(transitAddress + " - " + networkProvider);
        transitDataModel.setActualKms(strActualKms);
        transitDataModel.setActualAmt(strActualTravelCost);
        transitDataModel.setRemarks(strRemarks);
        transitDataModel.setHotelExpense(strHotelExp);
        transitDataModel.setActualHotelExpense(strActualHotelExp);

        atmdbHelper.upsertTransitData(transitDataModel);//save in data base
        updateTodaySiteTable(siteId);
    }

    //save transit data into DB
    private void saveTransitDataIntoDB(String transitAddress, String siteId, String totalTravelCost, String totalDistance, String strActualKms, String strActualTravelCost, String strRemarks, String strHotelExp, String strActualHotelExp) {
        String transitAddressStr = transitAddress + " - " + networkProvider;
        if (ASTUIUtil.isOnline(getContext())) {
            saveTransitData(userId, siteId, selectedButtonType, lat, lon, totalDistance, totalTravelCost, transitAddressStr, strActualKms, strActualTravelCost, strRemarks, strHotelExp, strActualHotelExp);
        } else {
            saveTransitIntoDB(userId, siteId, selectedButtonType, lat, lon, totalDistance, totalTravelCost, transitAddressStr, strActualKms, strActualTravelCost, strRemarks, strHotelExp, strActualHotelExp);
        }
    }

    private void updateTodaySiteTable(String siteId) {
        if (selectedButtonType.equals("4")) {
            atmdbHelper.updateTodaySiteTransitComplete(1, siteId);//update today planed site status so that it will show only uncomplete site
        }
        Toast.makeText(getContext(), "Your Transit is saved", Toast.LENGTH_LONG).show();
    }

    //-----------call service-------------------------
    private void saveTransitData(String userIdStr, String siteIdStr, String selectedButtonTypeStr, double newlat, double newlon, String totalDistanceStr, String totalTravelCostStr, String transitAddressStr, String strActualKmsStr, String strActualTravelCostStr, String strRemarksStr, String strHotelExpStr, String strActualHotelExp) {
        _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        String userId = userIdStr;
        String siteNumId = siteIdStr;
        String transitType = selectedButtonTypeStr;
        String date = String.valueOf(System.currentTimeMillis());
        String lat = String.valueOf(newlat);
        String lon = String.valueOf(newlon);
        String calculatedDistance = totalDistanceStr;
        String calculatedAmount = totalTravelCostStr;
        String address = transitAddressStr + " - " + networkProvider;
        String actualDistance = strActualKmsStr;
        String actualAmount = strActualTravelCostStr;
        String remarks = strRemarksStr;
        String hotelExpenses = strHotelExpStr;
        hotelExpenses = hotelExpenses.replace("Rs.", "0");
        String actualHotelExpenses = strActualHotelExp;
        if (Double.parseDouble(calculatedAmount) > 1000) {
            calculatedDistance = "0";
            calculatedAmount = "0";
        }

        if (calculatedDistance.equals("") || calculatedDistance.equals(null)) {
            calculatedDistance = "0";
        }
        if (calculatedAmount.equals("") || calculatedAmount.equals(null)) {
            calculatedAmount = "0";
        }
        if (address.equals("") || address.equals(null)) {
            address = "NA";
        }
        if (actualDistance.equals("") || actualDistance.equals(null)) {
            actualDistance = "0";
        }
        if (actualAmount.equals("") || actualAmount.equals(null)) {
            actualAmount = "0";
        }
        if (remarks.equals("") || remarks.equals(null)) {
            remarks = "NA";
        }

        serviceURL = Contants.BASE_URL + Contants.ADD_TRANSIT_URL;

        serviceURL += "&uid=" + userId + "&sid=" + siteNumId + "&btnid=" + transitType + "&transittime=" + date +
                "&distance=" + calculatedDistance + "&address=" + address + "&calculatedamt=" + calculatedAmount + "&actualtravelamt=" + actualAmount + "&remark=" + remarks +
                "&actualkms=" + actualDistance + "&actualhotelprice=" + hotelExpenses + "&hotelprice=" + actualHotelExpenses + "&lat=" + lat + "&lon=" + lon;
        serviceURL = serviceURL.replace(" ", "^^");
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        serviceCaller.CallCommanServiceMethod(serviceURL, "getEbrhData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveTransitData(result, userIdStr, siteIdStr, selectedButtonTypeStr, newlat, newlon, totalDistanceStr, totalTravelCostStr, transitAddressStr, strActualKmsStr, strActualTravelCostStr, strRemarksStr, strHotelExpStr, strActualHotelExp);
                } else {
                    ASTUIUtil.showToast("Transit Data Not Saved");
                    if (_progrssBar.isShowing()) {
                        _progrssBar.dismiss();
                    }
                }
            }
        });
    }

    public void parseandsaveTransitData(String result, String userIdStr, String siteIdStr, String selectedButtonTypeStr, double newlat, double newlon, String totalDistanceStr, String totalTravelCostStr, String transitAddressStr, String strActualKmsStr, String strActualTravelCostStr, String strRemarksStr, String strHotelExpStr, String strActualHotelExp) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    updateTodaySiteTable(siteIdStr);
                } else {
                    saveTransitIntoDB(userIdStr, siteIdStr, selectedButtonTypeStr, newlat, newlon, totalDistanceStr, totalTravelCostStr, transitAddressStr, strActualKmsStr, strActualTravelCostStr, strRemarksStr, strHotelExpStr, strActualHotelExp);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }
        if (_progrssBar.isShowing()) {
            _progrssBar.dismiss();
        }
    }
}