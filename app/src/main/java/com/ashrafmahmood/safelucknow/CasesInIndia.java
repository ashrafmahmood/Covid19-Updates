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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.COVID19IndiaApi;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.cases;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.datajson;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.statewisedata;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasesInIndia extends AppCompatActivity {

    TextView total, active, recov, deaths, dTotal, dRecov, dDeaths;
    ImageView redArrow, greenArrow, greyArrow;
    Button btnStatewise, btnUPCases;
    AnyChartView pieIndia, lineIndia, barIndia;
    ScrollView sV;
    int pA, pR, pD;
    ArrayList<String> lineDate = new ArrayList<>();
    ArrayList<String> lineT = new ArrayList<>();
    ArrayList<String> lineR = new ArrayList<>();
    ArrayList<String> lineD = new ArrayList<>();
    ArrayList<Integer> lineA = new ArrayList<>();
    ArrayList<Integer> bardConf = new ArrayList<>();
    ArrayList<Integer> bardRecov = new ArrayList<>();
    ArrayList<Integer> bardDeaths = new ArrayList<>();

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
        pieIndia = findViewById(R.id.pieIndia);
        lineIndia = findViewById(R.id.lineIndia);
        barIndia = findViewById(R.id.barIndia);
        sV = findViewById(R.id.sV);

        sV.smoothScrollTo(0,0);


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

                    lineDate.add(c.getDate());
                    lineT.add(c.getTotalconfirmed());
                    lineR.add(c.getTotalrecovered());
                    lineD.add(c.getTotaldeceased());
                    lineA.add(Integer.parseInt(c.getTotalconfirmed()) - (Integer.parseInt(c.getTotaldeceased()) + Integer.parseInt(c.getTotalrecovered())));
                    bardConf.add(Integer.parseInt(c.getDailyconfirmed()));
                    bardRecov.add(Integer.parseInt(c.getDailyrecovered()));
                    bardDeaths.add(Integer.parseInt(c.getDailydeceased()));
                    total.setText(myformat.format(Integer.parseInt(c.getTotalconfirmed())));
                    recov.setText(myformat.format(Integer.parseInt(c.getTotalrecovered())));
                    deaths.setText(myformat.format(Integer.parseInt(c.getTotaldeceased())));

                    active.setText(myformat.format(Integer.parseInt(c.getTotalconfirmed()) - (Integer.parseInt(c.getTotaldeceased()) + Integer.parseInt(c.getTotalrecovered()))));

                    pA = Integer.parseInt(c.getTotalconfirmed()) - (Integer.parseInt(c.getTotaldeceased()) + Integer.parseInt(c.getTotalrecovered()));
                    pR = Integer.parseInt(c.getTotalrecovered());
                    pD = Integer.parseInt(c.getTotaldeceased());

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

                setLineIndia();
                setBarIndia();
                setPieIndia();




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
    public void setLineIndia()
    {
        APIlib.getInstance().setActiveAnyChartView(lineIndia);
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 10d, 20d, 5d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);


        cartesian.yAxis(0).title("Number of Cases").labels().fontSize(8d);
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d).fontSize(8d);

        List<DataEntry> seriesData = new ArrayList<>();
        for ( int i = 0; i<lineDate.size(); i++)
        {
            seriesData.add(new CustomDataEntry(lineDate.get(i), Integer.parseInt(lineT.get(i)), Integer.parseInt(lineR.get(i)), Integer.parseInt(lineD.get(i)), lineA.get(i)));
        }
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Confirmed");
        series1.color("#d50000");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        Line series4 = cartesian.line(series4Mapping);
        series4.name("Active");
        series4.color("#00a5e5");
        series4.hovered().markers().enabled(true);
        series4.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series4.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Recovered");
        series2.color("#78d663");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Deaths");
        series3.color("#424242");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


        cartesian.title("Total Cases");
        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(10d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        lineIndia.setZoomEnabled(true);
        lineIndia.setChart(cartesian);
    }

    public void setBarIndia()
    {
        APIlib.getInstance().setActiveAnyChartView(barIndia);
        Cartesian cartesian = AnyChart.column();

        cartesian.padding(10d, 10d, 20d, 5d);

        List<DataEntry> barCdata = new ArrayList<>();
        for ( int i = 0; i<lineDate.size(); i++) {
            barCdata.add(new CustomDataEntry2(lineDate.get(i),bardConf.get(i), bardRecov.get(i), bardDeaths.get(i)));
        }

        Set set = Set.instantiate();
        set.data(barCdata);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Column series1 = cartesian.column(series1Mapping);


        series1.tooltip()
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d);
        series1.color("#d50000");
        series1.name("Confirmed");

        Column series2 = cartesian.column(series2Mapping);


        series2.tooltip()
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d);
        series2.color("#78d663");
        series2.name("Recovered");

        Column series3 = cartesian.column(series3Mapping);

        series3.tooltip()
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d);
        series3.color("#424242");
        series3.name("Deaths");

        cartesian.animation(true);
        cartesian.title("Daily Cases");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels();

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);


        cartesian.yAxis(0).title("Number of Cases").labels().fontSize(8d);
        cartesian.xAxis(0).labels().fontSize(8d);
        barIndia.setZoomEnabled(true);
        cartesian.legend().enabled(true);

        barIndia.setChart(cartesian);
    }

   public  void setPieIndia()
    {
        APIlib.getInstance().setActiveAnyChartView(pieIndia);
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        dataEntries.add(new ValueDataEntry("Active", pA));
        dataEntries.add(new ValueDataEntry("Recovered", pR));
        dataEntries.add(new ValueDataEntry("Deaths", pD));
        pie.data(dataEntries);
        pie.palette(new String[]{"#00a5e5", "#78d663", "#999999"});
        pie.padding(10d, 10d, 20d, 5d);
        pieIndia.setChart(pie);
    }
    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
        }

    }
    private class CustomDataEntry2 extends ValueDataEntry {

        CustomDataEntry2(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }


}
