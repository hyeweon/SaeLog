package com.we.saelog.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.we.saelog.MainActivity;
import com.we.saelog.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public Button mBtnPre;
    public Button mBtnNext;
    public EditText mName;
    public EditText mBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mName = (EditText) findViewById(R.id.name);
        mBio = (EditText) findViewById(R.id.bio);

        // setOnClickListener
        mBtnPre = (Button) findViewById(R.id.btnPre);
        mBtnPre.setOnClickListener(this);
        mBtnNext = (Button) findViewById(R.id.btnNext);
        mBtnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPre:
                onBackPressed();
                break;
            case R.id.btnNext:
                if(mName.getText().toString().isEmpty()){
                    // 사용자명이 공백일 경우
                    mName.setHintTextColor(ContextCompat.getColorStateList(getApplicationContext(),R.color.red));
                    mName.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.red));
                    Drawable icon = getDrawable( R.drawable.icon_error );
                    icon.setBounds( 0, 0, 70, 70 );
                    mName.setCompoundDrawables(null, null, icon, null);
                }
                else {
                    // SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("Pref", MODE_PRIVATE);
                    prefs.edit().putString("name", mName.getText().toString()).apply();
                    prefs.edit().putString("bio", mBio.getText().toString()).apply();

                    // WalkThrough Activity 종료
                    WalkthroughActivity walkthroughActivity = (WalkthroughActivity) WalkthroughActivity.walkthroughActivity;
                    walkthroughActivity.finish();
                    // MainActivity 실행
                    Intent intent;
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}