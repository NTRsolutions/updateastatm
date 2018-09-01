package com.atm.ast.astatm.model;

/**
 * /** * @author AST Inc.  08-05-2017.
 */

public class CallTrackerDataModel {

    String duration;
    String dialledNumber;
    String dialledEmpId;
    String callType;
    String dialerUserId;
    String callTime;
    String id;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDialledNumber() {
        return dialledNumber;
    }

    public void setDialledNumber(String dialledNumber) {
        this.dialledNumber = dialledNumber;
    }

    public String getDialledEmpId() {
        return dialledEmpId;
    }

    public void setDialledEmpId(String dialledEmpId) {
        this.dialledEmpId = dialledEmpId;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getDialerUserId() {
        return dialerUserId;
    }

    public void setDialerUserId(String dialerUserId) {
        this.dialerUserId = dialerUserId;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
