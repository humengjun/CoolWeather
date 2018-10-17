package com.hmj.demo.coolweather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hmj.demo.coolweather.MyApplication;
import com.hmj.demo.coolweather.R;
import com.hmj.demo.coolweather.adapter.ChooseAddressAdapter;
import com.hmj.demo.coolweather.injector.components.ApplicationComponent;
import com.hmj.demo.coolweather.injector.components.DaggerChooseAddressComponents;
import com.hmj.demo.coolweather.injector.modules.AddressAdapterModule;
import com.hmj.demo.coolweather.rxbus.RxBus;
import com.hmj.demo.coolweather.rxbus.event.TitleEvent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ChooseAddressFragment extends BaseFragment {

    public final int PROVINCE_LEVEL = 0;
    public final int CITY_LEVEL = 1;
    public final int COUNTY_LEVEL = 2;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    @Inject
    public ChooseAddressAdapter adapter;
    @Inject
    public Gson gson;
    @Inject
    public RxBus rxBus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.choose_address_frg, null);
        Observable<TitleEvent> observable =
                rxBus.toObservableSticky(TitleEvent.class)
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Action1<TitleEvent>() {
            @Override
            public void call(TitleEvent event) {
                title.setText(event.getTitle());
                if (event.getLevel() == PROVINCE_LEVEL) {
                    back.setVisibility(View.INVISIBLE);
                } else {
                    back.setVisibility(View.VISIBLE);
                }
            }
        });
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    void inject() {
        DaggerChooseAddressComponents.builder()
                .applicationComponent(getApplication())
                .addressAdapterModule(new AddressAdapterModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        rxBus.removeAllStickyEvents();
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        adapter.back();
    }

    public ApplicationComponent getApplication() {
        return MyApplication.getApplicationComponent();
    }
}
