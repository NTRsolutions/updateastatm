package com.atm.ast.astatm.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.utils.ASTUIUtil;

import java.io.IOException;
import java.text.DecimalFormat;

import static android.content.Context.MODE_PRIVATE;

public class AboutFragment extends MainFragment {
    TextView tvNetworkStrength, tvOsVersion, tvDateLastConnected, tvUserName, tvUserLocation;
    SharedPreferences pref;
    ASTUIUtil commonFunctions;
    String address;
    DecimalFormat df;
    private boolean isWifiConnected = false;
    String lastConnectedDateTime;
    String userName;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_about2;
    }

    @Override
    protected void loadView() {
        this.tvNetworkStrength = this.findViewById(R.id.tvNetworkStrength);
        this.tvOsVersion = this.findViewById(R.id.tvOsVersion);
        this.tvDateLastConnected = this.findViewById(R.id.tvDateLastConnected);
        this.tvUserName = this.findViewById(R.id.tvUserName);
        this.tvUserLocation = this.findViewById(R.id.tvUserLocation);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }


    /**
     * get Shared Pref Data
     */
    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        lastConnectedDateTime = pref.getString("LAST_CONNECTED", "Not Connected");
        userName = pref.getString("userName", "");
    }

    @Override
    protected void dataToView() {
        commonFunctions = new ASTUIUtil();
        df = new DecimalFormat("0.00");
        getSharedPrefData();
        TextView appversion_number = (TextView) findViewById(R.id.version_number);
        String appversionName = ASTUIUtil.getAppVersionName(getContext());
        if (appversionName != null) {
            appversion_number.setText(appversionName);
        }
        double lat, lon;
        Location location = commonFunctions.getLocation(getContext());
        if (location == null) {
            lat = 26.5;
            lon = 26.5;
            address = "Address not Found";
        } else {
            lat = location.getLongitude();
            lon = location.getLatitude();
            try {
                address = commonFunctions.getLocationAddress(location, getContext(), "");
                String test = "";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!lastConnectedDateTime.equals("Not Connected")) {
            tvDateLastConnected.setText(commonFunctions.formatDate(lastConnectedDateTime));
        }
        int index = Build.VERSION.SDK_INT;
        String versionName = String.valueOf(index);
        ConnectivityManager connectivitymanager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = connectivitymanager.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    this.isWifiConnected = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    this.isWifiConnected = false;
        }
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int linkSpeed = wifiManager.getConnectionInfo().getRssi();
        tvOsVersion.setText(versionName);

        if (ASTUIUtil.isOnline(getContext())) {
            if (!isWifiConnected) { //-200 is not connected
                MyPhoneStateListener MyListener = new MyPhoneStateListener();
                TelephonyManager Tel = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                Tel.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            } else {
                tvNetworkStrength.setText("Wifi: " + String.valueOf((linkSpeed / 2) + 100 + "% (" + df.format(linkSpeed) + " dbm)"));
            }
        } else {
            tvNetworkStrength.setText("Please check your network connection");
        }
        tvUserName.setText(userName);
        tvUserLocation.setText(address);
        String test = "";
    }


    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            int MobileNetworkStrength = signalStrength.getGsmSignalStrength(); // 0 is low 31 is good
            if (MobileNetworkStrength * 3.3 < 0) {
                tvNetworkStrength.setText("Mobile Network: " + String.valueOf(0) + "% (" + (df.format(6.3 * MobileNetworkStrength - 200)) + " dbm)");
                ASTUIUtil.showToast( "Netwoirk Unavailable");
            } else {
                tvNetworkStrength.setText("Mobile Network: " + String.valueOf(MobileNetworkStrength * 3.3) + "% (" + (df.format(6.3 * MobileNetworkStrength - 200)) + " dbm)");
            }
        }

    }

}
