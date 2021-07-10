package com.example.navernews.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NewsDTO.class},version = 1)
public abstract class AppNewsDatabase extends RoomDatabase {

    private static AppNewsDatabase INSTANCE;

    public abstract NewsDAO newsDAO();

    public static AppNewsDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppNewsDatabase.class,"news.db")
                    .fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
