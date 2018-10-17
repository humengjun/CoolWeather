package com.hmj.demo.coolweather.utils;

import com.hmj.demo.coolweather.api.GetAddressService;
import com.hmj.demo.coolweather.api.GetWeatherService;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RetrofitUtil {
    public static final String BASE_URL = "http://guolin.tech/api/";


    private static GetAddressService service;


    private static void initService() {
        if (service == null) {
            synchronized (RetrofitUtil.class) {
                if (service == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl(BASE_URL)
                            .build();
                    service = retrofit.create(GetAddressService.class);
                }
            }
        }
    }

    public static Observable<ResponseBody> getProvinceJson() {
        initService();
        Observable<ResponseBody> observable = service.getProvinceJson()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    public static void getCityJson(int provinceId, Action1<ResponseBody> observer) {
        initService();
        Observable<ResponseBody> observable = service.getCityJson(provinceId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);
    }

    public static void getCountyJson(int provinceId, int cityId, Action1<ResponseBody> observer) {
        initService();
        Observable<ResponseBody> observable = service.getCountryJson(provinceId, cityId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);
    }

    public static Observable<ResponseBody> getWeatherJson(String weatherId, String key) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GetWeatherService service = retrofit.create(GetWeatherService.class);
        Observable<ResponseBody> observable = service.getWeather(weatherId, key)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    public static Observable<ResponseBody> getBackgroundUrl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        GetWeatherService service = retrofit.create(GetWeatherService.class);
        Observable<ResponseBody> observable = service.getWeatherBackground()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
