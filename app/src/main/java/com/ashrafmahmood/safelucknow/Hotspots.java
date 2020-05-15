package com.ashrafmahmood.safelucknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Hotspots extends AppCompatActivity {

    TextView total, list;
    DatabaseReference reff;
    String ld = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspots);

        total = findViewById(R.id.total);
        list = findViewById(R.id.rz);



        reff = FirebaseDatabase.getInstance().getReference().child("LucknowHotspots");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tot = dataSnapshot.child("Total").getValue().toString();
                List<String> l1 = dataSnapshot.child("List").getValue(new GenericTypeIndicator<List<String>>(){});

                for (int i = 1; i<l1.size(); i++)
                {
                    ld = ld + Integer.toString(i)+". "+l1.get(i)+"\n";
                }

                total.setText(tot);

                list.setText(ld);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
