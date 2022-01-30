package com.we.saelog.room;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.lifecycle.LiveData;

import java.util.List;

// 카테고리 저장 DB를 위한 Data Access Object
@Dao
public interface PostDAO {
    @Query("SELECT * FROM MyPost")
    LiveData<List<MyPost>> getAll();         // LiveData를 사용하여 변화가 있을 때마다 observe

    @Query(("SELECT * FROM MyPost where postID=:ID"))
    List<MyPost> findByID(int ID);

    @Insert
    void insert(MyPost myPost);           // DB에 카테고리 추가

    @Update
    void update(MyPost myPost);           // 카테고리 수정

    @Delete
    void delete(MyPost myPost);           // DB에서 카테고리 삭제
}
