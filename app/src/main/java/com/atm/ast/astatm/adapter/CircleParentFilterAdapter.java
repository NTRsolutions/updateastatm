package com.atm.ast.astatm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.fragment.PlannedActivityListFragment;

import java.util.List;

/**
 * @author AST
 */
public class CircleParentFilterAdapter extends BaseAdapter {
    Context context;
    List<String> rowItems;
    String filterType;

    public CircleParentFilterAdapter(Context context, List<String> items, String filterType) {
        this.context = context;
        this.rowItems = items;
        this.filterType = filterType;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvActivityName;
        CheckBox cbActivity;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        /*if (convertView == null) {*/
        convertView = mInflater.inflate(R.layout.activity_filter_popup_child_item, null);
        holder = new ViewHolder();
        holder.cbActivity = (CheckBox) convertView.findViewById(R.id.cbActivity);

        //holder.tvActivityName.setText(rowItems.get(position));
        //holder.tvActivityName.setText("Activity Name");

        final ViewHolder finalHolder = holder;
        holder.cbActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (filterType.equals("Circle")) {
                    if (finalHolder.cbActivity.isChecked()) {
                       // PlannedActivityListFragment.checkListCircle[position] = 1;
                    } else {
                       // PlannedActivityListFragment.checkListCircle[position] = 0;
                    }
                } else if (filterType.equals("Activity")) {
                    if (finalHolder.cbActivity.isChecked()) {
                      //  PlannedActivityListFragment.checkListActivity[position] = 1;
                    } else {
                       // PlannedActivityListFragment.checkListActivity[position] = 0;
                    }
                }
            }
        });

        convertView.setTag(holder);
        /*} else {
            holder = (ViewHolder) convertView.getTag();
        }*/

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
        //return 10;
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