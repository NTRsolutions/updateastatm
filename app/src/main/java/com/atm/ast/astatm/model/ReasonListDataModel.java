package com.atm.ast.astatm.model;

/**
 * Created by kishore on 11/27/2015.
 */
public class ReasonListDataModel {

    String reasonId;
    String reasonName;
    String activityId;
    String lastUpdatedDate;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
