package com.atm.ast.astatm.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.model.newmodel.Capacity;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.model.newmodel.Make;
import com.atm.ast.astatm.model.newmodel.SCMCode;
import com.atm.ast.astatm.model.newmodel.SCMDescription;
import com.atm.ast.astatm.utils.ASTReqResCode;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FNObjectUtil;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.atm.ast.astatm.utils.FNObjectUtil.isEmptyStr;

@SuppressLint("ValidFragment")
public class EquipMentBarcodeFragment extends Fragment implements View.OnClickListener {
    private AppCompatEditText etSerailNumber;
    private Spinner etSCMCode, etSCMDiscription;
    private Spinner etCapacity;
    private AppCompatEditText etRemarks;
    private AppCompatEditText etQrCodeScreen;
    private Button btnSubmit;
    private String  strmakeSpinner,strCapacity, strSerailNumber, strcapacitypanel, strSCMCode, strSCMDiscription, strQrCodeScreen, stretRemarks;
    private String strUserId, strSiteId;
    private SharedPreferences userPref;
    private ImageView qrCodeImage;
    private List<Data> allDataList;
    private ATMDBHelper atmdbHelper;
    Spinner makeSpinner;
    private View view;
    private Context context;
    int qty;
    int makeID;
    String Equipmentdataa;
    int capacityId;
    EquipmnetContentData contentDataa;
    Equipment equipmentdata;
    @SuppressLint("ValidFragment")
    public EquipMentBarcodeFragment(String Equipmentdata) {
        this.Equipmentdataa = Equipmentdata;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.eqibarcode_fragment, container, false);
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
        equipmentdata = new Gson().fromJson(Equipmentdataa, Equipment.class);
    }

    protected void setClickListeners() {
        qrCodeImage.setOnClickListener(this);
    }

    protected void setAccessibility() {

    }


    protected void dataToView() {
        getUserPref();
        atmdbHelper = new ATMDBHelper(getContext());
        allDataList = new ArrayList<>();
        allDataList = atmdbHelper.getAllEquipmentListData();
        if (allDataList != null) {
            for (Data dataModel : allDataList) {
                contentDataa = dataModel.getEquipmnetContentData();
            }

        }
        setMakeData();

    }

    /**
     * set Make Data Spinner value
     */

    public void setMakeData() {
        ArrayList<String> makeList = new ArrayList<>();
        ArrayList<Integer> makeIdList = new ArrayList<>();
            for (Make make : contentDataa.getMake()) {
                if (equipmentdata.getId() == make.getEqId()) {
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

    /**
     * set Capacity Data Spinner value
     */

    public void setCpacityData() {
        ArrayList<String> capacityList = new ArrayList<>();
        ArrayList<Integer> capacityIdList = new ArrayList<>();
        for (Capacity capacity : contentDataa.getCapacity()) {
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

    /**
     * set Data
     */
    public void setScMCodeData() {
        ArrayList<String> scmcodeList = new ArrayList<>();
        for (SCMCode scmCode : contentDataa.getSCMCode()) {
            if (capacityId == scmCode.getCapcityId()) {
                scmcodeList.add(scmCode.getName());
            }
        }
        ArrayAdapter<String> scmeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, scmcodeList);
        etSCMCode.setAdapter(scmeadapter);


    }


    public void setscmcodeiscriptionData() {
        ArrayList<String> scmcodeiscriptionList = new ArrayList<>();
        for (SCMDescription scmDescription : contentDataa.getSCMDescription()) {
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
        }
    }


    public boolean isValidate() {

        strCapacity = FNObjectUtil.getTextFromView(this.etCapacity);
        strSerailNumber = FNObjectUtil.getTextFromView(this.etSerailNumber);
        strSCMCode = FNObjectUtil.getTextFromView(this.etSCMCode);
        strSCMDiscription = FNObjectUtil.getTextFromView(this.etSCMDiscription);
        strQrCodeScreen = FNObjectUtil.getTextFromView(this.etQrCodeScreen);
        stretRemarks = FNObjectUtil.getTextFromView(this.etRemarks);
        if (isEmptyStr(strCapacity)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity");
            return false;
        } else if (isEmptyStr(strSerailNumber)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter SerailNumber");
            return false;
        } else if (isEmptyStr(strcapacitypanel)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity panel");
            return false;
        } else if (isEmptyStr(strSCMCode)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No  SCM Code");
            return false;
        } else if (isEmptyStr(strSCMDiscription)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No  SCMDiscription");
            return false;
        } else if (isEmptyStr(strQrCodeScreen)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No QrCodeScreen ");
            return false;
        } else if (isEmptyStr(stretRemarks)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No  Qr Code store)");
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

}
