package com.atm.ast.astatm.model;

/**
  * @author AST Inc. 1/28/2016.
 */
public class TransitDataModel {

    String id;
    String userId;
    String type;
    String siteId;
    String siteName;
    String siteUserId;
    String dateTime;
    String longitude;
    String latitude;
    String calculatedDistance;
    String calculatedAmount;
    String address;
    String actualAmt;
    String actualKms;
    String hotelExpense;
    String actualHotelExpense;
    String remarks;

    public String getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(String calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }

    public String getHotelExpense() {
        return hotelExpense;
    }

    public void setHotelExpense(String hotelExpense) {
        this.hotelExpense = hotelExpense;
    }

    public String getActualHotelExpense() {
        return actualHotelExpense;
    }

    public void setActualHotelExpense(String actualHotelExpense) {
        this.actualHotelExpense = actualHotelExpense;
    }

    public String getCalcilatedDistance() {
        return calculatedDistance;
    }

    public void setCalcilatedDistance(String calcilatedDistance) {
        this.calculatedDistance = calcilatedDistance;
    }

    public String getCalculatedAmount() {
        return calculatedAmount;
    }

    public void setCalculatedAmount(String calculatedAmount) {
        this.calculatedAmount = calculatedAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(String actualAmt) {
        this.actualAmt = actualAmt;
    }

    public String getActualKms() {
        return actualKms;
    }

    public void setActualKms(String actualKms) {
        this.actualKms = actualKms;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUserId() {
        return siteUserId;
    }

    public void setSiteUserId(String siteUserId) {
        this.siteUserId = siteUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
