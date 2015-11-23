package kr.hs.zion.zionbob;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;

/**
 * Created by youngbin on 15. 11. 23.
 */
public class ZionBobApp extends Application {
    String TAG = "ZionBobApp";
    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize Parse SDK
        Log.d(TAG, "Initializing Parse SDK");
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "q7kS8LbOzzkfTmwWHm7W2I2F6Ly0fYQtNyOvlwR1", "4D5RIxEj9OcXQw38uNjbxORvlMI6yZ1cSTsBvEKx");

        GuidTool GT = new GuidTool(ZionBobApp.this);
    }
}

