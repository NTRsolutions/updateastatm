package com.atm.ast.astatm.model;

/**
 * /** * @author AST Inc.  04-01-2017.
 */
public class EBRunHourModel {
    String Date;
    String EBRH;
    String DGRH;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDGRH() {
        return DGRH;
    }

    public void setDGRH(String DGRH) {
        this.DGRH = DGRH;
    }

    public String getEBRH() {
        return EBRH;
    }

    public void setEBRH(String EBRH) {
        this.EBRH = EBRH;
    }
}
