package com.atm.ast.astatm.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.EqupmentPagerAdapter;
import com.atm.ast.astatm.component.SwitchViewPager;
import com.atm.ast.astatm.filepicker.FNFilePicker;
import com.atm.ast.astatm.filepicker.model.MediaFile;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FNReqResCode;

import java.util.ArrayList;

public class EquipmnetMainFragment extends MainFragment {
    SwitchViewPager mPager;
    int qty;

    @Override
    protected void getArgs() {
        super.getArgs();
        qty = this.getArgsInt("qty");
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_mainequipment;
    }

    @Override
    protected void loadView() {
        mPager = this.findViewById(R.id.viewPager_itemList);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        EqupmentPagerAdapter mAdapter = new EqupmentPagerAdapter(getActivity().getSupportFragmentManager(), qty);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(2);
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
                    getHostActivity().redirectToHomeMenu();
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
        getContext().registerReceiver(receiver, new IntentFilter("ViewPageChange"));
    }

    @Override
    public void onDestroy() {
        if (receiver != null) {
            getContext().unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }


    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FNReqResCode.ATTACHMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FNFilePicker.EXTRA_SELECTED_MEDIA);

           /* int currentPagepre = mPager.getCurrentItem();
            if (currentPagepre == 0)
                SmpsFragment.getResult(files);
            else if (currentPagepre == 1) {
                PIUVoltageStablizerFragment.getResult(files);

            }*/


        }
    }

}
