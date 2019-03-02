package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.object.Food;
import com.example.admin.myapplication.model.object.Material;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FoodDAO {

    @Query("SELECT * FROM TbFood WHERE id=:id")
    Food getFoodById(int id);

    @Query("SELECT * FROM tbfood")
    List<Food> getAllFood();

    @Insert
    void insertAll(Food food);

    @Query("DELETE FROM tbfood WHERE id=(:id)")
    void delete(int id);// xoas theo id

}
