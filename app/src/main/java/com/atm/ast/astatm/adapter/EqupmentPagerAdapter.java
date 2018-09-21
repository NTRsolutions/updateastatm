package com.atm.ast.astatm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.atm.ast.astatm.fragment.EquipMentBarcodeFragment;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EqupmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Equipment> mKeyList = new ArrayList<Equipment>();
    private String ticketNo = "";

    public EqupmentPagerAdapter(FragmentManager fm, String ticketNo) {
        super(fm);
        this.ticketNo = ticketNo;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        String Equipmentdata = new Gson().toJson(mKeyList.get(position));
        EquipMentBarcodeFragment tab1 = new EquipMentBarcodeFragment(Equipmentdata);
        return tab1;
    }

    @Override
    public int getCount() {
        return mKeyList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "SCOUT " + (getCount() - position);
    }

    public void add(int position, Equipment equipment) {
        mKeyList.add(position, equipment);
        notifyDataSetChanged();
    }
}