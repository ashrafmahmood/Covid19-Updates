package com.ashrafmahmood.safelucknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafmahmood.safelucknow.districtzones.ZonesApi;
import com.ashrafmahmood.safelucknow.districtzones.zonedata;
import com.ashrafmahmood.safelucknow.districtzones.zones;
import com.ashrafmahmood.safelucknow.state_district_wise.DistrictWiseApi;
import com.ashrafmahmood.safelucknow.state_district_wise.districtWise;
import com.ashrafmahmood.safelucknow.state_district_wise.lucknowCases;
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

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private static final String TAG = "MainActivity";

    TextView total,active, recov, deaths, rz, oz, gz, tvUpdate, tvLink, dTotal,dRecov,dDeaths;
    Button btnHotspot;
    ImageView redArrow, greenArrow,greyArrow;


    DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




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

        btnHotspot = findViewById(R.id.btnHotspot);

        rz = findViewById(R.id.rz);
        oz = findViewById(R.id.oz);
        gz = findViewById(R.id.gz);
        tvUpdate = findViewById(R.id.tvUpdate);
        tvLink = findViewById(R.id.tvLink);

        rz.setVisibility(View.GONE);
        oz.setVisibility(View.GONE);
        gz.setVisibility(View.GONE);
        tvUpdate.setVisibility(View.GONE);
        tvLink.setVisibility(View.GONE);
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

        btnHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,  com.ashrafmahmood.safelucknow.Hotspots.class);
                startActivity(intent1);
            }
        });


                /*reff = FirebaseDatabase.getInstance().getReference().child("LucknowCovid19Cases");



                    reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String tot = dataSnapshot.child("Total").getValue().toString();
                        String act = dataSnapshot.child("Active").getValue().toString();
                        String rec = dataSnapshot.child("Recovered").getValue().toString();
                        String dea = dataSnapshot.child("Deaths").getValue().toString();
                        String sta = dataSnapshot.child("Status").getValue().toString();
                        String up = dataSnapshot.child("UpdatesAvail").getValue().toString();
                        final String upLink = dataSnapshot.child("UpdateLink").getValue().toString();
                         String sta = dataSnapshot.child("Status").getValue().toString();
                        if (sta.equalsIgnoreCase("R")) {
                            rz.setVisibility(View.VISIBLE);
                            oz.setVisibility(View.GONE);
                            gz.setVisibility(View.GONE);
                        }
                        else if(sta.equalsIgnoreCase("O")) {
                            oz.setVisibility(View.VISIBLE);
                            rz.setVisibility(View.GONE);
                            gz.setVisibility(View.GONE);
                        }
                        else if(sta.equalsIgnoreCase("G")) {
                            gz.setVisibility(View.VISIBLE);
                            oz.setVisibility(View.GONE);
                            rz.setVisibility(View.GONE);
                        }

                        if(up.equalsIgnoreCase("y"))
                        {
                            tvUpdate.setVisibility(View.VISIBLE);
                            tvLink.setVisibility(View.VISIBLE);
                            tvLink.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+upLink));
                                    startActivity(intent);
                                }
                            });
                        }
                        else if (up.equalsIgnoreCase("n"))
                        {
                            tvUpdate.setVisibility(View.GONE);
                            tvLink.setVisibility(View.GONE);
                        }




                        total.setText(tot);
                        active.setText(act);
                        recov.setText(rec);
                        deaths.setText(dea);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DistrictWiseApi districtWiseApi = retrofit.create(DistrictWiseApi.class);

        Call<districtWise> call = districtWiseApi.getdistrictWise();
        call.enqueue(new Callback<districtWise>() {
            @Override
            public void onResponse(Call<districtWise> call, Response<districtWise> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                districtWise data = response.body();

                        lucknowCases lkoCase = data.getUttar_Pradesh().getDistrictData().getLucknow();
                NumberFormat myformat = NumberFormat.getInstance();
                            active.setText(myformat.format(Integer.parseInt(lkoCase.getActive())));
                            total.setText(myformat.format(Integer.parseInt(lkoCase.getConfirmed())));
                            recov.setText(myformat.format(Integer.parseInt(lkoCase.getRecovered())));
                            deaths.setText(myformat.format(Integer.parseInt(lkoCase.getDeceased())));
                            if(!lkoCase.getDelta().getConfirmed().equals("0"))
                            {
                                dTotal.setText(myformat.format(Integer.parseInt(lkoCase.getDelta().getConfirmed())));
                                dTotal.setVisibility(View.VISIBLE);
                                redArrow.setVisibility(View.VISIBLE);
                            }
                            if (!lkoCase.getDelta().getRecovered().equals("0"))
                            {
                                dRecov.setText(myformat.format(Integer.parseInt(lkoCase.getDelta().getRecovered())));
                                dRecov.setVisibility(View.VISIBLE);
                                greenArrow.setVisibility(View.VISIBLE);
                            }
                            if(!lkoCase.getDelta().getDeceased().equals("0"))
                            {
                                dDeaths.setText(myformat.format(Integer.parseInt(lkoCase.getDelta().getDeceased())));
                                dDeaths.setVisibility(View.VISIBLE);
                                greyArrow.setVisibility(View.VISIBLE);
                            }
            }

            @Override
            public void onFailure(Call<districtWise> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage() );
                Toast.makeText(MainActivity.this, "Something went wrong"+t, Toast.LENGTH_SHORT).show();

            }
        });
        ZonesApi zonesApi = retrofit.create(ZonesApi.class);

        Call<zonedata> call1 = zonesApi.getZonedata();
        call1.enqueue(new Callback<zonedata>() {
            @Override
            public void onResponse(Call<zonedata> call, Response<zonedata> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                int r=0, o=0, g=0;
                zonedata zdata = response.body();
                ArrayList<zones> z1 = zdata.getZones();
                for(zones z: z1)
                {
                    if(z.getDistrict().equals("Lucknow"))
                    {
                        if(z.getZone().equals("Red"))
                        {
                            rz.setVisibility(View.VISIBLE);
                            oz.setVisibility(View.GONE);
                            gz.setVisibility(View.GONE);
                        }
                        else if(z.getZone().equals("Orange"))
                        {
                            oz.setVisibility(View.VISIBLE);
                            rz.setVisibility(View.GONE);
                            gz.setVisibility(View.GONE);
                        }
                        else if(z.getZone().equals("Green"))
                        {
                            gz.setVisibility(View.VISIBLE);
                            oz.setVisibility(View.GONE);
                            rz.setVisibility(View.GONE);

                        }


                    }
                }



            }

            @Override
            public void onFailure(Call<zonedata> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });








    }



}
