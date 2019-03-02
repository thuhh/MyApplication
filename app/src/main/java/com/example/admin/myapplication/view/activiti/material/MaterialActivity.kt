package com.example.admin.myapplication.view.activiti.material

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterMaterial
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Material
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.LoginActivity
import com.example.admin.myapplication.view.dialog.DialogAddMaterial
import kotlinx.android.synthetic.main.activity_material.*
import java.util.*

class MaterialActivity : AppCompatActivity(), ItemTableClick, IClickDialog, View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd){
            MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"yes")
            dialogAddMaterial!!.show()
        }
        else if (v?.id == R.id.btnSearch){
            lnSearch.visibility = View.VISIBLE
            search()
        }else if (v?.id == R.id.btnKhac){
            startActivity(Intent(this, LoginActivity::class.java).putExtra("menu",1))
            finish()
        }
    }

    private fun search() {
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterMaterial!!.filter.filter(charSequence)

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    override fun onclick(check: String?) {
        if (check=="save"){
            materials = rdbMaterial!!.materialDAO().allApp
            adapterMaterial = AdapterMaterial(this, materials,this)
            val manager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            rcMaterial!!.layoutManager = manager
            rcMaterial.adapter = adapterMaterial
        }
    }

    override fun iClick(check: String?, id: Int) {
        Log.e("sdsd","kkk")
    }


    private var rdbMaterial : RDBApp? =null
    private var materials: List<Material> ? =null
    private var adapterMaterial: AdapterMaterial? = null
    private var dialogAddMaterial : DialogAddMaterial?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)

        try {
            rdbMaterial = RDBApp.getAppDatabase(this)
            loadMaterial(MyPreferenceHelper.getInt(MyPreferenceHelper.idUser, this))
            materials = rdbMaterial!!.materialDAO().allApp
        }catch (e: Exception){
            e.printStackTrace()
        }
        dialogAddMaterial = DialogAddMaterial(this)
        dialogAddMaterial!!.setClick(this)

        initListener()

        initData()
    }

    private fun initData() {
        Collections.reverse(materials)
        adapterMaterial = AdapterMaterial(this, materials,this)
        val manager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
        rcMaterial!!.layoutManager = manager
        rcMaterial.adapter = adapterMaterial
    }

    fun loadMaterial(userId : Int){
        if (!MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.firstMaterial, this)) {
            MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.firstMaterial, true, this)
            rdbMaterial!!.materialDAO().insertAll(Material(0, "Bạch tuộc biển", "1", 5, 200, "10:30", "Hải phòng", "R.drawable.material1","Kg","0345505538","anh Thắng",userId))
            rdbMaterial!!.materialDAO().insertAll(Material(1, "Gà", "2", 5, 200, "10:30", "Hải phòng", "R.drawable.material2","Kg","0345505538","anh Thắng",userId))
            rdbMaterial!!.materialDAO().insertAll(Material(2, "Ếch", "3", 5, 200, "10:30", "Hải phòng", "R.drawable.material3","Kg","0345505538","anh Thắng",userId))
            rdbMaterial!!.materialDAO().insertAll(Material(3, "Ngao biển", "1", 5, 200, "10:30", "Hải phòng", "R.drawable.material4","Kg","0345505538","anh Thắng",userId))
            rdbMaterial!!.materialDAO().insertAll(Material(4, "Tôm hùm", "1", 5, 200, "10:30", "Hải phòng", "R.drawable.material5","Kg","0345505538","anh Thắng",userId))
            rdbMaterial!!.materialDAO().insertAll(Material(5, "Thịt trâu gác bếp", "2", 5, 200, "10:30", "Hải phòng", "R.drawable.material6","Kg","0345505538","anh Thắng",userId))
            MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.firstMaterial, true, this)
        }
    }
    private fun initListener() {
        btnAdd.setOnClickListener(this)
        btnSearch.setOnClickListener(this)
        btnKhac.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"no")
    }

    override fun onStop() {
        super.onStop()
        MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"no")
    }
}
