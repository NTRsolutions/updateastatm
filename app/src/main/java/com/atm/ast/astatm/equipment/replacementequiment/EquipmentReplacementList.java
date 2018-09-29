package com.atm.ast.astatm.equipment.replacementequiment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.equipment.EquipMentBarcodeFragment;
import com.atm.ast.astatm.equipment.EquipmentListAdapter;
import com.atm.ast.astatm.fragment.MainFragment;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmentInfo;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("ValidFragment")
public class EquipmentReplacementList extends MainFragment {
    private RecyclerView recyclerView;
    private Spinner equipmentMakeSpinnernew, equipmentCapacitySpinnernew, equipmentsrnoSpinnernew,
            equipmentscmSpinnernew, equipmentscmpartSpinnernew;

    AppCompatEditText etequipmentqrcodenew;
    private String equipmentMakestr, equipmentCapacitystr, equipmentsrnostr, equipmentscmstr, equipmentscmpartstr,
            equipmentqrstr,
            equipmentMakestrnew, equipmentCapacitystrnew, equipmentsrnostrnew,
            equipmentscmstrnew, equipmentscmpartstrnew,
            equipmentqrstrnew;
    int screenPosition;
    TextView previous, next, done,equipmentMakeSpinner, equipmentCapacitySpinner, equipmentsrnoSpinner, equipmentscmSpinner, equipmentscmpartSpinner,
            etequipmentqrcode;

    @SuppressLint("ValidFragment")
    public EquipmentReplacementList(int postion) {
        this.screenPosition = postion;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_replacemen_list;
    }

    @Override
    protected void loadView() {
        previous = view.findViewById(R.id.previous);
        next = view.findViewById(R.id.next);
        done = view.findViewById(R.id.done);
        equipmentMakeSpinner = view.findViewById(R.id.equipmentMakeSpinner);
        equipmentCapacitySpinner = view.findViewById(R.id.equipmentCapacitySpinner);
        equipmentsrnoSpinner = view.findViewById(R.id.equipmentsrnoSpinner);
        equipmentscmSpinner = view.findViewById(R.id.equipmentscmSpinner);
        equipmentscmpartSpinner = view.findViewById(R.id.equipmentscmpartSpinner);
        etequipmentqrcode = view.findViewById(R.id.etequipmentqrcode);
        equipmentMakeSpinnernew = view.findViewById(R.id.equipmentMakeSpinnernew);
        equipmentCapacitySpinnernew = view.findViewById(R.id.equipmentCapacitySpinnernew);
        equipmentsrnoSpinnernew = view.findViewById(R.id.equipmentsrnoSpinnernew);
        equipmentscmSpinnernew = view.findViewById(R.id.equipmentscmSpinnernew);
        equipmentscmpartSpinnernew = view.findViewById(R.id.equipmentscmpartSpinnernew);
        etequipmentqrcodenew = view.findViewById(R.id.etequipmentqrcodenew);

    }


    protected void setClickListeners() {
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        done.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.qrCodeImage) {
            //  IntentIntegrator.forSupportFragment(EquipmentReplacementList.this).initiateScan();
        } else if (view.getId() == R.id.previous) {
            //  saveScreenData(false);
        } else if (view.getId() == R.id.next) {
            //  saveScreenData(true);

        }
    }


    //save data into db
    private void saveEquipmentInfo(boolean NextPreviousFlag) {
        Intent intent = new Intent("ViewPageChange");
        intent.putExtra("NextPreviousFlag", NextPreviousFlag);
        intent.putExtra("screenPosition", screenPosition);
        getActivity().sendBroadcast(intent);
    }

    private void saveScreenData(boolean NextPreviousFlag) {
        saveEquipmentInfo(NextPreviousFlag);
    }

}