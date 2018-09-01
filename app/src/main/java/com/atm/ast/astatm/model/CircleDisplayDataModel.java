package com.atm.ast.astatm.model;

import java.util.Comparator;

/**
 * Created by kishore on 10/6/2015.
 */
public class CircleDisplayDataModel  implements Comparable<CircleDisplayDataModel>  {

    String circleName; //cin
    String totalSites; //cd
    String totalAlarmSites; //civ
    String colorCode; //co
    String circleId; //ciid
    String circleHeadContact; //chp
    String headerTotalSites;
    String headerAlarmSites;
    String headerNonComm;
    String headerInvAlarm;
    String headerLowBattery;
    String headerNmsSites;

    /*
     * Comparator implementation to Sort Order object based on Amount
     */

    public String getHeaderTotalSites() {
        return headerTotalSites;
    }

    public void setHeaderTotalSites(String headerTotalSites) {
        this.headerTotalSites = headerTotalSites;
    }

    public String getHeaderAlarmSites() {
        return headerAlarmSites;
    }

    public void setHeaderAlarmSites(String headerAlarmSites) {
        this.headerAlarmSites = headerAlarmSites;
    }

    public String getHeaderNonComm() {
        return headerNonComm;
    }

    public void setHeaderNonComm(String headerNonComm) {
        this.headerNonComm = headerNonComm;
    }

    public String getHeaderInvAlarm() {
        return headerInvAlarm;
    }

    public void setHeaderInvAlarm(String headerInvAlarm) {
        this.headerInvAlarm = headerInvAlarm;
    }

    public String getHeaderLowBattery() {
        return headerLowBattery;
    }

    public void setHeaderLowBattery(String headerLowBattery) {
        this.headerLowBattery = headerLowBattery;
    }

    public String getHeaderNmsSites() {
        return headerNmsSites;
    }

    public void setHeaderNmsSites(String headerNmsSites) {
        this.headerNmsSites = headerNmsSites;
    }

    public static class orderByName implements Comparator<CircleDisplayDataModel> {

        @Override
        public int compare(CircleDisplayDataModel o1, CircleDisplayDataModel o2) {
            return o1.circleName.compareTo(o2.circleName);
        }
    }


    @Override
    public int compareTo(CircleDisplayDataModel arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * Comparator implementation to Sort Order object based on Amount
     */
    public static class orderByAlarmSites implements Comparator<CircleDisplayDataModel> {

        @Override
        public int compare(CircleDisplayDataModel o1, CircleDisplayDataModel o2) {
            return o1.totalAlarmSites.trim().compareTo(o2.totalAlarmSites.trim());
        }
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    String lastUpdatedDate;

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getCircleHeadContact() {
        return circleHeadContact;
    }

    public void setCircleHeadContact(String circleHeadContact) {
        this.circleHeadContact = circleHeadContact;
    }
}
