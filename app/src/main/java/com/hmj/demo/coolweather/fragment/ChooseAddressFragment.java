package com.hmj.demo.coolweather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmj.demo.coolweather.R;
import com.hmj.demo.coolweather.adapter.ChooseAddressAdapter;
import com.hmj.demo.coolweather.event.TitleEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChooseAddressFragment extends Fragment {

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


    private ChooseAddressAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_address_frg, null);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ChooseAddressAdapter(getActivity());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        adapter.back();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTitle(TitleEvent event) {
        title.setText(event.getTitle());
        if (event.getLevel() == PROVINCE_LEVEL) {
            back.setVisibility(View.INVISIBLE);
        } else {
            back.setVisibility(View.VISIBLE);
        }
    }

}
