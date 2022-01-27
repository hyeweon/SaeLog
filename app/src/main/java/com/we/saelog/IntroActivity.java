package com.we.saelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        prefs = getSharedPreferences("Pref12", MODE_PRIVATE);
        checkFirstRun();
    }

    public void checkFirstRun() {
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            getSupportFragmentManager().beginTransaction().add(R.id.pager,new WalkthroughFragment1()).commit();
            findViewById(R.id.logo).setVisibility(View.GONE);
            findViewById(R.id.btnStart).setVisibility(View.VISIBLE);
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }
        else {
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
    }

    public void mOnClick(View v){
        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}