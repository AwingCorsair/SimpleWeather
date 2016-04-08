package com.awingcorsair.simpleweather.Entity;

/**
 * Created by Mao on 2016/4/6.
 */
public class Tmp {
    /**
     *   "max": "34℃", //最高温度
     "min": "18℃" //最低温度
     */
    private String max;
    private String min;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
