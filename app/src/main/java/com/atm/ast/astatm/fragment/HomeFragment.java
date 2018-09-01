package com.atm.ast.astatm.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.utils.ASTReqResCode;
import com.atm.ast.astatm.utils.ASTUIUtil;

import static com.atm.ast.astatm.ApplicationHelper.application;


/**
 * Created by Narayan .
 */
public class HomeFragment extends MainFragment {
    TextView result;
    ImageView camera;
    String searchWord;

    @Override
    protected int fragmentLayout() {
        return R.layout.home_fragment;
    }

    @Override
    protected void loadView() {
        result = this.findViewById(R.id.result);
        this.camera = this.findViewById(R.id.cammera);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        init();
    }

    private void init() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHostActivity().requestPermission(ASTReqResCode.PERMISSION_REQ_CAMERA);
            }
        });
    }


    @Override
    public boolean onBackPressed() {
        if (application().isClickedMenuIsPrimary()) {
            this.moveTaskToBack();
            return false;
        }
        return super.onBackPressed();
    }

    @Override
    public void permissionGranted(int requestCode) {
        super.permissionGranted(requestCode);
        if (requestCode == ASTReqResCode.PERMISSION_REQ_CAMERA) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(ApplicationHelper.application().getActivity());
            scanIntegrator.initiateScan();
            ASTUIUtil.showToast("granteed");
        }
    }


    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        super.updateOnResult(requestCode, resultCode, data);
        if (data != null && requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                this.searchWord = scanContent;
                ASTUIUtil.showToast("update result");
                ASTUIUtil.showToast(scanContent);
                result.setText(scanContent);
            } else {
                ASTUIUtil.showToast("no bar");
            }
        }
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        System.out.println("never here");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String scanContent = scanResult.getContents();
            String scanFormat = scanResult.getFormatName();
            ASTUIUtil.showToast( scanContent, Toast.LENGTH_LONG).show();
            this.searchWord = scanContent;
            ASTUIUtil.showToast( "update result on activity", Toast.LENGTH_LONG).show();
            result.setText(scanContent);
        }
    }*/
}