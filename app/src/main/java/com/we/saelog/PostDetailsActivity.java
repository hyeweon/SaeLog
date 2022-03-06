package com.we.saelog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.Adapter.PostDetailsRecyclerAdapter;
import com.we.saelog.room.MyPost;
import com.we.saelog.room.PostDAO;
import com.we.saelog.room.PostDB;

import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {

    PostDB db;
    PostDetailsRecyclerAdapter mRecyclerAdapter;

    private ImageButton mMenu;
    private ImageButton mDownBack;
    private TextView mTitle;

    MyPost post;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        // Intent로 클릭한 포스트의 정보 받아오기
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        post = (MyPost) intent.getSerializableExtra("post");

        // DB 호출
        db = PostDB.getAppDatabase(this);

        mMenu = findViewById(R.id.menu);
        mMenu.setOnClickListener(clickListener);

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
                case R.id.menu:
                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                    getMenuInflater().inflate(R.menu.post_details_items, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.btnUpdate:

                                    break;
                                case R.id.btnDelete:
                                    new PostDetailsActivity.DeleteAsyncTask(db.postDAO()).execute(post.getPostID());
                                    onBackPressed();
                                    break;
                                case R.id.btnSaveImage:

                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    break;
                case R.id.downBack:
                    onBackPressed();
                    break;
            }
        }
    };

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class DeleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private PostDAO postDAO;

        public  DeleteAsyncTask(PostDAO postDAO){
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(Integer... id) {
            List<MyPost> deleteTicket = postDAO.findByID(id[0]);   // PrimaryKey로 티켓 찾기
            postDAO.delete(deleteTicket.get(0));                      // 티켓 삭제
            return null;
        }
    }
}