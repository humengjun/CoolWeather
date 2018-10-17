package com.hmj.demo.coolweather.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GetWeatherService {

    @GET("weather?")
    Observable<ResponseBody> getWeather(@Query("cityid") String cityId, @Query("key") String key);

    @GET("bing_pic")
    Observable<ResponseBody> getWeatherBackground();
}
