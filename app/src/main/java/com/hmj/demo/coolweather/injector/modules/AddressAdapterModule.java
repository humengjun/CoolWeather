package com.hmj.demo.coolweather.injector.modules;

import com.google.gson.Gson;
import com.hmj.demo.coolweather.adapter.ChooseAddressAdapter;
import com.hmj.demo.coolweather.fragment.ChooseAddressFragment;
import com.hmj.demo.coolweather.injector.PerFragment;
import com.hmj.demo.coolweather.rxbus.RxBus;
import com.hmj.demo.coolweather.utils.TestGson;

import dagger.Module;
import dagger.Provides;

@Module
public class AddressAdapterModule {
    private ChooseAddressFragment fragment;

    public AddressAdapterModule(ChooseAddressFragment fragment) {
        this.fragment = fragment;
    }

    @PerFragment
    @Provides
    public ChooseAddressAdapter provideAdapter(RxBus rxBus){
        return new ChooseAddressAdapter(fragment.getActivity(),rxBus);
    }
    @PerFragment
    @Provides
    public TestGson provideGson(Gson gson){
        return new TestGson(gson);
    }
}
