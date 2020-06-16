package com.ashrafmahmood.safelucknow.UPdistrict;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UP {

    private String state;
    private String statecode;
    private List<District> districtData;

    public String getState() {
        return state;
    }

    public String getStatecode() {
        return statecode;
    }


    public List<District> getDistrictData() {
        return districtData;
    }
}

