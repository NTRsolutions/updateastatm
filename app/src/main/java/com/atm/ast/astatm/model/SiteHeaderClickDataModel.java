package com.atm.ast.astatm.model;

/**
 * /** * @author AST Inc.  14-02-2017.
 */

public class SiteHeaderClickDataModel {
    String siteId = "";
    String siteName = "";
    String currentBattVoltage = "";
    String battChgCurrent = "";
    String battDisChgCurrent = "";
    String EBRH = "";
    String DGRH = "";
    String upTimeDay = "";
    String upTimeNight = "";
    String currentalarm = "";
    String solarCurrent = "";

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

    public String getCurrentBattVoltage() {
        return currentBattVoltage;
    }

    public void setCurrentBattVoltage(String currentBattVoltage) {
        this.currentBattVoltage = currentBattVoltage;
    }

    public String getBattChgCurrent() {
        return battChgCurrent;
    }

    public void setBattChgCurrent(String battChgCurrent) {
        this.battChgCurrent = battChgCurrent;
    }

    public String getBattDisChgCurrent() {
        return battDisChgCurrent;
    }

    public void setBattDisChgCurrent(String battDisChgCurrent) {
        this.battDisChgCurrent = battDisChgCurrent;
    }

    public String getEBRH() {
        return EBRH;
    }

    public void setEBRH(String EBRH) {
        this.EBRH = EBRH;
    }

    public String getDGRH() {
        return DGRH;
    }

    public void setDGRH(String DGRH) {
        this.DGRH = DGRH;
    }

    public String getUpTimeDay() {
        return upTimeDay;
    }

    public void setUpTimeDay(String upTimeDay) {
        this.upTimeDay = upTimeDay;
    }

    public String getUpTimeNight() {
        return upTimeNight;
    }

    public void setUpTimeNight(String upTimeNight) {
        this.upTimeNight = upTimeNight;
    }

    public String getCurrentalarm() {
        return currentalarm;
    }

    public void setCurrentalarm(String currentalarm) {
        this.currentalarm = currentalarm;
    }

    public String getSolarCurrent() {
        return solarCurrent;
    }

    public void setSolarCurrent(String solarCurrent) {
        this.solarCurrent = solarCurrent;
    }
}
