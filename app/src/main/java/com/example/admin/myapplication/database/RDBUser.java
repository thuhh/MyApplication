package com.example.admin.myapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.admin.myapplication.model.User;

@Database(entities = {User.class},version = RDBUser.DB_VERSION)
public abstract class RDBUser extends RoomDatabase {
    public static final int DB_VERSION = 1;

    public abstract UserDAO userDAO();

    private static RDBUser mInstance;

    public static RDBUser getAppDatabase(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), RDBUser.class, "qlnh.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return mInstance;
    }
}
