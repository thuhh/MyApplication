package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.admin.myapplication.model.object.Food;
import com.example.admin.myapplication.model.object.Material;

@Database(entities = {Food.class},version = RDBFood.DB_VERSION)
public abstract class RDBFood extends RoomDatabase {
    public static final int DB_VERSION = 1;

    public abstract FoodDAO foodDAO();

    private static RDBFood mInstance;

    public static RDBFood getAppDatabase(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), RDBFood.class, "food.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return mInstance;
    }
}
