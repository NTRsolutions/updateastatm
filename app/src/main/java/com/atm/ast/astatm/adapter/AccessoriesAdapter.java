package com.atm.ast.astatm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.newmodel.Accessories;

import java.util.ArrayList;
import java.util.List;

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.ItemViewHolder> {

    private Context mCtx;
    private ArrayList<Accessories> accessoriesArrayList;

    public AccessoriesAdapter(Context mCtx, ArrayList<Accessories> accessoriesArrayList) {
        this.mCtx = mCtx;
        this.accessoriesArrayList = accessoriesArrayList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.accessories_item_row, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Accessories item = accessoriesArrayList.get(position);
        holder.name.setText(item.getName());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                holder.accessoriesSpinner.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
    }


    @Override
    public int getItemCount() {
        return accessoriesArrayList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        CheckBox checkBox;
        LinearLayout accessoriesSpinner;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            imageView = itemView.findViewById(R.id.itemImage);
            checkBox = itemView.findViewById(R.id.selectItemCheckBox);
            accessoriesSpinner = itemView.findViewById(R.id.accessoriesSpinner);

        }
    }


}