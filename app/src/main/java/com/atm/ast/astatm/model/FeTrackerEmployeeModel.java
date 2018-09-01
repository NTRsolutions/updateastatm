package com.atm.ast.astatm.model;

import java.util.ArrayList;

/**
 ** * @author AST Inc.  3/21/2018.
 */
public class FeTrackerEmployeeModel {

    String userId;
    String name;
    String contactNo;
    String transitTime;
    String lat;
    String lon;
    String status;
    String color;
    String siteName;
    String customerSiteId;
    String circle;
    String district;
    int totalTransit;
    String shortAddress;
    String distance;
    String lastTrackedTime;
    String activity;

    public String getLastTrackedTime() {
        return lastTrackedTime;
    }

    public void setLastTrackedTime(String lastTrackedTime) {
        this.lastTrackedTime = lastTrackedTime;
    }
   


    ArrayList<FeTrackerChildItemModel> arrayListFeTrackerChild;

    public ArrayList<FeTrackerChildItemModel> getArrayListFeTrackerChild() {
        return arrayListFeTrackerChild;
    }

    public void setArrayListFeTrackerChild(ArrayList<FeTrackerChildItemModel> arrayListFeTrackerChild) {
        this.arrayListFeTrackerChild = arrayListFeTrackerChild;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public int getTotalTransit() {
        return totalTransit;
    }

    public void setTotalTransit(int totalTransit) {
        this.totalTransit = totalTransit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String namre) {
        this.name = namre;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getTransitTime() {
        return transitTime;
    }

    public void setTransitTime(String transitTime) {
        this.transitTime = transitTime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCustomerSiteId() {
        return customerSiteId;
    }

    public void setCustomerSiteId(String customerSiteId) {
        this.customerSiteId = customerSiteId;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
