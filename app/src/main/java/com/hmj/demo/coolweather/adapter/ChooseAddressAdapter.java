package com.hmj.demo.coolweather.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmj.demo.coolweather.MainActivity;
import com.hmj.demo.coolweather.R;
import com.hmj.demo.coolweather.WeatherActivity;
import com.hmj.demo.coolweather.db.City;
import com.hmj.demo.coolweather.db.County;
import com.hmj.demo.coolweather.db.Province;
import com.hmj.demo.coolweather.rxbus.RxBus;
import com.hmj.demo.coolweather.rxbus.event.TitleEvent;
import com.hmj.demo.coolweather.utils.ActivityUtils;
import com.hmj.demo.coolweather.utils.DbUtil;
import com.hmj.demo.coolweather.utils.RetrofitUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.functions.Action1;

public class ChooseAddressAdapter extends RecyclerView.Adapter<ChooseAddressAdapter.MyViewHolder> {

    public final int PROVINCE_LEVEL = 0;
    public final int CITY_LEVEL = 1;
    public final int COUNTY_LEVEL = 2;

    private Context context;

    private List<String> addressList = new ArrayList<>();

    private int addressLevel;

    private List<City> cityList;
    private List<Province> provinceList;
    private List<County> countyList;

    private int currentProvinceCode;
    private int currentCityCode;
    private String currentCityName;
    private String currentProvinceName;
    private int adapterPosition;

    private RxBus rxBus;


    public ChooseAddressAdapter(Context context, RxBus rxBus) {
        this.context = context;
        this.rxBus = rxBus;
        queryProvince();
    }

    public void setCityList(int addressLevel, List<City> cityList) {
        this.addressLevel = addressLevel;
        addressList.clear();
        this.cityList = cityList;
        for (int i = 0; i < cityList.size(); i++) {
            addressList.add(cityList.get(i).getCityName());
        }
        notifyDataSetChanged();
    }

    public void setProvinceList(int addressLevel, List<Province> provinceList) {
        this.addressLevel = addressLevel;
        addressList.clear();
        this.provinceList = provinceList;
        for (int i = 0; i < provinceList.size(); i++) {
            addressList.add(provinceList.get(i).getProvinceName());
        }
        notifyDataSetChanged();
    }

    public void setCountyList(int addressLevel, List<County> countyList) {
        this.addressLevel = addressLevel;
        addressList.clear();
        this.countyList = countyList;
        for (int i = 0; i < countyList.size(); i++) {
            addressList.add(countyList.get(i).getCountyName());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_recycler_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterPosition = viewHolder.getAdapterPosition();
                if (addressLevel == PROVINCE_LEVEL) {
                    queryCity(adapterPosition);
                } else if (addressLevel == CITY_LEVEL) {
                    queryCounty(adapterPosition);
                } else if (addressLevel == COUNTY_LEVEL) {
                    if (context instanceof MainActivity) {
                        Bundle bundle = new Bundle();
                        bundle.putString(WeatherActivity.WEATHER_ID, countyList.get(adapterPosition).getWeatherId());
                        ActivityUtils.startParamsActivity(context, WeatherActivity.class, bundle);
                        ((Activity) context).finish();
                    } else if (context instanceof WeatherActivity) {
                        WeatherActivity weatherActivity = (WeatherActivity) context;
                        weatherActivity.drawerLayout.closeDrawers();
                        weatherActivity.weatherId = countyList.get(adapterPosition).getWeatherId();
                        weatherActivity.swipeRefresh.setRefreshing(true);
                        weatherActivity.loadPicBackground();
                        weatherActivity.queryWeather();
                    }
                }
            }
        });

        return viewHolder;
    }

    private void queryProvince() {
        rxBus.postSticky(new TitleEvent(PROVINCE_LEVEL, "中国"));
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            setProvinceList(PROVINCE_LEVEL, provinceList);
        } else {
            queryServer(PROVINCE_LEVEL);
        }
    }

    private void queryCounty(int adapterPosition) {
        currentCityCode = cityList.get(adapterPosition).getCityCode();
        currentCityName = cityList.get(adapterPosition).getCityName();
        rxBus.postSticky(new TitleEvent(COUNTY_LEVEL,
                currentCityName));
        countyList = DataSupport.where("cityCode = ?",
                String.valueOf(currentCityCode)).find(County.class);
        if (countyList.size() > 0) {
            setCountyList(COUNTY_LEVEL, countyList);
        } else {
            queryServer(COUNTY_LEVEL);
        }

    }

    private void queryCity(int adapterPosition) {
        currentProvinceCode = provinceList.get(adapterPosition).getProvinceCode();
        currentProvinceName = provinceList.get(adapterPosition).getProvinceName();
        rxBus.postSticky(new TitleEvent(CITY_LEVEL,
                currentProvinceName));
        cityList = DataSupport.where("provinceCode = ?",
                String.valueOf(currentProvinceCode)).find(City.class);
        if (cityList.size() > 0) {
            setCityList(CITY_LEVEL, cityList);
        } else {
            queryServer(CITY_LEVEL);
        }

    }

    private void queryServer(int level) {
        switch (level) {
            case PROVINCE_LEVEL:
                RetrofitUtil.getProvinceJson()
                        .subscribe(new Action1<ResponseBody>() {
                            @Override
                            public void call(ResponseBody responseBody) {
                                try {
                                    boolean result = DbUtil.saveProvinceDb(responseBody.string());
                                    if (result) {
                                        queryProvince();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                break;
            case CITY_LEVEL:
                RetrofitUtil.getCityJson(currentProvinceCode, new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            boolean result = DbUtil.saveCityDb(responseBody.string(), currentProvinceCode);
                            if (result) {
                                queryCity(adapterPosition);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case COUNTY_LEVEL:
                RetrofitUtil.getCountyJson(currentProvinceCode, currentCityCode,
                        new Action1<ResponseBody>() {
                            @Override
                            public void call(ResponseBody responseBody) {
                                try {
                                    boolean result = DbUtil.saveCountyDb(responseBody.string(), currentCityCode);
                                    if (result) {
                                        queryCounty(adapterPosition);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                break;
        }

    }

    public void back() {
        switch (addressLevel) {
            case PROVINCE_LEVEL:

                break;
            case CITY_LEVEL:
                cityList.clear();
                rxBus.postSticky(new TitleEvent(PROVINCE_LEVEL, "中国"));
                setProvinceList(PROVINCE_LEVEL, provinceList);
                break;
            case COUNTY_LEVEL:
                countyList.clear();
                rxBus.postSticky(new TitleEvent(CITY_LEVEL,
                        currentProvinceName));
                setCityList(CITY_LEVEL, cityList);
                break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_address.setText(addressList.get(position));
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_address;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_address = itemView.findViewById(R.id.address);
        }
    }
}
