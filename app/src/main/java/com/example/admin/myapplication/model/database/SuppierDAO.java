package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.object.Food;
import com.example.admin.myapplication.model.object.Suppliers;

import java.util.List;

@Dao
public interface SuppierDAO {

    @Query("SELECT * FROM TbSupplier WHERE id=:id")
    Suppliers getFoodById(int id);

    @Query("SELECT * FROM TbSupplier")
    List<Suppliers> getAllFood();

    @Insert
    void insertAll(Suppliers suppliers);

    @Query("DELETE FROM TbSupplier WHERE id=(:id)")
    void delete(int id);// xoas theo id

}
