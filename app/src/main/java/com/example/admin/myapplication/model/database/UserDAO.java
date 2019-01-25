package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.object.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM TbUser WHERE username=:id")
    Flowable<User> getUserById(int id);

    @Query("SELECT * FROM tbuser")
    List<User> getAllUser();

    @Insert
    void insertAll(User applists);

    @Query("DELETE FROM tbuser WHERE username=(:id)")
    void delete(String id);// xoas theo id

    @Query("UPDATE tbuser SET username = (:name) AND password=(:pass) AND configPass = (:pass) AND pinCode = (:pin) WHERE username=(:id)")
    void update(int id, String name, String pass, String pin);

}
