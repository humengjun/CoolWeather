package com.hmj.demo.coolweather;

import android.app.Application;

import com.hmj.demo.coolweather.injector.components.ApplicationComponent;
import com.hmj.demo.coolweather.injector.components.DaggerApplicationComponent;
import com.hmj.demo.coolweather.injector.modules.ApplicationModule;

public class MyApplication extends Application {
    private static ApplicationComponent applicationComonent;

    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    private void inject() {
        applicationComonent  = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public MyApplication getApplication() {
        return this;
    }

    public static ApplicationComponent getApplicationComonent() {
        return applicationComonent;
    }
}
