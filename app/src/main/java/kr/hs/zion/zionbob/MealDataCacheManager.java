package kr.hs.zion.zionbob;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by youngbin on 15. 11. 24.
 */
public class MealDataCacheManager {
    String TAG = "MealDataCacheManager";

    Context mContext;
    Realm mRealm;

    public MealDataCacheManager(Context c){
        mContext = c;
        mRealm = Realm.getInstance(mContext);
    }

    public void updateCache(String Date, String Meal,
                            String Origin, String Nutrients){
        RealmQuery<MealDataCacheModel> query = mRealm.where(MealDataCacheModel.class);
        query.equalTo("date",Date);
        MealDataCacheModel result = query.findFirst();
        if(result==null){
            Log.d(TAG, "Caching New Data");
            mRealm.beginTransaction();
            MealDataCacheModel MDCM = mRealm.createObject(MealDataCacheModel.class);
            MDCM.setDate(Date);
            MDCM.setMeal(Meal);
            MDCM.setOrigin(Origin);
            MDCM.setNutrients(Nutrients);
            mRealm.commitTransaction();
        }else{
            Log.d(TAG, "Updating Cached Data");
            mRealm.beginTransaction();
            result.setMeal(Meal);
            result.setOrigin(Origin);
            result.setNutrients(Nutrients);
            mRealm.commitTransaction();
        }
    }

    public String[] getFromCache(String Date){
        RealmQuery<MealDataCacheModel> query = mRealm.where(MealDataCacheModel.class);
        query.equalTo("date",Date);
        MealDataCacheModel result = query.findFirst();
        String CachedData[] = new String[4];
        CachedData[0] = result.getDate();
        CachedData[1] = result.getMeal();
        CachedData[2] = result.getOrigin();
        CachedData[3] = result.getNutrients();
        return CachedData;
    }

}