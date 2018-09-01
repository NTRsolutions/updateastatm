package com.atm.ast.astatm.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atm.ast.astatm.R;

/**
 *  /** * @author AST Inc.  09/7/2018..
 */
public class FilterListParentAdapter extends BaseAdapter {
    Context context;
    String[] arrParentList;
    String first = "", uid = "", role = "";
    String lat = "0.0000", lon = "0.0000";

    Dialog confermationDialog = null;

    public FilterListParentAdapter(Context context, String[] arrParentList) {
        this.context = context;
        this.arrParentList = arrParentList;
        this.first = first;
        this.uid = uid;
        this.role = role;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvFilterName;
        LinearLayout llFilterParent;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        /*if (convertView == null) {*/
        convertView = mInflater.inflate(R.layout.activity_filter_popup_parent_item, null);
        holder = new ViewHolder();
        holder.tvFilterName = (TextView) convertView.findViewById(R.id.tvParentItemName);
        holder.tvFilterName.setText(arrParentList[position]);

        holder.llFilterParent = (LinearLayout) convertView.findViewById(R.id.llFilterParent);

        if (position == 0){
            holder.llFilterParent.setBackgroundColor(Color.parseColor("#ededed"));
        }
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public int getCount() {
        return arrParentList.length;
    }

    @Override
    public Object getItem(int position) {
        return arrParentList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
