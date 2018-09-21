package com.atm.ast.astatm.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.AccessoriesAdapter;
import com.atm.ast.astatm.adapter.EquipmentListAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.newmodel.AccFeedBack;
import com.atm.ast.astatm.model.newmodel.Accessories;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.atm.ast.astatm.model.newmodel.Make;
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

    @Override
    protected int fragmentLayout() {
        return R.layout.recyclerview_layout;
    }

    @Override
    protected void loadView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        getSiteEquipListData();
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
                    atmdbHelper.insertSiteEquipmentData(data);
                    allDataList = new ArrayList<>();
                    allDataList = atmdbHelper.getAllEquipmentListData();
                    if (allDataList != null) {
                        for (Data dataModel : allDataList) {
                            EquipmnetContentData contentDataa = dataModel.getEquipmnetContentData();
                            accessoriesArrayList = new ArrayList<Accessories>(Arrays.asList(contentDataa.getAccessories()));
                            accFeedBacks = contentDataa.getAccFeedBack();

                        }
                        if (accessoriesArrayList != null || accFeedBacks != null) {
                            AccessoriesAdapter adapter = new AccessoriesAdapter(getContext(), accessoriesArrayList, accFeedBacks);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }

            }
        }
    }


}
