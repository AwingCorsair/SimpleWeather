package com.awingcorsair.simpleweather.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awingcorsair.simpleweather.R;
import com.awingcorsair.simpleweather.SideSlip.ResideMenu;
import com.awingcorsair.simpleweather.SideSlip.ResideMenuItem;
import com.awingcorsair.simpleweather.util.ChangeBackground;
import com.awingcorsair.simpleweather.util.Utility;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mao on 2016/4/5.
 */
public class ShowWeatherActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView pm25;

    private TextView temp_high;

    private TextView temp_below;

    private TextView wet;

    private TextView sun_down;

    private TextView feel_temp;

    private TextView current_condition;

    private TextView temp_now;

    private TextView sun_rise;

    private Button refresh;

    private Button left_menu;

    private TextView city_name;


    private TextView day_one_time;
    private TextView day_one_temp_below;
    private TextView day_one_con;

    private TextView day_two_time;
    private TextView day_two_temp_below;
    private TextView day_two_con;

    private TextView day_three_time;
    private TextView day_three_temp_below;
    private TextView day_three_con;
    private int condition_code;
    private static String countyName;

    private int mBackKeyPressedTimes = 0;


    private LinearLayout layout;

    DrawerLayout mDrawerLayout = null;

    NavigationView navigationView;

    private FeedbackDialog feedBack;
    FloatingActionButton floatingActionButton;
    CoordinatorLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        initView();
        countyName = getIntent().getStringExtra("countyName");
        if (!TextUtils.isEmpty(countyName)) {
            apiTest(countyName);
        } else {
            showWeather();
        }

    }


    private void initView() {
        showProgressDialog();
        left_menu = (Button) findViewById(R.id.left_menu);
        left_menu.setOnClickListener(this);
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
        pm25 = (TextView) findViewById(R.id.pm25);
        day_one_time = (TextView) findViewById(R.id.day_one_time);
        day_one_con = (TextView) findViewById(R.id.day_one_con);
        day_one_temp_below = (TextView) findViewById(R.id.day_one_temp_below);
        //    day_one_temp_high = (TextView) findViewById(R.id.day_one_temp_high);
        day_two_time = (TextView) findViewById(R.id.day_two_time);
        day_two_con = (TextView) findViewById(R.id.day_two_con);
        day_two_temp_below = (TextView) findViewById(R.id.day_two_temp_below);
        //    day_two_temp_high = (TextView) findViewById(R.id.day_two_temp_high);
        day_three_time = (TextView) findViewById(R.id.day_three_time);
        day_three_con = (TextView) findViewById(R.id.day_three_con);
        day_three_temp_below = (TextView) findViewById(R.id.day_three_temp_below);
        //    day_three_temp_high = (TextView) findViewById(R.id.day_three_temp_high);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(countyName)) {
                    apiTest(countyName);
                    Toast.makeText(ShowWeatherActivity.this, "更新完成", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowWeatherActivity.this, "更新失败,请稍后重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
        layout = (LinearLayout) findViewById(R.id.main_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = new Fragment();

                switch (id) {
                    case R.id.change_city:
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ShowWeatherActivity.this).edit();
                        editor.putBoolean("city_selected", false);
                        editor.commit();
                        Intent intent = new Intent(ShowWeatherActivity.this, ChooseAreaActivity.class);
                        startActivity(intent);
                        finish();
                        break;
//                    case R.id.setting:
//                        Snackbar.make(navigationView, "Click one ", Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(ShowWeatherActivity.this, "ha ha", Toast.LENGTH_LONG).show();
//                            }
//                        }).show();
//                        break;
                    case R.id.about:
                        showDialog();
                        break;
                    case R.id.contact:
                        feedSetting();
                        break;
                }
                return false;
            }
        });
        frameLayout = (CoordinatorLayout) findViewById(R.id.fablayout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        //tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        });
    }


    /**
     * bind data to widget
     */
    private void showWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        city_name.setText(sharedPreferences.getString("city_name", "Null"));
        temp_now.setText(sharedPreferences.getString("temp_now", "Null") + "℃");
        feel_temp.setText(sharedPreferences.getString("feel_temp", "Null") + "℃");
        current_condition.setText(sharedPreferences.getString("current_condition", "Null"));

        sun_down.setText(sharedPreferences.getString("sun_down", "Null") + "↓");
        sun_rise.setText(sharedPreferences.getString("sun_rise", "Null") + "↑");
        temp_below.setText(sharedPreferences.getString("temp_below", "Null"));
        temp_high.setText(sharedPreferences.getString("temp_high", "Null") + "℃");
        wet.setText("湿度：" + sharedPreferences.getString("wet", "Null") + "%");
        pm25.setText("PM2.5：" + sharedPreferences.getString("pm25", "Null"));

        day_one_time.setText(getWeek(sharedPreferences.getString("day_one_time", "NULL")));
        day_one_con.setText(sharedPreferences.getString("day_one_con", "NULL"));
        day_one_temp_below.setText(sharedPreferences.getString("day_one_temp_below", "NULL") + "°" + "/" + sharedPreferences.getString("day_one_temp_high", "NULL") + "°");

        day_two_time.setText(getWeek(sharedPreferences.getString("day_two_time", "NULL")));
        day_two_con.setText(sharedPreferences.getString("day_two_con", "NULL"));
        day_two_temp_below.setText(sharedPreferences.getString("day_two_temp_below", "NULL") + "°" + "/" + sharedPreferences.getString("day_two_temp_high", "NULL") + "°");

        day_three_time.setText(getWeek(sharedPreferences.getString("day_three_time", "NULL")));
        day_three_con.setText(sharedPreferences.getString("day_three_con", "NULL"));
        day_three_temp_below.setText(sharedPreferences.getString("day_three_temp_below", "NULL") + "°" + "/" + sharedPreferences.getString("day_three_temp_high", "NULL") + "°");

        condition_code = sharedPreferences.getInt("condition_code", 100);
        ChangeBackground background = new ChangeBackground(layout, condition_code);
        closeProgressDialog();
    }


    /**
     * about
     */
    private void showDialog() {
        new AlertDialog.Builder(ShowWeatherActivity.this).setTitle("关于")
                .setMessage("感谢使用极简天气，欢迎到我的Github项目里Fork and Star^_^,如果想了解详细的开发过程，也欢迎来我的Blog逛一逛(｡・`ω´･)")
                .setPositiveButton("屈尊一逛", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse(getString(R.string.Blog));   //指定网址
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);           //指定Action
                        intent.setData(uri);                            //设置Uri
                        ShowWeatherActivity.this.startActivity(intent);        //启动Activity
                    }
                }).setNeutralButton("围观Github", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse(getString(R.string.Github));   //指定网址
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);           //指定Action
                intent.setData(uri);                            //设置Uri
                ShowWeatherActivity.this.startActivity(intent);        //启动Activity
            }
        })
                .show();
    }

    /**
     * query Weather info
     *
     * @param countyName
     */
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

                        Log.i("log", "onSuccess" + responseString);
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

    /**
     * convert date to weekday
     *
     * @param sDate
     * @return
     */
    public static String getWeek(String sDate) {
        try {
            Date date = null;
            SimpleDateFormat formater = new SimpleDateFormat();
            formater.applyPattern("yyyy-MM-dd");
            date = formater.parse(sDate);
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0) {
                w = 0;
            }
            return weekDays[w];
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (v == left_menu) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }


    /**
     * feedback setting
     */
    private void feedSetting() {
        FeedbackSettings feedbackSettings = new FeedbackSettings();

        //SUBMIT-CANCEL BUTTONS
        feedbackSettings.setCancelButtonText("取消");
        feedbackSettings.setSendButtonText("发送");

        //DIALOG TEXT
        feedbackSettings.setText("如果您有更好的建议或者有Bug要反馈，请在下面的输入框里提交相应信息并留下联系方式以便开发者及时回复(ง •̀_•́)ง");
        feedbackSettings.setYourComments("请在此输入信息...");
        feedbackSettings.setTitle("反馈");

        //TOAST MESSAGE
        feedbackSettings.setToast("非常感谢！");
        feedbackSettings.setToastDuration(Toast.LENGTH_SHORT);  // Default

        //RADIO BUTTONS
        feedbackSettings.setRadioButtons(false); // Disables radio buttons
        feedbackSettings.setBugLabel("Bug");
        feedbackSettings.setIdeaLabel("建议");

        //RADIO BUTTONS ORIENTATION AND GRAVITY
        feedbackSettings.setOrientation(LinearLayout.HORIZONTAL); // Default
        feedbackSettings.setGravity(Gravity.RIGHT); // Default

        //SET DIALOG MODAL
        feedbackSettings.setModal(true); //Default is false

        //DEVELOPER REPLIES
        feedbackSettings.setReplyTitle("来自开发者的消息");
        feedbackSettings.setReplyCloseButtonText("关闭");
        feedbackSettings.setReplyRateButtonText("OK");

        feedBack = new FeedbackDialog(this, "AF-91B15C013B82-9F", feedbackSettings);
        feedBack.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
        feedBack.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (layout.getBackground() != null) {

            Bitmap oldBitmap = ((BitmapDrawable) layout.getBackground()).getBitmap();

            layout.setBackground(null);

            if (oldBitmap != null) {

                oldBitmap.recycle();

                oldBitmap = null;

            }

        }
        System.gc();
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

    private ProgressDialog progressDialog;

    /**
     * show ProgressDialog
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载.....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * close ProgressDialog
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
