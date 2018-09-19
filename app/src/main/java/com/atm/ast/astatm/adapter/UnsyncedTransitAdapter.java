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
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.model.TransitDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.utils.ASTUIUtil;

import java.util.List;

/**
 * /** * @author AST Inc.  09/7/2018.
 */
public class UnsyncedTransitAdapter extends BaseAdapter {
    Context context;
    List<TransitDataModel> transitDataArrayList;
    ASTUIUtil commonFunctions;
    ATMDBHelper atmdbHelper;

    public UnsyncedTransitAdapter(Context context, List<TransitDataModel> transitDataArrayList) {
        this.context = context;
        this.transitDataArrayList = transitDataArrayList;
        this.atmdbHelper = new ATMDBHelper(context);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvTransitType;
        TextView tvTransitTime;
        TextView tvSiteId;
        LinearLayout llListItem;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        commonFunctions = new ASTUIUtil();

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = mInflater.inflate(R.layout.unsynced_transit_listitem, null);
        holder = new ViewHolder();
        holder.tvTransitType = (TextView) convertView.findViewById(R.id.tvTransitType);
        holder.tvTransitTime = (TextView) convertView.findViewById(R.id.tvTransitTime);
        holder.tvSiteId = (TextView) convertView.findViewById(R.id.tvSiteId);
        holder.llListItem = (LinearLayout) convertView.findViewById(R.id.llListItem);
        String siteId = "";
        String siteName = "";
        String time = commonFunctions.formatDate(transitDataArrayList.get(position).getDateTime());
        String transitType = "";


        siteId = transitDataArrayList.get(position).getSiteId();
        Data siteData = atmdbHelper.getPopulateSiteListDataBySiteID(Long.parseLong(siteId));
        if (siteData != null) {
            siteName = siteData.getSiteName();
        }
        switch (transitDataArrayList.get(position).getType()) {
            case "1":
                transitType = "Start Home";
                break;
            case "2":
                transitType = "Reached Site";
                break;
            case "3":
                transitType = "Left Site";
                break;
            case "4":
                transitType = "Reached Home";
                break;
        }

        holder.tvSiteId.setText(siteName + " (" + siteId + ")");
        holder.tvTransitTime.setText(time);
        holder.tvTransitType.setText(transitType);
        if (position % 2 == 0) {
            holder.llListItem.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.llListItem.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }

        convertView.setTag(holder);

        return convertView;
    }

    @Override
    public int getCount() {
        return transitDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return transitDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return transitDataArrayList.indexOf(getItem(position));
    }
}