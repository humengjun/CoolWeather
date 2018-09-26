package com.hmj.demo.coolweather.injector.components;

import com.hmj.demo.coolweather.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
}
