package com.atm.ast.astatm.equipment.replacementequiment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.atm.ast.astatm.equipment.EquipMentBarcodeFragment;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EqupmentReplacePagerAdapter extends FragmentStatePagerAdapter {

    private String ticketNo = "";
    int postion;

    public EqupmentReplacePagerAdapter(FragmentManager fm, String ticketNo,int postion) {
        super(fm);
        this.ticketNo = ticketNo;
        this.postion=postion;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        EquipmentReplacementList tab1 = new EquipmentReplacementList(postion);
        return tab1;
    }

    @Override
    public int getCount() {
        return postion;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "SCOUT " + (getCount() - position);
    }

    public void add(int position, Equipment equipment) {
    //    mKeyList.add(position, equipment);
        notifyDataSetChanged();
    }
}