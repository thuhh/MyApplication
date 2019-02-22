package com.example.admin.myapplication.view.activiti.food

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.admin.myapplication.R
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.database.RDBApp
import kotlinx.android.synthetic.main.activity_detail_food.*

class DetailFoodActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id==R.id.flEdit){

        }else if (v?.id==R.id.flDelete){

        }else if (v?.id==R.id.btnBack){

        }else if (v?.id==R.id.txtBuy){

        }
    }

    private var rdbFood : RDBApp? =null;
    private var foods: List<Food> ? =null
    private var id = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_food)
        if (intent!=null){
            id = intent.getIntExtra("id",0)
        }
        try {
            rdbFood = RDBApp.getAppDatabase(this)
            foods = rdbFood!!.foodDAO().allFood
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }

        for (i in 0 until foods!!.size){
            if (foods!!.get(i).id==id){
                txtName.setText(foods!!.get(i).name)
                txtType.setText(foods!!.get(i).type)
                txtMoney.setText(foods!!.get(i).money)

                Glide.with(this)
                        .asBitmap()
                        .load(foods!!.get(i).getImage())
                        .into(imgFood)
            }
        }

        flEdit.setOnClickListener(this)
        flDelete.setOnClickListener(this)

    }
}
