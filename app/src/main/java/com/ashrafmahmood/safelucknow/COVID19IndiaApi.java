package com.ashrafmahmood.safelucknow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface COVID19IndiaApi {

    @GET("data.json")
    Call<List<datajson>> getdatajsons();
}
