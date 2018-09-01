package com.atm.ast.astatm.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.FeTrackerChildItemModel;
import com.atm.ast.astatm.model.FeTrackerEmployeeModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  @author AST Inc.
 */
public class FeTrackerAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {
    public Context context;
    CheckBox checkBox;
    private LayoutInflater vi;
    private String[][] data;
    int _objInt;
    public static Boolean checked[] = new Boolean[1];
    HashMap<Long, Boolean> checkboxMap = new HashMap<Long, Boolean>();
    private static final int GROUP_ITEM_RESOURCE = R.layout.fe_tracker_group_list_item;
    private static final int CHILD_ITEM_RESOURCE = R.layout.fe_tracker_child_list_item;
    public String[] check_string_array;
    String[][] arrColorCodes = new String[2][5];
    ArrayList<FeTrackerEmployeeModel> arrFeTracker;
    ArrayList<FeTrackerChildItemModel> arrayListTempFeChild;

    public FeTrackerAdapter(Context context, String[][] data, ArrayList<FeTrackerEmployeeModel> arrFeTracker, ArrayList<FeTrackerChildItemModel> arrayListFeTrackerChild) {
        this.data = data;
        this.context = context;
        this.arrFeTracker = arrFeTracker;
        vi = LayoutInflater.from(context);
        _objInt = data.length;
        check_string_array = new String[_objInt];
        popolaCheckMap();
    }

    public void popolaCheckMap() {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String buffer = null;

        for (int i = 0; i < _objInt; i++) {
            buffer = settings.getString(String.valueOf((int) i), "false");
            if (buffer.equals("false"))
                checkboxMap.put((long) i, false);
            else checkboxMap.put((long) i, true);
        }
    }

    public String getChild(int groupPosition, int childPosition) {
        return data[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return arrFeTracker.get(groupPosition).getTotalTransit();
    }

    public class ViewHolder {
        public TextView tvFeName;
        public TextView tvColorCode;
        public TextView tvDistance;
        public TextView tvShortAddress;
        public TextView tvLastTrackedTime;
        ImageView imgCall;

        public ViewHolder(View v) {
            this.tvFeName = (TextView) v.findViewById(R.id.tvFeName);
            this.tvColorCode = (TextView) v.findViewById(R.id.tvColorCode);
            this.tvDistance = (TextView) v.findViewById(R.id.tvDistance);
            this.tvShortAddress = (TextView) v.findViewById(R.id.tvShortAddress);
            this.tvColorCode = (TextView) v.findViewById(R.id.tvColorCode);
            this.imgCall = (ImageView) v.findViewById(R.id.imgCall);
            this.tvLastTrackedTime = (TextView) v.findViewById(R.id.tvLastTrackedTime);
        }
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView tvTransitTime;
        TextView tvSiteName;
        TextView tvCircle;
        TextView tvTransitType;
        TextView tvDistance;
        LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(CHILD_ITEM_RESOURCE, null);
        tvTransitType = (TextView) convertView.findViewById(R.id.tvTransitType);
        tvTransitTime = (TextView) convertView.findViewById(R.id.tvTransitTime);
        tvSiteName = (TextView) convertView.findViewById(R.id.tvSiteName);
        tvCircle = (TextView) convertView.findViewById(R.id.tvCircle);
        tvDistance = (TextView) convertView.findViewById(R.id.tvDistance);
        ArrayList<FeTrackerChildItemModel> arrayListTest = arrFeTracker.get(groupPosition).getArrayListFeTrackerChild();
        Log.v("FeTrackerChildData", arrFeTracker.get(groupPosition).getArrayListFeTrackerChild().get(childPosition).getSiteName() + " - " +
                arrFeTracker.get(groupPosition).getArrayListFeTrackerChild().get(childPosition).getTransitTime());
        tvTransitType.setText(arrFeTracker.get(groupPosition).getArrayListFeTrackerChild().get(childPosition).getStatus());
        tvTransitTime.setText(arrFeTracker.get(groupPosition).getArrayListFeTrackerChild().get(childPosition).getTransitTime());
        tvSiteName.setText(arrFeTracker.get(groupPosition).getArrayListFeTrackerChild().get(childPosition).getSiteName());
        tvCircle.setText(arrFeTracker.get(groupPosition).getArrayListFeTrackerChild().get(childPosition).getCircle());
        tvDistance.setText(arrFeTracker.get(groupPosition).getArrayListFeTrackerChild().get(childPosition).getDistance());
        return convertView;
    }

    public String getGroup(int groupPosition) {
        return "group-" + groupPosition;
    }

    public int getGroupCount() {
        return data.length;
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        String group = null;
        int id_res = 0;
        long group_id = getGroupId(groupPosition);

       /* arrayListTempFeChild = new ArrayList<>();

        for(int j = 0; j < arrayListFeTrackerChild.size(); j++){
            if (arrFeTracker.get(groupPosition).getUserId().equals(arrayListFeTrackerChild.get(j).getUserId())){
                arrayListTempFeChild.add(arrayListFeTrackerChild.get(j));
                    *//*
                    tvTransitType.setText(arrayListFeTrackerChild.get(j).getStatus());
                    tvTransitTime.setText(arrayListFeTrackerChild.get(j).getTransitTime());
                    tvSiteName.setText(arrayListFeTrackerChild.get(j).getSiteName());
                    tvCircle.setText(arrayListFeTrackerChild.get(j).getCircle());
                    tvDistance.setText(arrayListFeTrackerChild.get(j).getDistance());*//*
            }
        }*/
        for (int i = 0; i < arrFeTracker.size() - 1; i++) {
            group = String.valueOf(i);
        }

        if (arrFeTracker.size() == 1) {
            group = String.valueOf(1);
        }

        /*if (group_id == 0) {
            group = "Audi";
            //id_res = R.drawable.audi;
        } else if (group_id == 1) {
            group = "BMW";
            //id_res = R.drawable.bmw;
        } else if (group_id == 2) {
            group = "Ferrari";
            //id_res = R.drawable.ferrari;
        }
*/
        if (group != null) {
            v = vi.inflate(GROUP_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);

            holder.imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + arrFeTracker.get(groupPosition).getContactNo()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(callIntent);
                }
            });

            holder.tvFeName.setText(arrFeTracker.get(groupPosition).getName());
            holder.tvShortAddress.setText(arrFeTracker.get(groupPosition).getShortAddress());
            holder.tvDistance.setText(arrFeTracker.get(groupPosition).getDistance() + " Km");
            holder.tvColorCode.setText(arrFeTracker.get(groupPosition).getStatus());
            holder.tvLastTrackedTime.setText(arrFeTracker.get(groupPosition).getLastTrackedTime());
            if (arrFeTracker.get(groupPosition).getColor().equals("GN"))
                holder.tvColorCode.setBackgroundColor(Color.parseColor("#55AE3A"));
            else if (arrFeTracker.get(groupPosition).getColor().equals("GY"))
                holder.tvColorCode.setBackgroundColor(Color.parseColor("#D3D3D3"));
            else if (arrFeTracker.get(groupPosition).getColor().equals("OE"))
                holder.tvColorCode.setBackgroundColor(Color.parseColor("#FFA500"));
            else if (arrFeTracker.get(groupPosition).getColor().equals("BE"))
                holder.tvColorCode.setBackgroundColor(Color.parseColor("#1E90FF"));
            else if (arrFeTracker.get(groupPosition).getColor().equals("RD"))
                holder.tvColorCode.setBackgroundColor(Color.parseColor("#ff4b0f"));
        }
        return v;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }
}