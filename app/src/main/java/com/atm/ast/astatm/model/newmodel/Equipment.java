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
}
