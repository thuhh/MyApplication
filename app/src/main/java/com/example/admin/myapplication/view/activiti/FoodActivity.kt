package com.example.admin.myapplication.view.activiti

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.User
import com.example.admin.myapplication.model.database.RDBFood
import com.example.admin.myapplication.model.database.RDBUser
import com.example.admin.myapplication.view.dialog.DialogAddFood
import kotlinx.android.synthetic.main.activity_food.*
import java.io.File
import java.util.ArrayList

class FoodActivity : AppCompatActivity(), View.OnClickListener, IClickDialog {
    override fun onclick(check: String?) {

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd){
            MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"yes")
            dialogAddFood!!.show()
//            val i = Intent(this, AlbumActivity::class.java)
//            startActivity(i)
        }
    }

    private var rdbFood : RDBFood ? =null;
    private var foods: List<Food> ? =null

    private var rdbUser : RDBUser ? =null;
    private var users: List<User> ? =null
    private var dialogAddFood : DialogAddFood ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        try {
            rdbFood = RDBFood.getAppDatabase(this)
            foods = rdbFood!!.foodDAO().allFood
            rdbUser = RDBUser.getAppDatabase(this)
            users = rdbUser!!.userDAO().allUser
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        dialogAddFood = DialogAddFood(this);
        dialogAddFood!!.setClick(this)


//        rdbFood!!.foodDAO().insertAll(Food(1,"Gà","Xào","1200$",true,))
        btnAdd.setOnClickListener(this)
        loadImage()
        if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this)!=null){
            if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this).equals("yes")){
                dialogAddFood!!.show()
            }
        }
    }

    private fun loadImage() {
        Glide.with(applicationContext)
                .asBitmap()
                .load(MyPreferenceHelper.getString(MyPreferenceHelper.SELECT_IMAGE, applicationContext))
                .into(ln_view)
    }

}
