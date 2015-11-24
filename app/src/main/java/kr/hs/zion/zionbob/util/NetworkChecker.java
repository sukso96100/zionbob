package kr.hs.zion.zionbob.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by youngbin on 15. 11. 24.
 */
public class NetworkChecker {
    Context Ctx;
    ConnectivityManager cManager;
    public NetworkChecker(Context context){
        this.Ctx = context;

    }

    public boolean isNetworkConnected(){
        cManager =
                (ConnectivityManager)Ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cManager.getActiveNetworkInfo();
        if(activeNetwork==null){
            return false;
        }else{
            return activeNetwork.isConnected();
        }
    }
}