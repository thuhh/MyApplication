package com.example.admin.myapplication.view.activiti

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterFood
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.interfaces.IOnClick
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.database.RDBUser
import com.example.admin.myapplication.view.dialog.DialogAddFood
import kotlinx.android.synthetic.main.activity_food.*
import java.io.File
import java.util.ArrayList

class FoodActivity : AppCompatActivity(), View.OnClickListener, IClickDialog, IOnClick {
    override fun iClick(check: String?) {

    }

    override fun onclick(check: String?) {
        if (check=="save"){

            foods = rdbFood!!.foodDAO().allFood
            adapterFood!!.notifyDataSetChanged()
            rvFood.adapter = adapterFood
            MyPreferenceHelper.setString(this, MyPreferenceHelper.SELECT_IMAGE,"")
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd){
            MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"yes")
            dialogAddFood!!.show()
//            val i = Intent(this, AlbumActivity::class.java)
//            startActivity(i)
        }
    }

    private var rdbFood : RDBUser ? =null;
    private var foods: List<Food> ? =null
    private var adapterFood: AdapterFood? = null
    private var dialogAddFood : DialogAddFood ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        try {
            rdbFood = RDBUser.getAppDatabase(this)
            foods = rdbFood!!.foodDAO().allFood
            Log.e("Sdsdsd",foods!!.size.toString())
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        dialogAddFood = DialogAddFood(this);
        dialogAddFood!!.setClick(this)

        btnAdd.setOnClickListener(this)
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

}
