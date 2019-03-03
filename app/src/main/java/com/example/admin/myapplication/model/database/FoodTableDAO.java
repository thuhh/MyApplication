package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.object.Food;
import com.example.admin.myapplication.model.object.FoodTable;

import java.util.List;

@Dao
public interface FoodTableDAO {

    @Query("SELECT * FROM TbFoodTable WHERE id=:id")
    FoodTable getFoodById(int id);

    @Query("SELECT * FROM TbFoodTable")
    List<FoodTable> getAllFood();

    @Insert
    void insertAll(FoodTable food);

    @Query("DELETE FROM TbFoodTable WHERE id=(:id)")
    void delete(int id);// xoas theo id

}
