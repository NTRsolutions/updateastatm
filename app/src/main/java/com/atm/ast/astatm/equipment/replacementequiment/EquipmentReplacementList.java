package com.atm.ast.astatm.equipment.replacementequiment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.equipment.EquipmentListAdapter;
import com.atm.ast.astatm.fragment.MainFragment;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EquipmentReplacementList extends MainFragment {
    private List<Data> allEquipmentList;
    ArrayList<Equipment> equipmentList;
    private RecyclerView recyclerView;
    private ATMDBHelper atmdbHelper;
    private List<Equipment> alldispEquipmentList;

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_replacemen_list;
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

    }




}