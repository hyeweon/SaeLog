package com.we.saelog.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class MyPost implements Serializable {
    // 각 포스트의 식별을 위한 ID (PrimaryKey)
    @PrimaryKey(autoGenerate = true)
    private int postID;

    // 포스트 카테고리 ID (Foreign Key)
    @ColumnInfo(name="post_category")
    private int category;

    // 마음글 등록 여부
    @ColumnInfo(name="post_is_hearted")
    private boolean isHearted;

    // 포스트 작성 년월일
    @ColumnInfo(name="post_date")
    private String date;

    // 포스트 제목
    @ColumnInfo(name="post_title")
    private String title;

    // 대표 이미지
    @ColumnInfo(name="post_thumbnail")
    private String thumbnail;

    // 첫번째 컨텐츠
    @ColumnInfo(name="post_content1")
    public String content1;

    // 두번째 컨텐츠
    @ColumnInfo(name="post_content2")
    public String content2;

    // 세번째 컨텐츠
    @ColumnInfo(name="post_content3")
    public String content3;

    // 네번째 컨텐츠
    @ColumnInfo(name="post_content4")
    public String content4;

    // 다섯번째 컨텐츠
    @ColumnInfo(name="post_content5")
    public String content5;

    // 여섯번째 컨텐츠
    @ColumnInfo(name="post_content6")
    public String content6;

    // 일곱번째 컨텐츠
    @ColumnInfo(name="post_content7")
    public String content7;

    // 여덟번째 컨텐츠
    @ColumnInfo(name="post_content8")
    public String content8;

    // 생성자
    public MyPost(int category, String date, String title, String thumbnail) {
        this.category = category;
        this.isHearted = false;
        this.date = date;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    // 각 Attribute에 접근할 때 사용하는 함수
    public int getPostID() {
        return postID;
    }
    public int getCategory() {
        return category;
    }
    public boolean getHearted() {
        return isHearted;
    }
    public String getDate() {
        return date;
    }
    public String getTitle() {
        return title;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public void setHearted(boolean hearted) {
        this.isHearted = hearted;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent(int n){
        switch (n){
            case 1:
                return content1;
            case 2:
                return content2;
            case 3:
                return content3;
            case 4:
                return content4;
            case 5:
                return content5;
            case 6:
                return content6;
            case 7:
                return content7;
            case 8:
                return content8;
        }
        return null;
    }

    public void setContent(int n, String content){
        switch (n){
            case 1:
                content1 = content;
                break;
            case 2:
                content2 = content;
                break;
            case 3:
                content3 = content;
                break;
            case 4:
                content4 = content;
                break;
            case 5:
                content5 = content;
                break;
            case 6:
                content6 = content;
                break;
            case 7:
                content7 = content;
                break;
            case 8:
                content8 = content;
                break;
        }
    }

    public void setContentArray(int contentNum, ArrayList<String> content) {
        for (int i = 0; i < contentNum; i++){
            setContent(i + 1, content.get(i));
        }
    }
}