package com.ashrafmahmood.safelucknow.UPdistrict;

import com.ashrafmahmood.safelucknow.state_district_wise.districtWise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface UPDistrictCasesApi {

    @GET("v2/state_district_wise.json")
    Call<List<UP>> getup();
}
