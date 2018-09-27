package com.atm.ast.astatm.equipment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.SwitchViewPager;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmentInfo;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FontManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EquipmentBarActivity extends AppCompatActivity implements View.OnClickListener {
    SwitchViewPager mPager;
    private Toolbar toolbar;
    int equID;
    FloatingActionButton addMoreEqi;
    EqupmentPagerAdapter mAdapter;
    private ATMDBHelper atmdbHelper;
    ArrayList<Equipment> equipmentlist;
    SharedPreferences pref;
    String userId, userRole, userAccess;
    String userName = "";

    String EquipId, MakeId, CapacityId, SerialNo, SCMDescId, SCMCodeId, QRCode, remarke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_bar);
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
        atmdbHelper = new ATMDBHelper(EquipmentBarActivity.this);
        equipmentlist = new ArrayList<Equipment>();
        // equipmentlist = atmdbHelper.getDispatchEquipmentData(0);
        ArrayList<Data> allEquipmentList = atmdbHelper.getAllEquipmentListData();
        if (allEquipmentList != null) {
            for (Data dataModel : allEquipmentList) {
                EquipmnetContentData contentData = dataModel.getEquipmnetContentData();
                if (contentData != null) {
                    for (Equipment equipment : contentData.getEquipment()) {
                        if (equipment.getId() == equID) {
                            equipmentlist.add(equipment);
                        }
                    }
                }
            }
        }

        mAdapter = new EqupmentPagerAdapter(getSupportFragmentManager(), "");
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(equipmentlist.size());
        if (equipmentlist == null || equipmentlist.size() > 0) {
            for (int i = 0; i < equipmentlist.size(); i++) {
                mAdapter.add(i, equipmentlist.get(i));
            }
        }
    }

    //get use data
    public void getSharedPrefData() {
        pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        userId = pref.getString("userId", "");
        userName = pref.getString("userName", "");
        userAccess = pref.getString("userAccess", "");
        userRole = pref.getString("roleId", "");
    }

    //for geting next previous click action
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("ViewPageChange")) {
                boolean NextPreviousFlag = intent.getBooleanExtra("NextPreviousFlag", false);
                int screenPosition = intent.getIntExtra("screenPosition", 0);



                    int currentPage = mPager.getCurrentItem();
                    if (NextPreviousFlag) {
                        if (equipmentlist.size() == screenPosition) {
                            finish();
                        } else {
                            mPager.setCurrentItem(currentPage + 1, true);
                        }
                    } else {
                        if (currentPage > 0) {
                            mPager.setCurrentItem(currentPage - 1, true);
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
            addNewEquipmentScreen();

        }
    }

    public void addNewEquipmentScreen() {
        Equipment equipment = new Equipment();
        equipment.setId(equID);
        //equipment.setLastPage(true);
        equipmentlist.add(equipment);
        mPager.setOffscreenPageLimit(equipmentlist.size());
        mAdapter.add(equipmentlist.size() - 1, equipment);
        ASTUIUtil.showToast("New Equipment Added");

    }

}
