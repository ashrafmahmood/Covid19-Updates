package com.ashrafmahmood.safelucknow;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.datajson;
import com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested.statewisedata;
import com.ashrafmahmood.safelucknow.stateDaily.dailyjson;
import com.ashrafmahmood.safelucknow.stateDaily.stateDaily;
import com.ashrafmahmood.safelucknow.stateDaily.stateDailyApi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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
    AnyChartView pieUP, barUP, lineUP;
    Button btnDistrictwise;
    ScrollView sV;
    int pA, pR, pD;
    ArrayList<String> date = new ArrayList<>();
    ArrayList<Integer> lineT = new ArrayList<>();
    ArrayList<Integer> lineR = new ArrayList<>();
    ArrayList<Integer> lineD = new ArrayList<>();
    ArrayList<Integer> lineA = new ArrayList<>();
    ArrayList<Integer> bardConf = new ArrayList<>();
    ArrayList<Integer> bardRecov = new ArrayList<>();
    ArrayList<Integer> bardDeaths = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_in_up);
        total = findViewById(R.id.total);
        active = findViewById(R.id.active);
        recov = findViewById(R.id.recov);
        deaths = findViewById(R.id.deaths);
        btnDistrictwise= findViewById(R.id.btnDistrictwise);
        pieUP = findViewById(R.id.pieUP);
        barUP = findViewById(R.id.barUP);
        lineUP = findViewById(R.id.lineUP);

        sV = findViewById(R.id.sV);

        sV.smoothScrollTo(0,0);

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

                        pA = Integer.parseInt(c.getActive());
                        pR = Integer.parseInt(c.getRecovered());
                        pD = Integer.parseInt(c.getDeaths());

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
                setPieUP();

            }

            @Override
            public void onFailure(Call<datajson> call, Throwable t) {

                Toast.makeText(CasesInUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });


        stateDailyApi statedailyApi = retrofit.create(stateDailyApi.class);
        Call<dailyjson> call2 = statedailyApi.getDailyjson();
        call2.enqueue(new Callback<dailyjson>() {
            @Override
            public void onResponse(Call<dailyjson> call, Response<dailyjson> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(CasesInUp.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                dailyjson djson = response.body();
                ArrayList<stateDaily> sdaily = djson.getStates_daily();
                int c=0,r=0,d=0,a=0;
                for(stateDaily daily : sdaily)
                {

                    if(daily.getStatus().equalsIgnoreCase("Confirmed"))
                    {
                        date.add(daily.getDate());
                        bardConf.add(Integer.parseInt(daily.getUp()));
                        c=c+Integer.parseInt(daily.getUp());
                        lineT.add(c);
                    }
                    if(daily.getStatus().equalsIgnoreCase("Recovered"))
                    {
                        bardRecov.add(Integer.parseInt(daily.getUp()));
                        r=r+Integer.parseInt(daily.getUp());
                        lineR.add(r);
                    }
                    if(daily.getStatus().equalsIgnoreCase("Deceased"))
                    {
                        bardDeaths.add(Integer.parseInt(daily.getUp()));
                        d=d+Integer.parseInt(daily.getUp());
                        lineD.add(d);
                        a=(c-(d+r));
                        lineA.add(a);

                    }


                }
                setBarUP();
                setLineUP();



            }

            @Override
            public void onFailure(Call<dailyjson> call, Throwable t) {
                Toast.makeText(CasesInUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });



    }
    public void setLineUP()
    {
        APIlib.getInstance().setActiveAnyChartView(lineUP);
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 15d, 20d, 5d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);


        cartesian.yAxis(0).title("Number of Cases").labels().fontSize(8d);
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d).fontSize(8d);

        List<DataEntry> seriesData = new ArrayList<>();
        for ( int i = 0; i<date.size(); i++)
        {
            seriesData.add(new CustomDataEntry(date.get(i), lineT.get(i), lineR.get(i), lineD.get(i), lineA.get(i)));
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
        lineUP.setZoomEnabled(true);
        lineUP.setChart(cartesian);
    }
    public void setBarUP()
    {
        APIlib.getInstance().setActiveAnyChartView(barUP);
        Cartesian cartesian = AnyChart.column();

        cartesian.padding(10d, 15d, 20d, 5d);

        List<DataEntry> barCdata = new ArrayList<>();
        for ( int i = 0; i<date.size(); i++) {
            barCdata.add(new CustomDataEntry2(date.get(i),bardConf.get(i), bardRecov.get(i), bardDeaths.get(i)));
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
        barUP.setZoomEnabled(true);
        cartesian.legend().enabled(true);

        barUP.setChart(cartesian);
    }

    public  void setPieUP()
    {
        APIlib.getInstance().setActiveAnyChartView(pieUP);
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        dataEntries.add(new ValueDataEntry("Active", pA));
        dataEntries.add(new ValueDataEntry("Recovered", pR));
        dataEntries.add(new ValueDataEntry("Deaths", pD));
        pie.data(dataEntries);
        pie.palette(new String[]{"#00a5e5", "#78d663", "#999999"});
        pie.padding(10d, 10d, 20d, 5d);
        pieUP.setChart(pie);
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
