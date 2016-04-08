package com.awingcorsair.simpleweather.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.awingcorsair.simpleweather.R;
import com.awingcorsair.simpleweather.util.Utility;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

/**
 * Created by Mao on 2016/4/5.
 */
public class ShowWeatherActivity extends AppCompatActivity {

    private TextView wind;

    private TextView temp_high;

    private TextView temp_below;

    private TextView wet;

    private TextView sun_down;

    private TextView feel_temp;

    private TextView current_condition;

    private TextView temp_now;

    private TextView sun_rise;

    private Button change_city;

    private TextView city_name;


    private TextView day_one_time;
    private TextView day_one_temp_below;
    private TextView day_one_temp_high;
    private TextView day_one_con;

    private TextView day_two_time;
    private TextView day_two_temp_below;
    private TextView day_two_temp_high;
    private TextView day_two_con;

    private TextView day_three_time;
    private TextView day_three_temp_below;
    private TextView day_three_temp_high;
    private TextView day_three_con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        initView();
        String countyName=getIntent().getStringExtra("countyName");
        apiTest(countyName);
    //    Log.d("sdkdemo", "result: " + response);
    //    Utility.handleWeatherResponse(this, response);
        showWeather();


    }

    private void initView(){
        city_name = (TextView) findViewById(R.id.city_name);
        change_city = (Button) findViewById(R.id.change_city);
        sun_rise = (TextView) findViewById(R.id.sun_rise);
        sun_down = (TextView) findViewById(R.id.sun_down);
        temp_now = (TextView) findViewById(R.id.temp_now);
        temp_below = (TextView) findViewById(R.id.temp_below);
        temp_high = (TextView) findViewById(R.id.temp_high);
        feel_temp = (TextView) findViewById(R.id.feel_temp);
        current_condition = (TextView) findViewById(R.id.current_condition);
        wet = (TextView) findViewById(R.id.wet);
        wind=(TextView)findViewById(R.id.wind);
        day_one_time = (TextView) findViewById(R.id.day_one_time);
        day_one_con = (TextView) findViewById(R.id.day_one_con);
        day_one_temp_below = (TextView) findViewById(R.id.day_one_temp_below);
        day_one_temp_high = (TextView) findViewById(R.id.day_one_temp_high);
        day_two_time = (TextView) findViewById(R.id.day_two_time);
        day_two_con = (TextView) findViewById(R.id.day_two_con);
        day_two_temp_below = (TextView) findViewById(R.id.day_two_temp_below);
        day_two_temp_high = (TextView) findViewById(R.id.day_two_temp_high);
        day_three_time = (TextView) findViewById(R.id.day_three_time);
        day_three_con = (TextView) findViewById(R.id.day_three_con);
        day_three_temp_below = (TextView) findViewById(R.id.day_three_temp_below);
        day_three_temp_high = (TextView) findViewById(R.id.day_three_temp_high);
    }

    private void showWeather(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        city_name.setText(sharedPreferences.getString("city_name","Null"));
        temp_now.setText(sharedPreferences.getString("temp_now","Null") + "℃");
        feel_temp.setText(sharedPreferences.getString("feel_temp","Null") + "℃");
        current_condition.setText(sharedPreferences.getString("current_condition","Null"));

        sun_down.setText(sharedPreferences.getString("sun_down","Null"));
        sun_rise.setText(sharedPreferences.getString("sun_rise","Null"));
        temp_below.setText(sharedPreferences.getString("temp_below","Null"));
        temp_high.setText(sharedPreferences.getString("temp_high","Null") + "℃");
        wet.setText("湿度："+sharedPreferences.getString("wet","Null")+"%");
        wind.setText(sharedPreferences.getString("wind_dir","Null")+"/"+sharedPreferences.getString("wind_sc","Null"));

        day_one_time.setText(sharedPreferences.getString("day_one_time", "NULL"));
        day_one_con.setText(sharedPreferences.getString("day_one_con","NULL"));
        day_one_temp_below.setText(sharedPreferences.getString("day_one_temp_below","NULL"));
        day_one_temp_high.setText(sharedPreferences.getString("day_one_temp_high","NULL") + "℃");

        day_two_time.setText(sharedPreferences.getString("day_two_time","NULL"));
        day_two_con.setText(sharedPreferences.getString("day_two_con","NULL"));
        day_two_temp_below.setText(sharedPreferences.getString("day_two_temp_below","NULL"));
        day_two_temp_high.setText(sharedPreferences.getString("day_two_temp_high","NULL") + "℃");

        day_three_time.setText(sharedPreferences.getString("day_three_time","NULL"));
        day_three_con.setText(sharedPreferences.getString("day_three_con","NULL"));
        day_three_temp_below.setText(sharedPreferences.getString("day_three_temp_below","NULL"));
        day_three_temp_high.setText(sharedPreferences.getString("day_three_temp_high","NULL") + "℃");
    }


    private void apiTest(String countyName) {

        ApiStoreSDK.init(getApplicationContext(), "375c83c832fb063405f1c81b1e12d9dc");
        Parameters para = new Parameters();
//        String cityName="浦口";
        para.put("city", countyName);
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free",
                ApiStoreSDK.GET,
                para,
                new ApiCallBack() {

                    @Override
                    public void onSuccess(int status, String responseString) {

                        Log.d("sdkdemo", responseString);
                        Utility.handleWeatherResponse(ShowWeatherActivity.this, responseString);
                        showWeather();
                        Log.i("sdkdemo", "onSuccess" + responseString);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("sdkdemo", "onComplete");
                    }

                    @Override
                    public void onError(int status, String responseString, Exception e) {
                        Log.i("sdkdemo", "onError, status: " + status);
                        Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
                    }

                });

    }

}
