package com.example.admin.myapplication.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.view.activiti.AlbumActivity
import kotlinx.android.synthetic.main.activity_add_food.*
import kotlinx.android.synthetic.main.activity_food.*
import kotlinx.android.synthetic.main.activity_login.*

class DialogAddFood(internal var context: Context) : Dialog(context, R.style.DialogCustomTheme), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.imgImage) {
//            iClickDialog!!.onclick("sub12free")
            var i = Intent(context, AlbumActivity::class.java)
            context.startActivity(i)
        } else if (v?.id == R.id.btnSave) {
//            iClickDialog!!.onclick("sub12btTry")
            MyPreferenceHelper.setString(context,MyPreferenceHelper.DialogFood,"yes")
            dismiss()
        }
    }


    private var iClickDialog: IClickDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = this.layoutInflater
        setCancelable(false)
        val view = inflater.inflate(R.layout.activity_add_food, null)
        setContentView(view)
        imgImage.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        radNew.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){

            }
            else{

            }
        }

        if (MyPreferenceHelper.getString(MyPreferenceHelper.SELECT_IMAGE, context)!=null){
            loadImage()
        }

    }

    fun setClick(iClickDialog: IClickDialog) {
        this.iClickDialog = iClickDialog
    }

    override fun onBackPressed() {
        super.onBackPressed()
        iClickDialog!!.onclick("sub12free")
        dismiss()
    }

    private fun loadImage() {
        Glide.with(context)
                .asBitmap()
                .load(MyPreferenceHelper.getString(MyPreferenceHelper.SELECT_IMAGE, context))
                .into(imgImage)
    }

}
