package com.ashrafmahmood.safelucknow.Cases_time_series_statewise_tested;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class datajson {

    @SerializedName("cases_time_series")
    @Expose
    private ArrayList<cases> cases_time_series;

    @SerializedName("statewise")
    @Expose
    private ArrayList<statewisedata> statewise;
    @SerializedName("tested")
    @Expose
    private ArrayList<test> tested;

    public ArrayList<cases> getCases_time_series() {
        return cases_time_series;
    }

    public void setCases_time_series(ArrayList<cases> cases_time_series) {
        this.cases_time_series = cases_time_series;
    }

    public ArrayList<statewisedata> getStatewise() {
        return statewise;
    }

    public void setStatewise(ArrayList<statewisedata> statewise) {
        this.statewise = statewise;
    }

    public ArrayList<test> getTested() {
        return tested;
    }

    public void setTested(ArrayList<test> tested) {
        this.tested = tested;
    }

    @Override
    public String toString() {
        return "datajson{" +
                "cases_time_series=" + cases_time_series +
                ", statewise=" + statewise +
                ", tested=" + tested +
                '}';
    }
}

