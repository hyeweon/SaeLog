package com.we.saelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.we.saelog.room.CategoryDAO;
import com.we.saelog.room.CategoryDB;
import com.we.saelog.room.MyCategory;

import java.util.ArrayList;

public class NewCategoryActivity extends AppCompatActivity {

    CategoryDB db;

    int contentNum;
    Button btnContentAdd;

    private EditText mTitle;

    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DB 호출
        db = CategoryDB.getAppDatabase(this);

        contentNum = 1;
        setContentView(R.layout.activity_new_category);


        viewPager = findViewById(R.id.viewPager);

        ArrayList<Drawable> drawableArrayList = new ArrayList<>();
        drawableArrayList.add(getResources().getDrawable(R.drawable.icon_fullheart));
        drawableArrayList.add(getResources().getDrawable(R.drawable.icon_heart));
        drawableArrayList.add(getResources().getDrawable(R.drawable.icon_fullheart));

        viewPager.setAdapter(new NewCategoryTypeAdapter(drawableArrayList));



        Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner)findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner)findViewById(R.id.spinner1);
        Spinner spinner5 = (Spinner)findViewById(R.id.spinner1);
        Spinner spinner6 = (Spinner)findViewById(R.id.spinner1);
        Spinner spinner7 = (Spinner)findViewById(R.id.spinner1);
        Spinner spinner8 = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter contentTypeAdapter = ArrayAdapter.createFromResource(this, R.array.content_types, android.R.layout.simple_spinner_item);
        spinner1.setAdapter(contentTypeAdapter);
        spinner2.setAdapter(contentTypeAdapter);
        spinner3.setAdapter(contentTypeAdapter);

        mTitle = findViewById(R.id.title);
        Button btnSave = (Button) findViewById(R.id.btnSave);
    }

    public void mOnClick(View v){
        btnContentAdd = (Button) findViewById(R.id.btnContentAdd);
        switch (contentNum){
            case 1:
                findViewById(R.id.content2).setVisibility(View.VISIBLE);
                btnContentAdd.setText("항목 추가 (2/8)");
                contentNum++;
                break;
            case 2:
                findViewById(R.id.content3).setVisibility(View.VISIBLE);
                btnContentAdd.setText("항목 추가 (3/8)");
                contentNum++;
                break;
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