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
import com.we.saelog.Adapter.OnIconClickListener;
import com.we.saelog.room.CategoryDAO;
import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;

import java.util.ArrayList;
import java.util.Arrays;

public class NewCategoryActivity extends AppCompatActivity implements OnIconClickListener {

    private CategoryDB db;
    private NewCategoryContentsAdapter mRecyclerAdapter;

    private String title;
    private int theme;
    private int contentNum;
    private ArrayList<String> contentTitles;

    private static final int GET_IMAGE_FOR_ThumbNail = 100;

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
        drawableArrayList.add(getDrawable(R.drawable.test_image));
        drawableArrayList.add(getDrawable(R.drawable.type1));
        drawableArrayList.add(getDrawable(R.drawable.test_image));
        drawableArrayList.add(getDrawable(R.drawable.type1));
        drawableArrayList.add(getDrawable(R.drawable.test_image));

        // View Pager Adapter
        mViewPager.setAdapter(new NewCategoryTypeAdapter(drawableArrayList));

        // Indicator
        mIndicator = findViewById(R.id.indicator);
        new TabLayoutMediator(mIndicator, mViewPager, (tab, position) -> tab.view.setClickable(false)).attach();

        // Content
        // Recycler View Adapter
        mRecyclerAdapter = new NewCategoryContentsAdapter();
        mRecyclerAdapter.addItem();

        // Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);


        // 항목 추가하기 버튼
        contentNum = mRecyclerAdapter.getItemCount();
        mBtnContentAdd = (Button) findViewById(R.id.btnContentAdd);
        mBtnContentAdd.setOnClickListener(clickListener);

        Button btnSave = (Button) findViewById(R.id.btnSave1);
    }

    public void saveOnClick(View v){
        if(mTitle.getText().toString().trim().length() <= 0) {      // 제목이 입력되지 않은 경우
            Toast.makeText(this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(theme == 0){                                        // 테마가 선택되지 않은 경우
            Toast.makeText(this, "카테고리 테마를 선택해주세요.", Toast.LENGTH_SHORT).show();
        }else{
            title = mTitle.getText().toString();
            contentTitles = mRecyclerAdapter.contentTitles;
            contentNum = mRecyclerAdapter.getItemCount();

            MyCategory newCategory = new MyCategory(title, theme, 0, contentNum);

            // DB에 새로운 카테고리 추가를 위한 AsyncTask 호출
            new InsertAsyncTask(db.categoryDAO()).execute(newCategory);

            onBackPressed();
        }
    }

    final View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.thumbnail:        // Thumbnail
                    Intent intent;
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, GET_IMAGE_FOR_ThumbNail);
                    break;
                case R.id.btnContentAdd:    // 항목 추가하기
                    if (contentNum==8) {
                        Toast.makeText(getApplicationContext(), "항목은 최대 8개까지 가능합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        contentNum++;
                        mRecyclerAdapter.addItem();
                        mBtnContentAdd.setText("항목 추가하기 (" + Integer.toString(contentNum)+"/8)");
                    }
                    break;
            }
        }
    };

    // Theme
    final CompoundButton.OnCheckedChangeListener themeListener = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int color;

            if(b) {
                switch (compoundButton.getId()){
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
                    if(mTheme.get(i)!=compoundButton) mTheme.get(i).setChecked(false);
                }

                mCardView.setCardBackgroundColor(getColor(color));
            }
        }
    };

    @Override
    public void onIconClick(){

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

    private class ViewHolder {
    }
}