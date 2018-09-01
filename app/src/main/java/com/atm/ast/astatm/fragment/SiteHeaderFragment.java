package com.atm.ast.astatm.fragment;

import android.widget.ListView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.SiteDetailsHeaderAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.SiteHeaderClickDataModel;
import com.atm.ast.astatm.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SiteHeaderFragment extends MainFragment {
    ListView lvSiteDetails;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_site_header;
    }

    @Override
    protected void loadView() {
        this.lvSiteDetails = this.findViewById(R.id.lvSiteDetails);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getCircleSiteHeaderData();
    }


    /*
     *
     * Calling Web Service to get Site Header Click Data
     */
    private void getCircleSiteHeaderData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        String uid = "3494";
        String circleId = "44";
        String districtId = "0";
        String alarmType = "NSM";
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.GET_SITE_HEADER_CLICK_URL;
        serviceURL += "&uid=" + uid + "&circleid=" + circleId + "&districtid=" + districtId + "&alarmtype=" + alarmType;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getCircleSiteHeaderData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSiteHeaderClickData(result);
                } else {
                    ASTUIUtil.showToast("Data Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }
    /*
     * Parse and Save Site Header Click Data and set
     */

    public void parseandsaveSiteHeaderClickData(String result) {
        if (result != null) {
            try {
                ArrayList<SiteHeaderClickDataModel> siteHeaderClickDataModelArrayList = new ArrayList<>();
                SiteHeaderClickDataModel siteHeaderClickDataModel;
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArrayHeader = jsonRootObject.optJSONArray("data");
                    for (int i = 0; i < jsonArrayHeader.length(); i++) {
                        JSONObject jsonObject = jsonArrayHeader.getJSONObject(i);
                        siteHeaderClickDataModel = new SiteHeaderClickDataModel();
                        siteHeaderClickDataModel.setSiteId(jsonObject.optString("SiteId").toString());
                        siteHeaderClickDataModel.setSiteName(jsonObject.optString("SiteName").toString());
                        siteHeaderClickDataModel.setCurrentBattVoltage(jsonObject.optString("CurrentBattVoltage").toString());
                        siteHeaderClickDataModel.setBattChgCurrent(jsonObject.optString("BattChgCurrent").toString());
                        siteHeaderClickDataModel.setBattDisChgCurrent(jsonObject.optString("BattDisChgCurrent").toString());
                        siteHeaderClickDataModel.setEBRH(jsonObject.optString("EBRH").toString());
                        siteHeaderClickDataModel.setDGRH(jsonObject.optString("DGRH").toString());
                        siteHeaderClickDataModel.setUpTimeDay(jsonObject.optString("UpTimeDay").toString());
                        siteHeaderClickDataModel.setUpTimeNight(jsonObject.optString("UpTimeNight").toString());
                        siteHeaderClickDataModel.setCurrentalarm(jsonObject.optString("Currentalarm").toString());
                        siteHeaderClickDataModel.setSolarCurrent(jsonObject.optString("SoalrCurrent").toString());
                        siteHeaderClickDataModelArrayList.add(siteHeaderClickDataModel);
                    }
                    lvSiteDetails.setAdapter(new SiteDetailsHeaderAdapter(getContext(), siteHeaderClickDataModelArrayList));
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }

    }


}
