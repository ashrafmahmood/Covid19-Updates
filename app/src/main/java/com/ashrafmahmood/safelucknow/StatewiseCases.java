package com.ashrafmahmood.safelucknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.COVID19IndiaApi;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.cases;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.datajson;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.statewisedata;

import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatewiseCases extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;


    ArrayList<StateCovid19> statedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statewise_cases);

        recyclerView = findViewById(R.id.stateList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        COVID19IndiaApi covid19IndiaApi = retrofit.create(COVID19IndiaApi.class);

        Call<datajson> call = covid19IndiaApi.getdatajsons();

        call.enqueue(new Callback<datajson>() {
            @Override
            public void onResponse(Call<datajson> call, Response<datajson> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(StatewiseCases.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                datajson data = response.body();
                datajson data1 = response.body();
                ArrayList<cases> s = data.getCases_time_series();
                ArrayList<statewisedata> sd =data1.getStatewise();


                statedata = new ArrayList<StateCovid19>();

                myAdapter = new StateCovid19Adapter(StatewiseCases.this,statedata);
                recyclerView.setAdapter(myAdapter);



                for (statewisedata swd : sd)
                {
                    if(!swd.getState().equals("Total"))
                    {

                        statedata.add(new StateCovid19(swd.getState(),swd.getConfirmed(),swd.getDeltaconfirmed(),swd.getActive(),swd.getRecovered(),swd.getDeltarecovered(),swd.getDeaths(),swd.getDeltadeaths()));
                    }}


            }

            @Override
            public void onFailure(Call<datajson> call, Throwable t) {

                Toast.makeText(StatewiseCases.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
