package kr.hs.zion.zionbob.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kr.hs.zion.zionbob.MainActivity;
import kr.hs.zion.zionbob.R;

public class FirstRunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        Button Agree = (Button)findViewById(R.id.agree);
        Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences Pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor Edit = Pref.edit();
                Edit.putBoolean("firstrun", false);
                Edit.commit();
                startActivity(new Intent(FirstRunActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
