package kr.hs.zion.zionbob.pusher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by youngbin on 15. 11. 24.
 */
public class MealAlarmManager {
    int LunchHour = 12;
    int LunchMin = 30;
    Context mContext;
    AlarmManager AM;

    public MealAlarmManager(Context context){
        mContext = context;
        
        // Get Alarm Manager
        AM = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
    }
    void registerAlarm(){

        // Lunch Alarm Time
        Calendar mLunch = Calendar.getInstance();
        mLunch.set(Calendar.HOUR_OF_DAY, 12);
        mLunch.set(Calendar.MINUTE, 20);

        // Intent For Lunch
        Intent LunchIntent = new Intent(mContext, MealPushService.class);
        LunchIntent.putExtra("mealtype", 2);
        PendingIntent LunchPI = PendingIntent.getService(mContext, 1, LunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set Lunch Alarm
        AM.setRepeating(AlarmManager.RTC_WAKEUP, mLunch.getTimeInMillis(), AlarmManager.INTERVAL_DAY, LunchPI);

        // Dinner Alarm Time
        Calendar mDinner = Calendar.getInstance();
        mDinner.set(Calendar.HOUR_OF_DAY, 17);
        mDinner.set(Calendar.MINUTE, 20);

        // Intent For Dinner
        Intent DinnerIntent = new Intent(mContext, MealPushService.class);
        DinnerIntent.putExtra("mealtype", 3);
        PendingIntent DinnerPI = PendingIntent.getService(mContext, 2, DinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set Dinner Alarm
        AM.setRepeating(AlarmManager.RTC_WAKEUP, mDinner.getTimeInMillis(), AlarmManager.INTERVAL_DAY, DinnerPI);
    }

    void calcelAlarm(){
        for(int i=2; i<4; i++){
            // Calel Push Alarm
            Intent intent = new Intent(mContext, MealPushService.class);
            PendingIntent PI4CALCEL = PendingIntent.getService(mContext, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AM.cancel(PI4CALCEL);
        }
    }
}
