package com.atm.ast.astatm.model.newmodel;

public class SCMDescription {

    private int Id;
    private String Name;
    private long capcityId;

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

    public long getCapcityId() {
        return capcityId;
    }

    public void setCapcityId(long capcityId) {
        this.capcityId = capcityId;
    }
}
