package com.ashrafmahmood.safelucknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.COVID19IndiaApi;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.datajson;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.statewisedata;
import com.ashrafmahmood.safelucknow.districtzones.ZonesApi;
import com.ashrafmahmood.safelucknow.districtzones.zonedata;
import com.ashrafmahmood.safelucknow.districtzones.zones;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasesInUp extends AppCompatActivity {
    TextView total,active, recov, deaths,rNo,oNo,gNo,test, dTotal,dRecov,dDeaths;
    Spinner rzd,ozd,gzd;
    ImageView refresh;
    ImageView redArrow, greenArrow,greyArrow;

    DatabaseReference reff;

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
                rNo.setText("Red = "+Integer.toString(r));
                gNo.setText("Green = "+Integer.toString(g));
                oNo.setText("Orange = "+Integer.toString(o));



            }

            @Override
            public void onFailure(Call<zonedata> call, Throwable t) {
                Toast.makeText(CasesInUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });





        /*reff = FirebaseDatabase.getInstance().getReference().child("CasesInUp");







                reff.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                        /*refresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((ImageView)findViewById(R.id.refresh)).clearAnimation();





                        String tot = dataSnapshot.child("Total").getValue().toString();
                        String act = dataSnapshot.child("Active").getValue().toString();
                        String rec = dataSnapshot.child("Recovered").getValue().toString();
                        String dea = dataSnapshot.child("Deaths").getValue().toString();

                        String o = dataSnapshot.child("OrangeZones").getValue().toString();
                        String g = dataSnapshot.child("GreenZones").getValue().toString();
                        String r = dataSnapshot.child("RedZones").getValue().toString();




                        total.setText(tot);
                        active.setText(act);
                        recov.setText(rec);
                        deaths.setText(dea);
                        rNo.setText(r);
                        oNo.setText(o);
                        gNo.setText(g);




                            }
                        });



                        for(DataSnapshot item:dataSnapshot.child("RedZonesList").getChildren()){

                            rData.add(item.getValue().toString());
                        }
                        adapter.notifyDataSetChanged();
                        for(DataSnapshot item:dataSnapshot.child("OrangeZonesList").getChildren()){

                            oData.add(item.getValue().toString());
                        }
                        adapter.notifyDataSetChanged();
                        for(DataSnapshot item:dataSnapshot.child("GreenZonesList").getChildren()){

                            gData.add(item.getValue().toString());
                        }
                        adapter.notifyDataSetChanged();












                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.lko:
                Intent intent6 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.MainActivity.class);
                startActivity(intent6);
                return true;
            case R.id.India:
                Intent intent2 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.CasesInIndia.class);
                startActivity(intent2);
                return true;




            case R.id.about:
                Intent intent4 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.About.class);
                startActivity(intent4);
                return true;


            case R.id.sources:
                Intent intent5 = new Intent(CasesInUp.this,  com.ashrafmahmood.safelucknow.Sources.class);
                startActivity(intent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}
