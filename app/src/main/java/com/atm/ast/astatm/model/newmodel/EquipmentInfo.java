package com.atm.ast.astatm.model.newmodel;

public class EquipmentInfo {

    int id;
    String EquipId, MakeId, CapacityId, SerialNo, SCMDescId, SCMCodeId, QRCode, remarke;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipId() {
        return EquipId;
    }

    public void setEquipId(String equipId) {
        EquipId = equipId;
    }

    public String getMakeId() {
        return MakeId;
    }

    public void setMakeId(String makeId) {
        MakeId = makeId;
    }

    public String getCapacityId() {
        return CapacityId;
    }

    public void setCapacityId(String capacityId) {
        CapacityId = capacityId;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getSCMDescId() {
        return SCMDescId;
    }

    public void setSCMDescId(String SCMDescId) {
        this.SCMDescId = SCMDescId;
    }

    public String getSCMCodeId() {
        return SCMCodeId;
    }

    public void setSCMCodeId(String SCMCodeId) {
        this.SCMCodeId = SCMCodeId;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getRemarke() {
        return remarke;
    }

    public void setRemarke(String remarke) {
        this.remarke = remarke;
    }

    String accId;
    String accStatus;

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }


    private String SiteId;
    int Status;

    public String getSiteId() {
        return SiteId;
    }

    public void setSiteId(String siteId) {
        SiteId = siteId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
