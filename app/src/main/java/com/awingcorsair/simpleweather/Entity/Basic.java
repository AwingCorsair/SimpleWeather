package com.awingcorsair.simpleweather.Entity;

/**
 * Created by Mao on 2016/4/6.
 */
public class Basic {
    /**
     *  "city": "北京",  //城市名称
     "cnty": "中国",  //国家
     "id": "CN101010100",  //城市ID，参见http://www.heweather.com/documents/cn-city-list
     "lat": "39.904000",  //城市维度
     "lon": "116.391000",  //城市经度
     */
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
