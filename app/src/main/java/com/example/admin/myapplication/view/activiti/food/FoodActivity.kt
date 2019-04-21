package com.example.admin.myapplication.view.activiti.food

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterFood
import com.example.admin.myapplication.controller.adapter.MyAdapter
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.controller.util.Utils
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.LoginActivity
import com.example.admin.myapplication.view.dialog.DialogAddFood
import kotlinx.android.synthetic.main.activity_food.*
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class FoodActivity : AppCompatActivity(), View.OnClickListener, IClickDialog, ItemTableClick {
    override fun iClick(check: String?, id: Int) {
        if (check=="click") {
            startActivity(Intent(this, DetailFoodActivity::class.java).putExtra("foodId", id))
        }else if (check=="edit"){
            MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.checkEdit,true,this)
            MyPreferenceHelper.setInt(MyPreferenceHelper.clickItem,id,this)
            dialogAddFood!!.show()
        }else if (check=="delete"){
            rdbFood!!.foodDAO().delete(id)
            foods = rdbFood!!.foodDAO().allFood
            initList()
        }else if (check=="like"){
            foods = rdbFood!!.foodDAO().allFood
            initList()
        }
    }

    override fun onclick(check: String?) {
            foods = rdbFood!!.foodDAO().allFood
            initList()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd){
            MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"yes")
            dialogAddFood!!.show()
        }
        else if (v?.id == R.id.btnKhac){
            startActivity(Intent(this@FoodActivity, LoginActivity::class.java).putExtra("menu",1))
            finish()
        }else if (v?.id == R.id.btnSearch){
            lnSearch.visibility = View.VISIBLE
            search()
        }
    }

    private fun search(){
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterFood!!.filter.filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    private var rdbFood : RDBApp? =null
    private var foods: List<Food> ? =null
    private var adapterFood: AdapterFood? = null
    private var adapterFood2: AdapterFood? = null
    private var dialogAddFood : DialogAddFood ?= null

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        dialogAddFood = DialogAddFood(this)
        dialogAddFood!!.setClick(this)
        try {
            rdbFood = RDBApp.getAppDatabase(this)
            foods = rdbFood!!.foodDAO().allFood
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        initListener()
        initList()
    }

    private fun initList() {
        var food1: MutableList<Food> = mutableListOf<Food>()
        var food2: MutableList<Food> = mutableListOf<Food>()
        Collections.reverse(foods)
        for (i in 0 until foods!!.size) {
            Log.e("sdsdsdsdsd",foods!![i].type)
            if (foods!![i].type=="1"){
                food1.add(foods!![i])
            }else{
                food2.add(foods!![i])
            }
        }
        adapterFood2 = AdapterFood(this@FoodActivity, food1, this)
        adapterFood = AdapterFood(this@FoodActivity, food2,this)
        val manager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val manager2 = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvFood!!.layoutManager = manager
        rvMany!!.layoutManager = manager2

        rvFood.adapter = adapterFood
        rvMany.adapter = adapterFood2
    }

    private fun initListener() {
        btnAdd.setOnClickListener(this)
        btnKhac.setOnClickListener(this)
        btnSearch.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"no")
    }

}
