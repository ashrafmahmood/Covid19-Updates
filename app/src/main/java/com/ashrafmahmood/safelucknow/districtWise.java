package com.ashrafmahmood.safelucknow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class districtWise {
    @SerializedName("Uttar Pradesh")
    @Expose
    private StateData Uttar_Pradesh;


    public StateData getUttar_Pradesh() {
        return Uttar_Pradesh;
    }
}