package com.example.admin.myapplication.view.activiti.account

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.User
import com.example.admin.myapplication.model.database.RDBApp
import kotlinx.android.synthetic.main.activity_acount.*


class AcountActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnBack) {
            onBackPressed()
        } else if (v?.id == R.id.btnChangePass) {
            pass = true
            lnReset.visibility = View.VISIBLE
            imgDownPass.setImageResource(R.drawable.ic_up)
        }
        else if (v?.id == R.id.imgDownPass) {
            if (pass){
                lnReset.visibility = View.GONE
                imgDownPass.setImageResource(R.drawable.ic_down)
            }else{
                lnReset.visibility = View.VISIBLE
                imgDownPass.setImageResource(R.drawable.ic_up)
            }

            pass=!pass
        }
        else if (v?.id == R.id.imgDownData) {
            //reset data
            if (data){
                lnresetData.visibility = View.GONE
                imgDownData.setImageResource(R.drawable.ic_down)
            }else{
                lnresetData.visibility = View.VISIBLE
                imgDownData.setImageResource(R.drawable.ic_up)
            }

            data=!data

        }
        else if (v?.id == R.id.btnReset) {
            //reset data
            data = true
            lnresetData.visibility = View.VISIBLE
            imgDownData.setImageResource(R.drawable.ic_up)

        }
        else if (v?.id == R.id.btnSaveData) {
            //reset data
            data = false
            lnresetData.visibility = View.VISIBLE
            imgDownData.setImageResource(R.drawable.ic_up)

        }
        else if (v?.id == R.id.btnCancleData) {
            //reset data
            data = false
            lnresetData.visibility = View.GONE
            imgDownData.setImageResource(R.drawable.ic_down)

        }
        else if (v?.id == R.id.btnCancle) {
            //reset data
            pass = false
            lnReset.visibility = View.GONE
            imgDownPass.setImageResource(R.drawable.ic_down)

        }
        else if (v?.id == R.id.btnSave) {
            pass = false
            newPass = edtNewPass.text.toString().trim()
            configPass = edtConfig.text.toString().trim()
            if (newPass.isNotEmpty() && configPass.isNotEmpty() && newPass.equals(configPass)) {
                for (i in 0 until users!!.size) {
                    if (users!![i].id == curentId) {
                        currentPass = MyPreferenceHelper.getString(MyPreferenceHelper.password, this)
                        curentPin = users!![i].pinCode
                        if (currentPass != null && currentPass == edtCurrentPass.text.toString().trim()) {
                            rdbApp!!.userDAO().delete(curentId)
                            rdbApp!!.userDAO().insertAll(User(curentId, MyPreferenceHelper.getString(MyPreferenceHelper.userName, this), newPass, configPass, curentPin))
                            MyPreferenceHelper.setString(this, MyPreferenceHelper.password, newPass)
                            Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this,"Mật khẩu hiện tại không chính xác",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }else{
              Toast.makeText(this,"Bạn phải nhập chính xác mật khẩu mới",Toast.LENGTH_LONG).show()
            }

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
    var newPass = ""
    var configPass = ""
    var curentId = 0
    var curentPin = ""

    var pass = false
    var data = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acount)
        try {
            rdbApp = RDBApp.getAppDatabase(this)
            users = rdbApp!!.userDAO().allUser
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        curentId = MyPreferenceHelper.getInt(MyPreferenceHelper.idUser, this)

        btnBack.setOnClickListener(this)
        btnChangePass.setOnClickListener(this)
        btnReset.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        imgDownData.setOnClickListener(this)
        imgDownPass.setOnClickListener(this)
        btnCancle.setOnClickListener(this)
        btnSaveData.setOnClickListener(this)
        btnCancleData.setOnClickListener(this)
    }
}
