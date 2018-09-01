package com.atm.ast.astatm.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.utils.ASTReqResCode;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.content.Context.MODE_PRIVATE;
import static com.atm.ast.astatm.utils.FNObjectUtil.isEmptyStr;

public class EquipMentBarcodeFragment extends MainFragment {
    AppCompatEditText etmake, etqty, etCapacity, etSerailNumber, etcapacitypanel, etSCMCode, etSCMDiscription, etQrCodeScreen;
            AppCompatTextView etQrCodestore;
    Button btnSubmit;
    String strCapacity, strSerailNumber, strcapacitypanel, strSCMCode, strSCMDiscription, strQrCodeScreen, strQrCodestore;
    String strUserId, strSiteId;
    SharedPreferences userPref;
    ImageView qrCodeImage;
    String searchWord;

    @Override
    protected int fragmentLayout() {
        return R.layout.eqibarcode_fragment;
    }

    @Override
    protected void loadView() {
        etmake = findViewById(R.id.etmake);
        etqty = findViewById(R.id.etqty);
        this.etCapacity = findViewById(R.id.etCapacity);
        this.etSerailNumber = findViewById(R.id.etSerailNumber);
        this.etcapacitypanel = findViewById(R.id.etcapacitypanel);
        this.etSCMCode = findViewById(R.id.etSCMCode);
        this.etSCMDiscription = findViewById(R.id.etSCMDiscription);
        this.etQrCodeScreen = findViewById(R.id.etQrCodeScreen);
        this.etQrCodestore = findViewById(R.id.etQrCodestore);
        qrCodeImage= findViewById(R.id.qrCodeImage);
        //this.btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
       // btnSubmit.setOnClickListener(this);
        qrCodeImage.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getSharedPrefSaveData();
        getUserPref();

        if (!isEmptyStr(strCapacity) || !isEmptyStr(strSerailNumber) ||
                !isEmptyStr(strcapacitypanel) || !isEmptyStr(strSCMCode)) {

        }

    }
    /*
     *
     * get Data in Shared Pref.
     */

    public void getSharedPrefSaveData() {
    /*    solarPannelSharedPref = getContext().getSharedPreferences("SolarPannelSharedPref", MODE_PRIVATE);
        strNoofPanel = solarPannelSharedPref.getString("SOLARPAN_NoofPanel", "");
        serMake = solarPannelSharedPref.getString("SOLARPAN_Make", "");
        strcapacitypanel = solarPannelSharedPref.getString("SOLARPAN_capacitypanel", "");
        strAgb = solarPannelSharedPref.getString("SOLARPAN_noOfAgb", "");*/
    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
    }


    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.qrCodeImage){
            getHostActivity().requestPermission(ASTReqResCode.PERMISSION_REQ_CAMERA);
        }
       /* if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {

            }
        }*/
    }


    public boolean isValidate() {
        strCapacity = getTextFromView(this.etCapacity);
        strSerailNumber = getTextFromView(this.etSerailNumber);
        strcapacitypanel = getTextFromView(this.etcapacitypanel);
        strSCMCode = getTextFromView(this.etSCMCode);
        strSCMDiscription = getTextFromView(this.etSCMDiscription);
        strQrCodeScreen = getTextFromView(this.etQrCodeScreen);
        strQrCodestore = getTextFromView(this.etQrCodestore);
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
        } else if (isEmptyStr(strQrCodestore)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No  Qr Code store)");
            return false;
        }

        return true;
    }
    @Override
    public void permissionGranted(int requestCode) {
        super.permissionGranted(requestCode);
        if (requestCode == ASTReqResCode.PERMISSION_REQ_CAMERA) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(ApplicationHelper.application().getActivity());
            scanIntegrator.initiateScan();
            ASTUIUtil.showToast("granteed");
        }
    }


    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        super.updateOnResult(requestCode, resultCode, data);
        if (data != null && requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                this.searchWord = scanContent;
                ASTUIUtil.showToast("update result");
                ASTUIUtil.showToast(scanContent);
                etQrCodestore.setText(scanContent);
            } else {
                ASTUIUtil.showToast("no bar");
            }
        }
    }

}
