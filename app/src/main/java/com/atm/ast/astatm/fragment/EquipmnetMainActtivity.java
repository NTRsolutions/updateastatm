package com.atm.ast.astatm.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.EqupmentPagerAdapter;
import com.atm.ast.astatm.component.SwitchViewPager;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmentInfo;
import com.atm.ast.astatm.model.newmodel.Header;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FontManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class EquipmnetMainActtivity extends AppCompatActivity implements View.OnClickListener {
    SwitchViewPager mPager;
    //int qty;

    private Toolbar toolbar;
    int equID;
    FloatingActionButton addMoreEqi;
    EqupmentPagerAdapter mAdapter;
    int currentPagepre;
    private ATMDBHelper atmdbHelper;
    ArrayList<EquipmentInfo> equipmentlist = new ArrayList<EquipmentInfo>();
    SharedPreferences pref;
    String userId, userRole, userAccess;
    String userName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mainequipment);
        loadView();
        setSupportActionBar(toolbar);
        Typeface materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(this, "fonts/materialdesignicons-webfont.otf");
        TextView back = toolbar.findViewById(R.id.back);
        back.setTypeface(materialdesignicons_font);
        back.setText(Html.fromHtml("&#xf04e;"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setClickListeners();
    }


    protected void getArgs() {
        // qty = getIntent().getIntExtra("qty", 1);
        equID = getIntent().getIntExtra("equipmentId", 0);
    }

    protected void loadView() {
        mPager = this.findViewById(R.id.viewPager_itemList);
        addMoreEqi = this.findViewById(R.id.addMoreEqi);
        toolbar = findViewById(R.id.toolbar);
        dataToView();

    }

    protected void setClickListeners() {

        addMoreEqi.setOnClickListener(this);
    }


    protected void dataToView() {
        getArgs();
        getSharedPrefData();
        /*equipmentlist = new Gson().fromJson(equipmentListData, new TypeToken<ArrayList<EquipmentInfo>>() {
        }.getType());*/
        ATMDBHelper atmdbHelper=new ATMDBHelper(EquipmnetMainActtivity.this);
        equipmentlist=atmdbHelper.getDispatchEquipmentData(String.valueOf(0));
        mAdapter = new EqupmentPagerAdapter(getSupportFragmentManager(), "");
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(equipmentlist.size());
        if (equipmentlist == null || equipmentlist.size() == 0) {
            for (int i = 0; i < equipmentlist.size(); i++) {
                EquipmentInfo equipment = new EquipmentInfo();
                //  equipment.setQty(qty);
                equipment.setEquipId(String.valueOf(equID));
                mAdapter.add(i, equipment);
            }


            atmdbHelper = new ATMDBHelper(EquipmnetMainActtivity.this);
        }
    }
    public void getSharedPrefData() {
        //Shared Prefrences
        pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        userId = pref.getString("userId", "");
        userName = pref.getString("userName", "");
        userAccess = pref.getString("userAccess", "");
        userRole = pref.getString("roleId", "");
    }
    private List<EquipmentInfo> allDataList;
    String EquipId, MakeId, CapacityId, SerialNo, SCMDescId, SCMCodeId, QRCode, remarke;

    //for geting next previous click action
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("ViewPageChange")) {
                boolean DoneFlag = intent.getBooleanExtra("DoneFlag", false);
                boolean NextPreviousFlag = intent.getBooleanExtra("NextPreviousFlag", false);
                String ActivityId = intent.getStringExtra("ActivityId");
                String PlanId = intent.getStringExtra("PlanId");
                String SiteId = intent.getStringExtra("SiteId");
                String FeId = intent.getStringExtra("FeId");

                allDataList = atmdbHelper.getEquipmentInfoData();
                if (allDataList != null) {
                    for (EquipmentInfo equipmentInfo : allDataList) {
                        EquipId = equipmentInfo.getEquipId();
                        MakeId = equipmentInfo.getMakeId();
                        CapacityId = equipmentInfo.getCapacityId();
                        SerialNo = equipmentInfo.getSerialNo();
                        SCMDescId = equipmentInfo.getSCMDescId();
                        SCMCodeId = equipmentInfo.getSCMCodeId();
                        QRCode = equipmentInfo.getQRCode();
                        remarke = equipmentInfo.getRemarke();
                    }
                    EquipmentInfo equipmentInfo = new EquipmentInfo();
                    equipmentInfo.setEquipId(EquipId);
                    equipmentInfo.setMakeId(MakeId);
                    equipmentInfo.setCapacityId(CapacityId);
                    equipmentInfo.setSerialNo(SerialNo);
                    equipmentInfo.setSCMDescId(SCMDescId);
                    equipmentInfo.setSCMCodeId(SCMCodeId);
                    equipmentInfo.setQRCode(QRCode);
                    equipmentInfo.setRemarke(remarke);
                    if (DoneFlag) {
                        //equipment.setDoneFlag(DoneFlag);//for add only fsr images in data
                    }

                    //  checkEquipmentExistOrNotAndAdd(equipment);
                    if (DoneFlag && NextPreviousFlag) {
                        // moveToConfirmScreen();
                    } else if (NextPreviousFlag) {
                        int currentPage = mPager.getCurrentItem();
                        mPager.setCurrentItem(currentPage + 1, true);
                    } else {
                        int currentPagepre = mPager.getCurrentItem();
                        if (currentPagepre > 0) {
                            mPager.setCurrentItem(currentPagepre - 1, true);
                        }
                    }

                }
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter("ViewPageChange"));
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addMoreEqi) {
            setEqiAdapter();

        }
    }

    public void setEqiAdapter() {
        mPager.setOffscreenPageLimit(equipmentlist.size());
        EquipmentInfo equipment = new EquipmentInfo();
        equipment.setId(equID);
        mAdapter.add(equipmentlist.size(), equipment);
        ASTUIUtil.showToast("New Item Added");

    }

}
