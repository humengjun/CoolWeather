package com.hmj.demo.coolweather.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetWeatherService {

    @GET("weather?")
    Call<ResponseBody> getWeather(@Query("cityid") String cityId, @Query("key") String key);

    @GET("bing_pic")
    Call<ResponseBody> getWeatherBackground();
}
