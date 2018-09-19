package com.atm.ast.astatm.utils;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.SiteDisplayDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author AST Inc.
 */

public class SiteNotFoundPopup {
    Point point = new Point();
    //PopupWindow popup = null;
    View layout;
    Button btnSendMessage, btncancel;
    TextView tvMessage;
    EditText etSiteId;
    String globalUserId;
    ArrayList siteDetailArrayList;
   // AtmDatabase atmDatabase;
    String[] arrSiteName;
    String[] arrSiteId;
    Context globalContext;
    String globalUserName;
    String shaluContact = "9540998988";
    //String shaluContact = "9899155788";

    ASTUIUtil commonFunctions;

    ProgressDialog progressbar;

    @TargetApi(Build.VERSION_CODES.M)
    public void getSitePopup(final PopupWindow popup, final Context context, final String strMessage,
                             final String btnText, String userId, final String popupType, String userName) {
        point.x = 100;
        point.y = 100;

        globalUserId = userId;
        globalUserName = userName;
        globalContext = context;

        commonFunctions = new ASTUIUtil();
        //popup = new PopupWindow(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        int popupWidth = width;
        int popupHeight = height - 40;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.site_not_found_popup, null);
        layout.bringToFront();
        //layout.getForeground().setAlpha(0);
        btnSendMessage = (Button) layout.findViewById(R.id.btnSendMessage);
        btnSendMessage.setText(btnText);
        btncancel = (Button) layout.findViewById(R.id.btncancel);
        tvMessage = (TextView) layout.findViewById(R.id.tvMessage);
        tvMessage.setText(strMessage);
        etSiteId = (EditText) layout.findViewById(R.id.etSiteId);
        if (popupType.equalsIgnoreCase("SMS")) {
            etSiteId.setVisibility(View.VISIBLE);
        }

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, arrParentData);

        lvFilterParent.setAdapter(adapter);*/


        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupType.equalsIgnoreCase("SMS")) {
                    String siteId = etSiteId.getText().toString();
                    if (siteId != null && !siteId.equals("")) {
                        commonFunctions.SendMessage(globalContext, shaluContact, siteId + " <> " + globalUserName);
                        popup.dismiss();
                    } else {
                        Toast.makeText(context, "Please enter site ID!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    getSiteSearchData();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 40;
        int OFFSET_Y = 40;
        Animation bottomUp = AnimationUtils.loadAnimation(context, R.anim.right_to_left);
        layout.startAnimation(bottomUp);
        layout.setVisibility(View.VISIBLE);
        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }


    /*
     *
      Calling Web Service to get Site Data--------------------------
      */
    private void getSiteSearchData() {
        progressbar = ProgressDialog.show(globalContext, "",
                "Please wait while getting data..", true);
        ServiceCaller serviceCaller = new ServiceCaller(globalContext);
        String serviceURL = "";
        serviceURL = Contants.BASE_URL + Contants.ALL_SITE_ID_URL;
        serviceURL += "&uid=" + globalUserId + "&lat=" + "0.000" + "&lon=" + "0.000";
        serviceCaller.CallCommanServiceMethod(serviceURL, "getSiteSearchData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsavegetSiteSearchData(result);
                } else {
                    ASTUIUtil.showToast("Site Search Data Not Avilable");
                    if(progressbar.isShowing()){
                        progressbar.dismiss();
                    }
                }
            }
        });
    }


    /*
     *
     * Parse and Save getEbrh Data
     */

    public void parseandsavegetSiteSearchData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("status").toString();
                siteDetailArrayList = new ArrayList<>();
                if (jsonStatus.equals("2")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("data");
                    arrSiteName = new String[jsonArray.length()];
                    arrSiteId = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String siteId = jsonObject.optString("SiteId").toString();
                        String customerSiteId = jsonObject.optString("CustomerSiteId").toString();
                        String siteName = jsonObject.optString("SiteName").toString();
                        String circleName = jsonObject.optString("Circle").toString();
                        String circleId = jsonObject.optString("CircleId").toString();
                        String clientId = jsonObject.optString("ClientId").toString();
                        String clientName = jsonObject.optString("Client").toString();
                        SiteDisplayDataModel siteDisplayDataModel = new SiteDisplayDataModel();
                        siteDisplayDataModel.setSiteId(customerSiteId);
                        siteDisplayDataModel.setSiteName(siteName);
                        siteDisplayDataModel.setSiteNumId(siteId);
                        siteDisplayDataModel.setCircleId(circleId);
                        siteDisplayDataModel.setCircleName(circleName);
                        siteDisplayDataModel.setClientName(clientName);
                        siteDisplayDataModel.setClientId(clientId);
                        siteDetailArrayList.add(siteDisplayDataModel);
                        arrSiteName[i] = String.valueOf(siteName);
                        arrSiteId[i] = String.valueOf(customerSiteId);
                    }
                } else if (jsonStatus.equals("0")) {
                    Toast.makeText(globalContext, "Data is not available", Toast.LENGTH_SHORT).show();
                }
                progressbar.dismiss();
            } catch (JSONException e) {
            }
        }
    }
}
