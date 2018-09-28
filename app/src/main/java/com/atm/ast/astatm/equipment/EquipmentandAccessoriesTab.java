package com.atm.ast.astatm.equipment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.fragment.MainFragment;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.AccFeedBack;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmentInfo;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EquipmentandAccessoriesTab extends MainFragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    Button btnSubmit;
    ATMDBHelper atmdbHelper;
    ViewPagerAdapter adapter;

    @Override
    protected int fragmentLayout() {
        return R.layout.equipment_accessoris_tab_fragment;
    }

    @Override
    protected void getArgs() {
    }

    @Override
    protected void loadView() {
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabs);
        btnSubmit = this.findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }


    @Override
    protected void dataToView() {
        atmdbHelper = new ATMDBHelper(getContext());
        if (ASTUIUtil.isOnline(getContext())) {
            getSiteEquipListData();
            getDispatchEquipment();
        } else {
            setPage();
        }
    }

    private void setPage() {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EquipmnetList(), "Equipmnet");
        adapter.addFragment(new AccessoriesFragment(), "Accessories");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Adapter for the viewpager using FragmentPagerAdapter
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<MainFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public MainFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(MainFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    /**
     * Calling Web Service to get Site Equipment List Data
     */

    public void getSiteEquipListData() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL_API + Contants.SITE_EQUIPMENT_LIST_URL;
        serviceCaller.callgetEquopentList(serviceURL, new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSiteEquipListData(result);
                } else {
                    ASTUIUtil.showToast("Data Not Availabale");
                }
                _progrssBar.dismiss();
            }
        });
    }



    /*
     *
     * Parse and Save Site EquipList Data
     */

    public void parseandsaveSiteEquipListData(String result) {
        if (result != null) {
            EquipmnetContentData contentData = new Gson().fromJson(result, EquipmnetContentData.class);
            if (contentData != null) {
                if (contentData.getStatus() == 2) {
                    Data data = new Data();
                    data.setEquipmnetContentData(contentData);
                    atmdbHelper.deleteAllRows("SiteEquipment");
                    atmdbHelper.insertSiteEquipmentData(data);
                }

            }
        }
    }


    /**
     * Calling Web Service to get Site GetDispatchEquipment List Data
     */

    public void getDispatchEquipment() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL_API + Contants.DISPATCH_EQUIPMENT + "siteid=" + 0;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getDispatchEquipment", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveDispatchEquipment(result);
                } else {
                    ASTUIUtil.showToast("Data Not Availabale");
                }
                _progrssBar.dismiss();
            }
        });
    }

    /*
     *
     * Parse and Save Site EquipList Data
     */

    public void parseandsaveDispatchEquipment(String result) {
        if (result != null) {
            EquipmnetContentData contentData = new Gson().fromJson(result, EquipmnetContentData.class);
            if (contentData != null) {
                if (contentData.getStatus() == 2) {
                    if (contentData.getEquipment() != null) {
                        atmdbHelper.deleteAllRows("DispatchEquipment");
                        for (Equipment equipment : contentData.getEquipment()) {
                            atmdbHelper.upsertDispatchEquipmentData(equipment, contentData.getSiteId());
                        }
                    }
                }

            }
        }
        setPage();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                saveEquipmentData();
                break;
        }
    }

    //send equipment detail into server
    private void saveEquipmentData() {
        JSONObject mainObject = new JSONObject();
        try {
            mainObject.put("ActivityId", 1);
            mainObject.put("PlanId", 1);
            mainObject.put("SiteId", 1);
            mainObject.put("FeId", 1);

            JSONArray EquipmentArray = new JSONArray();
            JSONArray feedBackArray = new JSONArray();
            List<EquipmentInfo> equipmentList = atmdbHelper.getEquipmentInfoData();
            ArrayList<AccFeedBack> accessoriesList = atmdbHelper.getSelectedAccessoriesInfo();
            if (equipmentList != null) {
                for (EquipmentInfo equipmentInfo : equipmentList) {
                    JSONObject EquipmentObject = new JSONObject();
                    EquipmentObject.put("EquipId", equipmentInfo.getEquipId());
                    EquipmentObject.put("MakeId", equipmentInfo.getMakeId());
                    EquipmentObject.put("CapacityId", equipmentInfo.getCapacityId());
                    EquipmentObject.put("SerialNo", equipmentInfo.getSerialNo());
                    EquipmentObject.put("SCMDescId", equipmentInfo.getSCMDescId());
                    EquipmentObject.put("SCMCodeId", equipmentInfo.getSCMCodeId());
                    EquipmentObject.put("QRCode", equipmentInfo.getQRCode());
                    EquipmentObject.put("remarke", equipmentInfo.getRemarke());
                    EquipmentArray.put(EquipmentObject);
                }
            }
            //set accessories value into json object
            if (accessoriesList != null) {
                for (AccFeedBack feedBack : accessoriesList) {
                    JSONObject feedBackObject = new JSONObject();
                    feedBackObject.put("accId", feedBack.getParentId());
                    feedBackObject.put("accStatus", feedBack.getId());
                    feedBackArray.put(feedBackObject);
                }
            }
            mainObject.put("Equipment", EquipmentArray);
            mainObject.put("Accessories", feedBackArray);
            if (equipmentList != null && equipmentList.size() > 0) {
                if (ASTUIUtil.isOnline(getContext())) {
                    saveEquipmentDataService(mainObject);
                }
            } else {
                ASTUIUtil.showToast("Please select atleast one Equipment and filled all data!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //save equipement data into server
    public void saveEquipmentDataService(JSONObject mainObject) {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL_API + Contants.InstallEquipment;
        serviceCaller.CallCommanServiceMethod(serviceURL, mainObject, "saveEquipmentDataService", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    // parseandsaveSiteEquipListData(result);
                    //atmdbHelper.deleteAllRows();
                    // atmdbHelper.deleteAllRows();
                } else {
                    ASTUIUtil.showToast("Data Not Availabale");
                }
                _progrssBar.dismiss();
            }
        });
    }
}