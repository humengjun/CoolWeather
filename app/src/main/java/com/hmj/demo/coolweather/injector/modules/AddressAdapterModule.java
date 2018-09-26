package com.hmj.demo.coolweather.injector.modules;

import com.hmj.demo.coolweather.adapter.ChooseAddressAdapter;
import com.hmj.demo.coolweather.fragment.ChooseAddressFragment;
import com.hmj.demo.coolweather.injector.PerFragment;

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
    public ChooseAddressAdapter provideAdapter(){
        return new ChooseAddressAdapter(fragment.getActivity());
    }
}
