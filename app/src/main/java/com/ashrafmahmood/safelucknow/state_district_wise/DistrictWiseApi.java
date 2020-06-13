package com.ashrafmahmood.safelucknow.state_district_wise;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DistrictWiseApi {


    @Headers("Content-Type: application/json")
    @GET("state_district_wise.json")
    Call<districtWise> getdistrictWise();
}
