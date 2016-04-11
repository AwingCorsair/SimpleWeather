package com.awingcorsair.simpleweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.awingcorsair.simpleweather.R;
import com.awingcorsair.simpleweather.SideSlip.ResideMenu;
import com.awingcorsair.simpleweather.SideSlip.ResideMenuItem;
import com.awingcorsair.simpleweather.util.Utility;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

/**
 * Created by Mao on 2016/4/5.
 */
public class ShowWeatherActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView wind;

    private TextView temp_high;

    private TextView temp_below;

    private TextView wet;

    private TextView sun_down;

    private TextView feel_temp;

    private TextView current_condition;

    private TextView temp_now;

    private TextView sun_rise;

    private Button refresh;

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
    private static String countyName;

    private int mBackKeyPressedTimes = 0;

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    //private ResideMenuItem itemProfile;
    //    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        initView();
        setUpMenu();
        countyName = getIntent().getStringExtra("countyName");
        if(!TextUtils.isEmpty(countyName)){
            apiTest(countyName);
            showWeather();
        }else {
            showWeather();
        }

    }

    private void initView() {
        city_name = (TextView) findViewById(R.id.city_name);
        refresh = (Button) findViewById(R.id.refresh);
        sun_rise = (TextView) findViewById(R.id.sun_rise);
        sun_down = (TextView) findViewById(R.id.sun_down);
        temp_now = (TextView) findViewById(R.id.temp_now);
        temp_below = (TextView) findViewById(R.id.temp_below);
        temp_high = (TextView) findViewById(R.id.temp_high);
        feel_temp = (TextView) findViewById(R.id.feel_temp);
        current_condition = (TextView) findViewById(R.id.current_condition);
        wet = (TextView) findViewById(R.id.wet);
        wind = (TextView) findViewById(R.id.wind);
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
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiTest(countyName);
                showWeather();
                Toast.makeText(ShowWeatherActivity.this, "更新完成", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        city_name.setText(sharedPreferences.getString("city_name", "Null"));
        temp_now.setText(sharedPreferences.getString("temp_now", "Null") + "℃");
        feel_temp.setText(sharedPreferences.getString("feel_temp", "Null") + "℃");
        current_condition.setText(sharedPreferences.getString("current_condition", "Null"));

        sun_down.setText(sharedPreferences.getString("sun_down", "Null"));
        sun_rise.setText(sharedPreferences.getString("sun_rise", "Null"));
        temp_below.setText(sharedPreferences.getString("temp_below", "Null"));
        temp_high.setText(sharedPreferences.getString("temp_high", "Null") + "℃");
        wet.setText("湿度：" + sharedPreferences.getString("wet", "Null") + "%");
        wind.setText(sharedPreferences.getString("wind_dir", "Null") + "/" + sharedPreferences.getString("wind_sc", "Null"));

        day_one_time.setText(sharedPreferences.getString("day_one_time", "NULL"));
        day_one_con.setText(sharedPreferences.getString("day_one_con", "NULL"));
        day_one_temp_below.setText(sharedPreferences.getString("day_one_temp_below", "NULL"));
        day_one_temp_high.setText(sharedPreferences.getString("day_one_temp_high", "NULL") + "℃");

        day_two_time.setText(sharedPreferences.getString("day_two_time", "NULL"));
        day_two_con.setText(sharedPreferences.getString("day_two_con", "NULL"));
        day_two_temp_below.setText(sharedPreferences.getString("day_two_temp_below", "NULL"));
        day_two_temp_high.setText(sharedPreferences.getString("day_two_temp_high", "NULL") + "℃");

        day_three_time.setText(sharedPreferences.getString("day_three_time", "NULL"));
        day_three_con.setText(sharedPreferences.getString("day_three_con", "NULL"));
        day_three_temp_below.setText(sharedPreferences.getString("day_three_temp_below", "NULL"));
        day_three_temp_high.setText(sharedPreferences.getString("day_three_temp_high", "NULL") + "℃");
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        //    resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.9f);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.icon_home, "切换城市");
        //    itemProfile = new ResideMenuItem(this, R.drawable.icon_profile, "Profile");
        //    itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "Calendar");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "设置");

        itemHome.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        //itemProfile.setOnClickListener(this);
        //    itemCalendar.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);
        //resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
//        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//            }
//        });
    }

    private void apiTest(String countyName) {

        ApiStoreSDK.init(getApplicationContext(), "375c83c832fb063405f1c81b1e12d9dc");
        Parameters para = new Parameters();
        para.put("city", countyName);
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free",
                ApiStoreSDK.GET,
                para,
                new ApiCallBack() {

                    @Override
                    public void onSuccess(int status, String responseString) {

                        //    Log.i("sdkdemo", "onSuccess" + responseString);
                        Utility.handleWeatherResponse(ShowWeatherActivity.this, responseString);
                        showWeather();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("sdkdemo", "onComplete");
                    }

                    @Override
                    public void onError(int status, String responseString, Exception e) {
                        Log.i("sdkdemo", "onError, status: " + status);
                        Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
                        Toast.makeText(ShowWeatherActivity.this, "哎呀，网络出错了", Toast.LENGTH_SHORT).show();
                    }

                });

    }


    @Override
    public void onClick(View v) {
        if(v==itemHome){
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putBoolean("city_selected",false);
            editor.commit();
            Intent intent=new Intent(this,ChooseAreaActivity.class);
            startActivity(intent);
            finish();
        }else if(v==itemSettings){
    //        changeFragment(new SettingFragment());
        }
    }
//
//    private void changeFragment(Fragment targetFragment){
//        resideMenu.clearIgnoredViewList();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_fragment, targetFragment, "fragment")
//                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit();
//    }

    /**
     * open Gesture slide
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onBackPressed() {
        if (mBackKeyPressedTimes == 0) {
            Toast.makeText(this, "再按一次退出程序 ", Toast.LENGTH_SHORT).show();
            mBackKeyPressedTimes = 1;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mBackKeyPressedTimes = 0;
                    }
                }
            }.start();
            return;
        } else {
            this.finish();
        }
        super.onBackPressed();
    }
}
