package com.atm.ast.astatm.fragment;

import android.widget.ListView;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.SiteUptimeAdapter;

public class UptimeFragment extends MainFragment {
    ListView lvUptimeDetails;


    @Override
    protected int fragmentLayout() {
        return R.layout.activity_uptime;
    }

    @Override
    protected void loadView() {
        lvUptimeDetails = findViewById(R.id.lvUptimeDetails);

    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        lvUptimeDetails.setAdapter(new SiteUptimeAdapter(getContext()));

    }
}

