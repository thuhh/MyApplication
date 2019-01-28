package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.object.Food;
import com.example.admin.myapplication.model.object.TableDinner;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TableDAO {

    @Query("SELECT * FROM TbTable WHERE id=:id")
    Flowable<TableDinner> getTableById(int id);

    @Query("SELECT * FROM tbtable")
    List<TableDinner> getAllTable();

    @Insert
    void insertAll(TableDinner tableDinner);

    @Query("DELETE FROM tbtable WHERE id=(:id)")
    void delete(String id);// xoas theo id

    @Query("UPDATE tbtable SET name=(:name) AND member = (:member) AND status = (:status) AND iduser = (:iduser) WHERE id=(:id)")
    void update(int id, String name, String member, boolean status, int iduser);

}
