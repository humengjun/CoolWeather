package com.hmj.demo.coolweather.injector.components;

import com.google.gson.Gson;
import com.hmj.demo.coolweather.injector.modules.ApplicationModule;
import com.hmj.demo.coolweather.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    //provide
    Gson getGson();
    RxBus getRxBus();
}
