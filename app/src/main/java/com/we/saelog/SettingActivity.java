package com.we.saelog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity {
    public SharedPreferences prefs;
    private Intent intent;

    private static final int GET_IMAGE_FOR_BACKGROUNDIMAGE = 201;
    private static final int GET_IMAGE_FOR_PROFILEIMAGE = 202;
    private Toolbar mToolbar;
    private ImageView backgroundImage, profileImage;
    private EditText mName, mBio;

    String strProfileImage, strBackgroundImage, name, bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_arrowback);

        backgroundImage = findViewById(R.id.backgroundImage);
        backgroundImage.setOnClickListener(click);

        profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(click);

        prefs = getSharedPreferences("Pref",MODE_PRIVATE);

        // 배경 이미지
        String strBackgroundImage = prefs.getString("backgroundImage", "");
        Bitmap bitmapBackgroundImage = StringToBitmap(strBackgroundImage);
        backgroundImage.setImageBitmap(bitmapBackgroundImage);

        // 프로필 이미지
        String strProfileImage = prefs.getString("profileImage", "");
        Bitmap bitmapProfileImage = StringToBitmap(strProfileImage);
        profileImage.setImageBitmap(bitmapProfileImage);

        // 이름
        name = prefs.getString("name", "");
        mName = (EditText) findViewById(R.id.name);
        mName.setText(name);
        mName.addTextChangedListener(textWatcher);

        // 자기소개
        bio = prefs.getString("bio", "");
        mBio = (EditText) findViewById(R.id.bio);
        mBio.setText(bio);
        mBio.addTextChangedListener(textWatcher);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;

        Bitmap bitmap = null;

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            switch (requestCode) {
                case GET_IMAGE_FOR_BACKGROUNDIMAGE:
                    selectedImageUri = data.getData();
                    backgroundImage.setImageURI(selectedImageUri);
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

            switch (requestCode) {
                case GET_IMAGE_FOR_BACKGROUNDIMAGE:
                    strBackgroundImage = BitmapToString(bitmap);
                    break;
                case GET_IMAGE_FOR_PROFILEIMAGE:
                    strProfileImage = BitmapToString(bitmap);
                    break;
            }
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(mName.hasFocus()){
                name = s.toString();
            } else {
                bio = s.toString();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //Tool Bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnSave:
                if(strBackgroundImage!=null) prefs.edit().putString("backgroundImage", strBackgroundImage).apply();
                if(strProfileImage!=null) prefs.edit().putString("profileImage", strProfileImage).apply();
                if(name!=null) prefs.edit().putString("name", name).apply();
                if(bio!=null) prefs.edit().putString("bio", bio).apply();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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

    // from https://stickode.com/detail.html?no=1297
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }
}