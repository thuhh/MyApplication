package com.example.admin.myapplication.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import com.bumptech.glide.Glide
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.AlbumActivity
import kotlinx.android.synthetic.main.dialog_add_food.*

class DialogAddFood(internal var context: Context) : Dialog(context, R.style.DialogCustomTheme), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.imgImage) {
//            iClickDialog!!.onclick("sub12free")
            var i = Intent(context, AlbumActivity::class.java)
            context.startActivity(i)
        } else if (v?.id == R.id.btnSave) {
            rdbFood = RDBApp.getAppDatabase(context)
            foods = rdbFood!!.foodDAO().allFood
            var id = 0
            if (foods!!.isNotEmpty()) {
                id = foods!!.size
            }
            rdbFood!!.foodDAO().insertAll(Food(id,edtName.text.toString().trim(),edtType.text.toString().trim(),edtMoney.text.toString().trim(),
                    radNew.isChecked,MyPreferenceHelper.getString(MyPreferenceHelper.SELECT_IMAGE, context),edtMaterial.text.toString().trim(),edtMaterial.text.toString().trim(),MyPreferenceHelper.getInt(MyPreferenceHelper.idUser,context)))
            MyPreferenceHelper.setString(context,MyPreferenceHelper.DialogFood,"no")
            iClickDialog!!.onclick("save")
            dismiss()
        }
    }


    private var iClickDialog: IClickDialog? = null
    private var rdbFood : RDBApp? =null
    private var foods: List<Food> ? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = this.layoutInflater
        setCancelable(false)
        val view = inflater.inflate(R.layout.dialog_add_food, null)
        setContentView(view)
        try {
            rdbFood = RDBApp.getAppDatabase(context)
            foods = rdbFood!!.foodDAO().allFood
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        imgImage.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        radNew.setOnClickListener(this)

        if (MyPreferenceHelper.getString(MyPreferenceHelper.SELECT_IMAGE, context)!=null){
            loadImage()
        }


    }

    fun setClick(iClickDialog: IClickDialog) {
        this.iClickDialog = iClickDialog
    }

    override fun onBackPressed() {
        super.onBackPressed()
        MyPreferenceHelper.setString(context,MyPreferenceHelper.DialogFood,"no")
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
