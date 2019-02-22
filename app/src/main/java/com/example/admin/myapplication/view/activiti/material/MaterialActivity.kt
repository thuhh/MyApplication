package com.example.admin.myapplication.view.activiti.material

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextWatcher
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

class MaterialActivity : AppCompatActivity(), ItemTableClick, IClickDialog, View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd){
            MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"yes")
            dialogAddMaterial!!.show()
        }
        else if (v?.id == R.id.btnSearch){
            search()
        }else if (v?.id == R.id.btnKhac){
            startActivity(Intent(this, LoginActivity::class.java).putExtra("menu",1))
            finish()
        }
    }

    private fun search() {

    }

    override fun onclick(check: String?) {
        if (check=="save"){
            materials = rdbMaterial!!.materialDAO().allApp
            adapterMaterial = AdapterMaterial(this, materials,this)
            val manager = GridLayoutManager(this, 1)
            rcMaterial!!.layoutManager = manager!!
            rcMaterial!!.addItemDecoration(GridSpacingItemDecoration(4, 5, true))
            rcMaterial.adapter = adapterMaterial
            MyPreferenceHelper.setString(this, MyPreferenceHelper.SELECT_IMAGE,"")
        }
    }

    override fun iClick(check: String?, id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private var rdbMaterial : RDBApp? =null;
    private var materials: List<Material> ? =null
    private var adapterMaterial: AdapterMaterial? = null
    private var dialogAddMaterial : DialogAddMaterial?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)

        try {
            rdbMaterial = RDBApp.getAppDatabase(this)
            materials = rdbMaterial!!.materialDAO().allApp
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        dialogAddMaterial = DialogAddMaterial(this);
        dialogAddMaterial!!.setClick(this)

        initListener()

        if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this)!=null){
            if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this).equals("yes")){
                dialogAddMaterial!!.show()
            }
        }
        adapterMaterial = AdapterMaterial(this, materials,this)
        val manager = GridLayoutManager(this, 1)
        rcMaterial!!.layoutManager = manager!!
        rcMaterial!!.addItemDecoration(GridSpacingItemDecoration(4, 5, true))
        rcMaterial.adapter = adapterMaterial
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