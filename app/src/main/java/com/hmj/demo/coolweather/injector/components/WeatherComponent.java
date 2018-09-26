package com.hmj.demo.coolweather.injector.components;

import com.hmj.demo.coolweather.WeatherActivity;
import com.hmj.demo.coolweather.injector.PerActivity;
import com.hmj.demo.coolweather.injector.modules.GsonModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = GsonModule.class)
public interface WeatherComponent {

    void inject(WeatherActivity weatherActivity);
}
