package com.we.saelog;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// 카테고리를 저장할 DB
@Database(entities = {MyPost.class}, version = 2, exportSchema=false)
public abstract class PostDB extends RoomDatabase{
    private static PostDB INSTANCE = null;
    public abstract PostDAO postDAO();

    // DB 생성
    public static PostDB getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, PostDB.class , "post_db").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    // DB 제거
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
