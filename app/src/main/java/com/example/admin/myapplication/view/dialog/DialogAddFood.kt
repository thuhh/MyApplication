package com.example.admin.myapplication.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import com.bumptech.glide.Glide
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.util.MessageEvent
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.iamge.AlbumActivity
import kotlinx.android.synthetic.main.dialog_add_food.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DialogAddFood(internal var context: Context) : Dialog(context, R.style.DialogCustomTheme), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.imgImage) {
            var i = Intent(context, AlbumActivity::class.java)
            context.startActivity(i)
        } else if (v?.id == R.id.btnSave) {
            rdbFood = RDBApp.getAppDatabase(context)
            foods = rdbFood!!.foodDAO().allFood
            var id = 0
            if (foods!!.isNotEmpty()) {
                id = foods!!.size
            }

            MyPreferenceHelper.setString(context,MyPreferenceHelper.DialogFood,"no")
            if (MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.checkEdit,context)){
                MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.checkEdit,false,context)
                id = MyPreferenceHelper.getInt(MyPreferenceHelper.clickItem,context)
                rdbFood!!.foodDAO().delete(id)
                rdbFood!!.foodDAO().insertAll(Food(id,edtName.text.toString().trim(),edtType.text.toString().trim(),edtMoney.text.toString().trim(),
                        true,path,edtMaterial.text.toString().trim(),edtMaterial.text.toString().trim(),5,0,edtDescribe.text.toString(),"1",MyPreferenceHelper.getInt(MyPreferenceHelper.idUser,context)))
                iClickDialog!!.onclick("edit")
            }else {
                rdbFood!!.foodDAO().insertAll(Food(id,edtName.text.toString().trim(),edtType.text.toString().trim(),edtMoney.text.toString().trim(),
                        true,path,edtMaterial.text.toString().trim(),edtMaterial.text.toString().trim(),5,0,edtDescribe.text.toString(),"1",MyPreferenceHelper.getInt(MyPreferenceHelper.idUser,context)))
                iClickDialog!!.onclick("save")
            }
            resetData()
            dismiss()
        }
    }

    private fun resetData() {
        MyPreferenceHelper.setString(context,MyPreferenceHelper.SELECT_IMAGE, "")
        edtName.setText("")
        edtType.setText("")
        edtMoney.setText("")
        edtMaterial.setText("")
        edtSale.setText("")
        edtDescribe.setText("")
    }


    private var iClickDialog: IClickDialog? = null
    private var rdbFood : RDBApp? =null
    private var foods: List<Food> ? =null
    private var path=""

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

        if (MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.checkEdit,context)){
            var id = MyPreferenceHelper.getInt(MyPreferenceHelper.clickItem,context)
            var food = foods!![id]
            for (i in 0 until foods!!.size){
                if (foods!![i].id == id)
                    food == foods!![i]
            }

            edtName.setText(food.name.toString())
            edtType.setText(food.type.toString())
            edtMoney.setText(food.money.toString())
            edtMaterial.setText(food.material.toString())
            edtSale.setText(food.sale.toString())
            edtDescribe.setText(food.descrip.toString())

            path = food.getImage()
            if (path != null && path !== "") {
                if (path == "R.drawable.food1") {
                    Glide.with(context).asBitmap().load(R.drawable.food1).into(imgImage)
                } else if (path == "R.drawable.food11") {
                    Glide.with(context).asBitmap().load(R.drawable.food11).into(imgImage)
                } else if (path == "R.drawable.food2") {
                    Glide.with(context).asBitmap().load(R.drawable.food2).into(imgImage)
                } else if (path == "R.drawable.food3") {
                    Glide.with(context).asBitmap().load(R.drawable.food3).into(imgImage)
                } else if (path == "R.drawable.food4") {
                    Glide.with(context).asBitmap().load(R.drawable.food4).into(imgImage)
                } else if (path == "R.drawable.food5") {
                    Glide.with(context).asBitmap().load(R.drawable.food5).into(imgImage)
                } else if (path == "R.drawable.food6") {
                    Glide.with(context).asBitmap().load(R.drawable.food6).into(imgImage)
                } else if (path == "R.drawable.food7") {
                    Glide.with(context).asBitmap().load(R.drawable.food7).into(imgImage)
                } else if (path == "R.drawable.food8") {
                    Glide.with(context).asBitmap().load(R.drawable.food8).into(imgImage)
                } else if (path == "R.drawable.food9") {
                    Glide.with(context).asBitmap().load(R.drawable.food9).into(imgImage)
                } else if (path == "R.drawable.food10") {
                    Glide.with(context).asBitmap().load(R.drawable.food10).into(imgImage)
                } else if (path == "R.drawable.food12") {
                    Glide.with(context).asBitmap().load(R.drawable.food12).into(imgImage)
                } else if (path == "R.drawable.food13") {
                    Glide.with(context).asBitmap().load(R.drawable.food13).into(imgImage)
                } else if (path == "R.drawable.food14") {
                    Glide.with(context).asBitmap().load(R.drawable.food14).into(imgImage)
                } else {
                    Glide.with(context).asBitmap().load(path).into(imgImage)
                }
            }
        }
    }

    fun setClick(iClickDialog: IClickDialog) {
        this.iClickDialog = iClickDialog
    }

    override fun onBackPressed() {
        super.onBackPressed()
        MyPreferenceHelper.setString(context,MyPreferenceHelper.DialogFood,"no")
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

