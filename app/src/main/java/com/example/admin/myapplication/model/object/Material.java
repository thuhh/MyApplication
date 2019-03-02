package com.example.admin.myapplication.model.object;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

@Entity(tableName = "TbMaterial")
public class Material {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "money")
    private long money;
    @ColumnInfo(name = "timeBuy")
    private String timeBuy;
    @ColumnInfo(name = "address")
    private String adrress;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "unit")
    private String unit;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "supName")
    private String supName;
    @ColumnInfo(name = "idUser")
    private int idUser;


    public Material(@NonNull int id, String name, String type, int amount, long money, String timeBuy, String adrress, String image, String unit, String phone, String supName, int idUser) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.money = money;
        this.timeBuy = timeBuy;
        this.adrress = adrress;
        this.image = image;
        this.unit = unit;
        this.phone = phone;
        this.supName = supName;
        this.idUser = idUser;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getTimeBuy() {
        return timeBuy;
    }

    public void setTimeBuy(String timeBuy) {
        this.timeBuy = timeBuy;
    }

    public String getAdrress() {
        return adrress;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }
}
