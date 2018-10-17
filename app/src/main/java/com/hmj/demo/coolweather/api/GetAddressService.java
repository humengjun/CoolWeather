package com.hmj.demo.coolweather.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GetAddressService {

    @GET("china/")
    Observable<ResponseBody> getProvinceJson();

    @GET("china/{provinceId}")
    Observable<ResponseBody> getCityJson(@Path("provinceId") int provinceId);

    @GET("china/{provinceId}/{cityId}")
    Observable<ResponseBody> getCountryJson(@Path("provinceId") int provinceId,@Path("cityId") int cityId);
}
