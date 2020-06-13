package com.ashrafmahmood.safelucknow.state_district_wise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class districtWise {
    @SerializedName("Uttar Pradesh")
    @Expose
    private StateData Uttar_Pradesh;


    public StateData getUttar_Pradesh() {
        return Uttar_Pradesh;
    }
}