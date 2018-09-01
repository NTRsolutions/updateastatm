package com.atm.ast.astatm.fragment;

import android.view.View;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.component.FontViewField;
import com.atm.ast.astatm.resource.FNResources;
import com.atm.ast.astatm.utils.ASTStringUtil;
import com.atm.ast.astatm.utils.ASTUtil;

import static com.atm.ast.astatm.utils.FNObjectUtil.isEmptyStr;


public class HeaderFragment extends MainFragment {

    protected String headerTxt;
    protected TextView title;
    protected FontViewField sliderBtn,backButton;
    private boolean showBackButton;
    private boolean showMenuButton;

    @Override
    protected void getArgs() {
        this.showMenuButton = getArguments().getBoolean("showMenuButton",true);
        headerTxt = this.getArguments().getString("headerTxt", "");
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.header;
    }

    @Override
    protected void loadView() {
        this.sliderBtn = this.findViewById(R.id.sliderBtn);
        this.backButton = this.findViewById(R.id.backBtn);
        this.title = this.findViewById(R.id.title);
        getHostActivity().setDrawerState(showMenuButton);

    }

    @Override
    protected void setClickListeners() {
        this.sliderBtn.setOnClickListener(this);
        this.backButton.setOnClickListener(this);

    }

    @Override
    protected void setAccessibility() {
        this.sliderBtn.setVisibility(showMenuButton ? View.VISIBLE : View.GONE);
        this.backButton.setVisibility(showMenuButton ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void dataToView() {
        this.title.setText(headerTxt);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sliderBtn || v.getId() == R.id.backBtn) {
            if (this.getHostActivity() != null) {
                if (v.getId() == R.id.backBtn) {
                    this.getHostActivity().onBackPressed();
                } else {
                    this.getHostActivity().showSideNavigationPanel();
                }
            }
        }
    }

    public void updateTitle() {
        if (isEmptyStr(this.headerTxt)) {
            return;
        }
        if (FNResources.string.get(this.headerTxt) != 0) {
            ASTUtil.setTextFromResourceName(ASTStringUtil.getStringForID(FNResources.string.get(this.headerTxt)), this.title);
        }
    }
}
