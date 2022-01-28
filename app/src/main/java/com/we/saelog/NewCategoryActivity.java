package com.we.saelog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewCategoryActivity extends AppCompatActivity {

    int contentNum;
    Button btnContentAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentNum = 1;
        setContentView(R.layout.activity_new_category);

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

/*
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnSave:
                if(mTicketTitle.getText().toString().trim().length() <= 0) {    // 카테고리명이 입력되지 않은 경우
                    Toast.makeText(NewCategoryActivity.this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    // DB에 새로운 티켓 추가를 위한 AsyncTask 호출
                    new InsertAsyncTask(db.ticketDAO())
                            .execute(new MyTickets(mTicketTitle.getText().toString(),date,time,mTicketSeat.getText().toString(),mTicketCast.getText().toString(),mTicketReview.getText().toString()));
                    onBackPressed();                                                // 이전 화면으로
                }
                break;
        }
    }

    // Main Thread에서 DB에 접근하는 것을 피하기 위한 AsyncTask 사용
    public static class InsertAsyncTask extends AsyncTask<MyTickets, Void, Void> {
        private TicketDAO ticketDAO;

        public  InsertAsyncTask(TicketDAO ticketDAO){
            this.ticketDAO = ticketDAO;
        }

        @Override
        protected Void doInBackground(MyTickets... myTickets) {
            ticketDAO.insert(myTickets[0]); // DB에 새로운 티켓 추가
            return null;
        }
    }
 */
}