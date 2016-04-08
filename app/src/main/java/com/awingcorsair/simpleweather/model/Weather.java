package com.awingcorsair.simpleweather.model;

import com.awingcorsair.simpleweather.Entity.Aqi;
import com.awingcorsair.simpleweather.Entity.Basic;
import com.awingcorsair.simpleweather.Entity.DailyForecast;
import com.awingcorsair.simpleweather.Entity.Now;

import java.util.List;

/**
 * Created by Mao on 2016/4/6.
 */
public class Weather {
    private Aqi aqi;
    private Basic basic;
    private List<DailyForecast> daily_forecast;
    private Now now;
    private String status;

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public List<DailyForecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
