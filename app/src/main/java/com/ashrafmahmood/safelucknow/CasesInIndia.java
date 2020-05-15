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

    TextView total,active, recov, deaths;


    DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_in_india);

        total = findViewById(R.id.total);
        active = findViewById(R.id.active);
        recov = findViewById(R.id.recov);
        deaths = findViewById(R.id.deaths);






                reff = FirebaseDatabase.getInstance().getReference().child("CasesInIndia");
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
                });







    }
}
