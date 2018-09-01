package com.atm.ast.astatm.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.FilterListAdapter;
import com.atm.ast.astatm.adapter.FilterListParentAdapter;
import com.atm.ast.astatm.fragment.CircleFragment;

/**
 * @author AST Inc.
        */
public class FilterPopupCircle {

    Point point = new Point();
    View layout;
    ListView lvFilterParent, lvFilterChild;
    Button btnFilter;
    TextView btnCancel;
    public void getFilterPopup(final PopupWindow popup, final Context context, String[] arrParentData, final String[] arrAlarmTypes, final String[] arrCustomers, final String[] arrCustomerId, final String strActivity) {
        point.x = 0;
        point.y = 0;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        int popupWidth = width;
        int popupHeight = height - 40;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.filters_popup, null);
        lvFilterParent = (layout.findViewById(R.id.lvFilterParent));
        lvFilterChild = layout.findViewById(R.id.lvFilterChild);
        btnFilter = layout.findViewById(R.id.btnFilter);
        btnCancel = layout.findViewById(R.id.btnCancel);
        lvFilterParent.setEmptyView(lvFilterParent);
        lvFilterParent.setAdapter(new FilterListParentAdapter(context, arrParentData));
        lvFilterChild.setEmptyView(lvFilterChild);
       // lvFilterChild.setAdapter(new FilterListAdapter(context, arrAlarmTypes, 0, strActivity));
        lvFilterParent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    lvFilterChild.setEmptyView(lvFilterChild);
                  //  lvFilterChild.setAdapter(new FilterListAdapter(context, arrAlarmTypes, position, strActivity));
                    parent.getChildAt(0).setBackgroundColor(Color.parseColor("#ededed"));
                    parent.getChildAt(1).setBackgroundColor(Color.parseColor("#d9d9d9"));
                } else if (position == 1) {
                    lvFilterChild.setEmptyView(lvFilterChild);
                   // lvFilterChild.setAdapter(new FilterListAdapter(context, arrCustomers, position, strActivity));
                    parent.getChildAt(1).setBackgroundColor(Color.parseColor("#ededed"));
                    parent.getChildAt(0).setBackgroundColor(Color.parseColor("#d9d9d9"));
                } else {

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int batteryLowFilterstatus = 0, noCommLowFilterstatus = 0, INVFilterstatus = 0, NSMFilterstatus = 0;
                if (CircleFragment.arrSelectedFilterOne[0] == true) {
                    batteryLowFilterstatus = 1;
                }
                if (CircleFragment.arrSelectedFilterOne[1] == true) {
                    noCommLowFilterstatus = 1;
                }
                if (CircleFragment.arrSelectedFilterOne[2] == true) {
                    INVFilterstatus = 1;
                }
                if (CircleFragment.arrSelectedFilterOne[3] == true) {
                    NSMFilterstatus = 1;
                }
                if (batteryLowFilterstatus == 1) {
                    if (CircleFragment.filterString.equals("") || CircleFragment.filterString.equals("NA")) {
                        CircleFragment.filterString = "BL1";
                    } else {
                        CircleFragment.filterString += "BL1";
                    }
                }
                if (noCommLowFilterstatus == 1) {
                    if (CircleFragment.filterString.equals("") || CircleFragment.filterString.equals("NA")) {
                        CircleFragment.filterString = "NOCOMM";
                    } else {
                        CircleFragment.filterString += ",NOCOMM";
                    }
                }
                //-------NSM Filter-----------------
                if (NSMFilterstatus == 1) {
                    if (CircleFragment.filterString.equals("") || CircleFragment.filterString.equals("NA")) {
                        CircleFragment.filterString = "NSM";
                    } else {
                        CircleFragment.filterString += ",NSM";
                    }
                }
                if (INVFilterstatus == 1) {
                    if (CircleFragment.filterString.equals("") || CircleFragment.filterString.equals("NA")) {
                        CircleFragment.filterString = "INV";
                    } else {
                        CircleFragment.filterString += ",INV";
                    }
                }
                CircleFragment.ctid = "";
                for (int i = 0; i < CircleFragment.arrSelectedFilterTwo.length; i++) {
                    if (CircleFragment.arrSelectedFilterTwo[i] == true) {
                        if (CircleFragment.ctid.equals("")) {
                            CircleFragment.ctid = arrCustomerId[i];
                        } else {
                            CircleFragment.ctid += "," + arrCustomerId[i];
                        }
                    }
                }
                popup.dismiss();
            }
        });
        // Creating the PopupWindow
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setTouchable(true);
        popup.setAnimationStyle((true) ? R.style.leftNavPopup : R.style.leftNavPopup);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.setAnimationStyle(R.anim.bottom_up_screen);
        int OFFSET_X = 0;
        int OFFSET_Y = 40;
        Animation bottomUp = AnimationUtils.loadAnimation(context, R.anim.right_to_left);
        layout.startAnimation(bottomUp);
        layout.setVisibility(View.VISIBLE);


        popup.showAtLocation(layout, Gravity.NO_GRAVITY, point.x + OFFSET_X, point.y + OFFSET_Y);
    }


}
