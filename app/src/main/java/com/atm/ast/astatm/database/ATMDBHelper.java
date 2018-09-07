package com.atm.ast.astatm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.model.ActivitySheetReportDataModel;
import com.atm.ast.astatm.model.newmodel.Activity;
import com.atm.ast.astatm.model.newmodel.ContentLocalData;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.District;
import com.atm.ast.astatm.model.newmodel.FieldEngineer;
import com.atm.ast.astatm.model.newmodel.Header;
import com.atm.ast.astatm.model.newmodel.NOCEngineer;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ATMDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "AST_ATM_DB";

    public ATMDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Today_Plan_TABLE = "CREATE TABLE todaySitePlan(SiteId TEXT,CustomerSiteId TEXT, SiteName TEXT,siteStatus INTEGER DEFAULT 0)";
        db.execSQL(CREATE_Today_Plan_TABLE);
        String CREATE_State_Detail_TABLE = "CREATE TABLE stateDetails(CircleId TEXT,Circle TEXT,DistrictJsonStr TEXT)";
        db.execSQL(CREATE_State_Detail_TABLE);
        String CREATE_Circle_Details_TABLE = "CREATE TABLE circleDetailsData(header TEXT,data TEXT)";
        db.execSQL(CREATE_Circle_Details_TABLE);
        String CREATE_CustomerData_TABLE = "CREATE TABLE CustomerData(ctId INTEGER, ctName TEXT)";
        db.execSQL(CREATE_CustomerData_TABLE);
        String CREATE_SiteListData_TABLE = "CREATE TABLE SiteListData(SiteId TEXT, CustomerSiteId TEXT,SiteName TEXT,CircleId TEXT,Circle TEXT,ClientId TEXT,Client TEXT,Lat TEXT,Lon TEXT,BaseDistance TEXT,BaseLocationId TEXT,BaseLocation TEXT,BaseLocationLat TEXT,BaseLocationLon TEXT)";
        db.execSQL(CREATE_SiteListData_TABLE);
        String CREATE_ClusterDetails_TABLE = "CREATE TABLE ClusterDetails(header TEXT,data TEXT)";
        db.execSQL(CREATE_ClusterDetails_TABLE);
        String CREATE_SiteDetails_TABLE = "CREATE TABLE SiteDetails(header TEXT,data TEXT)";
        db.execSQL(CREATE_SiteDetails_TABLE);
        String CREATE_ActivityDropdownDetails_TABLE = "CREATE TABLE ActivityDropdownDetails(TaskId INTEGER,TaskName TEXT, Activity TEXT)";
        db.execSQL(CREATE_ActivityDropdownDetails_TABLE);
        String CREATE_PlanActivityDetails_TABLE = "CREATE TABLE PlanActivityDetails(PlanId INTEGER,PlanDate TEXT, SiteId INTEGER,CustomerSiteId TEXT,SiteName TEXT,ActivityId INTEGER,ActivityName TEXT,md TEXT,remark TEXT,TaskId INTEGER,Task TEXT,ComplaintMessage TEXT,CircleId INTEGER,Circle TEXT,FEId INTEGER,FEName TEXT)";
        db.execSQL(CREATE_PlanActivityDetails_TABLE);
        String CREATE_PlanActivityHeaderDetails_TABLE = "CREATE TABLE PlanActivityHeaderDetails(Planned INTEGER,Executed INTEGER, OnTheWay TEXT,ReachedSite TEXT,LeftSite TEXT,Unknown TEXT,Circle TEXT,AttendanceCount INTEGER,WorkingCount INTEGER,LeaveCount INTEGER)";
        db.execSQL(CREATE_PlanActivityHeaderDetails_TABLE);
        String CREATE_NOCEngineer_TABLE = "CREATE TABLE NOCEngineer(NocEngId INTEGER,NocEngName TEXT, ContactNo TEXT)";
        db.execSQL(CREATE_NOCEngineer_TABLE);
        String CREATE_FieldEngineer_TABLE = "CREATE TABLE FieldEngineer(FieldEngId INTEGER,FieldEngName TEXT, ContactNo TEXT)";
        db.execSQL(CREATE_FieldEngineer_TABLE);
        String CREATE_ActivtyFormData_TABLE = "CREATE TABLE ActivtyFormData(planId TEXT,activtyFormData TEXT)";
        db.execSQL(CREATE_ActivtyFormData_TABLE);
        String CREATE_ActivitySheetReportHeaderDetails_TABLE = "CREATE TABLE ActivitySheetReportHeaderDetails(Planned INTEGER,Executed INTEGER, OnTheWay TEXT,ReachedSite TEXT,LeftSite TEXT,Unknown TEXT,Circle TEXT,AttendanceCount INTEGER,WorkingCount INTEGER,LeaveCount INTEGER)";
        db.execSQL(CREATE_ActivitySheetReportHeaderDetails_TABLE);
        String CREATE_ActivitySheetReportDetails_TABLE = "CREATE TABLE ActivitySheetReportDetails(siteName TEXT,Customer TEXT, ActivityDate TEXT,ActivityTime TEXT,ZoneType TEXT,TotalAmount TEXT,Status TEXT,Days TEXT,Color TEXT,NOCApprovel TEXT,Activity TEXT,TADA TEXT,Bonus TEXT,Penalty TEXT,Reason TEXT,CircleId TEXT,Circle TEXT,FEId TEXT,FEName TEXT)";
        db.execSQL(CREATE_ActivitySheetReportDetails_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS todaySitePlan");
        db.execSQL("DROP TABLE IF EXISTS stateDetails");
        db.execSQL("DROP TABLE IF EXISTS circleDetailsData");
        db.execSQL("DROP TABLE IF EXISTS CustomerData");
        db.execSQL("DROP TABLE IF EXISTS SiteListData");
        db.execSQL("DROP TABLE IF EXISTS ClusterDetails");
        db.execSQL("DROP TABLE IF EXISTS SiteDetails");
        db.execSQL("DROP TABLE IF EXISTS ActivityDropdownDetails");
        db.execSQL("DROP TABLE IF EXISTS PlanActivityDetails");
        db.execSQL("DROP TABLE IF EXISTS PlanActivityHeaderDetails");
        db.execSQL("DROP TABLE IF EXISTS NOCEngineer");
        db.execSQL("DROP TABLE IF EXISTS FieldEngineer");
        db.execSQL("DROP TABLE IF EXISTS ActivtyFormData");
        db.execSQL("DROP TABLE IF EXISTS ActivitySheetReportHeaderDetails");
        db.execSQL("DROP TABLE IF EXISTS ActivitySheetReportDetails");
        onCreate(db);
    }

    // upsert today site plan Data
    public boolean upsertTodaySitePlanData(Data ob) {
        boolean done = false;
        Data data = null;
        if (ob.getSiteId() != 0) {
            data = getTodaySitePlanDataBySiteId(ob.getSiteId());
            if (data == null) {
                done = insertTodaySitePlanData(ob);
            } else {
                done = updateTodaySitePlanData(ob);
            }
        }
        return done;
    }

    //get check today site plan data by siteid
    public Data getTodaySitePlanDataBySiteId(long siteId) {
        String query = "Select * FROM todaySitePlan WHERE SiteId = '" + siteId + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Data ob = new Data();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateTodaySitePlanData(cursor, ob);

            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate today site plan list data
    private void populateTodaySitePlanData(Cursor cursor, Data ob) {
        ob.setSiteId(Long.parseLong(cursor.getString(0)));
        ob.setCustomerSiteId(cursor.getString(1));
        ob.setSiteName(cursor.getString(2));
        ob.setSiteStatus(cursor.getInt(3));//for local use only
    }

    public boolean insertTodaySitePlanData(Data ob) {
        ContentValues values = new ContentValues();
        populateTodaySitePlanValueData(values, ob);

        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("todaySitePlan", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateTodaySitePlanData(Data ob) {
        ContentValues values = new ContentValues();
        populateTodaySitePlanValueData(values, ob);

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("todaySitePlan", values, " SiteId = '" + ob.getSiteId() + "'", null);

        db.close();
        return i > 0;
    }

    //complete site transit the update siteStatus
    public boolean updateTodaySiteTransitComplete(int siteStatus, String siteId) {
        ContentValues values = new ContentValues();

        values.put("siteStatus", siteStatus);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("todaySitePlan", values, " SiteId = '" + siteId + "' ", null);

        db.close();
        return i > 0;
    }

    public void populateTodaySitePlanValueData(ContentValues values, Data ob) {
        values.put("SiteId", String.valueOf(ob.getSiteId()));
        values.put("CustomerSiteId", ob.getCustomerSiteId());
        values.put("SiteName", ob.getSiteName());
        // values.put("siteStatus", 0);//for local use only default add 0 for uncomplete site
    }

    //get all today site plan data
    public ArrayList<Data> getAllTodaySiteListData() {
        String query = "Select *  FROM todaySitePlan WHERE siteStatus =0";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Data> list = new ArrayList<Data>();

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Data ob = new Data();
                populateTodaySitePlanData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    // upsert State details Data
    public boolean upsertStateDetailData(Data ob) {
        boolean done = false;
        Data data = null;
        if (ob.getCircleId() != 0) {
            data = getStateDetailDataByCircleId(ob.getCircleId());
            if (data == null) {
                done = insertStateDetailData(ob);
            } else {
                done = updateStateDetailData(ob);
            }
        }
        return done;
    }

    //get checkState details Data by id
    public Data getStateDetailDataByCircleId(long id) {
        String query = "Select * FROM stateDetails WHERE CircleId = '" + id + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Data ob = new Data();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateStateDetailData(cursor, ob);

            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate State details Data
    private void populateStateDetailData(Cursor cursor, Data ob) {
        ob.setCircleId(Long.parseLong(cursor.getString(0)));
        ob.setCircle(cursor.getString(1));
        District[] districtData = new Gson().fromJson(cursor.getString(2), new TypeToken<District[]>() {
        }.getType());
        ob.setDistrict(districtData);

    }

    public boolean insertStateDetailData(Data ob) {
        ContentValues values = new ContentValues();
        populateStateDetailValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("stateDetails", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateStateDetailData(Data ob) {
        ContentValues values = new ContentValues();
        populateStateDetailValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("stateDetails", values, " CircleId = '" + ob.getCircleId() + "'", null);

        db.close();
        return i > 0;
    }

    public void populateStateDetailValueData(ContentValues values, Data ob) {
        values.put("CircleId", String.valueOf(ob.getCircleId()));
        values.put("Circle", ob.getCircle());
        String DistrictStr = ASTGson.store().toJson(ob.getDistrict());
        values.put("DistrictJsonStr", DistrictStr);

    }

    //get allState details Data
    public ArrayList<Data> getAllStateDetailListData() {
        String query = "Select *  FROM stateDetails ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Data> list = new ArrayList<Data>();

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Data ob = new Data();
                populateStateDetailData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //-------------add circle data----------
    //get Circle Details Data
    public ServiceContentData getPopulateCircleDetailsData() {
        String query = "Select * FROM circleDetailsData ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ServiceContentData ob = new ServiceContentData();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateCircleDetailsData(cursor, ob);

            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate Circle Details Data
    private void populateCircleDetailsData(Cursor cursor, ServiceContentData ob) {
        //  Header[] headers = (Header[]) ASTGson.store().getObjectArray(cursor.getString(0));
        Header[] headers = new Gson().fromJson(cursor.getString(0), new TypeToken<Header[]>() {
        }.getType());
        ob.setHeader(headers);

        Data[] data = new Gson().fromJson(cursor.getString(1), new TypeToken<Data[]>() {
        }.getType());
        ob.setData(data);

    }

    public boolean insertCircleDetailsData(ServiceContentData ob) {
        ContentValues values = new ContentValues();
        populatecircleDetailsValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("circleDetailsData", null, values);
        db.close();
        return i > 0;
    }

    public void populatecircleDetailsValueData(ContentValues values, ServiceContentData ob) {
        String headerData = ASTGson.store().toJson(ob.getHeader());
        values.put("header", headerData);
        String data = ASTGson.store().toJson(ob.getData());
        values.put("data", data);
    }

    //get Circle Details Data
    public List<ServiceContentData> getAllcircleDetailsData() {
        String query = "Select *  FROM circleDetailsData ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<ServiceContentData> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                ServiceContentData ob = new ServiceContentData();
                populateCircleDetailsData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    public void deleteAllRows(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    //........................All Site List Data.....................................................
    // upsert all site list data
    public boolean upsertSiteListData(Data ob) {
        boolean done = false;
        Data data = null;
        if (ob.getSiteId() != 0) {
            data = getPopulateSiteListDataBySiteID(ob.getSiteId());
            if (data == null) {
                done = insertSiteListData(ob);
            } else {
                done = updateSiteListData(ob);
            }
        }
        return done;
    }

    //get check Site List Data
    public Data getPopulateSiteListDataBySiteID(long siteId) {
        String query = "Select * FROM SiteListData WHERE SiteId = '" + siteId + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Data ob = new Data();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateSiteListData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //get check Site List Data
    public Data getSiteDataBySiteName(String seiteName) {
        String query = "Select * FROM SiteListData WHERE SiteName = '" + seiteName + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Data ob = new Data();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateSiteListData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate SiteListData
    private void populateSiteListData(Cursor cursor, Data ob) {
        ob.setSiteId(Long.parseLong(cursor.getString(0)));
        ob.setCustomerSiteId(cursor.getString(1));
        ob.setSiteName(cursor.getString(2));
        ob.setCircleId(Long.parseLong(cursor.getString(3)));
        ob.setCircle(cursor.getString(4));
        ob.setClientId(Long.parseLong(cursor.getString(5)));
        ob.setClient(cursor.getString(6));
        ob.setLat(Double.parseDouble(cursor.getString(7)));
        ob.setLon(Double.parseDouble(cursor.getString(8)));
        ob.setBaseDistance(Double.parseDouble(cursor.getString(9)));
        ob.setBaseLocationId(Long.parseLong(cursor.getString(10)));
        ob.setBaseLocation(cursor.getString(11));
        ob.setBaseLocationLat(Double.parseDouble(cursor.getString(12)));
        ob.setBaseLocationLon(Double.parseDouble(cursor.getString(13)));
    }

    public boolean insertSiteListData(Data ob) {
        ContentValues values = new ContentValues();
        populateSiteListValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("SiteListData", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateSiteListData(Data ob) {
        ContentValues values = new ContentValues();
        populateSiteListValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("SiteListData", values, " SiteId = '" + ob.getSiteId() + "'", null);
        db.close();
        return i > 0;
    }

    public void populateSiteListValueData(ContentValues values, Data ob) {
        values.put("SiteId", String.valueOf(ob.getSiteId()));
        values.put("CustomerSiteId", ob.getCustomerSiteId());
        values.put("SiteName", ob.getSiteName());
        values.put("CircleId", String.valueOf(ob.getCircleId()));
        values.put("Circle", ob.getCircle());
        values.put("ClientId", String.valueOf(ob.getClientId()));
        values.put("Client", ob.getClient());
        values.put("Lat", String.valueOf(ob.getLat()));
        values.put("Lon", String.valueOf(ob.getLon()));
        values.put("BaseDistance", String.valueOf(ob.getBaseDistance()));
        values.put("BaseLocationId", String.valueOf(ob.getBaseLocationId()));
        values.put("BaseLocation", ob.getBaseLocation());
        values.put("BaseLocationLat", String.valueOf(ob.getBaseLocationLat()));
        values.put("BaseLocationLon", String.valueOf(ob.getBaseLocationLon()));
    }

    //get SiteListData
    public List<Data> getAllSiteListData() {
        String query = "Select *  FROM SiteListData";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Data> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Data ob = new Data();
                populateSiteListData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    // --------------upsert Customer Data--------
    public boolean upsertCustomerData(Data ob) {
        boolean done = false;
        Data data = null;
        if (ob.getCt() != 0) {
            data = getCustomerDataByID(ob.getCt());
            if (data == null) {
                done = insertCustomerData(ob);
            } else {
                done = updateCustomerData(ob);
            }
        }
        return done;
    }

    //get and check Customer Data by id
    public Data getCustomerDataByID(int ctid) {
        String query = "Select * FROM CustomerData WHERE ctId = '" + ctid + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Data ob = new Data();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateCustomerData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate Customer Data
    private void populateCustomerData(Cursor cursor, Data ob) {
        ob.setCt(cursor.getInt(0));
        ob.setCtn(cursor.getString(1));
    }

    public boolean insertCustomerData(Data ob) {
        ContentValues values = new ContentValues();
        populateCustomerDataValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("CustomerData", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateCustomerData(Data ob) {
        ContentValues values = new ContentValues();
        populateCustomerDataValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("CustomerData", values, " ctId = '" + ob.getCt() + "'", null);
        db.close();
        return i > 0;
    }

    public void populateCustomerDataValueData(ContentValues values, Data ob) {
        values.put("ctId", ob.getCt());
        values.put("ctName", ob.getCtn());
    }

    //get all Customer Data
    public ArrayList<Data> getAllCustomerData() {
        String query = "Select *  FROM CustomerData ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Data> list = new ArrayList<Data>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Data ob = new Data();
                populateCustomerData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }
    //-------------------- Cluster data-------------------

    //populate Cluster Details Data
    private void populateClusterData(Cursor cursor, ServiceContentData ob) {
        //  Header[] headers = (Header[]) ASTGson.store().getObjectArray(cursor.getString(0));
        Header[] headers = new Gson().fromJson(cursor.getString(0), new TypeToken<Header[]>() {
        }.getType());
        ob.setHeader(headers);

        Data[] data = new Gson().fromJson(cursor.getString(1), new TypeToken<Data[]>() {
        }.getType());
        ob.setData(data);
    }

    public boolean insertClusterData(ServiceContentData ob) {
        ContentValues values = new ContentValues();
        populateClusterDataValue(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("ClusterDetails", null, values);
        db.close();
        return i > 0;
    }

    public void populateClusterDataValue(ContentValues values, ServiceContentData ob) {
        String headerData = ASTGson.store().toJson(ob.getHeader());
        values.put("header", headerData);
        String data = ASTGson.store().toJson(ob.getData());
        values.put("data", data);
    }

    //get Cluster Details
    public List<ServiceContentData> getAllClusterData() {
        String query = "Select *  FROM ClusterDetails ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<ServiceContentData> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                ServiceContentData ob = new ServiceContentData();
                populateClusterData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //------------Site detail--------
    //populate Cluster Details Data
    private void populateSiteData(Cursor cursor, ServiceContentData ob) {
        //  Header[] headers = (Header[]) ASTGson.store().getObjectArray(cursor.getString(0));
        Header[] headers = new Gson().fromJson(cursor.getString(0), new TypeToken<Header[]>() {
        }.getType());
        ob.setHeader(headers);

        Data[] data = new Gson().fromJson(cursor.getString(1), new TypeToken<Data[]>() {
        }.getType());
        ob.setData(data);
    }

    public boolean insertSiteData(ServiceContentData ob) {
        ContentValues values = new ContentValues();
        populateSiteDataValue(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("SiteDetails", null, values);
        db.close();
        return i > 0;
    }

    public void populateSiteDataValue(ContentValues values, ServiceContentData ob) {
        String headerData = ASTGson.store().toJson(ob.getHeader());
        values.put("header", headerData);
        String data = ASTGson.store().toJson(ob.getData());
        values.put("data", data);
    }

    //get Site Details
    public List<ServiceContentData> getAllSiteData() {
        String query = "Select *  FROM SiteDetails ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<ServiceContentData> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                ServiceContentData ob = new ServiceContentData();
                populateSiteData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //------------------ActivityDropdownDetails----------------
    public boolean upsertActivityDropdownData(Data ob) {
        boolean done = false;
        Data data = null;
        if (ob.getTaskId() != 0) {
            data = getActivityDropdownDataByID(ob.getTaskId());
            if (data == null) {
                done = insertActivityDropdownData(ob);
            } else {
                done = updateActivityDropdownData(ob);
            }
        }
        return done;
    }

    //get and check ActivityDropdown Data by id
    public Data getActivityDropdownDataByID(long id) {
        String query = "Select * FROM ActivityDropdownDetails WHERE TaskId = '" + id + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Data ob = new Data();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateActivityDropdownData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate ActivityDropdown Data
    private void populateActivityDropdownData(Cursor cursor, Data ob) {
        ob.setTaskId(cursor.getInt(0));
        ob.setTaskName(cursor.getString(1));
        Activity[] activityData = new Gson().fromJson(cursor.getString(2), new TypeToken<Activity[]>() {
        }.getType());
        ob.setActivity(activityData);
    }

    public boolean insertActivityDropdownData(Data ob) {
        ContentValues values = new ContentValues();
        populateActivityDropdownValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("ActivityDropdownDetails", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateActivityDropdownData(Data ob) {
        ContentValues values = new ContentValues();
        populateActivityDropdownValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("ActivityDropdownDetails", values, " TaskId = '" + ob.getTaskId() + "'", null);
        db.close();
        return i > 0;
    }

    public void populateActivityDropdownValueData(ContentValues values, Data ob) {
        values.put("TaskId", ob.getTaskId());
        values.put("TaskName", ob.getTaskName());
        if (ob.getActivity() != null) {
            String activityStr = ASTGson.store().toJson(ob.getActivity());
            values.put("Activity", activityStr);
        }
    }

    //get all ActivityDropdown Data
    public ArrayList<Data> getAllActivityDropdownData() {
        String query = "Select *  FROM ActivityDropdownDetails ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Data> list = new ArrayList<Data>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Data ob = new Data();
                populateActivityDropdownData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //-----------------------PlanActivity Details----------------------
    public boolean upsertPlanActivityData(Data ob) {
        boolean done = false;
        Data data = null;
        if (ob.getPlanId() != 0) {
            data = getPlanActivityDataByID(ob.getPlanId());
            if (data == null) {
                done = insertPlanActivityData(ob);
            } else {
                done = updatePlanActivityData(ob);
            }
        }
        return done;
    }

    //get and check PlanActivity Data by id
    public Data getPlanActivityDataByID(long id) {
        String query = "Select * FROM PlanActivityDetails WHERE PlanId = '" + id + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Data ob = new Data();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populatePlanActivityData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate ActivityDropdown Data
    private void populatePlanActivityData(Cursor cursor, Data ob) {
        ob.setPlanId(cursor.getInt(0));
        ob.setPlanDate(cursor.getString(1));
        ob.setSiteId(cursor.getInt(2));
        ob.setCustomerSiteId(cursor.getString(3));
        ob.setSiteName(cursor.getString(4));
        ob.setActivityId(cursor.getInt(5));
        ob.setActivityName(cursor.getString(6));
        ob.setMd(cursor.getString(7));
        ob.setRemark(cursor.getString(8));
        ob.setTaskId(cursor.getInt(9));
        ob.setTask(cursor.getString(10));
        ob.setComplaintMessage(cursor.getString(11));
        ob.setCircleId(cursor.getInt(12));
        ob.setCircle(cursor.getString(13));
        ob.setFEId(cursor.getInt(14));
        ob.setFEName(cursor.getString(15));
    }

    public boolean insertPlanActivityData(Data ob) {
        ContentValues values = new ContentValues();
        populatePlanActivityValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("PlanActivityDetails", null, values);
        db.close();
        return i > 0;
    }

    public boolean updatePlanActivityData(Data ob) {
        ContentValues values = new ContentValues();
        populatePlanActivityValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("PlanActivityDetails", values, " PlanId = '" + ob.getPlanId() + "'", null);
        db.close();
        return i > 0;
    }

    public void populatePlanActivityValueData(ContentValues values, Data ob) {
        values.put("PlanId", ob.getPlanId());
        values.put("PlanDate", ob.getPlanDate());
        values.put("SiteId", ob.getSiteId());
        values.put("CustomerSiteId", ob.getCustomerSiteId());
        values.put("SiteName", ob.getSiteName());
        values.put("ActivityId", ob.getActivityId());
        values.put("ActivityName", ob.getActivityName());
        values.put("md", ob.getMd());
        values.put("remark", ob.getRemark());
        values.put("TaskId", ob.getTaskId());
        values.put("Task", ob.getTask());
        values.put("ComplaintMessage", ob.getComplaintMessage());
        values.put("CircleId", ob.getCircleId());
        values.put("Circle", ob.getCircle());
        values.put("FEId", ob.getFEId());
        values.put("FEName", ob.getFEName());
    }

    //get all ActivityDropdown Data
    public ArrayList<Data> getAllPlanActivityData() {
        String query = "Select *  FROM PlanActivityDetails ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Data> list = new ArrayList<Data>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Data ob = new Data();
                populatePlanActivityData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //-----------------------PlanActivityHeader Details----------------------
    //populate PlanActivityHeader Data
    private void populatePlanActivityHeaderData(Cursor cursor, Header ob) {
        ob.setPlanned(cursor.getInt(0));
        ob.setExecuted(cursor.getInt(1));
        ob.setOnTheWay(cursor.getString(2));
        ob.setReachedSite(cursor.getString(3));
        ob.setLeftSite(cursor.getString(4));
        ob.setUnknown(cursor.getString(5));
        ob.setCircle(cursor.getString(6));
        ob.setAttendanceCount(cursor.getInt(7));
        ob.setWorkingCount(cursor.getInt(8));
        ob.setLeaveCount(cursor.getInt(9));
    }

    public boolean insertPlanActivityHeaderData(Header ob) {
        ContentValues values = new ContentValues();
        populatePlanActivityHeaderValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("PlanActivityHeaderDetails", null, values);
        db.close();
        return i > 0;
    }

    public void populatePlanActivityHeaderValueData(ContentValues values, Header ob) {
        values.put("Planned", ob.getPlanned());
        values.put("Executed", ob.getExecuted());
        values.put("OnTheWay", ob.getOnTheWay());
        values.put("ReachedSite", ob.getReachedSite());
        values.put("LeftSite", ob.getLeftSite());
        values.put("Unknown", ob.getUnknown());
        values.put("Circle", ob.getCircle());
        values.put("AttendanceCount", ob.getAttendanceCount());
        values.put("WorkingCount", ob.getWorkingCount());
        values.put("LeaveCount", ob.getLeaveCount());
    }

    //get all PlanActivityHeader Data
    public ArrayList<Header> getAllPlanActivityHeaderData() {
        String query = "Select *  FROM PlanActivityHeaderDetails ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Header> list = new ArrayList<Header>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Header ob = new Header();
                populatePlanActivityHeaderData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //------------------NOCEngineer Details----------------
    public boolean upsertNOCEngineerData(NOCEngineer ob) {
        boolean done = false;
        NOCEngineer data = null;
        if (ob.getNocEngId() != 0) {
            data = getNOCEngineerDataByID(ob.getNocEngId());
            if (data == null) {
                done = insertNOCEngineerData(ob);
            } else {
                done = updateNOCEngineerData(ob);
            }
        }
        return done;
    }

    //get and check NOCEngineer Data by id
    public NOCEngineer getNOCEngineerDataByID(long id) {
        String query = "Select * FROM NOCEngineer WHERE NocEngId = '" + id + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        NOCEngineer ob = new NOCEngineer();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateNOCEngineerData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate NOCEngineer Data
    private void populateNOCEngineerData(Cursor cursor, NOCEngineer ob) {
        ob.setNocEngId(cursor.getInt(0));
        ob.setNocEngName(cursor.getString(1));
        ob.setContactNo(cursor.getString(2));
    }

    public boolean insertNOCEngineerData(NOCEngineer ob) {
        ContentValues values = new ContentValues();
        populateNOCEngineerValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("NOCEngineer", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateNOCEngineerData(NOCEngineer ob) {
        ContentValues values = new ContentValues();
        populateNOCEngineerValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("NOCEngineer", values, " NocEngId = '" + ob.getNocEngId() + "'", null);
        db.close();
        return i > 0;
    }

    public void populateNOCEngineerValueData(ContentValues values, NOCEngineer ob) {
        values.put("NocEngId", ob.getNocEngId());
        values.put("NocEngName", ob.getNocEngName());
        values.put("ContactNo", ob.getContactNo());
    }

    //get all NOCEngineer Data
    public ArrayList<NOCEngineer> getAllNOCEngineerData() {
        String query = "Select *  FROM NOCEngineer ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<NOCEngineer> list = new ArrayList<NOCEngineer>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                NOCEngineer ob = new NOCEngineer();
                populateNOCEngineerData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //------------------FieldEngineer Details----------------
    public boolean upsertFieldEngineerData(FieldEngineer ob) {
        boolean done = false;
        FieldEngineer data = null;
        if (ob.getFieldEngId() != 0) {
            data = getFieldEngineerDataByID(ob.getFieldEngId());
            if (data == null) {
                done = inserFieldEngineerData(ob);
            } else {
                done = updateFieldEngineerData(ob);
            }
        }
        return done;
    }

    //get and check FieldEngineer Data by id
    public FieldEngineer getFieldEngineerDataByID(long id) {
        String query = "Select * FROM FieldEngineer WHERE FieldEngId = '" + id + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        FieldEngineer ob = new FieldEngineer();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateFieldEngineerData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate FieldEngineer Data
    private void populateFieldEngineerData(Cursor cursor, FieldEngineer ob) {
        ob.setFieldEngId(cursor.getInt(0));
        ob.setFieldEngName(cursor.getString(1));
        ob.setContactNo(cursor.getString(2));
    }

    public boolean inserFieldEngineerData(FieldEngineer ob) {
        ContentValues values = new ContentValues();
        populateFieldEngineerValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("FieldEngineer", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateFieldEngineerData(FieldEngineer ob) {
        ContentValues values = new ContentValues();
        populateFieldEngineerValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("FieldEngineer", values, " FieldEngId = '" + ob.getFieldEngId() + "'", null);
        db.close();
        return i > 0;
    }

    public void populateFieldEngineerValueData(ContentValues values, FieldEngineer ob) {
        values.put("FieldEngId", ob.getFieldEngId());
        values.put("FieldEngName", ob.getFieldEngName());
        values.put("ContactNo", ob.getContactNo());
    }

    //get all FieldEngineer Data
    public ArrayList<FieldEngineer> getAllFieldEngineerData() {
        String query = "Select *  FROM FieldEngineer ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<FieldEngineer> list = new ArrayList<FieldEngineer>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                FieldEngineer ob = new FieldEngineer();
                populateFieldEngineerData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //------------------ActivtyFormData Details----------------
    public boolean upsertActivtyFormData(ContentLocalData ob) {
        boolean done = false;
        ContentLocalData data = null;
        if (ob.getPlanId() != null && !ob.getPlanId().equals("") && !ob.getPlanId().equals("0")) {
            data = getActivtyFormDataByID(ob.getPlanId());
            if (data == null) {
                done = inserActivtyFormData(ob);
            } else {
                done = updateActivtyFormData(ob);
            }
        }
        return done;
    }

    //get and check ActivtyFormData Data by id
    public ContentLocalData getActivtyFormDataByID(String id) {
        String query = "Select * FROM ActivtyFormData WHERE planId = '" + id + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ContentLocalData ob = new ContentLocalData();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateActivtyFormData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    //populate ActivtyFormData Data
    private void populateActivtyFormData(Cursor cursor, ContentLocalData ob) {
        ob.setPlanId(cursor.getString(0));
        ob.setActivityFormData(cursor.getString(1));
    }

    public boolean inserActivtyFormData(ContentLocalData ob) {
        ContentValues values = new ContentValues();
        populateActivtyFormValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("ActivtyFormData", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateActivtyFormData(ContentLocalData ob) {
        ContentValues values = new ContentValues();
        populateActivtyFormValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("ActivtyFormData", values, " planId = '" + ob.getPlanId() + "'", null);
        db.close();
        return i > 0;
    }

    public void populateActivtyFormValueData(ContentValues values, ContentLocalData ob) {
        values.put("planId", ob.getPlanId());
        values.put("activtyFormData", ob.getActivityFormData());
    }

    //get all ActivtyFormData Data
    public ArrayList<ContentLocalData> getAllActivtyFormData() {
        String query = "Select *  FROM ActivtyFormData ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<ContentLocalData> list = new ArrayList<ContentLocalData>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                ContentLocalData ob = new ContentLocalData();
                populateActivtyFormData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    public void deleteActivtyFormDataByPlanId(String planId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = " planId = '" + planId + "'";
        db.delete("ActivtyFormData", condition, null);
        db.close();
    }


    //-----------------------ActivitySheetReportHeader Details----------------------
    //populate ActivitySheetReportHeaderDetails Data
    private void populateActivitySheetReportHeaderDetails(Cursor cursor, Header ob) {
        ob.setPlanned(cursor.getInt(0));
        ob.setExecuted(cursor.getInt(1));
        ob.setOnTheWay(cursor.getString(2));
        ob.setReachedSite(cursor.getString(3));
        ob.setLeftSite(cursor.getString(4));
        ob.setUnknown(cursor.getString(5));
        ob.setCircle(cursor.getString(6));
        ob.setAttendanceCount(cursor.getInt(7));
        ob.setWorkingCount(cursor.getInt(8));
        ob.setLeaveCount(cursor.getInt(9));
    }

    public boolean insertActivitySheetReportHeaderDetails(Header ob) {
        ContentValues values = new ContentValues();
        populateActivitySheetReportHeaderDetailsValue(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("ActivitySheetReportHeaderDetails", null, values);
        db.close();
        return i > 0;
    }

    public void populateActivitySheetReportHeaderDetailsValue(ContentValues values, Header ob) {
        values.put("Planned", ob.getPlanned());
        values.put("Executed", ob.getExecuted());
        values.put("OnTheWay", ob.getOnTheWay());
        values.put("ReachedSite", ob.getReachedSite());
        values.put("LeftSite", ob.getLeftSite());
        values.put("Unknown", ob.getUnknown());
        values.put("Circle", ob.getCircle());
        values.put("AttendanceCount", ob.getAttendanceCount());
        values.put("WorkingCount", ob.getWorkingCount());
        values.put("LeaveCount", ob.getLeaveCount());
    }

    //get all ActivitySheetReportHeaderDetails Data
    public ArrayList<Header> getAllActivitySheetReportHeaderDetails() {
        String query = "Select *  FROM ActivitySheetReportHeaderDetails ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Header> list = new ArrayList<Header>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Header ob = new Header();
                populatePlanActivityHeaderData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }
    //populate ActivitySheetReportDetails
    private void populateActivitySheetReportDetails(Cursor cursor, ActivitySheetReportDataModel ob) {
        ob.setSiteName(cursor.getString(0));
        ob.setCustomer(cursor.getString(1));
        ob.setActivityDate(cursor.getString(2));
        ob.setActivityTime(cursor.getString(3));
        ob.setZoneType(cursor.getString(4));
        ob.setTotalAmount(cursor.getString(5));
        ob.setStatus(cursor.getString(6));
        ob.setDays(cursor.getString(7));
        ob.setColor(cursor.getString(8));
        ob.setNocApprovel(cursor.getString(9));
        ob.setActivity(cursor.getString(10));
        ob.setTaDaAmt(cursor.getString(11));
        ob.setBonus(cursor.getString(12));
        ob.setPenalty(cursor.getString(13));
        ob.setReason(cursor.getString(14));
        ob.setCircleId(cursor.getString(15));
        ob.setCircle(cursor.getString(16));
        ob.setFeId(cursor.getString(17));
        ob.setFeName(cursor.getString(18));
    }

    public boolean insertActivitySheetReportDetails(ActivitySheetReportDataModel ob) {
        ContentValues values = new ContentValues();
        populateActivitySheetReportDetailsValue(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("ActivitySheetReportDetails", null, values);
        db.close();
        return i > 0;
    }

    public void populateActivitySheetReportDetailsValue(ContentValues values, ActivitySheetReportDataModel ob) {
        values.put("siteName", ob.getSiteName());
        values.put("Customer", ob.getCustomer());
        values.put("ActivityDate", ob.getActivityDate());
        values.put("ActivityTime", ob.getActivityTime());
        values.put("ZoneType", ob.getZoneType());
        values.put("TotalAmount", ob.getTotalAmount());
        values.put("Status", ob.getStatus());
        values.put("Days", ob.getDays());
        values.put("Color", ob.getColor());
        values.put("NOCApprovel", ob.getNocApprovel());
        values.put("Activity", ob.getActivity());
        values.put("TADA", ob.getTaDaAmt());
        values.put("Bonus", ob.getBonus());
        values.put("Penalty", ob.getPenalty());
        values.put("Reason", ob.getReason());
        values.put("CircleId", ob.getCircleId());
        values.put("Circle", ob.getCircle());
        values.put("FEId", ob.getFeId());
        values.put("FEName", ob.getFeName());
    }

    //get all ActivitySheetReportDetails
    public ArrayList<ActivitySheetReportDataModel> getAllActivitySheetReportDetails() {
        String query = "Select *  FROM ActivitySheetReportDetails ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<ActivitySheetReportDataModel> list = new ArrayList<ActivitySheetReportDataModel>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                ActivitySheetReportDataModel ob = new ActivitySheetReportDataModel();
                populateActivitySheetReportDetails(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }
}
