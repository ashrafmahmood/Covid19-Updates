package com.ashrafmahmood.safelucknow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Sources extends AppCompatActivity {

    TextView s1,s2,s3,s4,s5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);
        s1 = findViewById(R.id.s1);
        s2 = findViewById(R.id.s2);
        s3 = findViewById(R.id.s3);
        s4 = findViewById(R.id.s4);
        s5 = findViewById(R.id.s5);

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+"www.worldometers.info/coronavirus/country/india/"));
                startActivity(intent);

            }
        });
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+"www.mohfw.gov.in/"));
                startActivity(intent);

            }
        });
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+"en.wikipedia.org/wiki/COVID-19_pandemic_in_Uttar_Pradesh"));
                startActivity(intent);


            }
        });
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+"www.jagran.com/uttar-pradesh/lucknow-city-coronavirus-hotspot-in-lucknow-three-new-hotspots-identified-in-lucknow-20242178.html"));
                startActivity(intent);

            }
        });
        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+"www.covid19india.org/"));
                startActivity(intent);

            }
        });




    }
}
