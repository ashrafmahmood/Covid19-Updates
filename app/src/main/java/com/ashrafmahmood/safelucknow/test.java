package com.ashrafmahmood.safelucknow;

public class test {

   private String individualstestedperconfirmedcase;
    private String positivecasesfromsamplesreported;
    private String samplereportedtoday;
    private String source;
    private String testpositivityrate;
    private String testsconductedbyprivatelabs;
    private String testsperconfirmedcase;
    private String testspermillion;
    private String totalindividualstested;
    private String totalpositivecases;
    private String totalsamplestested;
    private String updatetimestamp;

    public String getIndividualstestedperconfirmedcase() {
        return individualstestedperconfirmedcase;
    }

    public void setIndividualstestedperconfirmedcase(String individualstestedperconfirmedcase) {
        this.individualstestedperconfirmedcase = individualstestedperconfirmedcase;
    }

    public String getPositivecasesfromsamplesreported() {
        return positivecasesfromsamplesreported;
    }

    public void setPositivecasesfromsamplesreported(String positivecasesfromsamplesreported) {
        this.positivecasesfromsamplesreported = positivecasesfromsamplesreported;
    }

    public String getSamplereportedtoday() {
        return samplereportedtoday;
    }

    public void setSamplereportedtoday(String samplereportedtoday) {
        this.samplereportedtoday = samplereportedtoday;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTestpositivityrate() {
        return testpositivityrate;
    }

    public void setTestpositivityrate(String testpositivityrate) {
        this.testpositivityrate = testpositivityrate;
    }

    public String getTestsconductedbyprivatelabs() {
        return testsconductedbyprivatelabs;
    }

    public void setTestsconductedbyprivatelabs(String testsconductedbyprivatelabs) {
        this.testsconductedbyprivatelabs = testsconductedbyprivatelabs;
    }

    public String getTestsperconfirmedcase() {
        return testsperconfirmedcase;
    }

    public void setTestsperconfirmedcase(String testsperconfirmedcase) {
        this.testsperconfirmedcase = testsperconfirmedcase;
    }

    public String getTestspermillion() {
        return testspermillion;
    }

    public void setTestspermillion(String testspermillion) {
        this.testspermillion = testspermillion;
    }

    public String getTotalindividualstested() {
        return totalindividualstested;
    }

    public void setTotalindividualstested(String totalindividualstested) {
        this.totalindividualstested = totalindividualstested;
    }

    public String getTotalpositivecases() {
        return totalpositivecases;
    }

    public void setTotalpositivecases(String totalpositivecases) {
        this.totalpositivecases = totalpositivecases;
    }

    public String getTotalsamplestested() {
        return totalsamplestested;
    }

    public void setTotalsamplestested(String totalsamplestested) {
        this.totalsamplestested = totalsamplestested;
    }

    public String getUpdatetimestamp() {
        return updatetimestamp;
    }

    public void setUpdatetimestamp(String updatetimestamp) {
        this.updatetimestamp = updatetimestamp;
    }

    @Override
    public String toString() {
        return "test{" +
                "individualstestedperconfirmedcase='" + individualstestedperconfirmedcase + '\'' +
                ", positivecasesfromsamplesreported='" + positivecasesfromsamplesreported + '\'' +
                ", samplereportedtoday='" + samplereportedtoday + '\'' +
                ", source='" + source + '\'' +
                ", testpositivityrate='" + testpositivityrate + '\'' +
                ", testsconductedbyprivatelabs='" + testsconductedbyprivatelabs + '\'' +
                ", testsperconfirmedcase='" + testsperconfirmedcase + '\'' +
                ", testspermillion='" + testspermillion + '\'' +
                ", totalindividualstested='" + totalindividualstested + '\'' +
                ", totalpositivecases='" + totalpositivecases + '\'' +
                ", totalsamplestested='" + totalsamplestested + '\'' +
                ", updatetimestamp='" + updatetimestamp + '\'' +
                '}';
    }
}
