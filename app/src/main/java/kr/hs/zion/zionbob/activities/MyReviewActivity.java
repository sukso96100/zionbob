package kr.hs.zion.zionbob.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import kr.hs.zion.zionbob.R;
import kr.hs.zion.zionbob.util.GuidTool;

public class MyReviewActivity extends AppCompatActivity {
    Context mContext = MyReviewActivity.this;
    String TAG = "MyReviewActivity";

    RatingBar RB;
    EditText ET;
    ParseObject OBJ;
    String Date;
    String GUID;
    int POS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Date = getIntent().getStringExtra("date");
        GuidTool GT = new GuidTool(mContext);
        GUID = GT.getGUID();

        RB = (RatingBar)findViewById(R.id.ratingBar);
        ET = (EditText)findViewById(R.id.editText);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.whereEqualTo("date", Date);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d(TAG, "The getFirst request failed.");

                } else {
                    Log.d(TAG, "GOT DATA");
                    OBJ = object;
                    int myReviewPos = findMyReview(GUID, object.getList("guids"));
                    Log.d(TAG, "POS : "+myReviewPos);
                    if(myReviewPos!=-1){
                    RB.setNumStars(5);
                        try{
                            double d = (double) object.getList("rates").get(myReviewPos);
                            RB.setRating((float)d);
                        }catch(Exception E){
                            int d = (int) object.getList("rates").get(myReviewPos);
                            RB.setRating((float)d);
                        }

                    ET.setText((String)object.getList("reviews").get(myReviewPos));
                    }
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_myreview, menu);
        // Set date
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_done){
//            pickDate();
            sendReview(POS);
        }
        return super.onOptionsItemSelected(item);
    }

    //
    int findMyReview(String GUID, List<Object> GUIDs){
        for(int i=0; i<GUIDs.size(); i++){
            if(GUIDs.get(i).toString().equals(GUID)){
                Log.d(TAG, "Found POS : "+i);
                POS = i;
                return i;
            }
        }
        return -1;
    }

    void sendReview(int pos){
        if(OBJ==null){
            ParseObject ReviewObj = new ParseObject("Reviews");
            ReviewObj.put("date", Date);
            ArrayList<String> nGUIDs = new ArrayList<String>();
            ArrayList<Float> nRates = new ArrayList<Float>();
            ArrayList<String> nReviews = new ArrayList<String>();
            nGUIDs.add(GUID);
            nRates.add(RB.getRating());
            nReviews.add(ET.getText().toString());
            ReviewObj.put("guids", nGUIDs);
            ReviewObj.put("rates", nRates);
            ReviewObj.put("reviews", nReviews);
            ReviewObj.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e!=null){
                        Log.e(TAG, e.toString());
                    }
                    Log.d(TAG, "Created New Review Object then added my Rreview");
                    finish();
                }
            });
        }else{
            if(POS!=-1){
                List<Object> RATES = OBJ.getList("rates");
                List<Object> REVIEWS = OBJ.getList("reviews");
                RATES.set(pos, RB.getRating());
                REVIEWS.set(pos, ET.getText().toString());
                OBJ.put("rates",RATES);
                OBJ.put("reviews",REVIEWS);
                OBJ.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e!=null){
                            Log.e(TAG, e.toString());
                        }
                        Log.d(TAG, "updated my review");
                        finish();
                    }
                });
                }else{
                    OBJ.getList("guids").add(GUID);
                    OBJ.getList("rates").add(RB.getRating());
                    OBJ.getList("reviews").add(ET.getText().toString());
                    OBJ.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e!=null){
                                Log.e(TAG, e.toString());
                            }
                            Log.d(TAG, "added my review");
                            finish();
                        }
                    });
                }
        }

    }
}
