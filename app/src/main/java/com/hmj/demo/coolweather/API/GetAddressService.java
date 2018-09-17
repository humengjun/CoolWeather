package com.hmj.demo.coolweather.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetAddressService {

    @GET("china/")
    Call<ResponseBody> getProvinceJson();

    @GET("china/{provinceId}")
    Call<ResponseBody> getCityJson(@Path("provinceId") int provinceId);

    @GET("china/{provinceId}/{cityId}")
    Call<ResponseBody> getCountryJson(@Path("provinceId") int provinceId,@Path("cityId") int cityId);
}
