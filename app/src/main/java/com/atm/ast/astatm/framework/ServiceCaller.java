package com.atm.ast.astatm.framework;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.activity.MainActivity;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.model.ContentData;
import com.atm.ast.astatm.model.EquipListDataModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import com.atm.ast.astatm.constants.Contants;

/**
 * Created by Neeraj on 7/25/2017.
 */
public class ServiceCaller {
    Context context;

    public ServiceCaller(Context context) {
        this.context = context;
    }


    //call Commen Method ForCall Servier Data
    public void CallCommanServiceMethod(final String loginUrl, String methodNmae, final IAsyncWorkCompletedCallback workCompletedCallback) {
        new ServiceHelper().callService(loginUrl, null, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                    if (Contants.IS_DEBUG_LOG) {
                        Log.d(Contants.LOG_TAG, methodNmae + "********" + result);
                    }
                } else {
                    workCompletedCallback.onDone(methodNmae, false);
                }
            }
        });
    }

    //call Commen Method ForCall Servier Data with json Object
    public void CallCommanServiceMethod(final String loginUrl, JSONObject jsonObject, String methodNmae, final IAsyncWorkCompletedCallback workCompletedCallback) {
        Log.d(Contants.LOG_TAG, methodNmae + "********" + jsonObject.toString() + "Url*****" + loginUrl);
        new ServiceHelper().callService(loginUrl, jsonObject, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                    Log.d(Contants.LOG_TAG, methodNmae + "********" + result);
                } else {
                    workCompletedCallback.onDone(methodNmae, false);
                }
            }
        });
    }


    //call getEquipListData
    public void callgetEquipListDataServices(final String loginUrl, final IAsyncWorkCompletedCallback workCompletedCallback) {
        new ServiceHelper().callService(loginUrl, null, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    parseEquipListDataServices(result, workCompletedCallback);
                } else {
                    workCompletedCallback.onDone("getEquipListData done", false);
                }
            }
        });
    }


    /*
     *
     * Parse and Validate Login Service Data
     */

    public void parseEquipListDataServices(String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                Boolean flag = false;
                if (result != null) {
                    try {
                        JSONObject jsonRootObject = new JSONObject(result);
                        String jsonStatus = jsonRootObject.optString("Status").toString();
                        ATMDBHelper atmDatabase = new ATMDBHelper(ApplicationHelper.application().getContext());
                        atmDatabase.deleteAllRows("equipment_list");
                        if (jsonStatus.equals("2")) {
                            JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String equipId = jsonObject.optString("Id").toString();
                                String equipName = jsonObject.optString("Name").toString();
                                String parentId = jsonObject.optString("ParentId").toString();
                                EquipListDataModel equipListDataModel = new EquipListDataModel();
                                equipListDataModel.setEquipId(equipId);
                                equipListDataModel.setEquipName(equipName);
                                equipListDataModel.setEquipParentId(parentId);

                                atmDatabase.insertEquipmentList(equipListDataModel);
                            }
                            flag = true;
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //   e.printStackTrace();
                    }
                }

                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                if (flag) {
                    workCompletedCallback.onDone("getEquipListData done", true);

                } else {
                    workCompletedCallback.onDone("getEquipListData done", false);
                }
            }
        }.execute();
    }


    //call getEquipListData
    public void callgetEquopentList(final String url, final IAsyncWorkCompletedCallback workCompletedCallback) {
        new ServiceHelper().callGetService(url, null, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("getEquipListData done", false);
                }
            }
        });
    }
}
