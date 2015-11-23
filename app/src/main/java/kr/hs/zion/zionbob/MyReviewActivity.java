package kr.hs.zion.zionbob;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.RasterizerSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MyReviewActivity extends AppCompatActivity {
    Context mContext = MyReviewActivity.this;
    String TAG = "MyReviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String Date = getIntent().getStringExtra("date");
        GuidTool GT = new GuidTool(mContext);
        final String GUID = GT.getGUID();

        final RatingBar RB = (RatingBar)findViewById(R.id.ratingBar);
        final EditText ET = (EditText)findViewById(R.id.editText);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.whereEqualTo("date", Date);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d(TAG, "The getFirst request failed.");

                } else {
                    int myReviewPos = findMyReview(GUID, object.getList("guids"));
                    if(myReviewPos!=-1){
                    RB.setNumStars(5);
                    RB.setRating((float) object.getList("rates").get(myReviewPos));
                    ET.setText((String)object.getList("reviews").get(myReviewPos));
                    }
                }
            }
        });
    }

    int findMyReview(String GUID, List<Object> GUIDs){
        for(int i=0; i<GUIDs.size(); i++){
            if(GUIDs.get(i)==GUID){
                return i;
            }
        }
        return -1;
    }
}
