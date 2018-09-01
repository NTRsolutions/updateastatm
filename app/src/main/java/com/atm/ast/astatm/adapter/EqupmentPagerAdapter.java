package com.atm.ast.astatm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.atm.ast.astatm.fragment.EquipMentBarcodeFragment;

public class EqupmentPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfPage;
    int qty;

    public EqupmentPagerAdapter(FragmentManager fm, int qty) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                EquipMentBarcodeFragment tab1 = new EquipMentBarcodeFragment();
                return tab1;
            case 1:
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfPage;
    }


}

