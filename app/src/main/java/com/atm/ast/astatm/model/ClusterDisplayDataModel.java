package com.atm.ast.astatm.model;

/**
 * Created by kishore on 10/20/2015.
 */
public class ClusterDisplayDataModel {

    String clusterName; // cn
    String clusterId; // clid
    String clusterTotalSites; // cd
    String clusterTotalAlarmSites; // clv
    String clusterColorCode; // co
    String clusterHeadContact; // cip
    String lastUpdatedDate;
    String totalSites;
    String totalAlarmSites;
    String totalNonCom;
    String totalInvAlarm;
    String totalLowBattery;
    String nsmSites;

    public String getTotalSites() {
        return totalSites;
    }

    public void setTotalSites(String totalSites) {
        this.totalSites = totalSites;
    }

    public String getTotalAlarmSites() {
        return totalAlarmSites;
    }

    public void setTotalAlarmSites(String totalAlarmSites) {
        this.totalAlarmSites = totalAlarmSites;
    }

    public String getTotalNonCom() {
        return totalNonCom;
    }

    public void setTotalNonCom(String totalNonCom) {
        this.totalNonCom = totalNonCom;
    }

    public String getTotalInvAlarm() {
        return totalInvAlarm;
    }

    public void setTotalInvAlarm(String totalInvAlarm) {
        this.totalInvAlarm = totalInvAlarm;
    }

    public String getTotalLowBattery() {
        return totalLowBattery;
    }

    public void setTotalLowBattery(String totalLowBattery) {
        this.totalLowBattery = totalLowBattery;
    }

    public String getNsmSites() {
        return nsmSites;
    }

    public void setNsmSites(String nsmSites) {
        this.nsmSites = nsmSites;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }



    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterTotalSites() {
        return clusterTotalSites;
    }

    public void setClusterTotalSites(String clusterTotalSites) {
        this.clusterTotalSites = clusterTotalSites;
    }

    public String getClusterTotalAlarmSites() {
        return clusterTotalAlarmSites;
    }

    public void setClusterTotalAlarmSites(String clusterTotalAlarmSites) {
        this.clusterTotalAlarmSites = clusterTotalAlarmSites;
    }

    public String getClusterColorCode() {
        return clusterColorCode;
    }

    public void setClusterColorCode(String clusterColorCode) {
        this.clusterColorCode = clusterColorCode;
    }

    public String getClusterHeadContact() {
        return clusterHeadContact;
    }

    public void setClusterHeadContact(String clusterHeadContact) {
        this.clusterHeadContact = clusterHeadContact;
    }


}
