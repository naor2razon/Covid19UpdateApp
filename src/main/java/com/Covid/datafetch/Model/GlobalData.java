package com.Covid.datafetch.Model;

public class GlobalData {

    private long cases;

    private long deaths;

    private long recovered;

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public long getCases() {
        return cases;
    }

    public void setCases(long cases) {
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "GlobalData{" +
                "cases=" + cases +
                ", deaths=" + deaths +
                ", recovered=" + recovered +
                '}';
    }
}
