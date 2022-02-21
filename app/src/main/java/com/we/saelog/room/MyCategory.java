package com.we.saelog.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

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
    private String thumbnail;

    // 테마 색상
    @ColumnInfo(name="category_theme")
    private String theme;

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
    public int getContent1Type() {
        return content1Type;
    }
    public String getContent1Title() {
        return content1Title;
    }
    public int getContent2Type() {
        return content2Type;
    }
    public String getContent2Title() {
        return content2Title;
    }
    public int getContent3Type() {
        return content3Type;
    }
    public String getContent3Title() {
        return content3Title;
    }
    public int getContent4Type() {
        return content4Type;
    }
    public String getContent4Title() {
        return content4Title;
    }
    public int getContent5Type() {
        return content5Type;
    }
    public String getContent5Title() {
        return content5Title;
    }
    public int getContent6Type() {
        return content6Type;
    }
    public String getContent6Title() {
        return content6Title;
    }
    public int getContent7Type() {
        return content7Type;
    }
    public String getContent7Title() {
        return content7Title;
    }
    public int getContent8Type() {
        return content8Type;
    }
    public String getContent8Title() {
        return content8Title;
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
    public void setContent1Type(int content1Type) {
        this.content1Type = content1Type;
    }
    public void setContent1Title(String content1Title) {
        this.content1Title = content1Title;
    }
    public void setContent2Type(int content2Type) {
        this.content2Type = content2Type;
    }
    public void setContent2Title(String content2Title) {
        this.content2Title = content2Title;
    }
    public void setContent3Type(int content3Type) {
        this.content3Type = content3Type;
    }
    public void setContent3Title(String content3Title) {
        this.content3Title = content3Title;
    }
    public void setContent4Type(int content4Type) {
        this.content4Type = content4Type;
    }
    public void setContent4Title(String content4Title) {
        this.content4Title = content4Title;
    }
    public void setContent5Type(int content5Type) {
        this.content5Type = content5Type;
    }
    public void setContent5Title(String content5Title) {
        this.content5Title = content5Title;
    }
    public void setContent6Type(int content6Type) {
        this.content6Type = content6Type;
    }
    public void setContent6Title(String content6Title) {
        this.content6Title = content6Title;
    }
    public void setContent7Type(int content7Type) {
        this.content7Type = content7Type;
    }
    public void setContent7Title(String content7Title) {
        this.content7Title = content7Title;
    }
    public void setContent8Type(int content8Type) {
        this.content8Type = content8Type;
    }
    public void setContent8Title(String content8Title) {
        this.content8Title = content8Title;
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
