package com.we.saelog.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.we.saelog.MainActivity;
import com.we.saelog.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        checkFirstRun();
    }

    public void checkFirstRun() {
        // SharedPreferences
        SharedPreferences prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);

        // 실행 후 1초 뒤에 Activity 전환
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent;
                // 첫 실행일 경우 워크스루 보여주기
                if(isFirstRun){
                    intent = new Intent (getApplicationContext(), WalkthroughActivity.class);
                    prefs.edit().putBoolean("isFirstRun", false).apply();
                }
                // Main Activity로 전환
                else {
                    intent = new Intent (getApplicationContext(), MainActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}