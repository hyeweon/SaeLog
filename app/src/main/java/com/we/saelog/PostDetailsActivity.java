package com.we.saelog;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.Adapter.PostDetailsRecyclerAdapter;
import com.we.saelog.room.MyPost;

public class PostDetailsActivity extends AppCompatActivity {

    PostDetailsRecyclerAdapter mRecyclerAdapter;

    private ImageButton mDownBack;
    private TextView mTitle;

    MyPost post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        // Intent로 클릭한 포스트의 정보 받아오기
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        post = (MyPost) intent.getSerializableExtra("post");

        mDownBack = (ImageButton) findViewById(R.id.downBack);
        mDownBack.setOnClickListener(clickListener);
        mDownBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onBackPressed();
                }
                return false;
            }
        });

        // Title
        mTitle = findViewById(R.id.title);
        mTitle.setText(title);

        // Adapter 초기화
        mRecyclerAdapter = new PostDetailsRecyclerAdapter();

        mRecyclerAdapter.setCategoryDB(this);
        mRecyclerAdapter.setPost(post);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    final View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.downBack:
                    onBackPressed();
                    break;
            }
        }
    };
}