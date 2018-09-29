package com.atm.ast.astatm.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.equipment.EquipmentandAccessoriesTab;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.ActivitySheetModel;
import com.atm.ast.astatm.model.newmodel.ContentLocalData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PMCheckLIstFragment extends MainFragment {
    Button btnDone;
    TextView cbEarthVolt;
    EditText etEarthVolt;
    TextView cbBattTopup;
    EditText etBattTopup;
    TextView cbBattCell;
    EditText etBattCell;
    TextView cbCharger;
    EditText etCharger;
    TextView cbInverter;
    EditText etInverter;
    TextView cbEbConn;
    EditText etEbConn;
    TextView cbConn;
    EditText etConn;
    TextView cbSolar;
    Spinner spSolar;
    TextView cbCell1;
    EditText etCell1;
    TextView cbCell2;
    EditText etCell2;
    TextView cbCell3;
    EditText etCell3;
    TextView cbCell4;
    EditText etCell4;
    TextView cbCell5;
    EditText etCell5;
    TextView cbCell6;
    EditText etCell6;
    TextView cbCell7;
    EditText etCell7;
    TextView cbCell8;
    EditText etCell8;
    CheckBox cbPhotoYes;
    CheckBox cbPhotoNo;
    CheckBox cbModemConnectionYes;
    CheckBox cbModemConnectionNo;
    TextView cbSpareRequirement;
    EditText etSpareRequirement;
    TextView cbBatteryTerminal;
    Spinner spBatteryTerminal;
    TextView cbSolarStruct;
    Spinner spSolarStruct;
    CheckBox cbComm;
    CheckBox cbNonComm;
    CheckBox cbBankOfficial;
    CheckBox cbGuard;
    String strEarthVolt = "NA";
    String strBattTopup = "NA";
    String strBattCells = "NA";
    String strCharger = "NA";
    String strInverter = "NA";
    String strEbConn = "NA";
    String strConn = "NA";
    String strSignOff = "NA";
    String strCell1 = "NA";
    String strCell2 = "NA";
    String strCell3 = "NA";
    String strCell4 = "NA";
    String strCell5 = "NA";
    String strCell6 = "NA";
    String strCell7 = "NA";
    String strCell8 = "NA";
    String strSolarCleaning = "NA";
    String strSolarStructureAndPanelTightness = "NA";
    String strBatteryTerminalGreasing = "NA";
    String strPhotos = "NA";
    String strModemConn = "NA";
    String strCommStatus = "NA";
    String strSpareRequirement = "NA";
    String planId;
    ActivitySheetModel sheetModel;
    ATMDBHelper atmdbHelper;
    private String activityId = "";
    private String siteId = "";
    boolean qrCodeFlag = false;

    @Override
    protected int fragmentLayout() {
        return R.layout.pm_checklist_fragment;
    }

    @Override
    protected void loadView() {
        this.btnDone = this.findViewById(R.id.btnDone);
        this.cbEarthVolt = this.findViewById(R.id.cbEarthVolt);
        this.etEarthVolt = this.findViewById(R.id.etEarthVolt);
        this.cbBattTopup = this.findViewById(R.id.cbBattTopup);
        this.etBattTopup = this.findViewById(R.id.etBattTopup);
        this.cbBattCell = this.findViewById(R.id.cbBattCell);
        this.etBattCell = this.findViewById(R.id.etBattCell);
        this.cbCharger = this.findViewById(R.id.cbCharger);
        this.etCharger = this.findViewById(R.id.etCharger);
        this.cbInverter = this.findViewById(R.id.cbInverter);
        this.etInverter = this.findViewById(R.id.etInverter);
        this.cbEbConn = this.findViewById(R.id.cbEbConn);
        this.etEbConn = this.findViewById(R.id.etEbConn);
        this.cbConn = this.findViewById(R.id.cbConn);
        this.etConn = this.findViewById(R.id.etConn);
        this.cbSolar = this.findViewById(R.id.cbSolar);
        this.spSolar = this.findViewById(R.id.spSolar);
        this.cbCell1 = this.findViewById(R.id.cbCell1);
        this.etCell1 = this.findViewById(R.id.etCell1);
        this.cbCell2 = this.findViewById(R.id.cbCell2);
        this.etCell2 = this.findViewById(R.id.etCell2);
        this.cbCell3 = this.findViewById(R.id.cbCell3);
        this.etCell3 = this.findViewById(R.id.etCell3);
        this.cbCell4 = this.findViewById(R.id.cbCell4);
        this.etCell4 = this.findViewById(R.id.etCell4);
        this.cbCell5 = this.findViewById(R.id.cbCell5);
        this.etCell5 = this.findViewById(R.id.etCell5);
        this.cbCell6 = this.findViewById(R.id.cbCell6);
        this.etCell6 = this.findViewById(R.id.etCell6);
        this.cbCell7 = this.findViewById(R.id.cbCell7);
        this.etCell7 = this.findViewById(R.id.etCell7);
        this.cbCell8 = this.findViewById(R.id.cbCell8);
        this.etCell8 = this.findViewById(R.id.etCell8);
        this.cbPhotoYes = this.findViewById(R.id.cbPhotoYes);
        this.cbPhotoNo = this.findViewById(R.id.cbPhotoNo);
        this.cbModemConnectionYes = this.findViewById(R.id.cbModemConnectionYes);
        this.cbModemConnectionNo = this.findViewById(R.id.cbModemConnectionNo);
        this.cbSpareRequirement = this.findViewById(R.id.cbSpareRequirement);
        this.etSpareRequirement = this.findViewById(R.id.etSpareRequirement);
        this.cbBatteryTerminal = this.findViewById(R.id.cbBatteryTerminal);
        this.spSolarStruct = this.findViewById(R.id.spSolarStruct);
        this.cbSolarStruct = this.findViewById(R.id.cbSolarStruct);
        this.spBatteryTerminal = this.findViewById(R.id.spBatteryTerminal);
        this.cbComm = this.findViewById(R.id.cbComm);
        this.cbNonComm = this.findViewById(R.id.cbNonComm);
        this.cbBankOfficial = this.findViewById(R.id.cbBankOfficial);
        this.cbGuard = this.findViewById(R.id.cbGuard);


    }

    @Override
    protected void setClickListeners() {
        cbBatteryTerminal.setOnClickListener(this);
        cbSolarStruct.setOnClickListener(this);
        cbSpareRequirement.setOnClickListener(this);
        cbComm.setOnClickListener(this);
        cbNonComm.setOnClickListener(this);
        cbBankOfficial.setOnClickListener(this);
        cbGuard.setOnClickListener(this);
        cbEarthVolt.setOnClickListener(this);
        cbBattTopup.setOnClickListener(this);
        cbBattCell.setOnClickListener(this);
        cbCharger.setOnClickListener(this);
        cbInverter.setOnClickListener(this);
        cbEbConn.setOnClickListener(this);
        cbConn.setOnClickListener(this);
        cbSolar.setOnClickListener(this);
        cbCell1.setOnClickListener(this);
        cbCell2.setOnClickListener(this);
        cbCell3.setOnClickListener(this);
        cbCell4.setOnClickListener(this);
        cbCell5.setOnClickListener(this);
        cbCell6.setOnClickListener(this);
        cbCell7.setOnClickListener(this);
        cbCell8.setOnClickListener(this);
        cbPhotoNo.setOnClickListener(this);
        cbPhotoYes.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    @Override
    protected void getArgs() {
        super.getArgs();
        planId = this.getArguments().getString("PlanId");
        activityId = this.getArguments().getString("activityId");
        siteId = this.getArguments().getString("siteId");
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        /*  714	Sign Off     m         nm
        1282 Commisioning Rework    m    nm
        749	PM    m     nm
        1180 Special Project    m    nm*/

        //for these case QRCode screen non mandatory to open
        if (activityId.equals("714") || activityId.equals("1282") || activityId.equals("749") || activityId.equals("1180")) {
            btnDone.setText("Submit");
            qrCodeFlag = false;
        } else {
            qrCodeFlag = true;
            btnDone.setText("Next");
        }


        atmdbHelper = new ATMDBHelper(getContext());
        final ArrayList<String> yesNoList = new ArrayList<>();
        yesNoList.add("Yes");
        yesNoList.add("No");
        ArrayAdapter<String> yesNoAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, yesNoList);
        yesNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSolar.setAdapter(yesNoAdapter);
        spBatteryTerminal.setAdapter(yesNoAdapter);
        spSolarStruct.setAdapter(yesNoAdapter);
        spSolar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSolarCleaning = yesNoList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spBatteryTerminal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strBatteryTerminalGreasing = yesNoList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spSolarStruct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSolarStructureAndPanelTightness = yesNoList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cbComm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbNonComm.setChecked(false);
                    strCommStatus = "Comm";
                }
            }
        });
        cbNonComm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbComm.setChecked(false);
                    strCommStatus = "Non Comm";
                }
            }
        });

        cbBankOfficial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbGuard.setChecked(false);
                    strSignOff = "Bank Official";
                }
            }
        });
        cbGuard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbBankOfficial.setChecked(false);
                    strSignOff = "Guard";
                }
            }
        });
        cbPhotoNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbPhotoYes.setChecked(false);
                    strPhotos = "No";
                }
            }
        });
        cbPhotoYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbPhotoNo.setChecked(false);
                    strPhotos = "Yes";
                }
            }
        });

        cbModemConnectionYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbModemConnectionNo.setChecked(false);
                    strModemConn = "Yes";
                }
            }
        });
        cbModemConnectionNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbModemConnectionYes.setChecked(false);
                    strModemConn = "No";
                }
            }
        });
        getActivityFormData();

    }

    //get save activity form data
    private void getActivityFormData() {

        ContentLocalData localData = atmdbHelper.getActivtyFormDataByID(planId);
        if (localData != null) {
            if (localData.getActivityFormData() != null) {
                String activityFormStr = localData.getActivityFormData();
                sheetModel = new Gson().fromJson(activityFormStr, new TypeToken<ActivitySheetModel>() {
                }.getType());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDone:
                if (isValidate()) {
                    addValue();
                }
                break;
        }
    }

    // ----validation -----
    private boolean isValidate() {
        strEarthVolt = etEarthVolt.getText().toString();
        strInverter = etInverter.getText().toString();
        strBattCells = etBattCell.getText().toString();
        strCell1 = etCell1.getText().toString();
        strCell2 = etCell2.getText().toString();
        strCell3 = etCell3.getText().toString();
        strCell4 = etCell4.getText().toString();
        strEbConn = etEbConn.getText().toString();
        strBattTopup = etBattTopup.getText().toString();
        strConn = etConn.getText().toString();

        strCell5 = etCell5.getText().toString();
        strCell6 = etCell6.getText().toString();
        strCell7 = etCell7.getText().toString();
        strCell8 = etCell8.getText().toString();
        strCell5 = etCell5.getText().toString();
        strCell6 = etCell6.getText().toString();
        strCell7 = etCell7.getText().toString();
        strCell8 = etCell8.getText().toString();
        strSpareRequirement = etSpareRequirement.getText().toString();
        strCharger = etCharger.getText().toString();

        if (strEarthVolt.length() == 0) {
            ASTUIUtil.showToast("Please Provide Earthing Voltage");
            return false;
        } else if (strInverter.length() == 0) {
            ASTUIUtil.showToast("Please Provide Input for Inverter/PCU");
            return false;
        } else if (strEbConn.length() == 0) {
            ASTUIUtil.showToast("Please Provide Input for EB conn/I/P Voltage");
            return false;
        } else if (strConn.length() == 0) {
            ASTUIUtil.showToast("Please Provide Input for Connection and Tightness");
            return false;
        } else if (strSolarCleaning.length() == 0) {
            ASTUIUtil.showToast("Please Select Solar Cleaning");
            return false;
        } else if (strSolarStructureAndPanelTightness.length() == 0) {
            ASTUIUtil.showToast("Please Select Solar Struct & Panel Tightness");
            return false;
        } else if (strBattTopup.length() == 0) {
            ASTUIUtil.showToast("Please Provide Input for Battery Topup");
            return false;
        } else if (strBattCells.length() == 0) {
            ASTUIUtil.showToast("Please Provide Input for Battery Cells");
            return false;
        } else if (strBatteryTerminalGreasing.length() == 0) {
            ASTUIUtil.showToast("Please Select Battery Terminal Greasing");
            return false;
        } else if (strSolarCleaning.length() == 0) {
            ASTUIUtil.showToast("Please Select Solar Cleaning");
            return false;
        } else if (strSolarStructureAndPanelTightness.length() == 0) {
            ASTUIUtil.showToast("Please Select Solar Struct & panel Tightness");
            return false;
        } else if (strBatteryTerminalGreasing.length() == 0) {
            ASTUIUtil.showToast("Please Select Battery Termiinal Greasing");
            return false;
        } else if (strCell1.length() == 0) {
            ASTUIUtil.showToast("Please Provide Input for S.G. Cell 1");
            return false;
        } else if (strCell2.length() == 0) {
            ASTUIUtil.showToast("Please Provide Input for S.G. Cell 2");
            return false;
        } else if (strCell3.length() == 0) {
            ASTUIUtil.showToast("Please Provide Input for S.G. Cell 3");
            return false;
        } else if (strCell4.length() == 0) {
            ASTUIUtil.showToast("Please Provide Input for S.G. Cell 4");
            return false;
        } else if (strCommStatus.length() == 0) {
            ASTUIUtil.showToast("Please Select Comm Status");
            return false;
        } else if (strSignOff.length() == 0) {
            ASTUIUtil.showToast("Please Select Sign Off");
            return false;
        }
        return true;
    }

    //add value into model
    public void addValue() {
        sheetModel.setEarthVolt(strEarthVolt);
        sheetModel.setInverter(strInverter);
        sheetModel.setBattCells(strBattCells);
        sheetModel.setCell1(strCell1);
        sheetModel.setCell2(strCell2);
        sheetModel.setCell3(strCell3);
        sheetModel.setCell4(strCell4);
        sheetModel.setCell5(strCell5);
        sheetModel.setCell6(strCell6);
        sheetModel.setCell7(strCell7);
        sheetModel.setCell8(strCell8);
        sheetModel.setBatteryTerminalGreasing(strBatteryTerminalGreasing);
        sheetModel.setSolarStructureAndPanelTightness(strSolarStructureAndPanelTightness);
        sheetModel.setSpareRequirement(strSpareRequirement);
        sheetModel.setCharger(strCharger);
        sheetModel.setSolar(strSolarCleaning);
        sheetModel.setEbConn(strEbConn);
        sheetModel.setPhotos(strPhotos);
        sheetModel.setModemConn(strModemConn);
        sheetModel.setBattTopup(strBattTopup);


        SharedPreferences prefs = getContext().getSharedPreferences("ReachedSitePreferences", Context.MODE_PRIVATE);
        if (prefs != null) {
            boolean reachedOrNotFlag = prefs.getBoolean("reachedOrNotFlag", false);
            String transitSiteId = prefs.getString("siteId", "");
            if (siteId.equals(transitSiteId)) {
                if (reachedOrNotFlag) {
                    if (ASTUIUtil.isOnline(getContext())) {
                        activityFormDataServiceCall();
                    } else {
                        ASTUIUtil.showToast(Contants.OFFLINE_MESSAGE);
                        saveActivityFormDataIntoDb();
                    }
                } else {
                    reachedSiteFirstAlertMessage();
                }
            } else {
                reachedSiteFirstAlertMessage();
            }
        }

    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();

    }

    //---------------------Calling Web Service to save activity form data into server--------------------------
    public void activityFormDataServiceCall() {

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
                    if (qrCodeFlag) {
                        openQRCodeScreen();
                    } else {
                        openPlannedActivityListTabScreen();
                    }
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

    //save activity data into db
    private void saveActivityFormDataIntoDb() {
        String activityData = new Gson().toJson(sheetModel);
        ContentLocalData localData = new ContentLocalData();
        localData.setPlanId(planId);
        localData.setActivityFormData(activityData);
        atmdbHelper.upsertActivtyFormData(localData);
        ASTUIUtil.showToast("Activity Data is Saved Locally");

        if (qrCodeFlag) {
            openQRCodeScreen();
        } else {
            openPlannedActivityListTabScreen();
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

    //open QR code screen
    private void openQRCodeScreen() {
        EquipmentandAccessoriesTab qrFragment = new EquipmentandAccessoriesTab();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "Equipments");
        bundle.putString("activityId", activityId);
        bundle.putString("siteId", siteId);
        bundle.putString("planId", planId);
        getHostActivity().updateFragment(qrFragment, bundle);
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
