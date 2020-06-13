package com.ashrafmahmood.safelucknow.state_district_wise;

public class lucknowdaily {
    private String confirmed;
    private String deceased;
    private String recovered;

    public String getConfirmed() {
        return confirmed;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public void setDeceased(String deceased) {
        this.deceased = deceased;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    @Override
    public String toString() {
        return "lucknowdaily{" +
                "confirmed='" + confirmed + '\'' +
                ", deceased='" + deceased + '\'' +
                ", recovered='" + recovered + '\'' +
                '}';
    }
}
