package com.example.admin.myapplication.view.activiti.food

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.Material
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.dialog.DialogAddFood
import kotlinx.android.synthetic.main.activity_detail_food.*

class DetailFoodActivity : AppCompatActivity(), View.OnClickListener, IClickDialog {
    override fun onclick(check: String?) {
        if (check == "edit") {
            foods = rdbFood!!.foodDAO().allFood
            initData()
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnBack) {
            finish()
        } else if (v?.id == R.id.btnEdit) {
            MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.checkEdit, true, this)
            MyPreferenceHelper.setInt(MyPreferenceHelper.clickItem, id, this)
            dialogAddFood!!.show()
        } else if (v?.id == R.id.btnDelete) {
            rdbFood!!.foodDAO().delete(id)
            finish()
        }
    }

    private var rdbFood: RDBApp? = null
    private var foods: List<Food>? = null
    private var mas: List<Material>? = null
    private var id = 0
    private var dialogAddFood: DialogAddFood? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_food)
        if (intent != null) {
            id = intent.getIntExtra("foodId", 0)
        }
        try {
            rdbFood = RDBApp.getAppDatabase(this)
            foods = rdbFood!!.foodDAO().allFood
            mas = rdbFood!!.materialDAO().allApp
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        dialogAddFood = DialogAddFood(this)
        dialogAddFood!!.setClick(this)

        initData()

        btnBack.setOnClickListener(this)
        btnEdit.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
    }

    private fun initData() {
        for (i in 0 until foods!!.size) {
            if (foods!![i].id == id) {

                txtName.text = foods!![i].name
                txtType.text = "-loại: " + foods!![i].type

                txtMaterial.text = "-Nguyên liệu chính: " + foods!![i].material

                for (p in 0 until mas!!.size){
                    Log.e("sdsdsd",mas!![p].id.toString())
                    if (mas!![p].id  == foods!![i].material.toInt()){
                        txtMaterial.text = "-Nguyên liệu chính: " + mas!![p].name
                    }
                }



                txtMoney.text = foods!![i].money + "K"
                txtSale.text = foods!![i].sale
                txtDescrip.text = foods!![i].descrip

                val image = foods!![i].image
                if (image != null && image !== "") {
                    if (image == "R.drawable.food1") {
                        Glide.with(this).asBitmap().load(R.drawable.food1).into(imgFood)
                    } else if (image == "R.drawable.food11") {
                        Glide.with(this).asBitmap().load(R.drawable.food11).into(imgFood)
                    } else if (image == "R.drawable.food2") {
                        Glide.with(this).asBitmap().load(R.drawable.food2).into(imgFood)
                    } else if (image == "R.drawable.food3") {
                        Glide.with(this).asBitmap().load(R.drawable.food3).into(imgFood)
                    } else if (image == "R.drawable.food4") {
                        Glide.with(this).asBitmap().load(R.drawable.food4).into(imgFood)
                    } else if (image == "R.drawable.food5") {
                        Glide.with(this).asBitmap().load(R.drawable.food5).into(imgFood)
                    } else if (image == "R.drawable.food6") {
                        Glide.with(this).asBitmap().load(R.drawable.food6).into(imgFood)
                    } else if (image == "R.drawable.food7") {
                        Glide.with(this).asBitmap().load(R.drawable.food7).into(imgFood)
                    } else if (image == "R.drawable.food8") {
                        Glide.with(this).asBitmap().load(R.drawable.food8).into(imgFood)
                    } else if (image == "R.drawable.food9") {
                        Glide.with(this).asBitmap().load(R.drawable.food9).into(imgFood)
                    } else if (image == "R.drawable.food10") {
                        Glide.with(this).asBitmap().load(R.drawable.food10).into(imgFood)
                    } else if (image == "R.drawable.food12") {
                        Glide.with(this).asBitmap().load(R.drawable.food12).into(imgFood)
                    } else if (image == "R.drawable.food13") {
                        Glide.with(this).asBitmap().load(R.drawable.food13).into(imgFood)
                    } else if (image == "R.drawable.food14") {
                        Glide.with(this).asBitmap().load(R.drawable.food14).into(imgFood)
                    } else {
                        Glide.with(this).asBitmap().load(image).into(imgFood)
                    }
                }
            }
        }
    }
}
