package com.we.saelog;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity
public class MyPost {
    // 각 포스트의 식별을 위한 ID (PrimaryKey)
    @PrimaryKey(autoGenerate = true)
    private int postID;

    // 포스트 카테고리 ID (Foreign Key)
    @ColumnInfo(name="post_category")
    private int category;

    // 포스트 제목
    @ColumnInfo(name="post_title")
    private String title;

    // 첫번째 컨텐츠
    @ColumnInfo(name="post_content1")
    private String content1;

    // 두번째 컨텐츠
    @ColumnInfo(name="post_content2")
    private String content2;

    // 세번째 컨텐츠
    @ColumnInfo(name="post_content3")
    private String content3;

    // 네번째 컨텐츠
    @ColumnInfo(name="post_content4")
    private String content4;

    // 다섯번째 컨텐츠
    @ColumnInfo(name="post_content5")
    private String content5;

    // 여섯번째 컨텐츠
    @ColumnInfo(name="post_content6")
    private String content6;

    // 일곱번째 컨텐츠
    @ColumnInfo(name="post_content7")
    private String content7;

    // 여덟번째 컨텐츠
    @ColumnInfo(name="post_content8")
    private String content8;

    // 생성자
    public MyPost(int category, String title) {
        this.category = category;
        this.title = title;
    }

    // 각 Attribute에 접근할 때 사용하는 함수
    public int getID() {
        return postID;
    }
}