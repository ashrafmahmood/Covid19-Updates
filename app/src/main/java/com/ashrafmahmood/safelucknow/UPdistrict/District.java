package com.ashrafmahmood.safelucknow.UPdistrict;

public class District {

    private  String district;
    private int active;
    private int confirmed;
    private int deceased;
    private int recovered;
    private districtDaily delta;

    public String getDistrict() {
        return district;
    }

    public int getActive() {
        return active;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeceased() {
        return deceased;
    }

    public int getRecovered() {
        return recovered;
    }

    public districtDaily getDelta() {
        return delta;
    }
}
