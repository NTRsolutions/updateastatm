package com.atm.ast.astatm.equipment.replacementequiment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.fragment.MainFragment;
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

public class AllEquipmentList extends MainFragment {
    private List<Data> allEquipmentList;
    ArrayList<Equipment> equipmentList;
    private RecyclerView recyclerView;
    private ATMDBHelper atmdbHelper;
    private List<Equipment> alldispEquipmentList;

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_all_equipment_list;
    }

    @Override
    protected void loadView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);


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
        if (ASTUIUtil.isOnline(getContext())) {
            getSiteEquipListData();
        } else {
            ASTUIUtil.showToast("You Phone is offline");
        }
    }


    //check dispatch equipment exist or not
    private void getequipmentList() {
        alldispEquipmentList = new ArrayList<>();
        alldispEquipmentList = atmdbHelper.getDispatchEquipmentData(0);//need to change
        allEquipmentList = new ArrayList<>();
        allEquipmentList = atmdbHelper.getAllEquipmentListData();
        if (allEquipmentList != null) {
            for (Data dataModel : allEquipmentList) {
                EquipmnetContentData contentData = dataModel.getEquipmnetContentData();
                if (contentData != null) {
                    equipmentList = new ArrayList<Equipment>(Arrays.asList(contentData.getEquipment()));
                }
            }
        }
        setAdapter(equipmentList);
    }


    /**
     * set Equpment List Adapater
     *
     * @param equipmentListwithDisp
     */

    public void setAdapter(ArrayList<Equipment> equipmentListwithDisp) {
        if (equipmentListwithDisp != null) {
            AllEquipmentListAdapter adapter = new AllEquipmentListAdapter(getContext(), equipmentList);
            recyclerView.setAdapter(adapter);
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
        getequipmentList();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                saveEquipmentData();
                break;
        }
    }

    private void saveEquipmentData() {
        List<EquipmentInfo> allDataList = atmdbHelper.getEquipmentInfoData();
        if (allDataList != null) {
            for (EquipmentInfo equipmentInfo : allDataList) {
               /* EquipId = equipmentInfo.getEquipId();
                MakeId = equipmentInfo.getMakeId();
                CapacityId = equipmentInfo.getCapacityId();
                SerialNo = equipmentInfo.getSerialNo();
                SCMDescId = equipmentInfo.getSCMDescId();
                SCMCodeId = equipmentInfo.getSCMCodeId();
                QRCode = equipmentInfo.getQRCode();
                remarke = equipmentInfo.getRemarke();*/
            }

        }
    }
}