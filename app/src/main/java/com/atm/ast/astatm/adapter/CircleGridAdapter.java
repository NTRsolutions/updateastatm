package com.atm.ast.astatm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.CircleDisplayDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.utils.ASTUtil;

import java.util.List;


public class CircleGridAdapter extends BaseAdapter {
    private Context context;
    private final List<Data> circleData;
    CircleDisplayDataModel circleDataModel;
    private LayoutInflater mInflater;

    public CircleGridAdapter(Context context, List<Data> circleData) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.circleData = circleData;
    }


    @Override
    public int getCount() {
        return circleData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        ViewHolder holder;
        if (convertview == null) {
            convertview = mInflater.inflate(R.layout.center_grid_item, null);
            holder = new ViewHolder();
            {
                holder.tvCircleName = convertview.findViewById(R.id.tvCircleName);
                holder.tvTotalSites = convertview.findViewById(R.id.tvTotalSites);
                holder.tvTotalErrors = convertview.findViewById(R.id.tvTotalErrors);
                holder.imgCallUser = convertview.findViewById(R.id.imgCall);
                convertview.setTag(holder);
            }
        } else {
            holder = (ViewHolder) convertview.getTag();
        }
       /* cin-circle name
        cd-TotalSites
        civ-TotalAlarmSites
        co-ColorCode
        ciid-CircleId
        chp-CircleHeadContact*/
        String cin = circleData.get(position).getCin();
        String cd = String.valueOf(circleData.get(position).getCd());
        String civ = String.valueOf(circleData.get(position).getCiv());
        String co = circleData.get(position).getCo();
        String ciid = String.valueOf(circleData.get(position).getCiid());
        final String chp = circleData.get(position).getChp();
        holder.tvCircleName.setText(cin);
        holder.tvTotalSites.setText(cd);
        holder.tvTotalErrors.setText(civ);
        holder.imgCallUser.setOnClickListener(v -> {
            ASTUtil.doCall(context, chp);
        });

        return convertview;
    }

    public static class ViewHolder {
        TextView tvCircleName;
        TextView tvTotalSites;
        TextView tvTotalErrors;
        ImageView imgCallUser;

    }

}