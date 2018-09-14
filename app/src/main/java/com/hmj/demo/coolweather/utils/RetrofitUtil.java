package com.hmj.demo.coolweather.utils;

import com.hmj.demo.coolweather.API.GetAddressService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class RetrofitUtil {
    public static final String PROVINCE_URL = "http://guolin.tech/api/china/";
    public static final String WEATHER_URL = "http://guolin.tech/api/weather";

    private static GetAddressService service;


    private static void initService() {
        if (service == null) {
            synchronized (RetrofitUtil.class) {
                if (service == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(PROVINCE_URL)
                            .build();
                    service = retrofit.create(GetAddressService.class);
                }
            }
        }
    }

    public static void getProvinceJson(Callback<ResponseBody> callback) {
        initService();
        Call<ResponseBody> call = service.getProvinceJson();
        call.enqueue(callback);
    }

    public static void getCityJson(int provinceId, Callback<ResponseBody> callback) {
        initService();
        Call<ResponseBody> call = service.getCityJson(provinceId);
        call.enqueue(callback);
    }

    public static void getCountyJson(int provinceId, int cityId, Callback<ResponseBody> callback) {
        initService();
        Call<ResponseBody> call = service.getCountryJson(provinceId, cityId);
        call.enqueue(callback);
    }
}
