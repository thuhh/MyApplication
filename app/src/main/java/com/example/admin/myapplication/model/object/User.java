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
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "configPass")
    private String comfigPassword;
    @ColumnInfo(name = "pinCode")
    private String pinCode;

    public User(String username, String password, String comfigPassword, String pinCode) {
        this.username = username;
        this.password = password;
        this.comfigPassword = comfigPassword;
        this.pinCode = pinCode;
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

    public String getComfigPassword() {
        return comfigPassword;
    }

    public void setComfigPassword(String comfigPassword) {
        this.comfigPassword = comfigPassword;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
