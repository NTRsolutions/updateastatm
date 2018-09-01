package com.atm.ast.astatm.model.newmodel;

import com.atm.ast.astatm.model.newmodel.Data;

public class ServiceContentData {
    private int status;
    private Data[] data;
    private Header[] header;
    private Datasummary[] datasummary;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public Header[] getHeader() {
        return header;
    }

    public void setHeader(Header[] header) {
        this.header = header;
    }

    public Datasummary[] getDatasummary() {
        return datasummary;
    }

    public void setDatasummary(Datasummary[] datasummary) {
        this.datasummary = datasummary;
    }

    private FieldEngineer[] FieldEngineer;
    private NOCEngineer[] NOCEngineer;

    public com.atm.ast.astatm.model.newmodel.FieldEngineer[] getFieldEngineer() {
        return FieldEngineer;
    }

    public void setFieldEngineer(com.atm.ast.astatm.model.newmodel.FieldEngineer[] fieldEngineer) {
        FieldEngineer = fieldEngineer;
    }

    public com.atm.ast.astatm.model.newmodel.NOCEngineer[] getNOCEngineer() {
        return NOCEngineer;
    }

    public void setNOCEngineer(com.atm.ast.astatm.model.newmodel.NOCEngineer[] NOCEngineer) {
        this.NOCEngineer = NOCEngineer;
    }

    @Override
    public String toString() {
        return "ClassPojo [status = " + status + ", data = " + data + "]";
    }
}
