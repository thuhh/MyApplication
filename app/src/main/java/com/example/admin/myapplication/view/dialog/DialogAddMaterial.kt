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
import com.example.admin.myapplication.controller.util.MessageEvent
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.Material
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.iamge.AlbumActivity
import kotlinx.android.synthetic.main.dialog_add_material.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class DialogAddMaterial(internal var context: Context) : Dialog(context, R.style.DialogCustomTheme), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.imgImage) {
//            iClickDialog!!.onclick("sub12free")
            var i = Intent(context, AlbumActivity::class.java)
            context.startActivity(i)
        } else if (v?.id == R.id.btnSave) {
            rdbFood = RDBApp.getAppDatabase(context)
            materials = rdbFood!!.materialDAO().allApp
            Log.e("sdsdsdAll",materials!!.size.toString())
            var id = 0
            if (materials!!.isNotEmpty()) {
                id = materials!!.size
            }

            val calendar = Calendar.getInstance()
            rdbFood!!.materialDAO().insertAll(Material(id,edtName.text.toString().trim(),edtType.text.toString().trim(),edtSL.text.toString().toInt(),edtMoney.text.toString().trim().toLong(),calendar.get(Calendar.DATE).toString(),edtAddress.text.toString().trim(),
                    path,edtUnit.text.toString(),edtPhone.text.toString().trim(),edtSupName.text.toString().trim(),MyPreferenceHelper.getInt(MyPreferenceHelper.idUser, context)))
            MyPreferenceHelper.setString(context,MyPreferenceHelper.DialogFood,"no")
            iClickDialog!!.onclick("save")
            dismiss()
        }
    }


    private var iClickDialog: IClickDialog? = null
    private var rdbFood : RDBApp? =null
    private var materials: List<Material> ? =null
    private var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = this.layoutInflater
        setCancelable(false)
        val view = inflater.inflate(R.layout.dialog_add_material, null)
        setContentView(view)
        try {
            rdbFood = RDBApp.getAppDatabase(context)
            materials = rdbFood!!.materialDAO().allApp
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        imgImage.setOnClickListener(this)
        btnSave.setOnClickListener(this)

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

    private fun loadImage(path: String) {
        if(path!=null && path.length>5) {
            Glide.with(context)
                    .asBitmap()
                    .load(path)
                    .into(imgImage)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.mMessage == "clickImage"){
            path = event.image
            loadImage(event.image)
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}
