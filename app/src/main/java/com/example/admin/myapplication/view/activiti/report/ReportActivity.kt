package com.example.admin.myapplication.view.activiti.report

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
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
        if (v?.id == R.id.btnList){
            Toast.makeText(this,"Function is updating",Toast.LENGTH_LONG).show()
        }
        else if (v?.id == R.id.btnKhac){
            startActivity(Intent(this, LoginActivity::class.java).putExtra("menu",1))
            finish()
        }
    }

    override fun iClick(check: String?, id: Int) {
        Log.e("sdsd","sdsd")
    }


    var chartMonth: ChartMonth? = null
    var listReport: ListReport? = null

    private var verticalList: MutableList<Float>? = null
    private var horizontalList: MutableList<String>? = null
    private var barChart: ScrollBar? = null
    private var random: Random? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        initListener()

//        chartMonth = ChartMonth.newInstance()
//        listReport = ListReport.newInstance()
//
//        var array: List<Fragment?> = listOf(listReport, chartMonth)
//        viewpager!!.adapter = CustomAdapter(supportFragmentManager, array)
//        viewpager.offscreenPageLimit = 2
//        viewpager.currentItem = 2
//        viewpager.setOnTouchListener({ v, event -> true })

        barChart = findViewById(R.id.chartMonth)

        verticalList = ArrayList<Float>()
        horizontalList = ArrayList<String>()


        for (i in 0..30) {
            horizontalList!!.add("" + i)
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
        btnKhac.setOnClickListener(this)
    }

}
