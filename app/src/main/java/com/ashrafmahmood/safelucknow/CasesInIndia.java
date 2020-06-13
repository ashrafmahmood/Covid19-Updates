package com.ashrafmahmood.safelucknow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.COVID19IndiaApi;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.cases;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.datajson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasesInIndia extends AppCompatActivity {

    TextView total,active, recov, deaths, jData,test ;
    private  static final String BASE_URL = "https://api.covid19india.org/";
    private static final String TAG = "CasesInIndia";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_in_india);

        total = (TextView)findViewById(R.id.total);
        active = (TextView)findViewById(R.id.active);
        recov = (TextView)findViewById(R.id.recov);
        deaths = (TextView)findViewById(R.id.deaths);
        jData = (TextView)findViewById(R.id.jData);
        test = findViewById(R.id.test);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        COVID19IndiaApi covid19IndiaApi = retrofit.create(COVID19IndiaApi.class);

        Call<datajson> call = covid19IndiaApi.getdatajsons();

        call.enqueue(new Callback<datajson>() {
            @Override
            public void onResponse(Call<datajson> call, Response<datajson> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(CasesInIndia.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                datajson data = response.body();
                ArrayList<cases> s = data.getCases_time_series();
                String str="";
                for(cases c: s)
                {
                    str = c.getDailyconfirmed();
                    total.setText(c.getTotalconfirmed());
                    recov.setText(c.getTotalrecovered());
                    deaths.setText(c.getTotaldeceased());
                    int n = Integer.parseInt(c.getTotalconfirmed())-(Integer.parseInt(c.getTotaldeceased())+Integer.parseInt(c.getTotalrecovered()));
                    active.setText(Integer.toString(n));

                }

            }

            @Override
            public void onFailure(Call<datajson> call, Throwable t) {

                Toast.makeText(CasesInIndia.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });






                /*reff = FirebaseDatabase.getInstance().getReference().child("CasesInIndia");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String tot = dataSnapshot.child("Total").getValue().toString();
                        String act = dataSnapshot.child("Active").getValue().toString();
                        String rec = dataSnapshot.child("Recovered").getValue().toString();
                        String dea = dataSnapshot.child("Deaths").getValue().toString();
                        String migr = dataSnapshot.child("Migrated").getValue().toString();
                        total.setText(tot);
                        active.setText(act);
                        recov.setText(rec);
                        deaths.setText(dea);
                       
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/







    }
}
