package com.atm.ast.astatm.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.RecycleViewAdapter;
import com.atm.ast.astatm.model.newmodel.Item;

import java.util.ArrayList;
import java.util.List;

public class EquipmnetQRCodeMain extends MainFragment {
    List<Item> productList;
    RecyclerView recyclerView;

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
        productList = new ArrayList<>();
        productList.add(new Item("Comm Card", R.drawable.ic_battery_with));
        productList.add(new Item("Inverter", R.drawable.ic_battery_with));
        productList.add(new Item("Battery Bank", R.drawable.ic_power_bank));
        productList.add(new Item("Sim", R.drawable.ic_sim));
        productList.add(new Item("Solar Panel", R.drawable.ic_solar_panels_couple_in_sunlight));
        productList.add(new Item("Structure ", R.drawable.ic_diagram));
        productList.add(new Item("Transfermor Based Charger ", R.drawable.ic_battery));
        productList.add(new Item("MPPT ", R.drawable.ic_battery_with));
        productList.add(new Item("Power Card ", R.drawable.ic_battery_with));
        productList.add(new Item("Boskit", R.drawable.ic_battery_with));
        //creating recyclerview adapter
        RecycleViewAdapter adapter = new RecycleViewAdapter(getContext(), productList);
        recyclerView.setAdapter(adapter);
    }
}
