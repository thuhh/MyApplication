package com.example.admin.myapplication.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.view.Window
import com.bumptech.glide.Glide
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterFood
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.interfaces.IOnClick
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.database.RDBApp
import kotlinx.android.synthetic.main.dialog_list_food.*

class DialogListFood(internal var context: Context) : Dialog(context, R.style.DialogCustomTheme), View.OnClickListener, IOnClick {
    override fun iClick(check: String?) {

    }

    override fun onClick(v: View?) {
    }


    private var rdbFood: RDBApp? = null
    private var foods: List<Food>? = null
    private var adapterFood: AdapterFood? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = this.layoutInflater
        setCancelable(false)
        val view = inflater.inflate(R.layout.dialog_list_food, null)
        setContentView(view)
        try {
            rdbFood = RDBApp.getAppDatabase(context)
            foods = rdbFood!!.foodDAO().allFood
            adapterFood = AdapterFood(this, foods, context)
            val manager = GridLayoutManager(context, 2)
            rcFood!!.layoutManager = manager!!
            rcFood!!.addItemDecoration(GridSpacingItemDecoration(4, 5, true))
            rcFood.adapter = adapterFood
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        MyPreferenceHelper.setString(context, MyPreferenceHelper.DialogFood, "no")
        dismiss()
    }

}
