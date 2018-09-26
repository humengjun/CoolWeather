package com.hmj.demo.coolweather.injector.modules;

import com.google.gson.Gson;
import com.hmj.demo.coolweather.injector.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

    @Provides
    @PerActivity
    public Gson provideGson(){
        return new Gson();
    }
}
