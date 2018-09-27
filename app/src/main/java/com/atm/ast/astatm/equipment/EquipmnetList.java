package com.atm.ast.astatm.equipment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.fragment.MainFragment;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EquipmnetList extends MainFragment {
    private List<Data> allEquipmentList;
    ArrayList<Equipment> equipmentList;
    private RecyclerView recyclerView;
    private ATMDBHelper atmdbHelper;
    private List<Equipment> alldispEquipmentList;

    @Override
    protected int fragmentLayout() {
        return R.layout.equipment_list_layout;
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
        checkDispatchEquipmentExistorNot();
    }


    //check dispatch equipment exist or not
    private void checkDispatchEquipmentExistorNot() {
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
        ArrayList<Equipment> equipmentListtemp = new ArrayList<>();
        if (equipmentList != null) {
            for (Equipment equipment : equipmentList) {
                if (distpactEqupmentExtorNot(equipment.getId())) {
                    equipment.setSelectedOrNote(true);
                }
                equipmentListtemp.add(equipment);
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

    public boolean distpactEqupmentExtorNot(int id) {
        if (alldispEquipmentList != null) {
            for (Equipment equpment : alldispEquipmentList) {
                if (equpment.getEquipId() == id) {
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