package com.we.saelog.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class MyCategory implements Serializable {
    // 각 카테고리의 식별을 위한 ID (PrimaryKey)
    @PrimaryKey(autoGenerate = true)
    private int categoryID;

    // 카테고리명
    @ColumnInfo(name="category_title")
    private String title;

    // 대표 이미지
    @ColumnInfo(name="category_thumbnail")
    private String thumbnail;

    // 테마 색상
    @ColumnInfo(name="category_theme")
    private String theme;

    // 뷰 형식 (1 ~ 6)
    @ColumnInfo(name="category_type")
    private int type;

    // 컨텐츠 개수
    @ColumnInfo(name="category_contents_num")
    private int contentsNum;

    // 첫번째 컨텐츠명
    @ColumnInfo(name="category_content1_title")
    public String content1Title;

    // 첫번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content1_type")
    public int content1Type;

    // 두번째 컨텐츠명
    @ColumnInfo(name="category_content2_title")
    public String content2Title;

    // 두번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content2_type")
    public int content2Type;

    // 세번째 컨텐츠명
    @ColumnInfo(name="category_content3_title")
    public String content3Title;

    // 세번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content3_type")
    public int content3Type;

    // 네번째 컨텐츠명
    @ColumnInfo(name="category_content4_title")
    public String content4Title;

    // 네번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content4_type")
    public int content4Type;

    // 다섯번째 컨텐츠명
    @ColumnInfo(name="category_content5_title")
    public String content5Title;

    // 다섯번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content5_type")
    public int content5Type;

    // 여섯번째 컨텐츠명
    @ColumnInfo(name="category_content6_title")
    public String content6Title;

    // 여섯번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content6_type")
    public int content6Type;

    // 일곱번째 컨텐츠명
    @ColumnInfo(name="category_content7_title")
    public String content7Title;

    // 일곱번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content7_type")
    public int content7Type;

    // 여덟번째 컨텐츠명
    @ColumnInfo(name="category_content8_title")
    public String content8Title;

    // 여덟번째 컨텐츠 형식 (1 ~ 5)
    @ColumnInfo(name="category_content8_type")
    public int content8Type;

    // 생성자
    public MyCategory(String title, String thumbnail, String theme, int type, int contentsNum) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.theme = theme;
        this.type = type;
        this.contentsNum = contentsNum;
    }

    // 각 Attribute에 접근할 때 사용하는 함수
    public int getCategoryID() {
        return categoryID;
    }
    public String getTitle() {
        return title;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public String getTheme() {
        return theme;
    }
    public int getType() {
        return type;
    }
    public int getContentsNum() {
        return contentsNum;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public void setType(int type) {
        this.type = type;
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
            default:
                return 0;
        }
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
            default:
                return null;
        }
    }

    public void setContentType(int n, String contentType){
        int type = 0;
        switch (contentType){
            case "단문형 텍스트":
                type = 1;
                break;
            case "장문형 텍스트":
                type = 2;
                break;
            case "체크박스":
                type = 3;
                break;
            case "별점":
                type = 4;
                break;
            case "사진":
                type = 5;
                break;
            default:
                type = 0;
                break;
        }

        switch (n){
            case 1:
                this.content1Type = type;
                break;
            case 2:
                this.content2Type = type;
                break;
            case 3:
                this.content3Type = type;
                break;
            case 4:
                this.content4Type = type;
                break;
            case 5:
                this.content5Type = type;
                break;
            case 6:
                this.content6Type = type;
                break;
            case 7:
                this.content7Type = type;
                break;
            case 8:
                this.content8Type = type;
                break;
        }
    }
    public void setContentTitle(int n, String contentTitle){
        switch (n){
            case 1:
                this.content1Title = contentTitle;
                break;
            case 2:
                this.content2Title = contentTitle;
                break;
            case 3:
                this.content3Title = contentTitle;
                break;
            case 4:
                this.content4Title = contentTitle;
                break;
            case 5:
                this.content5Title = contentTitle;
                break;
            case 6:
                this.content6Title = contentTitle;
                break;
            case 7:
                this.content7Title = contentTitle;
                break;
            case 8:
                this.content8Title = contentTitle;
                break;
        }
    }

    public void setContentArray(ArrayList<String> contentTypeArray, ArrayList<String> contentTitleArray){
        for (int i = 0; i < contentsNum; i++){
            setContentType(i + 1, contentTypeArray.get(i));
            setContentTitle(i + 1, contentTitleArray.get(i));
        }
        for (int i = contentsNum; i < 8; i++){
            setContentType(i + 1, "none");
            setContentTitle(i + 1, null);
        }
    }
}
