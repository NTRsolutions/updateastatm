package com.atm.ast.astatm.model;

/**
 *@author AST Inc.
 */
public class ActivityListDataModel {

    String planID;
    String planDate;
    String siteId;
    String customerSiteId;
    String siteName;
    String activityId;
    String activityName;
    long dateMilli;
    String taskId;
    String marketingDistributor;
    String circleId;
    String circleName;
    String feId;
    String feName;
    String complaint;
    String submittedOffline;

    public String getSubmittedOffline() {
        return submittedOffline;
    }

    public void setSubmittedOffline(String submittedOffline) {
        this.submittedOffline = submittedOffline;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getFeName() {
        return feName;
    }

    public void setFeName(String feName) {
        this.feName = feName;
    }

    public String getFeId() {
        return feId;
    }

    public void setFeId(String feId) {
        this.feId = feId;
    }

    public String getMarketingDistributor() {
        return marketingDistributor;
    }

    public void setMarketingDistributor(String marketingDistributor) {
        this.marketingDistributor = marketingDistributor;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getDateMilli() {
        return dateMilli;
    }

    public void setDateMilli(long dateMilli) {
        this.dateMilli = dateMilli;
    }


    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
