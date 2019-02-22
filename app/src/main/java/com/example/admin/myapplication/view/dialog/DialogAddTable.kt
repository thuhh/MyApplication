package com.example.admin.myapplication.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.iamge.AlbumActivity
import kotlinx.android.synthetic.main.dialog_add_table.*

class DialogAddTable(internal var context: Context) : Dialog(context, R.style.DialogCustomTheme), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.imgImage) {
            var i = Intent(context, AlbumActivity::class.java)
            context.startActivity(i)
        } else if (v?.id == R.id.btnSave) {
            rdbTable = RDBApp.getAppDatabase(context)
            tables = rdbTable!!.tableDAO().allTable
            var member = edtMember.text.toString().trim().toInt()
            var id = 0
            if (tables!!.isNotEmpty()) {
                id = tables!!.size
            }
            Log.e("sdsd",id.toString()+tables?.size)
            rdbTable!!.tableDAO().insertAll(TableDinner(id,edtName.text.toString().trim(),member,radStatus.isChecked,"",MyPreferenceHelper.getInt(MyPreferenceHelper.idUser,context)))
            MyPreferenceHelper.setString(context,MyPreferenceHelper.DialogFood,"no")
            iClickDialog!!.onclick("save")
            dismiss()

        }
    }

    private var iClickDialog: IClickDialog? = null
    private var rdbTable : RDBApp? =null;
    private var tables: List<TableDinner> ? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = this.layoutInflater
        setCancelable(false)
        val view = inflater.inflate(R.layout.dialog_add_table, null)
        setContentView(view)
        try {
            rdbTable = RDBApp.getAppDatabase(context)
            tables = rdbTable!!.tableDAO().allTable
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        imgImage.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        radStatus.setOnClickListener(this)
    }

    fun setClick(iClickDialog: IClickDialog) {
        this.iClickDialog = iClickDialog
    }

    override fun onBackPressed() {
        super.onBackPressed()
        MyPreferenceHelper.setString(context,MyPreferenceHelper.DialogFood,"no")
        iClickDialog!!.onclick("sdasd")
        dismiss()
    }


}
