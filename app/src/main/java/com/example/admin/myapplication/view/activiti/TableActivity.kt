package com.example.admin.myapplication.view.activiti

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterFood
import com.example.admin.myapplication.controller.adapter.AdapterTable
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.interfaces.IOnClick
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.dialog.DialogAddFood
import com.example.admin.myapplication.view.dialog.DialogAddTable
import kotlinx.android.synthetic.main.activity_table.*

class TableActivity : AppCompatActivity(), IClickDialog, IOnClick, View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd){
            MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"yes")
            dialogAddTable!!.show()
        }
        else if (v?.id == R.id.btnSearch){
            edtSearch.visibility = View.VISIBLE
            search()
        } else if (v?.id == R.id.btnKhac){
            startActivity(Intent(this@TableActivity,LoginActivity::class.java).putExtra("menu",1))
            finish()
        }
    }

    override fun iClick(check: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onclick(check: String?) {
        if (check=="save"){
            tables = rdbTable!!.tableDAO().allTable
           initListItem()
        }
    }

    private fun search(){
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterTable!!.getFilter().filter(charSequence)

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }
    private var rdbTable : RDBApp? =null;
    private var tables: List<TableDinner> ? =null
    private var adapterTable: AdapterTable? = null
    private var dialogAddTable : DialogAddTable?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        try {
            rdbTable = RDBApp.getAppDatabase(this)
            tables = rdbTable!!.tableDAO().allTable
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        dialogAddTable = DialogAddTable(this);
        dialogAddTable!!.setClick(this)

        initListener()

        if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this)!=null){
            if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this).equals("yes")){
                dialogAddTable!!.show()
            }
        }

        initListItem()
    }

    private fun initListItem() {
        adapterTable = AdapterTable(this@TableActivity, tables,this)
        val manager = GridLayoutManager(this, 3)
        rvTable!!.layoutManager = manager!!
        rvTable!!.addItemDecoration(GridSpacingItemDecoration(4, 5, true))
        rvTable.adapter = adapterTable
    }

    private fun initListener() {
        btnAdd.setOnClickListener(this)
        btnSearch.setOnClickListener(this)
        btnKhac.setOnClickListener(this)
    }
}

