package com.example.admin.myapplication.view.activiti.report

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.admin.myapplication.R
import com.example.admin.myapplication.R.id.btnSearch
import com.example.admin.myapplication.controller.adapter.AdapterReport
import com.example.admin.myapplication.controller.adapter.CustomAdapter
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.LoginActivity
import com.example.admin.myapplication.view.fragment.ChartMonth
import com.example.admin.myapplication.view.fragment.ListReport
import kotlinx.android.synthetic.main.activity_report.*
import java.util.*
import android.view.MotionEvent
import com.example.admin.myapplication.model.chart.ScrollBar


class ReportActivity : AppCompatActivity(), ItemTableClick, View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnList) {
            chartMonth.visibility = View.GONE
            btnList.visibility = View.GONE
            btnChart.visibility = View.VISIBLE
            btnSearch.visibility = View.VISIBLE
            lnSearch.visibility = View.VISIBLE
            rcReport.visibility = View.VISIBLE
        } else if (v?.id == R.id.btnBack) {
            startActivity(Intent(this, LoginActivity::class.java).putExtra("menu", 1))
            finish()
        }
        else if (v?.id == R.id.btnSearch) {
           search()
        }else if (v?.id == R.id.btnChart) {
            chartMonth.visibility = View.VISIBLE
            btnList.visibility = View.VISIBLE
            btnChart.visibility = View.GONE
            btnSearch.visibility = View.GONE
            lnSearch.visibility = View.GONE
            rcReport.visibility = View.GONE
        }
    }

    override fun iClick(check: String?, id: Int) {
        Log.e("sdsd", "sdsd")
    }

    private var verticalList: MutableList<Float>? = null
    private var horizontalList: MutableList<String>? = null
    private var barChart: ScrollBar? = null
    private var random: Random? = null

    private var rdbTable: RDBApp? = null
    private var reports: List<Report>? = null
    private var adapterReport: AdapterReport? = null

    var calendar: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        calendar = Calendar.getInstance()
        try {
            rdbTable = RDBApp.getAppDatabase(this)
            reports = rdbTable!!.reportDAO().allReport

        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        initListItem()
        initListener()


        barChart = findViewById(R.id.chartMonth)

        verticalList = ArrayList<Float>()
        horizontalList = ArrayList<String>()

        for (i in 1..31) {
            horizontalList!!.add("" + i)
            var k = 0f
            for (j in 0 until reports!!.size) {
                Log.e("sdsdsd", reports!![j].date + "//" + i)
                if (reports!![j].date == i.toString()) {
                    k += (reports!![j].money).toFloat()
                }
            }
            verticalList!!.add(k)
        }


        random = Random()
        while (verticalList!!.size < 31) {
            val randomInt = random!!.nextInt(1000)
            verticalList!!.add(randomInt.toFloat())

        }


        barChart!!.setHorizontalList(horizontalList)
        barChart!!.setVerticalList(verticalList)
    }

    private fun initListener() {
        btnList.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        btnChart.setOnClickListener(this)
    }

    private fun initListItem() {
        Collections.reverse(reports)
        adapterReport = AdapterReport(this, reports, this)
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcReport!!.layoutManager = manager
        rcReport!!.adapter = adapterReport
    }

    private fun search() {
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterReport!!.filter.filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }
}
