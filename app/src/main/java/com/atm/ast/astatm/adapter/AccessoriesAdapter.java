package com.atm.ast.astatm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.model.newmodel.AccFeedBack;
import com.atm.ast.astatm.model.newmodel.Accessories;
import com.atm.ast.astatm.model.newmodel.Make;

import java.util.ArrayList;
import java.util.List;

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.ItemViewHolder> {

    private Context mCtx;
    private ArrayList<Accessories> accessoriesArrayList;
    ArrayList<AccFeedBack> accFeedBack;
    public ArrayList<AccFeedBack> selectedAccFeedbackList = new ArrayList<AccFeedBack>();
    int position;
    private ATMDBHelper atmdbHelper;

    public AccessoriesAdapter(Context mCtx, ArrayList<Accessories> accessoriesArrayList, ArrayList<AccFeedBack> accFeedBacks) {
        this.mCtx = mCtx;
        this.accessoriesArrayList = accessoriesArrayList;
        this.accFeedBack = accFeedBacks;
        this.atmdbHelper = new ATMDBHelper(mCtx);
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
                AccFeedBack selectAccFeedBack = new AccFeedBack();
                selectAccFeedBack.setParentId(item.getId());//add Accessories id
                holder.accessoriesSpinner.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                if (isChecked) {

                    ArrayList<String> accText = new ArrayList<>();
                    ArrayList<Integer> accId = new ArrayList<>();

                    for (AccFeedBack accFeedBack : accFeedBack) {
                        accText.add(accFeedBack.getText());
                        accId.add(accFeedBack.getId());
                    }

                    ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(mCtx, R.layout.spinner_row, accText);
                    holder.accSpinner.setAdapter(homeadapter);
                    holder.accSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectAccFeedBack.setId(accId.get(i));//add FeedBack id
                            selectAccessories(true, selectAccFeedBack);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    selectAccessories(false, selectAccFeedBack);
                }
            }
        });


    }

    private void selectAccessories(boolean addOrNor, AccFeedBack selectAccFeedBack) {
        if (addOrNor) {
            atmdbHelper.upsertSelectedAccessoriesInfo(selectAccFeedBack);
        } else {
            atmdbHelper.deleteSelectedAccessoriesInfo(selectAccFeedBack);
        }
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
        Spinner accSpinner;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            imageView = itemView.findViewById(R.id.itemImage);
            checkBox = itemView.findViewById(R.id.selectItemCheckBox);
            accessoriesSpinner = itemView.findViewById(R.id.accessoriesSpinner);
            accSpinner = itemView.findViewById(R.id.accSpinner);
        }
    }

}