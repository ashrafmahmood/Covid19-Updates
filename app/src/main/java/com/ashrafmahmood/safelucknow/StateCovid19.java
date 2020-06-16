package com.ashrafmahmood.safelucknow;

public class StateCovid19 {


    private String state;
    private String sTotal;
    private String sdTotal;
    private String sActive;

    private String sRecov;
    private String sdRecov;
    private String sDeaths;
    private String sdDeaths;

    public StateCovid19(String state, String sTotal, String sdTotal, String sActive, String sRecov, String sdRecov, String sDeaths, String sdDeaths) {
        this.state = state;
        this.sTotal = sTotal;
        this.sdTotal = sdTotal;
        this.sActive = sActive;
        this.sRecov = sRecov;
        this.sdRecov = sdRecov;
        this.sDeaths = sDeaths;
        this.sdDeaths = sdDeaths;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getsTotal() {
        return sTotal;
    }

    public void setsTotal(String sTotal) {
        this.sTotal = sTotal;
    }

    public String getSdTotal() {
        return sdTotal;
    }

    public void setSdTotal(String sdTotal) {
        this.sdTotal = sdTotal;
    }

    public String getsActive() {
        return sActive;
    }

    public void setsActive(String sActive) {
        this.sActive = sActive;
    }

    public String getsRecov() {
        return sRecov;
    }

    public void setsRecov(String sRecov) {
        this.sRecov = sRecov;
    }

    public String getSdRecov() {
        return sdRecov;
    }

    public void setSdRecov(String sdRecov) {
        this.sdRecov = sdRecov;
    }

    public String getsDeaths() {
        return sDeaths;
    }

    public void setsDeaths(String sDeaths) {
        this.sDeaths = sDeaths;
    }

    public String getSdDeaths() {
        return sdDeaths;
    }

    public void setSdDeaths(String sdDeaths) {
        this.sdDeaths = sdDeaths;
    }
}