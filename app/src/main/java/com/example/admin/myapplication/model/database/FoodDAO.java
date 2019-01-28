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
    Flowable<Food> getFoodById(int id);

    @Query("SELECT * FROM tbfood")
    List<Food> getAllFood();

    @Insert
    void insertAll(Food food);

    @Query("DELETE FROM tbfood WHERE id=(:id)")
    void delete(String id);// xoas theo id

    @Query("UPDATE tbfood SET name=(:name) AND type = (:type) AND money = (:money) AND newFood = (:newFood) AND image = (:image) AND material = (:material) AND sale = (:sale) AND userid = (:userid) WHERE id=(:id)")
    void update(int id, String name, String type,String money, boolean newFood, String image, String material, String sale, int userid);

}
