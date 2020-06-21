package com.ashrafmahmood.safelucknow;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.COVID19IndiaApi;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.datajson;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.statewisedata;
import com.ashrafmahmood.safelucknow.districtzones.ZonesApi;
import com.ashrafmahmood.safelucknow.districtzones.zonedata;
import com.ashrafmahmood.safelucknow.districtzones.zones;

import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasesInUp extends AppCompatActivity {
    TextView total,active, recov, deaths,rNo,oNo,gNo, dTotal,dRecov,dDeaths;
    Spinner rzd,ozd,gzd;
    ImageView refresh;
    ImageView redArrow, greenArrow,greyArrow;
    Button btnDistrictwise;
    ArrayAdapter<String> adapter;
    ArrayList<String> rData;
    ArrayList<String> oData;
    ArrayList<String> gData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_in_up);
        total = findViewById(R.id.total);
        active = findViewById(R.id.active);
        recov = findViewById(R.id.recov);
        deaths = findViewById(R.id.deaths);
        btnDistrictwise= findViewById(R.id.btnDistrictwise);

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

        rNo = findViewById(R.id.rNo);
        oNo = findViewById(R.id.oNo);
        gNo = findViewById(R.id.gNo);
        refresh = findViewById(R.id.refresh);
        final Animation animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(2000);

        ((ImageView)findViewById(R.id.refresh)).setAnimation(animation);
        refresh.setVisibility(View.GONE);
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
        btnDistrictwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.UPdistrict.UPDistrictWiseCases.class);
                startActivity(intent1);

            }
        });

        rzd = findViewById(R.id.rzd);
        ozd = findViewById(R.id.ozd);
        gzd = findViewById(R.id.gzd);


        rData =new ArrayList<>();
        adapter = new ArrayAdapter<String>(CasesInUp.this, R.layout.red_spinner, rData);
        rzd.setAdapter(adapter);
        oData =new ArrayList<>();
        adapter = new ArrayAdapter<String>(CasesInUp.this, R.layout.orange_spinner, oData);
        ozd.setAdapter(adapter);
        gData =new ArrayList<>();
        adapter = new ArrayAdapter<String>(CasesInUp.this, R.layout.green_spinner, gData);
        gzd.setAdapter(adapter);

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
                    Toast.makeText(CasesInUp.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                datajson data = response.body();
                ArrayList<statewisedata> s = data.getStatewise();
                NumberFormat myformat = NumberFormat.getInstance();
                for(statewisedata c: s)
                {
                    if(c.getStatecode().equals("UP")) {
                        total.setText(myformat.format(Integer.parseInt(c.getConfirmed())));
                        recov.setText(myformat.format(Integer.parseInt(c.getRecovered())));
                        deaths.setText(myformat.format(Integer.parseInt(c.getDeaths())));
                        active.setText(myformat.format(Integer.parseInt(c.getActive())));
                        if(!c.getDeltaconfirmed().equals("0"))
                        {
                            dTotal.setText(myformat.format(Integer.parseInt(c.getDeltaconfirmed())));
                            dTotal.setVisibility(View.VISIBLE);
                            redArrow.setVisibility(View.VISIBLE);
                        }
                        if(!c.getDeltarecovered().equals("0"))
                        {
                            dRecov.setText(myformat.format(Integer.parseInt(c.getDeltarecovered())));
                            dRecov.setVisibility(View.VISIBLE);
                            greenArrow.setVisibility(View.VISIBLE);
                        }
                        if(!c.getDeltadeaths().equals("0"))
                        {
                            dDeaths.setText(myformat.format(Integer.parseInt(c.getDeltadeaths())));
                            dDeaths.setVisibility(View.VISIBLE);
                            greyArrow.setVisibility(View.VISIBLE);
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<datajson> call, Throwable t) {

                Toast.makeText(CasesInUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

        ZonesApi zonesApi = retrofit.create(ZonesApi.class);

        Call<zonedata> call1 = zonesApi.getZonedata();
        call1.enqueue(new Callback<zonedata>() {
            @Override
            public void onResponse(Call<zonedata> call, Response<zonedata> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(CasesInUp.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                int r=0, o=0, g=0;
                zonedata zdata = response.body();
                ArrayList<zones> z1 = zdata.getZones();
                for(zones z: z1)
                {
                    if(z.getStatecode().equals("UP"))
                    {
                        if(z.getZone().equals("Red"))
                        {
                            rData.add(z.getDistrict());
                            r++;
                        }
                        else if(z.getZone().equals("Green"))
                        {
                            gData.add(z.getDistrict());
                            g++;

                        }
                        else if(z.getZone().equals("Orange"))
                        {
                            oData.add(z.getDistrict());
                            o++;
                        }
                    }
                }
                rNo.setText("Red - "+Integer.toString(r));
                gNo.setText("Green - "+Integer.toString(g));
                oNo.setText("Orange -  "+Integer.toString(o));



            }

            @Override
            public void onFailure(Call<zonedata> call, Throwable t) {
                Toast.makeText(CasesInUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
