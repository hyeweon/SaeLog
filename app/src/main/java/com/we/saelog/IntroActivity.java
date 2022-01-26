package com.we.saelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends AppCompatActivity {

    public SharedPreferences prefs;
    Fragment walkthroughFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        walkthroughFragment=new WalkthroughFragment1();

        getSupportFragmentManager().beginTransaction().add(R.id.frame,walkthroughFragment).commit();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000); // 1초 후
    }

    public void checkFirstRun() {
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }
    }
}