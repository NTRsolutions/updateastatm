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
import com.atm.ast.astatm.adapter.CircleGridAdapter;
import com.atm.ast.astatm.adapter.FilterListAdapter;
import com.atm.ast.astatm.adapter.FilterListParentAdapter;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.fragment.CircleFragment;
import com.atm.ast.astatm.model.newmodel.Activity;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AST Inc.
 */
public class FilterPopupActivity {

    Point point = new Point();
    View layout;
    ListView lvFilterParent, lvFilterChild;
    Button btnFilter;
    TextView btnCancel;
    ArrayList<Activity> activityList;
    List<Data> circleList;
    ATMDBHelper atmdbHelper;

    public void getFilterPopup(final PopupWindow popup, final Context context, String[] arrParentData, final String[] arrCustomerId, final String activityName) {
        atmdbHelper = new ATMDBHelper(context);
        getAllCircle();
        getAllActivity();

        point.x = 0;
        point.y = 0;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        int popupWidth = width;
        int popupHeight = height - 40;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.filters_popup, null);

        lvFilterParent = (ListView) layout.findViewById(R.id.lvFilterParent);
        lvFilterChild = (ListView) layout.findViewById(R.id.lvFilterChild);

        btnFilter = (Button) layout.findViewById(R.id.btnFilter);
        btnCancel = (TextView) layout.findViewById(R.id.btnCancel);

        lvFilterParent.setEmptyView(lvFilterParent);
        lvFilterParent.setAdapter(new FilterListParentAdapter(context, arrParentData));

        lvFilterChild.setEmptyView(lvFilterChild);
        lvFilterChild.setAdapter(new FilterListAdapter(context, activityList, 0, activityName));

        lvFilterParent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    lvFilterChild.setEmptyView(lvFilterChild);
                    lvFilterChild.setAdapter(new FilterListAdapter(context, activityList, position, activityName));
                    parent.getChildAt(0).setBackgroundColor(Color.parseColor("#ededed"));
                    //parent.getChildAt(0).
                    parent.getChildAt(1).setBackgroundColor(Color.parseColor("#d9d9d9"));
                    //lvFilterParent.setBackgroundColor(Color.parseColor("#078f4b"));
                } else if (position == 1) {
                    lvFilterChild.setEmptyView(lvFilterChild);
                    lvFilterChild.setAdapter(new FilterListAdapter(context, circleList, position, activityName));
                    parent.getChildAt(1).setBackgroundColor(Color.parseColor("#ededed"));
                    //parent.getChildAt(0).
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
                CircleFragment.filterString = "";
                popup.dismiss();
//                getCircleData(filterString);
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

        Animation bottomUp = AnimationUtils.loadAnimation(context,
                R.anim.right_to_left);

        layout.startAnimation(bottomUp);
        layout.setVisibility(View.VISIBLE);
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, point.x + OFFSET_X, point.y + OFFSET_Y);
    }

    //get all activity list
    private void getAllActivity() {
        ArrayList<Data> dataList = atmdbHelper.getAllActivityDropdownData();
        activityList = new ArrayList<Activity>();
        for (Data data : dataList) {
            for (Activity activity : data.getActivity()) {
                activityList.add(activity);
            }
        }
    }

    //get all circle list
    private void getAllCircle() {
        List<ServiceContentData> contentData = atmdbHelper.getAllcircleDetailsData();
        if (contentData != null && contentData.size() > 0) {
            for (ServiceContentData data : contentData) {
                if (data != null) {
                    Data[] circleData = data.getData();
                    if (circleData != null) {
                        circleList = new ArrayList<>(Arrays.asList(circleData));
                    }
                }
            }
        }
    }
}
