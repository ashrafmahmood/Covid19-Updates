package com.ashrafmahmood.safelucknow.UPdistrict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.COVID19IndiaApi;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.cases;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.datajson;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.statewisedata;
import com.ashrafmahmood.safelucknow.R;
import com.ashrafmahmood.safelucknow.StateCovid19;
import com.ashrafmahmood.safelucknow.StateCovid19Adapter;
import com.ashrafmahmood.safelucknow.StatewiseCases;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UPDistrictWiseCases extends AppCompatActivity {

    RecyclerView recyclerViewd;
    RecyclerView.Adapter myAdapterd;
    RecyclerView.LayoutManager layoutManagerd;


    ArrayList<UPDistrictCovid19> districtdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updistrict_wise_cases);

        recyclerViewd = findViewById(R.id.districtList);
        recyclerViewd.setHasFixedSize(true);
        layoutManagerd = new LinearLayoutManager(this);
        recyclerViewd.setLayoutManager(layoutManagerd);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UPDistrictCasesApi upDistrictCasesApi = retrofit.create(UPDistrictCasesApi.class);
        Call<List<UP>> call = upDistrictCasesApi.getup();
        call.enqueue(new Callback<List<UP>>() {
            @Override
            public void onResponse(Call<List<UP>> call, Response<List<UP>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UPDistrictWiseCases.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<UP> data = response.body();
                districtdata = new ArrayList<UPDistrictCovid19>();

                myAdapterd = new UPDistrictCovid19Adapter(UPDistrictWiseCases.this,districtdata);
                recyclerViewd.setAdapter(myAdapterd);
                for(UP up : data)
                {
                    if(up.getState().equals("Uttar Pradesh"))
                    {
                        List<District> dD = up.getDistrictData();
                        for (District d : dD) {
                            districtdata.add(new UPDistrictCovid19(d.getDistrict(), Integer.toString(d.getConfirmed()), Integer.toString(d.getDelta().getConfirmed()), Integer.toString(d.getActive()), Integer.toString(d.getRecovered()), Integer.toString(d.getDelta().getRecovered()), Integer.toString(d.getDeceased()), Integer.toString(d.getDelta().getDeceased())));
                        }
                    }
                }





            }

            @Override
            public void onFailure(Call<List<UP>> call, Throwable t) {
                Toast.makeText(UPDistrictWiseCases.this, "Something went wrong", Toast.LENGTH_SHORT).show();


            }
        });



    }
}
