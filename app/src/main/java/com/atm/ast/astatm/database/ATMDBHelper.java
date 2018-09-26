package com.atm.ast.astatm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.model.ActivitySheetReportDataModel;
import com.atm.ast.astatm.model.CallTrackerDataModel;
import com.atm.ast.astatm.model.ComplaintDataModel;
import com.atm.ast.astatm.model.ComplaintDescriptionDataModel;
import com.atm.ast.astatm.model.EquipListDataModel;
import com.atm.ast.astatm.model.ExpenseScreenDataModel;
import com.atm.ast.astatm.model.FeTrackerEmployeeModel;
import com.atm.ast.astatm.model.FillSiteActivityModel;
import com.atm.ast.astatm.model.LocationTrackingDataModel;
import com.atm.ast.astatm.model.TransitDataModel;
import com.atm.ast.astatm.model.newmodel.Activity;
import com.atm.ast.astatm.model.newmodel.ContentLocalData;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.District;
import com.atm.ast.astatm.model.newmodel.Equipment;
import com.atm.ast.astatm.model.newmodel.EquipmentInfo;
import com.atm.ast.astatm.model.newmodel.EquipmnetContentData;
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

    //--------------------------FE Tracker Data-------------------------------------
    private static final String FE_ID = "fe_id";
    private static final String FE_NAME = "fe_name";
    private static final String FE_CONTACT_NO = "contact_no";
    private static final String FE_COLOR = "color";
    private static final String FE_DISTANCE = "distance";
    private static final String FE_ADDRESS = "address";
    private static final String FE_STATUS = "status";
    private static final String FE_LAST_TRACKED_TIME = "last_tracked_time";
    private static final String FE_CIRCLE = "circle";
    private static final String FE_ACTIVITY = "acivity";

    //--------------------------FE Tracker Transit Data-------------------------------------
    private static final String FE_TRACKER_ID = "fe_id";
    private static final String FE_TRACKER_TRANSIT_TIME = "transit_time";
    private static final String FE_TRACKER_STATUS = "status";
    private static final String FE_TRACKER_COLOR = "color";
    private static final String FE_TRACKER_SITE_ID = "site_id";
    private static final String FE_TRACKER_SITE_NAME = "site_name";
    private static final String FE_TRACKER_CUSTOMER_SITE_ID = "customer_site_id";
    private static final String FE_TRACKER_CIRCLE = "circle";
    private static final String FE_TRACKER_DISTRICT = "district";
    private static final String FE_TRACKER_ACTIVITY_STATUS = "activity_status";
    private static final String FE_TRACKER_DISTANCE = "distance";
    private static final String FE_TRACKER_LAT = "lat";
    private static final String FE_TRACKER_LON = "lon";
    private static final String TABLE_FE_TRACKER = "table_fe_tracker";
    private static final String TABLE_FE_TRACKER_TRANSIT = "table_fe_tracker_transit";


    //--------------------------FE Call Tracking Data-------------------------------------
    private static final String TABLE_FE_CALL_TRACKER = "table_fe_call_tracker";
    private static final String CALL_TRACKING_DURATION = "duration";
    private static final String CALL_TRACKING_DIALLED_NUMBER = "dialled_number";
    private static final String CALL_TRACKING_DIALLED_EMP_ID = "status_emp_id";
    private static final String CALL_TRACKING_CALL_TYPE = "call_type";
    private static final String CALL_TRACKING_DIALER_USER_ID = "dialer_user_id";
    private static final String CALL_TRACKING_CALL_TIME = "call_time";
    private static final String KEY_ID = "id";
    private static final String LAST_UPDATED = "last_updated";

    //----------------------------------Activity Transit Data-------------------------------------
    private static final String TRANSIT_ID = "transit_id";
    private static final String TRANSIT_TYPE = "transit_type";
    private static final String TRANSIT_UID = "transit_uid";
    private static final String TRANSIT_SITE_ID = "transit_site_id";
    private static final String TRANSIT_DATETIME = "transit_datetime";
    private static final String TRANSIT_LONGITUDE = "transit_longitude";
    private static final String TRANSIT_LATITUDE = "transit_latitiude";
    private static final String TRANSIT_CALCULATED_DISTANCE = "transit_calculated_distance";
    private static final String TRANSIT_CALCULATED_AMOUNT = "transit_calculated_amount";
    private static final String TRANSIT_ADDRESS = "transit_address";
    private static final String TRANSIT_ACTUAL_AMOUNT = "transit_actual_amount";
    private static final String TRANSIT_ACTUAL_KMS = "transit_actual_kms";
    private static final String TRANSIT_HOTEL_EXPENSE = "transit_hotel_expense";
    private static final String TRANSIT_ACTUAL_HOTEL_EXPENSE = "transit_actual_hotel_expense";
    private static final String TRANSIT_REMARKS = "transit_remarks";
    private static final String TABLE_TRANSIT = "transit";


    //------------------------------Expense Sheet--------------------------------------
    private static final String EXPENSE_DATE = "expense_date";
    private static final String EXPENSE_SITE_NAME = "expense_site_name";
    private static final String EXPENSE_ATTENDANCE = "expense_attendance";
    private static final String EXPENSE_DA = "expense_da";
    private static final String EXPENSE_TA = "expense_ta";
    private static final String EXPENSE_HOTEL = "expense_hotel";
    private static final String EXPENSE_BONUS = "expense_bonus";
    private static final String EXPENSE_PENALTY = "expense_penalty";
    private static final String EXPENSE_WATER_COST = "expense_water_cost";
    private static final String EXPENSE_OTHER_EXP = "expense_other_exp";
    private static final String EXPENSE_TOTAL = "expense_total";
    private static final String EXPENSE_TOTAL_DA = "expense_total_da";
    private static final String EXPENSE_TOTAL_TA = "expense_total_ta";
    private static final String EXPENSE_TOTAL_HOTEL = "expense_total_hotel";
    private static final String EXPENSE_TOTAL_BONUS = "expense_total_bonus";
    private static final String EXPENSE_TOTAL_PENALTY = "expense_total_penalty";
    private static final String EXPENSE_TOTAL_WATER_COST = "expense_total_water_cost";
    private static final String EXPENSE_TOTAL_OTHER_EXP = "expense_total_other_exp";
    private static final String EXPENSE_TOTAL_TOTAL = "expense_total_total";
    private static final String EXPENSE_ADDITIONAL_BONUS = "expense_additional_bonus";
    private static final String EXPENSE_ADDITIONAL_PENALTY = "expense_additional_penalty";
    private static final String EXPENSE_GRAND_TOTAL = "expense_grand_total";
    private static final String EXPENSE_LAST_UPDATED_DATE = "expense_last_updated_date";
    private static final String EXPENSE_MONTH = "expense_month";
    private static final String TABLE_EXPENSE_SHEET = "expense_sheet";

    //------------------------------Complaint Description--------------------------------------
    private static final String COMPLAINT_DESCRIPTION_TEXT = "complaint_description_text";
    private static final String COMPLAINT_DESCRIPTION_TYPE = "complaint_description_type";
    private static final String COMPLAINT_DESCRIPTION_LAST_UPDATED = "complaint_description_last_updated";
    private static final String TABLE_COMPLAINT_DESCRIPTION = "complaint_description";

    //-----------------------------Complaint-------------------------------------------
    private static final String COMPLAINT_USER_ID = "user_id";
    private static final String COMPLAINT_SITE_ID = "site_id";
    private static final String COMPLAINT_NAME = "name";
    private static final String COMPLAINT_MOBILE_NUMBER = "mobile_number";
    private static final String COMPLAINT_EMAIL_ID = "email_id";
    private static final String COMPLAINT_TYPE = "type";
    private static final String COMPLAINT_PRIORITY = "priority";
    private static final String COMPLAINT_DESCRIPTION = "description";
    private static final String COMPLAINT_PROPOSE_PLAN = "propose_plan";
    private static final String COMPLAINT_TIME = "time";
    private static final String COMPLAINT_CLIENT_NAME = "client_name";
    private static final String TABLE_COMPLAINTS = "table_complaints";

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

        String CREATE_EquipmentList_TABLE = "CREATE TABLE EquipmentList(equip_id INTEGER,equip_name TEXT, updated_time TEXT,parent_id TEXT)";
        db.execSQL(CREATE_EquipmentList_TABLE);


        String CREATE_FE_TRACKER_TABLE = "CREATE TABLE " + TABLE_FE_TRACKER + "("
                + "id INTEGER PRIMARY KEY," + FE_ID + " TEXT, "
                + FE_NAME + " TEXT, " + FE_CONTACT_NO + " TEXT, "
                + FE_COLOR + " TEXT, " + FE_DISTANCE + " TEXT, "
                + FE_ADDRESS + " TEXT, " + FE_STATUS + " TEXT, "
                + FE_CIRCLE + " TEXT, " + FE_ACTIVITY + " TEXT, "
                + FE_LAST_TRACKED_TIME + " TEXT)";
        db.execSQL(CREATE_FE_TRACKER_TABLE);

        String CREATE_FE_TRACKER_TRANSIT_TABLE = "CREATE TABLE " + TABLE_FE_TRACKER_TRANSIT + "("
                + "id INTEGER PRIMARY KEY," + FE_TRACKER_ID + " TEXT,"
                + FE_TRACKER_TRANSIT_TIME + " TEXT," + FE_TRACKER_STATUS + " TEXT,"
                + FE_TRACKER_COLOR + " TEXT," + FE_TRACKER_SITE_ID + " TEXT,"
                + FE_TRACKER_SITE_NAME + " TEXT," + FE_TRACKER_CUSTOMER_SITE_ID + " TEXT,"
                + FE_TRACKER_CIRCLE + " TEXT," + FE_TRACKER_DISTRICT + " TEXT,"
                + FE_TRACKER_ACTIVITY_STATUS + " TEXT," + FE_TRACKER_DISTANCE + " TEXT,"
                + FE_TRACKER_LAT + " TEXT," + FE_TRACKER_LON + " TEXT)";

        db.execSQL(CREATE_FE_TRACKER_TRANSIT_TABLE);

        String CREATE_CALL_TRACKING = "CREATE TABLE " + TABLE_FE_CALL_TRACKER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + CALL_TRACKING_DURATION + " TEXT,"
                + CALL_TRACKING_DIALLED_NUMBER + " TEXT," + CALL_TRACKING_DIALLED_EMP_ID + " INTEGER,"
                + CALL_TRACKING_CALL_TYPE + " TEXT, " + CALL_TRACKING_DIALER_USER_ID + " TEXT,"
                + CALL_TRACKING_CALL_TIME + " TEXT, " + LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_CALL_TRACKING);

        String CREATE_TRANSIT_TABLE = "CREATE TABLE " + TABLE_TRANSIT + "("
                + TRANSIT_ID + " INTEGER PRIMARY KEY autoincrement," + TRANSIT_TYPE + " TEXT,"
                + TRANSIT_UID + " TEXT," + TRANSIT_SITE_ID + " TEXT," + TRANSIT_DATETIME + " TEXT,"
                + TRANSIT_CALCULATED_DISTANCE + " TEXT," + TRANSIT_CALCULATED_AMOUNT + " TEXT," + TRANSIT_ADDRESS + " TEXT,"
                + TRANSIT_ACTUAL_AMOUNT + " TEXT," + TRANSIT_ACTUAL_KMS + " TEXT," + TRANSIT_REMARKS + " TEXT,"
                + TRANSIT_HOTEL_EXPENSE + " TEXT," + TRANSIT_ACTUAL_HOTEL_EXPENSE + " TEXT,"
                + TRANSIT_LATITUDE + " TEXT," + TRANSIT_LONGITUDE + " TEXT)";

        db.execSQL(CREATE_TRANSIT_TABLE);

        String CREATE_EXPENSE_SHEET_TABLE = "CREATE TABLE " + TABLE_EXPENSE_SHEET + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + EXPENSE_DATE + " TEXT,"
                + EXPENSE_SITE_NAME + " TEXT," + EXPENSE_ATTENDANCE + " TEXT," + EXPENSE_DA + " INTEGER,"
                + EXPENSE_TA + " TEXT," + EXPENSE_HOTEL + " TEXT, " + EXPENSE_BONUS + " TEXT,"
                + EXPENSE_PENALTY + " TEXT, " + EXPENSE_WATER_COST + " TEXT,"
                + EXPENSE_OTHER_EXP + " TEXT, " + EXPENSE_TOTAL + " TEXT," + EXPENSE_MONTH + " TEXT,"
                + EXPENSE_TOTAL_DA + " TEXT, " + EXPENSE_TOTAL_TA + " TEXT,"
                + EXPENSE_TOTAL_HOTEL + " TEXT, " + EXPENSE_TOTAL_BONUS + " TEXT,"
                + EXPENSE_TOTAL_PENALTY + " TEXT, " + EXPENSE_TOTAL_WATER_COST + " TEXT,"
                + EXPENSE_TOTAL_OTHER_EXP + " TEXT, " + EXPENSE_TOTAL_TOTAL + " TEXT,"
                + EXPENSE_ADDITIONAL_BONUS + " TEXT, " + EXPENSE_ADDITIONAL_PENALTY + " TEXT,"
                + EXPENSE_GRAND_TOTAL + " TEXT, " + EXPENSE_LAST_UPDATED_DATE + " TEXT)";
        db.execSQL(CREATE_EXPENSE_SHEET_TABLE);

        String CREATE_COMPLAINT_DESCRIPTION_TABLE = "CREATE TABLE " + TABLE_COMPLAINT_DESCRIPTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + COMPLAINT_DESCRIPTION_TEXT + " TEXT,"
                + COMPLAINT_DESCRIPTION_TYPE + " TEXT," + COMPLAINT_DESCRIPTION_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_COMPLAINT_DESCRIPTION_TABLE);

        String CREATE_COMPLAINT_TABLE = "CREATE TABLE " + TABLE_COMPLAINTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + COMPLAINT_USER_ID + " TEXT,"
                + COMPLAINT_SITE_ID + " TEXT," + COMPLAINT_NAME + " TEXT," + COMPLAINT_MOBILE_NUMBER + " TEXT,"
                + COMPLAINT_EMAIL_ID + " TEXT," + COMPLAINT_TYPE + " TEXT," + COMPLAINT_PRIORITY + " TEXT,"
                + COMPLAINT_CLIENT_NAME + " TEXT," + COMPLAINT_DESCRIPTION + " TEXT,"
                + COMPLAINT_PROPOSE_PLAN + " TEXT," + COMPLAINT_TIME + " TEXT)";
        db.execSQL(CREATE_COMPLAINT_TABLE);


        String CREATE_SITEQUIPMENT_TABLE = "CREATE TABLE SiteEquipment(id INTEGER PRIMARY KEY autoincrement,equpimentData TEXT)";
        db.execSQL(CREATE_SITEQUIPMENT_TABLE);

        String CREATE_LocationTracker_TABLE = "CREATE TABLE LocationTracker(id INTEGER PRIMARY KEY autoincrement,user_id TEXT,lat TEXT,lon TEXT,address TEXT,tracked_time TEXT,tracked_distance TEXT)";
        db.execSQL(CREATE_LocationTracker_TABLE);

        String CREATE_AddSiteAddress_TABLE = "CREATE TABLE AddSiteAddress(id INTEGER PRIMARY KEY autoincrement,site_id TEXT,site_customer_id TEXT,site_name TEXT,branch_name TEXT,branch_code TEXT,city TEXT,pincode TEXT,on_of_site TEXT,address TEXT,circle_id TEXT,district_id TEXT,tehsil_id TEXT,start_time TEXT,end_time TEXT,lat TEXT,lon TEXT,last_updated_date TEXT)";
        db.execSQL(CREATE_AddSiteAddress_TABLE);


        String CREATE_Equipment_TABLE = "CREATE TABLE EquipmentInfo(id INTEGER, EquipId TEXT,MakeId TEXT, CapacityId TEXT,SerialNo TEXT,SCMDescId TEXT,SCMCodeId TEXT,QRCode TEXT,remarke TEXT)";
        db.execSQL(CREATE_Equipment_TABLE);


        String CREATE_Accessories_TABLE = "CREATE TABLE AccessoriesInfo(id INTEGER, accId TEXT,accStatus TEXT)";
        db.execSQL(CREATE_Accessories_TABLE);


        String CREATE_DISPATCHEQUIPMENT_TABLE = "CREATE TABLE DispatchEquipment(SiteId TEXT, EquipId TEXT,MakeId TEXT, CapacityId TEXT,SerialNo TEXT,SCMDescId TEXT,SCMCodeId TEXT,QRCode TEXT,remarke TEXT)";
        db.execSQL(CREATE_DISPATCHEQUIPMENT_TABLE);

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FE_TRACKER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FE_TRACKER_TRANSIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FE_CALL_TRACKER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_SHEET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLAINT_DESCRIPTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLAINTS);
        db.execSQL("DROP TABLE IF EXISTS SiteEquipment");
        db.execSQL("DROP TABLE IF EXISTS EquipmentList");
        db.execSQL("DROP TABLE IF EXISTS LocationTracker");
        db.execSQL("DROP TABLE IF EXISTS AddSiteAddress");
        db.execSQL("DROP TABLE IF EXISTS EquipmentInfo");
        db.execSQL("DROP TABLE IF EXISTS AccessoriesInfo");
        db.execSQL("DROP TABLE IF EXISTS DispatchEquipment");
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
        //db.close();
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
        //cursor.close();
        // db.close();
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

    //------------------------------Save FE Tracker Activity Data-------------------------------
    public void addFeTrackerData(ArrayList<FeTrackerEmployeeModel> arrayFeTrackerListData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayFeTrackerListData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(FE_ID, arrayFeTrackerListData.get(i).getUserId());
            values.put(FE_NAME, arrayFeTrackerListData.get(i).getName());
            values.put(FE_CONTACT_NO, arrayFeTrackerListData.get(i).getContactNo());
            values.put(FE_COLOR, arrayFeTrackerListData.get(i).getColor());
            values.put(FE_DISTANCE, arrayFeTrackerListData.get(i).getDistance());
            values.put(FE_ADDRESS, arrayFeTrackerListData.get(i).getShortAddress());
            values.put(FE_STATUS, arrayFeTrackerListData.get(i).getStatus());
            values.put(FE_LAST_TRACKED_TIME, arrayFeTrackerListData.get(i).getLastTrackedTime());
            values.put(FE_ACTIVITY, arrayFeTrackerListData.get(i).getActivity());
            values.put(FE_CIRCLE, arrayFeTrackerListData.get(i).getCircle());

            for (int j = 0; j < arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().size(); j++) {
                ContentValues childValues = new ContentValues();
                childValues.put(FE_TRACKER_ID, arrayFeTrackerListData.get(i).getUserId());
                childValues.put(FE_TRACKER_TRANSIT_TIME, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getTransitTime());
                childValues.put(FE_TRACKER_STATUS, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getStatus());
                childValues.put(FE_TRACKER_COLOR, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getColor());
                childValues.put(FE_TRACKER_SITE_ID, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getSiteId());
                childValues.put(FE_TRACKER_SITE_NAME, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getSiteName());
                childValues.put(FE_TRACKER_CUSTOMER_SITE_ID, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getCustomerSiteId());
                childValues.put(FE_TRACKER_CIRCLE, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getCircle());
                childValues.put(FE_TRACKER_DISTRICT, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getDistrict());
                childValues.put(FE_TRACKER_ACTIVITY_STATUS, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getActivityStatus());
                childValues.put(FE_TRACKER_DISTANCE, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getDistance());
                childValues.put(FE_TRACKER_LAT, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getLat());
                childValues.put(FE_TRACKER_LON, arrayFeTrackerListData.get(i).getArrayListFeTrackerChild().get(j).getLon());

                db.insert(TABLE_FE_TRACKER_TRANSIT, null, childValues);
            }

            // Inserting Row
            db.insert(TABLE_FE_TRACKER, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<FeTrackerEmployeeModel> getFETrackerData(String circles, String activities) {
        ArrayList<FeTrackerEmployeeModel> arrFeTrackerData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_FE_TRACKER + " WHERE " + FE_CIRCLE + " =circles ";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FeTrackerEmployeeModel feTrackerEmployeeModel = new FeTrackerEmployeeModel();
                feTrackerEmployeeModel.setName(cursor.getString(cursor.getColumnIndex(FE_NAME)));
                feTrackerEmployeeModel.setContactNo(cursor.getString(cursor.getColumnIndex(FE_CONTACT_NO)));
                feTrackerEmployeeModel.setColor(cursor.getString(cursor.getColumnIndex(FE_COLOR)));
                feTrackerEmployeeModel.setDistance(cursor.getString(cursor.getColumnIndex(FE_DISTANCE)));
                feTrackerEmployeeModel.setShortAddress(cursor.getString(cursor.getColumnIndex(FE_ADDRESS)));
                feTrackerEmployeeModel.setStatus(cursor.getString(cursor.getColumnIndex(FE_STATUS)));
                feTrackerEmployeeModel.setCircle(cursor.getString(cursor.getColumnIndex(FE_CIRCLE)));
                feTrackerEmployeeModel.setActivity(cursor.getString(cursor.getColumnIndex(FE_ACTIVITY)));
                feTrackerEmployeeModel.setLastTrackedTime(cursor.getString(cursor.getColumnIndex(FE_LAST_TRACKED_TIME)));

                // Adding contact to list
                arrFeTrackerData.add(feTrackerEmployeeModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrFeTrackerData;
    }
    //------------------------------Save FE CALL Tracker Activity Data-------------------------------

    public void addFeCallTrackerData(ArrayList<CallTrackerDataModel> arrayFeCallTrackerListData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayFeCallTrackerListData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(CALL_TRACKING_DURATION, arrayFeCallTrackerListData.get(i).getDuration());
            values.put(CALL_TRACKING_DIALLED_NUMBER, arrayFeCallTrackerListData.get(i).getDialledNumber());
            values.put(CALL_TRACKING_DIALLED_EMP_ID, arrayFeCallTrackerListData.get(i).getDialledEmpId());
            values.put(CALL_TRACKING_CALL_TYPE, arrayFeCallTrackerListData.get(i).getCallType());
            values.put(CALL_TRACKING_DIALER_USER_ID, arrayFeCallTrackerListData.get(i).getDialerUserId());
            values.put(CALL_TRACKING_CALL_TIME, arrayFeCallTrackerListData.get(i).getCallTime());

            // Inserting Row
            db.insert(TABLE_FE_CALL_TRACKER, null, values);
        }
        db.close();
    }

    public ArrayList<CallTrackerDataModel> getFECallTrackerData() {
        ArrayList<CallTrackerDataModel> arrFeCallTrackerData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_FE_CALL_TRACKER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CallTrackerDataModel feCallTrackerDataModel = new CallTrackerDataModel();
                feCallTrackerDataModel.setCallTime(cursor.getString(cursor.getColumnIndex(CALL_TRACKING_CALL_TIME)));
                feCallTrackerDataModel.setCallType(cursor.getString(cursor.getColumnIndex(CALL_TRACKING_CALL_TYPE)));
                feCallTrackerDataModel.setDialerUserId(cursor.getString(cursor.getColumnIndex(CALL_TRACKING_DIALER_USER_ID)));
                feCallTrackerDataModel.setDialledEmpId(cursor.getString(cursor.getColumnIndex(CALL_TRACKING_DIALLED_EMP_ID)));
                feCallTrackerDataModel.setDialledNumber(cursor.getString(cursor.getColumnIndex(CALL_TRACKING_DIALLED_NUMBER)));
                feCallTrackerDataModel.setDuration(cursor.getString(cursor.getColumnIndex(CALL_TRACKING_DURATION)));
                feCallTrackerDataModel.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));

                // Adding contact to list
                arrFeCallTrackerData.add(feCallTrackerDataModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrFeCallTrackerData;
    }

    //------------------------------Save Transit Screen Data-------------------------------

    public boolean upsertTransitData(TransitDataModel ob) {
        boolean done = false;
        TransitDataModel data = null;
        if (!ob.getSiteId().equals("0")) {
            data = getTransitDataBySiteId(ob.getSiteId(), ob.getType());
            if (data == null) {
                done = insertTransitData(ob);
            } else {
                done = updateTransitData(ob);
            }
        }
        return done;
    }

    public TransitDataModel getTransitDataBySiteId(String siteId, String type) {
        String query = "Select * FROM transit WHERE transit_site_id = '" + siteId + "' AND transit_type='" + type + "' ";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TransitDataModel ob = new TransitDataModel();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateTransitData(cursor, ob);

            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }

    public boolean insertTransitData(TransitDataModel ob) {
        ContentValues values = new ContentValues();
        populateTransitValueData(values, ob);

        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("transit", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateTransitData(TransitDataModel ob) {
        ContentValues values = new ContentValues();
        populateTransitValueData(values, ob);

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("transit", values, "transit_site_id = '" + ob.getSiteId() + "' AND transit_type='" + ob.getType() + "'", null);

        db.close();
        return i > 0;
    }

    public void populateTransitValueData(ContentValues values, TransitDataModel ob) {
        values.put(TRANSIT_SITE_ID, ob.getSiteId());
        values.put(TRANSIT_TYPE, ob.getType());
        values.put(TRANSIT_UID, ob.getUserId());
        values.put(TRANSIT_LATITUDE, ob.getLatitude());
        values.put(TRANSIT_LONGITUDE, ob.getLongitude());
        values.put(TRANSIT_DATETIME, ob.getDateTime());
        values.put(TRANSIT_CALCULATED_DISTANCE, ob.getCalcilatedDistance());
        values.put(TRANSIT_CALCULATED_AMOUNT, ob.getCalculatedAmount());
        values.put(TRANSIT_ADDRESS, ob.getAddress());
        values.put(TRANSIT_ACTUAL_AMOUNT, ob.getActualAmt());
        values.put(TRANSIT_ACTUAL_KMS, ob.getActualKms());
        values.put(TRANSIT_ACTUAL_KMS, ob.getActualKms());
        values.put(TRANSIT_HOTEL_EXPENSE, ob.getHotelExpense());
        values.put(TRANSIT_ACTUAL_HOTEL_EXPENSE, ob.getActualHotelExpense());
        values.put(TRANSIT_REMARKS, ob.getRemarks());
    }

    public void addTransitData(ArrayList<TransitDataModel> arrayTransitData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayTransitData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(TRANSIT_SITE_ID, arrayTransitData.get(i).getSiteId());
            values.put(TRANSIT_TYPE, arrayTransitData.get(i).getType());
            values.put(TRANSIT_UID, arrayTransitData.get(i).getUserId());
            values.put(TRANSIT_LATITUDE, arrayTransitData.get(i).getLatitude());
            values.put(TRANSIT_LONGITUDE, arrayTransitData.get(i).getLongitude());
            values.put(TRANSIT_DATETIME, arrayTransitData.get(i).getDateTime());
            values.put(TRANSIT_CALCULATED_DISTANCE, arrayTransitData.get(i).getCalcilatedDistance());
            values.put(TRANSIT_CALCULATED_AMOUNT, arrayTransitData.get(i).getCalculatedAmount());
            values.put(TRANSIT_ADDRESS, arrayTransitData.get(i).getAddress());
            values.put(TRANSIT_ACTUAL_AMOUNT, arrayTransitData.get(i).getActualAmt());
            values.put(TRANSIT_ACTUAL_KMS, arrayTransitData.get(i).getActualKms());
            values.put(TRANSIT_ACTUAL_KMS, arrayTransitData.get(i).getActualKms());
            values.put(TRANSIT_HOTEL_EXPENSE, arrayTransitData.get(i).getHotelExpense());
            values.put(TRANSIT_ACTUAL_HOTEL_EXPENSE, arrayTransitData.get(i).getActualHotelExpense());
            values.put(TRANSIT_REMARKS, arrayTransitData.get(i).getRemarks());

            // Inserting Row
            db.insert(TABLE_TRANSIT, null, values);
        }
        db.close(); // Closing database connection
    }

    private void populateTransitData(Cursor cursor, TransitDataModel transitDataModel) {
        transitDataModel.setId(cursor.getString(cursor.getColumnIndex(TRANSIT_ID)));
        transitDataModel.setSiteId(cursor.getString(cursor.getColumnIndex(TRANSIT_SITE_ID)));
        transitDataModel.setUserId(cursor.getString(cursor.getColumnIndex(TRANSIT_UID)));
        transitDataModel.setType(cursor.getString(cursor.getColumnIndex(TRANSIT_TYPE)));
        transitDataModel.setDateTime(cursor.getString(cursor.getColumnIndex(TRANSIT_DATETIME)));
        transitDataModel.setLatitude(cursor.getString(cursor.getColumnIndex(TRANSIT_LATITUDE)));
        transitDataModel.setLongitude(cursor.getString(cursor.getColumnIndex(TRANSIT_LONGITUDE)));
        transitDataModel.setCalcilatedDistance(cursor.getString(cursor.getColumnIndex(TRANSIT_CALCULATED_DISTANCE)));
        transitDataModel.setCalculatedAmount(cursor.getString(cursor.getColumnIndex(TRANSIT_CALCULATED_AMOUNT)));
        transitDataModel.setAddress(cursor.getString(cursor.getColumnIndex(TRANSIT_ADDRESS)));
        transitDataModel.setActualAmt(cursor.getString(cursor.getColumnIndex(TRANSIT_ACTUAL_AMOUNT)));
        transitDataModel.setActualKms(cursor.getString(cursor.getColumnIndex(TRANSIT_ACTUAL_KMS)));
        transitDataModel.setRemarks(cursor.getString(cursor.getColumnIndex(TRANSIT_REMARKS)));
        transitDataModel.setHotelExpense(cursor.getString(cursor.getColumnIndex(TRANSIT_HOTEL_EXPENSE)));
        transitDataModel.setActualHotelExpense(cursor.getString(cursor.getColumnIndex(TRANSIT_ACTUAL_HOTEL_EXPENSE)));
    }

    public ArrayList<TransitDataModel> getTransitData(String allData) {
        ArrayList<TransitDataModel> arrTransitData = new ArrayList<>();

        String selectQuery = "";

        if (allData.equals("1")) {
            selectQuery = "SELECT * FROM " + TABLE_TRANSIT + " ORDER BY " + TRANSIT_ID;
        } else {
            selectQuery = "SELECT * FROM " + TABLE_TRANSIT + " ORDER BY " + TRANSIT_ID + " DESC LIMIT 1 ";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TransitDataModel transitDataModel = new TransitDataModel();
                populateTransitData(cursor, transitDataModel);
                // Adding contact to list
                arrTransitData.add(transitDataModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrTransitData;
    }

    //------------------------------Select ExpenseSheet-------------------------------

    public void addExpenseSheetData(ArrayList<ExpenseScreenDataModel> arrExpenseSheet) {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.delete("activity_search_details", null, null);

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrExpenseSheet.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(EXPENSE_DATE, arrExpenseSheet.get(i).getDate());
            values.put(EXPENSE_SITE_NAME, arrExpenseSheet.get(i).getSiteName());
            values.put(EXPENSE_ATTENDANCE, arrExpenseSheet.get(i).getAttendance());
            values.put(EXPENSE_DA, arrExpenseSheet.get(i).getDa());
            values.put(EXPENSE_TA, arrExpenseSheet.get(i).getTa());
            values.put(EXPENSE_HOTEL, arrExpenseSheet.get(i).getHotel());
            values.put(EXPENSE_BONUS, arrExpenseSheet.get(i).getBonus());
            values.put(EXPENSE_PENALTY, arrExpenseSheet.get(i).getPenalty());
            values.put(EXPENSE_WATER_COST, arrExpenseSheet.get(i).getWaterCost());
            values.put(EXPENSE_OTHER_EXP, arrExpenseSheet.get(i).getOtherExp());
            values.put(EXPENSE_TOTAL, arrExpenseSheet.get(i).getTotal());
            values.put(EXPENSE_TOTAL_DA, arrExpenseSheet.get(i).getDaTotal());
            values.put(EXPENSE_TOTAL_TA, arrExpenseSheet.get(i).getTaTotal());
            values.put(EXPENSE_TOTAL_HOTEL, arrExpenseSheet.get(i).getHotelTotal());
            values.put(EXPENSE_TOTAL_BONUS, arrExpenseSheet.get(i).getBonusTotal());
            values.put(EXPENSE_TOTAL_PENALTY, arrExpenseSheet.get(i).getPenaltyTotal());
            values.put(EXPENSE_TOTAL_WATER_COST, arrExpenseSheet.get(i).getWaterCostTotal());
            values.put(EXPENSE_TOTAL_OTHER_EXP, arrExpenseSheet.get(i).getOtherExpTotal());
            values.put(EXPENSE_TOTAL_TOTAL, arrExpenseSheet.get(i).getTotalTotal());
            values.put(EXPENSE_ADDITIONAL_BONUS, arrExpenseSheet.get(i).getAdditionalBonus());
            values.put(EXPENSE_ADDITIONAL_PENALTY, arrExpenseSheet.get(i).getAdditionalPenalty());
            values.put(EXPENSE_GRAND_TOTAL, arrExpenseSheet.get(i).getGrandTotal());
            values.put(EXPENSE_MONTH, arrExpenseSheet.get(i).getMonth());
            values.put(EXPENSE_LAST_UPDATED_DATE, String.valueOf(time));

            // Inserting Row
            db.insert(TABLE_EXPENSE_SHEET, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<ExpenseScreenDataModel> getExpenseSheetData(int month) {
        ArrayList<ExpenseScreenDataModel> arrExpenseSheet = new ArrayList<>();

        String selectQuery = "";

        selectQuery = "SELECT * FROM " + TABLE_EXPENSE_SHEET + " WHERE " + EXPENSE_MONTH + " = " + month;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseScreenDataModel expenseScreenDataModel = new ExpenseScreenDataModel();
                expenseScreenDataModel.setDate(cursor.getString(cursor.getColumnIndex(EXPENSE_DATE)));
                expenseScreenDataModel.setSiteName(cursor.getString(cursor.getColumnIndex(EXPENSE_SITE_NAME)));
                expenseScreenDataModel.setAttendance(cursor.getString(cursor.getColumnIndex(EXPENSE_ATTENDANCE)));
                expenseScreenDataModel.setDa(cursor.getString(cursor.getColumnIndex(EXPENSE_DA)));
                expenseScreenDataModel.setTa(cursor.getString(cursor.getColumnIndex(EXPENSE_TA)));
                expenseScreenDataModel.setHotel(cursor.getString(cursor.getColumnIndex(EXPENSE_HOTEL)));
                expenseScreenDataModel.setBonus(cursor.getString(cursor.getColumnIndex(EXPENSE_BONUS)));
                expenseScreenDataModel.setPenalty(cursor.getString(cursor.getColumnIndex(EXPENSE_PENALTY)));
                expenseScreenDataModel.setWaterCost(cursor.getString(cursor.getColumnIndex(EXPENSE_WATER_COST)));
                expenseScreenDataModel.setOtherExp(cursor.getString(cursor.getColumnIndex(EXPENSE_OTHER_EXP)));
                expenseScreenDataModel.setTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_TOTAL)));
                expenseScreenDataModel.setDaTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_TOTAL_DA)));
                expenseScreenDataModel.setTaTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_TOTAL_TA)));
                expenseScreenDataModel.setHotelTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_TOTAL_HOTEL)));
                expenseScreenDataModel.setBonusTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_TOTAL_BONUS)));
                expenseScreenDataModel.setPenaltyTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_TOTAL_PENALTY)));
                expenseScreenDataModel.setWaterCostTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_TOTAL_WATER_COST)));
                expenseScreenDataModel.setOtherExpTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_TOTAL_OTHER_EXP)));
                expenseScreenDataModel.setTotalTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_TOTAL_TOTAL)));
                expenseScreenDataModel.setAdditionalBonus(cursor.getString(cursor.getColumnIndex(EXPENSE_ADDITIONAL_BONUS)));
                expenseScreenDataModel.setAdditionalPenalty(cursor.getString(cursor.getColumnIndex(EXPENSE_ADDITIONAL_PENALTY)));
                expenseScreenDataModel.setGrandTotal(cursor.getString(cursor.getColumnIndex(EXPENSE_GRAND_TOTAL)));
                expenseScreenDataModel.setMonth(cursor.getString(cursor.getColumnIndex(EXPENSE_MONTH)));
                expenseScreenDataModel.setLastUpdatedTime(cursor.getString(cursor.getColumnIndex(EXPENSE_LAST_UPDATED_DATE)));
                // Adding contact to list
                arrExpenseSheet.add(expenseScreenDataModel);
            } while (cursor.moveToNext());
        }

        db.close();
        return arrExpenseSheet;
    }
    //----------------------------Delete Selected Row----------------------------------

    public void deleteSelectedRows(String tableName, String columnName, String columnValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, columnName + "=" + columnValue, null);
        db.close();
    }

    //-----------------------------------Complaint Description-------------------------------------

    public void addComplaintDesription(ArrayList<ComplaintDescriptionDataModel> complaintArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        long time = System.currentTimeMillis();
        for (int i = 0; i < complaintArrayList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(COMPLAINT_DESCRIPTION_TYPE, complaintArrayList.get(i).getType());
            values.put(COMPLAINT_DESCRIPTION_TEXT, complaintArrayList.get(i).getDescription());
            values.put(COMPLAINT_DESCRIPTION_LAST_UPDATED, String.valueOf(time));

            // Inserting Row
            db.insert(TABLE_COMPLAINT_DESCRIPTION, null, values);
        }
        db.close();
    }

    public ArrayList<ComplaintDescriptionDataModel> getComplaintDesription(String descriptionType) {
        ArrayList<ComplaintDescriptionDataModel> arrComplaintData = new ArrayList<>();

        String selectQuery = "";

        selectQuery = "SELECT * FROM " + TABLE_COMPLAINT_DESCRIPTION + " WHERE " + COMPLAINT_DESCRIPTION_TYPE + " = '" + descriptionType + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ComplaintDescriptionDataModel complaintDataModel = new ComplaintDescriptionDataModel();
                complaintDataModel.setDescription(cursor.getString(cursor.getColumnIndex(COMPLAINT_DESCRIPTION_TEXT)));
                complaintDataModel.setType(cursor.getString(cursor.getColumnIndex(COMPLAINT_DESCRIPTION_TYPE)));
                complaintDataModel.setLastUpdatedTime(cursor.getString(cursor.getColumnIndex(COMPLAINT_DESCRIPTION_LAST_UPDATED)));

                // Adding contact to list
                arrComplaintData.add(complaintDataModel);
            } while (cursor.moveToNext());
        }

        db.close();
        return arrComplaintData;
    }

    public void addComplaintData(ComplaintDataModel complaint) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COMPLAINT_USER_ID, complaint.getUserId());
        values.put(COMPLAINT_SITE_ID, complaint.getSiteID());
        values.put(COMPLAINT_NAME, complaint.getName());
        values.put(COMPLAINT_MOBILE_NUMBER, complaint.getMobile());
        values.put(COMPLAINT_EMAIL_ID, complaint.getEmailId());
        values.put(COMPLAINT_TYPE, complaint.getType());
        values.put(COMPLAINT_PRIORITY, complaint.getPriority());
        values.put(COMPLAINT_DESCRIPTION, complaint.getDescription());
        values.put(COMPLAINT_PROPOSE_PLAN, complaint.getProposePlan());
        values.put(COMPLAINT_CLIENT_NAME, complaint.getClientName());
        values.put(COMPLAINT_TIME, complaint.getTime());

        db.insert(TABLE_COMPLAINTS, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<ComplaintDataModel> getComplaintData() {
        ArrayList<ComplaintDataModel> arrComplaintData = new ArrayList<>();

        String selectQuery = "";

        selectQuery = "SELECT * FROM " + TABLE_COMPLAINTS + " LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ComplaintDataModel complaintDataModel = new ComplaintDataModel();
                complaintDataModel.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                complaintDataModel.setUserId(cursor.getString(cursor.getColumnIndex(COMPLAINT_USER_ID)));
                complaintDataModel.setSiteID(cursor.getString(cursor.getColumnIndex(COMPLAINT_SITE_ID)));
                complaintDataModel.setName(cursor.getString(cursor.getColumnIndex(COMPLAINT_NAME)));
                complaintDataModel.setMobile(cursor.getString(cursor.getColumnIndex(COMPLAINT_MOBILE_NUMBER)));
                complaintDataModel.setEmailId(cursor.getString(cursor.getColumnIndex(COMPLAINT_EMAIL_ID)));
                complaintDataModel.setType(cursor.getString(cursor.getColumnIndex(COMPLAINT_TYPE)));
                complaintDataModel.setPriority(cursor.getString(cursor.getColumnIndex(COMPLAINT_PRIORITY)));
                complaintDataModel.setDescription(cursor.getString(cursor.getColumnIndex(COMPLAINT_DESCRIPTION)));
                complaintDataModel.setProposePlan(cursor.getString(cursor.getColumnIndex(COMPLAINT_PROPOSE_PLAN)));
                complaintDataModel.setTime(cursor.getString(cursor.getColumnIndex(COMPLAINT_TIME)));
                complaintDataModel.setClientName(cursor.getString(cursor.getColumnIndex(COMPLAINT_CLIENT_NAME)));
                arrComplaintData.add(complaintDataModel);
            } while (cursor.moveToNext());
        }

        db.close();
        return arrComplaintData;
    }

    public void deleteComplaintData(int complaintId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPLAINTS, KEY_ID + "=" + complaintId,
                null);
        db.close();
    }

    /**
     * @param cursor
     * @param ob     populate Site Equipment List and Accessories  Data
     */


    private void populateSiteEquipmentData(Cursor cursor, Data ob) {
        ob.setId(cursor.getInt(0));
        EquipmnetContentData equipmentData = new Gson().fromJson(cursor.getString(1), new TypeToken<EquipmnetContentData>() {
        }.getType());
        ob.setEquipmnetContentData(equipmentData);

    }

    public boolean insertSiteEquipmentData(Data ob) {
        ContentValues values = new ContentValues();
        populateSiteEquipmentValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("SiteEquipment", null, values);
        db.close();
        return i > 0;
    }

    public void populateSiteEquipmentValueData(ContentValues values, Data ob) {
        String EquipmentListStr = ASTGson.store().toJson(ob.getEquipmnetContentData());
        values.put("equpimentData", EquipmentListStr);
    }

    public ArrayList<Data> getAllEquipmentListData() {
        String query = "Select *  FROM SiteEquipment ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Data> list = new ArrayList<Data>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Data ob = new Data();
                populateSiteEquipmentData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //----------EquipmentList---------
    private void populateEquipmentList(Cursor cursor, EquipListDataModel ob) {
        ob.setEquipId(String.valueOf(cursor.getInt(0)));
        ob.setEquipName(cursor.getString(1));
        ob.setEquipTime(cursor.getString(2));
        ob.setEquipParentId(cursor.getString(3));

    }

    public boolean insertEquipmentList(EquipListDataModel ob) {
        ContentValues values = new ContentValues();
        populateEquipmentListValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("EquipmentList", null, values);
        db.close();
        return i > 0;
    }

    public void populateEquipmentListValueData(ContentValues values, EquipListDataModel ob) {
        long time = System.currentTimeMillis();
        values.put("equip_id", ob.getEquipId());
        values.put("equip_name", ob.getEquipName());
        values.put("updated_time", String.valueOf(time));
        values.put("parent_id", ob.getEquipParentId());
    }

    public ArrayList<EquipListDataModel> getAllEquipmentData() {
        String query = "Select *  FROM EquipmentList ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<EquipListDataModel> list = new ArrayList<EquipListDataModel>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                EquipListDataModel ob = new EquipListDataModel();
                populateEquipmentList(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //----------LocationTracker---------
    private void populateLocationTracker(Cursor cursor, LocationTrackingDataModel ob) {
        ob.setId(String.valueOf(cursor.getInt(0)));
        ob.setUserId(cursor.getString(1));
        ob.setLat(cursor.getString(2));
        ob.setLon(cursor.getString(3));
        ob.setAddress(cursor.getString(4));
        ob.setTime(cursor.getString(5));
        ob.setDistance(cursor.getString(6));

    }

    public boolean insertLocationTracker(LocationTrackingDataModel ob) {
        ContentValues values = new ContentValues();
        populateLocationTrackerValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("LocationTracker", null, values);
        db.close();
        return i > 0;
    }

    public void populateLocationTrackerValueData(ContentValues values, LocationTrackingDataModel ob) {
        values.put("user_id", ob.getUserId());
        values.put("lat", ob.getLat());
        values.put("lon", ob.getLon());
        values.put("address", ob.getAddress());
        values.put("tracked_time", ob.getTime());
        values.put("tracked_distance", ob.getDistance());
    }

    public ArrayList<LocationTrackingDataModel> getAllLocationTrackerData() {
        String query = "Select *  FROM LocationTracker ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<LocationTrackingDataModel> list = new ArrayList<LocationTrackingDataModel>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                LocationTrackingDataModel ob = new LocationTrackingDataModel();
                populateLocationTracker(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //----------AddSiteAddress---------
    private void populateAddSiteAddress(Cursor cursor, FillSiteActivityModel ob) {
        //ob.setId(String.valueOf(cursor.getInt(0)));
        ob.setSiteId(cursor.getString(1));
        ob.setCustomerSiteId(cursor.getString(2));
        ob.setSiteName(cursor.getString(3));
        ob.setBranchName(cursor.getString(4));
        ob.setBranchCode(cursor.getString(5));
        ob.setCity(cursor.getString(6));
        ob.setPincode(cursor.getString(7));
        ob.setOnOffSite(cursor.getString(8));
        ob.setAddress(cursor.getString(9));
        ob.setCircleId(cursor.getString(10));
        ob.setDistrictId(cursor.getString(11));
        ob.setTehsilId(cursor.getString(12));
        ob.setFunctionalFromTime(cursor.getString(13));
        ob.setFunctionalToTime(cursor.getString(14));
        ob.setLat(cursor.getString(15));
        ob.setLon(cursor.getString(16));

    }

    public boolean insertAddSiteAddress(FillSiteActivityModel ob) {
        ContentValues values = new ContentValues();
        populateAddSiteAddressValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("AddSiteAddress", null, values);
        db.close();
        return i > 0;
    }

    public void populateAddSiteAddressValueData(ContentValues values, FillSiteActivityModel ob) {
        values.put("site_id", ob.getSiteId());
        values.put("site_customer_id", ob.getCustomerSiteId());
        values.put("site_name", ob.getSiteName());
        values.put("branch_name", ob.getBranchName());
        values.put("branch_code", ob.getBranchCode());
        values.put("city", ob.getCity());
        values.put("pincode", ob.getPincode());
        values.put("on_of_site", ob.getOnOffSite());
        values.put("address", ob.getAddress());
        values.put("circle_id", ob.getCircleId());
        values.put("district_id", ob.getDistrictId());
        values.put("tehsil_id", ob.getTehsilId());
        values.put("start_time", ob.getFunctionalFromTime());
        values.put("end_time", ob.getFunctionalToTime());
        values.put("lat", ob.getLat());
        values.put("lon", ob.getLon());
        values.put("last_updated_date", "");
    }

    public ArrayList<FillSiteActivityModel> getAllAddSiteAddress() {
        String query = "Select *  FROM AddSiteAddress ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<FillSiteActivityModel> list = new ArrayList<FillSiteActivityModel>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                FillSiteActivityModel ob = new FillSiteActivityModel();
                populateAddSiteAddress(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    // Equipment Info Data
    public boolean upsertEquipmentInfoData(EquipmentInfo ob) {
        boolean done = false;
        EquipmentInfo data = null;
        if (ob.getEquipId() != null) {
            data = getEquipmentInfoDataByID(ob.getId(), ob.getEquipId());
            if (data == null) {
                done = insertEquipmentInfoData(ob);
            } else {
                done = updateEquipmentInfoData(ob);
            }
        }
        return done;
    }

    public EquipmentInfo getEquipmentInfoDataByID(int id, String eqtid) {
        String query = "Select * FROM EquipmentInfo WHERE EquipId  = '" + eqtid + "' AND  id  = '" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        EquipmentInfo ob = new EquipmentInfo();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateEquipmentInfoData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }


    private void populateEquipmentInfoData(Cursor cursor, EquipmentInfo ob) {
        ob.setId(cursor.getInt(0));
        ob.setEquipId(cursor.getString(1));
        ob.setMakeId(cursor.getString(2));
        ob.setCapacityId(cursor.getString(3));
        ob.setSerialNo(cursor.getString(4));
        ob.setSCMDescId(cursor.getString(5));
        ob.setSCMCodeId(cursor.getString(6));
        ob.setQRCode(cursor.getString(7));
        ob.setRemarke(cursor.getString(8));
    }


    public boolean insertEquipmentInfoData(EquipmentInfo ob) {
        ContentValues values = new ContentValues();
        populateEquipmentInfoValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("EquipmentInfo", null, values);
        db.close();
        return i > 0;
    }


    public void populateEquipmentInfoValueData(ContentValues values, EquipmentInfo ob) {
        values.put("(id ", ob.getId());
        values.put("EquipId", ob.getEquipId());
        values.put("MakeId", ob.getMakeId());
        values.put("CapacityId", ob.getCapacityId());
        values.put("SerialNo", ob.getSerialNo());
        values.put("SCMDescId", ob.getSCMDescId());
        values.put("SCMCodeId", ob.getSCMCodeId());
        values.put("QRCode", ob.getQRCode());
        values.put("remarke", ob.getRemarke());
    }


    public boolean updateEquipmentInfoData(EquipmentInfo ob) {
        ContentValues values = new ContentValues();
        populateEquipmentInfoValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("EquipmentInfo", values, " id = '" + ob.getId() + "' AND EquipId  = '" + ob.getEquipId() + "'", null);
        db.close();
        return i > 0;
    }

    public ArrayList<EquipmentInfo> getEquipmentInfoData() {
        String query = "Select *  FROM EquipmentInfo";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<EquipmentInfo> list = new ArrayList<EquipmentInfo>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                EquipmentInfo ob = new EquipmentInfo();
                populateEquipmentInfoData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    // save  All Accessories_

    public boolean upsertAccessoriesInfoData(EquipmentInfo ob) {
        boolean done = false;
        EquipmentInfo data = null;
        if (ob.getAccId() != null) {
            data = getAccessoriesInfoDataByID(ob.getId());
            if (data == null) {
                done = insertAccessoriesInfoData(ob);
            } else {
                done = updateAccessoriesInfoData(ob);
            }
        }
        return done;
    }

    public EquipmentInfo getAccessoriesInfoDataByID(int id) {
        String query = "Select * FROM AccessoriesInfo WHERE id  = '" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        EquipmentInfo ob = new EquipmentInfo();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateEquipmentInfoData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }


    private void populateAccessoriesInfoData(Cursor cursor, EquipmentInfo ob) {
        ob.setId(cursor.getInt(0));
        ob.setAccId(cursor.getString(1));
        ob.setAccStatus(cursor.getString(2));
    }


    public boolean insertAccessoriesInfoData(EquipmentInfo ob) {
        ContentValues values = new ContentValues();
        populateAccessoriesInfoValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("AccessoriesInfo", null, values);
        db.close();
        return i > 0;
    }


    public void populateAccessoriesInfoValueData(ContentValues values, EquipmentInfo ob) {
        values.put("(id ", ob.getId());
        values.put("accId", ob.getAccId());
        values.put("accStatus", ob.getAccStatus());
    }


    public boolean updateAccessoriesInfoData(EquipmentInfo ob) {
        ContentValues values = new ContentValues();
        populateEquipmentInfoValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("AccessoriesInfo", values, " id = '" + ob.getId() + "'", null);
        db.close();
        return i > 0;
    }

    public ArrayList<EquipmentInfo> geAccessoriesInfoData() {
        String query = "Select *  FROM AccessoriesInfo ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<EquipmentInfo> list = new ArrayList<EquipmentInfo>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                EquipmentInfo ob = new EquipmentInfo();
                populateEquipmentInfoData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    /**
     *   Dispatch Equipment Data  All DB Action
     * @param ob
     * @return
     */


    public boolean upsertDispatchEquipmentData(EquipmentInfo ob) {
        boolean done = false;
        EquipmentInfo data = null;
        if (ob.getEquipId() != null) {
            data = getDispatchEquipmentDataByID(ob.getSiteId(), ob.getEquipId());
            if (data == null) {
                done = insertDispatchEquipmentData(ob);
            } else {
                done = updateDispatchEquipmentData(ob);
            }
        }
        return done;
    }


    public EquipmentInfo getDispatchEquipmentDataByID(String SiteId, String eqtid) {
        String query = "Select * FROM DispatchEquipment WHERE SiteId  = '" + SiteId + "' AND  id  = '" + eqtid + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        EquipmentInfo ob = new EquipmentInfo();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateDispatchEquipmentData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }


    private void populateDispatchEquipmentData(Cursor cursor, EquipmentInfo ob) {
        ob.setSiteId(cursor.getString(0));
        ob.setEquipId(cursor.getString(1));
        ob.setMakeId(cursor.getString(2));
        ob.setCapacityId(cursor.getString(3));
        ob.setSerialNo(cursor.getString(4));
        ob.setSCMDescId(cursor.getString(5));
        ob.setSCMCodeId(cursor.getString(6));
        ob.setQRCode(cursor.getString(7));
        ob.setRemarke(cursor.getString(8));
    }


    public boolean insertDispatchEquipmentData(EquipmentInfo ob) {
        ContentValues values = new ContentValues();
        populateDispatchEquipmentValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("DispatchEquipment", null, values);
        db.close();
        return i > 0;
    }


    public void populateDispatchEquipmentValueData(ContentValues values, EquipmentInfo ob) {
        values.put("(SiteId ", ob.getId());
        values.put("EquipId", ob.getEquipId());
        values.put("MakeId", ob.getMakeId());
        values.put("CapacityId", ob.getCapacityId());
        values.put("SerialNo", ob.getSerialNo());
        values.put("SCMDescId", ob.getSCMDescId());
        values.put("SCMCodeId", ob.getSCMCodeId());
        values.put("QRCode", ob.getQRCode());
        values.put("remarke", ob.getRemarke());
    }


    public boolean updateDispatchEquipmentData(EquipmentInfo ob) {
        ContentValues values = new ContentValues();
        populateEquipmentInfoValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("DispatchEquipment", values, " SiteId = '" + ob.getSiteId() + "' AND EquipId  = '" + ob.getEquipId() + "'", null);
        db.close();
        return i > 0;
    }
    public ArrayList<EquipmentInfo> getDispatchEquipmentData(String SiteId) {
        String query = "Select *  FROM DispatchEquipment  WHERE SiteId = '" + SiteId + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<EquipmentInfo> list = new ArrayList<EquipmentInfo>();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                EquipmentInfo ob = new EquipmentInfo();
                populateDispatchEquipmentData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }
}
