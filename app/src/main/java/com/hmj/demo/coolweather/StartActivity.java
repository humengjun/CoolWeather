package com.hmj.demo.coolweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hmj.demo.coolweather.utils.ActivityUtils;
import com.hmj.demo.coolweather.utils.TextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.iv_background)
    ImageView ivBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherId = sharedPreferences.getString("weatherId", null);
        String background = sharedPreferences.getString("background", null);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.timg).into(ivBackground);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(weatherId)) {
            ActivityUtils.startEmptyActivity(this, MainActivity.class);
            finish();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(WeatherActivity.WEATHER_ID, weatherId);
            ActivityUtils.startParamsActivity(this, WeatherActivity.class, bundle);
            finish();
        }
    }
}
