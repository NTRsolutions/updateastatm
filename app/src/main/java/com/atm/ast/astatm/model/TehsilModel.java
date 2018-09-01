package com.atm.ast.astatm.model;

/**
 * /** * @author AST Inc.  02-09-2018.
 */
public class TehsilModel {

    String tehsilId;
    String tehsilName;
    String districtId;

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

    public String getTehsilName() {
        return tehsilName;
    }

    public void setTehsilName(String tehsilName) {
        this.tehsilName = tehsilName;
    }
}
