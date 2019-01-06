package com.example.admin.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM TbUser WHERE username=:id")
    Flowable<User> getStepById(int id);

    @Query("SELECT * FROM tbuser")
    List<User> getAllApp();

    @Insert
    void insertAll(User applists);

    @Query("DELETE FROM tbuser WHERE username=(:id)")
    void delete(String id);// xoas theo id

    @Query("UPDATE tbuser SET password=(:pass) AND configPass = (:pass) AND pinCode = (:pin) WHERE username=(:id)")
    void update(String id, String pass, String pin);

}
