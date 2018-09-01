package com.atm.ast.astatm.model;

/**
 * Created by kishore on 04/04/2018.
 */
public class FeTrackerChildItemModel {
    String transitTime;
    String status;
    String color;
    String siteId;
    String siteName;
    String customerSiteId;
    String circle;
    String district;
    String activityStatus;
    String distance;
    String userId;
    String siteBaseDistanceFromSite;
    String siteLong;
    String siteLat;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransitTime() {
        return transitTime;
    }

    public void setTransitTime(String transitTime) {
        this.transitTime = transitTime;
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

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setDistanceFromSite(String siteBaseDistanceFromSite) {
        this.siteBaseDistanceFromSite = siteBaseDistanceFromSite;
    }
    public String getDistanceFromSite() {
        return  siteBaseDistanceFromSite;
    }
    public String getLat() {
        return siteLat;
    }

    public void setLat(String siteLat) {
        this.siteLat = siteLat;
    }
    public String getLon() {
        return siteLong;
    }

    public void setLon(String siteLong) {
        this.siteLong = siteLong;
    }

}