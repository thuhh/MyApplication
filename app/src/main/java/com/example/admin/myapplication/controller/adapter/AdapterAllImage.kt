package com.example.admin.myapplication.controller.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.util.ImageViewForListImage
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.model.`object`.ImageModel
import com.example.admin.myapplication.view.activiti.food.FoodActivity
import java.util.*

class AdapterAllImage(private val context: Context, private val width: Int) : RecyclerView.Adapter<AdapterAllImage.ImageHolder>() {
    private val listData = ArrayList<ImageModel>()
    private val listSelected = HashMap<String, String>()
    private var canSelect = true

    private var listener: OnItemSelected? = null

    fun setListener(listener: OnItemSelected): AdapterAllImage {
        this.listener = listener
        return this
    }

    interface OnItemSelected {
        fun count(path: String, add: Boolean)
    }

    public fun clearAndAdd(listModel: ArrayList<ImageModel>) {
        listData.clear()
        this.listData.addAll(listModel)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = ImageHolder(LayoutInflater.from(context).inflate(R.layout.item_image_holder, parent, false))
        val params = RecyclerView.LayoutParams(width, width)
        view.itemView.layoutParams = params
        return view
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val data= listData[position]
        Glide.with(context).load(data!!.path).into(holder.imageThumb)
        if (data!!.isSelected) {
            holder.viewSelected.visibility = View.VISIBLE
        } else {
            holder.viewSelected.visibility = View.INVISIBLE
            if(MyPreferenceHelper.getInt(MyPreferenceHelper.SELECT_POSITION,context)!=-1){
                val data1 = listData[MyPreferenceHelper.getInt(MyPreferenceHelper.SELECT_POSITION,context)]
                if (position==MyPreferenceHelper.getInt(MyPreferenceHelper.SELECT_POSITION,context)) {
                    if (!data1.isSelected) {
                        holder.viewSelected.visibility = View.VISIBLE
                    }
                }
            }
        }
        holder.itemView.setOnClickListener {
            if (this!!.canSelect!!) {
                if (!data.isSelected) {
                    for(i in 0 until listData.size){
                        holder.viewSelected.visibility = View.GONE
                        listData[i].isSelected= false
                        notifyDataSetChanged()
                    }
                    if (listener != null){
                        if(MyPreferenceHelper.getInt(MyPreferenceHelper.SELECT_POSITION,context)!=-1){
                            val data1 = listData[MyPreferenceHelper.getInt(MyPreferenceHelper.SELECT_POSITION,context)]
                            if(data1.isSelected){
                                holder.viewSelected.visibility = View.GONE
                            }
                        }
                    }
                    listSelected.put(data.path,data.path)
                    holder.viewSelected.visibility = View.VISIBLE
                        val intent1 = Intent(context, FoodActivity::class.java)
                        MyPreferenceHelper.setString(context, MyPreferenceHelper.SELECT_IMAGE,data.path)
                        MyPreferenceHelper.setInt( MyPreferenceHelper.SELECT_POSITION,position,context)
                        context.startActivity(intent1)
                } else {
                    if (data.isSelected) {
                        val intent1 = Intent(context, FoodActivity::class.java)
                        MyPreferenceHelper.setInt( MyPreferenceHelper.SELECT_POSITION,position,context)
                        context.startActivity(intent1)
                    }
                }
            } else {
                if (data.isSelected) {
                    if (listener != null) listener?.count(data.path, false)
                    holder.viewSelected.visibility = View.INVISIBLE
                }
            }
        }
    }

    public fun checkSelect(canSelect: Boolean) {
        this.canSelect = canSelect
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun resetSelected() {
        for (model in listData) {
            if (model.isSelected) model.isSelected = false
        }
        notifyDataSetChanged()
    }

    inner class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var imageThumb: ImageViewForListImage
        internal var viewSelected: FrameLayout
        internal var parentView: FrameLayout

        init {
            imageThumb = itemView.findViewById(R.id.imageThumb)
            viewSelected = itemView.findViewById(R.id.viewSelected)
            parentView = itemView.findViewById(R.id.parentView)
        }
    }
}
