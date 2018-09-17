package com.hmj.demo.coolweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.hmj.demo.coolweather.gson.HeWeather;
import com.hmj.demo.coolweather.utils.RetrofitUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitUtil.getWeatherJson("CN101210907",
                "bc0418b57b2d4918819d3974ac1285d9", new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            HeWeather heWeather = new Gson().fromJson(response.body().string(), HeWeather.class);
                            Log.i("weatherBody:", heWeather.getHeWeather().get(0).getNow().getCond_txt()+"");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
