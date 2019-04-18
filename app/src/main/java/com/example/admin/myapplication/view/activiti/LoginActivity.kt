package com.example.admin.myapplication.view.activiti

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest

import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.model.`object`.User
import com.example.admin.myapplication.view.activiti.account.AcountActivity
import com.example.admin.myapplication.view.activiti.food.FoodActivity
import com.example.admin.myapplication.view.activiti.material.MaterialActivity
import com.example.admin.myapplication.view.activiti.report.ReportActivity
import com.example.admin.myapplication.view.activiti.table.TableActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    //database user
    internal var rdbApp: RDBApp ? = null
    internal var users: List<User>? = null

    internal var btnLogin: Button? = null
    internal var edtUserLogin: EditText? = null
    internal var edtPassLogin: EditText? = null
    internal var chkRemember: CheckBox? = null
    internal var txtForget: TextView? = null
    internal var btnEye: ImageView? = null


    internal var rlLogin: RelativeLayout? = null
    internal var viewFlipper: ViewFlipper? = null

    internal var ctBanAn: ConstraintLayout? = null
    internal var ctFood: ConstraintLayout? = null
    internal var ctMaterial: ConstraintLayout? = null
    internal var ctReport: ConstraintLayout? = null
    internal var ctUser: ConstraintLayout? = null
    internal var ctSetting: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initUI()
        checkRemember()

        if (intent != null) {
            if (intent.getIntExtra("menu", -1) > -1) {
                viewFlipper!!.displayedChild = 1
            }
        }

    }


    private fun checkRemember() {
        if (MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.remember, this)) {
            chkRemember!!.isChecked = true
            edtUserLogin!!.setText(MyPreferenceHelper.getString(MyPreferenceHelper.userName, this))
            edtPassLogin!!.setText(MyPreferenceHelper.getString(MyPreferenceHelper.password, this))
            viewFlipper!!.displayedChild = 1
        } else {
            chkRemember!!.isChecked = false
        }
    }

    private fun initUI() {
        try {
            rdbApp = RDBApp.getAppDatabase(this)
            users = rdbApp!!.userDAO().allUser
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }


        viewFlipper = findViewById(R.id.viewFlipper)
        viewFlipper!!.displayedChild = 0
        rlLogin = findViewById(R.id.rlLogin)
        btnLogin = findViewById(R.id.btnLogin)
        btnEye = findViewById(R.id.imgEye)
        edtUserLogin = findViewById(R.id.edtUser)
        edtPassLogin = findViewById(R.id.edtPass)
        txtForget = findViewById(R.id.txtForget)
        chkRemember = findViewById(R.id.chkRemember)
        ctBanAn = findViewById(R.id.ctBanAn)
        ctFood = findViewById(R.id.ctDoAn)
        ctMaterial = findViewById(R.id.ctNguyenLieu)
        ctReport = findViewById(R.id.ctBaoCao)
        ctBanAn!!.setOnClickListener(this)
        ctFood!!.setOnClickListener(this)
        ctMaterial!!.setOnClickListener(this)
        ctReport!!.setOnClickListener(this)
        btnLogin!!.setOnClickListener(this)
        txtForget!!.setOnClickListener(this)
        chkRemember!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.remember, true, this@LoginActivity)
            } else {
                MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.remember, false, this@LoginActivity)
            }
        }

    }

    private fun nextActivity(value: Int, activity: Class<*>) {
        MyPreferenceHelper.setInt(MyPreferenceHelper.keyNext, value, this)
        startActivity(Intent(this@LoginActivity, activity))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> if (!edtUserLogin!!.text.toString().trim { it <= ' ' }.isEmpty() && !edtPassLogin!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                users = rdbApp!!.userDAO().allUser
                if (users!!.isNotEmpty()) {
                    for (i in users!!.indices) {
                        if (users!![i].username == edtUserLogin!!.text.toString().trim { it <= ' ' } && users!![i].password == edtPassLogin!!.text.toString().trim { it <= ' ' }) {
                            MyPreferenceHelper.setInt(MyPreferenceHelper.idUser, users!![i].id, this)
                            MyPreferenceHelper.setString(this@LoginActivity, MyPreferenceHelper.userName, edtUserLogin!!.text.toString())
                            MyPreferenceHelper.setString(this@LoginActivity, MyPreferenceHelper.password, edtPassLogin!!.text.toString())
                            viewFlipper!!.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
                            viewFlipper!!.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
                            viewFlipper!!.displayedChild = 1

                            return
                        }
                    }
                    Toast.makeText(this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Tài khoản này không tồn tại!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu đang để trống!", Toast.LENGTH_SHORT).show()
            }

            R.id.imgEye -> {
            }
            R.id.ctBanAn -> nextActivity(1, TableActivity::class.java)
            R.id.ctDoAn -> nextActivity(2, FoodActivity::class.java)
            R.id.ctNguyenLieu -> nextActivity(3, SettingActivity::class.java)
            R.id.ctBaoCao -> nextActivity(4, ReportActivity::class.java)
//            R.id.ctCaiDat -> nextActivity(5, SettingActivity::class.java)
//            R.id.ctTaiKhoan -> nextActivity(6, AcountActivity::class.java)

            else -> {
            }
        }
    }
}
