package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.object.Material;

import java.util.List;

@Dao
public interface MaterialDAO {

    @Query("SELECT * FROM TbMaterial WHERE id=:id")
    Material getmaterialById(int id);

    @Query("SELECT * FROM tbmaterial")
    List<Material> getAllApp();

    @Insert
    void insertAll(Material material);

    @Query("DELETE FROM tbmaterial WHERE id=(:id)")
    void delete(String id);// xoas theo id

    @Query("DELETE FROM tbmaterial")
    public void nukeTable();
}
