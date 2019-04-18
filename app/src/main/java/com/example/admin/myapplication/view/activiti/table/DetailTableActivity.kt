package com.example.admin.myapplication.view.activiti.table

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterFood
import com.example.admin.myapplication.controller.adapter.AdapterFoodClick
import com.example.admin.myapplication.controller.adapter.AdapterFoodTable
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.controller.util.MySingleton
import com.example.admin.myapplication.controller.util.Utils
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.report.ReportActivity
import kotlinx.android.synthetic.main.activity_detail_table.*
import java.util.*


class DetailTableActivity : AppCompatActivity(), View.OnClickListener, ItemTableClick {
    override fun iClick(check: String?, id: Int) {
        tables = rdbTable!!.tableDAO().allTable
        initFoods = mutableListOf<Food>()

        for (k in 0 until tables!!.size) {
            if (tables!![k].id == idTable){
                table = tables!![k]
            }
        }


        if (check == "click") {
            ctAddFood.visibility = View.GONE
            checkVisibale = false
            var count = 0
            if (table!!.listFood != "") {
                val listFood = table!!.listFood.split(",")
                val listCount = table!!.listCount.split(",")
                var d = 0
                for (i in 0 until listFood.size) {
                    val idFood = listFood[i].toInt()
                    if (idFood == id) {
                        val builder = StringBuilder()
                        for (k in 0 until listCount.size) {
                            if (k < listCount.size - 1) {
                                if (i == k) {
                                    builder.append(listCount!![k].toInt() + 1)
                                    count = listCount!![k].toInt() + 1
                                } else {
                                    builder.append(listCount[k])
                                }
                                builder.append(",")
                            } else {
                                if (i == k) {
                                    builder.append(listCount!![k].toInt() + 1)
                                    count = listCount!![k].toInt() + 1
                                } else {
                                    builder.append(listCount[k])
                                }
                            }
                        }
                        d++
                        table!!.listCount = builder.toString()
                        for (k in 0 until allFoods!!.size) {
                            if (allFoods!![k].id == idFood){
                                var food = allFoods!![k]
                                food.count = count
                                initFoods.add(food)
                            }
                        }
                    } else {
                        for (k in 0 until allFoods!!.size) {
                            if (allFoods!![k].id == idFood){
                                var food = allFoods!![k]
                                food.count = listCount[i].toInt()
                                initFoods.add(food)
                            }
                        }

                    }
                }
                if (d == 0) {
                    table!!.listFood = table!!.listFood + "," + id.toString()
                    table!!.listCount = table!!.listCount + "," + 1
                    for (k in 0 until allFoods!!.size) {
                        if (allFoods!![k].id == id){
                            var food = allFoods!![k]
                            food.count = 1
                            initFoods.add(food)
                        }
                    }
                }
            } else {
                table!!.listFood = id.toString()
                table!!.listCount = "1"
                for (k in 0 until allFoods!!.size) {
                    if (allFoods!![k].id == id){
                        val food = allFoods!![k]
                        food.count = 1
                        initFoods.add(food)
                    }
                }

            }

            initListFood(initFoods)
            //set lại tiền
            val money = txtSumMoney.text.toString().toInt()
            var old = 0
            var sale = 0
            for (k in 0 until allFoods!!.size) {
                if (allFoods!![k].id == id){
                    val food = allFoods!![k]
                    old = food.money.toString().toInt()
                    sale = food.sale.toString().toInt()
                }
            }
            val moneyFood = old - old * sale / 100
            txtSumMoney.text = (money + moneyFood).toString()
            rdbTable?.tableDAO()!!.delete(idTable)
            rdbTable!!.tableDAO().insertAll(TableDinner(idTable, table?.name, table?.member!!, edtKh.text.toString().toInt(),true, table?.listFood, table?.listCount, table?.iduser!!))

        } else if (check == "delete") {
            var listF = ""
            var listC = ""
            var money = 0
            if (table!!.listFood != "") {
                val listFood = table!!.listFood.split(",")
                val listCount = table!!.listCount.split(",")
                var d = 0
                for (i in 0 until listFood.size) {
                    val idFood = listFood[i].toInt()
                    Log.e("sdsdsd",idFood.toString()+".."+id)
                    if (idFood != id) {
                        if (d == 0) {
                            listF = listFood[i]
                            listC = listCount[i]
                            d++
                        } else {
                            listF = listF + "," + listFood[i]
                            listC = listC + "," + listCount[i]
                        }

                        for (k in 0 until allFoods!!.size) {
                            if (allFoods!![k].id == idFood){
                                var food = allFoods!![k]
                                food.count = listCount[i].toInt()
                                val old = food.money.toInt()
                                val sale = food.sale.toInt()
                                val moneyFood = old - old * sale / 100
                                money += moneyFood
                                initFoods.add(food)
                            }
                        }
                    }
                }
            }

            //set lại tiền
            txtSumMoney.text = money.toString()

            initListFood(initFoods)
            rdbTable?.tableDAO()!!.delete(idTable)
            rdbTable!!.tableDAO().insertAll(TableDinner(idTable, table?.name, table?.member!!, edtKh.text.toString().toInt(),true, listF, listC, table?.iduser!!))
            tables = rdbTable!!.tableDAO().allTable
            for (k in 0 until tables!!.size) {
                if (tables!![k].id == idTable){
                    table = tables!![k]
                }
            }

        } else if (check == "plus") {
            Toast.makeText(this, "Thêm món ăn $id", Toast.LENGTH_LONG).show()
            val listFood = table!!.listFood.split(",")
            val listCount = table!!.listCount.split(",")
            var count = 0
            for (i in 0 until listFood.size) {
                val idFood = listFood[i].toInt()
                if (idFood == id) {
                    val builder = StringBuilder()
                    for (k in 0 until listCount.size) {
                        if (k < listCount.size - 1) {
                            if (i == k) {
                                builder.append(listCount[k].toInt() + 1)
                                count = listCount[k].toInt() + 1
                            } else {
                                builder.append(listCount[k])
                            }
                            builder.append(",")
                        } else {
                            if (i == k) {
                                builder.append(listCount[k].toInt() + 1)
                                count = listCount[k].toInt() + 1
                            } else {
                                builder.append(listCount[k])
                            }
                        }
                    }
                    table!!.listCount = builder.toString()
                    for (k in 0 until allFoods!!.size) {
                        if (allFoods!![k].id == idFood){
                            var food = allFoods!![k]
                            food.count = count
                            initFoods.add(food)
                        }
                    }

                } else {
                    for (k in 0 until allFoods!!.size) {
                        if (allFoods!![k].id == idFood){
                            var food = allFoods!![k]
                            food.count = listCount[i].toInt()
                            initFoods.add(food)
                        }
                    }
                }
            }

            initListFood(initFoods)
            //set lại tiền
            val money = txtSumMoney.text.toString().toInt()
//            val moneyFood = allFoods!![id].money.toString().toInt()
            var old = 0
            var sale = 0
            for (k in 0 until allFoods!!.size) {
                if (allFoods!![k].id == id){
                    val food = allFoods!![k]
                    old = food.money.toString().toInt()
                    sale = food.sale.toString().toInt()
                }
            }

            val moneyFood = old - old * sale / 100
            txtSumMoney.text = (money + moneyFood).toString()
            rdbTable?.tableDAO()!!.delete(idTable)
            rdbTable!!.tableDAO().insertAll(TableDinner(idTable, table?.name, table?.member!!, edtKh.text.toString().toInt(),true, table?.listFood, table?.listCount, table?.iduser!!))
            tables = rdbTable!!.tableDAO().allTable
            for (k in 0 until tables!!.size) {
                if (tables!![k].id == idTable){
                    table = tables!![k]
                }
            }
        } else if (check == "down") {
            Toast.makeText(this, "Trừ món ăn $id", Toast.LENGTH_LONG).show()
            val listFood = table!!.listFood.split(",")
            val listCount = table!!.listCount.split(",")
            var count = 0
            for (i in 0 until listFood.size) {
                val idFood = listFood[i].toInt()
                if (idFood == id) {
                    val builder = StringBuilder()
                    for (k in 0 until listCount.size) {
                        if (k < listCount.size - 1) {
                            if (i == k) {
                                builder.append(listCount!![k].toInt() - 1)
                                count = listCount!![k].toInt() - 1
                            } else {
                                builder.append(listCount[k])
                            }
                            builder.append(",")
                        } else {
                            if (i == k) {
                                builder.append(listCount!![k].toInt() -1)
                                count = listCount!![k].toInt() - 1
                            } else {
                                builder.append(listCount[k])
                            }
                        }
                    }
                    table!!.listCount = builder.toString()

                    for (k in 0 until allFoods!!.size) {
                        if (allFoods!![k].id == idFood){
                            var food = allFoods!![k]
                            food.count = count
                            initFoods.add(food)
                        }
                    }
                } else {
                    for (k in 0 until allFoods!!.size) {
                        if (allFoods!![k].id == idFood){
                            var food = allFoods!![k]
                            food.count = listCount[i].toInt()
                            initFoods.add(food)
                        }
                    }
                }
            }

            initListFood(initFoods)
            //set lại tiền
            val money = txtSumMoney.text.toString().toInt()
            var old = 0
            var sale = 0
            for (k in 0 until allFoods!!.size) {
                if (allFoods!![k].id == id){
                    val food = allFoods!![k]
                    old = food.money.toString().toInt()
                    sale = food.sale.toString().toInt()
                }
            }
            val moneyFood = old - old * sale / 100
//            val moneyFood = allFoods!![id].money.toString().toInt()
            txtSumMoney.text = (money - moneyFood).toString()
            rdbTable?.tableDAO()!!.delete(idTable)
            rdbTable!!.tableDAO().insertAll(TableDinner(idTable, table?.name, table?.member!!, edtKh.text.toString().toInt(),true, table?.listFood, table?.listCount, table?.iduser!!))
            tables = rdbTable!!.tableDAO().allTable
            for (k in 0 until tables!!.size) {
                if (tables!![k].id == idTable){
                    table = tables!![k]
                }
            }
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
            rdbTable!!.reportDAO().insertAll(Report(id, "Report $id", idTable, table!!.listFood, edtKh.text.toString().toInt(),table!!.listCount, txtSumMoney.text.toString(), time,calendar.get(Calendar.DAY_OF_MONTH).toString(),(calendar.get(Calendar.MONTH)+1).toString(),calendar.get(Calendar.YEAR).toString()))
            rdbTable!!.tableDAO().delete(idTable)
            rdbTable!!.tableDAO().insertAll(TableDinner(idTable, table!!.name, table!!.member, edtKh.text.toString().toInt(),false, "", "", table!!.iduser))

            //post data
            var stringRequest: StringRequest = object : StringRequest(Request.Method.POST,  Utils.url + "postReport.php", Response.Listener { }, Response.ErrorListener { error -> error.printStackTrace() }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["price"] = txtSumMoney.text.toString()
                    params["listFood"] = table!!.listFood
                    params["listCount"] = table!!.listCount
                    params["tableId"] = idTable.toString()
                    params["member"] = table!!.member.toString()
                    params["date"] = calendar.get(Calendar.DAY_OF_MONTH).toString()
                    params["month"] = (calendar.get(Calendar.MONTH)+1).toString()
                    params["year"] = calendar.get(Calendar.YEAR).toString()

                    return params
                }
            }

            MySingleton.getInstance(this).addTorequestque(stringRequest)

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

    private var initFoods: MutableList<Food> = mutableListOf<Food>()
    private var listFood: List<String>? = null
    private var listCount: List<String>? = null
    private var table: TableDinner? = null
    private var adapterFoodTable: AdapterFoodTable? = null
    private var adapterFood: AdapterFoodClick? = null

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
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        idTable = intent.getIntExtra("tableId", 0)
        for (k in 0 until tables!!.size){
            if (idTable == tables!![k].id){
                table = tables!![k]
                if (table!!.member!=null) {
                    edtKh.setText(table!!.member.toString())
                }else{
                    edtKh.setText(0)
                }
            }
        }

        if (table!!.listFood.toString().trim() != "") {
            listFood = table!!.listFood.split(",")
            listCount = table!!.listCount.split(",")
            if (listFood!!.isNotEmpty()) {
                for (i in 0 until listFood!!.size) {
                    val idFood = listFood!![i].toInt()
                    for (k in 0 until allFoods!!.size) {
                        if (allFoods!![k].id == idFood){
                            val food = allFoods!![k]
                            food.count = listCount!![i].toInt()
                            initFoods.add(food)
                            val money = txtSumMoney.text.toString().toInt()
                            val old = food.money.toString().toInt()
                            val sale = food.sale.toString().toInt()
                            val moneyFood = (old - old * sale / 100)* listCount!![i].toInt()
                            txtSumMoney.text = (money + moneyFood).toString()
                        }
                    }
                }
            }
        }

        txtTable.text = table!!.name.toString()
//
        initListFood(initFoods)
        adapterFood = AdapterFoodClick(this, allFoods, this)
        val managerFood = GridLayoutManager(this, 1)
        rcAddFood!!.layoutManager = managerFood as RecyclerView.LayoutManager?
        rcAddFood!!.addItemDecoration(GridSpacingItemDecoration(4, 5, true))
        rcAddFood.adapter = adapterFood
    }

    private fun initListFood(foods: MutableList<Food>) {
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
            rdbTable!!.tableDAO().updateMember(idTable, edtKh.text.toString().toInt())
        }
    }

}

