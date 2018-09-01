package com.atm.ast.astatm.model.newmodel;

public class NOCEngineer {

    private long NocEngId;

    private String ContactNo;

    private String NocEngName;

    public long getNocEngId() {
        return NocEngId;
    }

    public void setNocEngId(long nocEngId) {
        NocEngId = nocEngId;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getNocEngName() {
        return NocEngName;
    }

    public void setNocEngName(String nocEngName) {
        NocEngName = nocEngName;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [NocEngId = "+NocEngId+", ContactNo = "+ContactNo+", NocEngName = "+NocEngName+"]";
    }
}
