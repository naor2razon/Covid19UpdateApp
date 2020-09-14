package com.Covid.Config;

public class ConfigModel {
    private int refreshIntervalInSecounds;
    private String countryName;
    private String countryCode;

    public ConfigModel() {
        refreshIntervalInSecounds = 300;
        countryName = "Israel";
        countryCode = "IL";
    }

    public int getRefreshIntervalInSecounds() {
        return refreshIntervalInSecounds;
    }

    public void setRefreshIntervalInSecounds(int refreshIntervalInSecounds) {
        this.refreshIntervalInSecounds = refreshIntervalInSecounds;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
