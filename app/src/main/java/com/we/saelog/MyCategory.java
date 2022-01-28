package com.we.saelog;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import android.graphics.Bitmap;

@Entity
public class MyCategory {
    // 각 카테고리의 식별을 위한 ID (PrimaryKey)
    @PrimaryKey(autoGenerate = true)
    private int categoryID;

    // 카테고리명
    @ColumnInfo(name="category_title")
    private String title;

    // 대표 이미지
    @ColumnInfo(name="category_thumbnail")
    private Bitmap thumbnail;

    // 테마 색상 (0 ~ 7)
    @ColumnInfo(name="category_color")
    private int color;

    // 뷰 형식 (0 ~ 5)
    @ColumnInfo(name="category_type")
    private int type;

    // 컨텐츠 개수
    @ColumnInfo(name="category_contents_num")
    private int contentsNum;

    // 첫번째 컨텐츠명
    @ColumnInfo(name="category_content1_title")
    private String content1Title;

    // 첫번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content1_type")
    private int content1Type;

    // 두번째 컨텐츠명
    @ColumnInfo(name="category_content2_title")
    private String content2Title;

    // 두번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content2_type")
    private int content2Type;

    // 세번째 컨텐츠명
    @ColumnInfo(name="category_content3_title")
    private String content3Title;

    // 세번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content3_type")
    private int content3Type;

    // 네번째 컨텐츠명
    @ColumnInfo(name="category_content4_title")
    private String content4Title;

    // 네번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content4_type")
    private int content4Type;

    // 다섯번째 컨텐츠명
    @ColumnInfo(name="category_content5_title")
    private String content5Title;

    // 다섯번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content5_type")
    private int content5Type;

    // 여섯번째 컨텐츠명
    @ColumnInfo(name="category_content6_title")
    private String content6Title;

    // 여섯번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content6_type")
    private int content6Type;

    // 일곱번째 컨텐츠명
    @ColumnInfo(name="category_content7_title")
    private String content7Title;

    // 일곱번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content7_type")
    private int content7Type;

    // 여덟번째 컨텐츠명
    @ColumnInfo(name="category_content8_title")
    private String content8Title;

    // 여덟번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content8_type")
    private int content8Type;

    // 생성자
    public MyCategory(String title, int color, int type, int contentsNum) {
        this.title = title;
        this.color = color;
        this.type = type;
        this.contentsNum = contentsNum;
    }

    // 각 Attribute에 접근할 때 사용하는 함수
    public int getID() {
        return categoryID;
    }
    public String getContentTitle(int n){
        switch (n){
            case 1:
                return content1Title;
            case 2:
                return content2Title;
            case 3:
                return content3Title;
            case 4:
                return content4Title;
            case 5:
                return content5Title;
            case 6:
                return content6Title;
            case 7:
                return content7Title;
            case 8:
                return content8Title;
        }
        return null;
    }
    public int getContentType(int n){
        switch (n){
            case 1:
                return content1Type;
            case 2:
                return content2Type;
            case 3:
                return content3Type;
            case 4:
                return content4Type;
            case 5:
                return content5Type;
            case 6:
                return content6Type;
            case 7:
                return content7Type;
            case 8:
                return content8Type;
        }
        return 0;
    }
}
