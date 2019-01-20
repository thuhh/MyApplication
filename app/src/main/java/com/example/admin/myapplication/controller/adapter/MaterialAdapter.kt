package com.example.admin.myapplication.controller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.admin.myapplication.R
import com.example.admin.myapplication.model.`object`.Material
import com.example.admin.myapplication.model.database.RDBApp


class MaterialAdapter(internal var context: Context, private val listStorage: List<Material>) : BaseAdapter() {
    private var layoutInflater: LayoutInflater?= null
    private lateinit var rdbMaterial: RDBApp
    private lateinit var listMaterial: List<Material>

    private var isTouched: Boolean? = false

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }


    override fun getCount(): Int {
        return listStorage.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun getView(position: Int, convertVieww: View?, parent: ViewGroup): View? {
        var convertView = convertVieww
        try {
            val listViewHolder: ViewHolder
            if (convertView == null) {
                listViewHolder = ViewHolder()
                convertView = layoutInflater!!.inflate(R.layout.item_material, parent, false)
                rdbMaterial = RDBApp.getAppDatabase(context)
                listMaterial = rdbMaterial.materialDAO().allApp

                listViewHolder.imgPicture = convertView!!.findViewById(R.id.imgPicture)
                listViewHolder.txtName = convertView.findViewById(R.id.txtName)
                listViewHolder.txtType = convertView.findViewById(R.id.txtType)
                listViewHolder.txtAddress = convertView.findViewById(R.id.txtAddress)
                listViewHolder.txtDate = convertView.findViewById(R.id.txtDate)
                convertView.tag = listViewHolder
            } else {
                listViewHolder = convertView.tag as ViewHolder
            }

            listViewHolder.imgPicture!!.setImageBitmap( BitmapFactory.decodeByteArray(listMaterial.get(position).image, 0, listMaterial.get(position).image.size))
            listViewHolder.txtName!!.text = listMaterial.get(position).name
            listViewHolder.txtType!!.text = listMaterial.get(position).type
            listViewHolder.txtAddress!!.text = listMaterial.get(position).adrress
            listViewHolder.txtDate!!.text = listMaterial.get(position).timeBuy

            return convertView
        } catch (e: Exception) {

        }

        return convertView
    }

    internal class ViewHolder {
        var imgPicture : ImageView ?= null
        var txtName : TextView ?= null
        var txtType : TextView ?= null
        var txtAddress : TextView ?= null
        var txtDate : TextView ?= null

    }
}
