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
import com.atm.ast.astatm.model.EBRunHourModel;
import com.atm.ast.astatm.model.newmodel.Data;

import java.util.ArrayList;

/**
 * @author AST
 */
public class EBRunHourAdapter extends BaseAdapter {
    Context context;
    ArrayList<Data> rowItems;
    private LayoutInflater mInflater;

    public EBRunHourAdapter(Context context, ArrayList<Data> arrErRunHourData) {
        this.context = context;
        this.rowItems = arrErRunHourData;
        mInflater = LayoutInflater.from(context);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvDate;
        TextView tvDgRunHour;
        TextView tvEbRunHour;
        LinearLayout mainLayout;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.site_eb_run_hour_item, null);
            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvDgRunHour = (TextView) convertView.findViewById(R.id.tvDgRunHour);
            holder.tvEbRunHour = (TextView) convertView.findViewById(R.id.tvEbRunHour);
            holder.mainLayout = convertView.findViewById(R.id.mainLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvDate.setText(rowItems.get(position).getDate());
        holder.tvDgRunHour.setText(rowItems.get(position).getDGRH());
        holder.tvEbRunHour.setText(rowItems.get(position).getEBRH());
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
