package com.example.admin.myapplication.view.activiti

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.interfaces.IClickDialog
import com.example.admin.myapplication.model.`object`.TableDinner
import com.example.admin.myapplication.model.database.RDBApp
import com.example.admin.myapplication.view.dialog.DialogListFood
import kotlinx.android.synthetic.main.activity_detail_table.*

class DetailTableActivity : AppCompatActivity(),View.OnClickListener, IClickDialog {
    override fun onclick(check: String?) {

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAddFood){
            dialogListFood!!.show()
        }else if (v?.id == R.id.btnReset){

        }
    }

    private var rdbTable: RDBApp? = null;
    private var tables: List<TableDinner>? = null
    private var id = 0;
    private var dialogListFood: DialogListFood ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_table)
        dialogListFood = DialogListFood(this)
        if (intent != null) {
            id = intent.getIntExtra("id", 0)
        }
        try {
            rdbTable = RDBApp.getAppDatabase(this)
            tables = rdbTable!!.tableDAO().allTable
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        for (i in 0 until tables!!.size) {
            if (tables!!.get(i).id == id) {
                txtTable.setText(tables!!.get(i).name)


//                Glide.with(this)
//                        .asBitmap()
//                        .load(foods!!.get(i).getImage())
//                        .into(imgFood)
            }
        }

        btnAddFood.setOnClickListener(this)
        btnReset.setOnClickListener(this)
    }
}
