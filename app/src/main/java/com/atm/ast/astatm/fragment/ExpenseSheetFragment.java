package com.atm.ast.astatm.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.ExpenseScreenAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.database.AtmDatabase;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.ExpenseScreenDataModel;
import com.atm.ast.astatm.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class ExpenseSheetFragment extends MainFragment {
    private ArrayList<ExpenseScreenDataModel> arrExpenseData;
    private Spinner spMonth;
    SharedPreferences pref;
    String userId, userRole, userAccess, r1;
    String userName = "";
    TextView tvDATotal, tvTATotal, tvHotelTotal, tvBonusTotal, tvWaterCostTotal, tvOtherExpTotal, tvTotalTotal;
    TextView tvAdditionalBonus, tvAdditionalPenalty, tvGrandTotal;
    ImageView imgRefresh, imgSendEmail;
    String[][] arrAllMonths;
    ListView lview;
    ATMDBHelper atmdbHelper;
    ExpenseScreenDataModel expenseScreenDataModel;
    int year;
    int month;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_expense_sheet;
    }

    @Override
    protected void loadView() {
        lview = findViewById(R.id.listview);
        spMonth = findViewById(R.id.spMonth);
        tvDATotal = findViewById(R.id.tvDATotal);
        tvTATotal = findViewById(R.id.tvTATotal);
        tvHotelTotal = findViewById(R.id.tvHotelTotal);
        tvBonusTotal = findViewById(R.id.tvBonusTotal);
        tvWaterCostTotal = findViewById(R.id.tvWaterCostTotal);
        tvOtherExpTotal = findViewById(R.id.tvOtherExpTotal);
        tvTotalTotal = findViewById(R.id.tvTotalTotal);
        imgRefresh = findViewById(R.id.imgRefresh);
        tvAdditionalBonus = findViewById(R.id.tvAdditionalBonus);
        tvAdditionalPenalty = findViewById(R.id.tvAdditionalPenalty);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);
        imgSendEmail = findViewById(R.id.imgEmail);
    }

    @Override
    protected void setClickListeners() {
        imgRefresh.setOnClickListener(this);
        imgSendEmail.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }


    @Override
    protected void dataToView() {
        atmdbHelper = new ATMDBHelper(getContext());
        getSharedPrefData();
        arrExpenseData = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                if (postion == 0) {
                    if (checkDataReload(month)) {
                        getExpenseData(userId, getContext(), String.valueOf(year), String.valueOf(month));
                    } else {
                        setOfflineExpenseData(month);
                    }
                } else if (postion == 1) {
                    int selectedMonth = month - 1;
                    int selectedYear = year;
                    if (month == 1) {
                        selectedMonth = 12;
                        selectedYear = year - 1;
                    }
                    if (checkDataReload(selectedMonth)) {
                        getExpenseData(userId, getContext(), String.valueOf(selectedYear), String.valueOf(selectedMonth));
                    } else {
                        if (month == 1) {
                            setOfflineExpenseData(12);
                        } else {
                            setOfflineExpenseData(month - 1);
                        }
                    }
                } else if (postion == 2) {
                    int selectedMonth = month - 2;
                    int selectedYear = year;
                    if (month == 1) {
                        selectedMonth = 11;
                        selectedYear = year - 1;
                    }
                    if (checkDataReload(selectedMonth)) {
                        getExpenseData(userId, getContext(), String.valueOf(selectedYear), String.valueOf(selectedMonth));
                    } else {
                        if (month == 2) {
                            setOfflineExpenseData(11);
                        } else {
                            setOfflineExpenseData(month - 2);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getimageRefreshgData();
    }

    /* *
     * get SharedPrefDate
     */
    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        userId = pref.getString("userId", "");
        userName = pref.getString("userName", "");
    }

    /*
     *
     * Calling Web Service to getExpenseData
     */
    public void getExpenseData(final String userId, Context context, final String year, final String month) {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        JSONObject dataobject = new JSONObject();
        try {
            dataobject.put("UserId", userId);
            dataobject.put("Year", year);
            dataobject.put("Month", month);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = "";
        serviceURL = Contants.BASE_URL_API + Contants.EXPENSE_SCREEN_URL;
        serviceCaller.CallCommanServiceMethod(serviceURL, dataobject, "getExpenseData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsavetExpenseData(result);
                } else {
                    ASTUIUtil.showToast("Expense Data  Not Avilable");
                }
                _progrssBar.dismiss();
            }
        });
    }


    /*
     *
     * Parse and Save ExpenseData
     */

    public void parseandsavetExpenseData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                ArrayList<ExpenseScreenDataModel> arrExpenseScreen;
                String jsonStatus = jsonRootObject.optString("Status").toString();
                if (jsonStatus.equals("2")) {
                    arrExpenseScreen = new ArrayList<>();
                    JSONObject jsonObjectSummary = jsonRootObject.optJSONObject("Summary");
                    String summaryTotal = jsonObjectSummary.optString("Total").toString();
                    String additionalBonus = jsonObjectSummary.optString("AdditionalBonus").toString();
                    String additionalPenalty = jsonObjectSummary.optString("AdditionalPenalty").toString();
                    String grandTotal = jsonObjectSummary.optString("GrandTotal").toString();
                    tvAdditionalPenalty.setText("Additional Penalty: " + additionalPenalty);
                    tvAdditionalBonus.setText("Additional Bonus: " + additionalBonus);
                    tvGrandTotal.setText("Grand Total: " + grandTotal);
                    Calendar c = Calendar.getInstance();
                    final int year = c.get(Calendar.YEAR);
                    final int month = c.get(Calendar.MONTH) + 1;
                    int daTotal = 0;
                    int taTotal = 0;
                    int hotelTotal = 0;
                    int bonusTotal = 0;
                    int penaltyTotal = 0;
                    int waterCostTotal = 0;
                    int otherExpTotal = 0;
                    int totalTotal = 0;
                    int selectedMonth = month;
                    if (spMonth.getSelectedItemPosition() == 1) {
                        if (month == 1) {
                            selectedMonth = 12;
                        } else {
                            selectedMonth = month - 1;
                        }
                    } else if (spMonth.getSelectedItemPosition() == 2) {
                        if (month == 2) {
                            selectedMonth = 12;
                        } else {
                            selectedMonth = month - 2;
                        }
                    }

                    JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String date = jsonObject.optString("Date").toString();
                        String siteName = jsonObject.optString("SiteName").toString();
                        String attendence = jsonObject.optString("Attendence").toString();
                        String da = jsonObject.optString("DA").toString();
                        String ta = jsonObject.optString("TA").toString();
                        String hotel = jsonObject.optString("Hotel").toString();
                        String bonus = jsonObject.optString("Bonus").toString();
                        String penalty = jsonObject.optString("Penalty").toString();
                        String waterCost = jsonObject.optString("WaterCost").toString();
                        String otherExp = jsonObject.optString("OtherExp").toString();
                        String total = jsonObject.optString("Total").toString();
                        if (da.equalsIgnoreCase("null")) {
                            da = "0";
                        } else if (ta.equalsIgnoreCase("null")) {
                            ta = "0";
                        } else if (hotel.equalsIgnoreCase("null")) {
                            hotel = "0";
                        } else if (bonus.equalsIgnoreCase("null")) {
                            bonus = "0";
                        } else if (penalty.equalsIgnoreCase("null")) {
                            penalty = "0";
                        } else if (waterCost.equalsIgnoreCase("null")) {
                            waterCost = "0";
                        } else if (otherExp.equalsIgnoreCase("null")) {
                            otherExp = "0";
                        } else if (total.equalsIgnoreCase("null")) {
                            total = "0";
                        }
                        expenseScreenDataModel = new ExpenseScreenDataModel();
                        expenseScreenDataModel.setDate(date);
                        expenseScreenDataModel.setSiteName(siteName);
                        expenseScreenDataModel.setAttendance(attendence);
                        expenseScreenDataModel.setDa(da);
                        expenseScreenDataModel.setTa(ta);
                        expenseScreenDataModel.setHotel(hotel);
                        expenseScreenDataModel.setBonus(bonus);
                        expenseScreenDataModel.setPenalty(penalty);
                        expenseScreenDataModel.setWaterCost(waterCost);
                        expenseScreenDataModel.setOtherExp(otherExp);
                        expenseScreenDataModel.setTotal(total);
                        expenseScreenDataModel.setMonth(String.valueOf(selectedMonth));
                        expenseScreenDataModel.setAdditionalBonus(additionalBonus);
                        expenseScreenDataModel.setAdditionalPenalty(additionalPenalty);
                        expenseScreenDataModel.setGrandTotal(grandTotal);
                        if (jsonArray.length() - 1 == i) {
                            expenseScreenDataModel.setDaTotal(String.valueOf(daTotal));
                            expenseScreenDataModel.setTaTotal(String.valueOf(taTotal));
                            expenseScreenDataModel.setHotelTotal(String.valueOf(hotelTotal));
                            expenseScreenDataModel.setBonusTotal(String.valueOf(bonusTotal));
                            expenseScreenDataModel.setPenaltyTotal(String.valueOf(penaltyTotal));
                            expenseScreenDataModel.setWaterCostTotal(String.valueOf(waterCostTotal));
                            expenseScreenDataModel.setOtherExpTotal(String.valueOf(otherExpTotal));
                            expenseScreenDataModel.setTotalTotal(String.valueOf(totalTotal));
                            tvDATotal.setText(String.valueOf(daTotal));
                            tvTATotal.setText(String.valueOf(taTotal));
                            tvHotelTotal.setText(String.valueOf(hotelTotal));
                            tvBonusTotal.setText(String.valueOf(bonusTotal) + "/" + String.valueOf(penaltyTotal));
                            tvWaterCostTotal.setText(String.valueOf(waterCostTotal));
                            tvOtherExpTotal.setText(String.valueOf(otherExpTotal));
                            tvTotalTotal.setText(String.valueOf(totalTotal));
                        }
                        arrExpenseScreen.add(expenseScreenDataModel);
                    }

                    if (spMonth.getSelectedItemPosition() == 0) {
                        atmdbHelper.deleteSelectedRows("expense_sheet", "expense_month", String.valueOf(month));
                        atmdbHelper.addExpenseSheetData(arrExpenseScreen);
                    } else if (spMonth.getSelectedItemPosition() == 1) {
                        if (month == 1) {
                            atmdbHelper.deleteSelectedRows("expense_sheet", "expense_month", String.valueOf(12));
                            atmdbHelper.addExpenseSheetData(arrExpenseScreen);
                        } else {
                            atmdbHelper.deleteSelectedRows("expense_sheet", "expense_month", String.valueOf(month - 1));
                            atmdbHelper.addExpenseSheetData(arrExpenseScreen);
                        }
                    } else if (spMonth.getSelectedItemPosition() == 2) {
                        if (month == 2) {
                            atmdbHelper.deleteSelectedRows("expense_sheet", "expense_month", String.valueOf(12));
                            atmdbHelper.addExpenseSheetData(arrExpenseScreen);
                        } else {
                            atmdbHelper.deleteSelectedRows("expense_sheet", "expense_month", String.valueOf(month - 2));
                            atmdbHelper.addExpenseSheetData(arrExpenseScreen);
                        }
                    }
                    String test = "";
                    ExpenseScreenAdapter expenseScreenAdapter = new ExpenseScreenAdapter(getActivity(), arrExpenseScreen);
                    lview.setAdapter(expenseScreenAdapter);
                    expenseScreenAdapter.notifyDataSetChanged();
                    setTotalForAllColums(arrExpenseScreen);
                } else if (jsonStatus.equals("0")) {
                    ASTUIUtil.showToast("Data is not available");

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //   e.printStackTrace();
            }
        }

    }

    /**
     * Check Data Reload Page data
     *
     * @param selectedMonth
     * @return
     */
    public boolean checkDataReload(int selectedMonth) {
        ArrayList<ExpenseScreenDataModel> arrExpenseScreen = atmdbHelper.getExpenseSheetData(selectedMonth);
        boolean result = true;
        if (arrExpenseScreen != null && arrExpenseScreen.size() > 0) {
            String lastUpdatedDate = arrExpenseScreen.get(0).getLastUpdatedTime();
            Calendar calendarLastUpdated = Calendar.getInstance();
            calendarLastUpdated.setTimeInMillis(Long.parseLong(lastUpdatedDate));
            int mDayLastUpdated = calendarLastUpdated.get(Calendar.DAY_OF_MONTH);
            int mHourLastUpdated = calendarLastUpdated.get(Calendar.HOUR_OF_DAY);
            Calendar calendarCurrentDate = Calendar.getInstance();
            calendarCurrentDate.setTimeInMillis(System.currentTimeMillis());
            int mDayCurrentDate = calendarCurrentDate.get(Calendar.DAY_OF_MONTH);
            int mHourCurrentDate = calendarCurrentDate.get(Calendar.HOUR_OF_DAY);
            result = true;
        } else {
            result = true;
        }
        return result;
    }

    /**
     * set Off Line Data
     *
     * @param month
     */
    public void setOfflineExpenseData(int month) {
        ArrayList<ExpenseScreenDataModel> arrExpenseScreen = atmdbHelper.getExpenseSheetData(month);
        tvAdditionalPenalty.setText("Additional Penalty: " + arrExpenseScreen.get(0).getAdditionalPenalty());
        tvAdditionalBonus.setText("Additional Bonus: " + arrExpenseScreen.get(0).getAdditionalBonus());
        tvGrandTotal.setText("Grand Total: " + arrExpenseScreen.get(0).getGrandTotal());
        ExpenseScreenAdapter expenseScreenAdapter = new ExpenseScreenAdapter(getActivity(), arrExpenseScreen);
        lview.setAdapter(expenseScreenAdapter);
        expenseScreenAdapter.notifyDataSetChanged();
        setTotalForAllColums(arrExpenseScreen);
    }

    /**
     * set Total for all Couluma Data
     *
     * @param arrExpenseScreen
     */

    public void setTotalForAllColums(ArrayList<ExpenseScreenDataModel> arrExpenseScreen) {
        int daTotal = 0;
        int taTotal = 0;
        int hotelTotal = 0;
        int bonusTotal = 0;
        int penaltyTotal = 0;
        int waterCostTotal = 0;
        int otherExpTotal = 0;
        int totalTotal = 0;
        for (int i = 0; i < arrExpenseScreen.size(); i++) {
            daTotal += Integer.parseInt(arrExpenseScreen.get(i).getDa());
            taTotal += Integer.parseInt(arrExpenseScreen.get(i).getTa());
            hotelTotal += Integer.parseInt(arrExpenseScreen.get(i).getHotel());
            bonusTotal += Integer.parseInt(arrExpenseScreen.get(i).getBonus());
            penaltyTotal += Integer.parseInt(arrExpenseScreen.get(i).getPenalty());
            waterCostTotal += Integer.parseInt(arrExpenseScreen.get(i).getWaterCost());
            otherExpTotal += Integer.parseInt(arrExpenseScreen.get(i).getOtherExp());
            totalTotal += Integer.parseInt(arrExpenseScreen.get(i).getTotal());
        }
        tvDATotal.setText(String.valueOf(daTotal));
        tvTATotal.setText(String.valueOf(taTotal));
        tvHotelTotal.setText(String.valueOf(hotelTotal));
        tvBonusTotal.setText(String.valueOf(bonusTotal) + "/" + String.valueOf(penaltyTotal));
        tvWaterCostTotal.setText(String.valueOf(waterCostTotal));
        tvOtherExpTotal.setText(String.valueOf(otherExpTotal));
        tvTotalTotal.setText(String.valueOf(totalTotal));
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgRefresh) {
            getimageRefreshgData();
        } else if (view.getId() == R.id.imgEmail) {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"alert@appliedsolartechnologies.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            email.putExtra(Intent.EXTRA_TEXT, "MEssage");
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        }
    }

    /**
     * get image Refresh  data on page when clik image referesh icon
     */

    public void getimageRefreshgData() {
        arrAllMonths = new String[2][12];
        arrAllMonths[0][0] = "1";
        arrAllMonths[1][0] = "Jan";
        arrAllMonths[0][1] = "2";
        arrAllMonths[1][1] = "Feb";
        arrAllMonths[0][2] = "3";
        arrAllMonths[1][2] = "Mar";
        arrAllMonths[0][3] = "4";
        arrAllMonths[1][3] = "Apr";
        arrAllMonths[0][4] = "5";
        arrAllMonths[1][4] = "May";
        arrAllMonths[0][5] = "6";
        arrAllMonths[1][5] = "June";
        arrAllMonths[0][6] = "7";
        arrAllMonths[1][6] = "July";
        arrAllMonths[0][7] = "8";
        arrAllMonths[1][7] = "Aug";
        arrAllMonths[0][8] = "9";
        arrAllMonths[1][8] = "Sep";
        arrAllMonths[0][9] = "10";
        arrAllMonths[1][9] = "Oct";
        arrAllMonths[0][10] = "11";
        arrAllMonths[1][10] = "Nov";
        arrAllMonths[0][11] = "12";
        arrAllMonths[1][11] = "Dec";
        ArrayList<String> arrMonthList = new ArrayList<>();
        arrMonthList.add(arrAllMonths[1][month - 1]);
        if (month == 1) {
            arrMonthList.add(arrAllMonths[1][11]);
            arrMonthList.add(arrAllMonths[1][10]);
        } else if (month == 2) {
            arrMonthList.add(arrAllMonths[1][month - 2]);
            arrMonthList.add(arrAllMonths[1][11]);
        } else {
            arrMonthList.add(arrAllMonths[1][month - 2]);
            arrMonthList.add(arrAllMonths[1][month - 3]);
        }
        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrMonthList);
        dataAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(dataAdapterMonth);
        atmdbHelper.deleteAllRows("expense_sheet");
        getExpenseData(userId, getContext(), String.valueOf(year), String.valueOf(month));
    }
}
