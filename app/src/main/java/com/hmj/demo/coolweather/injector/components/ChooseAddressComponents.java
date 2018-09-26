package com.hmj.demo.coolweather.injector.components;

import com.hmj.demo.coolweather.fragment.ChooseAddressFragment;
import com.hmj.demo.coolweather.injector.PerFragment;
import com.hmj.demo.coolweather.injector.modules.AddressAdapterModule;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class,modules = AddressAdapterModule.class)
public interface ChooseAddressComponents {
    void inject(ChooseAddressFragment fragment);
}
