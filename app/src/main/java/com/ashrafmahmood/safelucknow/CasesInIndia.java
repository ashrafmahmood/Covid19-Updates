package com.ashrafmahmood.safelucknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.COVID19IndiaApi;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.cases;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.datajson;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.statewisedata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasesInIndia extends AppCompatActivity {

    TextView total,active, recov, deaths, dTotal,dRecov,dDeaths;
    ImageView redArrow, greenArrow,greyArrow;


    DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_in_india);

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
                ArrayList<cases> s = data.getCases_time_series();
                NumberFormat myformat = NumberFormat.getInstance();
                for(cases c: s)
                {

                        total.setText(myformat.format(Integer.parseInt(c.getTotalconfirmed())));
                        recov.setText(myformat.format(Integer.parseInt(c.getTotalrecovered())));
                        deaths.setText(myformat.format(Integer.parseInt(c.getTotaldeceased())));

                        active.setText(myformat.format(Integer.parseInt(c.getTotalconfirmed())-(Integer.parseInt(c.getTotaldeceased())+Integer.parseInt(c.getTotalrecovered()))));
                        if (!c.getDailyconfirmed().equals("0"))
                        {
                            dTotal.setText(myformat.format(Integer.parseInt(c.getDailyconfirmed())));
                            dTotal.setVisibility(View.VISIBLE);
                            redArrow.setVisibility(View.VISIBLE);
                        }
                    if (!c.getDailyrecovered().equals("0"))
                    {
                        dRecov.setText(myformat.format(Integer.parseInt(c.getDailyrecovered())));
                        dRecov.setVisibility(View.VISIBLE);
                        greenArrow.setVisibility(View.VISIBLE);
                    }
                    if (!c.getDailydeceased().equals("0"))
                    {
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
