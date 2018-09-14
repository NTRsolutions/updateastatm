package com.atm.ast.astatm.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.fragment.CircleFragment;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.ActivityListDataModel;
import com.atm.ast.astatm.model.EquipListDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AST
 */

public class EquipmentChecklistAdapter extends BaseAdapter {
    Context context;
    List<ActivityListDataModel> rowItems;
    String first = "", uid = "", activityType = "";
    String lat = "0.0000", lon = "0.0000";
    Dialog confermationDialog = null;
    ArrayList<EquipListDataModel> arrEquipLIstData;
    ATMDBHelper atmDatabase;

    public EquipmentChecklistAdapter(Context context, String activityType) {
        this.context = context;
        atmDatabase = new ATMDBHelper(context);
        this.first = first;
        this.uid = uid;
        this.activityType = activityType;
        arrEquipLIstData = new ArrayList<>();
        arrEquipLIstData = atmDatabase.getAllEquipmentData();
        String test = "";
    }

    private class ViewHolder {
        TextView tvEquipName;
        EditText etQuantity;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        EquipmentChecklistAdapter.ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.equipment_checklist_popup_item, null);
            holder = new EquipmentChecklistAdapter.ViewHolder();
            holder.tvEquipName = (TextView) convertView.findViewById(R.id.tvEquipName);
            holder.etQuantity = (EditText) convertView.findViewById(R.id.etQuantity);
            holder.tvEquipName.setText(arrEquipLIstData.get(position).getEquipName());
            holder.tvEquipName.setId(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return arrEquipLIstData.size();
    }

    @Override
    public Object getItem(int position) {
        return arrEquipLIstData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrEquipLIstData.indexOf(getItem(position));
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
    //---------------------Calling Web Service to delete plan--------------------------
    ProgressDialog progressbar;

    public void cancelPlan(String planId) {
        // TODO Auto-generated method stub
        progressbar = ProgressDialog.show(context, "",
                "Please wait..", true);

        String serviceURL = "";
        serviceURL = Contants.BASE_URL + Contants.CANCEL_PLAN;

        serviceURL += "&uid=" + uid + "&planid=" + planId + "&lat=" + lat + "&lon=" + lon;

        //username=at&password=welcome123&lat=25.23&lon=25.23

        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqSuccessListener(),
                reqErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", "3494");
                params.put("ctid", "0");
                params.put("lat", "25.23");
                params.put("lon", "25.23");
                params.put("mr", "1");


                return params;
            }

            ;
        };
        int socketTimeout = 30000;//30 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
        queue.add(myReq);
    }*/

    /*
     *
     * Calling Web Service to delete plan
     */
    public void cancelPlan(String planId) {
        ASTProgressBar _progrssBar = new ASTProgressBar(context);
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(context);
        String serviceURL = Contants.BASE_URL + Contants.CANCEL_PLAN;
        serviceURL += "&uid=" + uid + "&planid=" + planId + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "canclePlan", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandcancelPlan(result);
                } else {
                    // FNUIUtil.showToast("Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }


    /*
     *
     * Parse and Save getEbrh Data
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
                        CircleFragment circleFragment = new CircleFragment();
                        Bundle bundle = new Bundle();
                        ApplicationHelper.application().getActivity().updateFragment(circleFragment, bundle);
                    }
                } else {
                    Toast.makeText(context, "Your Internet Connection is Unstable", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }

    }


}
