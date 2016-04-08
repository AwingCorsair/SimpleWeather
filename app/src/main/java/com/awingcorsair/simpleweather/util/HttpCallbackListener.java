package com.awingcorsair.simpleweather.util;

/**
 * Created by Mao on 2016/4/3.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
