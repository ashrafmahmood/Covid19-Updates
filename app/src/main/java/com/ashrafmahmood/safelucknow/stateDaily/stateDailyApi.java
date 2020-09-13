package com.ashrafmahmood.safelucknow.stateDaily;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface stateDailyApi {
    String BASE_URL = "https://api.covid19india.org/";

    @Headers("Content-Type: application/json")
    @GET("states_daily.json")
    Call<dailyjson> getDailyjson();
}
