package com.atm.ast.astatm.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.ClusterDisplayDataModel;
import com.atm.ast.astatm.model.newmodel.Data;

import java.util.Date;
import java.util.List;

/**
 * @author AST
 */
public class ClusterGridAdapter extends BaseAdapter {
    private Context context;
    private final List<Data> clusterData;
    private LayoutInflater mInflater;

    public ClusterGridAdapter(Context context, List<Data> clusterData) {
        this.context = context;
        this.clusterData = clusterData;
        mInflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView tvCircleName;
        TextView tvTotalSites;
        TextView tvTotalErrors;
        ImageView imgCallUser;
        LinearLayout mainLayout;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // get layout from mobile.xml
            convertView = mInflater.inflate(R.layout.center_grid_item, null);
            holder = new ViewHolder();
            holder.tvCircleName = (TextView) convertView.findViewById(R.id.tvCircleName);
            holder.tvTotalSites = (TextView) convertView.findViewById(R.id.tvTotalSites);
            holder.tvTotalErrors = (TextView) convertView.findViewById(R.id.tvTotalErrors);
            holder.imgCallUser = (ImageView) convertView.findViewById(R.id.imgCall);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCircleName.setText(clusterData.get(position).getDn());//clusterName
        holder.tvTotalSites.setText(clusterData.get(position).getDd() + "");//clusterAllSites
        holder.tvTotalErrors.setText(clusterData.get(position).getDv() + "");//clusterAlarmSites

      /*  String clusterName = jsonObject.optString("dn").toString();
        String clusterAllSites = jsonObject.optString("dd").toString();
        String clusterAlarmSites = jsonObject.optString("dv").toString();
        String clusterColorCode = jsonObject.optString("co").toString();
        String clusterId = jsonObject.optString("did").toString();
        String clusterHeadContact = jsonObject.optString("dmp").toString();*/

        holder.imgCallUser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + clusterData.get(position).getDmp()));//clusterHeadContact
                if (ActivityCompat.checkSelfPermission(ApplicationHelper.application().getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
        return convertView;
    }

    @Override
    public int getCount() {
        return clusterData.size();
    }

    @Override
    public Object getItem(int position) {
        return clusterData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return clusterData.indexOf(getItem(position));
    }

}