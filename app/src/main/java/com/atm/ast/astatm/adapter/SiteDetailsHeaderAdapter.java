package com.atm.ast.astatm.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.SiteHeaderClickDataModel;

import java.util.ArrayList;

/**
 * /** * @author AST Inc.  09/7/2018.
 */
public class SiteDetailsHeaderAdapter extends BaseAdapter {
    Context context;
    ArrayList<SiteHeaderClickDataModel> siteHeaderClickDataModelArrayList = new ArrayList<>();
    ;

    public SiteDetailsHeaderAdapter(Context context, ArrayList<SiteHeaderClickDataModel> siteHeaderClickDataModelArrayList) {
        this.context = context;
        this.siteHeaderClickDataModelArrayList = siteHeaderClickDataModelArrayList;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvSiteId;
        TextView tvSiteName;
        TextView tvBattVoltage;
        TextView tvBattCurrent;
        TextView tvEbRh;
        TextView tvDgRh;
        TextView tvUptimeDay;
        TextView tvUptimeNight;
        TextView tvCurrentAlarm;
        TextView tvSolarAlarm;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        /*if (convertView == null) {*/
        convertView = mInflater.inflate(R.layout.site_details_header_list_item, null);
        holder = new ViewHolder();
        holder.tvSiteId = convertView.findViewById(R.id.tvSiteId); //
        holder.tvSiteName = convertView.findViewById(R.id.tvSiteName); //
        holder.tvBattVoltage = convertView.findViewById(R.id.tvBattVoltage); //
        holder.tvBattCurrent = convertView.findViewById(R.id.tvBattCurrent); //
        holder.tvEbRh = convertView.findViewById(R.id.tvEbRh); //
        holder.tvDgRh = convertView.findViewById(R.id.tvDgRh);
        holder.tvUptimeDay = convertView.findViewById(R.id.tvUptimeDay); //
        holder.tvUptimeNight = convertView.findViewById(R.id.tvUptimeNight); //
        holder.tvCurrentAlarm = convertView.findViewById(R.id.tvCurrentAlarm); //
        holder.tvSolarAlarm = convertView.findViewById(R.id.tvSolarAlarm); //
        holder.tvSiteId.setText(siteHeaderClickDataModelArrayList.get(position).getSiteId());
        holder.tvSiteName.setText(siteHeaderClickDataModelArrayList.get(position).getSiteName());
        holder.tvBattVoltage.setText(siteHeaderClickDataModelArrayList.get(position).getCurrentBattVoltage());
        holder.tvBattCurrent.setText(siteHeaderClickDataModelArrayList.get(position).getBattChgCurrent());
        holder.tvEbRh.setText(siteHeaderClickDataModelArrayList.get(position).getEBRH());
        holder.tvDgRh.setText(siteHeaderClickDataModelArrayList.get(position).getDGRH());
        holder.tvUptimeDay.setText(siteHeaderClickDataModelArrayList.get(position).getUpTimeDay());
        holder.tvUptimeNight.setText(siteHeaderClickDataModelArrayList.get(position).getUpTimeNight());
        holder.tvCurrentAlarm.setText(siteHeaderClickDataModelArrayList.get(position).getCurrentalarm());
        holder.tvSolarAlarm.setText(siteHeaderClickDataModelArrayList.get(position).getSolarCurrent());
        if (position % 2 != 0) {
            convertView.setBackgroundColor(Color.parseColor("#ffd9a1"));
        }
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public int getCount() {
        return siteHeaderClickDataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return siteHeaderClickDataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return siteHeaderClickDataModelArrayList.indexOf(getItem(position));
    }
}
