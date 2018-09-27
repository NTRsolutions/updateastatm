package com.atm.ast.astatm.model.newmodel;

import java.io.Serializable;

public class Equipment implements Serializable {
    private int Id;
    private String Name;
    boolean isSelectedOrNote = false;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private int qty;
    public int makeID;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getMakeID() {
        return makeID;
    }

    public void setMakeID(int makeID) {
        this.makeID = makeID;
    }

    public boolean isSelectedOrNote() {
        return isSelectedOrNote;
    }

    public void setSelectedOrNote(boolean selectedOrNote) {
        isSelectedOrNote = selectedOrNote;
    }

    private long EquipId;
    private long MakeId;
    private long CapacityId;
    private String SerialNo;
    private long SCMDescId;
    private long SCMCodeId;
    private String QRCode;
    private String Remark;
    private int siteId;
    public long getEquipId() {
        return EquipId;
    }

    public void setEquipId(long equipId) {
        EquipId = equipId;
    }

    public long getMakeId() {
        return MakeId;
    }

    public void setMakeId(long makeId) {
        MakeId = makeId;
    }

    public long getCapacityId() {
        return CapacityId;
    }

    public void setCapacityId(long capacityId) {
        CapacityId = capacityId;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public long getSCMDescId() {
        return SCMDescId;
    }

    public void setSCMDescId(long SCMDescId) {
        this.SCMDescId = SCMDescId;
    }

    public long getSCMCodeId() {
        return SCMCodeId;
    }

    public void setSCMCodeId(long SCMCodeId) {
        this.SCMCodeId = SCMCodeId;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
    private boolean isLastPage=false;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }
}
