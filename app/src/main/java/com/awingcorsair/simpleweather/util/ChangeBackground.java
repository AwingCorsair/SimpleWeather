package com.awingcorsair.simpleweather.util;


import android.text.format.Time;
import android.widget.LinearLayout;

import com.awingcorsair.simpleweather.R;


/**
 * Created by Mao on 2016/4/13.
 */
public class ChangeBackground{

    public ChangeBackground(LinearLayout layout,int condition_code){
        Time t=new Time(); // or Time t=new Time("GMT+8");
        t.setToNow(); // get system time
    //    int year = t.year;
    //    int month = t.month+1;
    //    int date = t.monthDay;
        int hour = t.hour;
    //    Drawable day_clearsky=resources.getDrawable(R.drawable.day_clearsky);

        if(hour>=18||hour<=6){
            //according to http://www.heweather.com/documents/condition-code
            switch (condition_code) {
                case 100:
                    layout.setBackgroundResource(R.drawable.night_clearsky);
                    break;
                case 101:
                    layout.setBackgroundResource(R.drawable.night_cloudy);
                    break;
                case 102:
                case 103:
                case 104:
                    layout.setBackgroundResource(R.drawable.night_partlycloudy);
                    break;
                case 300:
                case 301:
                case 302:
                case 303:
                case 304:
                case 305:
                case 306:
                case 307:
                case 308:
                case 309:
                case 310:
                case 311:
                case 312:
                case 313:
                    layout.setBackgroundResource(R.drawable.night_rain);
                    break;
                case 400:
                case 401:
                case 402:
                case 403:
                case 404:
                case 405:
                case 406:
                case 407:
                    layout.setBackgroundResource(R.drawable.night_snow);
                    break;
                case 500:
                case 501:
                case 502:
                case 503:
                case 504:
                case 507:
                case 508:
                    layout.setBackgroundResource(R.drawable.day_fog);
                    break;
                default:
                    layout.setBackgroundResource(R.drawable.night_clearsky);
                    break;
            }
        }else {
            switch (condition_code) {
                case 100:
                    layout.setBackgroundResource(R.drawable.day_clearsky);
                    break;
                case 101:
                    layout.setBackgroundResource(R.drawable.day_cloudy);
                    break;
                case 102:
                case 103:
                case 104:
                    layout.setBackgroundResource(R.drawable.day_partlycloudy);
                    break;
                case 300:
                case 301:
                case 302:
                case 303:
                case 304:
                case 305:
                case 306:
                case 307:
                case 308:
                case 309:
                case 310:
                case 311:
                case 312:
                case 313:
                    layout.setBackgroundResource(R.drawable.day_rain);
                    break;
                case 400:
                case 401:
                case 402:
                case 403:
                case 404:
                case 405:
                case 406:
                case 407:
                    layout.setBackgroundResource(R.drawable.day_snow);
                    break;
                case 500:
                case 501:
                case 502:
                case 503:
                case 504:
                case 507:
                case 508:
                    layout.setBackgroundResource(R.drawable.day_fog);
                    break;
                default:
                    layout.setBackgroundResource(R.drawable.day_clearsky);
                    break;
            }
        }
//        if(hour>=18||hour<=6){
//            //according to http://www.heweather.com/documents/condition-code
//            switch (condition_code) {
//                case 100:
//                    Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.night_clearsky);
//                    BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
//                    layout.setBackgroundDrawable(bd);
//                    break;
//                case 101:
//                    Bitmap bm1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.night_cloudy);
//                    BitmapDrawable bd1 = new BitmapDrawable(context.getResources(), bm1);
//                    layout.setBackgroundDrawable(bd1);
//                    break;
//                case 102:
//                case 103:
//                case 104:
//                case 300:
//                case 301:
//                case 302:
//                case 303:
//                case 304:
//                case 305:
//                case 306:
//                case 307:
//                case 308:
//                case 309:
//                case 310:
//                case 311:
//                case 312:
//                case 313:
//
//                case 400:
//                case 401:
//                case 402:
//                case 403:
//                case 404:
//                case 405:
//                case 406:
//                case 407:
//
//                case 500:
//                case 501:
//                case 502:
//                case 503:
//                case 504:
//                case 507:
//                case 508:
//
//                default:
//                    Bitmap bm2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.night_snow);
//                    BitmapDrawable bd2 = new BitmapDrawable(context.getResources(), bm2);
//                    layout.setBackgroundDrawable(bd2);
//                    break;
//            }
//        }else {
//            switch (condition_code) {
//                case 100:
//                    layout.setBackgroundResource(R.drawable.day_clearsky);
//                    break;
//                case 101:
//                    layout.setBackgroundResource(R.drawable.day_cloudy);
//                    break;
//                case 102:
//                case 103:
//                case 104:
//                    layout.setBackgroundResource(R.drawable.day_partlycloudy);
//                    break;
//                case 300:
//                case 301:
//                case 302:
//                case 303:
//                case 304:
//                case 305:
//                case 306:
//                case 307:
//                case 308:
//                case 309:
//                case 310:
//                case 311:
//                case 312:
//                case 313:
//                    layout.setBackgroundResource(R.drawable.day_rain);
//                    break;
//                case 400:
//                case 401:
//                case 402:
//                case 403:
//                case 404:
//                case 405:
//                case 406:
//                case 407:
//                    layout.setBackgroundResource(R.drawable.day_snow);
//                    break;
//                case 500:
//                case 501:
//                case 502:
//                case 503:
//                case 504:
//                case 507:
//                case 508:
//                    layout.setBackgroundResource(R.drawable.day_fog);
//                    break;
//                default:
//                    layout.setBackgroundResource(R.drawable.day_clearsky);
//                    break;
//            }
//        }
    }
}
