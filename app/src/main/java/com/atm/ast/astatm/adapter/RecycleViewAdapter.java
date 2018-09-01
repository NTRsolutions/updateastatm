package com.atm.ast.astatm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.fragment.EquipmnetMainFragment;
import com.atm.ast.astatm.model.newmodel.Item;
import com.atm.ast.astatm.utils.ASTUIUtil;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ItemViewHolder> {

    private Context mCtx;
    private List<Item> productList;

    public RecycleViewAdapter(Context mCtx, List<Item> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycle_item_row, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = productList.get(position);
        holder.name.setText(item.getName());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(item.getImage()));

    }


    @Override
    public int getItemCount() {
        return productList.size();
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

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        showInputDialog();
                    }
                }
            });
        }
    }


    public void showInputDialog() {
        final View myview = LayoutInflater.from(mCtx).inflate(R.layout.quntity_dilaog, null);
        final EditText edt_inputqty = myview.findViewById(R.id.edit_quantity);
        Button btnLogIn = myview.findViewById(R.id.btnLogIn);
        Button btncancel = myview.findViewById(R.id.btncancel);

        final AlertDialog alertDialog = new AlertDialog.Builder(mCtx).setIcon(R.drawable.bell_icon).setCancelable(false)
                .setView(myview).create();
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_inputqty.getText().toString().length() == 0) {
                    ASTUIUtil.showToast("Please enter Item quantity Name!");
                } else {
                    EquipmnetMainFragment equipmnetMainFragment = new EquipmnetMainFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("headerTxt", "Equipment Info");
                    bundle.putBoolean("showMenuButton", false);
                    int qty = Integer.parseInt(edt_inputqty.getText().toString());
                    bundle.putInt("qty", qty);
                    ApplicationHelper.application().getActivity().updateFragment(equipmnetMainFragment, bundle);
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