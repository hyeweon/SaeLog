package com.we.saelog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class IntroActivity extends AppCompatActivity {

    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        checkFirstRun();
    }

    public void checkFirstRun() {
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        // 첫 실행일 경우 워크스루 보여주기
        if (isFirstRun) {
            getSupportFragmentManager().beginTransaction().add(R.id.frame,new WalkthroughFragment1()).commit();
            findViewById(R.id.logo).setVisibility(View.GONE);
            findViewById(R.id.btnStart).setVisibility(View.VISIBLE);
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }
        // 실행 후 1초 뒤에 Main Activity로 전환
        else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);
        }
    }

    // 시작하기 버튼을 누르면 바로 Main Activity 실행
    public void mOnClick(View v){
        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}