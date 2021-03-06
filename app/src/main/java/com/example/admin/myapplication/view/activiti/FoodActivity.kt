package com.example.admin.myapplication.view.activiti

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterFood
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.interfaces.IOnClick
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.dialog.DialogAddFood
import kotlinx.android.synthetic.main.activity_food.*

class FoodActivity : AppCompatActivity(), View.OnClickListener, IClickDialog, IOnClick {
    override fun iClick(check: String?) {

    }

    override fun onclick(check: String?) {
        if (check=="save"){
            foods = rdbFood!!.foodDAO().allFood
            adapterFood = AdapterFood(this@FoodActivity, foods,this)
            val manager = GridLayoutManager(this, 2)
            rvFood!!.layoutManager = manager!!
            rvFood!!.addItemDecoration(GridSpacingItemDecoration(4, 5, true))
            rvFood.adapter = adapterFood
            MyPreferenceHelper.setString(this, MyPreferenceHelper.SELECT_IMAGE,"")
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd){
            MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"yes")
            dialogAddFood!!.show()
        }
        else if (v?.id == R.id.btnSearch){
            edtSearch.visibility = View.VISIBLE
            search()
        }else if (v?.id == R.id.btnKhac){
            startActivity(Intent(this@FoodActivity,LoginActivity::class.java).putExtra("menu",1))
            finish()
        }
    }

    private fun search(){
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterFood!!.getFilter().filter(charSequence)

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    private var rdbFood : RDBApp? =null;
    private var foods: List<Food> ? =null
    private var adapterFood: AdapterFood? = null
    private var dialogAddFood : DialogAddFood ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        try {
            rdbFood = RDBApp.getAppDatabase(this)
            foods = rdbFood!!.foodDAO().allFood
            Log.e("Sdsdsd",foods!!.size.toString())
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        dialogAddFood = DialogAddFood(this);
        dialogAddFood!!.setClick(this)

        initListener()

        if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this)!=null){
            if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this).equals("yes")){
                dialogAddFood!!.show()
            }
        }
        adapterFood = AdapterFood(this@FoodActivity, foods,this)
        val manager = GridLayoutManager(this, 2)
        rvFood!!.layoutManager = manager!!
        rvFood!!.addItemDecoration(GridSpacingItemDecoration(4, 5, true))
        rvFood.adapter = adapterFood

    }

    private fun initListener() {
        btnAdd.setOnClickListener(this)
        btnSearch.setOnClickListener(this)
        btnKhac.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"no")
    }
}
