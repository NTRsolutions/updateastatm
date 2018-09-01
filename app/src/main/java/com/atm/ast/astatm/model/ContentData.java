package com.atm.ast.astatm.model;

public class ContentData {
    private int Status;

    private String Remarks;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }


    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "ClassPojo [Status = " + Status + ", Message = " + Message + "]";
    }
}
