package com.hmj.demo.coolweather.injector.modules;

import com.google.gson.Gson;
import com.hmj.demo.coolweather.injector.PerActivity;
import com.hmj.demo.coolweather.utils.TestGson;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

    @Provides
    @PerActivity
    public TestGson provideGson(Gson gson){
        return new TestGson(gson);
    }
}
