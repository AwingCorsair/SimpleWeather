package com.awingcorsair.simpleweather.Entity;

/**
 * Created by Mao on 2016/4/6.
 */
public class City {
    /**
     *
     "aqi": "30", //空气质量指数
     "co": "0", //一氧化碳1小时平均值(ug/m³)
     "no2": "10", //二氧化氮1小时平均值(ug/m³)
     "o3": "94", //臭氧1小时平均值(ug/m³)
     "pm10": "10", //PM10 1小时平均值(ug/m³)
     "pm25": "7", //PM2.5 1小时平均值(ug/m³)
     "qlty": "优", //空气质量类别
     "so2": "3" //二氧化硫1小时平均值(ug/m³)
     */
    private String pm25;

    private String aqi;

 public String getAqi() {
  return aqi;
 }

 public void setAqi(String aqi) {
  this.aqi = aqi;
 }

 public String getPm25() {
  return pm25;
 }

 public void setPm25(String pm25) {
  this.pm25 = pm25;
 }
}
