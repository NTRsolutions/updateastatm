package com.atm.ast.astatm.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.atm.ast.astatm.ASTGson;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.adapter.ClusterGridAdapter;
import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.constants.Contants;
import com.atm.ast.astatm.database.ATMDBHelper;
import com.atm.ast.astatm.framework.IAsyncWorkCompletedCallback;
import com.atm.ast.astatm.framework.ServiceCaller;
import com.atm.ast.astatm.model.SiteDisplayDataModel;
import com.atm.ast.astatm.model.newmodel.Data;
import com.atm.ast.astatm.model.newmodel.Header;
import com.atm.ast.astatm.model.newmodel.ServiceContentData;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.ASTUtil;
import com.atm.ast.astatm.utils.TooltipWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ClusterFragment extends MainFragment {
    GridView clusterViewGrid;
    TooltipWindow tooltipWindow;
    PopupWindow popup = null;
    public List<Data> clusterViewResDataList;
    ClusterGridAdapter clusterGridAdapter;
    AutoCompleteTextView etSearch;
    TextView tvSort, tvLastUpdated, tvClearFilter;
    ImageView imgRefresh;
    TextView tvTotalSites, tvTotalAlarmSites, tvTotalNonComm, tvTotalInvAlarm, tvTotalLowBattery, tvNSMSites, tvFilter;
    SharedPreferences pref;
    String userName = "";
    String uid = "";
    String circleID = "0";
    String ctid = "NA";
    String lat = "23.33";
    String lon = "23.33";
    String circleName = "";
    int refresh = 0;
    LinearLayout filterLayout;
    ATMDBHelper atmdbHelper;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_cluster;
    }

    @Override
    protected void getArgs() {
        circleID = this.getArguments().getString("CIRCLE_ID");
        circleName = this.getArguments().getString("CIRCLE_NAME");
        CircleFragment.filterString = this.getArguments().getString("ALARM_FILTER");
        ctid = this.getArguments().getString("CUSTOMER_FILTER");
    }

    @Override
    protected void loadView() {
        this.clusterViewGrid = this.findViewById(R.id.clusterViewGrid);
        this.tvLastUpdated = this.findViewById(R.id.tvLastUpdated);
        this.imgRefresh = this.findViewById(R.id.imgRefresh);
        this.tvSort = this.findViewById(R.id.tvSort);
        this.tvTotalSites = this.findViewById(R.id.tvTotalSites);
        this.tvTotalAlarmSites = this.findViewById(R.id.tvTotalAlarmSites);
        this.tvTotalNonComm = this.findViewById(R.id.tvTotalNonComm);
        this.tvTotalInvAlarm = this.findViewById(R.id.tvTotalInvAlarm);
        this.tvTotalLowBattery = this.findViewById(R.id.tvTotalLowBattery);
        this.tvNSMSites = this.findViewById(R.id.tvNSMSites);
        this.tvFilter = this.findViewById(R.id.tvFilter);
        this.tvClearFilter = this.findViewById(R.id.tvClearFilter);
        filterLayout = this.findViewById(R.id.filterLayout);
        this.etSearch = this.findViewById(R.id.etSearch);

    }

    @Override
    protected void setClickListeners() {
        this.imgRefresh.setOnClickListener(this);
        this.tvFilter.setOnClickListener(this);
        this.tvClearFilter.setOnClickListener(this);
        this.filterLayout.setOnClickListener(this);
        this.tvSort.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    /**
     * get Shared Pref Data
     */
    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        uid = pref.getString("userId", "");
        userName = pref.getString("userName", "");
    }

    @Override
    protected void dataToView() {
        atmdbHelper = new ATMDBHelper(getContext());
        popup = new PopupWindow(getContext());
        getSharedPrefData();
        populateClusterGrid();
        gestureDetectorAction();
        clusterViewGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openSiteScreen(position);
            }
        });
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!CircleFragment.filterString.equals("") || !CircleFragment.filterString.equals("NA")) {
                    getClusterData(CircleFragment.filterString);
                }
                if (!CircleFragment.ctid.equals("NA") || !CircleFragment.filterString.equals("NA")) {
                    tvClearFilter.setVisibility(View.VISIBLE);
                    filterLayout.setVisibility(View.VISIBLE);
                } else {
                    tvClearFilter.setVisibility(View.GONE);
                    filterLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    // open site screen
    private void openSiteScreen(int position) {
        SiteFragment siteFragment = new SiteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", clusterViewResDataList.get(position).getDn() + " " + "Site");
        bundle.putBoolean("showMenuButton", false);
        bundle.putString("CLUSTER_ID", clusterViewResDataList.get(position).getDid() + "");//ClusterId
        bundle.putString("CLUSTER_NAME", clusterViewResDataList.get(position).getDn());//clusterName
        bundle.putString("ALARM_FILTER", CircleFragment.filterString);
        bundle.putString("CUSTOMER_FILTER", ctid);
        //getClusterData("NA");
        ctid = "NA";
        getHostActivity().updateFragment(siteFragment, bundle);
    }

    /**
     * All GestureDetectorAction perform
     */
    public void gestureDetectorAction() {
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                CircleFragment.filterString = "NA";
                getClusterData(CircleFragment.filterString);
                tooltipWindow = new TooltipWindow(getContext(), "long press to see all sites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalSites);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

        });
        tvTotalSites.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        final GestureDetector gestureDetector1 = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                CircleFragment.filterString = "NOCOMM" + ",NSM" + ",INV" + ",BL1";
                getClusterData(CircleFragment.filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all AlarmSites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalAlarmSites);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

        });
        tvTotalAlarmSites.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector1.onTouchEvent(event);
                return true;
            }
        });
        final GestureDetector gestureDetector2 = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                CircleFragment.filterString = "NOCOMM";
                getClusterData(CircleFragment.filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all NonComm sites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalNonComm);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

        });
        tvTotalNonComm.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector2.onTouchEvent(event);
                return true;
            }
        });
        final GestureDetector gestureDetector3 = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                CircleFragment.filterString = "INV";
                getClusterData(CircleFragment.filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all InvAlarm sites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalInvAlarm);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
        tvTotalInvAlarm.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector3.onTouchEvent(event);
                return true;
            }
        });
        final GestureDetector gestureDetector4 = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                CircleFragment.filterString = "BL1";
                getClusterData(CircleFragment.filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all LowBattery sites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvTotalLowBattery);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
        tvTotalLowBattery.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector4.onTouchEvent(event);
                return true;
            }
        });
        final GestureDetector gestureDetector5 = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                CircleFragment.filterString = "NSM";
                getClusterData(CircleFragment.filterString);
                tooltipWindow = new TooltipWindow(getContext(), "Long press to see all NSMSites");
                if (!tooltipWindow.isTooltipShown())
                    tooltipWindow.showToolTip(tvNSMSites);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            /*    Intent intentClusterActivity = new Intent(ClusterActivity.this, SiteActivity.class);
                intentClusterActivity.putExtra("CLUSTER_ID","743");
                intentClusterActivity.putExtra("CLUSTER_NAME", "Arwal");
                intentClusterActivity.putExtra("ALARM_FILTER", "NA");
                intentClusterActivity.putExtra("CUSTOMER_FILTER", ctid);
                startActivity(intentClusterActivity);
                overridePendingTransition(R.transition.left_to_right, R.transition.right_to_left);*/
                super.onLongPress(e);
            }

        });
        tvNSMSites.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector5.onTouchEvent(event);
                return true;
            }
        });
    }

    /**
     * populate Cluster Grid data
     */
    public void populateClusterGrid() {
        setSiteNameListIntoSearch();
        if (ASTUIUtil.isOnline(getContext())) {
            getClusterData("NA");
        } else {
            setAdapter();
        }
    }

    /**
     * get cluster value into db and set into Adapter View
     */

    public void setAdapter() {
        tvLastUpdated.setText("Last Updated: " + ASTUIUtil.formatDate(String.valueOf(System.currentTimeMillis())));

        ATMDBHelper atmdbHelper = new ATMDBHelper(getContext());
        List<ServiceContentData> contentData = atmdbHelper.getAllClusterData();
        if (contentData != null && contentData.size() > 0) {
            for (ServiceContentData data : contentData) {
                if (data != null) {
                    setHeaderData(data.getHeader());
                    Data[] clusterData = data.getData();
                    if (clusterData != null) {
                        clusterViewResDataList = new ArrayList<>(Arrays.asList(clusterData));
                        if (clusterViewResDataList == null && clusterViewResDataList.size() == 0) {
                            clusterViewGrid.setEmptyView(clusterViewGrid);
                        } else {
                            clusterGridAdapter = new ClusterGridAdapter(getContext(), clusterViewResDataList);
                            clusterViewGrid.setEmptyView(clusterViewGrid);
                            clusterViewGrid.setAdapter(clusterGridAdapter);
                            doSortingClusterList("dn", true);//dn means clusterName
                        }
                    }
                }
            }
        }
    }

    /**
     * set Header Data on View
     *
     * @param headerData
     */
    private void setHeaderData(Header[] headerData) {
        for (Header header : headerData) {
            tvTotalSites.setText("Total Sites: " + header.getTotalSites());
            tvTotalAlarmSites.setText("Total Alarm Sites: " + header.getAlarmSites());
            tvTotalNonComm.setText("Total Non Comm: " + header.getNoncomSites());
            tvTotalInvAlarm.setText("Total INV Alarm: " + header.getINVSites());
            tvTotalLowBattery.setText("Total Low Battery: " + header.getLowBatterySies());
            tvNSMSites.setText("NMS Sites: " + header.getNSMSies());
        }
    }

    /*
     *
     * Calling Web Service to get Cluster Data
     */
    private void getClusterData(String alarmTypes) {
        ASTProgressBar _progrssBar = new ASTProgressBar(getContext());
        _progrssBar.show();
        if (alarmTypes.equals("")) {
            alarmTypes = "NA";
        }
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        String serviceURL = Contants.BASE_URL + Contants.CLUSTER_DATA_NEW_URL;
        serviceURL += "&uid=" + uid + "&ciid=" + circleID + "&ctids=" + ctid + "&alarmtypes=" + alarmTypes + "&lat=" + lat + "&lon=" + lon;
        serviceCaller.CallCommanServiceMethod(serviceURL, "getClusterData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveClusterData(result);
                } else {
                    ASTUIUtil.showToast("District Data Not Avilable");
                }
                _progrssBar.dismiss();
            }

        });
    }


    /**
     * Parse and Save  ClusterDataServices
     */

    public void parseandsaveClusterData(String result) {
        if (result != null) {
            boolean msgFlag = true;
            ServiceContentData serviceData = ASTGson.store().getObject(ServiceContentData.class, result);
            if (serviceData != null) {
                if (serviceData.getStatus() == 2) {
                    atmdbHelper.deleteAllRows("ClusterDetails");
                    atmdbHelper.insertClusterData(serviceData);
                    msgFlag = false;
                    setAdapter();
                }
            }
            if (msgFlag) {
                ASTUIUtil.showToast("Cluster Data not available.");
            }
        }

    }

    /**
     * genertae Sort List
     */
    public void genrateSortList() {
        final CharSequence[] items = {
                "A-Z", "Z-A", "Alarm Count ↑", "Alarm Count ↓"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        doSortingClusterList("dn", true);//dn means clusterName
                        break;
                    case 1:
                        doSortingClusterList("dn", false);//dn means clusterName
                        break;
                    case 2:
                        doSortingClusterList("dv", false);//dv means clusterAlarmSites
                        break;
                    case 3:
                        doSortingClusterList("dv", true);//dv means clusterAlarmSites
                        break;
                    default:
                        break;
                }
            }
        });
        AlertDialog sortAlert = builder.create();
        sortAlert.show();
    }

    /**
     * set Site Search Name Adapter and openClusterScreen SiteDetail Fragment with search Data
     */
    private void setSiteNameListIntoSearch() {
        List<Data> siteList = atmdbHelper.getAllSiteListData();
        if (siteList != null && siteList.size() > 0) {
            String[] arrSearchData = new String[siteList.size()];
            for (int i = 0; i < siteList.size(); i++) {
                arrSearchData[i] = siteList.get(i).getSiteName();
            }
            ArrayAdapter<String> adapterSiteName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrSearchData);
            etSearch.setAdapter(adapterSiteName);
            etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String searchString = etSearch.getText().toString();
                    if (searchString != null && !searchString.equals("")) {
                        getSiteDetailAndOpenSiteDetailScreen(searchString);
                    }
                }
            });
        }
    }

    //get site detail based on search value form db and open site detail screen
    private void getSiteDetailAndOpenSiteDetailScreen(String searchString) {
        Data siteData = atmdbHelper.getSiteDataBySiteName(searchString);
        if (siteData != null) {
            SiteDetailFragment siteDetailFragment = new SiteDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("headerTxt", "Site Details");
            bundle.putBoolean("showMenuButton", false);
            bundle.putString("SITE_ID_NUM", siteData.getCustomerSiteId());
            bundle.putString("SITE_NAME", siteData.getSiteName());
            bundle.putString("SITE_ID", String.valueOf(siteData.getSiteId()));
            getHostActivity().updateFragment(siteDetailFragment, bundle);
        }
    }

    /**
     * do sorting method
     *
     * @param sortType
     */
    public void doSortingClusterList(String sortType, boolean isAsc) {
        if (clusterViewResDataList != null && clusterViewResDataList.size() > 0) {
            ASTUtil.sortArray(clusterViewResDataList, sortType, isAsc);
            clusterGridAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgRefresh) {
            CircleFragment.ctid = "NA";
            CircleFragment.filterString = "NA";
            refresh = 1;
            populateClusterGrid();
        } else if (view.getId() == R.id.tvFilter) {
            getFIlterData();
        } else if (view.getId() == R.id.tvClearFilter || view.getId() == R.id.filterLayout) {
            ctid = "NA";
            CircleFragment.filterString = "NA";
            getClusterData(CircleFragment.filterString);
            tvClearFilter.setVisibility(View.GONE);
        } else if (view.getId() == R.id.tvSort) {
            genrateSortList();
        }
    }


    /**
     * get Filter Data
     */

    public void getFIlterData() {
       /* ArrayList<CustomerListDataModel> arrCustomerData = new ArrayList<>();
        if (atmDB.getCircleCount("customer_data", "", "") > 0) {
            selectedCustomerFilter = new boolean[atmDB.getCircleCount("customer_data", "", "") + 1];
            arrCustomerData = atmDB.getCustomerData();
        } else {
            selectedCustomerFilter = new boolean[0];
        }
        final String[] arrFilteredData = new String[arrCustomerData.size()];
        final String[] arrFilteredIdData = new String[arrCustomerData.size()];
        for (int i = 0; i < arrCustomerData.size(); i++) {
            arrFilteredData[i] = arrCustomerData.get(i).getCustomerName();
            arrFilteredIdData[i] = arrCustomerData.get(i).getCustomerId();
        }
        String[] arrParentData = new String[2];
        String[] arrAlarmTypes = {"Battery Low", "No Comm", "INV", "NSM"};
        arrParentData[0] = "Alarm Type";
        arrParentData[1] = "Customer";
        CircleFragment.arrSelectedFilterOne = new Boolean[arrAlarmTypes.length];
        for (int i = 0; i < arrAlarmTypes.length; i++) {
            CircleFragment.arrSelectedFilterOne[i] = false;
        }
        CircleFragment.arrSelectedFilterTwo = new Boolean[arrFilteredData.length];
        for (int i = 0; i < arrFilteredData.length; i++) {
            CircleFragment.arrSelectedFilterTwo[i] = false;
        }
        FilterPopupCircle filterPopup = new FilterPopupCircle();
        filterPopup.getFilterPopup(popup, getContext(), arrParentData, arrAlarmTypes, arrFilteredData, arrFilteredIdData, "circle");*/
    }
}
