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
import com.atm.ast.astatm.model.SiteDetailsDisplayModel;
import java.util.ArrayList;


/**
 * @author AST Inc.
 */
public class SiteUptimeAdapter extends BaseAdapter {
    Context context;
    ArrayList<SiteDetailsDisplayModel> rowItems;

    public SiteUptimeAdapter(Context context) {
        this.context = context;
        rowItems = new ArrayList<SiteDetailsDisplayModel>();
        SiteDetailsDisplayModel siteDetailsDisplayModel = new SiteDetailsDisplayModel();

        for (int i = 0; i < 20; i++) {
            rowItems.add(siteDetailsDisplayModel);
        }
        //this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvDate;
        TextView tvFunctionalUptime;
        TextView tvAbsoluteUptime;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.site_uptime_item, null);
        holder = new ViewHolder();
        holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate); //
        holder.tvFunctionalUptime = (TextView) convertView.findViewById(R.id.tvFunctionalUptime); //
        holder.tvAbsoluteUptime = (TextView) convertView.findViewById(R.id.tvAbsoluteUptime); //
        if (position % 2 != 0) {
            convertView.setBackgroundColor(Color.parseColor("#ffd9a1"));
        }
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public int getCount() {
        //return rowItems.size();
        return 20;
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
