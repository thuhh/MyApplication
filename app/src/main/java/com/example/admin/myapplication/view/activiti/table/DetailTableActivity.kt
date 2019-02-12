package com.example.admin.myapplication.view.activiti.table

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterFood
import com.example.admin.myapplication.controller.adapter.AdapterFoodTable
import com.example.admin.myapplication.controller.adapter.AdapterTable
import com.example.admin.myapplication.controller.interfaces.IOnClick
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.dialog.DialogAddTable
import kotlinx.android.synthetic.main.activity_detail_table.*
import kotlinx.android.synthetic.main.activity_food.*
import kotlinx.android.synthetic.main.activity_table.*
import java.util.*
import java.util.Arrays.asList


class DetailTableActivity : AppCompatActivity(), View.OnClickListener, IOnClick, ItemTableClick {
    override fun iClick(check: String?, id: Int) {
        ctAddFood.visibility = View.GONE
    }

    override fun iClick(check: String?) {

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAddFood) {
            ctAddFood.visibility = View.VISIBLE
        } else if (v?.id == R.id.btnThanhtoan) {

        }

    }

    var id = 0;
    private var rdbTable: RDBApp? = null
    private var reports: List<Report>? = null
    private var allFoods: List<Food>? = null
    var foods: MutableList<Food> = mutableListOf<Food>()
    //    private var listFood: String = "0"
    private var listFood: List<String>? = null

    private var report: Report? = null
    private var adapterFoodTable: AdapterFoodTable? = null
    private var adapterFood: AdapterFood? = null
    private var dialogAddTable: DialogAddTable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_table)
        btnAddFood.setOnClickListener(this)
        btnThanhtoan.setOnClickListener(this)
        try {
            rdbTable = RDBApp.getAppDatabase(this)
            reports = rdbTable!!.reportDAO().allReport
            allFoods = rdbTable!!.foodDAO().allFood
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

//        if (intent.getIntExtra("tableId",0)>0){
//            id = intent.getIntExtra("tableId",0)
//            report = reports!![id]
//            listFood = Arrays.asList(report!!.listFood.split(",")) as List<String>
//            for (i in 0 until listFood!!.size){
//                var food : Food
//                food = allFoods!!.get(listFood!!.get(i) as Int)
//                foods!!.add(food)
//            }
//        }
//
//        txtTable.text = "Table $id"
//
//
//        adapterFoodTable = AdapterFoodTable(this, foods,this)
//        val manager = LinearLayoutManager(this)
//        manager.orientation = LinearLayout.VERTICAL
//        rvTable!!.layoutManager = manager!!
//        rvTable.adapter = adapterFoodTable


        adapterFood = AdapterFood(this, allFoods, this)
        val managerFood = GridLayoutManager(this, 2)
        rcAddFood!!.layoutManager = managerFood!!
        rcAddFood!!.addItemDecoration(GridSpacingItemDecoration(4, 5, true))
        rcAddFood.adapter = adapterFood
    }
}
