package com.awingcorsair.simpleweather.Entity;

/**
 * Created by Mao on 2016/4/6.
 */
public class Now {
    /**
     *  "cond": {} //天气状况
     * "fl": "30", //体感温度
     * "hum": "20%", //相对湿度（%）
     * "pcpn": "0.0", //降水量（mm）
     * "pres": "1001", //气压
     * "tmp": "32", //温度
     * "vis": "10", //能见度（km）
     */
    private Cond cond;
    private String fl;
    private String hum;
    private String tmp;


    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
}
