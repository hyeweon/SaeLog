package com.we.saelog;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.lifecycle.LiveData;

import java.util.List;

// 카테고리 저장 DB를 위한 Data Access Object
@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM MyCategory")
    LiveData<List<MyCategory>> getAll();         // LiveData를 사용하여 변화가 있을 때마다 observe

    @Query(("SELECT * FROM MyCategory where categoryID=:ID"))
    List<MyCategory> findByID(int ID);

    @Insert
    void insert(MyCategory myCategory);           // DB에 카테고리 추가

    @Update
    void update(MyCategory myCategory);           // 카테고리 수정

    @Delete
    void delete(MyCategory myCategory);           // DB에서 카테고리 삭제
}