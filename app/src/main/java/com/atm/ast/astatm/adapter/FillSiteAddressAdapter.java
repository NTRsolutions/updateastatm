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
import com.atm.ast.astatm.model.FillSiteActivityModel;
import com.atm.ast.astatm.utils.ASTUIUtil;

import java.util.List;


/**
 * Created by AST on 27-09-2016.
 */
public class FillSiteAddressAdapter extends BaseAdapter {
    Context context;
    List<FillSiteActivityModel> siteAddressDataArrayList;
    ASTUIUtil commonFunctions;

    public FillSiteAddressAdapter(Context context, List<FillSiteActivityModel> siteAddressDataArrayList) {
        this.context = context;
        //this.siteDetailArrayList = siteDetailArrayList;
        this.siteAddressDataArrayList = siteAddressDataArrayList;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvSiteName;
        TextView tvSiteId;
        TextView tvNameOfBranch;
        TextView tvBranchCode;
        LinearLayout llListItem;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        commonFunctions = new ASTUIUtil();
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.unsynced_fill_site_address_listitem, null);
        holder = new ViewHolder();
        holder.tvSiteName = (TextView) convertView.findViewById(R.id.tvSiteName);
        holder.tvSiteId = (TextView) convertView.findViewById(R.id.tvSiteId);
        holder.tvNameOfBranch = (TextView) convertView.findViewById(R.id.tvNameOfBranch);
        holder.tvBranchCode = (TextView) convertView.findViewById(R.id.tvBranchCode);
        holder.llListItem = (LinearLayout) convertView.findViewById(R.id.llListItem);
        String siteId = "";
        String siteName = "";
        String nameOfBranch = "";
        String branchCode = "";
        siteId = siteAddressDataArrayList.get(position).getSiteId();
        siteName = siteAddressDataArrayList.get(position).getSiteName();
        nameOfBranch = siteAddressDataArrayList.get(position).getBranchName();
        branchCode = siteAddressDataArrayList.get(position).getBranchCode();
        holder.tvSiteId.setText(siteId);
        holder.tvSiteName.setText(siteName);
        holder.tvBranchCode.setText(nameOfBranch);
        holder.tvNameOfBranch.setText(branchCode);
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
        return siteAddressDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return siteAddressDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return siteAddressDataArrayList.indexOf(getItem(position));
    }
}