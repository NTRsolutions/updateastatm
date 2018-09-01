package com.atm.ast.astatm.model;

/**
 * @author AST Inc.  30-08-2018.
 */
public class FillSiteActivityModel {
    String siteId;
    String customerSiteId;
    String siteName;
    String branchName;
    String branchCode;
    String city;
    String pincode;
    String onOffSite;
    String address;
    String circleId;
    String districtId;
    String tehsilId;
    String lat;
    String lon;
    String siteAddressId;
    String functionalFromTime;
    String functionalToTime;

    public String getFunctionalFromTime() {
        return functionalFromTime;
    }

    public void setFunctionalFromTime(String functionalFromTime) {
        this.functionalFromTime = functionalFromTime;
    }

    public String getFunctionalToTime() {
        return functionalToTime;
    }

    public void setFunctionalToTime(String functionalToTime) {
        this.functionalToTime = functionalToTime;
    }

    public String getSiteAddressId() {
        return siteAddressId;
    }

    public void setSiteAddressId(String siteAddressId) {
        this.siteAddressId = siteAddressId;
    }

    public String getCustomerSiteId() {
        return customerSiteId;
    }

    public void setCustomerSiteId(String customerSiteId) {
        this.customerSiteId = customerSiteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getOnOffSite() {
        return onOffSite;
    }

    public void setOnOffSite(String onOffSite) {
        this.onOffSite = onOffSite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getTehsilId() {
        return tehsilId;
    }

    public void setTehsilId(String tehsilId) {
        this.tehsilId = tehsilId;
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
}
