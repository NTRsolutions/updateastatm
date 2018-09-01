package com.atm.ast.astatm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atm.ast.astatm.model.ClusterDisplayDataModel;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.ActivityFormDataModel;
import com.atm.ast.astatm.model.ActivityListSheetDataModel;
import com.atm.ast.astatm.model.CallTrackerDataModel;
import com.atm.ast.astatm.model.CircleDisplayDataModel;
import com.atm.ast.astatm.model.ComplaintDataModel;
import com.atm.ast.astatm.model.ComplaintDescriptionDataModel;
import com.atm.ast.astatm.model.CustomerListDataModel;
import com.atm.ast.astatm.model.DisplayExpenseDataModel;
import com.atm.ast.astatm.model.DistrictModel;
import com.atm.ast.astatm.model.EquipListDataModel;
import com.atm.ast.astatm.model.ExecutedActivityListModel;
import com.atm.ast.astatm.model.ExpenseScreenDataModel;
import com.atm.ast.astatm.model.FeTrackerEmployeeModel;
import com.atm.ast.astatm.model.FillSiteActivityModel;
import com.atm.ast.astatm.model.LocationTrackingDataModel;
import com.atm.ast.astatm.model.NocEnggListDataModel;
import com.atm.ast.astatm.model.PlannedActivityListModel;
import com.atm.ast.astatm.model.ReasonListDataModel;
import com.atm.ast.astatm.model.SiteDetailsDisplayModel;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.model.StateModel;
import com.atm.ast.astatm.model.TaskListDataModel;
import com.atm.ast.astatm.model.TehsilModel;
import com.atm.ast.astatm.model.TransitDataModel;
import com.atm.ast.astatm.model.ZoneDisplayDataModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AST on 10/14/2015.
 */
public class AtmDatabase extends SQLiteOpenHelper {
    //http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 72;

    // Database Name
    private static final String DATABASE_NAME = "AST_ATM";

    // table name
    private static final String TABLE_COMPLAINT_DESCRIPTION = "complaint_description";
    private static final String TABLE_CIRCLE = "circle";
    private static final String TABLE_ZONE = "zone";
    private static final String TABLE_CLUSTER = "cluster";
    private static final String TABLE_SITE = "site";
    private static final String TABLE_SITE_DETAILS = "site_details";
    private static final String TABLE_SITE_SEARCH = "site_search_details";
    private static final String TABLE_ACTIVITY_SEARCH = "activity_search_details";
    private static final String TABLE_NOC_ENGG = "noc_engg_details";
    private static final String TABLE_ACTIVITY_FORM = "activty_form_data";
    private static final String TABLE_REASON = "activty_reason";
    private static final String TABLE_TASK = "activty_task";
    private static final String TABLE_TRANSIT = "transit";
    private static final String TABLE_CUSTOMER = "customer_data";
    private static final String TABLE_PLANNED_ACTIVITY = "planned_activity";
    private static final String TABLE_EXECUTED_ACTIVITY = "executed_activity";
    private static final String TABLE_EXPENSE_DISPLAY = "expense_display";
    private static final String TABLE_STATE = "state";
    private static final String TABLE_DISTRICT = "district";
    private static final String TABLE_TEHSIL = "tehsil";
    private static final String TABLE_FILL_SITE_DATA = "fill_site_data";
    private static final String TABLE_FE_TRACKER = "table_fe_tracker";
    private static final String TABLE_FE_TRACKER_TRANSIT = "table_fe_tracker_transit";
    private static final String TABLE_FE_CALL_TRACKER = "table_fe_call_tracker";
    private static final String TABLE_LOCATION_TRACKER = "table_location_tracker";
    private static final String TABLE_COMPLAINTS = "table_complaints";
    private static final String TABLE_EXPENSE_SHEET = "expense_sheet";
    private static final String TABLE_EQUIP_LIST = "equipment_list";

    private static final String KEY_ID = "id";

    // Circle Table Columns
    //private static final String KEY_ID = "id";
    private static final String CIRCLE_NAME = "circle_name";
    private static final String CIRCLE_ID = "circle_id";
    private static final String CIRCLE_TOTAL_SITES = "total_sites";
    private static final String CIRCLE_TOTAL_ALARM_SITES = "total_alarm_sites";
    private static final String CIRCLE_COLOR_CODE = "color_code";
    private static final String CIRCLE_HEAD_CONTACT = "head_contact";
    private static final String LAST_UPDATED = "last_updated";
    private static final String CIRCLE_HEADER_TOTAL_SITES = "header_total_sites";
    private static final String CIRCLE_HEADER_ALARM_SITES = "header_alarm_sites";
    private static final String CIRCLE_HEADER_NON_COMM = "header_non_comm";
    private static final String CIRCLE_HEADER_INV_ALARM = "header_inv_alarm";
    private static final String CIRCLE_HEADER_LOW_BATTERY = "header_low_battery";
    private static final String CIRCLE_HEADER_NMS_SITES = "header_nms_sites";

    // Zone Table Columns
    //private static final String KEY_ID = "id";
    private static final String ZONE_NAME = "zone_name";
    private static final String ZONE_ID = "zone_id";
    private static final String ZONE_TOTAL_SITES = "zone_sites";
    private static final String ZONE_TOTAL_ALARM_SITES = "zone_alarm_sites";
    private static final String ZONE_COLOR_CODE = "color_code";
    private static final String ZONE_HEAD_CONTACT = "head_contact";
    private static final String ZONE_LAST_UPDATED = "last_updated";
    private static final String ZONE_CIRCLE_ID = "circle_id";

    // Cluster Table Columns
    private static final String CLUSTER_NAME = "zone_name";
    private static final String CLUSTER_ID = "zone_id";
    private static final String CLUSTER_TOTAL_SITES = "zone_sites";
    private static final String CLUSTER_TOTAL_ALARM_SITES = "zone_alarm_sites";
    private static final String CLUSTER_COLOR_CODE = "color_code";
    private static final String CLUSTER_HEAD_CONTACT = "head_contact";
    private static final String CLUSTER_LAST_UPDATED = "last_updated";
    private static final String CLUSTER_ZONE_ID = "circle_id";
    private static final String CLUSTER_HEADER_TOTAL_SITES = "total_sites";
    private static final String CLUSTER_HEADER_ALARM_SITES = "total_alarm_sites";
    private static final String CLUSTER_HEADER_NON_COM = "total_non_com";
    private static final String CLUSTER_INV_ALARM = "total_inv_alarm";
    private static final String CLUSTER_LOW_BATTERY = "total_low_battery";
    private static final String CLUSTER_NMS_SITES = "total_nms_sites";

    // Site Table Columns
    private static final String SITE_NAME = "site_name";
    private static final String SITE_ID = "site_id";
    private static final String SITE_NAME_HINDI = "site_name_hindi";
    private static final String SITE_CLIENT_NAME = "site_client_name";
    private static final String SITE_STATUS = "site_status";
    private static final String SITE_VOLTAGE = "site_voltage";
    private static final String SITE_COLOR_CODE = "site_color_code";
    private static final String SITE_HEAD_CONTACT = "site_head_contact";
    private static final String SITE_LAST_UPDATE = "last_updated";
    private static final String SITE_NUM_ID = "site_num_id";
    private static final String SITE_CLUSTER_ID = "cluster_id";
    private static final String SITE_HEADER_TOTAL_SITES = "header_total_sites";
    private static final String SITE_HEADER_ALARM_SITES = "header_alarm_sites";
    private static final String SITE_HEADER_NON_COM_SITES = "header_non_com_sites";
    private static final String SITE_HEADER_INV_SITES = "header_inv_sites";
    private static final String SITE_HEADER_LOW_BATTERY_SITES = "header_low_battery_sites";
    private static final String SITE_HEADER_NSM_SITES = "header_nsm_sites";

    // Site Details Table Columns
    private static final String SITE_DETAIL_NAME = "site_detail_name";
    private static final String SITE_DETAIL_ID = "site_detail_id";
    private static final String SITE_DETAIL_NUM_ID = "site_detail_num_id";
    private static final String SITE_DETAIL_COLOR_CODE = "site_detail_color_code";
    private static final String SITE_DETAIL_BATTERY_VOLTAGE = "site_detail_battery_volt";
    private static final String SITE_DETAIL_DATE = "site_detail_date";
    private static final String SITE_DETAIL_TIME = "site_detail_time";
    private static final String SITE_DETAIL_STATUS = "site_detail_status";
    private static final String SITE_DETAIL_BCI = "site_detail_bci";
    private static final String SITE_DETAIL_BDI = "site_detail_bdi";
    private static final String SITE_DETAIL_IN_OUT_VOLT = "site_detail_in_out_volt";
    private static final String SITE_DETAIL_LOAD_CURRENT = "site_detail_load_current";
    private static final String SITE_DETAIL_SOLAR_CURRENT = "site_detail_solar_current";
    private static final String SITE_DETAIL_ALARM_CODE = "site_detail_alarm_code";
    private static final String SITE_DETAIL_LAST_UPDATED = "site_detail_last_updated";
    private static final String SITE_DETAIL_SITE_ID = "site_detail_site_id";

    //---------------------------Site Search Data------------------------------------------
    private static final String SITE_SEARCH_ID = "site_search_id";
    private static final String SITE_SEARCH_NAME = "site_search_name";
    private static final String SITE_SEARCH_CUSTOMER_ID = "site_search_customer_id";
    private static final String SITE_SEARCH_LAST_UPDATED = "site_search_last_updated";
    private static final String SITE_SEARCH_CIRCLE_ID = "site_circle_id";
    private static final String SITE_SEARCH_CIRCLE_NAME = "site_circle_name";
    private static final String SITE_SEARCH_CLIENT_ID = "site_client_id";
    private static final String SITE_SEARCH_CLIENT_NAME = "site_client_name";
    private static final String SITE_SEARCH_TYPE = "site_type";
    private static final String SITE_LAT = "site_lat";
    private static final String SITE_LON = "site_long";
    private static final String SITE_DISTANCE_FROM_BASE = "base_distance";


    //--------------------------Site Activity Search Data-------------------------------------
    private static final String ACTIVITY_ID = "activity_id";
    private static final String ACTIVITY_NAME = "activity_name";
    private static final String ACTIVITY_LAST_UPDATED_DATE = "last_updated_date";
    private static final String ACTIVITY_TASK_LIST = "activity_task_id";

    //--------------------------Site Activity Search Data-------------------------------------
    private static final String NOC_ENGG_ID = "noc_engg_id";
    private static final String NOC_ENGG_NAME = "noc_engg_name";
    private static final String NOC_ENGG_CONTACT = "noc_engg_contact";
    private static final String NOC_ENGG_TYPE = "noc_engg_type";
    private static final String NOC_ENGG_LAST_UPDATED_DATE = "last_updated_date";



    //--------------------------Site Reason Data-------------------------------------
    private static final String REASON_ID = "reason_id";
    private static final String REASON_NAME = "reason_name";
    private static final String REASON_ACTIVITY_ID = "activity_id";
    private static final String REASON_LAST_UPDATED_DATE = "last_updated_date";


    //------------------------------Activity Form Data--------------------------------------
    private static final String FORM_ID = "form_id";
    private static final String FORM_USER_ID = "form_user_id";
    private static final String FORM_SITE_ID = "form_site_id";
    private static final String FORM_ACTIVITY_ID = "form_activty_id";
    private static final String FORM_NOC_ENGG_ID = "form_noc_engg_id";
    private static final String FORM_STATUS = "form_status";
    private static final String FORM_REASON = "form_reason";
    private static final String FORM_ZONE = "form_zone";
    private static final String FORM_DATE = "form_date";
    private static final String FORM_TASK_ID = "form_task_id";
    private static final String FORM_MATERIAL_STATUS = "form_material_status";
    private static final String FORM_REMARKS = "form_remarks";
    private static final String FORM_NOC_ENGG_CONTACT = "form_noc_engg_contact";
    private static final String FORM_EARTH_VOLT = "form_earth_volt";
    private static final String FORM_BATT_VOLT = "form_batt_volt";
    private static final String FORM_BATT_CELLS = "form_batt_cells";
    private static final String FORM_CHARGER = "form_charger";
    private static final String FORM_INVERTER = "form_inverter";
    private static final String FORM_EB_CONN = "form_eb_conn";
    private static final String FORM_CONN = "form_conn";
    private static final String FORM_SOLAR = "form_solar";
    private static final String FORM_SIGN_OFF = "form_sign_off";
    private static final String FORM_CELL_1 = "form_cell_one";
    private static final String FORM_CELL_2 = "form_cell_two";
    private static final String FORM_CELL_3 = "form_cell_three";
    private static final String FORM_CELL_4 = "form_cell_four";
    private static final String FORM_CELL_5 = "form_cell_five";
    private static final String FORM_CELL_6 = "form_cell_six";
    private static final String FORM_CELL_7 = "form_cell_seven";
    private static final String FORM_CELL_8 = "form_cell_eight";
    private static final String FORM_BATTERY_TERMINAL = "form_battery_terminal";
    private static final String FORM_SOLAR_STRUCTURE = "form_solar_structure";
    private static final String FORM_CIRCLE_NAME = "form_circle_name";
    private static final String FORM_CLIENT_NAME = "form_client_name";
    private static final String FORM_IS_PLANNED = "form_is_planned";
    private static final String FORM_PLANNED_DATE = "form_planned_date";
    private static final String FORM_PLANNED_ID = "form_planned_id";
    private static final String FORM_LONGITUDE = "form_longitude";
    private static final String FORM_LATITUDE = "from_latitude";
    private static final String FORM_TASK_NAME = "form_task_name";
    private static final String FORM_ACTIVITY_NAME = "form_activity_name";
    private static final String FORM_STATUS_NAME = "form_status_name";
    private static final String FORM_SITE_NAME = "form_site_name";
    private static final String FORM_DA_AMOUNT = "form_da_name";
    private static final String FORM_DAY_NUMBER = "form_day_number";
    private static final String FORM_IS_PM = "form_is_pm";

    //----------------------------------Activity Task-------------------------------------
    private static final String TASK_ID = "task_id";
    private static final String TASK_NAME = "task_name";
    private static final String TASK_LAST_UPDATED = "last_updated";

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

    //--------------------------Customer Data-------------------------------------
    private static final String CUSTOMER_ID = "reason_id";
    private static final String CUSTOMER_NAME = "reason_name";
    private static final String CUSTOMER_LAST_UPDATED_DATE = "activity_id";

    //--------------------------Planned Activity-------------------------------------
    private static final String PLANNED_ACTIVITY_ID = "activity_id";
    private static final String PLANNED_EXECUTED = "executed";
    private static final String PLANNED_ACTIVITY_ON_THE_WAY = "on_the_way";
    private static final String PLANNED_REACHED_SITE = "reached_site";
    private static final String PLANNED_LEFT_SITE = "left_site";
    private static final String PLANNED_UNKNOWN = "unknown";
    private static final String PLANNED_ATTENDANCE = "attendance";
    private static final String PLANNED_SITE_NAME = "site_name";
    private static final String PLANNED_NUM_SITE_ID = "num_site_id";
    private static final String PLANNED_DATE = "date";
    private static final String PLANNED_SITE_ID = "site_id";
    private static final String PLANNED_ACTIVITY = "activity";
    private static final String PLANNED_FE_name = "fe_name";
    private static final String PLANNED_PLAN_ID = "plan_id";
    private static final String PLAN_ACTIVITY_ID = "plan_activity_id";
    private static final String PLANNED_MARKETING_DISTRIBUTOR = "marketing_distributor";
    private static final String PLANNED_TASK_ID = "task_id";
    private static final String PLANNED_CIRCLE_NAME = "circle_name";
    private static final String PLANNED_CIRCLE_ID = "circle_id";
    private static final String PLANNED_FE_ID = "fe_id";
    private static final String PLANNED_COMPLAINT_MESSAGE = "complaint_message";
    private static final String PLANNED_LAST_UPDATED_DATE = "last_updated_date";
    private static final String PLANNED_ACTIVITY_MILLI = "activity_milli";
    private static final String PLANNED_ACTIVITY_LEAVE_COUNT = "activity_leave_count";
    private static final String PLANNED_ACTIVITY_SUBMITTED_OFFLINE = "activity_submitted_offline";


    //--------------------------Executed Activity-------------------------------------
    private static final String EXECUTED_ACTIVITY_ID = "activity_id";
    private static final String EXECUTED_EXECUTED = "executed";
    private static final String EXECUTED_ACTIVITY_ON_THE_WAY = "on_the_way";
    private static final String EXECUTED_REACHED_SITE = "reached_site";
    private static final String EXECUTED_LEFT_SITE = "left_site";
    private static final String EXECUTED_UNKNOWN = "unknown";
    private static final String EXECUTED_ATTENDANCE = "attendance";
    private static final String EXECUTED_SITE_NAME = "site_name";
    private static final String EXECUTED_CUSTOMER = "customer";
    private static final String EXECUTED_DATE = "date";
    private static final String EXECUTED_TIME = "time";
    private static final String EXECUTED_ZONE = "zone";
    private static final String EXECUTED_TOTAL_AMOUNT = "total_amount";
    private static final String EXECUTED_TA_DA_AMOUNT = "ta_da_amount";
    private static final String EXECUTED_STATUS = "status";
    private static final String EXECUTED_DAYS_TAKEN = "days_taken";
    private static final String EXECUTED_NOC_APPROVED_COLOR = "noc_approved_color";
    private static final String EXECUTED_ACTIVITY = "activity";
    private static final String EXECUTED_NOC_APPROVAL = "noc_approval";
    private static final String EXECUTED_BONUS = "bonus";
    private static final String EXECUTED_PENALTY = "penalty";
    private static final String EXECUTED_FE_NAME = "fe_name";
    private static final String EXECUTED_FE_ID = "fe_id";
    private static final String EXECUTED_LAST_UPDATED_DATE = "last_updated_date";
    private static final String EXECUTED_LEAVE_COUNT = "leave_count";
    private static final String EXECUTED_CIRCLE_NAME = "circle_name";

    //--------------------------Display Expense Activity-------------------------------------
    private static final String DISPLAY_EXPENSE_ID = "id";
    private static final String DISPLAY_EXPENSE_SITE_ID = "site_id";
    private static final String DISPLAY_EXPENSE_SITE_NAME = "site_name";
    private static final String DISPLAY_EXPENSE_SUBMITTED_DATE = "submitted_date";
    private static final String DISPLAY_EXPENSE_TOTAL_EXPENSE = "total_expense";
    private static final String DISPLAY_EXPENSE_TA_EXPENSE = "ta_expense";
    private static final String DISPLAY_EXPENSE_DA_EXPENSE = "da_expense";
    private static final String DISPLAY_EXPENSE_HOTEL_EXPENSE = "hotel_expense";
    private static final String DISPLAY_EXPENSE_OTHER_EXPENSE = "other_expense";
    private static final String DISPLAY_EXPENSE_HOME_TO_SITE_KM = "home_to_site_km";
    private static final String DISPLAY_EXPENSE_HOME_TO_SITE_EXPENSE = "home_to_site_expense";
    private static final String DISPLAY_EXPENSE_HOME_TO_SITE_APPROVAL = "home_to_site_approval";
    private static final String DISPLAY_EXPENSE_SITE_TO_SITE_KM = "site_to_site_km";
    private static final String DISPLAY_EXPENSE_SITE_TO_SITE_EXPENSE = "site_to_site_expense";
    private static final String DISPLAY_EXPENSE_SITE_TO_SITE_APPROVAL = "site_to_site_approval";
    private static final String DISPLAY_EXPENSE_DA_DAY = "da_day";
    private static final String DISPLAY_EXPENSE_DA_STATUS = "da_status";
    private static final String DISPLAY_EXPENSE_HOTEL_DAY = "hotel_day";
    private static final String DISPLAY_EXPENSE_HOTEL_STATUS = "hotel_status";
    private static final String DISPLAY_EXPENSE_OTHER_STATUS = "other_status";
    private static final String DISPLAY_EXPENSE_LAST_UPDATED_DATE = "last_updated_date";

    //----------------------------------Fill Site Data-------------------------------------
    private static final String FILL_SITE_ID = "site_id";
    private static final String FILL_CUSTOMER_SITE_ID = "site_customer_id";
    private static final String FILL_SITE_NAME = "site_name";
    private static final String FILL_BRANCH_NAME = "branch_name";
    private static final String FILL_BRANCH_CODE = "branch_code";
    private static final String FILL_CITY = "city";
    private static final String FILL_PINCODE = "pincode";
    private static final String FILL_ON_OFF_SITE = "on_of_site";
    private static final String FILL_ADDRESS = "address";
    private static final String FILL_SAVE_CIRCLE_ID = "circle_id";
    private static final String FILL_SAVE_DISTRICT_ID = "district_id";
    private static final String FILL_SAVE_TEHSIL_ID = "tehsil_id";
    private static final String FILL_START_TIME = "start_time";
    private static final String FILL_END_TIME = "end_time";
    private static final String FILL_LAT = "lat";
    private static final String FILL_LONG = "long";
    private static final String FILL_SITE_DETAILS_DATE = "last_updated_date";

    //--------------------------State Data-------------------------------------
    private static final String STATE_ID = "state_id";
    private static final String STATE_NAME = "state_name";
    private static final String STATE_SN = "count_id";

    //--------------------------District Data-------------------------------------
    private static final String DISTRICT_ID = "district_id";
    private static final String DISTRICT_NAME = "district_name";
    private static final String DISTRICT_SN = "state_id";

    //--------------------------Tehsil Data-------------------------------------
    private static final String TEHSIL_ID = "tehsil_id";
    private static final String TEHSIL_NAME = "tehsil_name";
    private static final String TEHSIL_SN = "district_id";

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

    //--------------------------FE Call Tracking Data-------------------------------------
    private static final String CALL_TRACKING_DURATION = "duration";
    private static final String CALL_TRACKING_DIALLED_NUMBER = "dialled_number";
    private static final String CALL_TRACKING_DIALLED_EMP_ID = "status_emp_id";
    private static final String CALL_TRACKING_CALL_TYPE = "call_type";
    private static final String CALL_TRACKING_DIALER_USER_ID = "dialer_user_id";
    private static final String CALL_TRACKING_CALL_TIME = "call_time";

    //-----------------------------Location Tracker-------------------------------------------
    private static final String LOCATION_TRACKER_USER_ID = "user_id";
    private static final String LOCATION_TRACKER_LAT = "lat";
    private static final String LOCATION_TRAKER_LON = "lon";
    private static final String LOCATION_TRACKER_ADDRESS = "address";
    private static final String LOCATION_TRACKER_TIME = "tracked_time";
    private static final String LOCATION_TRACKER_DISTANCE = "tracked_distance";

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

    //------------------------------Expense Sheet--------------------------------------
    // Circle Table Columns
    //private static final String KEY_ID = "id";
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

    //------------------------------Complaint Description--------------------------------------
    // Circle Table Columns
    //private static final String KEY_ID = "id";
    private static final String COMPLAINT_DESCRIPTION_TEXT = "complaint_description_text";
    private static final String COMPLAINT_DESCRIPTION_TYPE = "complaint_description_type";
    private static final String COMPLAINT_DESCRIPTION_LAST_UPDATED = "complaint_description_last_updated";

    //------------------------------Complaint Description--------------------------------------
    // Circle Table Columns
    //private static final String KEY_ID = "id";
    private static final String EQUIP_LIST_ID = "equip_id";
    private static final String EQUIP_LIST_NAME = "equip_name";
    private static final String EQUIP_LIST_DATE = "updated_time";
    private static final String EQUIP_LIST_PARENT_ID = "parent_id";

    public AtmDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

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

        String CREATE_CIRCLE_TABLE = "CREATE TABLE " + TABLE_CIRCLE + "("
                + CIRCLE_ID + " INTEGER PRIMARY KEY," + CIRCLE_NAME + " TEXT,"
                + CIRCLE_TOTAL_SITES + " TEXT," + CIRCLE_TOTAL_ALARM_SITES + " INTEGER,"
                + CIRCLE_COLOR_CODE + " TEXT," + CIRCLE_HEAD_CONTACT + " TEXT, "
                + CIRCLE_HEADER_TOTAL_SITES + " TEXT," + CIRCLE_HEADER_ALARM_SITES + " TEXT, "
                + CIRCLE_HEADER_NON_COMM + " TEXT," + CIRCLE_HEADER_INV_ALARM + " TEXT, "
                + CIRCLE_HEADER_NMS_SITES + " TEXT, " + CIRCLE_HEADER_LOW_BATTERY + " TEXT,"
                + LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_CIRCLE_TABLE);

        String CREATE_ZONE_TABLE = "CREATE TABLE " + TABLE_ZONE + "("
                + ZONE_ID + " INTEGER PRIMARY KEY," + ZONE_NAME + " TEXT,"
                + ZONE_TOTAL_SITES + " TEXT," + ZONE_TOTAL_ALARM_SITES + " INTEGER,"
                + ZONE_COLOR_CODE + " TEXT," + ZONE_HEAD_CONTACT + " TEXT, "
                + ZONE_LAST_UPDATED + " TEXT," + ZONE_CIRCLE_ID + " TEXT)";
        db.execSQL(CREATE_ZONE_TABLE);


        String CREATE_CLUSTER_TABLE = "CREATE TABLE " + TABLE_CLUSTER + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + CLUSTER_ID + " TEXT," + CLUSTER_NAME + " TEXT,"
                + CLUSTER_TOTAL_SITES + " TEXT," + CLUSTER_TOTAL_ALARM_SITES + " INTEGER,"
                + CLUSTER_COLOR_CODE + " TEXT," + CLUSTER_HEAD_CONTACT + " TEXT, "
                + CLUSTER_HEADER_ALARM_SITES + " TEXT," + CLUSTER_HEADER_TOTAL_SITES + " TEXT, "
                + CLUSTER_HEADER_NON_COM + " TEXT," + CLUSTER_INV_ALARM + " TEXT, "
                + CLUSTER_LOW_BATTERY + " TEXT," + CLUSTER_NMS_SITES + " TEXT, "
                + CLUSTER_LAST_UPDATED + " TEXT," + CLUSTER_ZONE_ID + " TEXT)";
        db.execSQL(CREATE_CLUSTER_TABLE);

        String CREATE_SITE_TABLE = "CREATE TABLE " + TABLE_SITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + SITE_NAME + " TEXT,"
                + SITE_ID + " TEXT," + SITE_NAME_HINDI + " TEXT," + SITE_CLIENT_NAME + " TEXT,"
                + SITE_STATUS + " TEXT," + SITE_VOLTAGE + " TEXT, "
                + SITE_COLOR_CODE + " TEXT," + SITE_HEAD_CONTACT + " TEXT, "
                + SITE_HEADER_TOTAL_SITES + " TEXT," + SITE_HEADER_ALARM_SITES + " TEXT," + SITE_HEADER_NON_COM_SITES + " TEXT,"
                + SITE_HEADER_INV_SITES + " TEXT," + SITE_HEADER_LOW_BATTERY_SITES + " TEXT," + SITE_HEADER_NSM_SITES + " TEXT,"
                + SITE_LAST_UPDATE + " TEXT," + SITE_NUM_ID + " TEXT, " + SITE_CLUSTER_ID + " TEXT)";
        db.execSQL(CREATE_SITE_TABLE);

        String CREATE_SITE_DETAIL_TABLE = "CREATE TABLE " + TABLE_SITE_DETAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + SITE_DETAIL_NAME + " TEXT,"
                + SITE_DETAIL_NUM_ID + " TEXT," + SITE_DETAIL_ID + " TEXT," + SITE_DETAIL_DATE + " TEXT,"
                + SITE_DETAIL_TIME + " TEXT," + SITE_DETAIL_STATUS + " TEXT, "
                + SITE_DETAIL_BCI + " TEXT," + SITE_DETAIL_BDI + " TEXT, "
                + SITE_DETAIL_IN_OUT_VOLT + " TEXT," + SITE_DETAIL_LOAD_CURRENT + " TEXT, "
                + SITE_DETAIL_COLOR_CODE + " TEXT," + SITE_DETAIL_BATTERY_VOLTAGE + " TEXT, "
                + SITE_DETAIL_SOLAR_CURRENT + " TEXT," + SITE_DETAIL_ALARM_CODE + " TEXT, "
                + SITE_DETAIL_LAST_UPDATED + " TEXT," + SITE_DETAIL_SITE_ID + " TEXT)";
        db.execSQL(CREATE_SITE_DETAIL_TABLE);

        String CREATE_SITE_SEARCH_TABLE = "CREATE TABLE " + TABLE_SITE_SEARCH + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + SITE_SEARCH_ID + " TEXT UNIQUE," + SITE_SEARCH_NAME + " TEXT,"
                + SITE_SEARCH_CIRCLE_ID + " TEXT," + SITE_SEARCH_CIRCLE_NAME + " TEXT,"
                + SITE_SEARCH_CLIENT_ID + " TEXT," + SITE_SEARCH_CLIENT_NAME + " TEXT," + SITE_SEARCH_TYPE + " TEXT," + SITE_LAT + " TEXT," + SITE_LON + " TEXT,"
                + SITE_DISTANCE_FROM_BASE + " TEXT,"
                + SITE_SEARCH_CUSTOMER_ID + " TEXT," + SITE_SEARCH_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_SITE_SEARCH_TABLE);

        String CREATE_ACTIVITY_TABLE = "CREATE TABLE " + TABLE_ACTIVITY_SEARCH + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + ACTIVITY_NAME + " TEXT,"
                + ACTIVITY_ID + " TEXT," + ACTIVITY_TASK_LIST + " TEXT," + ACTIVITY_LAST_UPDATED_DATE + " TEXT)";
        db.execSQL(CREATE_ACTIVITY_TABLE);

        String CREATE_NOC_ENGG_TABLE = "CREATE TABLE " + TABLE_NOC_ENGG + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + NOC_ENGG_NAME + " TEXT," + NOC_ENGG_TYPE + " TEXT,"
                + NOC_ENGG_CONTACT + " TEXT," + NOC_ENGG_ID + " TEXT," + NOC_ENGG_LAST_UPDATED_DATE + " TEXT)";
        db.execSQL(CREATE_NOC_ENGG_TABLE);

        String CREATE_REASON_TABLE = "CREATE TABLE " + TABLE_REASON + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + REASON_NAME + " TEXT,"
                + REASON_ACTIVITY_ID + " TEXT," + REASON_ID + " TEXT," + REASON_LAST_UPDATED_DATE + " TEXT)";
        db.execSQL(CREATE_REASON_TABLE);

        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + TASK_ID + " TEXT,"
                + TASK_NAME + " TEXT," + TASK_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_TASK_TABLE);

        String CREATE_FORM_TABLE = "CREATE TABLE " + TABLE_ACTIVITY_FORM + "("
                + FORM_ID + " INTEGER PRIMARY KEY autoincrement," + FORM_USER_ID + " TEXT,"
                + FORM_SITE_ID + " TEXT," + FORM_ACTIVITY_ID + " TEXT," + FORM_NOC_ENGG_ID + " TEXT,"
                + FORM_STATUS + " REAL, " + FORM_REASON + " TEXT," + FORM_ZONE + " TEXT, "
                + FORM_TASK_ID + " TEXT," + FORM_REMARKS + " TEXT, " + FORM_MATERIAL_STATUS + " TEXT, "
                + FORM_EARTH_VOLT + " TEXT," + FORM_BATT_VOLT + " TEXT, " + FORM_BATT_CELLS + " TEXT, "
                + FORM_CHARGER + " TEXT," + FORM_INVERTER + " TEXT, " + FORM_EB_CONN + " TEXT, "
                + FORM_CONN + " TEXT," + FORM_SOLAR + " TEXT, " + FORM_SIGN_OFF + " TEXT, "
                + FORM_CELL_1 + " TEXT," + FORM_CELL_2 + " TEXT, " + FORM_CELL_3 + " TEXT, "
                + FORM_CELL_4 + " TEXT," + FORM_CELL_5 + " TEXT, " + FORM_CELL_6 + " TEXT, "
                + FORM_CELL_7 + " TEXT," + FORM_CELL_8 + " TEXT, "
                + FORM_CIRCLE_NAME + " TEXT," + FORM_CLIENT_NAME + " TEXT, "
                + FORM_BATTERY_TERMINAL + " TEXT," + FORM_SOLAR_STRUCTURE + " TEXT, "
                + FORM_IS_PLANNED + " TEXT," + FORM_PLANNED_DATE + " TEXT, " + FORM_PLANNED_ID + " TEXT, "
                + FORM_LONGITUDE + " TEXT," + FORM_LATITUDE + " TEXT, "
                + FORM_TASK_NAME + " TEXT," + FORM_ACTIVITY_NAME + " TEXT, " + FORM_STATUS_NAME + " TEXT, "
                + FORM_DA_AMOUNT + " TEXT," + FORM_DAY_NUMBER + " TEXT, "
                + FORM_IS_PM + " TEXT,"
                + FORM_SITE_NAME + " TEXT," + FORM_NOC_ENGG_CONTACT + " TEXT, " + FORM_DATE + " TEXT)";
        db.execSQL(CREATE_FORM_TABLE);

        String CREATE_TRANSIT_TABLE = "CREATE TABLE " + TABLE_TRANSIT + "("
                + TRANSIT_ID + " INTEGER PRIMARY KEY autoincrement," + TRANSIT_TYPE + " TEXT,"
                + TRANSIT_UID + " TEXT," + TRANSIT_SITE_ID + " TEXT," + TRANSIT_DATETIME + " TEXT,"
                + TRANSIT_CALCULATED_DISTANCE + " TEXT," + TRANSIT_CALCULATED_AMOUNT + " TEXT," + TRANSIT_ADDRESS + " TEXT,"
                + TRANSIT_ACTUAL_AMOUNT + " TEXT," + TRANSIT_ACTUAL_KMS + " TEXT," + TRANSIT_REMARKS + " TEXT,"
                + TRANSIT_HOTEL_EXPENSE + " TEXT," + TRANSIT_ACTUAL_HOTEL_EXPENSE + " TEXT,"
                + TRANSIT_LATITUDE + " TEXT," + TRANSIT_LONGITUDE + " TEXT)";

        db.execSQL(CREATE_TRANSIT_TABLE);

        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + "("
                + "id" + " INTEGER PRIMARY KEY autoincrement," + CUSTOMER_ID + " TEXT,"
                + CUSTOMER_NAME + " TEXT," + CUSTOMER_LAST_UPDATED_DATE + " TEXT)";

        db.execSQL(CREATE_CUSTOMER_TABLE);

        String CREATE_PLANNED_ACTIVITY_TABLE = "CREATE TABLE " + TABLE_PLANNED_ACTIVITY + "("
                + PLANNED_ACTIVITY_ID + " TEXT," + PLANNED_EXECUTED + " TEXT,"
                + PLANNED_ACTIVITY_ON_THE_WAY + " TEXT," + PLANNED_REACHED_SITE + " TEXT," + PLANNED_LEFT_SITE + " TEXT,"
                + PLANNED_UNKNOWN + " TEXT," + PLANNED_ATTENDANCE + " TEXT," + PLANNED_SITE_NAME + " TEXT,"
                + PLANNED_NUM_SITE_ID + " TEXT," + PLANNED_DATE + " TEXT," + PLANNED_SITE_ID + " TEXT,"
                + PLANNED_PLAN_ID + " TEXT," + PLAN_ACTIVITY_ID + " TEXT," + PLANNED_MARKETING_DISTRIBUTOR + " TEXT,"
                + PLANNED_TASK_ID + " TEXT," + PLANNED_CIRCLE_NAME + " TEXT," + PLANNED_CIRCLE_ID + " TEXT,"
                + PLANNED_FE_ID + " TEXT," + PLANNED_COMPLAINT_MESSAGE + " TEXT," + PLANNED_LAST_UPDATED_DATE + " TEXT,"
                + PLANNED_ACTIVITY_LEAVE_COUNT + " TEXT," + PLANNED_ACTIVITY_SUBMITTED_OFFLINE + " TEXT,"
                + PLANNED_ACTIVITY_MILLI + " TEXT," + PLANNED_ACTIVITY + " TEXT," + PLANNED_FE_name + " TEXT)";

        db.execSQL(CREATE_PLANNED_ACTIVITY_TABLE);

        String CREATE_EXECUTED_ACTIVITY_TABLE = "CREATE TABLE " + TABLE_EXECUTED_ACTIVITY + "("
                + EXECUTED_ACTIVITY_ID + " INTEGER PRIMARY KEY autoincrement," + EXECUTED_EXECUTED + " TEXT,"
                + EXECUTED_ACTIVITY_ON_THE_WAY + " TEXT," + EXECUTED_REACHED_SITE + " TEXT," + EXECUTED_LEFT_SITE + " TEXT,"
                + EXECUTED_UNKNOWN + " TEXT," + EXECUTED_ATTENDANCE + " TEXT," + EXECUTED_SITE_NAME + " TEXT,"
                + EXECUTED_CUSTOMER + " TEXT," + EXECUTED_DATE + " TEXT," + EXECUTED_ZONE + " TEXT,"
                + EXECUTED_TA_DA_AMOUNT + " TEXT," + EXECUTED_STATUS + " TEXT," + EXECUTED_DAYS_TAKEN + " TEXT,"
                + EXECUTED_TIME + " TEXT," + EXECUTED_TOTAL_AMOUNT + " TEXT," + EXECUTED_NOC_APPROVAL + " TEXT,"
                + EXECUTED_BONUS + " TEXT," + EXECUTED_PENALTY + " TEXT," + EXECUTED_FE_NAME + " TEXT," + EXECUTED_FE_ID + " TEXT,"
                + EXECUTED_LEAVE_COUNT + " TEXT," + EXECUTED_CIRCLE_NAME + " TEXT,"
                + EXECUTED_LAST_UPDATED_DATE + " TEXT," + EXECUTED_NOC_APPROVED_COLOR + " TEXT," + EXECUTED_ACTIVITY + " TEXT)";

        db.execSQL(CREATE_EXECUTED_ACTIVITY_TABLE);

        String CREATE_EXPENSE_DISPLAY_STATUS = "CREATE TABLE " + TABLE_EXPENSE_DISPLAY + "("
                + DISPLAY_EXPENSE_ID + " INTEGER PRIMARY KEY autoincrement," + DISPLAY_EXPENSE_SITE_ID + " TEXT,"
                + DISPLAY_EXPENSE_SITE_NAME + " TEXT," + DISPLAY_EXPENSE_SUBMITTED_DATE + " TEXT," + DISPLAY_EXPENSE_TOTAL_EXPENSE + " TEXT,"
                + DISPLAY_EXPENSE_TA_EXPENSE + " TEXT," + DISPLAY_EXPENSE_DA_EXPENSE + " TEXT," + DISPLAY_EXPENSE_HOTEL_EXPENSE + " TEXT,"
                + DISPLAY_EXPENSE_OTHER_EXPENSE + " TEXT," + DISPLAY_EXPENSE_HOME_TO_SITE_KM + " TEXT," + DISPLAY_EXPENSE_HOME_TO_SITE_EXPENSE + " TEXT,"
                + DISPLAY_EXPENSE_HOME_TO_SITE_APPROVAL + " TEXT," + DISPLAY_EXPENSE_SITE_TO_SITE_KM + " TEXT," + DISPLAY_EXPENSE_SITE_TO_SITE_EXPENSE + " TEXT,"
                + DISPLAY_EXPENSE_SITE_TO_SITE_APPROVAL + " TEXT," + DISPLAY_EXPENSE_DA_DAY + " TEXT," + DISPLAY_EXPENSE_DA_STATUS + " TEXT,"
                + DISPLAY_EXPENSE_HOTEL_DAY + " TEXT," + DISPLAY_EXPENSE_HOTEL_STATUS + " TEXT," + DISPLAY_EXPENSE_OTHER_STATUS + " TEXT,"
                + DISPLAY_EXPENSE_LAST_UPDATED_DATE + " TEXT)";

        db.execSQL(CREATE_EXPENSE_DISPLAY_STATUS);

        String CREATE_FILL_SITE_DATA = "CREATE TABLE " + TABLE_FILL_SITE_DATA + "("
                + "id" + " INTEGER PRIMARY KEY," + FILL_SITE_ID + " TEXT,"
                + FILL_CUSTOMER_SITE_ID + " TEXT," + FILL_SITE_NAME + " TEXT,"
                + FILL_BRANCH_CODE + " TEXT," + FILL_BRANCH_NAME + " TEXT,"
                + FILL_CITY + " TEXT," + FILL_PINCODE + " TEXT, "
                + FILL_ON_OFF_SITE + " TEXT," + FILL_ADDRESS + " TEXT, "
                + FILL_SAVE_CIRCLE_ID + " TEXT," + FILL_SAVE_DISTRICT_ID + " TEXT, "
                + FILL_SAVE_TEHSIL_ID + " TEXT, " + FILL_LAT + " TEXT,"
                + FILL_START_TIME + " TEXT, " + FILL_END_TIME + " TEXT,"
                + FILL_SITE_DETAILS_DATE + " TEXT, " + FILL_LONG + " TEXT)";
        db.execSQL(CREATE_FILL_SITE_DATA);

        String CREATE_STATE_TABLE = "CREATE TABLE " + TABLE_STATE + "("
                + "id INTEGER PRIMARY KEY," + STATE_SN + " TEXT,"
                + STATE_NAME + " TEXT," + STATE_ID + " TEXT)";
        db.execSQL(CREATE_STATE_TABLE);

        String CREATE_DISTRICT_TABLE = "CREATE TABLE " + TABLE_DISTRICT + "("
                + "id INTEGER PRIMARY KEY," + DISTRICT_SN + " TEXT,"
                + DISTRICT_NAME + " TEXT," + DISTRICT_ID + " TEXT)";
        db.execSQL(CREATE_DISTRICT_TABLE);

        String CREATE_TEHSIL_TABLE = "CREATE TABLE " + TABLE_TEHSIL + "("
                + "id INTEGER PRIMARY KEY," + TEHSIL_SN + " TEXT,"
                + TEHSIL_NAME + " TEXT," + TEHSIL_ID + " TEXT)";
        db.execSQL(CREATE_TEHSIL_TABLE);

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

        String CREATE_LOCATION_TRACKER_TABLE = "CREATE TABLE " + TABLE_LOCATION_TRACKER + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + LOCATION_TRACKER_USER_ID + " TEXT,"
                + LOCATION_TRACKER_LAT + " TEXT," + LOCATION_TRAKER_LON + " TEXT," + LOCATION_TRACKER_DISTANCE + " TEXT,"
                + LOCATION_TRACKER_ADDRESS + " TEXT," + LOCATION_TRACKER_TIME + " TEXT)";
        db.execSQL(CREATE_LOCATION_TRACKER_TABLE);

        String CREATE_COMPLAINT_TABLE = "CREATE TABLE " + TABLE_COMPLAINTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + COMPLAINT_USER_ID + " TEXT,"
                + COMPLAINT_SITE_ID + " TEXT," + COMPLAINT_NAME + " TEXT," + COMPLAINT_MOBILE_NUMBER + " TEXT,"
                + COMPLAINT_EMAIL_ID + " TEXT," + COMPLAINT_TYPE + " TEXT," + COMPLAINT_PRIORITY + " TEXT,"
                + COMPLAINT_CLIENT_NAME + " TEXT," + COMPLAINT_DESCRIPTION + " TEXT,"
                + COMPLAINT_PROPOSE_PLAN + " TEXT," + COMPLAINT_TIME + " TEXT)";
        db.execSQL(CREATE_COMPLAINT_TABLE);

        String CREATE_COMPLAINT_DESCRIPTION_TABLE = "CREATE TABLE " + TABLE_COMPLAINT_DESCRIPTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + COMPLAINT_DESCRIPTION_TEXT + " TEXT,"
                + COMPLAINT_DESCRIPTION_TYPE + " TEXT," + COMPLAINT_DESCRIPTION_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_COMPLAINT_DESCRIPTION_TABLE);

        String CREATE_EQUIP_LIST_TABLE = "CREATE TABLE " + TABLE_EQUIP_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + EQUIP_LIST_ID + " TEXT,"
                + EQUIP_LIST_DATE + " TEXT," + EQUIP_LIST_PARENT_ID + " TEXT," + EQUIP_LIST_NAME + " TEXT)";

        db.execSQL(CREATE_EQUIP_LIST_TABLE);

        String CREATE_Today_Plan_TABLE = "CREATE TABLE todaySitePlan(SiteId TEXT,CustomerSiteId TEXT, SiteName TEXT,siteStatus INTEGER DEFAULT 0)";
        db.execSQL(CREATE_Today_Plan_TABLE);
    }

    //-----------------------------Location Tracker-------------------------------------------

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CIRCLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLAINT_DESCRIPTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZONE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUSTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE_SEARCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_SEARCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOC_ENGG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REASON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_FORM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANNED_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXECUTED_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_DISPLAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEHSIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILL_SITE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FE_TRACKER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FE_TRACKER_TRANSIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FE_CALL_TRACKER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION_TRACKER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLAINTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_SHEET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIP_LIST);
        db.execSQL("DROP TABLE IF EXISTS todaySitePlan");
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // -------------------------------Adding new Circle---------------------------------------
    public boolean addCircleData(List<CircleDisplayDataModel> circleDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < circleDataList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(CIRCLE_NAME, circleDataList.get(i).getCircleName());
                values.put(CIRCLE_ID, circleDataList.get(i).getCircleId());
                values.put(CIRCLE_TOTAL_SITES, circleDataList.get(i).getTotalSites());
                values.put(CIRCLE_TOTAL_ALARM_SITES, Integer.parseInt(circleDataList.get(i).getTotalAlarmSites()));
                //values.put(CIRCLE_COLOR_CODE, circleDataModel.get(i).getColorCode());
                values.put(CIRCLE_HEAD_CONTACT, circleDataList.get(i).getCircleHeadContact());
                values.put(LAST_UPDATED, String.valueOf(time));
                values.put(CIRCLE_HEADER_TOTAL_SITES, circleDataList.get(i).getHeaderTotalSites());
                values.put(CIRCLE_HEADER_ALARM_SITES, circleDataList.get(i).getHeaderAlarmSites());
                values.put(CIRCLE_HEADER_NON_COMM, circleDataList.get(i).getHeaderNonComm());
                values.put(CIRCLE_HEADER_INV_ALARM, circleDataList.get(i).getHeaderInvAlarm());
                values.put(CIRCLE_HEADER_LOW_BATTERY, circleDataList.get(i).getHeaderLowBattery());
                values.put(CIRCLE_HEADER_NMS_SITES, circleDataList.get(i).getHeaderNmsSites());

                // Inserting Row
                long insert = db.insert(TABLE_CIRCLE, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    // --------------------------------Adding new Zone---------------------------------------
    public void addZoneData(List<ZoneDisplayDataModel> zoneDataList, String circleId) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();

        for (int i = 0; i < zoneDataList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(ZONE_NAME, zoneDataList.get(i).getZn());
            values.put(ZONE_ID, zoneDataList.get(i).getZid());
            values.put(ZONE_TOTAL_SITES, zoneDataList.get(i).getZd());
            values.put(ZONE_TOTAL_ALARM_SITES, Integer.parseInt(zoneDataList.get(i).getZv()));
            //values.put(CIRCLE_COLOR_CODE, circleDataModel.get(i).getColorCode());
            values.put(ZONE_HEAD_CONTACT, zoneDataList.get(i).getZmp());
            values.put(ZONE_LAST_UPDATED, String.valueOf(time));
            values.put(ZONE_CIRCLE_ID, circleId);

            // Inserting Row
            db.insert(TABLE_ZONE, null, values);
        }
        db.close(); // Closing database connection
    }

    // --------------------------------Adding new Cluster---------------------------------------
    public void addClusterData(List<ClusterDisplayDataModel> clusterDataList, String zoneId) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();

        for (int i = 0; i < clusterDataList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(CLUSTER_NAME, clusterDataList.get(i).getClusterName());
            values.put(CLUSTER_ID, clusterDataList.get(i).getClusterId());
            values.put(CLUSTER_TOTAL_SITES, clusterDataList.get(i).getClusterTotalSites());
            values.put(CLUSTER_TOTAL_ALARM_SITES, Integer.parseInt(clusterDataList.get(i).getClusterTotalAlarmSites()));
            //values.put(CIRCLE_COLOR_CODE, circleDataModel.get(i).getColorCode());
            values.put(CLUSTER_HEAD_CONTACT, clusterDataList.get(i).getClusterHeadContact());
            values.put(CLUSTER_LAST_UPDATED, String.valueOf(time));
            values.put(CLUSTER_ZONE_ID, zoneId);
            //--------------Header Data-------------
            values.put(CLUSTER_HEADER_TOTAL_SITES, clusterDataList.get(i).getTotalAlarmSites());
            values.put(CLUSTER_HEADER_ALARM_SITES, clusterDataList.get(i).getTotalAlarmSites());
            values.put(CLUSTER_HEADER_NON_COM, clusterDataList.get(i).getTotalNonCom());
            values.put(CLUSTER_INV_ALARM, clusterDataList.get(i).getTotalInvAlarm());
            values.put(CLUSTER_LOW_BATTERY, clusterDataList.get(i).getTotalLowBattery());
            values.put(CLUSTER_NMS_SITES, clusterDataList.get(i).getNsmSites());

            // Inserting Row
            db.insert(TABLE_CLUSTER, null, values);
        }
        db.close(); // Closing database connection
    }

    // --------------------------------Adding new Site---------------------------------------

    public void addSiteData(ArrayList<SiteDisplayDataModel> siteDataList, String clusterId) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();

        for (int i = 0; i < siteDataList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(SITE_NAME, siteDataList.get(i).getSiteName());
            values.put(SITE_ID, siteDataList.get(i).getSiteId());
            values.put(SITE_NAME_HINDI, siteDataList.get(i).getSiteNameHindi());
            values.put(SITE_CLIENT_NAME, siteDataList.get(i).getClientName());
            //values.put(CIRCLE_COLOR_CODE, circleDataModel.get(i).getColorCode());
            values.put(SITE_STATUS, siteDataList.get(i).getSiteStatus());
            values.put(SITE_VOLTAGE, siteDataList.get(i).getSiteVoltage());
            values.put(SITE_COLOR_CODE, siteDataList.get(i).getColorCode());
            values.put(SITE_HEAD_CONTACT, siteDataList.get(i).getSiteHeadContact());
            values.put(SITE_LAST_UPDATE, String.valueOf(time));
            values.put(SITE_NUM_ID, siteDataList.get(i).getSiteNumId());
            values.put(SITE_CLUSTER_ID, clusterId);
            //------Header Data-------
            values.put(SITE_HEADER_TOTAL_SITES, siteDataList.get(i).getHeaderTotalSites());
            values.put(SITE_HEADER_ALARM_SITES, siteDataList.get(i).getHeaderAlarmSites());
            values.put(SITE_HEADER_NON_COM_SITES, siteDataList.get(i).getHeaderNonCommSites());
            values.put(SITE_HEADER_INV_SITES, siteDataList.get(i).getHeaderInvSites());
            values.put(SITE_HEADER_LOW_BATTERY_SITES, siteDataList.get(i).getHeaderLowBatterySites());
            values.put(SITE_HEADER_NSM_SITES, siteDataList.get(i).getHeaderNsmSites());

            // Inserting Row
            db.insert(TABLE_SITE, null, values);
        }
        db.close(); // Closing database connection
    }

    // --------------------------------Adding new Site Detail---------------------------------------

    public void addSiteDetailData(ArrayList<SiteDetailsDisplayModel> siteDetailDataList, String siteId, String siteName, String siteNumId) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();

        for (int i = 0; i < siteDetailDataList.size(); i++) {

            String date = siteDetailDataList.get(i).getDate();
            String[] datetime = date.split(" ");

            ContentValues values = new ContentValues();
            values.put(SITE_DETAIL_NAME, siteName);
            values.put(SITE_DETAIL_ID, siteId);
            values.put(SITE_DETAIL_NUM_ID, siteNumId);
            values.put(SITE_DETAIL_COLOR_CODE, siteDetailDataList.get(i).getColorCode());
            values.put(SITE_DETAIL_BATTERY_VOLTAGE, siteDetailDataList.get(i).getBatteryVoltage());
            values.put(SITE_DETAIL_DATE, datetime[0]);
            values.put(SITE_DETAIL_TIME, datetime[1]);
            values.put(SITE_DETAIL_STATUS, siteDetailDataList.get(i).getSiteStatus());
            values.put(SITE_DETAIL_BCI, siteDetailDataList.get(i).getBatteryChargeCurrent());
            values.put(SITE_DETAIL_BDI, siteDetailDataList.get(i).getBatteryDischargeCurrent());
            values.put(SITE_DETAIL_IN_OUT_VOLT, siteDetailDataList.get(i).getInputOutVoltage());
            values.put(SITE_DETAIL_LOAD_CURRENT, siteDetailDataList.get(i).getLoadCurrent());
            values.put(SITE_DETAIL_SOLAR_CURRENT, siteDetailDataList.get(i).getSolarCurrent());
            values.put(SITE_DETAIL_ALARM_CODE, siteDetailDataList.get(i).getCurrentAlarm());
            values.put(SITE_DETAIL_LAST_UPDATED, String.valueOf(time));

            // Inserting Row
            db.insert(TABLE_SITE_DETAILS, null, values);
        }
        db.close(); // Closing database connection
    }

    //--------------------------------------------------------------------------------------------

    // ------------------------Getting All Circle Data-------------------------------------------

    public ArrayList<SiteDetailsDisplayModel> getAllSiteDetailData(String siteId) {
        ArrayList<SiteDetailsDisplayModel> siteDetailList = new ArrayList<SiteDetailsDisplayModel>();
        // Select All Query
        String selectQuery = "";

        selectQuery = "SELECT  * FROM " + TABLE_SITE_DETAILS + " Where " + SITE_DETAIL_NUM_ID + " = '" + siteId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SiteDetailsDisplayModel siteDetailModel = new SiteDetailsDisplayModel();

                siteDetailModel.setSiteNumId(cursor.getString(cursor.getColumnIndex("site_detail_num_id")));
                siteDetailModel.setSiteName(cursor.getString(cursor.getColumnIndex("site_detail_name")));
                siteDetailModel.setSiteId(cursor.getString(cursor.getColumnIndex("site_detail_id")));
                siteDetailModel.setDate(cursor.getString(cursor.getColumnIndex("site_detail_date")) + " " + cursor.getString(cursor.getColumnIndex("site_detail_time")));
                siteDetailModel.setColorCode(cursor.getString(cursor.getColumnIndex("site_detail_color_code")));
                siteDetailModel.setBatteryVoltage(cursor.getString(cursor.getColumnIndex("site_detail_battery_volt")));
                siteDetailModel.setSiteStatus(cursor.getString(cursor.getColumnIndex("site_detail_status")));
                siteDetailModel.setInputOutVoltage(cursor.getString(cursor.getColumnIndex("site_detail_in_out_volt")));
                siteDetailModel.setSolarCurrent(cursor.getString(cursor.getColumnIndex("site_detail_solar_current")));
                siteDetailModel.setBatteryChargeCurrent(cursor.getString(cursor.getColumnIndex("site_detail_bci")));
                siteDetailModel.setBatteryDischargeCurrent(cursor.getString(cursor.getColumnIndex("site_detail_bdi")));
                siteDetailModel.setCurrentAlarm(cursor.getString(cursor.getColumnIndex("site_detail_alarm_code")));
                siteDetailModel.setLoadCurrent(cursor.getString(cursor.getColumnIndex("site_detail_load_current")));
                siteDetailModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex("site_detail_last_updated")));

                siteDetailList.add(siteDetailModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return siteDetailList;
    }

    //--------------------------------------------------------------------------------------------
   /* // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_PH_NO}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }*/

    // ------------------------Getting All Circle Data-------------------------------------------
    public List<CircleDisplayDataModel> getAllCircleData(String sortType) {
        List<CircleDisplayDataModel> circleList = new ArrayList<CircleDisplayDataModel>();
        // Select All Query
        String selectQuery = "";

        if (sortType.equalsIgnoreCase("NAME") || sortType.equalsIgnoreCase("0")) {
            selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_NAME + " COLLATE NOCASE ASC";
        } else if (sortType.equalsIgnoreCase("1")) {
            selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_NAME + " COLLATE NOCASE DESC";
        } else if (sortType.equalsIgnoreCase("3")) {
            selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " ASC";
        } else if (sortType.equalsIgnoreCase("ALARM_SITES") || sortType.equalsIgnoreCase("2")) {
            selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " DESC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CircleDisplayDataModel circleModel = new CircleDisplayDataModel();
                circleModel.setCircleName(cursor.getString(1));
                circleModel.setCircleId(cursor.getString(0));
                circleModel.setTotalSites(cursor.getString(2));
                circleModel.setTotalAlarmSites(cursor.getString(3));
                circleModel.setColorCode(cursor.getString(4));
                circleModel.setCircleHeadContact(cursor.getString(5));
                circleModel.setLastUpdatedDate(cursor.getString(6));
                circleModel.setHeaderAlarmSites(cursor.getString(cursor.getColumnIndex(CIRCLE_HEADER_ALARM_SITES)));
                circleModel.setHeaderTotalSites(cursor.getString(cursor.getColumnIndex(CIRCLE_HEADER_TOTAL_SITES)));
                circleModel.setHeaderNonComm(cursor.getString(cursor.getColumnIndex(CIRCLE_HEADER_NON_COMM)));
                circleModel.setHeaderNmsSites(cursor.getString(cursor.getColumnIndex(CIRCLE_HEADER_NMS_SITES)));
                circleModel.setHeaderLowBattery(cursor.getString(cursor.getColumnIndex(CIRCLE_HEADER_LOW_BATTERY)));
                circleModel.setHeaderInvAlarm(cursor.getString(cursor.getColumnIndex(CIRCLE_HEADER_INV_ALARM)));
                // Adding contact to list
                circleList.add(circleModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return circleList;
    }

    //---------------------------------------------------------------------------------------

    // ------------------------Getting All Zone Data-------------------------------------------
    public List<ZoneDisplayDataModel> getAllZoneData(String circleId, String sortType) {
        List<ZoneDisplayDataModel> zoneList = new ArrayList<ZoneDisplayDataModel>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_ZONE + " WHERE " + ZONE_CIRCLE_ID + " = '" + circleId + "'";

        String selectQuery = "";

        if (sortType.equalsIgnoreCase("NAME")) {
            selectQuery = "SELECT  * FROM " + TABLE_ZONE + " WHERE " + ZONE_CIRCLE_ID + " = '" + circleId + "'" + " ORDER BY " + ZONE_NAME + " ASC";
        } else if (sortType.equalsIgnoreCase("ALARM_SITES")) {
            selectQuery = "SELECT  * FROM " + TABLE_ZONE + " WHERE " + ZONE_CIRCLE_ID + " = '" + circleId + "'" + " ORDER BY " + ZONE_TOTAL_ALARM_SITES + " DESC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ZoneDisplayDataModel zoneModel = new ZoneDisplayDataModel();
                zoneModel.setZn(cursor.getString(1));
                zoneModel.setZid(cursor.getString(0));
                zoneModel.setZd(cursor.getString(2));
                zoneModel.setZv(cursor.getString(3));
                zoneModel.setCo(cursor.getString(4));
                zoneModel.setZmp(cursor.getString(5));
                zoneModel.setLastUpdatedDate(cursor.getString(6));
                // Adding contact to list
                zoneList.add(zoneModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return zoneList;
    }

    //---------------------------------------------------------------------------------------

    // ------------------------Getting All Cluster Data-------------------------------------------
    public List<ClusterDisplayDataModel> getAllClusterData(String zone_id, String sortType) {
        List<ClusterDisplayDataModel> clusterList = new ArrayList<ClusterDisplayDataModel>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_CLUSTER + " WHERE " + CLUSTER_ZONE_ID + " = '" + zone_id + "'";

        String selectQuery = "";

        if (sortType.equalsIgnoreCase("NAME") || sortType.equalsIgnoreCase("0")) {
            selectQuery = "SELECT  * FROM " + TABLE_CLUSTER + " WHERE " + CLUSTER_ZONE_ID + " = '" + zone_id + "'" + " ORDER BY " + CLUSTER_NAME + " COLLATE NOCASE ASC";
        } else if (sortType.equalsIgnoreCase("1")) {
            selectQuery = "SELECT  * FROM " + TABLE_CLUSTER + " WHERE " + CLUSTER_ZONE_ID + " = '" + zone_id + "'" + " ORDER BY " + CLUSTER_NAME + " COLLATE NOCASE DESC";
        } else if (sortType.equalsIgnoreCase("ALARM_SITES") || sortType.equalsIgnoreCase("2")) {
            selectQuery = "SELECT  * FROM " + TABLE_CLUSTER + " WHERE " + CLUSTER_ZONE_ID + " = '" + zone_id + "'" + " ORDER BY " + CLUSTER_TOTAL_ALARM_SITES + " DESC";
        } else if (sortType.equalsIgnoreCase("3")) {
            selectQuery = "SELECT  * FROM " + TABLE_CLUSTER + " WHERE " + CLUSTER_ZONE_ID + " = '" + zone_id + "'" + " ORDER BY " + CLUSTER_TOTAL_ALARM_SITES + " ASC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ClusterDisplayDataModel clusterModel = new ClusterDisplayDataModel();
                clusterModel.setClusterName(cursor.getString(cursor.getColumnIndex(CLUSTER_NAME)));
                clusterModel.setClusterId(cursor.getString(cursor.getColumnIndex(CLUSTER_ID)));
                clusterModel.setClusterTotalSites(cursor.getString(cursor.getColumnIndex(CLUSTER_TOTAL_SITES)));
                clusterModel.setClusterTotalAlarmSites(cursor.getString(cursor.getColumnIndex(CLUSTER_TOTAL_ALARM_SITES)));
                clusterModel.setClusterColorCode(cursor.getString(cursor.getColumnIndex(CLUSTER_COLOR_CODE)));
                clusterModel.setClusterHeadContact(cursor.getString(cursor.getColumnIndex(CLUSTER_HEAD_CONTACT)));
                clusterModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(CLUSTER_LAST_UPDATED)));
                //-------------Get Header Data-------------
                clusterModel.setTotalSites(cursor.getString(cursor.getColumnIndex(CLUSTER_HEADER_TOTAL_SITES)));
                clusterModel.setTotalAlarmSites(cursor.getString(cursor.getColumnIndex(CLUSTER_HEADER_ALARM_SITES)));
                clusterModel.setTotalNonCom(cursor.getString(cursor.getColumnIndex(CLUSTER_HEADER_NON_COM)));
                clusterModel.setTotalInvAlarm(cursor.getString(cursor.getColumnIndex(CLUSTER_INV_ALARM)));
                clusterModel.setTotalLowBattery(cursor.getString(cursor.getColumnIndex(CLUSTER_LOW_BATTERY)));
                clusterModel.setNsmSites(cursor.getString(cursor.getColumnIndex(CLUSTER_NMS_SITES)));
                // Adding contact to list
                clusterList.add(clusterModel);
            } while (cursor.moveToNext());
        }
        db.close();
        return clusterList;
    }

    //---------------------------------------------------------------------------------------

    // ------------------------Getting All Site Data-------------------------------------------

    public ArrayList<SiteDisplayDataModel> getAllSiteData(String cluster_id, String sortType) {
        ArrayList<SiteDisplayDataModel> siteList = new ArrayList<SiteDisplayDataModel>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_CLUSTER + " WHERE " + CLUSTER_ZONE_ID + " = '" + cluster_id + "'";

        String selectQuery = "";

        if (sortType.equalsIgnoreCase("NAME") || sortType.equalsIgnoreCase("0")) {
            selectQuery = "SELECT  * FROM " + TABLE_SITE + " WHERE " + SITE_CLUSTER_ID + " = '" + cluster_id + "'" + " ORDER BY " + SITE_NAME + " ASC";
        } else if (sortType.equalsIgnoreCase("1")) {
            selectQuery = "SELECT  * FROM " + TABLE_SITE + " WHERE " + SITE_CLUSTER_ID + " = '" + cluster_id + "'" + " ORDER BY " + SITE_NAME + " DESC";
        } else if (sortType.equalsIgnoreCase("2")) {
            selectQuery = "SELECT  * FROM " + TABLE_SITE + " WHERE " + SITE_CLUSTER_ID + " = '" + cluster_id + "'" + " ORDER BY " + SITE_VOLTAGE + " DESC";
        } else if (sortType.equalsIgnoreCase("SITE_VOLTAGE") || sortType.equalsIgnoreCase("3")) {
            selectQuery = "SELECT  * FROM " + TABLE_SITE + " WHERE " + SITE_CLUSTER_ID + " = '" + cluster_id + "'" + " ORDER BY " + SITE_VOLTAGE + " ASC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SiteDisplayDataModel siteModel = new SiteDisplayDataModel();
                siteModel.setSiteName(cursor.getString(cursor.getColumnIndex(SITE_NAME)));
                siteModel.setSiteId(cursor.getString(cursor.getColumnIndex(SITE_ID)));
                siteModel.setSiteNameHindi(cursor.getString(cursor.getColumnIndex(SITE_NAME_HINDI)));
                siteModel.setClientName(cursor.getString(cursor.getColumnIndex(SITE_CLIENT_NAME)));
                siteModel.setSiteStatus(cursor.getString(cursor.getColumnIndex(SITE_STATUS)));
                siteModel.setSiteVoltage(cursor.getString(cursor.getColumnIndex(SITE_VOLTAGE)));
                siteModel.setColorCode(cursor.getString(cursor.getColumnIndex(SITE_COLOR_CODE)));
                siteModel.setSiteHeadContact(cursor.getString(cursor.getColumnIndex(SITE_HEAD_CONTACT)));
                siteModel.setLatestTiming(cursor.getString(cursor.getColumnIndex(SITE_LAST_UPDATE)));
                siteModel.setSiteNumId(cursor.getString(cursor.getColumnIndex(SITE_NUM_ID)));
                //-----------------Get Header Data-----------------
                siteModel.setHeaderTotalSites(cursor.getString(cursor.getColumnIndex(SITE_HEADER_TOTAL_SITES)));
                siteModel.setHeaderAlarmSites(cursor.getString(cursor.getColumnIndex(SITE_HEADER_ALARM_SITES)));
                siteModel.setHeaderNonCommSites(cursor.getString(cursor.getColumnIndex(SITE_HEADER_NON_COM_SITES)));
                siteModel.setHeaderInvSites(cursor.getString(cursor.getColumnIndex(SITE_HEADER_INV_SITES)));
                siteModel.setHeaderNsmSites(cursor.getString(cursor.getColumnIndex(SITE_HEADER_NSM_SITES)));
                siteModel.setHeaderLowBatterySites(cursor.getString(cursor.getColumnIndex(SITE_HEADER_LOW_BATTERY_SITES)));

                // Adding contact to list
                siteList.add(siteModel);
            } while (cursor.moveToNext());
        }
        db.close();
        return siteList;
    }

    //---------------------------------------------------------------------------------------


   /* // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }
*/


    //----------------------Get Last Updated Date----------------------------------
    public String getLastUpdatedDate(String tableName, String columnName, String idColumnName, String idValue) {
        String lastUpdatedDate = "";
        String lastUpdatedDateQuery;

        if (tableName.equalsIgnoreCase("circle") || tableName.equalsIgnoreCase("site_search_details")) {
            lastUpdatedDateQuery = "SELECT " + columnName + " FROM " + tableName;
        } else {
            lastUpdatedDateQuery = "SELECT " + columnName + " FROM " + tableName + " WHERE " + idColumnName + " = '" + idValue + "'";
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(lastUpdatedDateQuery, null);
        if (cursor.moveToFirst()) {
            do {
                lastUpdatedDate = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return lastUpdatedDate;
    }

    //---------------------------Get Search Data for Circle view-----------------------------------
    public List<CircleDisplayDataModel> searchCircleData(String tableName, String searchString, String columnName) {

        List<CircleDisplayDataModel> circleList = new ArrayList<CircleDisplayDataModel>();

        String selectQuery = "SELECT  * FROM " + tableName + " WHERE " + columnName + " LIKE '%" + searchString + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CircleDisplayDataModel circleModel = new CircleDisplayDataModel();
                circleModel.setCircleName(cursor.getString(1));
                circleModel.setCircleId(cursor.getString(0));
                circleModel.setTotalSites(cursor.getString(2));
                circleModel.setTotalAlarmSites(cursor.getString(3));
                circleModel.setColorCode(cursor.getString(4));
                circleModel.setCircleHeadContact(cursor.getString(5));
                circleModel.setLastUpdatedDate(cursor.getString(6));
                // Adding contact to list
                circleList.add(circleModel);
            } while (cursor.moveToNext());
        }

        db.close();
        return circleList;
    }

    //--------------------------Get Search Data for Zone view---------------------------------------

    public List<ZoneDisplayDataModel> searchZoneData(String tableName, String searchString, String columnName) {

        List<ZoneDisplayDataModel> zoneList = new ArrayList<ZoneDisplayDataModel>();

        String selectQuery = "SELECT  * FROM " + tableName + " WHERE " + columnName + " LIKE '%" + searchString + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ZoneDisplayDataModel zoneModel = new ZoneDisplayDataModel();
                zoneModel.setZn(cursor.getString(1));
                zoneModel.setZid(cursor.getString(0));
                zoneModel.setZd(cursor.getString(2));
                zoneModel.setZv(cursor.getString(3));
                zoneModel.setCo(cursor.getString(4));
                zoneModel.setZmp(cursor.getString(5));
                zoneModel.setLastUpdatedDate(cursor.getString(6));
                // Adding contact to list
                zoneList.add(zoneModel);
            } while (cursor.moveToNext());
        }

        db.close();
        return zoneList;
    }

    //--------------------------------------------------------------------------------------------

    public void deleteAllRows(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }


   /* //-------------------Delete Zone Data ----------------------------------

    public void deleteZoneData(String tableName, String circleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    //-----------------------------------------------------------------------

    //-------------------Delete Cluster Data ----------------------------------

    public void deleteClusterData(String tableName, String zoneId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    //--------------------------------------------------------------------------

    //-------------------Delete Site Data ----------------------------------

    public void deleteSiteData(String tableName, String clusterId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }*/

    //-----------------------------------------------------------------------

    //----------------------------delete all tables data-------------------------

    /*public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("circle", null, null);
        db.delete("zone", null, null);
        db.delete("cluster", null, null);
        db.delete("site", null, null);
        db.delete("site_details", null, null);
        db.close();
    }*/

    //----------------------Search Site Table---------------------------------

    //---------------------------------Adding Site Details for Search-----------------------------
    public void addSearchSiteData(List<SiteDisplayDataModel> siteDisplayDataList) {
        SQLiteDatabase db = this.getWritableDatabase();
    /*if(siteDisplayDataList.get(0).getSiteType().equals("Survey")){
        db.delete(TABLE_SITE_SEARCH, SITE_SEARCH_TYPE + "=" + "'Survey'", null);
    }*/
        db.beginTransaction();
        long time = System.currentTimeMillis();
        for (int i = 0; i < siteDisplayDataList.size(); i++) {

            if (!siteDisplayDataList.get(i).getSiteId().equalsIgnoreCase("null") || !siteDisplayDataList.get(i).getSiteId().equalsIgnoreCase(null)) {
                ContentValues values = new ContentValues();
                values.put(SITE_SEARCH_ID, siteDisplayDataList.get(i).getSiteId());
                values.put(SITE_SEARCH_NAME, siteDisplayDataList.get(i).getSiteName());
                values.put(SITE_SEARCH_CUSTOMER_ID, siteDisplayDataList.get(i).getSiteNumId());
                values.put(SITE_SEARCH_LAST_UPDATED, String.valueOf(time));
                values.put(SITE_SEARCH_CIRCLE_ID, siteDisplayDataList.get(i).getCircleId());
                values.put(SITE_SEARCH_CIRCLE_NAME, siteDisplayDataList.get(i).getCircleName());
                values.put(SITE_SEARCH_CLIENT_ID, siteDisplayDataList.get(i).getClientId());
                values.put(SITE_SEARCH_CLIENT_NAME, siteDisplayDataList.get(i).getClientName());
                values.put(SITE_SEARCH_TYPE, siteDisplayDataList.get(i).getSiteType());
                values.put(SITE_LAT, siteDisplayDataList.get(i).getSiteLat());
                values.put(SITE_LON, siteDisplayDataList.get(i).getSiteLong());
                values.put(SITE_DISTANCE_FROM_BASE, siteDisplayDataList.get(i).getBaseDistance());

                // Inserting Row
                db.insert(TABLE_SITE_SEARCH, null, values);
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close(); // Closing database connection
    }

    //get site search data by siteid
    public SiteDisplayDataModel getSiteSearchDataBySiteId(String siteId) {
        String query = "SELECT *  FROM " + TABLE_SITE_SEARCH + " WHERE " + SITE_SEARCH_CUSTOMER_ID + "= '" + siteId + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        SiteDisplayDataModel siteDisplayDataModel = new SiteDisplayDataModel();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            siteDisplayDataModel.setSiteNumId(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CUSTOMER_ID)));
            siteDisplayDataModel.setSiteName(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_NAME)));
            siteDisplayDataModel.setSiteId(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_ID)));
            siteDisplayDataModel.setClientId(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CLIENT_ID)));
            siteDisplayDataModel.setClientName(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CLIENT_NAME)));
            siteDisplayDataModel.setCircleId(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CIRCLE_ID)));
            siteDisplayDataModel.setCircleName(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CIRCLE_NAME)));
            siteDisplayDataModel.setSiteLat(cursor.getString(cursor.getColumnIndex(SITE_LAT)));
            siteDisplayDataModel.setSiteLong(cursor.getString(cursor.getColumnIndex(SITE_LON)));
            siteDisplayDataModel.setBaseDistance((cursor.getString(cursor.getColumnIndex(SITE_DISTANCE_FROM_BASE))));

            cursor.close();
        } else {
            siteDisplayDataModel = null;
        }
        db.close();
        return siteDisplayDataModel;
    }

    public void deleteSiteSearchData() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("site_search_details", null, null);
            //db.delete("activity_search_details", null, null);
            db.delete("noc_engg_details", null, null);
            db.close();
        } catch (Exception ex) {

        }

    }

    public ArrayList<SiteDisplayDataModel> getFilteredData(String columnName, String searchString, String siteType) {
        ArrayList<SiteDisplayDataModel> arrFilteredData = new ArrayList<>();

        String selectQuery = "";
        /*if (siteType.equals("Survey")) {
            selectQuery = "SELECT  * FROM " + TABLE_SITE_SEARCH;
        } else {
            selectQuery = "SELECT  * FROM " + TABLE_SITE_SEARCH + " WHERE " + SITE_SEARCH_TYPE + " = null";
        }
*/
        selectQuery = "SELECT  * FROM " + TABLE_SITE_SEARCH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SiteDisplayDataModel siteDisplayDataModel = new SiteDisplayDataModel();
                siteDisplayDataModel.setSiteNumId(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CUSTOMER_ID)));
                siteDisplayDataModel.setSiteName(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_NAME)));
                siteDisplayDataModel.setSiteId(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_ID)));
                siteDisplayDataModel.setClientId(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CLIENT_ID)));
                siteDisplayDataModel.setClientName(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CLIENT_NAME)));
                siteDisplayDataModel.setCircleId(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CIRCLE_ID)));
                siteDisplayDataModel.setCircleName(cursor.getString(cursor.getColumnIndex(SITE_SEARCH_CIRCLE_NAME)));
                siteDisplayDataModel.setSiteLat(cursor.getString(cursor.getColumnIndex(SITE_LAT)));
                siteDisplayDataModel.setSiteLong(cursor.getString(cursor.getColumnIndex(SITE_LON)));
                siteDisplayDataModel.setBaseDistance((cursor.getString(cursor.getColumnIndex(SITE_DISTANCE_FROM_BASE))));

                // Adding contact to list
                arrFilteredData.add(siteDisplayDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        return arrFilteredData;
    }

    //------------------------------Select Activity in Activity Form-------------------------------

    public void addActivityData(ArrayList<ActivityListSheetDataModel> activityDetailArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.delete("activity_search_details", null, null);

        long time = System.currentTimeMillis();
        for (int i = 0; i < activityDetailArrayList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(ACTIVITY_ID, activityDetailArrayList.get(i).getActivityId());
            values.put(ACTIVITY_NAME, activityDetailArrayList.get(i).getActivityName());
            values.put(ACTIVITY_LAST_UPDATED_DATE, String.valueOf(time));
            values.put(ACTIVITY_TASK_LIST, activityDetailArrayList.get(i).getTaskId());

            // Inserting Row
            db.insert(TABLE_ACTIVITY_SEARCH, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<ActivityListSheetDataModel> getAllActivityData(String columnName, String columnValue) {
        ArrayList<ActivityListSheetDataModel> arrAllActivityData = new ArrayList<>();

        String selectQuery = "";
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            if (columnName.equalsIgnoreCase("")) {
                selectQuery = "SELECT *  FROM " + TABLE_ACTIVITY_SEARCH;
            } else {
                selectQuery = "SELECT *  FROM " + TABLE_ACTIVITY_SEARCH + " WHERE " + columnName + "= '" + columnValue + "'";
                //String selectQuery = "SELECT *  FROM " + TABLE_ACTIVITY_SEARCH;
            }

            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (!db.isOpen()) {
                db = this.getWritableDatabase();
            }
            if (cursor.moveToFirst()) {
                do {
                    String taskId = cursor.getString(3);
                   /* if (!taskId.equals(null) || !taskId.equals(null)) {

                    }*/
                    ActivityListSheetDataModel activityListSheetDataModel = new ActivityListSheetDataModel();
                    activityListSheetDataModel.setActivityId(cursor.getString(cursor.getColumnIndex(ACTIVITY_ID)));
                    activityListSheetDataModel.setActivityName(cursor.getString(cursor.getColumnIndex(ACTIVITY_NAME)));
                    activityListSheetDataModel.setTaskId(cursor.getString(cursor.getColumnIndex(ACTIVITY_TASK_LIST)));
                    activityListSheetDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(ACTIVITY_LAST_UPDATED_DATE)));
                    // Adding contact to list
                    arrAllActivityData.add(activityListSheetDataModel);
                } while (cursor.moveToNext());
            }

            db.close();
        } catch (Exception ex) {

        }

        //return arrFilteredAllActivityData;
        return arrAllActivityData;
    }

    //---------------------------------------------------------------------------------------------

    //------------------------------Select Activity in Activity Form-------------------------------

    public void addNocEnggData(ArrayList<NocEnggListDataModel> arrayListNocEnggData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayListNocEnggData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(NOC_ENGG_ID, arrayListNocEnggData.get(i).getEnggId());
            values.put(NOC_ENGG_NAME, arrayListNocEnggData.get(i).getEnggName());
            values.put(NOC_ENGG_CONTACT, arrayListNocEnggData.get(i).getEnggContact());
            values.put(NOC_ENGG_TYPE, arrayListNocEnggData.get(i).getEnggType());
            values.put(NOC_ENGG_LAST_UPDATED_DATE, String.valueOf(time));

            // Inserting Row
            db.insert(TABLE_NOC_ENGG, null, values);
        }
        int count = getCircleCount(TABLE_NOC_ENGG, "", "");
        db.close(); // Closing database connection
    }

    public ArrayList<NocEnggListDataModel> getAllNocEnggData(String enggType) {
        ArrayList<NocEnggListDataModel> arrAllNocEnggData = new ArrayList<>();

        int count = getCircleCount(TABLE_NOC_ENGG, "", "");

        String selectQuery = "SELECT  * FROM " + TABLE_NOC_ENGG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NocEnggListDataModel nocEnggListDataModel = new NocEnggListDataModel();
                nocEnggListDataModel.setEnggId(cursor.getString(cursor.getColumnIndex(NOC_ENGG_ID)));
                nocEnggListDataModel.setEnggName(cursor.getString(cursor.getColumnIndex(NOC_ENGG_NAME)));
                nocEnggListDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(NOC_ENGG_LAST_UPDATED_DATE)));
                nocEnggListDataModel.setEnggContact(cursor.getString(cursor.getColumnIndex(NOC_ENGG_CONTACT)));
                // Adding contact to list
                if (cursor.getString((cursor.getColumnIndex(NOC_ENGG_TYPE))).equals(enggType)) {
                    arrAllNocEnggData.add(nocEnggListDataModel);
                }

            } while (cursor.moveToNext());
        }
        db.close();

        return arrAllNocEnggData;
    }

   /* public ArrayList<NocEnggListDataModel> getSelectedNocEnggData(String userId) {
        ArrayList<NocEnggListDataModel> arrAllNocEnggData = new ArrayList<>();

        int count = getCircleCount(TABLE_NOC_ENGG, "", "");

        String selectQuery = "SELECT " + NOC_ENGG_ID + "FROM " + TABLE_NOC_ENGG + " WHERE " + NOC_ENGG_CONTACT + " = " + userId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NocEnggListDataModel nocEnggListDataModel = new NocEnggListDataModel();
                nocEnggListDataModel.setEnggId(cursor.getString(cursor.getColumnIndex(NOC_ENGG_ID)));
                nocEnggListDataModel.setEnggName(cursor.getString(cursor.getColumnIndex(NOC_ENGG_NAME)));
                nocEnggListDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(NOC_ENGG_LAST_UPDATED_DATE)));
                nocEnggListDataModel.setEnggContact(cursor.getString(cursor.getColumnIndex(NOC_ENGG_CONTACT)));
                // Adding contact to list
                arrAllNocEnggData.add(nocEnggListDataModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrAllNocEnggData;
    }
*/

    //---------------------------------------------------------------------------------------------

    //------------------------------Select Activity in Activity Form-------------------------------

    public void addEquipListData(ArrayList<EquipListDataModel> arrayEquipListEnggData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayEquipListEnggData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(EQUIP_LIST_ID, arrayEquipListEnggData.get(i).getEquipId());
            values.put(EQUIP_LIST_NAME, arrayEquipListEnggData.get(i).getEquipName());
            values.put(EQUIP_LIST_PARENT_ID, arrayEquipListEnggData.get(i).getEquipParentId());
            values.put(EQUIP_LIST_DATE, String.valueOf(time));

            // Inserting Row
            db.insert(TABLE_EQUIP_LIST, null, values);
        }
        //int count = getCircleCount(TABLE_NOC_ENGG, "", "");
        db.close(); // Closing database connection
    }

    public ArrayList<EquipListDataModel> getEquipDataData(String activityType) {
        ArrayList<EquipListDataModel> arrEquipLIstData = new ArrayList<>();

        //int count = getCircleCount(TABLE_EQUIP_LIST, "", "");

        String selectQuery = "SELECT  * FROM " + TABLE_EQUIP_LIST + " WHERE " + EQUIP_LIST_PARENT_ID + " = '" + activityType + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EquipListDataModel equipListDataModel = new EquipListDataModel();
                equipListDataModel.setEquipId(cursor.getString(cursor.getColumnIndex(EQUIP_LIST_ID)));
                equipListDataModel.setEquipName(cursor.getString(cursor.getColumnIndex(EQUIP_LIST_NAME)));
                equipListDataModel.setEquipTime(cursor.getString(cursor.getColumnIndex(EQUIP_LIST_DATE)));
                equipListDataModel.setEquipParentId(cursor.getString(cursor.getColumnIndex(EQUIP_LIST_PARENT_ID)));
                equipListDataModel.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(KEY_ID))));

                arrEquipLIstData.add(equipListDataModel);

            } while (cursor.moveToNext());
        }
        db.close();

        return arrEquipLIstData;
    }


    //-----------------------------Select Task in Activity Form-----------------------------------

    public void addTaskData(ArrayList<TaskListDataModel> arrayListTaskData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayListTaskData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(TASK_ID, arrayListTaskData.get(i).getTaskId());
            values.put(TASK_NAME, arrayListTaskData.get(i).getTaskName());
            values.put(TASK_LAST_UPDATED, String.valueOf(time));

            // Inserting Row
            db.insert(TABLE_TASK, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<TaskListDataModel> getTaskData() {
        ArrayList<TaskListDataModel> arrTaskData = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskListDataModel taskListDataModel = new TaskListDataModel();
                taskListDataModel.setTaskId(cursor.getString(cursor.getColumnIndex("task_id")));
                taskListDataModel.setTaskName(cursor.getString(cursor.getColumnIndex("task_name")));
                taskListDataModel.setTaskLastUpdated(cursor.getString(cursor.getColumnIndex("last_updated")));
                // Adding contact to list
                arrTaskData.add(taskListDataModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrTaskData;
    }

    //------------------------------Select Reason in Activity Form-------------------------------

    public void addReasonData(ArrayList<ReasonListDataModel> arrayListReasonData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayListReasonData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(REASON_ID, arrayListReasonData.get(i).getReasonId());
            values.put(REASON_NAME, arrayListReasonData.get(i).getReasonName());
            values.put(REASON_ACTIVITY_ID, arrayListReasonData.get(i).getActivityId());
            values.put(REASON_LAST_UPDATED_DATE, String.valueOf(time));

            // Inserting Row
            db.insert(TABLE_REASON, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<ReasonListDataModel> getReasonData(String columnValue) {
        ArrayList<ReasonListDataModel> arrAllReasonData = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_REASON + " WHERE " + REASON_ACTIVITY_ID + " = " + columnValue;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReasonListDataModel reasonListDataModel = new ReasonListDataModel();
                reasonListDataModel.setReasonId(cursor.getString(cursor.getColumnIndex(REASON_ID)));
                reasonListDataModel.setReasonName(cursor.getString(cursor.getColumnIndex(REASON_NAME)));
                reasonListDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(REASON_LAST_UPDATED_DATE)));
                // Adding contact to list
                arrAllReasonData.add(reasonListDataModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrAllReasonData;
    }

    //---------------------------------------------------------------------------------------------

    //-------------------------------Activity Form Data--------------------------------------------

    public void addFormData(String[] arrFormData, String isPm) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put(FORM_BATTERY_TERMINAL, arrFormData[45]);
        values.put(FORM_SOLAR_STRUCTURE, arrFormData[46]);
        values.put(FORM_DATE, time);
        values.put(FORM_ACTIVITY_ID, arrFormData[0]);
        values.put(FORM_NOC_ENGG_ID, arrFormData[1]);
        values.put(FORM_REASON, arrFormData[2]);
        values.put(FORM_SITE_ID, arrFormData[3]);
        values.put(FORM_STATUS, arrFormData[4]);
        values.put(FORM_USER_ID, arrFormData[5]);
        values.put(FORM_ZONE, arrFormData[6]);
        values.put(FORM_TASK_ID, arrFormData[7]);
        values.put(FORM_MATERIAL_STATUS, arrFormData[8]);
        values.put(FORM_REMARKS, arrFormData[9]);
        values.put(FORM_NOC_ENGG_CONTACT, arrFormData[10]);
        values.put(FORM_EARTH_VOLT, arrFormData[11]);
        values.put(FORM_BATT_VOLT, arrFormData[12]);
        values.put(FORM_BATT_CELLS, arrFormData[13]);
        values.put(FORM_CHARGER, arrFormData[14]);
        values.put(FORM_INVERTER, arrFormData[15]);
        values.put(FORM_EB_CONN, arrFormData[16]);
        values.put(FORM_CONN, arrFormData[17]);
        values.put(FORM_SOLAR, arrFormData[18]);
        values.put(FORM_SIGN_OFF, arrFormData[19]);
        values.put(FORM_CELL_1, arrFormData[20]);
        values.put(FORM_CELL_2, arrFormData[21]);
        values.put(FORM_CELL_3, arrFormData[22]);
        values.put(FORM_CELL_4, arrFormData[23]);
        values.put(FORM_CELL_5, arrFormData[24]);
        values.put(FORM_CELL_6, arrFormData[25]);
        values.put(FORM_CELL_7, arrFormData[26]);
        values.put(FORM_CELL_8, arrFormData[27]);
        values.put(FORM_CIRCLE_NAME, arrFormData[28]);
        values.put(FORM_CLIENT_NAME, arrFormData[29]);
        values.put(FORM_IS_PLANNED, arrFormData[30]);
        values.put(FORM_PLANNED_DATE, arrFormData[31]);
        values.put(FORM_PLANNED_ID, arrFormData[32]);
        values.put(FORM_LONGITUDE, arrFormData[33]);
        values.put(FORM_LATITUDE, arrFormData[34]);
        values.put(FORM_TASK_NAME, arrFormData[35]);
        values.put(FORM_ACTIVITY_NAME, arrFormData[36]);
        values.put(FORM_STATUS_NAME, arrFormData[37]);
        values.put(FORM_SITE_NAME, arrFormData[38] + " (" + arrFormData[39] + ")");
        values.put(FORM_DA_AMOUNT, arrFormData[40]);
        values.put(FORM_DAY_NUMBER, arrFormData[41]);
        values.put(FORM_IS_PM, isPm);

        // Inserting Row
        db.insert(TABLE_ACTIVITY_FORM, null, values);
        db.close();
    }

    public String[] getFormData() {
        String selectQuery = "SELECT * FROM " + TABLE_ACTIVITY_FORM;

        String[] arrFormOfflineData = new String[40];

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int i = 0;

        String[] columnName = new String[12];

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            //columnName[i] = cursor.getColumnName(i + 1);
            arrFormOfflineData[0] = cursor.getString(cursor.getColumnIndex("form_id"));
            arrFormOfflineData[1] = cursor.getString(cursor.getColumnIndex("form_user_id"));
            arrFormOfflineData[2] = cursor.getString(cursor.getColumnIndex("form_site_id"));
            arrFormOfflineData[3] = cursor.getString(cursor.getColumnIndex("form_activty_id"));
            arrFormOfflineData[4] = cursor.getString(cursor.getColumnIndex("form_noc_engg_id"));
            arrFormOfflineData[5] = cursor.getString(cursor.getColumnIndex("form_status"));
            arrFormOfflineData[6] = cursor.getString(cursor.getColumnIndex("form_reason"));
            arrFormOfflineData[7] = cursor.getString(cursor.getColumnIndex("form_zone"));
            arrFormOfflineData[8] = cursor.getString(cursor.getColumnIndex(FORM_TASK_ID));
            arrFormOfflineData[9] = cursor.getString(cursor.getColumnIndex(FORM_MATERIAL_STATUS));
            arrFormOfflineData[10] = cursor.getString(cursor.getColumnIndex(FORM_REMARKS));
            arrFormOfflineData[11] = cursor.getString(cursor.getColumnIndex(FORM_NOC_ENGG_CONTACT));
            arrFormOfflineData[12] = cursor.getString(cursor.getColumnIndex(FORM_EARTH_VOLT));
            arrFormOfflineData[13] = cursor.getString(cursor.getColumnIndex(FORM_BATT_VOLT));
            arrFormOfflineData[14] = cursor.getString(cursor.getColumnIndex(FORM_BATT_CELLS));
            arrFormOfflineData[15] = cursor.getString(cursor.getColumnIndex(FORM_CHARGER));
            arrFormOfflineData[16] = cursor.getString(cursor.getColumnIndex(FORM_INVERTER));
            arrFormOfflineData[17] = cursor.getString(cursor.getColumnIndex(FORM_EB_CONN));
            arrFormOfflineData[18] = cursor.getString(cursor.getColumnIndex(FORM_CONN));
            arrFormOfflineData[19] = cursor.getString(cursor.getColumnIndex(FORM_SOLAR));
            arrFormOfflineData[20] = cursor.getString(cursor.getColumnIndex(FORM_SIGN_OFF));
            arrFormOfflineData[21] = cursor.getString(cursor.getColumnIndex(FORM_CELL_1));
            arrFormOfflineData[22] = cursor.getString(cursor.getColumnIndex(FORM_CELL_2));
            arrFormOfflineData[23] = cursor.getString(cursor.getColumnIndex(FORM_CELL_3));
            arrFormOfflineData[24] = cursor.getString(cursor.getColumnIndex(FORM_CELL_4));
            arrFormOfflineData[25] = cursor.getString(cursor.getColumnIndex(FORM_CELL_5));
            arrFormOfflineData[26] = cursor.getString(cursor.getColumnIndex(FORM_CELL_6));
            arrFormOfflineData[27] = cursor.getString(cursor.getColumnIndex(FORM_CELL_7));
            arrFormOfflineData[28] = cursor.getString(cursor.getColumnIndex(FORM_CELL_8));
            arrFormOfflineData[29] = cursor.getString(cursor.getColumnIndex(FORM_CIRCLE_NAME));
            arrFormOfflineData[30] = cursor.getString(cursor.getColumnIndex(FORM_CLIENT_NAME));
            arrFormOfflineData[31] = cursor.getString(cursor.getColumnIndex(FORM_IS_PLANNED));
            arrFormOfflineData[32] = cursor.getString(cursor.getColumnIndex(FORM_PLANNED_DATE));
            arrFormOfflineData[33] = cursor.getString(cursor.getColumnIndex(FORM_DATE));
            arrFormOfflineData[34] = cursor.getString(cursor.getColumnIndex(FORM_PLANNED_ID));
            arrFormOfflineData[35] = cursor.getString(cursor.getColumnIndex(FORM_LONGITUDE));
            arrFormOfflineData[36] = cursor.getString(cursor.getColumnIndex(FORM_LATITUDE));
            arrFormOfflineData[37] = cursor.getString(cursor.getColumnIndex(FORM_DA_AMOUNT));
            arrFormOfflineData[38] = cursor.getString(cursor.getColumnIndex(FORM_DAY_NUMBER));
            arrFormOfflineData[39] = cursor.getString(cursor.getColumnIndex(FORM_IS_PM));

        }
        db.close();
        return arrFormOfflineData;
    }

    public ArrayList<ActivityFormDataModel> getAllFormData() {
        String selectQuery = "SELECT * FROM " + TABLE_ACTIVITY_FORM;

        String[] arrFormOfflineData = new String[37];

        ActivityFormDataModel activityFormDataModel;
        ArrayList<ActivityFormDataModel> arrListFormData = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int i = 0;

        String[] columnName = new String[12];

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                activityFormDataModel = new ActivityFormDataModel();

                activityFormDataModel.setId(cursor.getString(cursor.getColumnIndex("form_id")));
                activityFormDataModel.setUserId(cursor.getString(cursor.getColumnIndex("form_user_id")));
                activityFormDataModel.setSiteId(cursor.getString(cursor.getColumnIndex("form_site_id")));
                activityFormDataModel.setActivityId(cursor.getString(cursor.getColumnIndex("form_activty_id")));
                activityFormDataModel.setNocEnggId(cursor.getString(cursor.getColumnIndex("form_noc_engg_id")));
                activityFormDataModel.setReason(cursor.getString(cursor.getColumnIndex("form_reason")));
                activityFormDataModel.setZone(cursor.getString(cursor.getColumnIndex("form_zone")));
                activityFormDataModel.setTaskId(cursor.getString(cursor.getColumnIndex(FORM_TASK_ID)));
                activityFormDataModel.setMaterialStatus(cursor.getString(cursor.getColumnIndex(FORM_MATERIAL_STATUS)));
                activityFormDataModel.setRemarks(cursor.getString(cursor.getColumnIndex(FORM_REMARKS)));
                activityFormDataModel.setNocEnggContact(cursor.getString(cursor.getColumnIndex(FORM_NOC_ENGG_CONTACT)));
                activityFormDataModel.setEarthVolt(cursor.getString(cursor.getColumnIndex(FORM_EARTH_VOLT)));
                activityFormDataModel.setBattVolt(cursor.getString(cursor.getColumnIndex(FORM_BATT_VOLT)));
                activityFormDataModel.setBattCells(cursor.getString(cursor.getColumnIndex(FORM_BATT_CELLS)));
                activityFormDataModel.setCharger(cursor.getString(cursor.getColumnIndex(FORM_CHARGER)));
                activityFormDataModel.setInverter(cursor.getString(cursor.getColumnIndex(FORM_INVERTER)));
                activityFormDataModel.setEbConn(cursor.getString(cursor.getColumnIndex(FORM_EB_CONN)));
                activityFormDataModel.setConn(cursor.getString(cursor.getColumnIndex(FORM_CONN)));
                activityFormDataModel.setSolar(cursor.getString(cursor.getColumnIndex(FORM_SOLAR)));
                activityFormDataModel.setSignOff(cursor.getString(cursor.getColumnIndex(FORM_SIGN_OFF)));
                activityFormDataModel.setCell1(cursor.getString(cursor.getColumnIndex(FORM_CELL_1)));
                activityFormDataModel.setCell2(cursor.getString(cursor.getColumnIndex(FORM_CELL_2)));
                activityFormDataModel.setCell3(cursor.getString(cursor.getColumnIndex(FORM_CELL_3)));
                activityFormDataModel.setCell4(cursor.getString(cursor.getColumnIndex(FORM_CELL_4)));
                activityFormDataModel.setCell5(cursor.getString(cursor.getColumnIndex(FORM_CELL_5)));
                activityFormDataModel.setCell6(cursor.getString(cursor.getColumnIndex(FORM_CELL_6)));
                activityFormDataModel.setCell7(cursor.getString(cursor.getColumnIndex(FORM_CELL_7)));
                activityFormDataModel.setCell8(cursor.getString(cursor.getColumnIndex(FORM_CELL_8)));
                activityFormDataModel.setCircleName(cursor.getString(cursor.getColumnIndex(FORM_CIRCLE_NAME)));
                activityFormDataModel.setClientName(cursor.getString(cursor.getColumnIndex(FORM_CLIENT_NAME)));
                activityFormDataModel.setIsPlanned(cursor.getString(cursor.getColumnIndex(FORM_IS_PLANNED)));
                activityFormDataModel.setPlannedDate(cursor.getString(cursor.getColumnIndex(FORM_PLANNED_DATE)));
                activityFormDataModel.setDate(cursor.getString(cursor.getColumnIndex(FORM_DATE)));
                activityFormDataModel.setPlannedId(cursor.getString(cursor.getColumnIndex(FORM_PLANNED_ID)));
                activityFormDataModel.setLongitude(cursor.getString(cursor.getColumnIndex(FORM_LONGITUDE)));
                activityFormDataModel.setLatitude(cursor.getString(cursor.getColumnIndex(FORM_LATITUDE)));
                activityFormDataModel.setTaskName(cursor.getString(cursor.getColumnIndex(FORM_TASK_NAME)));
                activityFormDataModel.setActivityName(cursor.getString(cursor.getColumnIndex(FORM_ACTIVITY_NAME)));
                activityFormDataModel.setStatusName(cursor.getString(cursor.getColumnIndex(FORM_STATUS_NAME)));
                activityFormDataModel.setSiteName(cursor.getString(cursor.getColumnIndex(FORM_SITE_NAME)));
                activityFormDataModel.setBatteryTerminal(cursor.getString(cursor.getColumnIndex(FORM_BATTERY_TERMINAL)));
                activityFormDataModel.setSolarStructure(cursor.getString(cursor.getColumnIndex(FORM_SOLAR_STRUCTURE)));

                arrListFormData.add(activityFormDataModel);

            } while (cursor.moveToNext());
        }
        db.close();
        return arrListFormData;
    }

    public void deleteFormData(int formId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACTIVITY_FORM, FORM_ID + "=" + formId,
                null);
        db.close();
    }

    public void deleteComplaintData(int complaintId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPLAINTS, KEY_ID + "=" + complaintId,
                null);
        db.close();
    }

    public void deleteAllFormData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACTIVITY_FORM, null,
                null);
        db.close();
    }

    //------------------------------Save Transit Screen Data-------------------------------

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

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TransitDataModel transitDataModel = new TransitDataModel();
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
                // Adding contact to list
                arrTransitData.add(transitDataModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrTransitData;
    }

    //---------------------------------------------------------------------------------------------

    //------------------------------Save Customer Data-------------------------------

    public void addCustomerData(ArrayList<CustomerListDataModel> arrayCustomerData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayCustomerData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(CUSTOMER_ID, arrayCustomerData.get(i).getCustomerId());
            values.put(CUSTOMER_NAME, arrayCustomerData.get(i).getCustomerName());
            values.put(CUSTOMER_LAST_UPDATED_DATE, arrayCustomerData.get(i).getLastUpdatedDate());

            // Inserting Row
            db.insert(TABLE_CUSTOMER, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<CustomerListDataModel> getCustomerData() {
        ArrayList<CustomerListDataModel> arrCustomerData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CUSTOMER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerListDataModel customerListDataModel = new CustomerListDataModel();
                customerListDataModel.setCustomerId(cursor.getString(cursor.getColumnIndex(CUSTOMER_ID)));
                customerListDataModel.setCustomerName(cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME)));
                customerListDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(CUSTOMER_LAST_UPDATED_DATE)));

                // Adding contact to list
                arrCustomerData.add(customerListDataModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrCustomerData;
    }

    //---------------------------------------------------------------------------------------------

    //------------------------------Save Pending Activity Data-------------------------------

    public void addPendingActivityData(ArrayList<PlannedActivityListModel> arrayPlannedListData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayPlannedListData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(PLANNED_ACTIVITY_ID, arrayPlannedListData.get(i).getActivityId());
            values.put(PLANNED_EXECUTED, arrayPlannedListData.get(i).getExecuted());
            values.put(PLANNED_ACTIVITY_ON_THE_WAY, arrayPlannedListData.get(i).getOnTheWay());
            values.put(PLANNED_REACHED_SITE, arrayPlannedListData.get(i).getReachedSite());
            values.put(PLANNED_LEFT_SITE, arrayPlannedListData.get(i).getLeftSite());
            values.put(PLANNED_UNKNOWN, arrayPlannedListData.get(i).getUnknown());
            values.put(PLANNED_ATTENDANCE, arrayPlannedListData.get(i).getAttendance());
            values.put(PLANNED_SITE_NAME, arrayPlannedListData.get(i).getSiteName());
            values.put(PLANNED_NUM_SITE_ID, arrayPlannedListData.get(i).getSiteNumId());
            values.put(PLANNED_DATE, arrayPlannedListData.get(i).getPlannedDate());
            values.put(PLANNED_SITE_ID, arrayPlannedListData.get(i).getSiteId());
            values.put(PLANNED_ACTIVITY, arrayPlannedListData.get(i).getPlannedActivity());
            values.put(PLANNED_FE_name, arrayPlannedListData.get(i).getFeName());
            values.put(PLANNED_LAST_UPDATED_DATE, arrayPlannedListData.get(i).getLastUpdatedDate());
            values.put(PLANNED_PLAN_ID, arrayPlannedListData.get(i).getPlanId());
            values.put(PLAN_ACTIVITY_ID, arrayPlannedListData.get(i).getActivityId());
            values.put(PLANNED_MARKETING_DISTRIBUTOR, arrayPlannedListData.get(i).getMarketingDistributor());
            values.put(PLANNED_TASK_ID, arrayPlannedListData.get(i).getTaskId());
            values.put(PLANNED_CIRCLE_NAME, arrayPlannedListData.get(i).getCircleName());
            values.put(PLANNED_CIRCLE_ID, arrayPlannedListData.get(i).getCircleId());
            values.put(PLANNED_FE_ID, arrayPlannedListData.get(i).getFeId());
            values.put(PLANNED_COMPLAINT_MESSAGE, arrayPlannedListData.get(i).getComplaintMessages());
            values.put(PLANNED_ACTIVITY_MILLI, arrayPlannedListData.get(i).getDateMilli());
            values.put(PLANNED_ACTIVITY_LEAVE_COUNT, arrayPlannedListData.get(i).getLeave());
            values.put(PLANNED_ACTIVITY_SUBMITTED_OFFLINE, arrayPlannedListData.get(i).getSubmittedOffline());

            // Inserting Row
            db.insert(TABLE_PLANNED_ACTIVITY, null, values);
        }
        db.close(); // Closing database connection
    }

    public void updatePendingActivity(String planId) {
        //String selectQuery = "UPDATE "+ TABLE_PLANNED_ACTIVITY + " SET " + PLANNED_ACTIVITY_SUBMITTED_OFFLINE + " = '1' WHERE "+ PLANNED_ACTIVITY_ID + " = " + activityId;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PLANNED_ACTIVITY_SUBMITTED_OFFLINE, "1");

        db.update(TABLE_PLANNED_ACTIVITY, cv, PLANNED_PLAN_ID + "=" + planId, null);
    }

    public ArrayList<PlannedActivityListModel> getPendingActivityData() {
        ArrayList<PlannedActivityListModel> arrPendingActivityData = new ArrayList<>();

        //String selectQuery = "SELECT * FROM " + TABLE_PLANNED_ACTIVITY + " WHERE " + PLANNED_ACTIVITY_SUBMITTED_OFFLINE + " = '0'";
        //String selectQuery = "SELECT * FROM " + TABLE_PLANNED_ACTIVITY + " WHERE " + PLANNED_ACTIVITY_SUBMITTED_OFFLINE + " = '0'";
        String selectQuery = "SELECT * FROM " + TABLE_PLANNED_ACTIVITY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //arrPendingActivityData = new ArrayList<>();
                PlannedActivityListModel plannedActivityListModel = new PlannedActivityListModel();
                plannedActivityListModel.setExecuted(cursor.getString(cursor.getColumnIndex(PLANNED_EXECUTED)));
                plannedActivityListModel.setOnTheWay(cursor.getString(cursor.getColumnIndex(PLANNED_ACTIVITY_ON_THE_WAY)));
                plannedActivityListModel.setReachedSite(cursor.getString(cursor.getColumnIndex(PLANNED_REACHED_SITE)));
                plannedActivityListModel.setLeftSite(cursor.getString(cursor.getColumnIndex(PLANNED_LEFT_SITE)));
                plannedActivityListModel.setUnknown(cursor.getString(cursor.getColumnIndex(PLANNED_UNKNOWN)));
                plannedActivityListModel.setAttendance(cursor.getString(cursor.getColumnIndex(PLANNED_ATTENDANCE)));
                plannedActivityListModel.setSiteName(cursor.getString(cursor.getColumnIndex(PLANNED_SITE_NAME)));
                plannedActivityListModel.setSiteNumId(cursor.getString(cursor.getColumnIndex(PLANNED_NUM_SITE_ID)));
                plannedActivityListModel.setPlannedDate(cursor.getString(cursor.getColumnIndex(PLANNED_DATE)));
                plannedActivityListModel.setSiteId(cursor.getString(cursor.getColumnIndex(PLANNED_SITE_ID)));
                plannedActivityListModel.setPlannedActivity(cursor.getString(cursor.getColumnIndex(PLANNED_ACTIVITY)));
                plannedActivityListModel.setFeName(cursor.getString(cursor.getColumnIndex(PLANNED_FE_name)));
                plannedActivityListModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(PLANNED_LAST_UPDATED_DATE)));
                plannedActivityListModel.setPlanId(cursor.getString(cursor.getColumnIndex(PLANNED_PLAN_ID)));
                plannedActivityListModel.setActivityId(cursor.getString(cursor.getColumnIndex(PLANNED_ACTIVITY_ID)));
                plannedActivityListModel.setMarketingDistributor(cursor.getString(cursor.getColumnIndex(PLANNED_MARKETING_DISTRIBUTOR)));
                plannedActivityListModel.setTaskId(cursor.getString(cursor.getColumnIndex(PLANNED_TASK_ID)));
                plannedActivityListModel.setCircleName(cursor.getString(cursor.getColumnIndex(PLANNED_CIRCLE_NAME)));
                plannedActivityListModel.setCircleId(cursor.getString(cursor.getColumnIndex(PLANNED_CIRCLE_ID)));
                plannedActivityListModel.setFeId(cursor.getString(cursor.getColumnIndex(PLANNED_FE_ID)));
                plannedActivityListModel.setComplaintMessages(cursor.getString(cursor.getColumnIndex(PLANNED_COMPLAINT_MESSAGE)));
                plannedActivityListModel.setDateMilli(cursor.getString(cursor.getColumnIndex(PLANNED_ACTIVITY_MILLI)));
                plannedActivityListModel.setLeave(cursor.getString(cursor.getColumnIndex(PLANNED_ACTIVITY_LEAVE_COUNT)));
                plannedActivityListModel.setSubmittedOffline(cursor.getString(cursor.getColumnIndex(PLANNED_ACTIVITY_SUBMITTED_OFFLINE)));

                // Adding contact to list
                arrPendingActivityData.add(plannedActivityListModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrPendingActivityData;
    }

    public long getPendingActivityLastUpdatedDate() {

        long lastUpdatedDate = 0;
        String selectQuery = "SELECT " + PLANNED_LAST_UPDATED_DATE + " FROM " + TABLE_PLANNED_ACTIVITY + " LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            lastUpdatedDate = Long.parseLong(cursor.getString(cursor.getColumnIndex(PLANNED_LAST_UPDATED_DATE)));
        }

        return lastUpdatedDate;
    }

   /* public static void deletePendingActivityListData(){
        String selectQuery = "DELETE FROM " + TABLE_PLANNED_ACTIVITY;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANNED_ACTIVITY, selectQuery, null);
        db.close();
    }*/

    //---------------------------------------------------------------------------------------------

    //------------------------------Save Executed Activity Data-------------------------------

    public void addExecutedActivityData(ArrayList<ExecutedActivityListModel> arrayExecutedListData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayExecutedListData.size(); i++) {
            ContentValues values = new ContentValues();
            //values.put(PLANNED_ACTIVITY_ID, arrayPlannedListData.get(i).getCustomerId());
            values.put(EXECUTED_EXECUTED, arrayExecutedListData.get(i).getExecuted());
            values.put(EXECUTED_ACTIVITY_ON_THE_WAY, arrayExecutedListData.get(i).getOnTheWay());
            values.put(EXECUTED_REACHED_SITE, arrayExecutedListData.get(i).getReachedSite());
            values.put(EXECUTED_LEFT_SITE, arrayExecutedListData.get(i).getLeftSite());
            values.put(EXECUTED_UNKNOWN, arrayExecutedListData.get(i).getUnknown());
            values.put(EXECUTED_ATTENDANCE, arrayExecutedListData.get(i).getAttendance());
            values.put(EXECUTED_SITE_NAME, arrayExecutedListData.get(i).getSiteName());
            values.put(EXECUTED_CUSTOMER, arrayExecutedListData.get(i).getCustomer());
            values.put(EXECUTED_DATE, arrayExecutedListData.get(i).getActivityDate());
            values.put(EXECUTED_TIME, arrayExecutedListData.get(i).getActivityTime());
            values.put(EXECUTED_ZONE, arrayExecutedListData.get(i).getZoneType());
            values.put(EXECUTED_TOTAL_AMOUNT, arrayExecutedListData.get(i).getTotalAmount());
            values.put(EXECUTED_TA_DA_AMOUNT, arrayExecutedListData.get(i).getTaDaAmt());
            values.put(EXECUTED_STATUS, arrayExecutedListData.get(i).getStatus());
            values.put(EXECUTED_DAYS_TAKEN, arrayExecutedListData.get(i).getDays());
            values.put(EXECUTED_NOC_APPROVED_COLOR, arrayExecutedListData.get(i).getColor());
            values.put(EXECUTED_ACTIVITY, arrayExecutedListData.get(i).getActivity());
            values.put(EXECUTED_NOC_APPROVAL, arrayExecutedListData.get(i).getNocApprovel());
            values.put(EXECUTED_BONUS, arrayExecutedListData.get(i).getBonus());
            values.put(EXECUTED_PENALTY, arrayExecutedListData.get(i).getPenalty());
            values.put(EXECUTED_FE_NAME, arrayExecutedListData.get(i).getFeName());
            values.put(EXECUTED_FE_ID, arrayExecutedListData.get(i).getFeId());
            values.put(EXECUTED_LAST_UPDATED_DATE, String.valueOf(System.currentTimeMillis()));
            values.put(EXECUTED_LEAVE_COUNT, arrayExecutedListData.get(i).getLeave());
            values.put(EXECUTED_CIRCLE_NAME, arrayExecutedListData.get(i).getCircle());

            // Inserting Row
            db.insert(TABLE_EXECUTED_ACTIVITY, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<ExecutedActivityListModel> getExecutedActivityData() {
        ArrayList<ExecutedActivityListModel> arrExecutedActivityData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_EXECUTED_ACTIVITY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //arrExecutedActivityData = new ArrayList<>();
                ExecutedActivityListModel executedActivityListModel = new ExecutedActivityListModel();
                executedActivityListModel.setExecuted(cursor.getString(cursor.getColumnIndex(EXECUTED_EXECUTED)));
                executedActivityListModel.setOnTheWay(cursor.getString(cursor.getColumnIndex(EXECUTED_ACTIVITY_ON_THE_WAY)));
                executedActivityListModel.setReachedSite(cursor.getString(cursor.getColumnIndex(EXECUTED_REACHED_SITE)));
                executedActivityListModel.setLeftSite(cursor.getString(cursor.getColumnIndex(EXECUTED_LEFT_SITE)));
                executedActivityListModel.setUnknown(cursor.getString(cursor.getColumnIndex(EXECUTED_UNKNOWN)));
                executedActivityListModel.setAttendance(cursor.getString(cursor.getColumnIndex(EXECUTED_ATTENDANCE)));
                executedActivityListModel.setSiteName(cursor.getString(cursor.getColumnIndex(EXECUTED_SITE_NAME)));
                executedActivityListModel.setCustomer(cursor.getString(cursor.getColumnIndex(EXECUTED_CUSTOMER)));
                executedActivityListModel.setActivityDate(cursor.getString(cursor.getColumnIndex(EXECUTED_DATE)));
                executedActivityListModel.setActivityTime(cursor.getString(cursor.getColumnIndex(EXECUTED_TIME)));
                executedActivityListModel.setZoneType(cursor.getString(cursor.getColumnIndex(EXECUTED_ZONE)));
                executedActivityListModel.setTotalAmount(cursor.getString(cursor.getColumnIndex(EXECUTED_TOTAL_AMOUNT)));
                executedActivityListModel.setTaDaAmt(cursor.getString(cursor.getColumnIndex(EXECUTED_TA_DA_AMOUNT)));
                executedActivityListModel.setStatus(cursor.getString(cursor.getColumnIndex(EXECUTED_STATUS)));
                executedActivityListModel.setActivity(cursor.getString(cursor.getColumnIndex(EXECUTED_ACTIVITY)));
                executedActivityListModel.setDays(cursor.getString(cursor.getColumnIndex(EXECUTED_DAYS_TAKEN)));
                executedActivityListModel.setColor(cursor.getString(cursor.getColumnIndex(EXECUTED_NOC_APPROVED_COLOR)));
                executedActivityListModel.setActivity(cursor.getString(cursor.getColumnIndex(EXECUTED_ACTIVITY)));
                executedActivityListModel.setNocApprovel(cursor.getString(cursor.getColumnIndex(EXECUTED_NOC_APPROVAL)));
                executedActivityListModel.setBonus(cursor.getString(cursor.getColumnIndex(EXECUTED_BONUS)));
                executedActivityListModel.setPenalty(cursor.getString(cursor.getColumnIndex(EXECUTED_PENALTY)));
                executedActivityListModel.setFeName(cursor.getString(cursor.getColumnIndex(EXECUTED_FE_NAME)));
                executedActivityListModel.setFeId(cursor.getString(cursor.getColumnIndex(EXECUTED_FE_ID)));
                executedActivityListModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(EXECUTED_LAST_UPDATED_DATE)));
                executedActivityListModel.setLeave(cursor.getString(cursor.getColumnIndex(EXECUTED_LEAVE_COUNT)));
                executedActivityListModel.setCircle(cursor.getString(cursor.getColumnIndex(EXECUTED_CIRCLE_NAME)));

                // Adding contact to list
                arrExecutedActivityData.add(executedActivityListModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrExecutedActivityData;
    }

    public long getExecutedActivityLastUpdatedDate() {

        long lastUpdatedDate = 0;
        String selectQuery = "SELECT " + EXECUTED_LAST_UPDATED_DATE + " FROM " + TABLE_EXECUTED_ACTIVITY + " LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            lastUpdatedDate = Long.parseLong(cursor.getString(cursor.getColumnIndex(EXECUTED_LAST_UPDATED_DATE)));
        }

        return lastUpdatedDate;
    }

    //---------------------------------------------------------------------------------------------

    //--------------------------------Display Expense Screen ---------------------------------------

    //------------------------------Save Executed Activity Data-------------------------------

    public void addDisplayExpenseScreenData(ArrayList<DisplayExpenseDataModel> arrayExecutedListData) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayExecutedListData.size(); i++) {
            ContentValues values = new ContentValues();
            //values.put(PLANNED_ACTIVITY_ID, arrayPlannedListData.get(i).getCustomerId());
            values.put(DISPLAY_EXPENSE_SITE_ID, arrayExecutedListData.get(i).getSiteId());
            values.put(DISPLAY_EXPENSE_SITE_NAME, arrayExecutedListData.get(i).getSiteName());
            values.put(DISPLAY_EXPENSE_SUBMITTED_DATE, arrayExecutedListData.get(i).getSubmittedDate());
            values.put(DISPLAY_EXPENSE_TOTAL_EXPENSE, arrayExecutedListData.get(i).getTotalExpense());
            values.put(DISPLAY_EXPENSE_TA_EXPENSE, arrayExecutedListData.get(i).getTaExpense());
            values.put(DISPLAY_EXPENSE_DA_EXPENSE, arrayExecutedListData.get(i).getDaExpense());
            values.put(DISPLAY_EXPENSE_HOTEL_EXPENSE, arrayExecutedListData.get(i).getHotelExpense());
            values.put(DISPLAY_EXPENSE_OTHER_EXPENSE, arrayExecutedListData.get(i).getOtherExpense());
            values.put(DISPLAY_EXPENSE_HOME_TO_SITE_KM, arrayExecutedListData.get(i).getHomeToSiteKms());
            values.put(DISPLAY_EXPENSE_HOME_TO_SITE_EXPENSE, arrayExecutedListData.get(i).getHomeToSiteExpense());
            values.put(DISPLAY_EXPENSE_HOME_TO_SITE_APPROVAL, arrayExecutedListData.get(i).getSiteToSiteApproval());
            values.put(DISPLAY_EXPENSE_SITE_TO_SITE_KM, arrayExecutedListData.get(i).getSiteToSiteKms());
            values.put(DISPLAY_EXPENSE_SITE_TO_SITE_APPROVAL, arrayExecutedListData.get(i).getSiteToSiteApproval());
            values.put(DISPLAY_EXPENSE_SITE_TO_SITE_EXPENSE, arrayExecutedListData.get(i).getSiteToSiteExpense());
            values.put(DISPLAY_EXPENSE_DA_DAY, arrayExecutedListData.get(i).getDaDay());
            values.put(DISPLAY_EXPENSE_DA_STATUS, arrayExecutedListData.get(i).getDaStatus());
            values.put(DISPLAY_EXPENSE_HOTEL_DAY, arrayExecutedListData.get(i).getHotelDay());
            values.put(DISPLAY_EXPENSE_HOTEL_STATUS, arrayExecutedListData.get(i).getHotelStatus());
            values.put(DISPLAY_EXPENSE_OTHER_STATUS, arrayExecutedListData.get(i).getOtherStatus());
            values.put(DISPLAY_EXPENSE_LAST_UPDATED_DATE, String.valueOf(System.currentTimeMillis()));

            // Inserting Row
            db.insert(TABLE_EXPENSE_DISPLAY, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<DisplayExpenseDataModel> getExpenseDisplayData() {
        ArrayList<DisplayExpenseDataModel> arrExpenseDisplayData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_EXPENSE_DISPLAY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //arrExecutedActivityData = new ArrayList<>();
                DisplayExpenseDataModel displayExpenseDataModel = new DisplayExpenseDataModel();
                displayExpenseDataModel.setSiteId(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_SITE_ID)));
                displayExpenseDataModel.setSiteName(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_SITE_NAME)));
                displayExpenseDataModel.setSubmittedDate(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_SUBMITTED_DATE)));
                displayExpenseDataModel.setTotalExpense(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_TOTAL_EXPENSE)));
                displayExpenseDataModel.setTaExpense(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_TA_EXPENSE)));
                displayExpenseDataModel.setDaExpense(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_DA_EXPENSE)));
                displayExpenseDataModel.setHotelExpense(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_HOTEL_EXPENSE)));
                displayExpenseDataModel.setOtherExpense(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_OTHER_EXPENSE)));
                displayExpenseDataModel.setHomeToSiteKms(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_HOME_TO_SITE_KM)));
                displayExpenseDataModel.setHomeToSiteExpense(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_HOME_TO_SITE_EXPENSE)));
                displayExpenseDataModel.setHomeToSiteApproval(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_HOME_TO_SITE_APPROVAL)));
                displayExpenseDataModel.setSiteToSiteKms(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_SITE_TO_SITE_KM)));
                displayExpenseDataModel.setSiteToSiteExpense(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_SITE_TO_SITE_EXPENSE)));
                displayExpenseDataModel.setSiteToSiteApproval(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_SITE_TO_SITE_APPROVAL)));
                displayExpenseDataModel.setDaDay(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_DA_DAY)));
                displayExpenseDataModel.setDaStatus(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_DA_STATUS)));
                displayExpenseDataModel.setHotelDay(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_HOTEL_DAY)));
                displayExpenseDataModel.setHotelStatus(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_HOTEL_STATUS)));
                displayExpenseDataModel.setOtherStatus(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_OTHER_STATUS)));
                displayExpenseDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(DISPLAY_EXPENSE_LAST_UPDATED_DATE)));

                // Adding contact to list
                arrExpenseDisplayData.add(displayExpenseDataModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrExpenseDisplayData;
    }

    //----------------------------------------------------------------------------------------------

    //------------------------------Save Site Address Activity Data-------------------------------

    public void addSiteAddress(ArrayList<FillSiteActivityModel> arraySiteAddress) {
        SQLiteDatabase db = this.getWritableDatabase();

        long time = System.currentTimeMillis();
        for (int i = 0; i < arraySiteAddress.size(); i++) {
            ContentValues values = new ContentValues();
            //values.put(PLANNED_ACTIVITY_ID, arrayPlannedListData.get(i).getCustomerId());
            values.put(FILL_SITE_ID, arraySiteAddress.get(i).getSiteId());
            values.put(FILL_CUSTOMER_SITE_ID, arraySiteAddress.get(i).getCustomerSiteId());
            values.put(FILL_SITE_NAME, arraySiteAddress.get(i).getSiteName());
            values.put(FILL_BRANCH_NAME, arraySiteAddress.get(i).getBranchName());
            values.put(FILL_BRANCH_CODE, arraySiteAddress.get(i).getBranchCode());
            values.put(FILL_ON_OFF_SITE, arraySiteAddress.get(i).getOnOffSite());
            values.put(FILL_ADDRESS, arraySiteAddress.get(i).getAddress());
            values.put(FILL_CITY, arraySiteAddress.get(i).getCity());
            values.put(FILL_SAVE_CIRCLE_ID, arraySiteAddress.get(i).getCircleId());
            values.put(FILL_SAVE_DISTRICT_ID, arraySiteAddress.get(i).getDistrictId());
            values.put(FILL_SAVE_TEHSIL_ID, arraySiteAddress.get(i).getTehsilId());
            values.put(FILL_PINCODE, arraySiteAddress.get(i).getPincode());
            values.put(FILL_LONG, arraySiteAddress.get(i).getLon());
            values.put(FILL_LAT, arraySiteAddress.get(i).getLat());
            values.put(FILL_START_TIME, arraySiteAddress.get(i).getFunctionalFromTime());
            values.put(FILL_END_TIME, arraySiteAddress.get(i).getFunctionalToTime());
            // Inserting Row
            db.insert(TABLE_FILL_SITE_DATA, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<FillSiteActivityModel> getSiteAddress() {
        ArrayList<FillSiteActivityModel> arrSiteAddressActivityData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_FILL_SITE_DATA + " LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //arrExecutedActivityData = new ArrayList<>();
                FillSiteActivityModel fillSiteActivityListModel = new FillSiteActivityModel();
                fillSiteActivityListModel.setSiteId(cursor.getString(cursor.getColumnIndex(FILL_SITE_ID)));
                fillSiteActivityListModel.setCustomerSiteId(cursor.getString(cursor.getColumnIndex(FILL_CUSTOMER_SITE_ID)));
                fillSiteActivityListModel.setSiteName(cursor.getString(cursor.getColumnIndex(FILL_SITE_NAME)));
                fillSiteActivityListModel.setBranchName(cursor.getString(cursor.getColumnIndex(FILL_BRANCH_NAME)));
                fillSiteActivityListModel.setBranchCode(cursor.getString(cursor.getColumnIndex(FILL_BRANCH_CODE)));
                fillSiteActivityListModel.setOnOffSite(cursor.getString(cursor.getColumnIndex(FILL_ON_OFF_SITE)));
                fillSiteActivityListModel.setAddress(cursor.getString(cursor.getColumnIndex(FILL_ADDRESS)));
                fillSiteActivityListModel.setCity(cursor.getString(cursor.getColumnIndex(FILL_CITY)));
                fillSiteActivityListModel.setCircleId(cursor.getString(cursor.getColumnIndex(FILL_SAVE_CIRCLE_ID)));
                fillSiteActivityListModel.setDistrictId(cursor.getString(cursor.getColumnIndex(FILL_SAVE_DISTRICT_ID)));
                fillSiteActivityListModel.setTehsilId(cursor.getString(cursor.getColumnIndex(FILL_SAVE_TEHSIL_ID)));
                fillSiteActivityListModel.setPincode(cursor.getString(cursor.getColumnIndex(FILL_PINCODE)));
                fillSiteActivityListModel.setLat(cursor.getString(cursor.getColumnIndex(FILL_LAT)));
                fillSiteActivityListModel.setLon(cursor.getString(cursor.getColumnIndex(FILL_LONG)));
                fillSiteActivityListModel.setFunctionalFromTime(cursor.getString(cursor.getColumnIndex(FILL_START_TIME)));
                fillSiteActivityListModel.setFunctionalToTime(cursor.getString(cursor.getColumnIndex(FILL_END_TIME)));
                fillSiteActivityListModel.setSiteAddressId(cursor.getString(cursor.getColumnIndex("id")));

                // Adding contact to list
                arrSiteAddressActivityData.add(fillSiteActivityListModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrSiteAddressActivityData;
    }

    //------------------------------Save State, District and Tehsil Activity Data-------------------------------

    public void addStateData(ArrayList<StateModel> arrayState, ArrayList<DistrictModel> arrDistrict, ArrayList<TehsilModel> arrTehsil) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        long time = System.currentTimeMillis();
        for (int i = 0; i < arrayState.size(); i++) {
            ContentValues values = new ContentValues();
            //values.put(PLANNED_ACTIVITY_ID, arrayPlannedListData.get(i).getCustomerId());
            values.put(STATE_NAME, arrayState.get(i).getStateName());
            values.put(STATE_ID, arrayState.get(i).getStateId());
            // Inserting Row
            db.insert(TABLE_STATE, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.beginTransaction();
        for (int i = 0; i < arrDistrict.size(); i++) {
            ContentValues values = new ContentValues();
            //values.put(PLANNED_ACTIVITY_ID, arrayPlannedListData.get(i).getCustomerId());
            values.put(DISTRICT_NAME, arrDistrict.get(i).getDistrictName());
            values.put(DISTRICT_SN, arrDistrict.get(i).getStateId());
            values.put(DISTRICT_ID, arrDistrict.get(i).getDistrictId());
            // Inserting Row
            db.insert(TABLE_DISTRICT, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.beginTransaction();
        for (int i = 0; i < arrTehsil.size(); i++) {
            ContentValues values = new ContentValues();
            //values.put(PLANNED_ACTIVITY_ID, arrayPlannedListData.get(i).getCustomerId());
            values.put(TEHSIL_NAME, arrTehsil.get(i).getTehsilName());
            values.put(TEHSIL_ID, arrTehsil.get(i).getTehsilId());
            values.put(TEHSIL_SN, arrTehsil.get(i).getDistrictId());
            // Inserting Row
            db.insert(TABLE_TEHSIL, null, values);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();// Closing database connection
    }

    public ArrayList<StateModel> getStateData() {
        ArrayList<StateModel> arrStateData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_STATE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //arrExecutedActivityData = new ArrayList<>();
                StateModel stateModel = new StateModel();
                stateModel.setStateId(cursor.getString(cursor.getColumnIndex(STATE_ID)));
                stateModel.setStateName(cursor.getString(cursor.getColumnIndex(STATE_NAME)));

                // Adding contact to list
                arrStateData.add(stateModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrStateData;
    }

    public ArrayList<DistrictModel> getDistrictData(String stateId) {
        ArrayList<DistrictModel> arrDistrictData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_DISTRICT + " WHERE " + DISTRICT_SN + "='" + stateId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //arrExecutedActivityData = new ArrayList<>();
                DistrictModel districtModel = new DistrictModel();
                districtModel.setStateId(cursor.getString(cursor.getColumnIndex(DISTRICT_SN)));
                districtModel.setDistrictId(cursor.getString(cursor.getColumnIndex(DISTRICT_ID)));
                districtModel.setDistrictName(cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)));

                // Adding contact to list
                arrDistrictData.add(districtModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrDistrictData;
    }

    public ArrayList<TehsilModel> getTehsilData(String districtId) {
        ArrayList<TehsilModel> arrTehsilData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TEHSIL + " WHERE " + TEHSIL_SN + "='" + districtId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to listzx
        if (cursor.moveToFirst()) {
            do {
                //arrExecutedActivityData = new ArrayList<>();
                TehsilModel tehsilModel = new TehsilModel();
                tehsilModel.setDistrictId(cursor.getString(cursor.getColumnIndex(TEHSIL_SN)));
                tehsilModel.setTehsilId(cursor.getString(cursor.getColumnIndex(TEHSIL_ID)));
                tehsilModel.setTehsilName(cursor.getString(cursor.getColumnIndex(TEHSIL_NAME)));

                // Adding contact to list
                arrTehsilData.add(tehsilModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return arrTehsilData;
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

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //arrPendingActivityData = new ArrayList<>();
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
        db.close(); // Closing database connection
    }

    public ArrayList<CallTrackerDataModel> getFECallTrackerData() {
        ArrayList<CallTrackerDataModel> arrFeCallTrackerData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_FE_CALL_TRACKER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //arrPendingActivityData = new ArrayList<>();
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
    //----------------------------------------------------------------------------------------------

    //-------------------------------Location Tracking--------------------------------------
    public void addLocationTrackedData(ArrayList<LocationTrackingDataModel> siteLocationtrackingList) {
        SQLiteDatabase db = this.getWritableDatabase();

        //long time = System.currentTimeMillis();

        for (int i = 0; i < siteLocationtrackingList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(LOCATION_TRACKER_USER_ID, siteLocationtrackingList.get(i).getUserId());
            values.put(LOCATION_TRACKER_ADDRESS, siteLocationtrackingList.get(i).getAddress());
            values.put(LOCATION_TRACKER_LAT, siteLocationtrackingList.get(i).getLat());
            values.put(LOCATION_TRAKER_LON, siteLocationtrackingList.get(i).getLon());
            values.put(LOCATION_TRACKER_TIME, siteLocationtrackingList.get(i).getTime());
            values.put(LOCATION_TRACKER_DISTANCE, siteLocationtrackingList.get(i).getTime());

            // Inserting Row
            db.insert(TABLE_LOCATION_TRACKER, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<LocationTrackingDataModel> getTrackedLocationData() {
        ArrayList<LocationTrackingDataModel> locationTrackingDataList = new ArrayList<LocationTrackingDataModel>();
        // Select All Query
        String selectQuery = "";

        selectQuery = "SELECT  * FROM " + TABLE_LOCATION_TRACKER + " ORDER BY " + KEY_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationTrackingDataModel locationTrackingDataModel = new LocationTrackingDataModel();

                locationTrackingDataModel.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                locationTrackingDataModel.setAddress(cursor.getString(cursor.getColumnIndex(LOCATION_TRACKER_ADDRESS)));
                locationTrackingDataModel.setLat(cursor.getString(cursor.getColumnIndex(LOCATION_TRACKER_LAT)));
                locationTrackingDataModel.setLon(cursor.getString(cursor.getColumnIndex(LOCATION_TRAKER_LON)));
                locationTrackingDataModel.setTime(cursor.getString(cursor.getColumnIndex(LOCATION_TRACKER_TIME)));
                locationTrackingDataModel.setUserId(cursor.getString(cursor.getColumnIndex(LOCATION_TRACKER_USER_ID)));
                locationTrackingDataModel.setDistance(cursor.getString(cursor.getColumnIndex(LOCATION_TRACKER_DISTANCE)));

                locationTrackingDataList.add(locationTrackingDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return locationTrackingDataList;
    }

    //------------------------------Select Complaint Creation-------------------------------

    public void addComplaintData(ArrayList<ComplaintDataModel> complaintArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.delete("activity_search_details", null, null);

        long time = System.currentTimeMillis();
        for (int i = 0; i < complaintArrayList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(COMPLAINT_USER_ID, complaintArrayList.get(i).getUserId());
            values.put(COMPLAINT_SITE_ID, complaintArrayList.get(i).getSiteID());
            values.put(COMPLAINT_NAME, complaintArrayList.get(i).getName());
            values.put(COMPLAINT_MOBILE_NUMBER, complaintArrayList.get(i).getMobile());
            values.put(COMPLAINT_EMAIL_ID, complaintArrayList.get(i).getEmailId());
            values.put(COMPLAINT_TYPE, complaintArrayList.get(i).getType());
            values.put(COMPLAINT_PRIORITY, complaintArrayList.get(i).getPriority());
            values.put(COMPLAINT_DESCRIPTION, complaintArrayList.get(i).getDescription());
            values.put(COMPLAINT_PROPOSE_PLAN, complaintArrayList.get(i).getProposePlan());
            values.put(COMPLAINT_CLIENT_NAME, complaintArrayList.get(i).getClientName());
            values.put(COMPLAINT_TIME, String.valueOf(time));

            // Inserting Row
            db.insert(TABLE_COMPLAINTS, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<ComplaintDataModel> getComplaintData() {
        ArrayList<ComplaintDataModel> arrComplaintData = new ArrayList<>();

        String selectQuery = "";

        selectQuery = "SELECT * FROM " + TABLE_COMPLAINTS + " LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
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

                // Adding contact to list
                arrComplaintData.add(complaintDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        //return arrFilteredAllActivityData;
        return arrComplaintData;
    }

    //---------------------------------------------------------------------------------------------

    //------------------------------Select Complaint Creation-------------------------------

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

        //return arrFilteredAllActivityData;
        return arrExpenseSheet;
    }
    //---------------------------------------------------------------------------------------------

    //-----------------------------------Complaint Description-------------------------------------

    public void addComplaintDesription(ArrayList<ComplaintDescriptionDataModel> complaintArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.delete("activity_search_details", null, null);

        long time = System.currentTimeMillis();
        for (int i = 0; i < complaintArrayList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(COMPLAINT_DESCRIPTION_TYPE, complaintArrayList.get(i).getType());
            values.put(COMPLAINT_DESCRIPTION_TEXT, complaintArrayList.get(i).getDescription());
            values.put(COMPLAINT_DESCRIPTION_LAST_UPDATED, String.valueOf(time));

            // Inserting Row
            db.insert(TABLE_COMPLAINT_DESCRIPTION, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<ComplaintDescriptionDataModel> getComplaintDesription(String descriptionType) {
        ArrayList<ComplaintDescriptionDataModel> arrComplaintData = new ArrayList<>();

        String selectQuery = "";

        selectQuery = "SELECT * FROM " + TABLE_COMPLAINT_DESCRIPTION + " WHERE " + COMPLAINT_DESCRIPTION_TYPE + " = '" + descriptionType + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
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

        //return arrFilteredAllActivityData;
        return arrComplaintData;
    }


    //---------------------------------------------------------------------------------------------


    //----------------------------Delete Selected Row----------------------------------

    public void deleteSelectedRows(String tableName, String columnName, String columnValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, columnName + "=" + columnValue, null);
        db.close();
    }

    // ----------------------Getting Row Count--------------------------------
    public int getCircleCount(String tableName, String columnName, String columnValue) {
        String countQuery;
        if (tableName.equalsIgnoreCase("circle") || tableName.equalsIgnoreCase("site_search_details")
                || tableName.equalsIgnoreCase("activty_form_data") || tableName.equals((TABLE_NOC_ENGG)) ||
                tableName.equals((TABLE_CUSTOMER))) {
            countQuery = "SELECT  * FROM " + tableName;
        } else {
            countQuery = "SELECT  * FROM " + tableName + " WHERE " + columnName + " = '" + columnValue + "'";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        int count = cursor.getCount();
        return cursor.getCount();
    }

    public int getAllRowCount(String tableName) {
        String countQuery;
        countQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        int count = cursor.getCount();
        return cursor.getCount();


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
}

