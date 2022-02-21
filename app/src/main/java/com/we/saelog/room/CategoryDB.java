package com.we.saelog.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// 카테고리를 저장할 DB
@Database(entities = {MyCategory.class}, version = 3, exportSchema=false)
public abstract class CategoryDB extends RoomDatabase{
    private static CategoryDB INSTANCE = null;
    public abstract CategoryDAO categoryDAO();

    // DB 생성
    public static CategoryDB getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, CategoryDB.class , "category_db").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    // DB 제거
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
