package com.ashrafmahmood.safelucknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CasesInIndia extends AppCompatActivity {

    TextView total,active, recov, deaths,dRecov,dTotal,dDeaths;
    ImageView redArrow,greenArrow, greyArrow;


    DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_in_india);

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






                reff = FirebaseDatabase.getInstance().getReference().child("CasesInIndia");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String tot = dataSnapshot.child("Total").getValue().toString();
                        String act = dataSnapshot.child("Active").getValue().toString();
                        String rec = dataSnapshot.child("Recovered").getValue().toString();
                        String dea = dataSnapshot.child("Deaths").getValue().toString();
                        String migr = dataSnapshot.child("Migrated").getValue().toString();
                        String dR = dataSnapshot.child("DailyRecovered").getValue().toString();
                        String dT = dataSnapshot.child("DailyTotal").getValue().toString();
                        String dD = dataSnapshot.child("DailyDeaths").getValue().toString();
                        redArrow.setVisibility(View.GONE);
                        greenArrow.setVisibility(View.GONE);
                        greyArrow.setVisibility(View.GONE);
                        dTotal.setVisibility(View.GONE);
                        dRecov.setVisibility(View.GONE);
                        dDeaths.setVisibility(View.GONE);

                        total.setText(tot);
                        active.setText(act);
                        recov.setText(rec);
                        deaths.setText(dea);
                        dRecov.setText(dR);
                        dTotal.setText(dT);
                        dDeaths.setText(dD);
                        if (Integer.parseInt(dR)>0)
                        {
                            greenArrow.setVisibility(View.VISIBLE);
                            dRecov.setVisibility(View.VISIBLE);

                        }
                        if (Integer.parseInt(dT)>0)
                        {
                            redArrow.setVisibility(View.VISIBLE);
                            dTotal.setVisibility(View.VISIBLE);
                        }
                        if (Integer.parseInt(dD)>0)
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
