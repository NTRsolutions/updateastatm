package com.atm.ast.astatm.equipment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.model.newmodel.Capacity;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmentInfo;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.model.newmodel.Make;
import com.atm.ast.astatm.model.newmodel.SCMCode;
import com.atm.ast.astatm.model.newmodel.SCMDescription;
import com.atm.ast.astatm.utils.ASTObjectUtil;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FNObjectUtil;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class EquipMentBarcodeFragment extends Fragment implements View.OnClickListener {
    private AppCompatEditText etSerailNumber;
    private Spinner etSCMCode, etSCMDiscription;
    private Spinner etCapacity;
    private AppCompatEditText etRemarks;
    private AppCompatEditText etQrCodeScreen;
    private Button btnSubmit;
    private String strmakeSpinner, strCapacity, strSerailNumber, strcapacitypanel, strSCMCode, strSCMDiscription, strQrCodeScreen, stretRemarks;
    private String strUserId, strSiteId;
    private SharedPreferences userPref;
    private ImageView qrCodeImage;
    private List<Data> allEquipmentList;
    private ATMDBHelper atmdbHelper;
    Spinner makeSpinner;
    private View view;
    private Context context;
    int makeID;
    String EquipmentdataStr;
    int capacityId;
    EquipmnetContentData equipmentData;
    Equipment selectedEquipmentdData;
    int screenPosition;
    TextView previous, next, done;

    @SuppressLint("ValidFragment")
    public EquipMentBarcodeFragment(String Equipmentdata, int postion) {
        this.EquipmentdataStr = Equipmentdata;
        this.screenPosition = postion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.equipment_barcode_fragment, container, false);
        loadView();
        dataToView();
        setClickListeners();
        return view;
    }

    protected void loadView() {
        this.etCapacity = view.findViewById(R.id.etCapacity);
        this.etSerailNumber = view.findViewById(R.id.etSerailNumber);
        this.etSCMCode = view.findViewById(R.id.etSCMCode);
        this.etSCMDiscription = view.findViewById(R.id.etSCMDiscription);
        this.etQrCodeScreen = view.findViewById(R.id.etQrCodeScreen);
        this.etRemarks = view.findViewById(R.id.etRemarks);
        qrCodeImage = view.findViewById(R.id.qrCodeImage);
        makeSpinner = view.findViewById(R.id.makeSpinner);
        //this.btnSubmit = findViewById(R.id.btnSubmit);
        selectedEquipmentdData = new Gson().fromJson(EquipmentdataStr, Equipment.class);
        previous = view.findViewById(R.id.previous);
        next = view.findViewById(R.id.next);
        done = view.findViewById(R.id.done);

    }

    protected void setClickListeners() {
        qrCodeImage.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        done.setOnClickListener(this);
    }

    protected void setAccessibility() {
        previous.setVisibility(screenPosition == 1 ? View.INVISIBLE : View.VISIBLE);
    }


    protected void dataToView() {
        getUserPref();
        atmdbHelper = new ATMDBHelper(getContext());
        allEquipmentList = new ArrayList<>();
        allEquipmentList = atmdbHelper.getAllEquipmentListData();
        if (allEquipmentList != null) {
            for (Data dataModel : allEquipmentList) {
                equipmentData = dataModel.getEquipmnetContentData();
            }
            setMakeData();
            setAccessibility();
        }

    }

    /**
     * set Make Data Spinner value
     */

    public void setMakeData() {
        ArrayList<String> makeList = new ArrayList<>();
        ArrayList<Integer> makeIdList = new ArrayList<>();
        if (equipmentData.getMake() != null) {
            for (Make make : equipmentData.getMake()) {
                if (selectedEquipmentdData.getId() == make.getEqId()) {
                    makeList.add(make.getName());
                    makeIdList.add(make.getId());
                }
            }
            ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, makeList);
            makeSpinner.setAdapter(homeadapter);
            makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> arg0, View arg1, int postion, long arg3) {
                    makeID = makeIdList.get(postion);
                    Log.d(Contants.LOG_TAG, "makelist.get(arg2)" + makeIdList.get(postion));
                    setCpacityData();

                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });
        }

    }

    /**
     * set Capacity Data Spinner value
     */

    public void setCpacityData() {
        ArrayList<String> capacityList = new ArrayList<>();
        ArrayList<Integer> capacityIdList = new ArrayList<>();
        if (equipmentData.getCapacity() != null) {
            for (Capacity capacity : equipmentData.getCapacity()) {
                if (makeID == capacity.getMakeId()) {
                    capacityList.add(capacity.getName());
                    capacityIdList.add(capacity.getId());
                }
            }
            ArrayAdapter<String> capacityAdtapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, capacityList);
            etCapacity.setAdapter(capacityAdtapter);


            etCapacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> arg0, View arg1, int postion, long arg3) {
                    capacityId = capacityIdList.get(postion);
                    Log.d(Contants.LOG_TAG, "capacityIdList.get(arg2)8888888888" + capacityIdList.get(postion));
                    setScMCodeData();
                    setscmcodeiscriptionData();
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });
        }

    }

    /**
     * set scm code Data
     */
    public void setScMCodeData() {
        ArrayList<String> scmcodeList = new ArrayList<>();
        for (SCMCode scmCode : equipmentData.getSCMCode()) {
            if (capacityId == scmCode.getCapcityId()) {
                scmcodeList.add(scmCode.getName());
            }
        }
        ArrayAdapter<String> scmeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, scmcodeList);
        etSCMCode.setAdapter(scmeadapter);
    }


    public void setscmcodeiscriptionData() {
        ArrayList<String> scmcodeiscriptionList = new ArrayList<>();
        for (SCMDescription scmDescription : equipmentData.getSCMDescription()) {
            if (capacityId == scmDescription.getCapcityId()) {
                scmcodeiscriptionList.add(scmDescription.getName());
            }
        }
        ArrayAdapter<String> scmcodedesdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, scmcodeiscriptionList);
        etSCMDiscription.setAdapter(scmcodedesdapter);


    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.qrCodeImage) {
            //  getHostActivity().requestPermission(ASTReqResCode.PERMISSION_REQ_CAMERA);
         /*   IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
            scanIntegrator.initiateScan();*/
            IntentIntegrator.forSupportFragment(EquipMentBarcodeFragment.this).initiateScan();
            ASTUIUtil.showToast("granteed");
        } else if (view.getId() == R.id.previous) {
            saveScreenData(false);
        } else if (view.getId() == R.id.next) {
           /* if (isValidate()) {
                saveScreenData(true, false);
            }*/
            saveScreenData(true);
        }
    }


    public boolean isValidate() {
        // strmakeSpinner = makeSpinner.getSelectedItem().toString();
        //strCapacity = etCapacity.getSelectedItem().toString();
        strSerailNumber = FNObjectUtil.getTextFromView(this.etSerailNumber);
        // strSCMCode = etSCMCode.getSelectedItem().toString();
        // strSCMDiscription = this.etSCMDiscription.getSelectedItem().toString();
        strQrCodeScreen = FNObjectUtil.getTextFromView(this.etQrCodeScreen);
        stretRemarks = FNObjectUtil.getTextFromView(this.etRemarks);
        if (ASTObjectUtil.isEmptyStr(strCapacity)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity");
            ASTUIUtil.showToast("Please Enter Capacity");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(strSerailNumber)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter SerailNumber");
            ASTUIUtil.showToast("Please Enter SerailNumber");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(strSCMCode)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select No  SCM Code");
            ASTUIUtil.showToast("Please  Select No  SCM Code");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(strSCMDiscription)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No  SCMDiscription");
            ASTUIUtil.showToast("Please Enter SCMDiscription");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(strQrCodeScreen)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No QrCodeScreen ");
            ASTUIUtil.showToast("Please Enter QrCodeScreen");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(stretRemarks)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No  Qr Code store)");
            ASTUIUtil.showToast("Please Enter  Qr Code store");
            return false;
        }

        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                ASTUIUtil.showToast(scanContent);
                this.etRemarks.setText(scanContent);
                this.etQrCodeScreen.setText(scanContent);
            } else {
                ASTUIUtil.showToast("no bar");
            }
        }
    }

    //save data into db
    private void saveEquipmentInfo(String EquipId, String MakeId, String CapacityId, String SerialNo,
                                   String SCMDescId, String SCMCodeId, String QRCode, String remarke) {
        EquipmentInfo equipmentInfo = new EquipmentInfo();
        equipmentInfo.setId(screenPosition);
        equipmentInfo.setEquipId(EquipId);
        equipmentInfo.setMakeId(MakeId);
        equipmentInfo.setCapacityId(CapacityId);
        equipmentInfo.setSerialNo(SerialNo);
        equipmentInfo.setSCMDescId(SCMDescId);
        equipmentInfo.setSCMCodeId(SCMCodeId);
        equipmentInfo.setQRCode(QRCode);
        equipmentInfo.setRemarke(remarke);
        atmdbHelper.upsertEquipmentInfoData(equipmentInfo);//save in data base
    }

    private void saveScreenData(boolean NextPreviousFlag) {
        Intent intent = new Intent("ViewPageChange");
        intent.putExtra("NextPreviousFlag", NextPreviousFlag);
        intent.putExtra("screenPosition", screenPosition);
        saveEquipmentInfo(selectedEquipmentdData.getId() + "", strmakeSpinner, strCapacity, strSerailNumber, strSCMDiscription, strSCMCode,
                strQrCodeScreen, stretRemarks);

        getActivity().sendBroadcast(intent);

    }

}
