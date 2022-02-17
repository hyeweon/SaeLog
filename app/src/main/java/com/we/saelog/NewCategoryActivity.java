package com.we.saelog;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.we.saelog.room.CategoryDAO;
import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;

import java.util.ArrayList;
import java.util.Arrays;

public class NewCategoryActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    CategoryDB db;
    NewCategoryContentsAdapter mRecyclerAdapter;

    public Intent intent;
    int theme;
    int contentNum;

    ArrayList<String> contentTitles = new ArrayList<String>();

    private static final int GET_IMAGE_FOR_ThumbNail = 100;

    public Toolbar mToolbar;
    public EditText mTitle;
    public CardView mCardView;
    public ImageView mThumbnail;
    public ArrayList<CheckBox> mTheme;
    public ViewPager2 mViewPager;
    public TabLayout mIndicator;

    public Button btnContentAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        // DB 호출
        db = CategoryDB.getAppDatabase(this);

        // Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // Title 텍스트 Bold 처리
        mTitle = findViewById(R.id.title);
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTitle.setTypeface(Typeface.DEFAULT_BOLD);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTitle.setTypeface(Typeface.DEFAULT_BOLD);
            }
        });

        mThumbnail = findViewById(R.id.thumbnail);
        mThumbnail.setOnClickListener(click);

        // Theme
        CheckBox mCoral = (CheckBox)findViewById(R.id.coral);
        CheckBox mIndigo = (CheckBox)findViewById(R.id.indigo);
        CheckBox mYellow = (CheckBox)findViewById(R.id.yellow);
        CheckBox mPurple = (CheckBox)findViewById(R.id.purple);
        CheckBox mGreen = (CheckBox)findViewById(R.id.green);
        CheckBox mPink = (CheckBox)findViewById(R.id.pink);
        CheckBox mBlue = (CheckBox)findViewById(R.id.blue);
        CheckBox mGray = (CheckBox)findViewById(R.id.gray);
        mCoral.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        mIndigo.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        mYellow.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        mPurple.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        mGreen.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        mPink.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        mBlue.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        mGray.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        mTheme = new ArrayList<CheckBox>(Arrays.asList(mCoral, mIndigo, mYellow, mPurple, mGreen, mPink, mBlue, mGray));
        mCardView = findViewById(R.id.cardView);

        // Category Type View Pager
        mViewPager = findViewById(R.id.viewPager);
        ArrayList<Drawable> drawableArrayList = new ArrayList<>();
        drawableArrayList.add(getDrawable(R.drawable.type1));
        drawableArrayList.add(getDrawable(R.drawable.test_image));
        drawableArrayList.add(getDrawable(R.drawable.type1));
        drawableArrayList.add(getDrawable(R.drawable.test_image));
        drawableArrayList.add(getDrawable(R.drawable.type1));
        drawableArrayList.add(getDrawable(R.drawable.test_image));

        // Category Type Adapter
        mViewPager.setAdapter(new NewCategoryTypeAdapter(drawableArrayList));

        // Category Type Indicator
        mIndicator = findViewById(R.id.indicator);
        new TabLayoutMediator(mIndicator, mViewPager, (tab, position) -> tab.view.setClickable(false)).attach();

        //
        mRecyclerAdapter = new NewCategoryContentsAdapter();
        contentTitles.add(null);
        mRecyclerAdapter.setContentsArrayList(contentTitles);
        // RecyclerView 초기화
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.contentRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);

        // Content Spinner
        contentNum = 1;

        btnContentAdd = (Button) findViewById(R.id.btnContentAdd);

        Button btnSave = (Button) findViewById(R.id.btnSave1);
    }

    public void mOnClick(View v){
        if (contentNum==8) {
            Toast.makeText(getApplicationContext(), "항목은 최대 8개까지 가능합니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            contentTitles.add(null);
            mRecyclerAdapter.setContentsArrayList(contentTitles);
            btnContentAdd.setText("항목 추가하기 (" + Integer.toString(contentNum)+"/8)");
        }
    }

    public void saveOnClick(View v){
        if(mTitle.getText().toString().trim().length() <= 0) {        // 제목이 입력되지 않은 경우
            Toast.makeText(this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else{
            // DB에 새로운 카테고리 추가를 위한 AsyncTask 호출
            new InsertAsyncTask(db.categoryDAO())
                    .execute(new MyCategory(mTitle.getText().toString(), 0, 0, 0));
            mTitle.setText(null);
        }
    }

    // Thumbnail
    final View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.thumbnail:
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, GET_IMAGE_FOR_ThumbNail);
                    break;
                default:
                    break;
            }
        }
    };

    // Theme
    public void onCheckedChanged(CompoundButton view, boolean b) {
        int color;
        if(b) {
            switch (view.getId()){
                case R.id.coral:
                    color = R.color.coral;
                    theme = 1;
                    break;
                case R.id.indigo:
                    color = R.color.indigo;
                    theme = 2;
                    break;
                case R.id.yellow:
                    color = R.color.yellow;
                    theme = 3;
                    break;
                case R.id.purple:
                    color = R.color.purple;
                    theme = 4;
                    break;
                case R.id.green:
                    color = R.color.green;
                    theme = 5;
                    break;
                case R.id.pink:
                    color = R.color.pink;
                    theme = 6;
                    break;
                case R.id.blue:
                    color = R.color.blue;
                    theme = 7;
                    break;
                case R.id.gray:
                    color = R.color.gray;
                    theme = 8;
                    break;
                default:
                    color = R.color.white;
                    theme = 0;
            }

            for (int i=0; i<mTheme.size();i++) {
                if(mTheme.get(i)!=view) mTheme.get(i).setChecked(false);
            }
            mCardView.setCardBackgroundColor(getColor(color));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    // List View
    public class ListViewAdapter extends BaseAdapter {
        ArrayList<String> items = new ArrayList<String>();

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class InsertAsyncTask extends AsyncTask<MyCategory, Void, Void> {
        private CategoryDAO categoryDAO;

        public  InsertAsyncTask(CategoryDAO categoryDAO){
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(MyCategory... myCategory) {
            categoryDAO.insert(myCategory[0]); // DB에 새로운 티켓 추가
            return null;
        }
    }
}