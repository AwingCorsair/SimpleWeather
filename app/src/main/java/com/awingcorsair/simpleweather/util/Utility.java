package com.awingcorsair.simpleweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.awingcorsair.simpleweather.Entity.Aqi;
import com.awingcorsair.simpleweather.Entity.Astro;
import com.awingcorsair.simpleweather.Entity.Basic;
import com.awingcorsair.simpleweather.Entity.DailyForecast;
import com.awingcorsair.simpleweather.Entity.Now;
import com.awingcorsair.simpleweather.model.City;
import com.awingcorsair.simpleweather.model.County;
import com.awingcorsair.simpleweather.model.Province;
import com.awingcorsair.simpleweather.model.Result;
import com.awingcorsair.simpleweather.model.Weather;
import com.awingcorsair.simpleweather.model.WeatherDB;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Mao on 2016/4/3.
 */
public class Utility {
    /**
     * analysis and handle the Province data from server
     */
    public synchronized static boolean handleProvincesResponse(WeatherDB weatherDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces=response.split(",");
            if(allProvinces!=null&&allProvinces.length>0){
                for(String p:allProvinces){
                    String[] array=p.split("\\|"); //escape "|"
                    Province province=new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * analysis and handle the City data from server
     */
    public static boolean handlCitiesResponse(WeatherDB weatherDB,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allCities=response.split(",");
            if(allCities!=null&&allCities.length>0){
                for(String c:allCities){
                    String[] array=c.split("\\|");
                    City city=new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * analysis and handle County from server
     */
    public static boolean handleCountiesResponse(WeatherDB weatherDB,String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] allCounties=response.split(",");
            if(allCounties!=null&&allCounties.length>0){
                for(String c:allCounties){
                    String[] array=c.split("\\|");
                    County county=new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * handle JSON response from Api
     */
    public static void handleWeatherResponse(Context context,String response){
        Gson gson=new Gson();
        Result result = gson.fromJson(response, Result.class);
        List<Weather> weatherInfoList = result.getWeatherInfoList();
        Weather weatherInfo = weatherInfoList.get(0);
        Aqi aqi=weatherInfo.getAqi();
        Now now=weatherInfo.getNow();
        Basic basic=weatherInfo.getBasic();
        String cityName=basic.getCity();
        String tempNow=now.getTmp();
        String feelTemp=now.getFl();
        String currentCondition=now.getCond().getTxt();

        //initialize today
        DailyForecast dailyforecast_today =weatherInfo.getDaily_forecast().get(0);
        Astro astro_today= dailyforecast_today.getAstro();
        String sunRiseToday=astro_today.getSr();
        String sunDownToday=astro_today.getSs();
        String tempBelowToday= dailyforecast_today.getTmp().getMin();
        String tempHighToday= dailyforecast_today.getTmp().getMax();
        String wet= dailyforecast_today.getHum();
        String pm25=aqi.getCity().getPm25();
        Log.d("lo","pm2.5:"+pm25);
        int condition_code=now.getCond().getCode();

        //initialize day1
        DailyForecast dailyforecast_day1 =weatherInfo.getDaily_forecast().get(1);
        String timeDay1= dailyforecast_day1.getDate();
        String conditionDay1= dailyforecast_day1.getCond().getTxt_d();
        String tempBelowDay1= dailyforecast_day1.getTmp().getMin();
        String tempHighDay1= dailyforecast_day1.getTmp().getMax();

        //initialize day2
        DailyForecast dailyforecast_day2 =weatherInfo.getDaily_forecast().get(2);
        String timeDay2= dailyforecast_day2.getDate();
        String conditionDay2= dailyforecast_day2.getCond().getTxt_d();
        String tempBelowDay2= dailyforecast_day2.getTmp().getMin();
        String tempHighDay2= dailyforecast_day2.getTmp().getMax();

        //initialize day3
        DailyForecast dailyforecast_day3 =weatherInfo.getDaily_forecast().get(3);
        String timeDay3= dailyforecast_day3.getDate();
        String conditionDay3= dailyforecast_day3.getCond().getTxt_d();
        String tempBelowDay3= dailyforecast_day3.getTmp().getMin();
        String tempHighDay3= dailyforecast_day3.getTmp().getMax();

        saveWeatherInfoBasic(context, cityName, tempNow, feelTemp, currentCondition,condition_code);
        saveWeatherInfoToday(context,sunRiseToday,sunDownToday,tempBelowToday,tempHighToday,wet,pm25);
        saveWeatherInfoDay1(context, timeDay1, conditionDay1, tempBelowDay1, tempHighDay1);
        saveWeatherInfoDay2(context, timeDay2, conditionDay2, tempBelowDay2, tempHighDay2);
        saveWeatherInfoDay3(context, timeDay3, conditionDay3, tempBelowDay3, tempHighDay3);
    }

    /**
     * save Weather Info of Basic
     */
    public static void saveWeatherInfoBasic(Context context,String cityName,String tempNow,String feelTemp,String currentCondition,int condition_code){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name", cityName);
        editor.putString("temp_now", tempNow);
        editor.putString("feel_temp", feelTemp);
        editor.putString("current_condition", currentCondition);
        editor.putInt("condition_code", condition_code);
        editor.commit();

    }

    /**
     * save weather info of Today
     */
    public static void saveWeatherInfoToday(Context context,String sunRiseToday,String sunDownToday,String tempBelowToday,String tempHighToday,String wet,String pm25){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("sun_rise",sunRiseToday);
        editor.putString("sun_down",sunDownToday);
        editor.putString("temp_below",tempBelowToday);
        editor.putString("temp_high",tempHighToday);
        editor.putString("wet",wet);
        editor.putString("pm25",pm25);
        editor.commit();
    }

    /**
     * save weather info of Day1
     */
    public static void saveWeatherInfoDay1(Context context,String timeDay1,String conditionDay1,String tempBelowDay1,String tempHighDay1){
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("day_one_time",timeDay1);
        editor.putString("day_one_con",conditionDay1);
        editor.putString("day_one_temp_below",tempBelowDay1);
        editor.putString("day_one_temp_high",tempHighDay1);
        editor.commit();
    }

    /**
     * save weather info of Day2
     */
    public static void saveWeatherInfoDay2(Context context,String timeDay2,String conditionDay2,String tempBelowDay2,String tempHighDay2){
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("day_two_time",timeDay2);
        editor.putString("day_two_con",conditionDay2);
        editor.putString("day_two_temp_below",tempBelowDay2);
        editor.putString("day_two_temp_high",tempHighDay2);
        editor.commit();
    }

    /**
     * save weather info of Day3
     */
    public static void saveWeatherInfoDay3(Context context,String timeDay3,String conditionDay3,String tempBelowDay3,String tempHighDay3){
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("day_three_time",timeDay3);
        editor.putString("day_three_con",conditionDay3);
        editor.putString("day_three_temp_below",tempBelowDay3);
        editor.putString("day_three_temp_high",tempHighDay3);
        editor.commit();
    }
}
