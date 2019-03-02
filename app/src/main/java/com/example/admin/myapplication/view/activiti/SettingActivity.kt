package com.example.admin.myapplication.view.activiti

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.admin.myapplication.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.btnBack){
            finish();
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        btnBack.setOnClickListener(this)
    }
}
