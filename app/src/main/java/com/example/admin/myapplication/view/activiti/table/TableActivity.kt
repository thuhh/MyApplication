package com.example.admin.myapplication.view.activiti.table

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterTable
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.controller.interfaces.ItemTableClick
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.controller.util.Utils
import com.example.admin.myapplication.model.`object`.Report
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.activiti.LoginActivity
import com.example.admin.myapplication.view.dialog.DialogAddTable
import kotlinx.android.synthetic.main.activity_table.*
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class TableActivity : AppCompatActivity(), IClickDialog, View.OnClickListener, ItemTableClick {
    override fun iClick(check: String?, id: Int) {
        if (check == "detail") {
            startActivity(Intent(this, DetailTableActivity::class.java).putExtra("tableId", id))
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd) {
            MyPreferenceHelper.setString(this, MyPreferenceHelper.DialogFood, "yes")
            dialogAddTable!!.show()
        } else if (v?.id == R.id.btnSearch) {
            lnSearch.visibility = View.VISIBLE
            search()
        } else if (v?.id == R.id.btnKhac) {
            startActivity(Intent(this@TableActivity, LoginActivity::class.java).putExtra("menu", 1))
            finish()
        }
    }

    override fun onclick(check: String?) {
        if (check == "save") {
            tables = rdbTable!!.tableDAO().allTable
            initListItem()
        }
    }

    private fun search() {
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterTable!!.filter.filter(charSequence)

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    private var rdbTable: RDBApp? = null
    private var tables: List<TableDinner>? = null
    private var adapterTable: AdapterTable? = null
    private var dialogAddTable: DialogAddTable? = null

    internal var dulieu: StringBuilder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        try {
            rdbTable = RDBApp.getAppDatabase(this)
            loadTable(MyPreferenceHelper.getInt(MyPreferenceHelper.idUser, this))
            tables = rdbTable!!.tableDAO().allTable
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        dialogAddTable = DialogAddTable(this)
        dialogAddTable!!.setClick(this)

        initListener()

        if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood, this) != null) {
            if (MyPreferenceHelper.getString(MyPreferenceHelper.DialogFood, this) == "yes") {
                dialogAddTable!!.show()
            }
        }

        initListItem()
    }

    fun loadTable(userId: Int) {
        if (!MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.firstTable, this)) {
            //get du lieu
            val url = Utils.url + "getTable.php"
            val downloadJSON = DownloadJSON()
            downloadJSON.execute(url)
            MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.firstTable, true, this)
        }

    }

    private fun initListItem() {
        Collections.reverse(tables)
        adapterTable = AdapterTable(this@TableActivity, tables, this)
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvTable!!.layoutManager = manager
        rvTable.adapter = adapterTable
    }

    private fun initListener() {
        btnAdd.setOnClickListener(this)
        btnSearch.setOnClickListener(this)
        btnKhac.setOnClickListener(this)
    }

    inner class DownloadJSON : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String): String {
            try {
                val url = URL(Utils.url + "getTable.php")
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                val bufferedReader = BufferedReader(inputStreamReader)
                dulieu = StringBuilder()
                val text: List<String> = bufferedReader.readLines()
                for (line in text) {
                    dulieu!!.append(line)
                }

                var jsonarray: JSONArray? = null
                try {
                    jsonarray = JSONArray(dulieu.toString())
                    for (i in 0 until jsonarray!!.length()) {
                        val jsonobject = jsonarray.getJSONObject(i)
                        val name = jsonobject.getString("table_name")
                        val id = jsonobject.getInt("table_id")
                        val member = jsonobject.getInt("table_member")
                        rdbTable!!.tableDAO().insertAll(TableDinner(id, name, member, false, "", "", 1))
                    }
                    tables = rdbTable!!.tableDAO().allTable
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
            initListItem()
        }
    }
}

