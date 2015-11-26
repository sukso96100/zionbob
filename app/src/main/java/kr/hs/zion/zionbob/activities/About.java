package kr.hs.zion.zionbob.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import kr.hs.zion.zionbob.BuildConfig;
import kr.hs.zion.zionbob.R;
import kr.hs.zion.zionbob.pusher.MealAlarmManager;

public class About extends AppCompatActivity {
    Context mContext = About.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView Version = (TextView)findViewById(R.id.version);
        String VersionName = BuildConfig.VERSION_NAME;
        Version.setText(VersionName);

        // Push Settings
        SharedPreferences SP = getSharedPreferences("pref", MODE_PRIVATE);
        final SharedPreferences.Editor SPEDIT = SP.edit();
        CheckBox CB = (CheckBox)findViewById(R.id.checkBox);
        CB.setChecked(SP.getBoolean("push", false));

        CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPEDIT.putBoolean("push", isChecked);
                SPEDIT.commit();
                MealAlarmManager MAM = new MealAlarmManager(mContext);
                if(isChecked){
                    MAM.registerAlarm();
                }else{
                    MAM.calcelAlarm();
                }
            }
        });
    }
}
