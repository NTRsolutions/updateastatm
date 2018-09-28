package com.atm.ast.astatm.equipment.replacementequiment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.newmodel.Equipment;
import java.util.ArrayList;

public class AllEquipmentListAdapter extends RecyclerView.Adapter<AllEquipmentListAdapter.ItemViewHolder> {

    private Context mCtx;
    private ArrayList<Equipment> equipmentList;

    public AllEquipmentListAdapter(Context mCtx, ArrayList<Equipment> equipmentList) {
        this.mCtx = mCtx;
        this.equipmentList = equipmentList;
    }

    @Override
    public AllEquipmentListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycle_item_row, null);
        return new AllEquipmentListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllEquipmentListAdapter.ItemViewHolder holder, int position) {
        Equipment item = equipmentList.get(position);
        holder.name.setText(item.getName());
        if (equipmentList.get(position).getId() == 68) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_power_bank));
        } else if (equipmentList.get(position).getId() == 580) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_battery));
        } else if (equipmentList.get(position).getId() == 597) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_sim));
        } else if (equipmentList.get(position).getId() == 608) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_solar_panels_couple_in_sunlight));
        } else if (equipmentList.get(position).getId() == 901) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_battery_with));
        } else if (equipmentList.get(position).getId() == 923) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_battery_with));
        } else if (equipmentList.get(position).getId() == 938) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_battery_with));
        } else if (equipmentList.get(position).getId() == 963) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_battery_with));
        } else if (equipmentList.get(position).getId() == 974) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_diagram));
        } else if (equipmentList.get(position).getId() == 1004) {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_battery_with));
        }
        if (equipmentList.get(position).isSelectedOrNote()) {
            holder.checkBox.setChecked(true);
            holder.checkBox.setEnabled(false);
        } else {
            holder.checkBox.setChecked(false);
            holder.checkBox.setEnabled(false);
        }
        holder.cardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int equID = equipmentList.get(position).getId();
                Bundle bundle = new Bundle();
                EquipmentReplacementTab equipmentReplacementTab = new EquipmentReplacementTab();
                bundle.putString("headerTxt", "Equipment Add & Replacement");
                bundle.putBoolean("showMenuButton", false);
                bundle.putInt("equipmentId", equID);
                ApplicationHelper.application().getActivity().updateFragment(equipmentReplacementTab, bundle);
            }
        });



    }


    @Override
    public int getItemCount() {
        return equipmentList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        CheckBox checkBox;
        CardView cardViewLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            imageView = itemView.findViewById(R.id.itemImage);
            checkBox = itemView.findViewById(R.id.selectItemCheckBox);
            cardViewLayout = itemView.findViewById(R.id.cardViewLayout);

        }
    }

}
