package com.atm.ast.astatm.model;

/**
 * /** * @author AST Inc.  28-06-2017.
 */

public class ComplaintDataModel {

    String id;
    String userId;
    String siteID;
    String clientName;
    String name;
    String mobile;
    String emailId;
    String type;
    String priority;
    String description;
    String proposePlan;
    String time;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProposePlan() {
        return proposePlan;
    }

    public void setProposePlan(String proposePlan) {
        this.proposePlan = proposePlan;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
