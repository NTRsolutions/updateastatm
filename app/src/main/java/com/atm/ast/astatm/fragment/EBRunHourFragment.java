package com.atm.ast.astatm.fragment;

import android.widget.ListView;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.EBRunHourAdapter;
import com.atm.ast.astatm.adapter.SiteVisitAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.EBRunHourModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class EBRunHourFragment extends MainFragment {
    ListView lvEBRHDetails;
    String siteId, siteName, siteNumId, userId;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_eb_run_hour;
    }

    @Override
    protected void getArgs() {
        siteId = this.getArguments().getString("SITE_ID");
        siteName = this.getArguments().getString("SITE_NAME");
        siteNumId = this.getArguments().getString("SITE_NUM_ID");
        userId = this.getArguments().getString("USER_ID");
    }

    @Override
    protected void loadView() {
        lvEBRHDetails = findViewById(R.id.lvEBRHDetails);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getEbrhData();
    }

    /*
     *
     * Calling Web Service to getEbrh Data
     */
    private void getEbrhData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.GET_EBRH_URL_NEW;
        serviceURL += "&uid=" + userId + "&sid=" + siteId;
        serviceCaller.CallCommanServiceMethod(serviceURL,"getEbrhData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveScallgetEbrhData(result);
                } else {
                    ASTUIUtil.showToast("Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }
    /*
     * Parse and Save getEbrh Data and set Adapter
     */

    public void parseandsaveScallgetEbrhData(String result) {
        if (result != null) {
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {
                    if(serviceData.getData()!=null){
                        ArrayList<Data> arrErRunHourData = new ArrayList<Data>();
                        for (Data data : serviceData.getData()) {
                            arrErRunHourData.add(data);
                        }
                        lvEBRHDetails.setAdapter(new EBRunHourAdapter(getContext(), arrErRunHourData));
                    }
                }
            }
/*
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                EBRunHourModel ebRunHourModel;
                ArrayList<EBRunHourModel> arrErRunHourData = new ArrayList<>();
                String jsonStatus = jsonRootObject.optString("status").toString();
                String date = "";
                String EBRH = "";
                String DGRH = "";
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        date = jsonObject.optString("Date").toString();
                        EBRH = jsonObject.optString("EBRH").toString();
                        DGRH = jsonObject.optString("DGRH").toString();
                        ebRunHourModel = new EBRunHourModel();
                        ebRunHourModel.setDate(date);
                        ebRunHourModel.setDGRH(DGRH);
                        ebRunHourModel.setEBRH(EBRH);
                        arrErRunHourData.add(ebRunHourModel);
                    }
                    lvEBRHDetails.setAdapter(new EBRunHourAdapter(getContext(), arrErRunHourData));
                } else {
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }*/
        }

    }


}
