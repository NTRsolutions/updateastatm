package com.atm.ast.astatm.model.newmodel;

public class District {

    private Tehsil[] Tehsil;
    private String District;
    private long DistrictId;


    public Tehsil[] getTehsil() {
        return Tehsil;
    }

    public void setTehsil(Tehsil[] tehsil) {
        Tehsil = tehsil;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public long getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(long districtId) {
        DistrictId = districtId;
    }
}
