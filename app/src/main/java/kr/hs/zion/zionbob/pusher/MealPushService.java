package kr.hs.zion.zionbob.pusher;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MealPushService extends Service {
    public MealPushService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
