package com.awingcorsair.simpleweather.util;


import android.app.Application;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

public class LocationApplication extends Application {
    public LocationClient mLocationClient;//定位SDK的核心类
    public MyLocationListener mMyLocationListener;//定义监听类

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
    //        sb.append("\nerror code : ");
    //        sb.append(location.getLocType());//获得erro code得知定位现状
            if (location.getLocType() == BDLocation.TypeGpsLocation){//通过GPS定位
    //            sb.append("\n地址为 : ");
                sb.append(location.getAddrStr());//获得当前地址
    //            sb.append(location.getDirection());//获得方位
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){//通过网络连接定位
    //            sb.append("\n地址为 : ");
                sb.append(location.getAddrStr());//获得当前地址
            }
            Toast.makeText(getApplicationContext(),"Address:"+location.getAddrStr(),Toast.LENGTH_SHORT).show();
            mLocationClient.stop();
        }
    }
}