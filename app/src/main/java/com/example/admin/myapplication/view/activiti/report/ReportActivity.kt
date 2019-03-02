package com.example.admin.myapplication.view.activiti.report

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.admin.myapplication.R
import com.example.admin.myapplication.R.id.btnSearch
import com.example.admin.myapplication.controller.adapter.AdapterReport
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.LoginActivity
import kotlinx.android.synthetic.main.activity_report.*
import java.util.*

class ReportActivity : AppCompatActivity(), ItemTableClick, View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnList){
            Toast.makeText(this,"Function is updating",Toast.LENGTH_LONG).show()
        }
        else if (v?.id == R.id.btnKhac){
            startActivity(Intent(this, LoginActivity::class.java).putExtra("menu",1))
            finish()
        }else if (v?.id == R.id.btnSearch){
            lnSearch.visibility = View.VISIBLE
            search()
        }
    }

    override fun iClick(check: String?, id: Int) {
        Log.e("sdsd","sdsd")
    }

    private fun search(){
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterReport!!.filter.filter(charSequence)

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }
    private var rdbTable : RDBApp? =null
    private var reports: List<Report> ? =null
    private var adapterReport: AdapterReport? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        try {
            rdbTable = RDBApp.getAppDatabase(this)
            reports = rdbTable!!.reportDAO().allReport

        }catch (e: IllegalStateException){
            e.printStackTrace()
        }

        initListItem()

        initListener()
    }

    private fun initListener() {
        btnList.setOnClickListener(this)
        btnKhac.setOnClickListener(this)
        btnSearch.setOnClickListener(this)
    }
    private fun initListItem() {
        Collections.reverse(reports)
        adapterReport = AdapterReport(this, reports, this)
        val manager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rcReport!!.layoutManager = manager
        rcReport.adapter = adapterReport
    }
}
