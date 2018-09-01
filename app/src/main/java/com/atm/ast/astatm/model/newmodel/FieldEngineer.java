package com.atm.ast.astatm.model.newmodel;

public class FieldEngineer {

    private long FieldEngId;

    private String FieldEngName;

    private String ContactNo;

    public long getFieldEngId() {
        return FieldEngId;
    }

    public void setFieldEngId(long fieldEngId) {
        FieldEngId = fieldEngId;
    }

    public String getFieldEngName() {
        return FieldEngName;
    }

    public void setFieldEngName(String fieldEngName) {
        FieldEngName = fieldEngName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [FieldEngId = "+FieldEngId+", FieldEngName = "+FieldEngName+", ContactNo = "+ContactNo+"]";
    }
}
