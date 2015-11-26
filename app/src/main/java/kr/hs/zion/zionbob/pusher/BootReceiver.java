package kr.hs.zion.zionbob.pusher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("BOOT", "ON BOOT");
        SharedPreferences SP = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        boolean isAlarmOn = SP.getBoolean("push", false);
        MealAlarmManager MAM = new MealAlarmManager(context);
        if(isAlarmOn){
            MAM.registerAlarm();
        }else {
            MAM.calcelAlarm();
        }
    }
}
