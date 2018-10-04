package com.atm.ast.astatm.equipment.replacementequiment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.utils.FontManager;

import java.util.ArrayList;

public class EquipmentReplacementActivity extends AppCompatActivity implements View.OnClickListener {
    SwitchViewPager mPager;
    private Toolbar toolbar;
    int equID;
    FloatingActionButton addMoreEqi;
    EqupmentReplacePagerAdapter mAdapter;
    TextView title;
    String EquipId, MakeId, CapacityId, SerialNo, SCMDescId, SCMCodeId, QRCode, remarke;
    int noofPage = 5;
    private ATMDBHelper atmdbHelper;
    ArrayList<Equipment> equipmentlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_bar);
        loadView();
        setSupportActionBar(toolbar);
        Typeface materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(this, "fonts/materialdesignicons-webfont.otf");
        TextView back = toolbar.findViewById(R.id.back);
        title = this.findViewById(R.id.title);
        title.setText("Equipment  Replacement");
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
        equID = getIntent().getIntExtra("equipmentId", 2);
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

    Equipment equipment;

    protected void dataToView() {
        getArgs();
        getSharedPrefData();



        atmdbHelper = new ATMDBHelper(EquipmentReplacementActivity.this);
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

        mAdapter = new EqupmentReplacePagerAdapter(getSupportFragmentManager(), "", noofPage);
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
                    if (noofPage == screenPosition) {
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
            Intent intent = new Intent(EquipmentReplacementActivity.this, NewEquipment.class);
            intent.putExtra("headerTxt", "Add New Equipment");
            intent.putExtra("showMenuButton", false);
            intent.putExtra("equipmentId", equID);
            this.startActivity(intent);
        }
    }


}
