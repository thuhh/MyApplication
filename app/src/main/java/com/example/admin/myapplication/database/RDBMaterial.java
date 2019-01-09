package com.example.admin.myapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.admin.myapplication.model.Material;
import com.example.admin.myapplication.model.User;

@Database(entities = {Material.class},version = RDBMaterial.DB_VERSION)
public abstract class RDBMaterial extends RoomDatabase {
    public static final int DB_VERSION = 1;

    public abstract MaterialDAO materialDAO();

    private static RDBMaterial mInstance;

    public static RDBMaterial getAppDatabase(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), RDBMaterial.class, "qlnh.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return mInstance;
    }
}
