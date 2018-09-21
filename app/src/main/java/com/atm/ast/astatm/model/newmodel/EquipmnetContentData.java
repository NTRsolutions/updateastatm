package com.atm.ast.astatm.model.newmodel;

import java.util.ArrayList;

public class EquipmnetContentData {

    private int id;
    private int Status;
    private SCMCode[] SCMCode;
    private Equipment[] Equipment;
    private Accessories[] Accessories;
    private String Message;
    private SCMDescription[] SCMDescription;
    private Capacity[] Capacity;
    private Make[] Make;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public SCMCode[] getSCMCode() {
        return SCMCode;
    }

    public void setSCMCode(SCMCode[] SCMCode) {
        this.SCMCode = SCMCode;
    }

    public Equipment[] getEquipment() {
        return Equipment;
    }

    public void setEquipment(Equipment[] Equipment) {
        this.Equipment = Equipment;
    }

    public Accessories[] getAccessories() {
        return Accessories;
    }

    public void setAccessories(Accessories[] Accessories) {
        this.Accessories = Accessories;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public SCMDescription[] getSCMDescription() {
        return SCMDescription;
    }

    public void setSCMDescription(SCMDescription[] SCMDescription) {
        this.SCMDescription = SCMDescription;
    }

    public Capacity[] getCapacity() {
        return Capacity;
    }

    public void setCapacity(Capacity[] Capacity) {
        this.Capacity = Capacity;
    }

    public Make[] getMake() {
        return Make;
    }

    public void setMake(Make[] Make) {
        this.Make = Make;
    }

    @Override
    public String toString() {
        return "ClassPojo [Status = " + Status + ", SCMCode = " + SCMCode + ", Equipment = " + Equipment + ", Accessories = " + Accessories + ", Message = " + Message + ", SCMDescription = " + SCMDescription + ", Capacity = " + Capacity + ", Make = " + Make + "]";
    }

    private ArrayList<AccFeedBack> AccFeedBack;

    public ArrayList<AccFeedBack> getAccFeedBack() {
        return this.AccFeedBack;
    }

    public void setAccFeedBack(ArrayList<AccFeedBack> AccFeedBack) {
        this.AccFeedBack = AccFeedBack;
    }
}
