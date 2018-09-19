package com.atm.ast.astatm.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.utils.ASTUIUtil;


/**
 * @author AST Inc.
 */
public class AstAppUgradeDlgActivity extends Dialog implements View.OnClickListener {
    private boolean isStopPrevVersion;
    private Activity activity;

    public AstAppUgradeDlgActivity(Activity activity1) {
        super(activity1);
        this.activity = activity1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (this.getWindow() != null) {
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        this.setContentView(R.layout.app_upgrade);
        setBackgroundRound();
        this.findViewById(R.id.upgrade).setOnClickListener(this);
        this.findViewById(R.id.skip).setOnClickListener(this);
        if (this.isStopPrevVersion) {
            this.hideSkip();
        }
    }

    private void setBackgroundRound() {
        float containerRadius = ASTUIUtil.getDimension(R.dimen._10dp);
        float buttonRadius = ASTUIUtil.getDimension(R.dimen._50dp);
        ASTUIUtil.setBackgroundRound(this.findViewById(R.id.update_available), android.R.color.white, new float[]{containerRadius, containerRadius, containerRadius, containerRadius, 0, 0, 0, 0});
        ASTUIUtil.setBackgroundRect(this.findViewById(R.id.dialogLayout), ASTUIUtil.getColor(R.color.gray), containerRadius);
        ASTUIUtil.setBackgroundRect(this.findViewById(R.id.appLogoContainer), android.R.color.white, containerRadius);
        ASTUIUtil.setBackgroundRect(this.findViewById(R.id.skip), ASTUIUtil.getColor(R.color.blue2), buttonRadius);
        ASTUIUtil.setBackgroundRect(this.findViewById(R.id.upgrade), ASTUIUtil.getColor(R.color.greenLight), buttonRadius);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.skip) {
            this.dismiss();
            this.onSkip();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.getContext().getPackageName()));
            this.activity.startActivity(intent);
        }
    }

    public void onSkip() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void hideSkip() {
        this.findViewById(R.id.skip).setVisibility(View.GONE);
    }
}