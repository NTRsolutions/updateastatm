package com.atm.ast.astatm.model.newmodel;

public class Datasummary {
    private double dg;
    private double vt;
    private double ut;
    private double eb;

    public double getDg() {
        return dg;
    }

    public void setDg(double dg) {
        this.dg = dg;
    }

    public double getVt() {
        return vt;
    }

    public void setVt(double vt) {
        this.vt = vt;
    }

    public double getUt() {
        return ut;
    }

    public void setUt(double ut) {
        this.ut = ut;
    }

    public double getEb() {
        return eb;
    }

    public void setEb(double eb) {
        this.eb = eb;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dg = "+dg+", vt = "+vt+", ut = "+ut+", eb = "+eb+"]";
    }
}
