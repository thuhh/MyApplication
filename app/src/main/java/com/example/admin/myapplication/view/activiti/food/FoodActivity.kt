package com.example.admin.myapplication.view.activiti.food

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterFood
import com.example.admin.myapplication.controller.adapter.MyAdapter
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.controller.util.Utils
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.LoginActivity
import com.example.admin.myapplication.view.dialog.DialogAddFood
import kotlinx.android.synthetic.main.activity_food.*
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class FoodActivity : AppCompatActivity(), View.OnClickListener, IClickDialog, ItemTableClick {
    override fun iClick(check: String?, id: Int) {
        if (check=="click") {
            startActivity(Intent(this, DetailFoodActivity::class.java).putExtra("foodId", id))
        }else if (check=="edit"){
            MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.checkEdit,true,this)
            MyPreferenceHelper.setInt(MyPreferenceHelper.clickItem,id,this)
            dialogAddFood!!.show()
        }else if (check=="delete"){
            rdbFood!!.foodDAO().delete(id)
            foods = rdbFood!!.foodDAO().allFood
            initList()
        }else if (check=="like"){
            foods = rdbFood!!.foodDAO().allFood
            initList()
        }
    }

    override fun onclick(check: String?) {
            foods = rdbFood!!.foodDAO().allFood
            initList()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd){
            MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"yes")
            dialogAddFood!!.show()
        }
        else if (v?.id == R.id.btnKhac){
            startActivity(Intent(this@FoodActivity, LoginActivity::class.java).putExtra("menu",1))
            finish()
        }else if (v?.id == R.id.btnSearch){
            lnSearch.visibility = View.VISIBLE
            search()
        }
    }

    private fun search(){
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                myAdapter!!.filter.filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    private var rdbFood : RDBApp? =null
    private var foods: List<Food> ? =null
    private var adapterFood: AdapterFood? = null
    var myAdapter: MyAdapter ?= null
    private var dialogAddFood : DialogAddFood ?= null

    internal var dulieu: StringBuilder? = null
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        if (!MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.firstData, this)) {
            //get du lieu
            val url = Utils.url + "getFood.php"
            val downloadJSON = DownloadJSON()
            downloadJSON.execute(url)
            MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.firstData, true, this)
        }
        dialogAddFood = DialogAddFood(this)
        dialogAddFood!!.setClick(this)
        initListener()
    }

    override fun onResume() {
        super.onResume()
        try {
            rdbFood = RDBApp.getAppDatabase(this)
            foods = rdbFood!!.foodDAO().allFood
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }

        if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this)!=null){
            if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood,this) == "yes"){
                dialogAddFood!!.show()
            }
        }
        initList()
    }

    private fun initList() {
        Collections.reverse(foods)
        adapterFood = AdapterFood(this@FoodActivity, foods,this)

        val manager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvFood!!.layoutManager = manager

//        rvFood.adapter = adapterFood

        //test
        myAdapter = MyAdapter(R.layout.item_food_swipemenu, foods,this,this)
        rvFood.adapter = myAdapter
    }

    private fun initListener() {
        btnAdd.setOnClickListener(this)
        btnKhac.setOnClickListener(this)
        btnSearch.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyPreferenceHelper.setString(this,MyPreferenceHelper.DialogFood,"no")
    }

    inner class DownloadJSON : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String): String {
            try {
                val url = URL(Utils.url + "getFood.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                val bufferedReader = BufferedReader(inputStreamReader)
                dulieu = StringBuilder()
                val text:List<String> = bufferedReader.readLines()
                for(line in text){
                    dulieu!!.append(line)
                }

                var jsonarray: JSONArray? = null
                try {
                    jsonarray = JSONArray(dulieu.toString())
                    for (i in 0 until jsonarray!!.length()) {
                        val jsonobject = jsonarray.getJSONObject(i)
                        val name = jsonobject.getString("food_name")
                        val id = jsonobject.getInt("food_id")
                        val image = Utils.url + jsonobject.getString("food_image")
                        val foodNew = jsonobject.getInt("food_new")
                        val price = jsonobject.getInt("food_price")
                        val sale = jsonobject.getInt("food_sale")
                        val rate = jsonobject.getInt("food_rate")
                        val des = jsonobject.getString("food_des")
                        val materials = jsonobject.getInt("food_material")
                        val food_type = jsonobject.getInt("food_type")
                        val type = jsonobject.getInt("type")

                        rdbFood!!.foodDAO().insertAll(Food(id,name,food_type.toString(),price.toString(),true,image,materials.toString(),sale.toString(),rate,0,des,type.toString(),1))
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return dulieu.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            foods = rdbFood!!.foodDAO().allFood
            initList()
        }
    }
}
