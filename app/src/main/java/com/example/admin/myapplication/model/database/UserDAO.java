package com.example.admin.myapplication.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.admin.myapplication.model.object.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM TbUser WHERE username=:id")
    User getUserById(int id);

    @Query("SELECT * FROM tbuser")
    List<User> getAllUser();

    @Insert
    void insertAll(User user);

    @Query("DELETE FROM tbuser WHERE id=(:id)")
    void delete(int id);// xoas theo id


}
