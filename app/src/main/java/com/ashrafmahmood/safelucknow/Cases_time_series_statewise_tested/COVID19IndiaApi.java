package com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface COVID19IndiaApi {
    String BASE_URL = "https://api.covid19india.org/";

    @Headers("Content-Type: application/json")
    @GET("data.json")
    Call<datajson> getdatajsons();
}
