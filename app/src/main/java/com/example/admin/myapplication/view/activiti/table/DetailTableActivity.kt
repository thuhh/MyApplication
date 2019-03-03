package com.example.admin.myapplication.view.activiti.table

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterFood
import com.example.admin.myapplication.controller.adapter.AdapterFoodTable
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.FoodTable
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.report.ReportActivity
import kotlinx.android.synthetic.main.activity_detail_table.*
import java.util.*

class DetailTableActivity : AppCompatActivity(), View.OnClickListener, ItemTableClick {
    override fun iClick(check: String?, id: Int) {
        if (check == "click") {
            ctAddFood.visibility = View.GONE
            checkVisibale = false
            var list = ""
            if (table!!.listFood != null) {
                list = table!!.listFood
            }
            if (list != "") {
                table!!.listFood = list + "," + allFoods!![id].id.toString()
            } else {
                table!!.listFood = allFoods!![id].id.toString()
            }

            val listFood = table!!.listFood.split(",")
            val foodTables: MutableList<FoodTable> = mutableListOf<FoodTable>()
            if (listFood!!.isNotEmpty()) {
                for (i in 0 until listFood!!.size) {
                    val idFood = listFood!![i].toInt()
                    var idFoodTable = 0
                    if (allFoodTables!!.isNotEmpty()) {
                        idFoodTable = allFoodTables!!.size
                    }

                    val food = allFoods!![idFood]
                    val foodTable = FoodTable(idFoodTable,food.name,food.type,food.money,food.isNewFood,food.image,food.material,food.sale,food.rate,1,food.descrip,food.userId)
                    initFoods.add(foodTable)

                    foodTables.add(foodTable)
                }
            }
            //set lại tiền
            val money = txtSumMoney.text.toString().toInt()
            val moneyFood = allFoods!![id].money.toString().toInt()
            txtSumMoney.text = (money + moneyFood).toString()

            initListFood(foodTables)
            rdbTable?.tableDAO()!!.delete(idTable)
            rdbTable!!.tableDAO().insertAll(TableDinner(idTable, table?.name, table?.member!!, true, table?.listFood,table?.listCount, table?.iduser!!))
        } else if (check == "delete") {
            Toast.makeText(this, "Hủy món ăn $id", Toast.LENGTH_LONG).show()
            val listFood = table!!.listFood.split(",")

            val foodTables: MutableList<FoodTable> = mutableListOf<FoodTable>()
            for (i in 0 until listFood.size) {
                val idFood = listFood[i].toInt()
                var idFoodTable = 0
                if (allFoodTables!!.isNotEmpty()) {
                    idFoodTable = allFoodTables!!.size
                }

                val food = allFoods!![idFood]
                val foodTable = FoodTable(idFoodTable,food.name,food.type,food.money,food.isNewFood,food.image,food.material,food.sale,food.rate,1,food.descrip,food.userId)
                initFoods.add(foodTable)

                foodTables.add(foodTable)
            }

            val list = ""
            for (i in 0 until listFood.size) {
                if (id == listFood[i].toInt()) {
                    foodTables.removeAt(i)
                } else {
                    if (list != "") {
                        table!!.listFood = list + "," + allFoods!![id].id.toString()
                    } else {
                        table!!.listFood = allFoods!![id].id.toString()
                    }
                }
            }
            //set lại tiền
            val money = txtSumMoney.text.toString().toInt()
            val moneyFood = allFoods!![id].money.toString().toInt()
            txtSumMoney.text = (money - moneyFood).toString()

            initListFood(foodTables)
            rdbTable?.tableDAO()!!.delete(idTable)
            rdbTable!!.tableDAO().insertAll(TableDinner(idTable, table?.name, table?.member!!, true, table?.listFood,table?.listCount, table?.iduser!!))

        } else if (check == "down") {
            Toast.makeText(this, "Thêm món ăn $id", Toast.LENGTH_LONG).show()
        } else if (check == "plus") {
            Toast.makeText(this, "Trừ món ăn $id", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAddFood) {
            ctAddFood.visibility = View.VISIBLE
            checkVisibale = true
        } else if (v?.id == R.id.btnThanhtoan) {
            val calendar: Calendar = Calendar.getInstance();
            val time = calendar.get(Calendar.HOUR).toString() + ":" + calendar.get(Calendar.MINUTE).toString()
            var id = 0
            if (reports!!.isNotEmpty()) {
                id = reports!!.size
            }
            rdbTable!!.reportDAO().insertAll(Report(id, "Report$id", idTable, table!!.listFood,table!!.listCount, txtSumMoney.text.toString(), time, calendar.get(Calendar.DATE).toString()))
            rdbTable!!.tableDAO().delete(idTable)
            rdbTable!!.tableDAO().insertAll(TableDinner(idTable, table!!.name, table!!.member, false, "","", table!!.iduser))
            startActivity(Intent(this, ReportActivity::class.java))
            finish()
        } else if (v?.id == R.id.btnBack) {
            finish()
        }
    }

    private var idTable = 0
    private var rdbTable: RDBApp? = null
    private var reports: List<Report>? = null
    private var tables: List<TableDinner>? = null
    private var allFoods: List<Food>? = null
    private var allFoodTables: List<FoodTable>? = null


    private var initFoods: MutableList<FoodTable> = mutableListOf<FoodTable>()
    private var listFood: List<String>? = null
    private var table: TableDinner? = null
    private var adapterFoodTable: AdapterFoodTable? = null
    private var adapterFood: AdapterFood? = null

    private var checkVisibale = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_table)
        btnAddFood.setOnClickListener(this)
        btnThanhtoan.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        try {
            rdbTable = RDBApp.getAppDatabase(this)
            reports = rdbTable!!.reportDAO().allReport
            tables = rdbTable!!.tableDAO().allTable
            allFoods = rdbTable!!.foodDAO().allFood
            allFoodTables = rdbTable!!.foodTableDAO().allFood
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        idTable = intent.getIntExtra("tableId", 0)
        table = tables!![idTable]
        if (table!!.listFood.toString().trim() != "") {
            listFood = table!!.listFood.split(",")
            if (listFood!!.isNotEmpty()) {
                for (i in 0 until listFood!!.size) {
                    var idFood = listFood!![i].toInt()
                    var food: Food
                    var foodTable: FoodTable

                    var id = 0
                    if (allFoodTables!!.isNotEmpty()) {
                        id = allFoodTables!!.size
                    }
                    food = allFoods!![idFood]
                    foodTable = FoodTable(id,food.name,food.type,food.money,food.isNewFood,food.image,food.material,food.sale,food.rate,1,food.descrip,food.userId)
                    initFoods.add(foodTable)

                    val money = txtSumMoney.text.toString().toInt()
                    val moneyFood = food.money.toString().toInt()
                    txtSumMoney.text = (money + moneyFood).toString()
                }
            }
        }
        txtTable.text = table!!.name.toString()
//
        initListFood(initFoods)


        adapterFood = AdapterFood(this, allFoods, this)
        val managerFood = GridLayoutManager(this, 1)
        rcAddFood!!.layoutManager = managerFood
        rcAddFood!!.addItemDecoration(GridSpacingItemDecoration(4, 5, true))
        rcAddFood.adapter = adapterFood
    }

    private fun initListFood(foods: MutableList<FoodTable>) {
        adapterFoodTable = AdapterFoodTable(this, foods, this)
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        rcFood!!.layoutManager = manager
        rcFood.adapter = adapterFoodTable
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (checkVisibale) {
            ctAddFood.visibility = View.GONE
            checkVisibale = false
        } else {
            finish()
        }
    }

}

