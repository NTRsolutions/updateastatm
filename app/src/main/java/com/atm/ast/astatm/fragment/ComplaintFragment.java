package com.atm.ast.astatm.fragment;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.ComplaintDataModel;
import com.atm.ast.astatm.model.ComplaintDescriptionDataModel;
import com.atm.ast.astatm.model.ContentData;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ComplaintFragment extends MainFragment {
    LinearLayout llComplaintDescription;
    AppCompatEditText etCustomerCode, etName, etMobile, etEmail, etComplaintDescriptionOthers, etClientName;
    AutoCompleteTextView etSiteName, etSiteId;
    Spinner spPriority, spComplaintType, spComplainDescription;
    Button btnSubmit;
    CheckBox cbProposePlan;
    SharedPreferences pref;
    String uid, userName, email, mobileNo, menuId, roleId = "0";
    int isCustomer;
    String[] arrSiteName, arrSiteId;
    int isProposed = 0;
    ATMDBHelper atmdbHelper;
    List<Data> siteDetailArrayList;
    String[] arrRoleIds;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_complaint;
    }

    @Override
    protected void loadView() {
        this.etSiteId = this.findViewById(R.id.etSiteId);
        this.etSiteName = this.findViewById(R.id.etSiteName);
        this.etClientName = this.findViewById(R.id.etClientName);
        this.etName = this.findViewById(R.id.etName);
        this.etMobile = this.findViewById(R.id.etMobileNum);
        this.etEmail = this.findViewById(R.id.etEmail);
        this.spPriority = this.findViewById(R.id.spPriority);
        this.spComplaintType = this.findViewById(R.id.spComplaintType);
        this.spComplainDescription = this.findViewById(R.id.spComplainDescription);
        this.btnSubmit = this.findViewById(R.id.btnSubmit);
        this.etComplaintDescriptionOthers = this.findViewById(R.id.etComplaintDescriptionOthers);
        this.llComplaintDescription = this.findViewById(R.id.llComplaintDescription);
        this.cbProposePlan = this.findViewById(R.id.cbProposePlan);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {
    }


    /**
     * getShared Pref save Data
     */
    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        uid = pref.getString("userId", "");
        userName = pref.getString("userName", "");
        email = pref.getString("EmailId", "");
        mobileNo = pref.getString("MobileNum", "");
        menuId = pref.getString("MenuId", "");
        roleId = pref.getString("roleId", "");
    }

    @Override
    protected void dataToView() {
        getSharedPrefData();
        ArrayList<String> complaintTypeList = new ArrayList<String>();
        complaintTypeList.add("-- Select Type --");
        complaintTypeList.add("SA");
        complaintTypeList.add("NSA");
        ArrayList<String> priorityList = new ArrayList<String>();
        priorityList.add("-- Select Priority --");
        priorityList.add("Regular");
        priorityList.add("Urgent");
        ArrayList<String> complaintDescriptionList = new ArrayList<String>();
        complaintDescriptionList.add("-- Select Decription --");
        complaintDescriptionList.add("UPS Trip");
        complaintDescriptionList.add("Low Battery Backup");
        complaintDescriptionList.add("EB Not Charging Batteries");
        complaintDescriptionList.add("Solar Generation issue");
        complaintDescriptionList.add("Earthing Issue");
        complaintDescriptionList.add("Joint Visit");
        complaintDescriptionList.add("Data Not Coming(Non Comm)");
        complaintDescriptionList.add("Other");
        if (roleId.contains(",")) {
            arrRoleIds = roleId.split(",");
        }
        for (int i = 0; i < arrRoleIds.length - 1; i++) {
            if (arrRoleIds[i].equalsIgnoreCase("113")) {
                //roleId = "113";
                cbProposePlan.setVisibility(View.GONE);
            }
        }
        //    etClientName.setEnabled(false);
        etMobile.setText(mobileNo);
        etMobile.setEnabled(false);
        etEmail.setText(email);
        etEmail.setEnabled(false);
        etName.setText(userName);
        etName.setEnabled(false);
        atmdbHelper = new ATMDBHelper(getContext());
        siteDetailArrayList = atmdbHelper.getAllSiteListData();
        arrSiteName = new String[siteDetailArrayList.size()];
        arrSiteId = new String[siteDetailArrayList.size()];
        for (int i = 0; i < siteDetailArrayList.size(); i++) {
            arrSiteName[i] = siteDetailArrayList.get(i).getSiteName();
            arrSiteId[i] = String.valueOf(siteDetailArrayList.get(i).getCustomerSiteId());
        }

        setSiteNameAdapter();
        ArrayAdapter complaintTypeAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, complaintTypeList);
        complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spComplaintType.setAdapter(complaintTypeAdapter);
        ArrayAdapter priorityAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, priorityList);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(priorityAdapter);
        ArrayAdapter complaintDescriptionAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, complaintDescriptionList);
        complaintDescriptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spComplainDescription.setAdapter(complaintDescriptionAdapter);
        spComplaintType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> arrStringComplaintDes = new ArrayList<String>();
                if (i == 1) {
                    ArrayList<ComplaintDescriptionDataModel> arrComplaintDes = new ArrayList<>();
                    arrComplaintDes = atmdbHelper.getComplaintDesription("SA");
                    for (int j = 0; j < arrComplaintDes.size(); j++) {
                        arrStringComplaintDes.add(arrComplaintDes.get(j).getDescription());
                    }
                    ArrayAdapter complaintDescriptionAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, arrStringComplaintDes);
                    complaintDescriptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spComplainDescription.setAdapter(complaintDescriptionAdapter);

                } else if (i == 2) {
                    ArrayList<ComplaintDescriptionDataModel> arrComplaintDes = new ArrayList<>();
                    arrComplaintDes = atmdbHelper.getComplaintDesription("NSA");
                    for (int j = 0; j < arrComplaintDes.size(); j++) {
                        arrStringComplaintDes.add(arrComplaintDes.get(j).getDescription());
                    }
                    ArrayAdapter complaintDescriptionAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, arrStringComplaintDes);
                    complaintDescriptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spComplainDescription.setAdapter(complaintDescriptionAdapter);
                } else {
                    arrStringComplaintDes.add("Please Select Complaint Type");
                    ArrayAdapter complaintDescriptionAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, arrStringComplaintDes);
                    complaintDescriptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spComplainDescription.setAdapter(complaintDescriptionAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spComplainDescription.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spComplainDescription.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                    llComplaintDescription.setVisibility(View.VISIBLE);
                } else {
                    llComplaintDescription.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String siteId = etSiteId.getText().toString();
                //String siteNumId = "NA";
                String name = etName.getText().toString();
                String mobileNumber = etMobile.getText().toString();
                String email = etEmail.getText().toString();
                String clientName = etClientName.getText().toString();
                int complaintTypeId = spComplaintType.getSelectedItemPosition();
                int priorityId = spPriority.getSelectedItemPosition();
                String complaintDescription = spComplainDescription.getSelectedItem().toString();
                int proposePlanId = isProposed;
                if (complaintDescription.equalsIgnoreCase("Other") || complaintDescription.equalsIgnoreCase("")) {
                    complaintDescription = etComplaintDescriptionOthers.getText().toString();
                }
                if (siteId.equalsIgnoreCase("")) {
                    ASTUIUtil.showToast("Please Select a Site");
                } else if (complaintTypeId == 0) {
                    ASTUIUtil.showToast("Please Select Complaint Type");
                } else if (priorityId == 0) {
                    ASTUIUtil.showToast("Please Select Priority");
                } else if (complaintDescription.equalsIgnoreCase("")) {
                    ASTUIUtil.showToast("Please Select Complaint Description");
                } else if (clientName == null) {
                    ASTUIUtil.showToast("Client Name Not Found");
                } else {
                    if (spPriority.getSelectedItemPosition() == 1) {
                        priorityId = 1279;
                    } else if (spPriority.getSelectedItemPosition() == 2) {
                        priorityId = 1280;
                    }
                    if (ASTUIUtil.isOnline(getContext())) {
                        saveComplainData(siteId, name, mobileNumber, email, complaintTypeId, priorityId, complaintDescription, proposePlanId, clientName);
                    } else {
                        saveOffLineComplaint(siteId, name, mobileNumber, email, complaintTypeId, priorityId, complaintDescription, proposePlanId, clientName);
                    }
                }
            }
        });
        cbProposePlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbProposePlan.isChecked()) {
                    isProposed = 1;
                } else {
                    isProposed = 0;
                }
            }
        });

      /*  ArrayList<ComplaintDescriptionDataModel> arrComplaintDes = new ArrayList<>();
        arrComplaintDes = atmDatabase.getComplaintDesription("SA");
        if (arrComplaintDes.size() < 1) {
            getComplaintDescriptionData();
        } else if (arrComplaintDes.size() > 0) {
        }*/
        getComplaintDescriptionData();
    }

    //save off line complaint
    private void saveOffLineComplaint(String siteId, String name, String mobileNumber, String email, int complaintTypeId, int priorityId, String complaintDescription, int proposePlanId, String clientName) {
        ComplaintDataModel complaintDataModel = new ComplaintDataModel();
        complaintDataModel.setUserId(uid);
        complaintDataModel.setSiteID(siteId);
        complaintDataModel.setName(name);
        complaintDataModel.setMobile(mobileNumber);
        complaintDataModel.setEmailId(email);
        complaintDataModel.setType(String.valueOf(complaintTypeId));
        complaintDataModel.setPriority(String.valueOf(priorityId));
        complaintDataModel.setDescription(complaintDescription);
        complaintDataModel.setProposePlan(String.valueOf(proposePlanId));
        complaintDataModel.setTime(String.valueOf(System.currentTimeMillis()));
        complaintDataModel.setClientName(clientName);
        atmdbHelper.addComplaintData(complaintDataModel);
        ASTUIUtil.showToast("Your ticket has been submitted successfully");
        getHostActivity().redirectToHomeMenu();
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
                        etClientName.setText(siteDetailArrayList.get(i).getClient());
                    }
                }
            }
        });
        ArrayAdapter<String> adapterSiteId = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrSiteId);
        etSiteId.setAdapter(adapterSiteId);
        etSiteId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String siteId = etSiteId.getText().toString();
                for (int i = 0; i <= arrSiteId.length - 1; i++) {
                    if (siteId.equalsIgnoreCase(arrSiteId[i])) {
                        etSiteName.setText(arrSiteName[i]);
                        etClientName.setText(siteDetailArrayList.get(i).getClient());
                    }
                }
            }
        });
    }

    public String getSiteNumericId(String selectedSiteId) {
        String siteNumId = "";
        for (int i = 0; i < arrSiteId.length; i++) {
            if (arrSiteId[i].equalsIgnoreCase(selectedSiteId)) {
                siteNumId = String.valueOf(siteDetailArrayList.get(i).getSiteId());
            }
        }
        return siteNumId;
    }

    /*
     *
     * Calling Web Service to get Complaint Description Data
     */

    private void getComplaintDescriptionData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = "";
        serviceURL = Contants.BASE_URL_API + Contants.COMPLAINT_DESCRIPTION;
        serviceCaller.CallCommanServiceMethod(serviceURL, "ComplaintDescriptionData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveComplaintDescriptionData(result);
                } else {
                    ASTUIUtil.showToast("ComplaintDescription Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     *
     * Parse and SaveComplaintDescription Data
     */
    public void parseandsaveComplaintDescriptionData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                ArrayList<ComplaintDescriptionDataModel> arrComplaintDes = new ArrayList<>();
                String jsonStatus = jsonRootObject.optString("Status").toString();
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String type = jsonObject.optString("Type").toString();
                        String description = jsonObject.optString("Description").toString();
                        String lastUpdated = Long.toString(System.currentTimeMillis());
                        ComplaintDescriptionDataModel complaintDescriptionDataModel = new ComplaintDescriptionDataModel();
                        complaintDescriptionDataModel.setType(type);
                        complaintDescriptionDataModel.setDescription(description);
                        complaintDescriptionDataModel.setLastUpdatedTime(lastUpdated);
                        arrComplaintDes.add(complaintDescriptionDataModel);
                    }
                    atmdbHelper.deleteAllRows("complaint_description");
                    atmdbHelper.addComplaintDesription(arrComplaintDes);
                    arrComplaintDes = atmdbHelper.getComplaintDesription("SA");
                    arrComplaintDes = atmdbHelper.getComplaintDesription("NSA");
                    String test = "";
                } else if (jsonStatus.equals("0")) {
                    ASTUIUtil.showToast("Data is not available");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }

    }

    /**
     * call web services to web foesaveComplain Data
     */
    private void saveComplainData(String siteId, String name, String mobileNumber, String email, int complaintTypeId, int priorityId, String complaintDescription, int proposePlanId, String clientName) {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("CustomerCode", clientName);
            mainObj.put("SiteID", siteId);
            mainObj.put("Name", name);
            mainObj.put("Mobile", mobileNumber);
            mainObj.put("Email", email);
            mainObj.put("ComplaintType", String.valueOf(complaintTypeId));
            mainObj.put("Priority", String.valueOf(priorityId));
            mainObj.put("Remarks", complaintDescription);
            mainObj.put("IsProposedPlan", String.valueOf(proposePlanId));
            mainObj.put("UserId", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String serviceURL = "";
        serviceURL = Contants.BASE_URL_API + Contants.SAVE_COMPLAINT_URL;
        serviceCaller.CallCommanServiceMethod(serviceURL, mainObj, "ComplaintDescriptionData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveComlaintData(result);
                } else {
                    ASTUIUtil.showToast("Your ticket not submitted successfully!");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     *
     * Parse and save Comlaint Data
     */
    public void parseandsaveComlaintData(String result) {
        if (result != null) {
            try {
                ContentData data = new Gson().fromJson(result, ContentData.class);
                if (data.getStatus() == 2) {
                    ASTUIUtil.showToast("Your ticket has been submitted successfully");
                    getHostActivity().redirectToHomeMenu();
                } else {
                    ASTUIUtil.showToast(data.getMessage());
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        } else {
            ASTUIUtil.showToast("Your ticket not submitted successfully!");
        }
    }
}
