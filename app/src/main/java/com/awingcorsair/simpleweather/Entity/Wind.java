package com.awingcorsair.simpleweather.Entity;

/**
 * Created by Mao on 2016/4/7.
 */
public class Wind {
    /**
     * "deg": "10", //风向（360度）
     * "dir": "北风", //风向
     * "sc": "3级", //风力
     * "spd": "15" //风速（kmph）
     */
    private String dir;
    private String sc;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }
}
