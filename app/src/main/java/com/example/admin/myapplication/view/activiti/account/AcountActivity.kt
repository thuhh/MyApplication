package com.example.admin.myapplication.view.activiti.account

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.User
import com.example.admin.myapplication.model.database.RDBApp
import kotlinx.android.synthetic.main.activity_acount.*
import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethodManager
import android.support.v4.content.ContextCompat.getSystemService
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService






class AcountActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnBack) {
            onBackPressed()
        } else if (v?.id == R.id.btnChangePass) {
            lnReset.visibility = View.VISIBLE

            newPass = edtNewPass.text.toString().trim()
            configPass = edtConfig.text.toString().trim()

            for (i in 0 until users!!.size) {
                if (users!!.get(i).id == curentId) {
                    curentId = MyPreferenceHelper.getInt(MyPreferenceHelper.idUser, this)
                    curentPin = users!![i].pinCode
                    rdbApp!!.userDAO().delete(curentId)
                    rdbApp!!.userDAO().insertAll(User(curentId, MyPreferenceHelper.getString(MyPreferenceHelper.userName, this), newPass, configPass, curentPin))

                    MyPreferenceHelper.setString(this,MyPreferenceHelper.password,newPass)
                }
            }
        } else if (v?.id == R.id.btnReset) {
            //reset data

        } else if (v?.id == R.id.btnSave) {
            lnReset.visibility = View.GONE

            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    var rdbApp: RDBApp? = null
    var users: List<User>? = null

    var currentPass = ""
    var currentname = ""
    var newPass = ""
    var configPass = ""
    var curentId = 0
    var curentPin = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acount)
        try {
            rdbApp = RDBApp.getAppDatabase(this)
            users = rdbApp!!.userDAO().allUser
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        btnBack.setOnClickListener(this)
        btnChangePass.setOnClickListener(this)
        btnReset.setOnClickListener(this)
        btnSave.setOnClickListener(this)
    }
}
