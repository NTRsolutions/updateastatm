package com.atm.ast.astatm.model;

/**
 * /** * @author AST Inc.  26-12-2018.
 */
public class SiteVisitDataModel {

    String visitDate;
    String faultEquipmentMake;
    String newEquipmentInstalledMake;
    String feName;

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getFaultEquipmentMake() {
        return faultEquipmentMake;
    }

    public void setFaultEquipmentMake(String faultEquipmentMake) {
        this.faultEquipmentMake = faultEquipmentMake;
    }

    public String getNewEquipmentInstalledMake() {
        return newEquipmentInstalledMake;
    }

    public void setNewEquipmentInstalledMake(String newEquipmentInstalledMake) {
        this.newEquipmentInstalledMake = newEquipmentInstalledMake;
    }

    public String getFeName() {
        return feName;
    }

    public void setFeName(String feName) {
        this.feName = feName;
    }
}
