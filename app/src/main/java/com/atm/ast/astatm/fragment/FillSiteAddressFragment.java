package com.atm.ast.astatm.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.SyncSiteAddressDataWithServer;
import com.atm.ast.astatm.adapter.FillSiteAddressAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.DistrictModel;
import com.atm.ast.astatm.model.FillSiteActivityModel;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.model.StateModel;
import com.atm.ast.astatm.model.TehsilModel;
import com.atm.ast.astatm.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FillSiteAddressFragment extends MainFragment {
    AutoCompleteTextView etSiteName, etSiteId;
    EditText etNameOfBranch, etBranchCode, etAddressLine, etCity, etPinCode, etStartTime, etEndTime;
    Spinner spBranchCode, spState, spDistrict, spTehsil, spOffsiteOnsite;
    Button btnSubmit;
    //private TimePicker timePicker1;
    CheckBox cb24Hr;
    ImageView imgGpsAddress, imgRefresh;
    Dialog unsyncedDialog = null;
    String filledCircleName, filledDistrictId, filledTehsilId;
    String[] arrSiteName, arrSiteId;
    String siteId, siteNumId;
    ArrayList<SiteDisplayDataModel> siteDetailArrayList;
    AtmDatabase atmDatabase = new AtmDatabase(getContext());
    ArrayList<DistrictModel> arrDistrict;
    ArrayList<TehsilModel> arrTehsil;
    ASTUIUtil commonFunctions;
    String startTime, endTime;
    FloatingActionButton btnSyncData;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_fill_site_address;
    }

    @Override
    protected void loadView() {
        this.etSiteName = this.findViewById(R.id.etSiteName);
        this.etSiteId = this.findViewById(R.id.etSiteId);
        this.etNameOfBranch = this.findViewById(R.id.etNameOfBranch);
        this.etBranchCode = this.findViewById(R.id.etBranchCode);
        this.etAddressLine = this.findViewById(R.id.etAddressLine);
        this.etCity = this.findViewById(R.id.etCity);
        this.etPinCode = this.findViewById(R.id.etPinCode);
        this.etBranchCode = this.findViewById(R.id.etBranchCode);
        this.spState = this.findViewById(R.id.spState);
        this.spDistrict = this.findViewById(R.id.spDistrict);
        this.spTehsil = this.findViewById(R.id.spTehsil);
        this.spOffsiteOnsite = this.findViewById(R.id.spOffsiteOnsite);
        this.btnSubmit = this.findViewById(R.id.btnSubmit);
        this.cb24Hr = this.findViewById(R.id.cb24Hr);
        this.etStartTime = this.findViewById(R.id.etStartTime);
        this.etEndTime = this.findViewById(R.id.etEndTime);
        this.imgGpsAddress = this.findViewById(R.id.imgGpsAddress);
        this.btnSyncData = this.findViewById(R.id.btnSyncData);
        this.imgRefresh = this.findViewById(R.id.imgRefresh);
    }

    @Override
    protected void setClickListeners() {
        this.btnSyncData.setOnClickListener(this);
        this.imgRefresh.setOnClickListener(this);
        this.imgGpsAddress.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        commonFunctions = new ASTUIUtil();
        unsyncedDialog = new Dialog(getContext());
        etStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            etStartTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            }
        });

        etEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            etEndTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            }
        });
        cb24Hr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    etStartTime.setEnabled(false);
                    etEndTime.setEnabled(false);
                } else {
                    etStartTime.setEnabled(true);
                    etEndTime.setEnabled(true);
                }
            }
        });
        final ArrayList<StateModel> arrState = atmDatabase.getStateData();
        if (arrState.size() > 0) {
            ArrayList<String> arrStateName = new ArrayList<>();
            arrStateName.add("-- Select State --");
            for (int i = 0; i < arrState.size(); i++) {
                arrStateName.add(arrState.get(i).getStateName());
            }
            ArrayAdapter<String> StateArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrStateName); //selected item will look like a spinner set from XML
            StateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spState.setAdapter(StateArrayAdapter);
        }
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    arrDistrict = atmDatabase.getDistrictData(arrState.get(position - 1).getStateId());
                    int selectedDistrictId = 0;
                    if (arrDistrict.size() > 0) {
                        ArrayList<String> arrDistrictName = new ArrayList<>();
                        arrDistrictName.add("-- Select District --");
                        for (int i = 0; i < arrDistrict.size(); i++) {
                            arrDistrictName.add(arrDistrict.get(i).getDistrictName());
                            if (arrDistrict.get(i).getDistrictId().equalsIgnoreCase(filledDistrictId)) {
                                selectedDistrictId = i + 1;
                            }
                        }
                        ArrayAdapter<String> DistrictArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrDistrictName); //selected item will look like a spinner set from XML
                        DistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spDistrict.setAdapter(DistrictArrayAdapter);
                        spDistrict.setSelection(selectedDistrictId);
                    }
                } else {
                    ArrayList<String> arrDistrictName = new ArrayList<>();
                    arrDistrictName.add("-- Select District --");
                    ArrayAdapter<String> DistrictArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrDistrictName); //selected item will look like a spinner set from XML
                    DistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(DistrictArrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0 || position != 0) {
                    int selectedTehsilId = 0;
                    arrTehsil = atmDatabase.getTehsilData(arrDistrict.get(position - 1).getDistrictId());
                    if (arrTehsil.size() > 0) {
                        ArrayList<String> arrTehsilName = new ArrayList<>();
                        arrTehsilName.add("-- Select Tehsil --");
                        for (int i = 0; i < arrTehsil.size(); i++) {
                            arrTehsilName.add(arrTehsil.get(i).getTehsilName());
                            if (arrTehsil.get(i).getTehsilId().equalsIgnoreCase(filledTehsilId)) {
                                selectedTehsilId = i + 1;
                            }
                        }
                        ArrayAdapter<String> TehsilArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrTehsilName); //selected item will look like a spinner set from XML
                        TehsilArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spTehsil.setAdapter(TehsilArrayAdapter);
                        spTehsil.setSelection(selectedTehsilId);
                    }
                } else {
                    ArrayList<String> arrTehsilName = new ArrayList<>();
                    arrTehsilName.add("-- Select Tehsil --");
                    ArrayAdapter<String> TehsilArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrTehsilName); //selected item will look like a spinner set from XML
                    TehsilArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spTehsil.setAdapter(TehsilArrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> offsiteOnsiteList = new ArrayList<>();
        offsiteOnsiteList.add("--Select Site Type--");
        offsiteOnsiteList.add("Onsite");
        offsiteOnsiteList.add("Offsite");
        ArrayAdapter<String> dataAdapterOnsiteOffsite = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, offsiteOnsiteList);
        dataAdapterOnsiteOffsite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOffsiteOnsite.setAdapter(dataAdapterOnsiteOffsite);
        siteDetailArrayList = atmDatabase.getFilteredData("site_search_name", "", "Survey");
        arrSiteName = new String[siteDetailArrayList.size()];
        arrSiteId = new String[siteDetailArrayList.size()];
        for (int i = 0; i < siteDetailArrayList.size(); i++) {
            arrSiteName[i] = siteDetailArrayList.get(i).getSiteName();
            arrSiteId[i] = siteDetailArrayList.get(i).getSiteId();
        }
        setSiteNameAdapter();
        //  getStateData();


    }


    private void setSiteNameAdapter() {
        ArrayAdapter<String> adapterSiteName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrSiteName);
        etSiteName.setAdapter(adapterSiteName);
        etSiteName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String siteName = etSiteName.getText().toString();
                for (int i = 0; i <= arrSiteName.length - 1; i++) {
                    if (siteName.equalsIgnoreCase(arrSiteName[i])) {
                        etSiteId.setText(arrSiteId[i]);
                    }
                }
                if (!etSiteId.getText().toString().equals("")) {
                    getSiteNumId();
                    getSiteAddressData(siteNumId);
                }
            }
        });

        ArrayAdapter<String> adapterSiteId = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrSiteId);
        etSiteId.setAdapter(adapterSiteId);
        etSiteId.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String siteId = etSiteId.getText().toString();
                for (int i = 0; i <= arrSiteId.length - 1; i++) {
                    if (siteId.equalsIgnoreCase(arrSiteId[i])) {
                        etSiteName.setText(arrSiteName[i]);
                    }
                }
                if (!etSiteId.getText().toString().equals("")) {
                    getSiteNumId();
                    getSiteAddressData(siteNumId);
                }
            }
        });
    }

    /*
     *
     * Calling Web Service to get Selected Site Data
     */
    private void getSiteAddressData(String strSiteNumId) {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = "";
        serviceURL = Contants.BASE_URL + Contants.GET_SITE_MASTER;
        serviceURL += "&sid=" + strSiteNumId;
        String a = "";
        serviceCaller.CallCommanServiceMethod(serviceURL, "getSiteAddressData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSelectedSiteData(result);
                } else {
                    ASTUIUtil.showToast("Selected Site Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }


    /*
     *
     * Parse and Save Selected Site Data
     */

    public void parseandsaveSelectedSiteData(String result) {
        if (result != null) {
            try {
                String siteId = "";
                String customerSiteId = "";
                String siteName = "";
                String branchName = "";
                String branchCode = "";
                String onOffSite = "";
                String address = "";
                String city = "";
                String circle = "";
                String circleId = "";
                String responseDistrict = "";
                String districtId = "";
                String tehsil = "";
                String tehsilId = "";
                String pinCode = "";
                String approvel = "";
                String latitude = "";
                String longitude = "";
                String startTime = "";
                String endTime = "";
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArrayHeader = jsonRootObject.optJSONArray("data");
                    for (int i = 0; i < jsonArrayHeader.length(); i++) {
                        JSONObject jsonObject = jsonArrayHeader.getJSONObject(i);
                        siteId = jsonObject.optString("SiteId").toString();
                        customerSiteId = jsonObject.optString("CustomerSiteId").toString();
                        siteName = jsonObject.optString("SiteName").toString();
                        branchName = jsonObject.optString("BranchName").toString();
                        branchCode = jsonObject.optString("BranchCode").toString();
                        onOffSite = jsonObject.optString("OnOffSite").toString();
                        address = jsonObject.optString("Address").toString();
                        city = jsonObject.optString("City").toString();
                        circle = jsonObject.optString("Circle").toString();
                        circleId = jsonObject.optString("CircleId").toString();
                        responseDistrict = jsonObject.optString("District").toString().trim();
                        districtId = jsonObject.optString("DistrictId").toString();
                        tehsil = jsonObject.optString("Tehsil").toString().trim();
                        tehsilId = jsonObject.optString("TehsilId").toString();
                        pinCode = jsonObject.optString("PinCode").toString();
                        approvel = jsonObject.optString("Approvel").toString();
                        latitude = jsonObject.optString("Latitude").toString();
                        longitude = jsonObject.optString("Longitude").toString();
                        startTime = jsonObject.optString("FunctinalHrTimeFrom").toString();
                        endTime = jsonObject.optString("FunctinalHrTimeTo").toString();
                        filledCircleName = circle;
                        filledDistrictId = districtId;
                        filledTehsilId = tehsilId;
                        etNameOfBranch.setText(branchName);
                        etBranchCode.setText(branchCode);
                        etAddressLine.setText(address);
                        etCity.setText(city);
                        etPinCode.setText(pinCode);
                        etStartTime.setText(startTime);
                        etEndTime.setText(endTime);
                        if (onOffSite.equalsIgnoreCase("onsite")) {
                            spOffsiteOnsite.setSelection(1);
                        } else {
                            spOffsiteOnsite.setSelection(2);
                        }

                        for (int j = 0; j < spState.getAdapter().getCount(); j++) {
                            if (spState.getAdapter().getItem(j).equals(circle)) {
                                spState.setSelection(j);
                                final ArrayList<StateModel> arrState = atmDatabase.getStateData();
                                final ArrayList<DistrictModel> arrDistrict = atmDatabase.getDistrictData(arrState.get(j - 1).getStateId());
                                if (arrDistrict.size() > 0) {
                                    ArrayList<String> arrDistrictName = new ArrayList<>();
                                    arrDistrictName.add("-- Select District --");
                                    for (int k = 0; k < arrDistrict.size(); k++) {
                                        arrDistrictName.add(arrDistrict.get(k).getDistrictName());
                                    }
                                    ArrayAdapter<String> DistrictArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrDistrictName); //selected item will look like a spinner set from XML
                                    DistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spDistrict.setAdapter(DistrictArrayAdapter);
                                }
                            }
                        }
                        //Select District
                        int count1 = spDistrict.getAdapter().getCount();
                        for (int j = 0; j < count1; j++) {
                            String adapterDistrict = spDistrict.getAdapter().getItem(j).toString().trim();
                            if (adapterDistrict.equalsIgnoreCase(responseDistrict)) {
                                spDistrict.setSelection(j);
                                break;
                            }
                        }
                        //Select Tehsil
                        int count = spTehsil.getAdapter().getCount();
                        for (int j = 0; j < count; j++) {
                            String te = spTehsil.getAdapter().getItem(j).toString().trim();
                            if (te.equalsIgnoreCase(tehsil)) {
                                spTehsil.setSelection(j);
                                break;
                            }
                        }
                        if (approvel.equals("Y")) {
                            etNameOfBranch.setEnabled(false);
                            etBranchCode.setEnabled(false);
                            //spOffsiteOnsite.setText(nonComSites);
                            ArrayList<String> arrOffSiteOnSite = new ArrayList<>();
                            arrOffSiteOnSite.add("--Select Off/On Site--");
                            arrOffSiteOnSite.add(onOffSite);
                            ArrayAdapter<String> adapterOffSiteOnSite = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrOffSiteOnSite);
                            spOffsiteOnsite.setAdapter(adapterOffSiteOnSite);
                            spOffsiteOnsite.setSelection(1);
                            spOffsiteOnsite.setEnabled(false);
                            etAddressLine.setEnabled(false);
                            etCity.setEnabled(false);
                            //spState.setText(nmsSites);
                            ArrayList<String> arrState = new ArrayList<>();
                            arrState.add("--Select State--");
                            arrState.add(circle);
                            ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrState);
                            spState.setAdapter(adapterState);
                            spState.setSelection(1);
                            spState.setEnabled(false);
                            etStartTime.setEnabled(false);
                            etEndTime.setEnabled(false);
                            //spDistrict.setText(nonComSites);
                            ArrayList<String> arrDistrict = new ArrayList<>();
                            arrDistrict.add("--Select District--");
                            arrDistrict.add(responseDistrict);
                            ArrayAdapter<String> adapterDistrict = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrDistrict);
                            spDistrict.setAdapter(adapterDistrict);
                            spDistrict.setSelection(1);
                            spDistrict.setEnabled(false);
                            //spTehsil.setText(invSites);
                            ArrayList<String> arrTehsil = new ArrayList<>();
                            arrTehsil.add("--Select Tehsil--");
                            arrTehsil.add(tehsil);
                            ArrayAdapter<String> adapterTehsil = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrTehsil);
                            spTehsil.setAdapter(adapterTehsil);
                            spTehsil.setSelection(1);
                            spTehsil.setEnabled(false);
                            etPinCode.setEnabled(false);
                            btnSubmit.setEnabled(false);
                        }

                    }
                } else {
                    //Toast.makeText(CircleActivity.this, "Data not available.");
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }

    }

    public void getSiteNumId() {
        siteId = etSiteId.getText().toString();
        for (int i = 0; i < arrSiteId.length; i++) {
            if (arrSiteId[i].equals(siteId)) {
                siteNumId = siteDetailArrayList.get(i).getSiteNumId();
                break;
            }
        }
    }

    public void genrateUnsyncedList() {
        unsyncedDialog.setContentView(R.layout.offline_sync_site_address_screen);
        unsyncedDialog.setTitle("Unsynced Entries");
        unsyncedDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        unsyncedDialog.setCanceledOnTouchOutside(false);
        ListView lvSiteAddress;
        Button btnSyncData;
        atmDatabase = new AtmDatabase(getContext());
        List<FillSiteActivityModel> siteAddressDataArrayList = atmDatabase.getSiteAddress();
        lvSiteAddress = (ListView) unsyncedDialog.findViewById(R.id.lvSiteAddress);
        btnSyncData = (Button) unsyncedDialog.findViewById(R.id.btnSyncData);

        if (siteAddressDataArrayList.size() > 0) {
            lvSiteAddress.setAdapter(new FillSiteAddressAdapter(getContext(),
                    siteAddressDataArrayList));
        } else {
            btnSyncData.setVisibility(View.GONE);
            ASTUIUtil.showToast( "No Pending Entries");
        }

        btnSyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getContext(), SyncSiteAddressDataWithServer.class);
                getContext().startService(intentService);
                unsyncedDialog.dismiss();
                ASTUIUtil.showToast( "Syncing with server.");
            }
        });
        if (siteAddressDataArrayList.size() > 0) {
            unsyncedDialog.show();
        }
    }


    /*
     *
     * Calling Web Service to geStateData
     */
    private void getStateData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = "";
        //serviceURL = Contants.BASE_URL + Contants.GET_SITE_MASTER;
        serviceURL = Contants.BASE_URL + Contants.GET_STATE_MASTER;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getStateData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveStateData(result);
                } else {
                    ASTUIUtil.showToast("Selected State Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }


    /*
     *
     * Parse and Save Selected StateData
     */

    public void parseandsaveStateData(String result) {
        if (result != null) {
            try {
                String circleName = "";
                String circleId = "";
                String districtName = "";
                String districtId = "";
                String tehsilName = "";
                String tehsilId = "";
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArrayHeader = jsonRootObject.optJSONArray("data");
                    ArrayList<DistrictModel> arrDistrict = new ArrayList<>();
                    ArrayList<TehsilModel> arrTehsil = new ArrayList<>();
                    ArrayList<StateModel> arrState = new ArrayList<>();
                    for (int i = 0; i < jsonArrayHeader.length(); i++) {
                        StateModel stateModel = new StateModel();
                        JSONObject jsonObject = jsonArrayHeader.getJSONObject(i);
                        circleName = jsonObject.optString("Circle").toString();
                        circleId = jsonObject.optString("CircleId").toString();
                        stateModel.setStateId(circleId);
                        stateModel.setStateName(circleName);
                        arrState.add(stateModel);
                        JSONArray jsonArrayDistrict = jsonObject.optJSONArray("District");
                        for (int j = 0; j < jsonArrayDistrict.length(); j++) {
                            DistrictModel districtModel = new DistrictModel();
                            JSONObject jsonDistrictObject = jsonArrayDistrict.getJSONObject(j);
                            districtId = jsonDistrictObject.optString("DistrictId").toString();
                            districtName = jsonDistrictObject.optString("District").toString();
                            districtModel.setDistrictName(districtName);
                            districtModel.setDistrictId(districtId);
                            districtModel.setStateId(circleId);
                            arrDistrict.add(districtModel);
                            JSONArray jsonArrayTehsil = jsonDistrictObject.optJSONArray("Tehsil");
                            for (int k = 0; k < jsonArrayTehsil.length(); k++) {
                                TehsilModel tehsilModel = new TehsilModel();
                                JSONObject jsonTehsilObject = jsonArrayTehsil.getJSONObject(k);
                                tehsilId = jsonTehsilObject.optString("TehsilId").toString();
                                tehsilName = jsonTehsilObject.optString("Tehsil").toString();
                                tehsilModel.setTehsilId(tehsilId);
                                tehsilModel.setTehsilName(tehsilName);
                                tehsilModel.setDistrictId(districtId);

                                arrTehsil.add(tehsilModel);
                            }
                        }
                    }
                    atmDatabase.deleteAllRows("state");
                    atmDatabase.deleteAllRows("district");
                    atmDatabase.deleteAllRows("tehsil");
                    atmDatabase.addStateData(arrState, arrDistrict, arrTehsil);
                    final ArrayList<StateModel> arrStateNew = atmDatabase.getStateData();
                    arrState = arrStateNew;
                    if (arrState.size() > 0) {
                        ArrayList<String> arrStateName = new ArrayList<>();
                        arrStateName.add("-- Select State --");
                        for (int i = 0; i < arrState.size(); i++) {
                            arrStateName.add(arrState.get(i).getStateName());
                        }
                        ArrayAdapter<String> StateArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrStateName); //selected item will look like a spinner set from XML
                        StateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spState.setAdapter(StateArrayAdapter);
                    }
                    Log.v("State List", "State List");

                } else {
                    //Toast.makeText(CircleActivity.this, "Data not available.", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSyncData) {
            genrateUnsyncedList();
        } else if (view.getId() == R.id.imgRefresh) {
            getStateData();
        } else if (view.getId() == R.id.imgGpsAddress) {
            Location location = commonFunctions.getLocation(getContext());
            if (location != null) {
                try {
                    String currentAddress = commonFunctions.getLocationAddress(location, getContext(), "");
                    etAddressLine.setText(currentAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
