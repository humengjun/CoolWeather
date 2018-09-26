package com.hmj.demo.coolweather.injector.modules;

import com.hmj.demo.coolweather.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private MyApplication mApplication;

    public ApplicationModule(MyApplication mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public MyApplication provideApplication(){
        return mApplication.getApplication();
    }
}
