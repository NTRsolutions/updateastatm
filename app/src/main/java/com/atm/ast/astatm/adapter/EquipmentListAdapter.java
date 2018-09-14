package com.atm.ast.astatm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.fragment.EquipmnetMainActtivity;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.model.newmodel.Make;
import com.atm.ast.astatm.utils.ASTUIUtil;

import java.util.ArrayList;
import java.util.List;

public class EquipmentListAdapter extends RecyclerView.Adapter<EquipmentListAdapter.ItemViewHolder> {

    private Context mCtx;
    private ArrayList<Equipment> equipmentList;
    private List<Data> allDataList;
    private int makeId;

    public EquipmentListAdapter(Context mCtx, ArrayList<Equipment> equipmentList, List<Data> allDataList) {
        this.mCtx = mCtx;
        this.equipmentList = equipmentList;
        this.allDataList = allDataList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycle_item_row, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
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
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    showEquipmentDetailsInputDialog(position);
                }
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

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            imageView = itemView.findViewById(R.id.itemImage);
            checkBox = itemView.findViewById(R.id.selectItemCheckBox);

        }
    }

    /**
     * Show Dialog for Equipment Quantity and Selection Make
     *
     * @param postion
     */


    public void showEquipmentDetailsInputDialog(int postion) {
        final View myview = LayoutInflater.from(mCtx).inflate(R.layout.quntity_dilaog, null);
        final EditText edt_inputqty = myview.findViewById(R.id.edit_quantity);
        int equID = equipmentList.get(postion).getId();
        Spinner makeSpinner = myview.findViewById(R.id.makeSpinner);
        Button btnLogIn = myview.findViewById(R.id.btnLogIn);
        Button btncancel = myview.findViewById(R.id.btncancel);
        ArrayList<String> makeList = new ArrayList<>();
        ArrayList<Integer> makeIdList = new ArrayList<>();
        EquipmnetContentData contentDataa = new EquipmnetContentData();
        if (allDataList != null) {
            for (Data dataModel : allDataList) {
                contentDataa = dataModel.getEquipmnetContentData();
            }

            for (Make make : contentDataa.getMake()) {
                if (equipmentList.get(postion).getId() == make.getEqId())
                    makeList.add(make.getName());
                makeIdList.add(make.getId());
            }
        }
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(mCtx, R.layout.spinner_row, makeList);
        makeSpinner.setAdapter(homeadapter);
        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int postion, long arg3) {
                makeId = makeIdList.get(postion);

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(mCtx).setIcon(R.drawable.bell_icon).setCancelable(false)
                .setView(myview).create();
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_inputqty.getText().toString().length() == 0) {
                    ASTUIUtil.showToast("Please enter Item quantity Name!");
                } else {
                    //  EquipmnetMainActtivity equipmnetMainFragment = new EquipmnetMainActtivity();

                    Intent intent = new Intent(mCtx, EquipmnetMainActtivity.class);
                    intent.putExtra("headerTxt", "Equipment Info");
                    intent.putExtra("showMenuButton", false);
                    int qty = Integer.parseInt(edt_inputqty.getText().toString());
                    intent.putExtra("qty", qty);
                    intent.putExtra("equipmentId", equID);
                    intent.putExtra("makeID", makeId);
                    //  ApplicationHelper.application().getActivity().updateFragment(equipmnetMainFragment, bundle);
                    mCtx.startActivity(intent);
                    alertDialog.dismiss();


                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }


}
