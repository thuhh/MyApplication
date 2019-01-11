package com.example.admin.myapplication.controller.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.admin.myapplication.R
import com.example.admin.myapplication.model.`object`.AlbumModel

class AdapterAlbum(private var context: Context, private var listAlbum: ArrayList<AlbumModel>) : RecyclerView.Adapter<AlbumHolder>() {

    private var listener: OnAlbumClick? = null

    fun setListener(listener: OnAlbumClick): AdapterAlbum {
        this.listener = listener;
        return this
    }

    interface OnAlbumClick {
        fun onAlbumSelected(bucketId: String, albumName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        return AlbumHolder(LayoutInflater.from(context).inflate(R.layout.item_album_holder, parent, false))
    }

    override fun getItemCount(): Int {
        return listAlbum.size
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.name?.text = listAlbum.get(position).name
        holder.itemView.setOnClickListener {
            if (listener != null) {
                listener?.onAlbumSelected(listAlbum.get(position).id, listAlbum.get(position).name)
            }
        }
    }

}

class AlbumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var name: TextView? = null

    init {
        name = itemView.findViewById(R.id.albumName)

    }

}