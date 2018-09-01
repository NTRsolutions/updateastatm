package com.atm.ast.astatm.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.ActivityListDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * /** * @author AST Inc.  09/7/2018..
 */
public class PlannedActivityListAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<Data> rowItems;
    private List<Data> mainPlannedList;
    String first = "", uid = "", role = "";
    String lat = "0.0000", lon = "0.0000";
    Dialog confermationDialog = null;
    private LayoutInflater mInflater;

    public PlannedActivityListAdapter(Context context, List<Data> items, String first, String uid, String role) {
        this.context = context;
        this.rowItems = items;
        this.mainPlannedList = items;
        this.first = first;
        this.uid = uid;
        this.role = role;
        mInflater = LayoutInflater.from(context);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvSiteId;
        TextView tvSiteName;
        TextView tvPlannedDate;
        TextView tvPlannedActivity;
        LinearLayout llPlannedListItem;
        ImageView imgCancelPlan;
        ImageView imgDisabled;
        RelativeLayout rlTransparent;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.planned_activity_list_item, null);
            holder = new ViewHolder();
            holder.tvSiteName = (TextView) convertView.findViewById(R.id.tvSiteName);
            holder.tvSiteId = (TextView) convertView.findViewById(R.id.tvSiteId);
            holder.tvPlannedDate = (TextView) convertView.findViewById(R.id.tvPlannedDate);
            holder.tvPlannedActivity = (TextView) convertView.findViewById(R.id.tvPlannedActivity);
            holder.llPlannedListItem = (LinearLayout) convertView.findViewById(R.id.llPlannedListItem);
            holder.imgCancelPlan = (ImageView) convertView.findViewById(R.id.imgCancelPlan);
            holder.imgDisabled = (ImageView) convertView.findViewById(R.id.imgDisabled);
            holder.rlTransparent = (RelativeLayout) convertView.findViewById(R.id.rlTransparent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (role.equalsIgnoreCase("fe")) {
            holder.imgCancelPlan.setVisibility(View.GONE);
        }
        //holder.imgCancelPlan.setVisibility(View.GONE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        long plannedActivityMillisecond = 0;
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(rowItems.get(position).getPlanDate());
            plannedActivityMillisecond = convertedDate.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long currentMilli = System.currentTimeMillis();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentMilli);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        Calendar calendar1 = Calendar.getInstance();
        calendar.setTimeInMillis(plannedActivityMillisecond);
        int activtyDay = calendar.get(Calendar.DAY_OF_MONTH);
        int activtyMonth = calendar.get(Calendar.MONTH) + 1;
        int activtyYear = calendar.get(Calendar.YEAR);
        if (activtyYear == currentYear) {
            if (activtyMonth == currentMonth) {
                if (activtyDay < currentDay) {
                    holder.llPlannedListItem.setBackgroundColor(Color.parseColor("#FA5858"));
                } else if (activtyDay == currentDay) {
                    holder.llPlannedListItem.setBackgroundColor(Color.parseColor("#F7FE2E"));
                } else if (activtyDay > currentDay) {
                    holder.llPlannedListItem.setBackgroundColor(Color.parseColor("#9FF781"));
                }
            } else if (activtyMonth < currentMonth) {
                holder.llPlannedListItem.setBackgroundColor(Color.parseColor("#FA5858"));
            } else if (activtyMonth > currentMonth) {
                holder.llPlannedListItem.setBackgroundColor(Color.parseColor("#9FF781"));
            }

        } else if (activtyYear < currentYear) {
            holder.llPlannedListItem.setBackgroundColor(Color.parseColor("#FA5858"));
        } else if (activtyYear > currentYear) {
            holder.llPlannedListItem.setBackgroundColor(Color.parseColor("#9FF781"));
        }
        String marketingDistributor = "";
        if (!rowItems.get(position).getMd().equals("")) {
            marketingDistributor = "(" + rowItems.get(position).getMd() + ")";
        }
        holder.tvSiteName.setText(rowItems.get(position).getSiteName() + marketingDistributor);
        if (first.equals("First")) {
            holder.tvSiteId.setText(rowItems.get(position).getCustomerSiteId());
        } else {
            holder.tvSiteId.setText(rowItems.get(position).getSiteId() + "");
        }
        holder.tvPlannedDate.setText(df.format(plannedActivityMillisecond));
        holder.tvPlannedActivity.setText(rowItems.get(position).getActivityName() + "(" + rowItems.get(position).getFEName() + ")");
        holder.imgCancelPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertMessage(rowItems.get(position).getPlanId() + "");
            }
        });
       /* try {
            if (rowItems.get(position).getSubmittedOffline().equalsIgnoreCase("1")) {
                holder.imgDisabled.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
        }*/
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

    public void alertMessage(final String planId) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        cancelPlan(planId);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure You Want to Submit?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    /*
     *
     * Calling Web Service to cancel plan
     */

    public void cancelPlan(String planId) {
        ASTProgressBar _progrssBar = new ASTProgressBar(context);
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(context);
        String serviceURL = "";
        serviceURL = Contants.BASE_URL + Contants.CANCEL_PLAN;
        serviceURL += "&uid=" + uid + "&planid=" + planId + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getEbrhData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandcancelPlan(result);
                } else {
                    ASTUIUtil.showToast("Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }


    /*
     *
     * Parse and parse and cancel Plan
     */

    public void parseandcancelPlan(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String uid = jsonObject.optString("uid").toString();
                        // Intent intentHomeScreen = new Intent(context, CircleActivity.class);
                        //  context.startActivity(intentHomeScreen);
                    }
                } else {
                    Toast.makeText(context, "Plan did not calcel!",
                            Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }

    }

    //for search fe name
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    rowItems = mainPlannedList;
                } else {

                    ArrayList<Data> filteredList = new ArrayList<Data>();

                    for (Data data : mainPlannedList) {

                        if (data.getFEName().toLowerCase().contains(charString)) {
                            filteredList.add(data);
                        }
                    }

                    rowItems = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = rowItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                rowItems = (ArrayList<Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
