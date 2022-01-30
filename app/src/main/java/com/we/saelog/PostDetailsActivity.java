package com.we.saelog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PostDetailsActivity extends AppCompatActivity {

    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        // Intent로 클릭한 티켓의 정보 받아오기
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        // 티켓의 정보를 보여줄 TextView를 id로 불러오기
        mTitle = findViewById(R.id.title);
        // 티켓의 정보가 보이게 설정
        mTitle.setText(title);
    }
}