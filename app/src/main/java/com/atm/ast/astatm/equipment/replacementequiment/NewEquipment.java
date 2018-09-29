package com.atm.ast.astatm.equipment.replacementequiment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.fragment.MainFragment;
import com.atm.ast.astatm.utils.FontManager;

public class NewEquipment extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_equipment);
        loadView();
        setSupportActionBar(toolbar);
        toolbar=this.findViewById(R.id.toolbar);
        Typeface materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(this, "fonts/materialdesignicons-webfont.otf");
        TextView back = toolbar.findViewById(R.id.back);
        title = this.findViewById(R.id.title);
        title.setText("Add New Equipment");
        back.setTypeface(materialdesignicons_font);
        back.setText(Html.fromHtml("&#xf04e;"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setClickListeners();
    }


    protected void getArgs() {
    }

    protected void loadView() {

    }

    protected void setClickListeners() {
    }


    protected void dataToView() {
    }

    //get use data
    public void getSharedPrefData() {
    }


    @Override
    public void onClick(View view) {

    }
}

