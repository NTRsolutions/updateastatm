package com.atm.ast.astatm.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.fragment.CircleFragment;
import com.atm.ast.astatm.fragment.PlannedActivityListFragment;
import com.atm.ast.astatm.model.newmodel.Activity;
import com.atm.ast.astatm.model.newmodel.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * @author AST Inc..
 */
public class FilterListAdapter extends BaseAdapter {
    Context context;
    String lat = "0.0000", lon = "0.0000";
    int parentPosition;
    String activityName = "";
    ArrayList<Activity> activityList;
    List<Data> circleList;
    private LayoutInflater mInflater;

    public FilterListAdapter(Context context, ArrayList<Activity> activityList, int parentPosition, String activityName) {
        this.context = context;
        this.activityList = activityList;
        this.parentPosition = parentPosition;
        this.activityName = activityName;
        mInflater = LayoutInflater.from(context);
    }

    public FilterListAdapter(Context context, List<Data> circleList, int parentPosition, String activityName) {
        this.context = context;
        this.circleList = circleList;
        this.parentPosition = parentPosition;
        this.activityName = activityName;
        mInflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        CheckBox cbActivity;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_filter_popup_child_item, null);
            holder = new ViewHolder();
            holder.cbActivity = (CheckBox) convertView.findViewById(R.id.cbActivity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (parentPosition == 0) {
            holder.cbActivity.setText(activityList.get(position).getActivityName());
        } else {
            holder.cbActivity.setText(circleList.get(position).getCin());//circle name
        }

        if (parentPosition == 0) {
            selectedAlarmFilterItems(position, holder);
        } else if (parentPosition == 1) {
            String strCustomerFilter = CircleFragment.ctid;
            if (!strCustomerFilter.equals("NA") || !strCustomerFilter.equals("")) {
                if (strCustomerFilter.contains(",")) {
                    String[] arrCustomerFilter = strCustomerFilter.split(",");
                    for (int i = 0; i < arrCustomerFilter.length; i++) {
                        /*if (arrChildList[position].equals("Battery Low") && arrAlarmFilter[i].equals("BL1")) {
                            holder.cbActivity.setChecked(true);
                        } else if (arrChildList[position].equals("No Comm") && arrAlarmFilter[i].equals("NOCOMM")) {
                            holder.cbActivity.setChecked(true);
                        } else if (arrChildList[position].equals("NSM") && arrAlarmFilter[i].equals("NSM")) {
                            holder.cbActivity.setChecked(true);
                        } else if (arrChildList[position].equals("INV") && arrAlarmFilter[i].equals("INV")) {
                            holder.cbActivity.setChecked(true);
                        }*/
                    }
                } else {
                    /*if (arrChildList[position].equals("Battery Low") && strAlarmFilter.equals("BL1")) {
                        holder.cbActivity.setChecked(true);
                    } else if (arrChildList[position].equals("No Comm") && strAlarmFilter.equals("NOCOMM")) {
                        holder.cbActivity.setChecked(true);
                    } else if (arrChildList[position].equals("NSM") && strAlarmFilter.equals("NSM")) {
                        holder.cbActivity.setChecked(true);
                    } else if (arrChildList[position].equals("INV") && strAlarmFilter.equals("INV")) {
                        holder.cbActivity.setChecked(true);
                    }*/

                }
            }
        }

        holder.cbActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

             /*   if (activityName.equals("planned_activity")) {
                    if (isChecked) {
                        if (parentPosition == 0) {
                            PlannedActivityListFragment.arrSelectedFilterOne[position] = true;
                        } else if (parentPosition == 1) {
                            PlannedActivityListFragment.arrSelectedFilterTwo[position] = true;
                        }
                    } else {
                        if (parentPosition == 0 && activityName.equals("circle")) {
                            PlannedActivityListFragment.arrSelectedFilterOne[position] = false;
                        } else if (parentPosition == 1) {
                            PlannedActivityListFragment.arrSelectedFilterTwo[position] = false;
                        }
                    }
                } else if (activityName.equals("feTracker")) {
                    if (isChecked) {
                        PlannedActivityListFragment.arrSelectedFilterOne[position] = true;
                    } else {
                        PlannedActivityListFragment.arrSelectedFilterOne[position] = false;
                    }
                } else {
                    if (isChecked) {
                        if (parentPosition == 0) {
                            CircleFragment.arrSelectedFilterOne[position] = true;
                        } else if (parentPosition == 1) {
                            CircleFragment.arrSelectedFilterTwo[position] = true;
                        }
                    } else {
                        if (parentPosition == 0) {
                            CircleFragment.arrSelectedFilterOne[position] = false;
                        } else if (parentPosition == 1) {
                            CircleFragment.arrSelectedFilterTwo[position] = false;
                        }
                    }
                }*/

            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        if (parentPosition == 0) {
            return activityList.size();
        } else {
            return circleList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (parentPosition == 0) {
            return activityList.get(position);
        } else {
            return circleList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static void selectedAlarmFilterItems(int position, ViewHolder holder) {
        String strAlarmFilter = CircleFragment.filterString;
        /*if (!strAlarmFilter.equals("NA") || !strAlarmFilter.equals("")) {
            if (strAlarmFilter.contains(",")) {
                String[] arrAlarmFilter = strAlarmFilter.split(",");
                for (int i = 0; i < arrAlarmFilter.length; i++) {
                    if (arrChildList[position].equals("Battery Low") && arrAlarmFilter[i].equals("BL1")) {
                        holder.cbActivity.setChecked(true);
                    } else if (arrChildList[position].equals("No Comm") && arrAlarmFilter[i].equals("NOCOMM")) {
                        holder.cbActivity.setChecked(true);
                    } else if (arrChildList[position].equals("NSM") && arrAlarmFilter[i].equals("NSM")) {
                        holder.cbActivity.setChecked(true);
                    } else if (arrChildList[position].equals("INV") && arrAlarmFilter[i].equals("INV")) {
                        holder.cbActivity.setChecked(true);
                    }
                }
            } else {
                if (arrChildList[position].equals("Battery Low") && strAlarmFilter.equals("BL1")) {
                    holder.cbActivity.setChecked(true);
                } else if (arrChildList[position].equals("No Comm") && strAlarmFilter.equals("NOCOMM")) {
                    holder.cbActivity.setChecked(true);
                } else if (arrChildList[position].equals("NSM") && strAlarmFilter.equals("NSM")) {
                    holder.cbActivity.setChecked(true);
                } else if (arrChildList[position].equals("INV") && strAlarmFilter.equals("INV")) {
                    holder.cbActivity.setChecked(true);
                }

            }
        }*/
    }

}
