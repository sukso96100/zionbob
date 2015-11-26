package kr.hs.zion.zionbob.pusher;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

public class MealPushService extends Service {
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
        int year = Cal.get(Calendar.YEAR);
        int month = Cal.get(Calendar.MONTH);
        int day = Cal.get(Calendar.DAY_OF_MONTH);
        int dayofweek = Cal.get(Calendar.DAY_OF_WEEK);

        // Get Meal Type
        int mealtype = intent.getIntExtra("mealtype",2);

        // Create Date String
        String DATE = year+"."+month+"."+day;


        return START_NOT_STICKY;
    }
}
