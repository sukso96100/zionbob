package kr.hs.zion.zionbob.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by youngbin on 15. 11. 23.
 */
public class GuidTool {
    UUID UUID;
    String TAG = "GuidTool";
    Context mContext;
    SharedPreferences Pref;
    String GUID;
    public GuidTool(Context context){
        this.mContext = context;
        Pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
    }

    public String getGUID(){
        //Get GUID
        GUID = Pref.getString("guid","NOGUID");
        if(GUID.equals("NOGUID")){
            //No GUID? Create New One
            GUID = createGUID();
            SharedPreferences.Editor Editor = Pref.edit();
            Editor.putString("guid",GUID);
            Editor.commit();
        }
        Log.d(TAG, "Your GUID Value : " + GUID);

        return GUID;
    }

    public String createGUID(){
        UUID uuid = UUID.randomUUID();
//        Calendar c = Calendar.getInstance();
//        String NewGUID = "GUID"
//                + c.get(Calendar.YEAR)
//                + c.get(Calendar.MONTH)
//                + c.get(Calendar.DAY_OF_MONTH)
//                + c.get(Calendar.HOUR_OF_DAY)
//                + c.get(Calendar.MINUTE)
//                + c.get(Calendar.MILLISECOND);
        return uuid.toString();
    }

    public void genGUID(){
        SharedPreferences.Editor Editor = Pref.edit();
        String NEW = createGUID();
        Editor.putString("guid",NEW);
        Editor.commit();
    }
}