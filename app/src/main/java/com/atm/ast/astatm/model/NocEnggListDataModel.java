package com.atm.ast.astatm.model;

/**
  * @author AST Inc. 11/18/2015.
 */
public class NocEnggListDataModel {

    String enggId;
    String enggName;
    String enggContact;
    String enggType;
    String lastUpdatedDate;

    public String getEnggType() {
        return enggType;
    }

    public void setEnggType(String enggType) {
        this.enggType = enggType;
    }

    public String getEnggContact() {
        return enggContact;
    }

    public void setEnggContact(String enggContact) {
        this.enggContact = enggContact;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getEnggName() {
        return enggName;
    }

    public void setEnggName(String enggName) {
        this.enggName = enggName;
    }

    public String getEnggId() {
        return enggId;
    }

    public void setEnggId(String enggId) {
        this.enggId = enggId;
    }


}
