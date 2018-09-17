package com.hmj.demo.coolweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hmj.demo.coolweather.gson.HeWeather;
import com.hmj.demo.coolweather.utils.RetrofitUtil;
import com.hmj.demo.coolweather.utils.ToastUtils;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    public static final String WEATHER_ID = "weatherId";
    public static final String API_KEY = "bc0418b57b2d4918819d3974ac1285d9";
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_degree)
    TextView tvDegree;
    @BindView(R.id.tv_weatherInfo)
    TextView tvWeatherInfo;
    @BindView(R.id.forecast_name)
    TextView forecastName;
    @BindView(R.id.forecast_layout)
    LinearLayout forecastLayout;
    @BindView(R.id.airquality_name)
    TextView airqualityName;
    @BindView(R.id.tv_aqi)
    TextView tvAqi;
    @BindView(R.id.tv_pm)
    TextView tvPm;
    @BindView(R.id.suggest_name)
    TextView suggestName;
    @BindView(R.id.tv_comfort)
    TextView tvComfort;
    @BindView(R.id.tv_car_wash)
    TextView tvCarWash;
    @BindView(R.id.tv_sport)
    TextView tvSport;
    private Gson mGson = new Gson();
    private String weatherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        weatherId = getIntent().getStringExtra(WEATHER_ID);
        loadPicBackground();
        queryWeather();
    }

    private void loadPicBackground() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String background = preferences.getString("background", null);
        if (!TextUtils.isEmpty(background)) {
            showBackground(background);
        } else {
            RetrofitUtil.getBackgroundUrl(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String background = response.body().string();
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(WeatherActivity.this).edit();
                        editor.putString("background", background);
                        showBackground(background);
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

    private void showBackground(final String background) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(WeatherActivity.this).load(background).into(ivBackground);
            }
        });
    }

    private void queryWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weather = sharedPreferences.getString("weather", null);
        if (!TextUtils.isEmpty(weather)) {
            HeWeather heWeather = mGson.fromJson(weather, HeWeather.class);
            showWeather(heWeather);
        } else if (!TextUtils.isEmpty(weatherId)) {
            RetrofitUtil.getWeatherJson(weatherId, API_KEY, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String weather = response.body().string();
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(WeatherActivity.this).edit();
                        editor.putString("weather", weather);
                        HeWeather heWeather = mGson.fromJson(weather, HeWeather.class);
                        showWeather(heWeather);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ToastUtils.shortToast(WeatherActivity.this, "请求网络失败！");
                }
            });
        }

    }

    private void showWeather(final HeWeather heWeather) {
        if (heWeather == null || heWeather.getHeWeather() == null || heWeather.getHeWeather().size() == 0)
            return;

        final HeWeather.HeWeatherBean weatherBean = heWeather.getHeWeather().get(0);
        if (!weatherBean.getStatus().equalsIgnoreCase("ok")) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvCity.setText(weatherBean.getBasic().getLocation());
                tvTime.setText(weatherBean.getUpdate().getLoc().substring(weatherBean.getUpdate().getLoc().lastIndexOf(" ")));
                tvDegree.setText(weatherBean.getNow().getFl());
                tvWeatherInfo.setText(weatherBean.getNow().getCond_txt());
                List<HeWeather.HeWeatherBean.DailyForecastBean> daily_forecast = weatherBean.getDaily_forecast();
                for (int i = 0; i < daily_forecast.size(); i++) {
                    HeWeather.HeWeatherBean.DailyForecastBean dailyForecastBean = daily_forecast.get(i);
                    View view = View.inflate(WeatherActivity.this, R.layout.weather_forecast_item, null);
                    TextView tv_date = view.findViewById(R.id.tv_date);
                    TextView tv_info = view.findViewById(R.id.tv_info);
                    TextView tv_maxDegree = view.findViewById(R.id.tv_maxDegree);
                    TextView tv_minDegree = view.findViewById(R.id.tv_min_Degree);
                    tv_date.setText(dailyForecastBean.getDate());
                    tv_info.setText(dailyForecastBean.getCond().getTxt_d());
                    tv_maxDegree.setText(dailyForecastBean.getTmp().getMax());
                    tv_minDegree.setText(dailyForecastBean.getTmp().getMin());
                    forecastLayout.addView(view);
                }
                tvAqi.setText(weatherBean.getAqi().getCity().getAqi());
                tvPm.setText(weatherBean.getAqi().getCity().getPm25());
                tvComfort.setText("舒适度：" + weatherBean.getSuggestion().getComf().getTxt());
                tvCarWash.setText("洗车指数：" + weatherBean.getSuggestion().getCw().getTxt());
                tvSport.setText("运动建议：" + weatherBean.getSuggestion().getSport().getTxt());
            }
        });
    }
}
