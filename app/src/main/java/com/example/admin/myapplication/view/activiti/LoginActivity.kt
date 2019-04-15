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
import com.example.admin.myapplication.controller.util.MySingleton
import com.example.admin.myapplication.controller.util.Utils
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.Suppliers
import com.example.admin.myapplication.model.`object`.User
import com.example.admin.myapplication.view.activiti.account.AcountActivity
import com.example.admin.myapplication.view.activiti.food.FoodActivity
import com.example.admin.myapplication.view.activiti.material.MaterialActivity
import com.example.admin.myapplication.view.activiti.report.ReportActivity
import com.example.admin.myapplication.view.activiti.table.TableActivity

import org.json.JSONArray
import org.json.JSONException

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.HashMap

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    //database user
    internal var rdbApp: RDBApp ? = null
    internal var users: List<User>? = null
    internal var suppliers: List<Suppliers>? = null

    internal var btnLogin: Button? = null
    internal var edtUserLogin: EditText? = null
    internal var edtPassLogin: EditText? = null
    internal var chkRemember: CheckBox? = null
    internal var txtForget: TextView? = null
    internal var txtSignup: TextView? = null
    internal var btnEye: ImageView? = null

    internal var btnSignup: Button? = null
    internal var edtUserSignup: EditText? = null
    internal var edtPassSignup: EditText? = null
    internal var edtComfig: EditText? = null
    internal var edtPinCode: EditText? = null

    internal var rlLogin: RelativeLayout? = null
    internal var rlSignup: RelativeLayout? = null
    internal var viewFlipper: ViewFlipper? = null

    internal var ctBanAn: ConstraintLayout? = null
    internal var ctFood: ConstraintLayout? = null
    internal var ctMaterial: ConstraintLayout? = null
    internal var ctReport: ConstraintLayout? = null
    internal var ctUser: ConstraintLayout? = null
    internal var ctSetting: ConstraintLayout? = null

    internal var dulieu: StringBuilder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initUI()
        checkRemember()
        if (intent != null) {
            if (intent.getIntExtra("menu", -1) > -1) {
                viewFlipper!!.displayedChild = 2
            }
        }

    }


    private fun checkRemember() {
        if (MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.remember, this)) {
            chkRemember!!.isChecked = true
            edtUserLogin!!.setText(MyPreferenceHelper.getString(MyPreferenceHelper.userName, this))
            edtPassLogin!!.setText(MyPreferenceHelper.getString(MyPreferenceHelper.password, this))
            viewFlipper!!.displayedChild = 2
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
        viewFlipper!!.displayedChild = 1
        rlLogin = findViewById(R.id.rlLogin)
        rlSignup = findViewById(R.id.rlSignup)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignUp)
        btnEye = findViewById(R.id.imgEye)
        edtUserLogin = findViewById(R.id.edtUser)
        edtPassLogin = findViewById(R.id.edtPass)
        edtUserSignup = findViewById(R.id.edtUserSignup)
        edtPassSignup = findViewById(R.id.edtPassSignUp)
        edtComfig = findViewById(R.id.edtComfigPassSignUp)
        edtPinCode = findViewById(R.id.edtPin)
        txtForget = findViewById(R.id.txtForget)
        txtSignup = findViewById(R.id.txtSignup)
        chkRemember = findViewById(R.id.chkRemember)
        ctBanAn = findViewById(R.id.ctBanAn)
        ctFood = findViewById(R.id.ctDoAn)
        ctMaterial = findViewById(R.id.ctNguyenLieu)
        ctReport = findViewById(R.id.ctBaoCao)
        ctSetting = findViewById(R.id.ctCaiDat)
        ctUser = findViewById(R.id.ctTaiKhoan)
        ctBanAn!!.setOnClickListener(this)
        ctFood!!.setOnClickListener(this)
        ctMaterial!!.setOnClickListener(this)
        ctReport!!.setOnClickListener(this)
        ctUser!!.setOnClickListener(this)
        ctSetting!!.setOnClickListener(this)
        btnLogin!!.setOnClickListener(this)
        btnSignup!!.setOnClickListener(this)
        txtForget!!.setOnClickListener(this)
        txtSignup!!.setOnClickListener(this)
        chkRemember!!.setOnCheckedChangeListener { buttonView, isChecked ->
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
                if (users!!.size > 0) {
                    for (i in users!!.indices) {
                        if (users!![i].username == edtUserLogin!!.text.toString().trim { it <= ' ' } && users!![i].password == edtPassLogin!!.text.toString().trim { it <= ' ' }) {
                            MyPreferenceHelper.setInt(MyPreferenceHelper.idUser, users!![i].id, this)
                            MyPreferenceHelper.setString(this@LoginActivity, MyPreferenceHelper.userName, edtUserLogin!!.text.toString())
                            MyPreferenceHelper.setString(this@LoginActivity, MyPreferenceHelper.password, edtPassLogin!!.text.toString())
                            viewFlipper!!.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
                            viewFlipper!!.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
                            viewFlipper!!.displayedChild = 2

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
            R.id.btnSignUp -> {
                if (users != null && users!!.size > 0) {
                    var d = 0
                    for (i in users!!.indices) {
                        if (users!![i].username == edtUserSignup!!.text.toString().trim { it <= ' ' }) {
                            Toast.makeText(this, "Tài khoản đã tồn tại!", Toast.LENGTH_LONG).show()
                            d++
                        }
                    }
                    if (d == 0) {
                        insertUser()
                    }
                } else {
                    insertUser()
                }

                //hide soft keyboard
                val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                var view = currentFocus
                if (view == null) {
                    view = View(this)
                }
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            R.id.txtForget -> txtSignup!!.setTextColor(Color.BLUE)
            R.id.txtSignup -> {
                viewFlipper!!.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
                viewFlipper!!.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
                viewFlipper!!.displayedChild = 0
            }
            R.id.imgEye -> {
            }
            R.id.ctBanAn -> nextActivity(1, TableActivity::class.java)
            R.id.ctDoAn -> nextActivity(2, FoodActivity::class.java)
            R.id.ctNguyenLieu -> nextActivity(3, MaterialActivity::class.java)
            R.id.ctBaoCao -> nextActivity(4, ReportActivity::class.java)
            R.id.ctCaiDat -> nextActivity(5, SettingActivity::class.java)
            R.id.ctTaiKhoan -> nextActivity(6, AcountActivity::class.java)

            else -> {
            }
        }
    }

    private fun insertUser() {
        var id = 0
        if (users != null) {
            id = users!!.size
        }
        if (!edtUserSignup!!.text.toString().trim { it <= ' ' }.isEmpty() && !edtPassSignup!!.text.toString().trim { it <= ' ' }.isEmpty() && !edtComfig!!.text.toString().trim { it <= ' ' }.isEmpty() && !edtPinCode!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            if (edtPassSignup!!.text.toString().trim { it <= ' ' } == edtComfig!!.text.toString().trim { it <= ' ' }) {
                rdbApp!!.userDAO().insertAll(User(id, edtUserSignup!!.text.toString().trim { it <= ' ' }, edtPassSignup!!.text.toString().trim { it <= ' ' }, edtComfig!!.text.toString().trim { it <= ' ' }, edtPinCode!!.text.toString().trim { it <= ' ' }))
                edtUserLogin!!.setText(edtUserSignup!!.text.toString().trim { it <= ' ' })
                edtPassLogin!!.setText(edtPassSignup!!.text.toString().trim { it <= ' ' })
                viewFlipper!!.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
                viewFlipper!!.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
                viewFlipper!!.setFlipInterval(1)
                viewFlipper!!.displayedChild = 1
            } else {
                Toast.makeText(this, "Mật khẩu xác nhận không đúng!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Bạn cần điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
        }
    }

    inner class DownloadJSON : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String): String {
            try {
                val url = URL(Utils.url + "getSupplier.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                val bufferedReader = BufferedReader(inputStreamReader)
                dulieu = StringBuilder()
                val text:List<String> = bufferedReader.readLines()
                for(line in text){
                    dulieu!!.append(line)
                }

                var jsonarray: JSONArray? = null
                try {
                    jsonarray = JSONArray(dulieu.toString())
                    for (i in 0 until jsonarray!!.length()) {
                        val jsonobject = jsonarray.getJSONObject(i)
                        val name = jsonobject.getString("supplier_name")
                        val id = jsonobject.getInt("supplier_id")
                        val phone = jsonobject.getString("supplier_phone")
                        val address = jsonobject.getString("supplier_address")

                        rdbApp!!.suppierDAO().insertAll(Suppliers(id,name,phone,address))
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return dulieu.toString()
        }
    }
}
