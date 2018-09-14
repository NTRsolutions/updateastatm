package com.atm.ast.astatm.model.newmodel;

public class SCMCode {

    private long Id;
    private String Name;
    private long capcityId;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
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
