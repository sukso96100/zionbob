package kr.hs.zion.zionbob;

import com.squareup.okhttp.ResponseBody;

import retrofit.http.GET;

/**
 * Created by youngbin on 15. 11. 21.
 */
public class MealDataLoader {
    public interface MealDataLoaderInterface{
        @GET("")
        public void getRawData(retrofit.Callback<ResponseBody> callback);
    }
}
