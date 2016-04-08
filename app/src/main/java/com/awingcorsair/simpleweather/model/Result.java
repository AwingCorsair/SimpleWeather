package com.awingcorsair.simpleweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mao on 2016/4/6.
 */
public class Result {
    @SerializedName("HeWeather data service 3.0")
    private List<Weather> weatherInfoList;

    public List<Weather> getWeatherInfoList() {
        return weatherInfoList;
    }


}
