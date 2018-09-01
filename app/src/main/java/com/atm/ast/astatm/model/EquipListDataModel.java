package com.atm.ast.astatm.model;

/**
 * /** * @author AST Inc.  02-02-2018.
 */

public class EquipListDataModel {
    int id;
    String equipId;
    String equipName;
    String equipParentId;
    String equipTime;

    public String getEquipParentId() {
        return equipParentId;
    }

    public void setEquipParentId(String equipParentId) {
        this.equipParentId = equipParentId;
    }

    public String getEquipTime() {
        return equipTime;
    }

    public void setEquipTime(String equipTime) {
        this.equipTime = equipTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipId() {
        return equipId;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }
}
