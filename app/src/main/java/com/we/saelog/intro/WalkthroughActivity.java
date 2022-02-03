package com.we.saelog.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.we.saelog.Adapter.WalkthroughAdapter;
import com.we.saelog.LoginActivity;
import com.we.saelog.R;

import java.util.ArrayList;

public class WalkthroughActivity extends AppCompatActivity implements View.OnClickListener {

    public static Activity walkthroughActivity;

    public ViewPager2 mViewPager;
    public TabLayout mIndicator;
    public Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        // 다른 Activity에서 접근할 수 있도록 객체에 할당
        walkthroughActivity = WalkthroughActivity.this;

        // View Pager
        mViewPager = findViewById(R.id.viewPager);

        // Indicator
        mIndicator = findViewById(R.id.indicator);

        // Array List
        ArrayList<String> walkthroughArrayList = new ArrayList<>();
        walkthroughArrayList.add("새록으로 관심사에 따라\n분류하여 아카이빙하기");
        walkthroughArrayList.add("주제에 맞는 다양한 템플릿을\n선택하여 아카이빙하기");
        walkthroughArrayList.add("취향에 맞는 컬러를 선택하여\n나만의 아카이빙하기");

        // Adapter
        mViewPager.setAdapter(new WalkthroughAdapter(walkthroughArrayList));

        // Indicator를 ViewPager와 연결
        // TabLayout 클릭 방지
        new TabLayoutMediator(mIndicator, mViewPager, (tab, position) -> tab.view.setClickable(false)).attach();

        // 시작하기 버튼 setOnClickListener
        mBtnStart = (Button) findViewById(R.id.btnStart);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 시작하기 버튼 클릭 시 로그인 Activity 실행
        Intent intent;
        intent = new Intent (getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}