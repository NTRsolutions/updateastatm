package com.atm.ast.astatm.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.SiteVisitDataModel;
import com.atm.ast.astatm.model.newmodel.Data;

import java.util.ArrayList;
import java.util.Date;


public class SiteVisitAdapter extends BaseAdapter {
    Context context;
    ArrayList<Data> rowItems;
    private LayoutInflater mInflater;

    public SiteVisitAdapter(Context context, ArrayList<Data> arrVisitData) {
        this.context = context;
        this.rowItems = arrVisitData;
        mInflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView tvDate;
        TextView tvFaultyEquipment;
        TextView tvNewEquipment;
        TextView tvFieldEngg;
        LinearLayout mainLayout;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.site_visit_item, null);
            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvFaultyEquipment = (TextView) convertView.findViewById(R.id.tvFaultyEquipment);
            holder.tvNewEquipment = (TextView) convertView.findViewById(R.id.tvNewEquipment);
            holder.tvFieldEngg = (TextView) convertView.findViewById(R.id.tvFieldEngg);
            holder.mainLayout = convertView.findViewById(R.id.mainLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvDate.setText(rowItems.get(position).getVisitDate());
        holder.tvFaultyEquipment.setText(rowItems.get(position).getFaultEquipmentMake());
        holder.tvNewEquipment.setText(rowItems.get(position).getNewEquipmentInstalledMake());
        holder.tvFieldEngg.setText(rowItems.get(position).getFEName());

        if (position % 2 != 0) {
            holder.mainLayout.setBackgroundColor(Color.parseColor("#ffd9a1"));
        } else {
            holder.mainLayout.setBackgroundColor(Color.parseColor("#ff9f63"));
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}
