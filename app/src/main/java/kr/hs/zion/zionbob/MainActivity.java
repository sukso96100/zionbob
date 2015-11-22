package kr.hs.zion.zionbob;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    String ProvienceCode = "goe";
    String SchooolCode = "J100000659";
    int SchoolTypeA = 4;
    String SchoolTypeB = "04";
    int lunch = 2;
    int dinner = 3;

    CardView LunchCV;
    TextView LunchAvr;
    TextView LunchTxt;
    CardView DinnerCV;
    TextView DinnerAvr;
    TextView DinnerTxt;

    int year;
    int month;
    int day;
    int dayofweek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LunchCV = (CardView)findViewById(R.id.lunchcard);
        LunchTxt = (TextView)findViewById(R.id.lunchtxt);
        LunchAvr = (TextView)findViewById(R.id.lunchavr);
        DinnerCV = (CardView)findViewById(R.id.dinnercard);
        DinnerTxt = (TextView)findViewById(R.id.dinnertxt);
        DinnerAvr = (TextView)findViewById(R.id.dinneravr);

        Calendar Cal = Calendar.getInstance();
        year = Cal.get(Calendar.YEAR);
        month = Cal.get(Calendar.MONTH);
        day = Cal.get(Calendar.DAY_OF_MONTH);
        dayofweek = Cal.get(Calendar.DAY_OF_WEEK);

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    void getData(){
        Log.d(TAG, "Loading Data...");
        LunchCV.setVisibility(View.INVISIBLE);
        DinnerCV.setVisibility(View.INVISIBLE);
        getLunch();
    }

    void getLunch(){
        Log.d(TAG, "Loading Lunch Data");
        final MealDataUtil LunchObj = new MealDataUtil(ProvienceCode, SchooolCode, SchoolTypeA, SchoolTypeB, lunch,"");
        AsyncHttpClient LunchClient = new AsyncHttpClient();
        LunchClient.get(LunchObj.getURL(), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String RawData = null;
                try {
                    RawData = new String(responseBody, "UTF-8");
                    Log.d("JsonResponse", RawData);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, RawData);
                LunchObj.parseData(RawData);
                LunchTxt.setText(LunchObj.Meal[dayofweek]);
                Log.d(TAG, "Lunch Data Loaded");
                getDinner();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Failed Loading Lunch Data");
                Log.e(TAG, error.toString());
                getDinner();
            }
        });
    }

    void getDinner(){
        Log.d(TAG, "Loading Dinner Data");
        final MealDataUtil DinnerObj = new MealDataUtil(ProvienceCode, SchooolCode, SchoolTypeA, SchoolTypeB, dinner,"");
        AsyncHttpClient DinnerClient = new AsyncHttpClient();
        DinnerClient.get(DinnerObj.getURL(), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String RawData = null;
                try {
                    RawData = new String(responseBody, "UTF-8");
                    Log.d("JsonResponse", RawData);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, RawData);
                DinnerObj.parseData(RawData);
                DinnerTxt.setText(DinnerObj.Meal[dayofweek]);
                LunchCV.setVisibility(View.VISIBLE);
                DinnerCV.setVisibility(View.VISIBLE);
                Log.d(TAG, "Dinner Data Loaded");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Failed Loading Dinner Data");
                Log.e(TAG, error.toString());
                LunchCV.setVisibility(View.VISIBLE);
                DinnerCV.setVisibility(View.VISIBLE);
            }
        });
    }
}
