package com.catalin.coronavirustracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int lastDayCases;
    private int prevDayCases;

    public int getPrevDayCases() {
        return prevDayCases;
    }

    public void setPrevDayCases(int prevDayCases) {
        this.prevDayCases = prevDayCases;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLastDayCases() {
        return lastDayCases;
    }

    public void setLastDayCases(int lastDayCases) {
        this.lastDayCases = lastDayCases;
    }
}
