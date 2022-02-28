package com.we.saelog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.we.saelog.Adapter.NewCategoryContentsAdapter;
import com.we.saelog.Adapter.NewCategoryTypeAdapter;
import com.we.saelog.Adapter.OnIconClickListener;
import com.we.saelog.room.CategoryDAO;
import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class NewCategoryActivity extends AppCompatActivity implements OnIconClickListener {

    private CategoryDB db;
    private NewCategoryContentsAdapter mRecyclerAdapter;

    private String title;
    private String thumbnail;
    private String theme;
    private int type;
    private int contentNum;
    private ArrayList<String> contentTitles;
    private ArrayList<String> contentType;

    private static final int GET_IMAGE_FOR_THUMBNAIL = 100;

    public Toolbar mToolbar;
    public EditText mTitle;
    public CardView mCardView;
    public ImageView mThumbnail;
    public ArrayList<CheckBox> mTheme;
    public ViewPager2 mViewPager;
    public TabLayout mIndicator;
    public RecyclerView mRecyclerView;
    public Button mBtnContentAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        // DB 호출
        db = CategoryDB.getAppDatabase(this);

        // Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_arrowback);

        // Title
        mTitle = findViewById(R.id.title);
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTitle.setTypeface(Typeface.DEFAULT_BOLD);      // 입력 시 텍스트 Bold
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTitle.setTypeface(Typeface.DEFAULT_BOLD);
            }
        });

        // Thumbnail
        mThumbnail = findViewById(R.id.thumbnail);
        mThumbnail.setOnClickListener(clickListener);

        // Theme
        CheckBox mCoral = (CheckBox)findViewById(R.id.coral);
        CheckBox mIndigo = (CheckBox)findViewById(R.id.indigo);
        CheckBox mYellow = (CheckBox)findViewById(R.id.yellow);
        CheckBox mPurple = (CheckBox)findViewById(R.id.purple);
        CheckBox mGreen = (CheckBox)findViewById(R.id.green);
        CheckBox mPink = (CheckBox)findViewById(R.id.pink);
        CheckBox mBlue = (CheckBox)findViewById(R.id.blue);
        CheckBox mGray = (CheckBox)findViewById(R.id.gray);
        mCoral.setOnCheckedChangeListener(themeListener);
        mIndigo.setOnCheckedChangeListener(themeListener);
        mYellow.setOnCheckedChangeListener(themeListener);
        mPurple.setOnCheckedChangeListener(themeListener);
        mGreen.setOnCheckedChangeListener(themeListener);
        mPink.setOnCheckedChangeListener(themeListener);
        mBlue.setOnCheckedChangeListener(themeListener);
        mGray.setOnCheckedChangeListener(themeListener);
        mTheme = new ArrayList<CheckBox>(Arrays.asList(mCoral, mIndigo, mYellow, mPurple, mGreen, mPink, mBlue, mGray));
        mCardView = findViewById(R.id.cardView);

        // Type
        // View Pager
        mViewPager = findViewById(R.id.viewPager);
        ArrayList<Drawable> drawableArrayList = new ArrayList<>();
        drawableArrayList.add(getDrawable(R.drawable.type1));
        drawableArrayList.add(getDrawable(R.drawable.type2));
        drawableArrayList.add(getDrawable(R.drawable.type3));
        drawableArrayList.add(getDrawable(R.drawable.type4));
        drawableArrayList.add(getDrawable(R.drawable.type5));
        drawableArrayList.add(getDrawable(R.drawable.type6));

        // View Pager Adapter
        mViewPager.setAdapter(new NewCategoryTypeAdapter(drawableArrayList));

        // Indicator
        mIndicator = findViewById(R.id.indicator);
        new TabLayoutMediator(mIndicator, mViewPager, (tab, position) -> tab.view.setClickable(false)).attach();

        // Content
        // Recycler View Adapter
        mRecyclerAdapter = new NewCategoryContentsAdapter();
        mRecyclerAdapter.setOnIconClickListener(this::onIconClick);
        mRecyclerAdapter.addItem();

        // Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);

        // 항목 추가하기 버튼
        contentNum = mRecyclerAdapter.getItemCount();
        mBtnContentAdd = (Button) findViewById(R.id.btnContentAdd);
        mBtnContentAdd.setOnClickListener(clickListener);
    }

    // Tool Bar
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_category_toolbar_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.btnSave:
                if(mTitle.getText().toString().trim().length() <= 0) {      // 제목이 입력되지 않은 경우
                    Toast.makeText(getApplicationContext(), "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(theme == null){                                        // 테마가 선택되지 않은 경우
                    Toast.makeText(getApplicationContext(), "카테고리 테마를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    title = mTitle.getText().toString();
                    type = mViewPager.getCurrentItem() + 1;
                    contentNum = mRecyclerAdapter.getItemCount();
                    contentType = mRecyclerAdapter.contentType;
                    contentTitles = mRecyclerAdapter.contentTitles;

                    MyCategory newCategory = new MyCategory(title, thumbnail, theme, type, contentNum);
                    newCategory.setContentArray(contentType, contentTitles);

                    // DB에 새로운 카테고리 추가를 위한 AsyncTask 호출
                    new InsertAsyncTask(db.categoryDAO()).execute(newCategory);
                    Toast.makeText(getApplicationContext(), "새로운 카테고리가 만들어졌어요.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    final View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.thumbnail:        // Thumbnail
                    Intent intent;
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, GET_IMAGE_FOR_THUMBNAIL);
                    break;
                case R.id.btnContentAdd:    // 항목 추가하기
                    if (contentNum==8) {
                        Toast.makeText(getApplicationContext(), "항목은 최대 8개까지 가능합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        mRecyclerAdapter.addItem();
                        contentNum = mRecyclerAdapter.getItemCount();
                        mBtnContentAdd.setText("항목 추가하기 (" + Integer.toString(contentNum)+"/8)");
                    }
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImageUri = null;
        Bitmap bitmap = null;

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            switch (requestCode) {
                case GET_IMAGE_FOR_THUMBNAIL:
                    selectedImageUri = data.getData();
                    mThumbnail.setImageURI(selectedImageUri);
                    break;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                try {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(),selectedImageUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            thumbnail = BitmapToString(bitmap);
        }
    }

    // Theme
    final CompoundButton.OnCheckedChangeListener themeListener = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int color;

            if(b) {
                switch (compoundButton.getId()){
                    case R.id.coral:
                        theme = "#E16B6B";
                        break;
                    case R.id.indigo:
                        theme = "#4A6FE9";
                        break;
                    case R.id.yellow:
                        theme = "#FFB300";
                        break;
                    case R.id.purple:
                        theme = "#7D57FF";
                        break;
                    case R.id.green:
                        theme = "#68C45D";
                        break;
                    case R.id.pink:
                        theme = "#DB4FBA";
                        break;
                    case R.id.blue:
                        theme = "#1CA6D9";
                        break;
                    case R.id.gray:
                        theme = "#777777";
                        break;
                    default:
                        theme = "#ffffff";
                }

                for (int i=0; i<mTheme.size();i++) {
                    if(mTheme.get(i)!=compoundButton) mTheme.get(i).setChecked(false);
                }

                mCardView.setCardBackgroundColor(Color.parseColor(theme));
            }
        }
    };

    // 항목 추가하기 버튼
    @Override
    public void onIconClick() {
        contentNum = mRecyclerAdapter.getItemCount();
        mBtnContentAdd.setText("항목 추가하기 (" + Integer.toString(contentNum)+"/8)");
    }

    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class InsertAsyncTask extends AsyncTask<MyCategory, Void, Void> {
        private CategoryDAO categoryDAO;

        public  InsertAsyncTask(CategoryDAO categoryDAO){
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(MyCategory... myCategory) {
            categoryDAO.insert(myCategory[0]); // DB에 새로운 카테고리 추가
            return null;
        }
    }
}