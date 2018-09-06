package com.atm.ast.astatm.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.SyncFormDataWithServerIntentService;

import java.util.ArrayList;
import java.util.List;

public class PlannedActivityListTabFragment extends MainFragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    String con = "";

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_planned_activity_list_tab;
    }

    @Override
    protected void getArgs() {
        con = this.getArguments().getString("condition");
    }

    @Override
    protected void loadView() {
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabs);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PlannedActivityListFragment(), "Planned");
        adapter.addFragment(new ActivitySheetReportFragment(), "Executed");
        adapter.addFragment(new FeTrackerFragment(), "FE Tracker");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Intent intentService = new Intent(getHostActivity(), SyncFormDataWithServerIntentService.class);
        getContext().startService(intentService);

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
