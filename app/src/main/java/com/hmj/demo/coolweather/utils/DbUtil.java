package com.hmj.demo.coolweather.utils;

import android.text.TextUtils;

import com.hmj.demo.coolweather.db.City;
import com.hmj.demo.coolweather.db.County;
import com.hmj.demo.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DbUtil {

    /**
     * 保存所有省到数据库
     *
     * @param provinceJson
     * @return boolean
     */
    public static boolean saveProvinceDb(String provinceJson) {
        if (!TextUtils.isEmpty(provinceJson)) {
            try {
                JSONArray jsonArray = new JSONArray(provinceJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceCode(jsonObject.getInt("id"));
                    province.setProvinceName(jsonObject.getString("name"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 保存相应省对应的城市到数据库
     *
     * @param cityJson
     * @param provinceCode
     * @return boolean
     */
    public static boolean saveCityDb(String cityJson, int provinceCode) {
        if (!TextUtils.isEmpty(cityJson)) {
            try {
                JSONArray jsonArray = new JSONArray(cityJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(jsonObject.getInt("id"));
                    city.setCityName(jsonObject.getString("name"));
                    city.setProvinceCode(provinceCode);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 保存相应市对应的县区到数据库
     *
     * @param countyJson
     * @param cityCode
     * @return boolean
     */
    public static boolean saveCountyDb(String countyJson, int cityCode) {
        if (!TextUtils.isEmpty(countyJson)) {
            try {
                JSONArray jsonArray = new JSONArray(countyJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    County county = new County();
                    county.setWeatherId(jsonObject.getString("weather_id"));
                    county.setCountyName(jsonObject.getString("name"));
                    county.setCityCode(cityCode);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
