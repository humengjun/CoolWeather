package com.hmj.demo.coolweather.injector.modules;

import com.google.gson.Gson;
import com.hmj.demo.coolweather.MyApplication;
import com.hmj.demo.coolweather.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final MyApplication mApplication;
    private final Gson gson;
    private final RxBus rxBus;

    public ApplicationModule(MyApplication mApplication, Gson gson, RxBus rxBus) {
        this.mApplication = mApplication;
        this.gson = gson;
        this.rxBus = rxBus;
    }

    @Provides
    @Singleton
    public MyApplication provideApplication(){
        return mApplication.getApplication();
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        return gson;
    }

    @Provides
    @Singleton
    public RxBus provideRxBus(){
        return rxBus;
    }
}
