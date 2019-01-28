package com.example.admin.myapplication.model.object;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "TbFood")
public class Food {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "money")
    private String money;
    @ColumnInfo(name = "newFood")
    private boolean newFood;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "material")
    private String material;
    @ColumnInfo(name = "sale")
    private String sale;
    @ColumnInfo(name = "userid")
    private int userId;

    public Food(@NonNull int id, String name, String type, String money, boolean newFood, String image, String material, String sale, int userId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.money = money;
        this.newFood = newFood;
        this.image = image;
        this.material = material;
        this.sale = sale;
        this.userId = userId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isNewFood() {
        return newFood;
    }

    public void setNewFood(boolean newFood) {
        this.newFood = newFood;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
