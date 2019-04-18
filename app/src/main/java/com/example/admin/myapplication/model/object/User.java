package com.example.admin.myapplication.model.object;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "TbUser")
public class User implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "pinCode")
    private String pinCode;
    @ColumnInfo(name = "lever")
    private int lever;

    public User(@NonNull int id, String username, String password, String email, String pinCode, int lever) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.pinCode = pinCode;
        this.lever = lever;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getLever() {
        return lever;
    }

    public void setLever(int lever) {
        this.lever = lever;
    }
}
