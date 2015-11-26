package kr.hs.zion.zionbob.pusher;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import kr.hs.zion.zionbob.MealDataUtil;
import kr.hs.zion.zionbob.R;
import kr.hs.zion.zionbob.data.MealDataCacheManager;
import kr.hs.zion.zionbob.util.MealDataProcessor;

public class MealPushService extends Service {

    Context mContext = MealPushService.this;

    String TAG = "MealPushService";

    String ProvienceCode = "goe";
    String SchooolCode = "J100000659";
    int SchoolTypeA = 4;
    String SchoolTypeB = "04";

    int year;
    int month;
    int day;
    int dayofweek;

    MealDataCacheManager Cache;
    
    String DATE;

    public MealPushService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        // Get Date of Today
        Calendar Cal = Calendar.getInstance();
        year = Cal.get(Calendar.YEAR);
        month = Cal.get(Calendar.MONTH)+1;
        day = Cal.get(Calendar.DAY_OF_MONTH)-1;
        dayofweek = Cal.get(Calendar.DAY_OF_WEEK)-1;

        // Get Meal Type
        final int mealtype = intent.getIntExtra("mealtype", 2);

        // Create Date String
        DATE = year+"."+month+"."+day;


        Log.d(TAG, "Loading Meal Data for Push Notification");
        final MealDataUtil MealObj = new MealDataUtil(ProvienceCode, SchooolCode,
                SchoolTypeA, SchoolTypeB, mealtype, DATE);
        AsyncHttpClient MealClient = new AsyncHttpClient();
        MealClient.get(MealObj.getURL(), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String RawData = null;
                try {
                    RawData = new String(responseBody, "UTF-8");
                    Log.d("Response", RawData);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, RawData);
                MealObj.parseData(RawData);
                Log.d(TAG, MealObj.Meal[dayofweek]);
                if (MealObj.Meal[dayofweek].length() <= 1) {
                    MealObj.Meal[dayofweek] = getResources().getString(R.string.error_meal_nodata);
                }
                if(mealtype==2){
                    MealPushNotification.notify(mContext,
                            DATE+" - "+mContext.getResources().getString(R.string.lunch),
                            DATE+" - "+mContext.getResources().getString(R.string.lunch),
                            MealObj.Meal[dayofweek], 0);
                }else if(mealtype==3){

                }

                Log.d(TAG, "Meal Data Loaded");
                // Cache Data
                Cache = new MealDataCacheManager(mContext);
                Cache.updateCache(DATE+"_"+mealtype, MealObj.Meal[dayofweek],
                        MealDataProcessor.processOriginData(MealObj, dayofweek, mContext),
                        MealDataProcessor.processNutrientsData(MealObj, dayofweek, mContext));
                stopSelf();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Failed Loading Meal Data");
                stopSelf();
            }
        });

        return START_STICKY;
    }
}
