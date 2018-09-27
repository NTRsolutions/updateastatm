package com.atm.ast.astatm.equipment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.AccessoriesAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.fragment.MainFragment;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.AccFeedBack;
import com.atm.ast.astatm.model.newmodel.Accessories;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccessoriesFragment extends MainFragment {
    private List<Data> allDataList;
    ArrayList<Accessories> accessoriesArrayList;
    ArrayList<AccFeedBack> accFeedBacks;
    private RecyclerView recyclerView;
    private ATMDBHelper atmdbHelper;
    AccessoriesAdapter adapter;

    @Override
    protected int fragmentLayout() {
        return R.layout.equipment_list_layout;
    }

    @Override
    protected void loadView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
       // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      //  recyclerView.setLayoutManager(linearLayoutManager);

        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
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
        allDataList = new ArrayList<>();
        allDataList = atmdbHelper.getAllEquipmentListData();
        if (allDataList != null) {
            for (Data dataModel : allDataList) {
                EquipmnetContentData contentDataa = dataModel.getEquipmnetContentData();
                if (contentDataa != null) {
                    accessoriesArrayList = new ArrayList<Accessories>(Arrays.asList(contentDataa.getAccessories()));
                    accFeedBacks = contentDataa.getAccFeedBack();
                }

            }
            if (accessoriesArrayList != null || accFeedBacks != null) {
                 adapter = new AccessoriesAdapter(getContext(), accessoriesArrayList, accFeedBacks);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ArrayList<AccFeedBack> selectedAccFeedbackList = adapter.selectedAccFeedbackList;
    }
}
