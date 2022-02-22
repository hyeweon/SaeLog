package com.we.saelog;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private Fragment timelineFragment, newPostFragment, myPageFragment;
    public Fragment selectedFragment, preFragment;
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.navigation);    // 하단 네비게이션 뷰 설정

        timelineFragment = new TimelineFragment();          // 타임라인 아이콘을 눌렀을 때 보여줄 타임라인 Fragment
        newPostFragment = new NewPostFragment();            // 새 글 아이콘을 눌렀을 때 보여줄 새 글 작성 Fragment
        myPageFragment = new MyPageFragment();              // 마이페이지 아이콘을 눌렀을 때 보여줄 마이페이지 Fragment

        // 초기화면의 Fragment 설정
        selectedFragment = timelineFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.frame, selectedFragment).commit();

        mNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_timeline:      // 타임라인 아이콘 선택 시
                        selectedFragment = timelineFragment;
                        break;
                    case R.id.action_newPost:       // 새 글 아이콘 선택 시
                        preFragment = selectedFragment;
                        selectedFragment = newPostFragment;
                        break;
                    case R.id.action_myPage:        // 마이페이지 아이콘 선택 시
                        selectedFragment = myPageFragment;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selectedFragment).commit();
                return false;
            }
        });
    }
}