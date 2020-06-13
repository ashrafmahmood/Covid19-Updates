package com.ashrafmahmood.safelucknow.districtzones;

import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.datajson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ZonesApi {

    String BASE_URL = "https://api.covid19india.org/";

    @Headers("Content-Type: application/json")
    @GET("zones.json")
    Call<zonedata> getZonedata();
}
