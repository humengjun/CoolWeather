package com.hmj.demo.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.hmj.demo.coolweather.utils.RetrofitUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.functions.Action1;

public class AutoRefreshService extends Service {

    private static final String API_KEY = "bc0418b57b2d4918819d3974ac1285d9";
    private final int AN_HOUR = 8 * 60 * 60 * 1000;
    private SharedPreferences sharedPreferences;
    private boolean isStart;

    public AutoRefreshService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isStart) {
            sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            updateWeather();
            updateBackground();
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long triggerAtTime = SystemClock.elapsedRealtime() + AN_HOUR;
            Intent i = new Intent(this, AutoRefreshService.class);
            PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
            alarmManager.cancel(pi);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        }
        isStart = true;
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        final String weatherId = sharedPreferences.getString("weatherId", null);
        if (!TextUtils.isEmpty(weatherId)) {
            RetrofitUtil.getWeatherJson(weatherId, API_KEY).subscribe(new Action1<ResponseBody>() {
                @Override
                public void call(ResponseBody responseBody) {
                    try {
                        String weather = responseBody.string();
                        sharedPreferences.edit().putString(weatherId, weather);
                        sharedPreferences.edit().apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void updateBackground() {
        RetrofitUtil.getBackgroundUrl().subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody responseBody) {
                try {
                    String background = responseBody.string();
                    sharedPreferences.edit().putString("background", background);
                    sharedPreferences.edit().apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
