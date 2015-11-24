package kr.hs.zion.zionbob.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import kr.hs.zion.zionbob.BuildConfig;
import kr.hs.zion.zionbob.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView Version = (TextView)findViewById(R.id.version);
        String VersionName = BuildConfig.VERSION_NAME;
        Version.setText(VersionName);
    }
}
