package com.example.admin.myapplication.model.object;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;
@Entity(tableName = "TbTable")
public class TableDinner {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "member")
    private int member;
    @ColumnInfo(name = "status")
    private boolean status;
    @ColumnInfo(name = "iduser")
    private int iduser;


    public TableDinner(@NonNull int id, String name, int member, boolean status, int iduser) {
        this.id = id;
        this.name = name;
        this.member = member;
        this.status = status;
        this.iduser = iduser;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
}
