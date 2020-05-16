package com.ashrafmahmood.safelucknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    TextView total,active, recov, deaths, rz, oz, gz, tvUpdate, tvLink,dRecov,dTotal,dDeaths;
    Button btnHotspot;
    ImageView redArrow,greenArrow, greyArrow;



    DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        total = findViewById(R.id.total);
        active = findViewById(R.id.active);
        recov = findViewById(R.id.recov);
        deaths = findViewById(R.id.deaths);
        dRecov = findViewById(R.id.dRecov);
        dTotal = findViewById(R.id.dTotal);
        dDeaths = findViewById(R.id.dDeaths);
        redArrow = findViewById(R.id.redArrow);
        greyArrow = findViewById(R.id.greyArrow);
        greenArrow = findViewById(R.id.greenArrow);

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

        btnHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,  com.ashrafmahmood.safelucknow.Hotspots.class);
                startActivity(intent1);
            }
        });


                reff = FirebaseDatabase.getInstance().getReference().child("LucknowCovid19Cases");



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
                        String dR = dataSnapshot.child("DailyRecovered").getValue().toString();
                        String dT = dataSnapshot.child("DailyTotal").getValue().toString();
                        String dD = dataSnapshot.child("DailyDeaths").getValue().toString();

                        redArrow.setVisibility(View.GONE);
                        greenArrow.setVisibility(View.GONE);
                        greyArrow.setVisibility(View.GONE);
                        dTotal.setVisibility(View.GONE);
                        dRecov.setVisibility(View.GONE);
                        dDeaths.setVisibility(View.GONE);



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
                        dRecov.setText(dR);
                        dTotal.setText(dT);
                        dDeaths.setText(dD);
                        if (!dR.equals("0"))
                        {
                            greenArrow.setVisibility(View.VISIBLE);
                            dRecov.setVisibility(View.VISIBLE);

                        }
                        if (!dT.equals("0"))
                        {
                            redArrow.setVisibility(View.VISIBLE);
                            dTotal.setVisibility(View.VISIBLE);
                        }
                        if (!dD.equals("0"))
                        {
                            greyArrow.setVisibility(View.VISIBLE);
                            dDeaths.setVisibility(View.VISIBLE);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





    }



}
