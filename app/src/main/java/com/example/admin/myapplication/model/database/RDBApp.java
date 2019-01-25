package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.admin.myapplication.model.object.Food;
import com.example.admin.myapplication.model.object.Material;
import com.example.admin.myapplication.model.object.Report;
import com.example.admin.myapplication.model.object.TableDinner;
import com.example.admin.myapplication.model.object.User;

@Database(entities = {User.class,Food.class,Material.class,TableDinner.class,Report.class},version = RDBApp.DB_VERSION)
public abstract class RDBApp extends RoomDatabase {
    public static final int DB_VERSION = 1;

    public abstract UserDAO userDAO();
    public abstract FoodDAO foodDAO();
    public abstract MaterialDAO materialDAO();
    public abstract TableDAO tableDAO();
    public abstract ReportDAO reportDAO();


    private static RDBApp mInstance;

    public static RDBApp getAppDatabase(Context context) {

        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), RDBApp.class, "qlnh.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return mInstance;
    }
}
