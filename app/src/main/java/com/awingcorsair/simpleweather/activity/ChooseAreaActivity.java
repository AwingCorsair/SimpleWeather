package com.awingcorsair.simpleweather.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.awingcorsair.simpleweather.R;
import com.awingcorsair.simpleweather.model.WeatherDB;
import com.awingcorsair.simpleweather.model.City;
import com.awingcorsair.simpleweather.model.County;
import com.awingcorsair.simpleweather.model.Province;
import com.awingcorsair.simpleweather.util.HttpCallbackListener;
import com.awingcorsair.simpleweather.util.HttpUtil;
import com.awingcorsair.simpleweather.util.Utility;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2016/4/3.
 */
public class ChooseAreaActivity extends AppCompatActivity {
    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private WeatherDB weatherDB;
    private List<String> dataList = new ArrayList<String>();
    private Button locateButton;

    private LocationClient mLocationClient;

    /**
     * Province list
     */
    private List<Province> provinceList;
    /**
     * City list
     */
    private List<City> cityList;
    /**
     * County list
     */
    private List<County> countyList;
    /**
     * selected Province
     */
    private Province selectedProvince;
    /**
     * selected City
     */
    private City selectedCity;
    /**
     * selected level
     */
    private int currentLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean("city_selected",false)){
            Intent intent=new Intent(ChooseAreaActivity.this,ShowWeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_text);
        locateButton = (Button) findViewById(R.id.locate);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        weatherDB = WeatherDB.getInstance(this);
        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                //        String str = ((LocationApplication) getApplication()).getLocate_result();
                //Toast.makeText(ChooseAreaActivity.this, "result=" + locate_result, Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    Log.d("why", "1 position : " + position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String countyName = countyList.get(position).getCountyName();
                    Intent intent = new Intent(ChooseAreaActivity.this, ShowWeatherActivity.class);
                    intent.putExtra("countyName", countyName);
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryProvinces();

    }

    /**
     * query Province from database or server
     */
    private void queryProvinces() {
        provinceList = weatherDB.loadProvinces();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else {
            queryFromServer(null, "province");
        }
    }

    /**
     * query City from database or server
     */
    private void queryCities() {
        cityList = weatherDB.loadCities(selectedProvince.getId());
        Log.d("why", "selectedProvinces:" + selectedProvince.getId());
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
                Log.d("why", "city is :" + city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        } else {
            queryFromServer(selectedProvince.getProvinceCode(), "city");
        }
    }

    /**
     * query County from database or server
     */
    private void queryCounties() {
        countyList = weatherDB.loadCounties(selectedCity.getId());
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        } else {
            queryFromServer(selectedCity.getCityCode(), "county");
        }
    }

    /**
     * query from server
     */
    private void queryFromServer(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvincesResponse(weatherDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.handlCitiesResponse(weatherDB, response, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountiesResponse(weatherDB, response, selectedCity.getId());
                }
                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * show ProgressDialog
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载.....");
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

    /**
     * set Back logic
     */
    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        } else {
            finish();
        }
    }

    public MyLocationListener mMyLocationListener;//定义监听类

    /**
     * start location
     */
    private void getLocation() {
        //    mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
        option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {

        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            //        sb.append("\nerror code : ");
            //        sb.append(location.getLocType());//获得erro code得知定位现状
            if (location.getLocType() == BDLocation.TypeGpsLocation) {//通过GPS定位
                //            sb.append("\n地址为 : ");
                sb.append(location.getAddrStr());//获得当前地址
                //            sb.append(location.getDirection());//获得方位
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {//通过网络连接定位
                //            sb.append("\n地址为 : ");
                sb.append(location.getDistrict());//获得当前地址
            }
            //        Toast.makeText(getApplicationContext(),"Address:"+location.getAddrStr(),Toast.LENGTH_SHORT).show();
            mLocationClient.stop();
        //    String str = location.getDistrict();
            String str=location.getCity();
            String locate_result = str.substring(0, str.length() - 1);
            //Toast.makeText(ChooseAreaActivity.this,"定位失败"+location.getLocType(),Toast.LENGTH_SHORT).show();

            //show weather
            if(location.getLocType()==61||location.getLocType()==161) {
                Intent intent = new Intent(ChooseAreaActivity.this, ShowWeatherActivity.class);
                intent.putExtra("countyName", locate_result);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(ChooseAreaActivity.this,"定位失败，请手动选择城市",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
