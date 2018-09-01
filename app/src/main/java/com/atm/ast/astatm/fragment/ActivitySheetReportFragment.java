package com.atm.ast.astatm.fragment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.ActivityListSheetDataModel;
import com.atm.ast.astatm.model.ActivitySheetReportDataModel;
import com.atm.ast.astatm.model.CircleDisplayDataModel;
import com.atm.ast.astatm.model.ExecutedActivityListModel;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FilterPopupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ActivitySheetReportFragment extends MainFragment {
    LinearLayout llActivitySheetReport;
    LayoutInflater inflater;
    TextView tvPlannedExecuted, tvOnTheWay, tvReachedSite, tvLeftSite, tvUnknown, tvCircleName, tvAttendance, tvFilter;
    View viewVertical5;
    AutoCompleteTextView etSearch;
    ImageView imgRefresh;
    AtmDatabase atmDatabase;
    String[] arrActivities;
    String[] arrCircles;
    public static int[] checkListActivity;
    public static int[] checkListCircle;
    PopupWindow popup = null;
    public static List<CircleDisplayDataModel> circleViewResDataList = new ArrayList<CircleDisplayDataModel>();
    public String[] arrFeName;
    String uid = "";
    String lat = "25.30";
    String lon = "25.30";
    //Shared Prefrences
    SharedPreferences pref;
    String userId, userRole, userAccess, r1;
    ActivitySheetReportDataModel activitySheetReportDataModel;
    ArrayList<ActivitySheetReportDataModel> activitySheetReportArrayList;
    ArrayList<ExecutedActivityListModel> arrExecutedActivityData;
    ArrayList<ActivityListSheetDataModel> arrActivityList;
    ArrayList<ActivitySheetReportDataModel> arrActivityListFilter;
    String[][] arrColorCode = new String[15][2];
    TextView tvVersionNumber;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_activity_sheet_report;
    }

    @Override
    protected void loadView() {
        this.llActivitySheetReport = findViewById(R.id.llActivitySheetReport);
        this.tvPlannedExecuted = this.findViewById(R.id.tvPlannedExecuted);
        this.tvOnTheWay = this.findViewById(R.id.tvOnTheWay);
        this.tvReachedSite = this.findViewById(R.id.tvReachedSite);
        this.tvLeftSite = this.findViewById(R.id.tvLeftSite);
        this.tvUnknown = this.findViewById(R.id.tvUnknown);
        this.tvCircleName = this.findViewById(R.id.tvCircleName);
        this.tvAttendance = this.findViewById(R.id.tvAttendance);
        this.tvFilter = this.findViewById(R.id.tvFilter);
        this.viewVertical5 = this.findViewById(R.id.viewVertical5);
        this.etSearch = this.findViewById(R.id.etSearch);
        this.imgRefresh = this.findViewById(R.id.imgRefresh);
        this.tvVersionNumber = this.findViewById(R.id.tvVersionNumber);
    }

    @Override
    protected void setClickListeners() {
        this.imgRefresh.setOnClickListener(this);
        this.tvFilter.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    /**
     * getShared Pref Data
     */
    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        uid = pref.getString("userId", "");
    }

    @Override
    protected void dataToView() {
        popup = new PopupWindow(getContext());
        atmDatabase = new AtmDatabase(getContext());
        arrExecutedActivityData = new ArrayList<>();
        arrColorCode[0][0] = "G";
        arrColorCode[0][1] = "#00FF00";
        arrColorCode[1][0] = "Y";
        arrColorCode[1][1] = "#FFFF00";
        arrColorCode[2][0] = "O";
        arrColorCode[2][1] = "#FFA500";
        arrColorCode[3][0] = "R";
        arrColorCode[3][1] = "#FF0000";
        arrColorCode[4][0] = "W";
        arrColorCode[4][1] = "#FFFFFF";
        arrColorCode[5][0] = "B";
        arrColorCode[5][1] = "0000FF";
        arrColorCode[6][0] = "N";
        arrColorCode[6][1] = "#000080";
        arrColorCode[7][0] = "E";
        arrColorCode[7][1] = "#808080";
        arrColorCode[8][0] = "S";
        arrColorCode[8][1] = "#C0C0C0";
        arrColorCode[9][0] = "T";
        arrColorCode[9][1] = "008080";
        arrColorCode[10][0] = "P";
        arrColorCode[10][1] = "#800080";
        arrColorCode[11][0] = "A";
        arrColorCode[11][1] = "#00FFFF";
        arrColorCode[12][0] = "M";
        arrColorCode[12][1] = "#800000";
        arrColorCode[13][0] = "L";
        arrColorCode[13][1] = "#000000";
        arrColorCode[14][0] = "F";
        arrColorCode[14][1] = "#FF00FF";
        getSharedPrefData();
        String versionName = ASTUIUtil.getAppVersionName(getContext());
        if (versionName != null) {
            tvVersionNumber.setText(versionName);
        }
        inflater = LayoutInflater.from(getContext());
        arrExecutedActivityData = atmDatabase.getExecutedActivityData();
        circleViewResDataList = atmDatabase.getAllCircleData("NAME");
        String lastUpdatedDate = new SimpleDateFormat("MMM d, yyyy").format(atmDatabase.getExecutedActivityLastUpdatedDate());
        String currentDate = new SimpleDateFormat("MMM d, yyyy").format(System.currentTimeMillis());
        arrActivityList = atmDatabase.getAllActivityData("", "");
        if (arrExecutedActivityData.size() <= 0 || !lastUpdatedDate.equals(currentDate)) {
            getActivitySheetReportData();
            arrActivities = new String[arrActivityList.size()];
            arrCircles = new String[circleViewResDataList.size()];
        } else {
            activitySheetReportArrayList = new ArrayList<>();
            tvPlannedExecuted.setText("Planned/Executed: " + arrExecutedActivityData.get(0).getExecuted());
            tvOnTheWay.setText("On The Way: " + arrExecutedActivityData.get(0).getOnTheWay());
            tvReachedSite.setText("Reached Site: " + arrExecutedActivityData.get(0).getReachedSite());
            tvLeftSite.setText("Left Site: " + arrExecutedActivityData.get(0).getLeftSite());
            tvUnknown.setText("Unknown: " + arrExecutedActivityData.get(0).getUnknown());
            //tvCircleName.setText("Circle Name: " + );
            tvAttendance.setText("Attendance/Leave: " + arrExecutedActivityData.get(0).getAttendance() + "/" + arrExecutedActivityData.get(0).getLeave());
            tvCircleName.setVisibility(View.GONE);
            int dd = arrExecutedActivityData.size();
            for (int i = 0; i < arrExecutedActivityData.size(); i++) {
                activitySheetReportDataModel = new ActivitySheetReportDataModel();
                activitySheetReportDataModel.setSiteName(arrExecutedActivityData.get(i).getSiteName());
                activitySheetReportDataModel.setCustomer(arrExecutedActivityData.get(i).getCustomer());
                activitySheetReportDataModel.setActivityDate(arrExecutedActivityData.get(i).getActivityDate());
                activitySheetReportDataModel.setActivityTime(arrExecutedActivityData.get(i).getActivityTime());
                activitySheetReportDataModel.setZoneType(arrExecutedActivityData.get(i).getZoneType());
                activitySheetReportDataModel.setTotalAmount(arrExecutedActivityData.get(i).getTotalAmount());
                activitySheetReportDataModel.setStatus(arrExecutedActivityData.get(i).getStatus());
                activitySheetReportDataModel.setDays(arrExecutedActivityData.get(i).getDays());
                activitySheetReportDataModel.setColor(arrExecutedActivityData.get(i).getColor());
                activitySheetReportDataModel.setActivity(arrExecutedActivityData.get(i).getActivity());
                activitySheetReportDataModel.setNocApprovel(arrExecutedActivityData.get(i).getNocApprovel());
                activitySheetReportDataModel.setTaDaAmt(arrExecutedActivityData.get(i).getTaDaAmt());
                activitySheetReportDataModel.setBonus(arrExecutedActivityData.get(i).getBonus());
                activitySheetReportDataModel.setPenalty(arrExecutedActivityData.get(i).getPenalty());
                activitySheetReportDataModel.setFeName(arrExecutedActivityData.get(i).getFeName());
                activitySheetReportDataModel.setFeId(arrExecutedActivityData.get(i).getFeId());
                activitySheetReportDataModel.setCircle(arrExecutedActivityData.get(i).getCircle());
                activitySheetReportArrayList.add(activitySheetReportDataModel);
            }
            arrFeName = new String[activitySheetReportArrayList.size()];
            ArrayList<String> arrListFeName = new ArrayList<>();
            for (int i = 0; i < arrFeName.length; i++) {
                arrListFeName.add(activitySheetReportArrayList.get(i).getFeName());
            }
            inflateTableRow(activitySheetReportArrayList);
            setFeNameAdapter(arrListFeName);
        }


        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
               /* arrActivityListFilter = activitySheetReportArrayList;
                ArrayList<ActivitySheetReportDataModel> arrActivityListFilterTemp = new ArrayList();
                ArrayList<ActivitySheetReportDataModel> arrActivityListFilterTemp1 = new ArrayList();
                for (int j = 0; j < arrActivities.length; j++) {
                    if (PlannedActivityListFragment.arrSelectedFilterOne[j] == true) {
                        for (int i = 0; i < arrActivityListFilter.size(); i++) {
                            if (arrActivities[j].equals(arrActivityListFilter.get(i).getActivity())) {
                                arrActivityListFilterTemp.add(arrActivityListFilter.get(i));
                            }
                        }
                    }
                }
                for (int i = 0; i < arrCircles.length; i++) {
                    if (PlannedActivityListFragment.arrSelectedFilterTwo[i] == true) {
                        if (arrActivityListFilterTemp.size() > 1) {
                            for (int j = 0; j < arrActivityListFilterTemp.size(); j++) {
                                if (arrActivityListFilterTemp.get(j).getCircle().equals(arrCircles[i])) {
                                    arrActivityListFilterTemp1.add(arrActivityListFilterTemp.get(j));
                                }
                            }
                        } else {
                            for (int j = 0; j < arrActivityListFilter.size(); j++) {
                                if (arrCircles[i].equals(arrActivityListFilter.get(j).getCircle())) {
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
                    inflateTableRow(arrActivityListFilterTemp);
                } else {
                    ASTUIUtil.showToast("Data Not Found");
                }*/
            }
        });
    }


    /**
     * inflate Table Row Item
     * @param activitySheetReportArrayList
     */

    public void inflateTableRow(final ArrayList<ActivitySheetReportDataModel> activitySheetReportArrayList) {
        if (activitySheetReportArrayList.size() > 0) {
            llActivitySheetReport.removeAllViews();
            for (int i = 0; i < activitySheetReportArrayList.size(); i++) {
                View view = inflater.inflate(R.layout.activity_sheet_report_table_item, llActivitySheetReport, false);
                TextView tvSiteName = view.findViewById(R.id.tvSiteName);
                TextView tvCustomer = view.findViewById(R.id.tvCustomer);
                TextView tvDate = view.findViewById(R.id.tvDate);
                TextView tvTime = view.findViewById(R.id.tvTime);
                TextView tvInOutZone = view.findViewById(R.id.tvInOutZone);
                TextView tvTaDaAmt = view.findViewById(R.id.tvTaDaAmt);
                TextView tvStatus = view.findViewById(R.id.tvStatus);
                TextView tvNocApprovalColor = view.findViewById(R.id.tvNocApprovalColor);
                TextView tvActivity = view.findViewById(R.id.tvActivity);
                tvSiteName.setText(activitySheetReportArrayList.get(i).getSiteName());
                tvCustomer.setText(activitySheetReportArrayList.get(i).getCustomer());
                tvDate.setText(activitySheetReportArrayList.get(i).getActivityDate() + " " + activitySheetReportArrayList.get(i).getActivityTime());
                tvInOutZone.setText(activitySheetReportArrayList.get(i).getZoneType());
                tvTaDaAmt.setText(activitySheetReportArrayList.get(i).getTotalAmount());
                tvStatus.setText(activitySheetReportArrayList.get(i).getStatus());
                int arrLength = arrColorCode.length;
                for (int j = 0; j < arrLength; j++) {
                    if (arrColorCode[j][0].equals(activitySheetReportArrayList.get(i).getColor())) {
                        tvNocApprovalColor.setBackgroundColor(Color.parseColor(arrColorCode[j][1]));
                        tvNocApprovalColor.setText(activitySheetReportArrayList.get(i).getNocApprovel());
                    }
                }
                tvActivity.setText(activitySheetReportArrayList.get(i).getActivity() + "(" + activitySheetReportArrayList.get(i).getFeName() + ")");
                if (i % 2 != 0) {
                    view.setBackgroundColor(Color.parseColor("#34ba77"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#8cffcd"));
                }
                final int finalI = i;
                tvTaDaAmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.activity_report_amount_details);
                        dialog.setTitle("TA/DA Amount");
                        TextView tvTaDa = (TextView) dialog.findViewById(R.id.tvTaDaValue);
                        TextView tvBonus = (TextView) dialog.findViewById(R.id.tvBonusValue);
                        TextView tvPenalty = (TextView) dialog.findViewById(R.id.tvPenaltyValue);
                        tvTaDa.setText(activitySheetReportArrayList.get(finalI).getTaDaAmt());
                        tvBonus.setText(activitySheetReportArrayList.get(finalI).getBonus());
                        tvPenalty.setText(activitySheetReportArrayList.get(finalI).getPenalty());
                        dialog.show();
                    }
                });

                llActivitySheetReport.addView(view);
            }
        }
    }


    /*
     *
     * Calling Web Service to get Activity Report Data
     */
    private void getActivitySheetReportData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.GET_ACTIVITY_REPORT_URL;
        serviceURL += "&uid=" + uid + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getActivitySheetReportData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveActivitySheetReportData(result);
                } else {
                    ASTUIUtil.showToast(" Activity Report Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     *
     * Parse and Save getActivitySheetReportData
     */
    public void parseandsaveActivitySheetReportData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    ArrayList<ExecutedActivityListModel> arrExecutedActivityData = new ArrayList<>();
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
                    activitySheetReportArrayList = new ArrayList<>(jsonArray.length());
                    ArrayList<String> arrListFeName = new ArrayList<>();
                    int rowNumber = 0;
                    int arrayLength = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        rowNumber = i;
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String siteName = jsonObject.optString("siteName").toString();
                        String customer = jsonObject.optString("Customer").toString();
                        String activityDate = jsonObject.optString("ActivityDate").toString();
                        String activityTime = jsonObject.optString("ActivityTime").toString();
                        String zoneType = jsonObject.optString("ZoneType").toString();
                        String totalAmount = jsonObject.optString("TotalAmount").toString();
                        String status = jsonObject.optString("Status").toString();
                        String days = jsonObject.optString("Days").toString();
                        String color = jsonObject.optString("Color").toString();
                        String activity = jsonObject.optString("Activity").toString();
                        String nocApprovel = jsonObject.optString("NOCApprovel").toString();
                        String taDaAmt = jsonObject.optString("TADA").toString();
                        String bonus = jsonObject.optString("Bonus").toString();
                        String penalty = jsonObject.optString("Penalty").toString();
                        String feName = jsonObject.optString("FEName").toString();
                        String feId = jsonObject.optString("FEId").toString();
                        String executedCircleName = jsonObject.optString("Circle").toString();
                        if (!arrListFeName.contains(feName)) {
                            arrListFeName.add(feName);
                        }
                        activitySheetReportDataModel = new ActivitySheetReportDataModel();
                        activitySheetReportDataModel.setSiteName(siteName);
                        activitySheetReportDataModel.setCustomer(customer);
                        activitySheetReportDataModel.setActivityDate(activityDate);
                        activitySheetReportDataModel.setActivityTime(activityTime);
                        activitySheetReportDataModel.setZoneType(zoneType);
                        activitySheetReportDataModel.setTotalAmount(totalAmount);
                        activitySheetReportDataModel.setStatus(status);
                        activitySheetReportDataModel.setDays(days);
                        activitySheetReportDataModel.setColor(color);
                        activitySheetReportDataModel.setActivity(activity);
                        activitySheetReportDataModel.setNocApprovel(nocApprovel);
                        activitySheetReportDataModel.setTaDaAmt(taDaAmt);
                        activitySheetReportDataModel.setBonus(bonus);
                        activitySheetReportDataModel.setPenalty(penalty);
                        activitySheetReportDataModel.setFeName(feName);
                        activitySheetReportDataModel.setFeId(feId);
                        activitySheetReportArrayList.add(activitySheetReportDataModel);
                        ExecutedActivityListModel executedActivityListModel = new ExecutedActivityListModel();
                        executedActivityListModel.setExecuted(executed);
                        executedActivityListModel.setOnTheWay(onTheWay);
                        executedActivityListModel.setReachedSite(reachedSite);
                        executedActivityListModel.setLeftSite(leftSite);
                        executedActivityListModel.setUnknown(unknown);
                        executedActivityListModel.setAttendance(attendanceCount);
                        executedActivityListModel.setSiteName(siteName);
                        executedActivityListModel.setCustomer(customer);
                        executedActivityListModel.setActivityDate(activityDate);
                        executedActivityListModel.setActivityTime(activityTime);
                        executedActivityListModel.setZoneType(zoneType);
                        executedActivityListModel.setTotalAmount(totalAmount);
                        executedActivityListModel.setStatus(status);
                        executedActivityListModel.setDays(days);
                        executedActivityListModel.setColor(color);
                        executedActivityListModel.setActivity(activity);
                        executedActivityListModel.setNocApprovel(nocApprovel);
                        executedActivityListModel.setTaDaAmt(taDaAmt);
                        executedActivityListModel.setBonus(bonus);
                        executedActivityListModel.setPenalty(penalty);
                        executedActivityListModel.setFeId(feId);
                        executedActivityListModel.setFeName(feName);
                        executedActivityListModel.setLeave(leaveCount);
                        executedActivityListModel.setCircle(executedCircleName);
                        arrExecutedActivityData.add(executedActivityListModel);
                    }
                    atmDatabase.deleteAllRows("executed_activity");
                    atmDatabase.addExecutedActivityData(arrExecutedActivityData);
                    inflateTableRow(activitySheetReportArrayList);
                    arrActivities = new String[activitySheetReportArrayList.size()];
                    int f = activitySheetReportArrayList.size();
                    arrCircles = new String[circleViewResDataList.size()];
                    setFeNameAdapter(arrListFeName);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }

    }

    /**
     * set Name Adapter
     * @param arrListFeName
     */

    public void setFeNameAdapter(ArrayList<String> arrListFeName) {
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
                    inflateTableRow(activitySheetReportArrayList);
                }
            }
        });
        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchString = etSearch.getText().toString();
                ArrayList<ActivitySheetReportDataModel> activitySheetReportTempArrayList = new ArrayList<ActivitySheetReportDataModel>();
                for (int i = 0; i < activitySheetReportArrayList.size(); i++) {
                    if (searchString.equalsIgnoreCase(activitySheetReportArrayList.get(i).getFeName())) {
                        activitySheetReportTempArrayList.add(activitySheetReportArrayList.get(i));
                    }
                }
                inflateTableRow(activitySheetReportTempArrayList);

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgRefresh) {
            getActivitySheetReportData();
        } else if (view.getId() == R.id.tvFilter) {
            getFIlterData();
        }

    }

    /**
     * get Filter Data
     */

    public void getFIlterData() {
        String[] arrParentData = new String[2];
        arrActivities = new String[arrActivityList.size()];
        arrCircles = new String[circleViewResDataList.size()];
        String[] arrFilteredIdData = {"Battery", "NoComm", "INV"};
        arrParentData[0] = "Activity";
        arrParentData[1] = "Circle";
        checkListCircle = new int[0];
        checkListActivity = new int[0];
        for (int i = 0; i < circleViewResDataList.size(); i++) {
            arrCircles[i] = circleViewResDataList.get(i).getCircleName();
        }
        for (int i = 0; i < arrActivityList.size(); i++) {
            arrActivities[i] = arrActivityList.get(i).getActivityName();
        }
        /*PlannedActivityListFragment.arrSelectedFilterOne = new Boolean[arrActivities.length];
        PlannedActivityListFragment.arrSelectedFilterTwo = new Boolean[arrCircles.length];
        for (int i = 0; i < arrActivities.length; i++) {
            PlannedActivityListFragment.arrSelectedFilterOne[i] = false;
        }
        for (int i = 0; i < arrCircles.length; i++) {
            PlannedActivityListFragment.arrSelectedFilterTwo[i] = false;
        }
        FilterPopupActivity filterPopup = new FilterPopupActivity();
        filterPopup.getFilterPopup(popup, getContext(), arrParentData, arrActivities, arrCircles, arrFilteredIdData, "planned_activity");*/

    }
}
