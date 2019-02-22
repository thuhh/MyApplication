package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.object.Material;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MaterialDAO {

    @Query("SELECT * FROM TbMaterial WHERE id=:id")
    Flowable<Material> getmaterialById(int id);

    @Query("SELECT * FROM tbmaterial")
    List<Material> getAllApp();

    @Insert
    void insertAll(Material material);

    @Query("DELETE FROM tbmaterial WHERE id=(:id)")
    void delete(String id);// xoas theo id

    @Query("UPDATE tbmaterial SET name=(:name) AND type = (:type) AND amount = (:amount) AND money=(:money) AND timeBuy = (:timeBuy) AND address = (:address) AND image = (:image) WHERE id=(:id)")
    void update(String id, String name, String type, int amount, long money, String timeBuy, String address, String image);


}
