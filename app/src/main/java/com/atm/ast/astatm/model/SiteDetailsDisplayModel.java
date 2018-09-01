package com.atm.ast.astatm.model;

/**
 * Created by kishore on 10/23/2015.
 */
public class SiteDetailsDisplayModel {


    String siteId;
    String siteName;
    String siteNumId;
    String date;//dt
    String colorCode;//co
    String batteryVoltage;//btv
    String siteStatus;//sst
    String inputOutVoltage;//iov - new
    String solarCurrent;//soa
    String batteryChargeCurrent;//bta
    String batteryDischargeCurrent;//bda
    String currentAlarm;//ca - new
    String loadCurrent;//la
    String lastUpdatedDate;

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteNumId() {
        return siteNumId;
    }

    public void setSiteNumId(String siteNumId) {
        this.siteNumId = siteNumId;
    }

    public String getInputOutVoltage() {
        return inputOutVoltage;
    }

    public void setInputOutVoltage(String inputOutVoltage) {
        this.inputOutVoltage = inputOutVoltage;
    }

    public String getCurrentAlarm() {
        return currentAlarm;
    }

    public void setCurrentAlarm(String currentAlarm) {
        this.currentAlarm = currentAlarm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(String batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public String getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(String siteStatus) {
        this.siteStatus = siteStatus;
    }

    public String getSolarCurrent() {
        return solarCurrent;
    }

    public void setSolarCurrent(String solarCurrent) {
        this.solarCurrent = solarCurrent;
    }

    public String getBatteryChargeCurrent() {
        return batteryChargeCurrent;
    }

    public void setBatteryChargeCurrent(String batteryChargeCurrent) {
        this.batteryChargeCurrent = batteryChargeCurrent;
    }

    public String getBatteryDischargeCurrent() {
        return batteryDischargeCurrent;
    }

    public void setBatteryDischargeCurrent(String batteryDischargeCurrent) {
        this.batteryDischargeCurrent = batteryDischargeCurrent;
    }


    public String getLoadCurrent() {
        return loadCurrent;
    }

    public void setLoadCurrent(String loadCurrent) {
        this.loadCurrent = loadCurrent;
    }
}
