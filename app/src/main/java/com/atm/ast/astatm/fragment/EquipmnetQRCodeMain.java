package com.atm.ast.astatm.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.EquipmentListAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmentInfo;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EquipmnetQRCodeMain extends MainFragment {
    private List<Data> allDataList;
    ArrayList<Equipment> equipmentList;
    private RecyclerView recyclerView;
    private ATMDBHelper atmdbHelper;
    private List<EquipmentInfo> alldispEquipmentList;

    @Override
    protected int fragmentLayout() {
        return R.layout.recyclerview_layout;
    }

    @Override
    protected void loadView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        //  RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        //    recyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        atmdbHelper = new ATMDBHelper(getContext());
        getSiteEquipListData();
        getDispatchEquipment();

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
                    allDataList = new ArrayList<>();
                    allDataList = atmdbHelper.getAllEquipmentListData();
                    if (allDataList != null) {
                        for (Data dataModel : allDataList) {
                            EquipmnetContentData contentDataa = dataModel.getEquipmnetContentData();
                            equipmentList = new ArrayList<Equipment>(Arrays.asList(contentDataa.getEquipment()));

                        }

                    }
                }

            }
            checkEquuipmentExiorNot();
        }
    }


    /**
     * Calling Web Service to get Site GetDispatchEquipment List Data
     */

    public void getDispatchEquipment() {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL_API + Contants.DISPATCH_EQUIPMENT;
        serviceCaller.callgetEquopentList(serviceURL, new IAsyncWorkCompletedCallback() {
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
            EquipmentInfo contentData = new Gson().fromJson(result, EquipmentInfo.class);
            if (contentData != null) {
                if (contentData.getStatus() == 2) {
                    atmdbHelper.deleteAllRows("DispatchEquipment");
                    atmdbHelper.upsertDispatchEquipmentData(contentData);
                    alldispEquipmentList = new ArrayList<>();
                    alldispEquipmentList = atmdbHelper.getDispatchEquipmentData(contentData.getSiteId());
                }

            }

        }
    }


    private void checkEquuipmentExiorNot() {
        ArrayList<Equipment> equipmentListtemp = new ArrayList<>();
        if (allDataList != null) {
            for (Data dataModel : allDataList) {
                EquipmnetContentData contentDataa = dataModel.getEquipmnetContentData();
                ArrayList<Equipment> equipmentList = new ArrayList<Equipment>(Arrays.asList(contentDataa.getEquipment()));
                for (Equipment equipment : equipmentList) {
                    if (distpactEqupmentExtorNot(String.valueOf(equipment.getId()))) {
                        equipment.setSelectedOrNote(true);
                    }
                    equipmentListtemp.add(equipment);
                }

            }
            setAdapter(equipmentListtemp);
        }
    }

    /**
     * get disptach Equpment exit or not
     *
     * @param id
     * @return
     */

    public boolean distpactEqupmentExtorNot(String id) {
        if (alldispEquipmentList != null) {
            for (EquipmentInfo equpment : alldispEquipmentList) {
                if (id.equals(equpment.getEquipId())) {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * set Equpment List Adapater
     *
     * @param equipmentListwithDisp
     */

    public void setAdapter(ArrayList<Equipment> equipmentListwithDisp) {
        if (equipmentListwithDisp != null) {
            EquipmentListAdapter adapter = new EquipmentListAdapter(getContext(), equipmentListwithDisp);
            recyclerView.setAdapter(adapter);
        }
    }
}