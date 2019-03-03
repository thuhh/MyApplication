package com.example.admin.myapplication.model.object;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "TbReport")
public class Report {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "tableId")
    private int idTAble;
    @ColumnInfo(name = "listFood")
    private String listFood;
    @ColumnInfo(name = "listCount")
    private String listCount;
    @ColumnInfo(name = "money")
    private String money;
    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "date")
    private String date;

    public Report(@NonNull int id, String name, int idTAble, String listFood, String listCount, String money, String time, String date) {
        this.id = id;
        this.name = name;
        this.idTAble = idTAble;
        this.listFood = listFood;
        this.listCount = listCount;
        this.money = money;
        this.time = time;
        this.date = date;
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

    public int getIdTAble() {
        return idTAble;
    }

    public void setIdTAble(int idTAble) {
        this.idTAble = idTAble;
    }

    public String getListFood() {
        return listFood;
    }

    public void setListFood(String listFood) {
        this.listFood = listFood;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getListCount() {
        return listCount;
    }

    public void setListCount(String listCount) {
        this.listCount = listCount;
    }
}
