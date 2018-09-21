package com.atm.ast.astatm.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.atm.ast.astatm.R;

import java.util.ArrayList;
import java.util.List;

public class EquipmentandAccessoriesTab extends MainFragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    Button btnSubmit;

    @Override
    protected int fragmentLayout() {
        return R.layout.equipmentaccesso_tav_fragment;
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EquipmnetQRCodeMain(), "Equipmnet");
        adapter.addFragment(new AccessoriesFragment(), "Accessories");
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
}