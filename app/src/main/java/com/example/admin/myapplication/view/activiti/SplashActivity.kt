package com.example.admin.myapplication.view.activiti

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.controller.util.Utils
import com.example.admin.myapplication.model.`object`.Food
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.`object`.User
import com.example.admin.myapplication.model.database.RDBApp
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class SplashActivity : AppCompatActivity() {

    internal var dulieu1: StringBuilder? = null
    internal var dulieu2: StringBuilder? = null
    internal var dulieu3: StringBuilder? = null
    internal var dulieu4: StringBuilder? = null
    internal var rdbApp: RDBApp? = null
    internal var users: List<User>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        try {
            rdbApp = RDBApp.getAppDatabase(this)
            users = rdbApp!!.userDAO().allUser
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        if (!MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.firstUser, this)) {
            val downloadJSON = DownloadJSON()
            downloadJSON.execute(Utils.url + "getUser.php")
            MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.firstUser, true, this)
        }else{
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }


    inner class DownloadJSON : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String): String {
            try {
                val url = URL(strings[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                val bufferedReader = BufferedReader(inputStreamReader)
                dulieu1 = StringBuilder()
                val text:List<String> = bufferedReader.readLines()
                for(line in text){
                    dulieu1!!.append(line)
                }

                var jsonarray: JSONArray? = null
                try {
                    jsonarray = JSONArray(dulieu1.toString())
                    for (i in 0 until jsonarray!!.length()) {
                        val jsonobject = jsonarray.getJSONObject(i)
                        val name = jsonobject.getString("user_name")
                        val id = jsonobject.getInt("user_id")
                        val email = jsonobject.getString("user_email")
                        val pass = jsonobject.getString("user_password")
                        val pincode = jsonobject.getString("user_pincode")
                        val lever = jsonobject.getInt("user_lever")

                        rdbApp!!.userDAO().insertAll(User(id,name,pass,email,pincode,lever))
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return dulieu1.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val downloadJSONEnd = DownloadJSONFood()
            downloadJSONEnd.execute(Utils.url + "getFood.php")
        }
    }

    inner class DownloadJSONFood : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String): String {
            try {
                val url = URL(Utils.url + "getFood.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                val bufferedReader = BufferedReader(inputStreamReader)
                dulieu2 = StringBuilder()
                val text:List<String> = bufferedReader.readLines()
                for(line in text){
                    dulieu2!!.append(line)
                }

                var jsonarray: JSONArray? = null
                try {
                    jsonarray = JSONArray(dulieu2.toString())
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

                        rdbApp!!.foodDAO().insertAll(Food(id,name,food_type.toString(),price.toString(),true,image,materials.toString(),sale.toString(),rate,0,des,type.toString(),1))

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return dulieu2.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val downloadJSONEnd = DownloadJSONTable()
            downloadJSONEnd.execute(Utils.url + "getTable.php")
        }
    }

    inner class DownloadJSONTable : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String): String {
            try {
                val url = URL(Utils.url + "getTable.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                val bufferedReader = BufferedReader(inputStreamReader)
                dulieu3 = StringBuilder()
                val text: List<String> = bufferedReader.readLines()
                for (line in text) {
                    dulieu3!!.append(line)
                }

                var jsonarray: JSONArray? = null
                try {
                    jsonarray = JSONArray(dulieu3.toString())
                    for (i in 0 until jsonarray!!.length()) {
                        val jsonobject = jsonarray.getJSONObject(i)
                        val name = jsonobject.getString("table_name")
                        val id = jsonobject.getInt("table_id")
                        val member = jsonobject.getInt("table_member")
                        rdbApp!!.tableDAO().insertAll(TableDinner(id, name, member, 0,false, "", "", 1))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return dulieu3.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val downloadJSONEnd = DownloadJSONEnd()
            downloadJSONEnd.execute(Utils.url + "getReport.php")
        }
    }

    inner class DownloadJSONEnd : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String): String {
            try {
                val url = URL(Utils.url  + "getReport.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                val bufferedReader = BufferedReader(inputStreamReader)
                dulieu4 = StringBuilder()
                val text:List<String> = bufferedReader.readLines()
                for(line in text){
                    dulieu4!!.append(line)
                }

                var jsonarray: JSONArray? = null
                try {
                    jsonarray = JSONArray(dulieu4.toString())
                    for (i in 0 until jsonarray!!.length()) {
                        val jsonobject = jsonarray.getJSONObject(i)
                        val id = jsonobject.getInt("report_id")
                        val price = jsonobject.getInt("report_price")
                        val member = jsonobject.getInt("report_member")
                        val table = jsonobject.getInt("report_table")
                        val listFood = jsonobject.getString("report_list")
                        val listCount = jsonobject.getString("report_count")
                        val day = jsonobject.getInt("report_date")
                        val month = jsonobject.getInt("report_month")
                        val year = jsonobject.getInt("report_year")
//                        val time = jsonobject.getString("create_at")

                        rdbApp!!.reportDAO().insertAll(Report(id, "table$id",table,listFood,member,listCount, price.toString(),"12/12/2019",day.toString(),month.toString(),year.toString()))
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return dulieu4.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }
}
