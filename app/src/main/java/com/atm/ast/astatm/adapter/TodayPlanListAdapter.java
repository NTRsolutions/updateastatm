package com.atm.ast.astatm.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.utils.FontManager;

import java.util.ArrayList;

/**
 * @author AST Inc.
 */
public class TodayPlanListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Data> rowItems;

    private Dialog confermationDialog = null;
    private LayoutInflater mInflater;
    private SharedPreferences transitsharedpreferences;
    private String siteId = "";
    Typeface materialdesignicons_font;

    public TodayPlanListAdapter(Context context, ArrayList<Data> items) {
        this.context = context;
        this.rowItems = items;
        mInflater = LayoutInflater.from(context);
        transitsharedpreferences = context.getSharedPreferences("TransitPrefs", Context.MODE_PRIVATE);
        siteId = transitsharedpreferences.getString("SITE_ID", "");
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(context, "fonts/materialdesignicons-webfont.otf");
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvSiteName, tvSiteId, selectCheck;
        CardView cardview;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.today_plan_list_item, null);
            holder = new ViewHolder();
            holder.tvSiteName = (TextView) convertView.findViewById(R.id.tvSiteName);
            holder.tvSiteId = (TextView) convertView.findViewById(R.id.tvSiteId);
            holder.cardview = (CardView) convertView.findViewById(R.id.cardview);
            holder.selectCheck = (TextView) convertView.findViewById(R.id.selectCheck);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.selectCheck.setTypeface(materialdesignicons_font);
        holder.tvSiteName.setText(rowItems.get(position).getSiteName() + " (" + rowItems.get(position).getCustomerSiteId() + ")");
        holder.tvSiteId.setText(rowItems.get(position).getSiteId() + "");
        if (siteId.equals(String.valueOf(rowItems.get(position).getSiteId()))) {
            holder.selectCheck.setText(Html.fromHtml("&#xf135;"));
            holder.selectCheck.setTextColor(Color.parseColor("#078f4b"));
        } else {
            holder.selectCheck.setText(Html.fromHtml("&#xf131;"));
            holder.selectCheck.setTextColor(Color.parseColor("#000000"));
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