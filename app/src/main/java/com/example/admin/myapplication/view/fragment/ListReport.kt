package com.example.admin.myapplication.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterReport
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.database.RDBApp
import kotlinx.android.synthetic.main.fragment_list_report.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ListReport : Fragment(), View.OnClickListener, ItemTableClick {
    override fun iClick(check: String?, id: Int) {

    }

    override fun onClick(v: View?) {

    }

    private var rdbTable : RDBApp? =null
    private var reports: List<Report> ? =null
    private var adapterReport: AdapterReport? = null

    var rcReport: RecyclerView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_report, container, false)
        try {
            rdbTable = RDBApp.getAppDatabase(context)
            reports = rdbTable!!.reportDAO().allReport

        }catch (e: IllegalStateException){
            e.printStackTrace()
        }
        rcReport = view.findViewById(R.id.rcReport)
        initListItem()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
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

    private fun initListItem() {
        Collections.reverse(reports)
        adapterReport = AdapterReport(this, reports, context)
        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rcReport!!.layoutManager = manager
        rcReport!!.adapter = adapterReport
    }

    companion object {

        var instance: ListReport? = null

        fun newInstance(): ListReport {
            if (instance == null) {
                instance = ListReport()
            }
            return instance as ListReport
        }
    }


}
