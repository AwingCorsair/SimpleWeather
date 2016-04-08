package com.awingcorsair.simpleweather.Entity;

/**
 * Created by Mao on 2016/4/6.
 */
public class DailyForecast {
    /**
     *   "date": "2015-07-02", //预报日期
     "astro": { //天文数值
     "sr": "04:50", //日出时间
     "ss": "19:47" //日落时间
     },
     "cond": { //天气状况
     "code_d": "100", //白天天气状况代码，
     "code_n": "100", //夜间天气状况代码
     "txt_d": "晴", //白天天气状况描述
     "txt_n": "晴" //夜间天气状况描述
     },
     "hum": "14", //相对湿度（%）
     "pcpn": "0.0", //降水量（mm）
     "pop": "0", //降水概率
     "pres": "1003", //气压
     "tmp": { //温度
     "max": "34℃", //最高温度
     "min": "18℃" //最低温度
     },
     */
    private String date;
    private Astro astro;
    private Cond cond;
    private String hum;
    private Tmp tmp;
    private Wind wind;

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public Tmp getTmp() {
        return tmp;
    }

    public void setTmp(Tmp tmp) {
        this.tmp = tmp;
    }
}
