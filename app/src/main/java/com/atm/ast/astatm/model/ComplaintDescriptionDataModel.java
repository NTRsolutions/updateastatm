package com.atm.ast.astatm.model;

/**
 * /** * @author AST Inc.  11-10-2017.
 */

public class ComplaintDescriptionDataModel {
    String description;
    String type;
    String lastUpdatedTime;

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
