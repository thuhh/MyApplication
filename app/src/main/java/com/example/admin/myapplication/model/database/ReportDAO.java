package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.object.Food;
import com.example.admin.myapplication.model.object.Report;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ReportDAO {

    @Query("SELECT * FROM tbreport WHERE id=:id")
    Flowable<Report> getReportById(int id);

    @Query("SELECT * FROM tbreport")
    List<Report> getAllReport();

    @Insert
    void insertAll(Report report);

    @Query("DELETE FROM tbreport WHERE id=(:id)")
    void delete(String id);// xoas theo id

    @Query("UPDATE tbreport SET name=(:name) AND tableId = (:idTAble) AND listFood=(:listFood) AND money = (:money) AND time = (:time) WHERE id=(:id)")
    void update(int id, String name, int idTAble, String listFood, String money, String time);

}
