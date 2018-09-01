package com.atm.ast.astatm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.model.ExpenseScreenDataModel;
import java.util.ArrayList;

/**
 * @author AST
 */
public class ExpenseScreenAdapter extends BaseAdapter {
    public ArrayList<ExpenseScreenDataModel> arrExpenseData;
    Activity activity;


    public ExpenseScreenAdapter(Activity activity, ArrayList<ExpenseScreenDataModel> arrExpenseData) {
        super();
        this.activity = activity;
        this.arrExpenseData = arrExpenseData;
    }

    @Override
    public int getCount() {
        return arrExpenseData.size();
    }

    @Override
    public Object getItem(int position) {
        return arrExpenseData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView tvSNo, tvDate, tvSiteName, tvAttendance, tvDA, tvTA, tvHotel, tvBonus, tvWaterCost, tvOtherExp, tvTotal;
        LinearLayout linearLayout1;
        //Button btnEmail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View newView = null;
        ViewHolder holder = null;
        LayoutInflater inflater = activity.getLayoutInflater();
        holder = new ViewHolder();

        if (newView == null) {

            newView = inflater.inflate(R.layout.expense_screen_listview_row, null);
            holder.linearLayout1 = newView.findViewById(R.id.linearLayout1);
            holder.tvSNo = newView.findViewById(R.id.tvSNo);
            holder.tvDate = newView.findViewById(R.id.tvDate);
            holder.tvSiteName = newView.findViewById(R.id.tvSiteName);
            holder.tvAttendance = newView.findViewById(R.id.tvAttendance);
            holder.tvDA = newView.findViewById(R.id.tvDA);
            holder.tvTA = newView.findViewById(R.id.tvTA);
            holder.tvHotel = newView.findViewById(R.id.tvHotel);
            holder.tvBonus = newView.findViewById(R.id.tvBonus);
            holder.tvWaterCost = newView.findViewById(R.id.tvWaterCost);
            holder.tvOtherExp = newView.findViewById(R.id.tvOtherExp);
            holder.tvTotal = newView.findViewById(R.id.tvTotal);
            newView.setTag(holder);
            holder.tvDate.setText(arrExpenseData.get(position).getDate());
            holder.tvSiteName.setText(arrExpenseData.get(position).getSiteName());
            holder.tvAttendance.setText(arrExpenseData.get(position).getAttendance());
            holder.tvDA.setText(arrExpenseData.get(position).getDa());
            holder.tvTA.setText(arrExpenseData.get(position).getTa());
            holder.tvHotel.setText(arrExpenseData.get(position).getHotel());
            holder.tvBonus.setText(arrExpenseData.get(position).getBonus() + "/" + arrExpenseData.get(position).getPenalty());
            holder.tvWaterCost.setText(arrExpenseData.get(position).getWaterCost());
            holder.tvOtherExp.setText(arrExpenseData.get(position).getOtherExp());
            holder.tvTotal.setText(arrExpenseData.get(position).getTotal());
            newView.setTag(holder);
        } else {
            holder = (ViewHolder) newView.getTag();
        }
        return newView;

    }

}