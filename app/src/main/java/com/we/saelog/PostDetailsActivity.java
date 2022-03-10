package com.we.saelog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.Adapter.PostDetailsRecyclerAdapter;
import com.we.saelog.Adapter.TimelineRecyclerAdapter;
import com.we.saelog.room.CategoryDAO;
import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;
import com.we.saelog.room.MyPost;
import com.we.saelog.room.PostDAO;
import com.we.saelog.room.PostDB;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PostDetailsActivity extends AppCompatActivity {

    PostDB postDB;
    CategoryDB categoryDB;
    PostDetailsRecyclerAdapter mRecyclerAdapter;

    private ImageButton mMenu;
    private ImageButton mDownBack;
    private ImageButton mBtnHeart;
    private ImageView mThumbnail;
    private TextView mTitle;

    MyPost post;
    MyCategory category;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        // Intent로 클릭한 포스트의 정보 받아오기
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        post = (MyPost) intent.getSerializableExtra("post");

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

        // DB 호출
        postDB = PostDB.getAppDatabase(this);
        categoryDB = CategoryDB.getAppDatabase(this);

        try {
            category = new CategoryAsyncTask(categoryDB.categoryDAO()).execute(post.getCategory()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mBtnHeart = findViewById(R.id.btnHeart);
        if(post.getHearted()==true){
            mBtnHeart.setImageResource(R.drawable.icon_details_heart_full);
        }
        else {
            mBtnHeart.setImageResource(R.drawable.icon_details_heart_empty);
        }
        mBtnHeart.setImageTintList(ColorStateList.valueOf(Color.parseColor(category.getTheme())));
        mBtnHeart.setOnClickListener(clickListener);

        // Thumbnail
        mThumbnail = findViewById(R.id.thumbnail);
        if(category.getType()==5) mThumbnail.setVisibility(View.GONE);
        String strThumbnail = post.getThumbnail();
        if(strThumbnail != null && strThumbnail != "") {
            Bitmap bitmapThumbnail = StringToBitmap(strThumbnail);
            mThumbnail.setImageBitmap(bitmapThumbnail);
        } else {
            mThumbnail.setVisibility(View.GONE);
        }

        // Title
        mTitle = findViewById(R.id.title);
        mTitle.setText(title);

        // Adapter 초기화
        mRecyclerAdapter = new PostDetailsRecyclerAdapter();

        mRecyclerAdapter.setPost(post);
        mRecyclerAdapter.setCategory(category);

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
                                    new PostDetailsActivity.DeleteAsyncTask(postDB.postDAO()).execute(post.getPostID());
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
                case R.id.btnHeart:
                    boolean itemIsHearted = post.getHearted();

                    if(itemIsHearted==true){
                        mBtnHeart.setImageResource(R.drawable.icon_details_heart_empty);
                        itemIsHearted = false;
                    }
                    else{
                        mBtnHeart.setImageResource(R.drawable.icon_details_heart_full);
                        itemIsHearted = true;
                    }

                    post.setHearted(itemIsHearted);
                    // DB에 update
                    new TimelineRecyclerAdapter.UpdateAsyncTask(postDB.postDAO()).execute(post);
            }
        }
    };

    // from https://stickode.com/detail.html?no=1297
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class CategoryAsyncTask extends AsyncTask<Integer, Void, MyCategory> {
        private final CategoryDAO categoryDAO;

        public  CategoryAsyncTask(CategoryDAO categoryDAO){
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected MyCategory doInBackground(Integer... ID) {
            List<MyCategory> categoryList = categoryDAO.findByID(ID[0]);
            try {
                return categoryList.get(0);
            } catch (Exception e) {
                return new MyCategory("","","",0,0);
            }
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<MyPost, Void, Void> {
        private final PostDAO postDAO;

        public  UpdateAsyncTask(PostDAO postDAO){
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(MyPost... myPost) {
            postDAO.update(myPost[0]); // DB에 새로운 포스트 추가
            return null;
        }
    }

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