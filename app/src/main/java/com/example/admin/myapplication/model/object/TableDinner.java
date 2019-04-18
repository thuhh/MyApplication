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
    @ColumnInfo(name = "memberMax")
    private int memberMax;
    @ColumnInfo(name = "member")
    private int member;
    @ColumnInfo(name = "status")
    private boolean status;
    @ColumnInfo(name = "listFood")
    private String listFood;
    @ColumnInfo(name = "listCount")
    private String listCount;
    @ColumnInfo(name = "iduser")
    private int iduser;

    public TableDinner(@NonNull int id, String name, int memberMax,int member, boolean status, String listFood, String listCount, int iduser) {
        this.id = id;
        this.name = name;
        this.memberMax = memberMax;
        this.member = member;
        this.status = status;
        this.listFood = listFood;
        this.listCount = listCount;
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

    public String getListFood() {
        return listFood;
    }

    public void setListFood(String listFood) {
        this.listFood = listFood;
    }

    public String getListCount() {
        return listCount;
    }

    public void setListCount(String listCount) {
        this.listCount = listCount;
    }

    public int getMemberMax() {
        return memberMax;
    }

    public void setMemberMax(int memberMax) {
        this.memberMax = memberMax;
    }
}
