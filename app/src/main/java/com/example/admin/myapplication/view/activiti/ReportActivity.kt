package com.example.admin.myapplication.view.activiti

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterReport
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.database.RDBApp
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity(), ItemTableClick {
    override fun iClick(check: String?, id: Int) {

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
            if (reports!!.size>0){
                Log.e("sdsd","s")
            }
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }

        initListItem()
    }

    private fun initListItem() {
        adapterReport = AdapterReport(this, reports, this)
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        rcReport!!.layoutManager = manager
        rcReport.adapter = adapterReport
    }
}
