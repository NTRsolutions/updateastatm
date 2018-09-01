package com.atm.ast.astatm.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.utils.ASTUIUtil;

import java.util.List;

/**
 * @author AST Inc.
 */
public class SiteGridAdapter extends BaseAdapter {
    private Context context;
    private final List<Data> siteData;
    private String[][] arrColorCode;
    private LayoutInflater mInflater;

    public SiteGridAdapter(Context context, List<Data> siteData) {
        this.context = context;
        this.siteData = siteData;
        mInflater = LayoutInflater.from(context);
        setColorCode();
    }

    private class ViewHolder {
        TextView tvSiteName, tvBatteryVoltage;
        TextView tvSiteId, tvUpdateDateTime, tvStatus;
        TextView tvSiteHindiName, tvClientName;
        ImageView imgCall;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ASTUIUtil commonFunctions = new ASTUIUtil();

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.site_grid_item, null);
            holder = new ViewHolder();
            holder.tvSiteName = (TextView) convertView.findViewById(R.id.tvSiteName);
            holder.tvSiteId = (TextView) convertView.findViewById(R.id.tvSiteId);
            holder.tvSiteHindiName = (TextView) convertView.findViewById(R.id.tvSiteHindiName);
            holder.tvClientName = (TextView) convertView.findViewById(R.id.tvClientName);
            holder.tvUpdateDateTime = (TextView) convertView.findViewById(R.id.tvUpdateDateTime);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            holder.tvBatteryVoltage = (TextView) convertView.findViewById(R.id.tvBatteryVoltage);
            holder.imgCall = (ImageView) convertView.findViewById(R.id.imgCall);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
      /*  stv-siteVoltage
        stn-siteName
        ca-siteNameHindi
        stc-siteId
        cln-clientName
        std-siteStatus
        co-colorCode
        stp-siteHeadContact
        it-latestTiming
        sid-siteNumId*/
        String batteryVoltageBGcolor = "#ffffff";
        if (siteData.get(position).getStv().equals("")) {
            holder.tvBatteryVoltage.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < arrColorCode.length; i++) {
            String serverColorCode = siteData.get(position).getCo();
            String colorCode = arrColorCode[i][0];
            if (serverColorCode.equals(colorCode)) {
                batteryVoltageBGcolor = arrColorCode[i][1];
            }
        }

        holder.tvBatteryVoltage.setBackgroundColor(Color.parseColor(batteryVoltageBGcolor));
        holder.tvSiteName.setText(siteData.get(position).getStn());
        holder.tvSiteId.setText(siteData.get(position).getStc());
        holder.tvSiteHindiName.setText(siteData.get(position).getCa());
        holder.tvClientName.setText(siteData.get(position).getCln());
        if (!siteData.get(position).getIt().equals("") || !siteData.get(position).getIt().contains("/")) {
            holder.tvUpdateDateTime.setText(commonFunctions.formatDate(siteData.get(position).getIt()));
        } else if (!siteData.get(position).getIt().equals("")) {
            holder.tvUpdateDateTime.setText(siteData.get(position).getIt());
        } else {
            holder.tvUpdateDateTime.setText(siteData.get(position).getIt());
        }
        holder.tvStatus.setText(siteData.get(position).getStd());
        holder.tvBatteryVoltage.setText(siteData.get(position).getStv());

        // ---------------------Call User-----------------------------------

        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + siteData.get(position).getStp()));//siteHeadContact
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
        // ------------------------------------ ------------------------------


        return convertView;
    }

    //set color code into string array
    private void setColorCode() {
        arrColorCode = new String[15][2];
        arrColorCode[0][0] = "G";
        arrColorCode[0][1] = "#00FF00";
        arrColorCode[1][0] = "Y";
        arrColorCode[1][1] = "#FFFF00";
        arrColorCode[2][0] = "O";
        arrColorCode[2][1] = "#FFA500";
        arrColorCode[3][0] = "R";
        arrColorCode[3][1] = "#FF0000";
        arrColorCode[4][0] = "W";
        arrColorCode[4][1] = "#FFFFFF";
        arrColorCode[5][0] = "B";
        arrColorCode[5][1] = "0000FF";
        arrColorCode[6][0] = "N";
        arrColorCode[6][1] = "#000080";
        arrColorCode[7][0] = "E";
        arrColorCode[7][1] = "#808080";
        arrColorCode[8][0] = "S";
        arrColorCode[8][1] = "#C0C0C0";
        arrColorCode[9][0] = "T";
        arrColorCode[9][1] = "008080";
        arrColorCode[10][0] = "P";
        arrColorCode[10][1] = "#800080";
        arrColorCode[11][0] = "A";
        arrColorCode[11][1] = "#00FFFF";
        arrColorCode[12][0] = "M";
        arrColorCode[12][1] = "#800000";
        arrColorCode[13][0] = "L";
        arrColorCode[13][1] = "#000000";
        arrColorCode[14][0] = "F";
        arrColorCode[14][1] = "#FF00FF";
    }

    @Override
    public int getCount() {
        return siteData.size();
    }

    @Override
    public Object getItem(int position) {
        return siteData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return siteData.indexOf(getItem(position));
    }

}