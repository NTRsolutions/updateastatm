package com.atm.ast.astatm.fragment;

import android.annotation.SuppressLint;
import android.widget.ListView;
import android.widget.TextView;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.SiteVisitAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.SiteVisitDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SiteVisitFragment extends MainFragment {
    ListView lvSiteVisits;
    TextView tvSiteName;
    String siteId, siteName, siteNumId, userId;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_site_visit;
    }

    @Override
    protected void getArgs() {
        this.siteId = this.getArguments().getString("SITE_ID");
        this.siteName = this.getArguments().getString("SITE_NAME");
        this.siteNumId = this.getArguments().getString("SITE_NUM_ID");
        this.userId = this.getArguments().getString("USER_ID");

    }

    @Override
    protected void loadView() {
        this.lvSiteVisits = this.findViewById(R.id.lvVisit);
        this.tvSiteName = this.findViewById(R.id.tvSiteName);

    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void dataToView() {
        this.tvSiteName.setText(siteName + "( " + siteNumId + " )");
        getSiteVisitData();
    }
    /*
     * Calling Web Service to get Site Visit Data and call parseandsaveSiteVisitData
     */
    //-----------------------------------------------
    private void getSiteVisitData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.GET_VISIT_URL_NEW;
        serviceURL += "&uid=" + userId + "&sid=" + siteId;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getSiteVisitData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSiteVisitData(result);
                } else {
                    ASTUIUtil.showToast("Site Visit Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }


    /*
     *
     * Parse and Save  Site Vist Data and set SiteVite Adapter
     */

    public void parseandsaveSiteVisitData(String result) {
        if (result != null) {
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {
                    if(serviceData.getData()!=null){
                        ArrayList<Data> arrVisitData = new ArrayList<Data>();
                        for (Data data : serviceData.getData()) {
                            arrVisitData.add(data);
                        }
                        lvSiteVisits.setAdapter(new SiteVisitAdapter(getContext(), arrVisitData));
                    }
                }
            }
        }

    }


}
