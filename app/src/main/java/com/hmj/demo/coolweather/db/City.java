package com.hmj.demo.coolweather.db;

import org.litepal.crud.DataSupport;

public class City extends DataSupport {

    private String cityName;
    private int cityCodeCode;
    private int id;
    private int province;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCodeCode() {
        return cityCodeCode;
    }

    public void setCityCodeCode(int cityCodeCode) {
        this.cityCodeCode = cityCodeCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }
}
