package kr.hs.zion.zionbob;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.CoordinatorLayout;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import kr.hs.zion.zionbob.activities.About;
import kr.hs.zion.zionbob.activities.DetailsActivity;
import kr.hs.zion.zionbob.activities.FirstRunActivity;
import kr.hs.zion.zionbob.data.MealDataCacheManager;
import kr.hs.zion.zionbob.util.MealDataProcessor;

public class MainActivity extends AppCompatActivity {
    Context mContext = MainActivity.this;
    String TAG = "MainActivity";

    String ProvienceCode = "goe";
    String SchooolCode = "J100000659";
    int SchoolTypeA = 4;
    String SchoolTypeB = "04";
    int lunch = 2;
    int dinner = 3;

    CoordinatorLayout Root;
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

    MealDataUtil LunchMduObj;
    MealDataUtil DinnerMduObj;

    static String[] PARAMS;

    MealDataCacheManager Cache;
    boolean LoadFromCache = false;
    String[] LunchCache;
    String[] DinnerCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FirstRun
        SharedPreferences Pref = getSharedPreferences("pref", MODE_PRIVATE);
        boolean FirstRun = Pref.getBoolean("firstrun", true);
        if(FirstRun){
            startActivity(new Intent(mContext, FirstRunActivity.class));
            finish();
        }


        Root = (CoordinatorLayout)findViewById(R.id.root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareMealData();
            }
        });

        PARAMS = getResources().getStringArray(R.array.array_params);
        Cache = new MealDataCacheManager(mContext);

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

        LunchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass some(?) data to DetailsActivity
                Intent LunchIntent = new Intent(mContext, DetailsActivity.class);
                // Basic Info
                LunchIntent.putExtra("mealtype", 2);
                LunchIntent.putExtra("date", year + "." + month + "." + day);
                if (!LoadFromCache) {
                    // Meal
                    LunchIntent.putExtra("meal", LunchMduObj.Meal[dayofweek]);
                    // Origin of Ingredients
                    LunchIntent.putExtra("origin", MealDataProcessor.processOriginData(LunchMduObj, dayofweek, mContext));
                    // Nutrients
                    LunchIntent.putExtra("nutrients", MealDataProcessor.processNutrientsData(LunchMduObj, dayofweek, mContext));
                }else{
                    LunchIntent.putExtra("meal", LunchCache[1]);
                    LunchIntent.putExtra("origin", LunchCache[2]);
                    LunchIntent.putExtra("nutrients", LunchCache[3]);
                }
                startActivity(LunchIntent);
            }
        });

        DinnerCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass some(?) data to DetailsActivity
                Intent DinnerIntent = new Intent(mContext, DetailsActivity.class);
                // Basic Info
                DinnerIntent.putExtra("mealtype", 3);
                DinnerIntent.putExtra(PARAMS[0], year + "." + month + "." + day);
                if (!LoadFromCache) {
                    // Meal
                    DinnerIntent.putExtra(PARAMS[1], DinnerMduObj.Meal[dayofweek]);
                    // Origin of Ingredients
                    DinnerIntent.putExtra("origin", MealDataProcessor.processOriginData(DinnerMduObj, dayofweek, mContext));
                    // Nutrients
                    DinnerIntent.putExtra("nutrients", MealDataProcessor.processNutrientsData(DinnerMduObj, dayofweek, mContext));
                }else{
                    DinnerIntent.putExtra("meal", DinnerCache[1]);
                    DinnerIntent.putExtra("origin", DinnerCache[2]);
                    DinnerIntent.putExtra("nutrients", DinnerCache[3]);
                }
                startActivity(DinnerIntent);
            }
        });

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
        // Set date
        menu.findItem(R.id.action_date).setTitle(year + "." + month + "." + day);
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
        }else if(id==R.id.action_about){
            startActivity(new Intent(mContext, About.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // start load meal data
    void getMealData(){
        Snackbar.make(Root,getResources().getString(R.string.loading),Snackbar.LENGTH_SHORT).show();
        LunchAvr.setText("0.0");
        DinnerAvr.setText("0.0");
        SRL.setRefreshing(true);
        Log.d(TAG, "Loading Data...");
        LunchCV.setVisibility(View.INVISIBLE);
        DinnerCV.setVisibility(View.INVISIBLE);
        getLunch();
    }

    // load lunch data
    void getLunch(){
        Log.d(TAG, "Loading Lunch Data");
        final MealDataUtil LunchObj = new MealDataUtil(ProvienceCode, SchooolCode,
                SchoolTypeA, SchoolTypeB, lunch, year+"."+month+"."+day);
        AsyncHttpClient LunchClient = new AsyncHttpClient();
        LunchClient.get(LunchObj.getURL(), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String RawData = null;
                try {
                    RawData = new String(responseBody, "UTF-8");
                    Log.d("Response", RawData);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, RawData);
                LunchObj.parseData(RawData);
                Log.d(TAG, LunchObj.Meal[dayofweek]);
                if (LunchObj.Meal[dayofweek].length() <= 1) {
                    LunchObj.Meal[dayofweek] = getResources().getString(R.string.error_meal_nodata);
                }
                LunchTxt.setText(LunchObj.Meal[dayofweek].replaceAll(" ","\n"));
                LunchMduObj = LunchObj;
                Log.d(TAG, "Lunch Data Loaded");
                // Cache Data
                Cache.updateCache(year + "." + month + "." + day + "_2", LunchObj.Meal[dayofweek],
                        MealDataProcessor.processOriginData(LunchObj, dayofweek, mContext),
                        MealDataProcessor.processNutrientsData(LunchObj, dayofweek, mContext));
                LoadFromCache = false;
                getDinner();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Failed Loading Lunch Data");
                Log.e(TAG, error.toString());
                // Load From Cache
                Snackbar.make(Root, getResources().getString(R.string.from_cache), Snackbar.LENGTH_SHORT).show();
                LunchCache = Cache.getFromCache(year + "." + month + "." + day + "_2");
                LunchTxt.setText(LunchCache[1].replaceAll(" ","\n"));
                LoadFromCache = true;
                getDinner();
            }
        });
    }

    //load dinner data
    void getDinner(){
        Log.d(TAG, "Loading Dinner Data");
        final MealDataUtil DinnerObj = new MealDataUtil(ProvienceCode, SchooolCode,
                SchoolTypeA, SchoolTypeB, dinner, year+"."+month+"."+day);
        AsyncHttpClient DinnerClient = new AsyncHttpClient();
        DinnerClient.get(DinnerObj.getURL(), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String RawData = null;
                try {
                    RawData = new String(responseBody, "UTF-8");
                    Log.d("Response", RawData);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, RawData);
                DinnerObj.parseData(RawData);
                Log.d(TAG, DinnerObj.Meal[dayofweek]);
                if (DinnerObj.Meal[dayofweek].length() <= 1) {
                    DinnerObj.Meal[dayofweek] = getResources().getString(R.string.error_meal_nodata);
                }
                DinnerTxt.setText(DinnerObj.Meal[dayofweek].replaceAll(" ","\n"));
                // Cache Data
                Cache.updateCache(year + "." + month + "." + day + "_3", DinnerObj.Meal[dayofweek],
                        MealDataProcessor.processOriginData(DinnerObj, dayofweek, mContext),
                        MealDataProcessor.processNutrientsData(DinnerObj, dayofweek, mContext));
                LunchCV.setVisibility(View.VISIBLE);
                DinnerCV.setVisibility(View.VISIBLE);
                DinnerMduObj = DinnerObj;
                Log.d(TAG, "Dinner Data Loaded");
                SRL.setRefreshing(false);
                setAverage();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Failed Loading Dinner Data");
                Log.e(TAG, error.toString());
                // Load From Cache
                Snackbar.make(Root, getResources().getString(R.string.error_net_cache), Snackbar.LENGTH_SHORT).show();
                DinnerCache = Cache.getFromCache(year + "." + month + "." + day + "_3");
                DinnerTxt.setText(DinnerCache[1].replaceAll(" ","\n"));
                LunchCV.setVisibility(View.VISIBLE);
                DinnerCV.setVisibility(View.VISIBLE);
                SRL.setRefreshing(false);
                setAverage();
            }
        });
    }

    // pick date from datepickerdialog
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

    void setAverage(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.whereEqualTo("date", year+"."+month+"."+day+"_2");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d(TAG, "The getFirst request failed.");

                } else {
                    Log.d(TAG, "GOT DATA");
                    // Caculate Average Rate
                    float Sum = 0;
                    for (int i = 0; i < object.getList("rates").size(); i++) {
                        try{
                        Sum += (double) object.getList("rates").get(i);
                        }catch (Exception E){
                            Sum += (int) object.getList("rates").get(i);
                        }
                    }
                    float Average = Sum / object.getList("rates").size();
                    LunchAvr.setText(String.valueOf(Average));
                }
            }
        });

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Reviews");
        query1.whereEqualTo("date", year+"."+month+"."+day+"_3");
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d(TAG, "The getFirst request failed.");

                } else {
                    Log.d(TAG, "GOT DATA");
                    // Caculate Average Rate
                    float Sum = 0;
                    for (int i = 0; i < object.getList("rates").size(); i++) {
                        Sum += (double) object.getList("rates").get(i);
                    }
                    float Average = Sum / object.getList("rates").size();
                    DinnerAvr.setText(String.valueOf(Average));
                }
            }
        });


    }

    void shareMealData(){
        String MEALSTRING = "";
        MEALSTRING += getResources().getString(R.string.app_name);
        MEALSTRING += " - " + year+"."+month+"."+day + "\n\n";
        MEALSTRING += "\n" + getResources().getString(R.string.lunch);
        MEALSTRING += "(" + getResources().getString(R.string.average) + " : " + LunchAvr.getText().toString() + ")";
        MEALSTRING += "\n" + LunchTxt.getText().toString() + "\n\n";
        MEALSTRING += "\n" + getResources().getString(R.string.dinner);
        MEALSTRING += "(" + getResources().getString(R.string.average) + " : " + DinnerAvr.getText().toString() + ")";
        MEALSTRING += "\n" + DinnerTxt.getText().toString();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, MEALSTRING);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


}