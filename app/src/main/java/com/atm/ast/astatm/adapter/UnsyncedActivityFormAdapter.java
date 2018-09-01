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
import com.atm.ast.astatm.model.ActivityFormDataModel;
import com.atm.ast.astatm.utils.ASTUIUtil;

import java.util.List;

/**
 * /** * @author AST Inc.  09/7/2018.
 */
public class UnsyncedActivityFormAdapter extends BaseAdapter {
    Context context;
    List<ActivityFormDataModel> activityFormDataArrayList;
    ASTUIUtil commonFunctions;

    public UnsyncedActivityFormAdapter(Context context, List<ActivityFormDataModel> activityFormDataArrayList) {
        this.context = context;
        this.activityFormDataArrayList = activityFormDataArrayList;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvSite;
        TextView tvDate;
        TextView tvActivity;
        TextView tvStatus;
        TextView tvTask;
        LinearLayout llListItem;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        commonFunctions = new ASTUIUtil();
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.unsynced_activity_form_listitem, null);
        holder = new ViewHolder();
        holder.tvSite = convertView.findViewById(R.id.tvSite);
        holder.tvDate = convertView.findViewById(R.id.tvDate);
        holder.tvActivity = convertView.findViewById(R.id.tvActivity);
        holder.tvStatus = convertView.findViewById(R.id.tvStatus);
        holder.tvTask = convertView.findViewById(R.id.tvTask);
        holder.llListItem = convertView.findViewById(R.id.llListItem);
        String activityDateTime = commonFunctions.formatDate(activityFormDataArrayList.get(position).getDate());
        holder.tvTask.setText(activityFormDataArrayList.get(position).getTaskName());
        holder.tvActivity.setText(activityFormDataArrayList.get(position).getActivityName());
        holder.tvStatus.setText(activityFormDataArrayList.get(position).getStatusName());
        holder.tvDate.setText(activityDateTime);
        holder.tvSite.setText(activityFormDataArrayList.get(position).getSiteName());
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
        return activityFormDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityFormDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return activityFormDataArrayList.indexOf(getItem(position));
    }
}