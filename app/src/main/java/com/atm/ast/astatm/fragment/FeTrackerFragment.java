package com.atm.ast.astatm.fragment;

import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.FeTrackerAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.ActivityListSheetDataModel;
import com.atm.ast.astatm.model.CircleDisplayDataModel;
import com.atm.ast.astatm.model.FeTrackerChildItemModel;
import com.atm.ast.astatm.model.FeTrackerEmployeeModel;
import com.atm.ast.astatm.model.PlannedActivityListModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Header;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FilterPopupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FeTrackerFragment extends MainFragment {
    ExpandableListView elvFeList;
    AutoCompleteTextView etSearch;
    ArrayList<FeTrackerEmployeeModel> arrFeTracker;
    ArrayList<FeTrackerChildItemModel> arrayListFeTrackerChild;
    SharedPreferences pref;
    String userId, userRole, userAccess, r1;
    String userName = "";
    TextView tvPlannedExecuted, tvOnTheWay, tvReachedSite, tvLeftSite, tvUnknown, tvCircleName, tvAttendance;
    TextView tvFilter;
    private ATMDBHelper atmdbHelper;
    ArrayList<Data> planActivityList;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_fe_tracker;
    }

    @Override
    protected void loadView() {
        elvFeList = findViewById(R.id.elvFeList);
        etSearch = findViewById(R.id.etSearch);
        tvPlannedExecuted = findViewById(R.id.tvPlannedExecuted);
        tvOnTheWay = findViewById(R.id.tvOnTheWay);
        tvReachedSite = findViewById(R.id.tvReachedSite);
        tvLeftSite = findViewById(R.id.tvLeftSite);
        tvUnknown = findViewById(R.id.tvUnknown);
        tvCircleName = findViewById(R.id.tvCircleName);
        tvAttendance = findViewById(R.id.tvAttendance);
        tvFilter = findViewById(R.id.tvFilter);


    }

    @Override
    protected void setClickListeners() {
        tvFilter.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        userId = pref.getString("userId", "");
        userName = pref.getString("userName", "");
        atmdbHelper = new ATMDBHelper(getContext());
        planActivityList = atmdbHelper.getAllPlanActivityData();
        setHeaderDetail();
        if (ASTUIUtil.isOnline(getContext())) {
            getFeTracker();
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

    /*
     *
     * Calling Web Service to get FETracker Data
     */
    private void getFeTracker() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        String lat = "23.30";
        String lon = "23.30";
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.FE_TRACKER_URL;
        serviceURL += "&uid=" + userId + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getFeTracker", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsavegetFeTrackerData(result);
                } else {
                    ASTUIUtil.showToast("FETracker Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     *
     * Parse and Save FeTracker Data
     */

    public void parseandsavegetFeTrackerData(String result) {
        if (result != null) {
            try {
                arrFeTracker = new ArrayList<>();
                FeTrackerEmployeeModel feTrackerEmployeeModel;
                ArrayList<String> arrListFeName = new ArrayList<>();
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    String[][] data = new String[jsonArray.length()][jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        feTrackerEmployeeModel = new FeTrackerEmployeeModel();
                        arrayListFeTrackerChild = new ArrayList<>();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String uid = jsonObject.optString("FEID").toString();
                        String name = jsonObject.optString("FEName").toString();
                        String contactNo = jsonObject.optString("FEContactNo").toString();
                        String color = jsonObject.optString("Color").toString();
                        String address = jsonObject.optString("Address").toString();
                        String distance = jsonObject.optString("Distance").toString();
                        String status = jsonObject.optString("Status").toString();
                        String lastTrackedTime = jsonObject.optString("LastTrackedTime").toString();
                        String feCircle = jsonObject.optString("Circle").toString();
                        String feActivity = jsonObject.optString("Activity").toString();
                        JSONArray jsonArrayTransitDetails = jsonObject.optJSONArray("FETransitDetails");
                        FeTrackerChildItemModel feTrackerChildItemModel;
                        int totalTransit = 0;
                        totalTransit = jsonArrayTransitDetails.length();
                        if (!jsonArrayTransitDetails.toString().equals("") ||
                                !jsonArrayTransitDetails.toString().equals("null") ||
                                !jsonArrayTransitDetails.toString().equals(null)) {
                            for (int j = 0; j < jsonArrayTransitDetails.length(); j++) {
                                feTrackerChildItemModel = new FeTrackerChildItemModel();
                                JSONObject jsonObjectTransitDetails = jsonArrayTransitDetails.getJSONObject(j);
                                String transitTime = jsonObjectTransitDetails.optString("TransitTime").toString();
                                String childStatus = jsonObjectTransitDetails.optString("Status").toString();
                                String childColor = jsonObjectTransitDetails.optString("Color").toString();
                                String siteId = jsonObjectTransitDetails.optString("SiteId").toString();
                                String siteName = jsonObjectTransitDetails.optString("SiteName").toString();
                                String customerSiteId = jsonObjectTransitDetails.optString("CustomerSiteId").toString();
                                String circle = jsonObjectTransitDetails.optString("Circle").toString();
                                String district = jsonObjectTransitDetails.optString("District").toString();
                                String activityStatus = jsonObjectTransitDetails.optString("ActivityStatus").toString();
                                String totalDistance = jsonObjectTransitDetails.optString("Distance").toString();
                                String siteLat = jsonObjectTransitDetails.optString("Lat").toString();
                                String siteLong = jsonObjectTransitDetails.optString("Lon").toString();
                                String siteBaseDistanceFromSite = jsonObjectTransitDetails.optString("Distance").toString();
                                feTrackerChildItemModel.setTransitTime(transitTime);
                                feTrackerChildItemModel.setStatus(childStatus);
                                feTrackerChildItemModel.setColor(childColor);
                                feTrackerChildItemModel.setSiteId(siteId);
                                feTrackerChildItemModel.setSiteName(siteName);
                                feTrackerChildItemModel.setCustomerSiteId(customerSiteId);
                                feTrackerChildItemModel.setCircle(circle);
                                feTrackerChildItemModel.setDistrict(district);
                                feTrackerChildItemModel.setActivityStatus(activityStatus);
                                feTrackerChildItemModel.setDistance(totalDistance);
                                feTrackerChildItemModel.setUserId(uid);
                                feTrackerChildItemModel.setLat(siteLat);
                                feTrackerChildItemModel.setLon(siteLong);
                                feTrackerChildItemModel.setDistanceFromSite(siteBaseDistanceFromSite);
                                arrayListFeTrackerChild.add(feTrackerChildItemModel);
                                Log.d("FeTrackerChildData", arrayListFeTrackerChild.get(j).getSiteName() + " - " +
                                        arrayListFeTrackerChild.get(j).getTransitTime());
                            }
                        }
                        if (!arrListFeName.contains(name)) {
                            arrListFeName.add(name);
                        }
                        feTrackerEmployeeModel.setUserId(uid);
                        feTrackerEmployeeModel.setName(name);
                        feTrackerEmployeeModel.setContactNo(contactNo);
                        feTrackerEmployeeModel.setStatus(status);
                        feTrackerEmployeeModel.setColor(color);
                        feTrackerEmployeeModel.setTotalTransit(totalTransit);
                        feTrackerEmployeeModel.setShortAddress(address);
                        feTrackerEmployeeModel.setDistance(distance);
                        feTrackerEmployeeModel.setArrayListFeTrackerChild(arrayListFeTrackerChild);
                        feTrackerEmployeeModel.setLastTrackedTime(lastTrackedTime);
                        feTrackerEmployeeModel.setCircle(feCircle);
                        feTrackerEmployeeModel.setActivity(feActivity);
                        arrFeTracker.add(feTrackerEmployeeModel);
                    }
                    atmdbHelper.deleteAllRows("table_fe_tracker");
                    atmdbHelper.deleteAllRows("table_fe_tracker_transit");
                    atmdbHelper.addFeTrackerData(arrFeTracker);
                    setFeNameAdapter(arrListFeName, data);
                    elvFeList.setAdapter(new FeTrackerAdapter(getContext(), data, arrFeTracker, arrayListFeTrackerChild));
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }
    }

    /**
     * set FeName Adapter
     *
     * @param arrListFeName
     * @param data
     */
    public void setFeNameAdapter(ArrayList<String> arrListFeName, final String[][] data) {
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
                    String[][] arrAllData = new String[arrFeTracker.size()][arrFeTracker.size()];
                    elvFeList.setAdapter(new FeTrackerAdapter(getContext(), arrAllData, arrFeTracker, arrayListFeTrackerChild));
                }
            }
        });

        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchString = etSearch.getText().toString();
                ArrayList<FeTrackerEmployeeModel> feListTempArrayList = new ArrayList<FeTrackerEmployeeModel>();
                for (int i = 0; i < arrFeTracker.size(); i++) {
                    if (searchString.equalsIgnoreCase(arrFeTracker.get(i).getName())) {
                        feListTempArrayList.add(arrFeTracker.get(i));
                    }
                }
                String[][] arrFilteredData = new String[feListTempArrayList.size()][feListTempArrayList.size()];
                elvFeList.setAdapter(new FeTrackerAdapter(getContext(), arrFilteredData, feListTempArrayList, arrayListFeTrackerChild));

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvFilter) {

        }
    }


}
