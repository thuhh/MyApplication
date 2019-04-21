package com.example.admin.myapplication.view.activiti.report

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterReport
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.Utils
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.`object`.Suppliers
import com.example.admin.myapplication.model.chart.ScrollBar
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.LoginActivity
import kotlinx.android.synthetic.main.activity_report.*
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class ReportActivity : AppCompatActivity(), ItemTableClick, View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnList) {
            chartMonth.visibility = View.GONE
            btnList.visibility = View.GONE
            lnDate.visibility = View.GONE
            btnChart.visibility = View.VISIBLE
            btnSearch.visibility = View.VISIBLE
            rcReport.visibility = View.VISIBLE
        } else if (v?.id == R.id.btnBack) {
            startActivity(Intent(this, LoginActivity::class.java).putExtra("menu", 1))
            finish()
        } else if (v?.id == R.id.btnSearch) {
            search()
        } else if (v?.id == R.id.btnChart) {
            chartMonth.visibility = View.VISIBLE
            btnList.visibility = View.VISIBLE
            lnDate.visibility = View.VISIBLE
            btnChart.visibility = View.GONE
            btnSearch.visibility = View.GONE
            lnSearch.visibility = View.GONE
            rcReport.visibility = View.GONE
        } else if (v?.id == R.id.btnNext) {
            month++
            if (month == 13) {
                year++
                month = 1
            }
            getData(month,year)

        } else if (v?.id == R.id.btnPre) {

            month--
            if (month == 0) {
                year--
                month = 12
            }
            getData(month,year)

            txtMonth.text = month.toString() + "/" + year.toString()
        }
    }

    private fun getData(month: Int, year: Int) {

        txtMonth.text = "$month/$year"
        verticalList = ArrayList<Float>()
        horizontalList = ArrayList<String>()

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            max = 31
        } else if (month == 2) {
            if (year % 4000 == 0) {
                max = 29
            } else {
                max = 28
            }
        }else{
            max = 30
        }

        for (i in 1..max) {
            horizontalList!!.add("" + i)
            var k = 0f
            for (j in 0 until reports!!.size) {
                if (reports!![j].date == i.toString() && reports!![j].month == month.toString() && reports!![j].year == year.toString())  {
                    k += (reports!![j].money).toFloat()
                }
            }
            verticalList!!.add(k)
        }

        barChart!!.setHorizontalList(horizontalList)
        barChart!!.setVerticalList(verticalList)

    }

    override fun iClick(check: String?, id: Int) {
        Log.e("sdsd", "sdsd")
    }

    private var verticalList: MutableList<Float>? = null
    private var horizontalList: MutableList<String>? = null
    private var barChart: ScrollBar? = null
    private var rdbApp: RDBApp? = null
    private var reports: List<Report>? = null
    private var adapterReport: AdapterReport? = null

    var calendar: Calendar? = null
    var month = 4
    var year = 2019
    var max = 30
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        try {
            rdbApp = RDBApp.getAppDatabase(this)
            reports = rdbApp!!.reportDAO().allReport

        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        initListItem()
        initListener()
        barChart = findViewById(R.id.chartMonth)

        //get data
        calendar = Calendar.getInstance()
        month = calendar!!.get(Calendar.MONTH)+1
        year = calendar!!.get(Calendar.YEAR)
        getData(month,year)
    }

    private fun initListener() {
        btnList.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        btnChart.setOnClickListener(this)
        btnPre.setOnClickListener(this)
        btnNext.setOnClickListener(this)
    }

    private fun initListItem() {
        Collections.reverse(reports)
        adapterReport = AdapterReport(this, reports, this)
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcReport!!.layoutManager = manager
        rcReport!!.adapter = adapterReport
    }

    private fun search() {
        lnSearch.visibility = View.VISIBLE
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
