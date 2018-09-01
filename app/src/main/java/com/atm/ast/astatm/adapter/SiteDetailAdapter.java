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
import com.atm.ast.astatm.model.SiteDetailsDisplayModel;
import com.atm.ast.astatm.model.newmodel.Data;

import java.util.ArrayList;

/**
 * @author AST Inc.
 */
public class SiteDetailAdapter extends BaseAdapter {
    Context context;
    ArrayList<Data> rowItems;
    private LayoutInflater mInflater;

    public SiteDetailAdapter(Context context, ArrayList<Data> items) {
        this.context = context;
        this.rowItems = items;
        mInflater = LayoutInflater.from(context);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvDate;
        TextView tvTime;
        TextView tvSiteStatus;
        TextView tvBatteryVoltage;
        TextView tvSolarCurrent;
        TextView tvSolarKWH;
        TextView tvBatteryChargeCurrent;
        TextView tvBatteryDischargeCurrent;
        TextView tvLoadCurrent;
        TextView tvLoadKWH;
        LinearLayout mainLayout;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.site_detail_table_item, null);
            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            holder.tvSiteStatus = (TextView) convertView.findViewById(R.id.tvSiteStatus);
            holder.tvBatteryVoltage = (TextView) convertView.findViewById(R.id.tvBatteryVoltage);
            holder.tvSolarCurrent = (TextView) convertView.findViewById(R.id.tvSolarCurrent);
            holder.tvSolarKWH = (TextView) convertView.findViewById(R.id.tvSolarKWH);
            holder.tvBatteryChargeCurrent = (TextView) convertView.findViewById(R.id.tvInOutVolt);
            holder.tvBatteryDischargeCurrent = (TextView) convertView.findViewById(R.id.tvBatteryDischargeCurrent);
            holder.tvLoadCurrent = (TextView) convertView.findViewById(R.id.tvLoadCurrent);
            holder.tvLoadKWH = (TextView) convertView.findViewById(R.id.tvAlarmCode);
            holder.mainLayout = (LinearLayout) convertView.findViewById(R.id.mainLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] arrDate = rowItems.get(position).getDt().split(" ");//Date

        holder.tvDate.setText(arrDate[0]);
        if (arrDate.length > 1) {
            holder.tvTime.setText(arrDate[1]);
        }

        holder.tvSiteStatus.setText(rowItems.get(position).getSst());//SiteStatus
        holder.tvBatteryVoltage.setText(rowItems.get(position).getBtv() + "");//BatteryVoltage
        holder.tvSolarCurrent.setText(rowItems.get(position).getSoa() + "");//SolarCurrent
        holder.tvSolarKWH.setText(rowItems.get(position).getIov() + "");//InputOutVoltage
        holder.tvBatteryChargeCurrent.setText(rowItems.get(position).getBta() + " \u2191");//BatteryChargeCurrent
        holder.tvBatteryDischargeCurrent.setText(rowItems.get(position).getBda() + " \u2193");//BatteryDischargeCurrent
        holder.tvLoadCurrent.setText(rowItems.get(position).getLa() + "");//LoadCurrent
        holder.tvLoadKWH.setText(rowItems.get(position).getCa());//CurrentAlarm

        if (position % 2 != 0) {
            holder.mainLayout.setBackgroundColor(Color.parseColor("#ffd9a1"));
        }else{
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
