package com.atm.ast.astatm.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.EqupmentPagerAdapter;
import com.atm.ast.astatm.component.SwitchViewPager;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FontManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class EquipmnetMainActtivity extends AppCompatActivity implements View.OnClickListener {
    SwitchViewPager mPager;
    int qty;
    TextView previous, next;
    private Toolbar toolbar;
    int equID;
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
        qty = getIntent().getIntExtra("qty", 0);
        equID = getIntent().getIntExtra("equipmentId", 0);
    }

    protected void loadView() {
        mPager = this.findViewById(R.id.viewPager_itemList);
        previous = this.findViewById(R.id.previous);
        next = this.findViewById(R.id.next);
        toolbar = findViewById(R.id.toolbar);
        dataToView();
    }

    protected void setClickListeners() {
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    protected void setAccessibility() {
        int currentPagepre = mPager.getCurrentItem();
            previous.setVisibility(currentPagepre == 1 ?View.GONE:View.VISIBLE);
            next.setVisibility(currentPagepre == qty ?View.GONE:View.VISIBLE);
    }

    protected void dataToView() {
        getArgs();
        Equipment equipment = new Equipment();
        equipment.setQty(qty);
        equipment.setId(equID);
        List<Equipment> equipmentist = new ArrayList<Equipment>();
        EqupmentPagerAdapter mAdapter = new EqupmentPagerAdapter(getSupportFragmentManager(), "");
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(qty);
        for (int i = 0; i < qty; i++) {
            mAdapter.add(i, equipment);
        }
    }

    //for geting next previous click action
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("ViewPageChange")) {
                boolean DoneFlag = intent.getBooleanExtra("DoneFlag", false);
                boolean NextPreviousFlag = intent.getBooleanExtra("NextPreviousFlag", false);
                if (NextPreviousFlag) {
                    int currentPage = mPager.getCurrentItem();
                    mPager.setCurrentItem(currentPage + 1, true);
                } else if (DoneFlag) {
                    ASTUIUtil.showToast("Done All Page");
                    //redirectToHomeMenu();
                } else {
                    int currentPagepre = mPager.getCurrentItem();
                    if (currentPagepre > 0) {
                        mPager.setCurrentItem(currentPagepre - 1, true);
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
    public void onDestroy() {
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
        if (view.getId() == R.id.previous) {
            int currentPagepre = mPager.getCurrentItem();
            if (currentPagepre > 0) {
                mPager.setCurrentItem(currentPagepre - 1, true);
            }
        } else if (view.getId() == R.id.next) {
            int currentPage = mPager.getCurrentItem();
            mPager.setCurrentItem(currentPage + 1, true);
        }
    }
}
