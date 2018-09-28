package com.atm.ast.astatm.equipment.replacementequiment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.fragment.MainFragment;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.EquipmentInfo;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EquipmentReplacementTab extends MainFragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    Button btnSubmit;
    ATMDBHelper atmdbHelper;
    ViewPagerAdapter adapter;

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_equipment_replacement_tab;
    }

    @Override
    protected void getArgs() {
    }

    @Override
    protected void loadView() {
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabs);
        btnSubmit = this.findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }


    @Override
    protected void dataToView() {
        atmdbHelper = new ATMDBHelper(getContext());
        setPage();
    }

    private void setPage() {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EquipmentReplacementList(), "Equipment Replacement ");
        adapter.addFragment(new NewEquipment(), "New Equipment");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Adapter for the viewpager using FragmentPagerAdapter
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<MainFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public MainFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(MainFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                break;
        }
    }

}