package com.ashrafmahmood.safelucknow;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class CasesInIndia extends AppCompatActivity {

    TextView total, active, recov, deaths, dTotal, dRecov, dDeaths;
    ImageView redArrow, greenArrow, greyArrow;
    Button btnStatewise, btnUPCases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_in_india);
        btnStatewise = findViewById(R.id.btnStatewise);
        btnUPCases = findViewById(R.id.btnUPCases);
        total = findViewById(R.id.total);
        active = findViewById(R.id.active);
        recov = findViewById(R.id.recov);
        deaths = findViewById(R.id.deaths);
        dTotal = findViewById(R.id.dTotal);
        active = findViewById(R.id.active);
        dRecov = findViewById(R.id.dRecov);
        dDeaths = findViewById(R.id.dDeaths);
        dTotal.setVisibility(View.GONE);
        dRecov.setVisibility(View.GONE);
        dDeaths.setVisibility(View.GONE);
        redArrow = findViewById(R.id.redArrow);
        greenArrow = findViewById(R.id.greenArrow);
        greyArrow = findViewById(R.id.greyArrow);
        redArrow.setVisibility(View.GONE);
        greenArrow.setVisibility(View.GONE);
        greyArrow.setVisibility(View.GONE);


        LinearLayout linearLayout1 = findViewById(R.id.layout_red);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) linearLayout1.getBackground();
        animationDrawable1.setEnterFadeDuration(2000);
        animationDrawable1.setExitFadeDuration(2000);
        animationDrawable1.start();
        LinearLayout linearLayout2 = findViewById(R.id.layout_blue);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout2.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        LinearLayout linearLayout3 = findViewById(R.id.layout_green);
        AnimationDrawable animationDrawable3 = (AnimationDrawable) linearLayout3.getBackground();
        animationDrawable3.setEnterFadeDuration(2000);
        animationDrawable3.setExitFadeDuration(2000);
        animationDrawable3.start();
        LinearLayout linearLayout = findViewById(R.id.layout_gray);
        AnimationDrawable animationDrawable4 = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable4.setEnterFadeDuration(2000);
        animationDrawable4.setExitFadeDuration(2000);
        animationDrawable4.start();


        btnStatewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CasesInIndia.this, com.ashrafmahmood.safelucknow.StatewiseCases.class);
                startActivity(intent1);
            }
        });

        btnUPCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CasesInIndia.this, CasesInUp.class);
                startActivity(intent1);

            }
        });


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
                    Toast.makeText(CasesInIndia.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                datajson data = response.body();
                datajson data1 = response.body();
                ArrayList<cases> s = data.getCases_time_series();
                ArrayList<statewisedata> sd = data1.getStatewise();

                NumberFormat myformat = NumberFormat.getInstance();
                for (cases c : s) {

                    total.setText(myformat.format(Integer.parseInt(c.getTotalconfirmed())));
                    recov.setText(myformat.format(Integer.parseInt(c.getTotalrecovered())));
                    deaths.setText(myformat.format(Integer.parseInt(c.getTotaldeceased())));

                    active.setText(myformat.format(Integer.parseInt(c.getTotalconfirmed()) - (Integer.parseInt(c.getTotaldeceased()) + Integer.parseInt(c.getTotalrecovered()))));
                    if (!c.getDailyconfirmed().equals("0")) {
                        dTotal.setText(myformat.format(Integer.parseInt(c.getDailyconfirmed())));
                        dTotal.setVisibility(View.VISIBLE);
                        redArrow.setVisibility(View.VISIBLE);
                    }
                    if (!c.getDailyrecovered().equals("0")) {
                        dRecov.setText(myformat.format(Integer.parseInt(c.getDailyrecovered())));
                        dRecov.setVisibility(View.VISIBLE);
                        greenArrow.setVisibility(View.VISIBLE);
                    }
                    if (!c.getDailydeceased().equals("0")) {
                        dDeaths.setText(myformat.format(Integer.parseInt(c.getDailydeceased())));
                        dDeaths.setVisibility(View.VISIBLE);
                        greyArrow.setVisibility(View.VISIBLE);
                    }

                }


            }

            @Override
            public void onFailure(Call<datajson> call, Throwable t) {

                Toast.makeText(CasesInIndia.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.about:
                Intent intent4 = new Intent(CasesInIndia.this, com.ashrafmahmood.safelucknow.About.class);
                startActivity(intent4);
                return true;


            case R.id.sources:
                Intent intent5 = new Intent(CasesInIndia.this, com.ashrafmahmood.safelucknow.Sources.class);
                startActivity(intent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}
