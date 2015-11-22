package kr.hs.zion.zionbob;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    Context mContext = MainActivity.this;
    String TAG = "MainActivity";

    String ProvienceCode = "goe";
    String SchooolCode = "J100000659";
    int SchoolTypeA = 4;
    String SchoolTypeB = "04";
    int lunch = 2;
    int dinner = 3;

    SwipeRefreshLayout SRL;
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

    DatePickerDialog DPD;
    DatePickerDialog.OnDateSetListener DPD_ODSL;

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

        SRL = (SwipeRefreshLayout)findViewById(R.id.srl);
        LunchCV = (CardView)findViewById(R.id.lunchcard);
        LunchTxt = (TextView)findViewById(R.id.lunchtxt);
        LunchAvr = (TextView)findViewById(R.id.lunchavr);
        DinnerCV = (CardView)findViewById(R.id.dinnercard);
        DinnerTxt = (TextView)findViewById(R.id.dinnertxt);
        DinnerAvr = (TextView)findViewById(R.id.dinneravr);

        Calendar Cal = Calendar.getInstance();
        year = Cal.get(Calendar.YEAR);
        month = Cal.get(Calendar.MONTH) + 1;
        day = Cal.get(Calendar.DAY_OF_MONTH);
        dayofweek = Cal.get(Calendar.DAY_OF_WEEK) - 1;

        SRL.setRefreshing(false);
        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMealData();
            }
        });
        getMealData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_date).setTitle(year+"."+month+"."+day);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_date){
            pickDate();
        }
        return super.onOptionsItemSelected(item);
    }

    void getMealData(){
        SRL.setRefreshing(true);
        Log.d(TAG, "Loading Data...");
        LunchCV.setVisibility(View.INVISIBLE);
        DinnerCV.setVisibility(View.INVISIBLE);
        getLunch();
    }

    void getLunch(){
        Log.d(TAG, "Loading Lunch Data");
        final MealDataUtil LunchObj = new MealDataUtil(ProvienceCode, SchooolCode, SchoolTypeA, SchoolTypeB, lunch, year+"."+month+"."+day);
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
                Log.d(TAG, LunchObj.Meal[dayofweek]);
                if (LunchObj.Meal[dayofweek].length() <= 1) {
                    LunchObj.Meal[dayofweek] = getResources().getString(R.string.error_meal_nodata);
                }
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
        final MealDataUtil DinnerObj = new MealDataUtil(ProvienceCode, SchooolCode, SchoolTypeA, SchoolTypeB, dinner, year+"."+month+"."+day);
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
                Log.d(TAG, DinnerObj.Meal[dayofweek]);
                if(DinnerObj.Meal[dayofweek].length()<=1){
                    DinnerObj.Meal[dayofweek] = getResources().getString(R.string.error_meal_nodata);
                }
                DinnerTxt.setText(DinnerObj.Meal[dayofweek]);
                LunchCV.setVisibility(View.VISIBLE);
                DinnerCV.setVisibility(View.VISIBLE);
                Log.d(TAG, "Dinner Data Loaded");
                SRL.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Failed Loading Dinner Data");
                Log.e(TAG, error.toString());
                LunchCV.setVisibility(View.VISIBLE);
                DinnerCV.setVisibility(View.VISIBLE);
                SRL.setRefreshing(false);
            }
        });
    }

    public void pickDate(){
        DPD_ODSL = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int Nyear, int NmonthOfYear, int NdayOfMonth) {
                year = Nyear;
                month = NmonthOfYear + 1;
                day = NdayOfMonth;
                Calendar NCal = Calendar.getInstance();
                NCal.set(year,month-1,day);
                dayofweek = NCal.get(Calendar.DAY_OF_WEEK) - 1;
                invalidateOptionsMenu();
                getMealData();
            }
        };
        DatePickerDialog DPD = new DatePickerDialog(mContext,DPD_ODSL,year,month-1,day);
        DPD.show();
    }
}
