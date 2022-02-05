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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.we.saelog.Adapter.NewCategoryTypeAdapter;
import com.we.saelog.room.CategoryDAO;
import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;

import java.util.ArrayList;
import java.util.Arrays;

public class NewCategoryActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    CategoryDB db;

    public Intent intent;
    String theme;
    int contentNum;

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

        // Content Spinner
        contentNum = 1;
        Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner)findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner)findViewById(R.id.spinner4);
        Spinner spinner5 = (Spinner)findViewById(R.id.spinner5);
        Spinner spinner6 = (Spinner)findViewById(R.id.spinner6);
        Spinner spinner7 = (Spinner)findViewById(R.id.spinner7);
        Spinner spinner8 = (Spinner)findViewById(R.id.spinner8);
        ArrayAdapter contentTypeAdapter = ArrayAdapter.createFromResource(this, R.array.content_types, R.layout.item_content_type);
        spinner1.setAdapter(contentTypeAdapter);
        spinner2.setAdapter(contentTypeAdapter);
        spinner3.setAdapter(contentTypeAdapter);
        spinner4.setAdapter(contentTypeAdapter);
        spinner5.setAdapter(contentTypeAdapter);
        spinner6.setAdapter(contentTypeAdapter);
        spinner7.setAdapter(contentTypeAdapter);
        spinner8.setAdapter(contentTypeAdapter);
        spinner1.setOnItemClickListener(this);
        btnContentAdd = (Button) findViewById(R.id.btnContentAdd);

        Button btnSave = (Button) findViewById(R.id.btnSave1);
    }

    public void mOnClick(View v){
        if (contentNum==8) {
            Toast.makeText(getApplicationContext(), "더이상 항목을 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            switch (contentNum){
                case 1:
                    findViewById(R.id.content2).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    findViewById(R.id.content3).setVisibility(View.VISIBLE);
                    break;
                case 3:
                    findViewById(R.id.content4).setVisibility(View.VISIBLE);
                    break;
                case 4:
                    findViewById(R.id.content5).setVisibility(View.VISIBLE);
                    break;
                case 5:
                    findViewById(R.id.content6).setVisibility(View.VISIBLE);
                    break;
                case 6:
                    findViewById(R.id.content7).setVisibility(View.VISIBLE);
                    break;
                case 7:
                    findViewById(R.id.content8).setVisibility(View.VISIBLE);
                    break;
            }
            contentNum++;
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
                    break;
                case R.id.indigo:
                    color = R.color.indigo;
                    break;
                case R.id.yellow:
                    color = R.color.yellow;
                    break;
                case R.id.purple:
                    color = R.color.purple;
                    break;
                case R.id.green:
                    color = R.color.green;
                    break;
                case R.id.pink:
                    color = R.color.pink;
                    break;
                case R.id.blue:
                    color = R.color.blue;
                    break;
                case R.id.gray:
                    color = R.color.gray;
                    break;
                default:
                    color = R.color.white;
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