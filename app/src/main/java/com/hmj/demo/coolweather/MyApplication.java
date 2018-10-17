package com.hmj.demo.coolweather;

import com.google.gson.Gson;
import com.hmj.demo.coolweather.injector.components.ApplicationComponent;
import com.hmj.demo.coolweather.injector.components.DaggerApplicationComponent;
import com.hmj.demo.coolweather.injector.modules.ApplicationModule;
import com.hmj.demo.coolweather.rxbus.RxBus;

import org.litepal.LitePalApplication;

public class MyApplication extends LitePalApplication {
    private static ApplicationComponent applicationComonent;

    private RxBus mRxBus = new RxBus();

    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    private void inject() {
        applicationComonent  = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this,new Gson(),mRxBus))
                .build();
    }

    public MyApplication getApplication() {
        return this;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComonent;
    }
}
