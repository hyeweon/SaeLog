package com.we.saelog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity {
    public SharedPreferences prefs;

    private static final int GET_IMAGE_FOR_BACKGROUNDIMAGE = 201;
    private static final int GET_IMAGE_FOR_PROFILEIMAGE = 202;
    private ImageView backgroundImage, profileImage;
    private Intent intent;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        backgroundImage = findViewById(R.id.backgroundImage);
        backgroundImage.setOnClickListener(click);

        profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(click);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); //ActionBar 객체 생성(액션바 커스텀마이징을 위해)

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setDisplayShowTitleEnabled(false); //기본 타이틀 제거

        prefs = getSharedPreferences("Pref",MODE_PRIVATE);
        String strProfileImage = prefs.getString("profileImage","");
    }

    final View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.backgroundImage:
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, GET_IMAGE_FOR_BACKGROUNDIMAGE);
                    break;
                case R.id.profileImage:
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, GET_IMAGE_FOR_PROFILEIMAGE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;

        Bitmap bitmap = null;
        String strImage;

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            switch (requestCode) {
                case GET_IMAGE_FOR_BACKGROUNDIMAGE:
                    selectedImageUri = data.getData();
                    break;
                case GET_IMAGE_FOR_PROFILEIMAGE:
                    selectedImageUri = data.getData();
                    profileImage.setImageURI(selectedImageUri);
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

            strImage = BitmapToString(bitmap);
            prefs.edit().putString("profileImage", strImage).apply();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnBack: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //ToolBar에 menu.xml을 인플레이트함
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_toolbar_items, menu);
        return true;
    }

    // from https://stickode.com/detail.html?no=1297
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //바이트 배열을 차례대로 읽어 들이기위한 ByteArrayOutputStream클래스 선언
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);//bitmap을 압축 (숫자 70은 70%로 압축한다는 뜻)
        byte[] bytes = baos.toByteArray();//해당 bitmap을 byte배열로 바꿔준다.
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);//Base 64 방식으로byte 배열을 String으로 변환
        return temp;//String을 retrurn
    }

}