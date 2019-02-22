package com.example.admin.myapplication.view.activiti.iamge

import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.admin.myapplication.R
import com.example.admin.myapplication.controller.adapter.AdapterAlbum
import com.example.admin.myapplication.controller.adapter.AdapterAllImage
import com.example.admin.myapplication.controller.util.GridSpacingItemDecoration
import com.example.admin.myapplication.controller.util.MyPreferenceHelper
import com.example.admin.myapplication.controller.util.Utils.convertDpToPixel
import com.example.admin.myapplication.model.`object`.AlbumModel
import com.example.admin.myapplication.model.`object`.ImageModel
import kotlinx.android.synthetic.main.activity_album.*
import java.io.File
import java.util.ArrayList

class AlbumActivity : AppCompatActivity() {

    internal var showAlbumPopup: PopupWindow? = null

    internal var adapterAllImage: AdapterAllImage? = null


    internal var listAlbums = ArrayList<AlbumModel>()

    private val listSelected = HashMap<String, String>()
    private val listItemImage = HashMap<String, String>()
    private var selectImage: Int? = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        MyPreferenceHelper.setInt( MyPreferenceHelper.SELECT_POSITION,-1,this)
        setUpAlbum()
        setUpAllImage()
        toggleAlbum.setOnClickListener { toggleAlbum() }
        left_header.setOnClickListener { onLeftHeaderClick() }
    }

    fun setUpAllImage() {
        val margin = 10 //px
        val metrics = DisplayMetrics()
        window.windowManager.defaultDisplay.getMetrics(metrics)
        val widthScreen = metrics.widthPixels
        val sizeItem = (widthScreen - 5 * margin) / 4
        adapterAllImage = AdapterAllImage(this, sizeItem).setListener(object : AdapterAllImage.OnItemSelected {
            override fun count(path: String, add: Boolean) {
                if (add) {
                    if (selectImage == 0) {
                        listSelected.clear()
                            listSelected.put(path, path)
                            adapterAllImage?.checkSelect(true)

                    }
                } else {
                    listSelected.clear()
                    adapterAllImage?.checkSelect(true)
                }

                if (listSelected.size == 0) {
                    iconX.visibility = View.VISIBLE
                    clearTv.visibility = View.INVISIBLE
                } else {
                    iconX.visibility = View.INVISIBLE
                    clearTv.visibility = View.VISIBLE
                }
            }
        })

        val manager = GridLayoutManager(this, 4)
        recycleImage!!.layoutManager = manager

        recycleImage!!.addItemDecoration(GridSpacingItemDecoration(4, margin, true))
        recycleImage!!.adapter = adapterAllImage
        getAllImage()

    }

    fun onLeftHeaderClick() {
        if (listSelected.size == 0) {
            finish()
        } else {
            listSelected.clear()
            adapterAllImage?.resetSelected()
            iconX.visibility = View.VISIBLE
            clearTv.visibility = View.INVISIBLE
        }
    }

    fun toggleAlbum() {
        if (showAlbumPopup?.isShowing!!) {
            showAlbumPopup?.dismiss()
        } else {
            showAlbumPopup!!.showAsDropDown(header)
        }
    }

    private fun setUpAlbum() {
        val all = AlbumModel()
        all.id = "all"
        all.name = "All Photo"
        listAlbums.add(all)
        doGetAllAlbum()
        val view = layoutInflater.inflate(R.layout.popup_showalbum, null)
        val recycleAlbum = view.findViewById<RecyclerView>(R.id.recycleAlbum)
        val adapterAlbum = AdapterAlbum(this, listAlbums).setListener(object : AdapterAlbum.OnAlbumClick {
            override fun onAlbumSelected(bucketId: String, albumName: String) {
                showAlbumPopup?.dismiss()
                nameAlbum.text = albumName
                if (bucketId == "all") {
                    getAllImage()
                } else {
                    doGetImageInAlbum(bucketId)
                }
            }

        })
        recycleAlbum.adapter = adapterAlbum
        showAlbumPopup = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, convertDpToPixel(180f, this).toInt())
        showAlbumPopup!!.setBackgroundDrawable(ColorDrawable())
        showAlbumPopup!!.isOutsideTouchable = true
        showAlbumPopup!!.contentView = view
    }

    private fun doGetAllAlbum() {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        val ids = ArrayList<String>()
        var tmpAlbums = ArrayList<AlbumModel>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var columnIndex = cursor
                        .getColumnIndex(MediaStore.Images.Media.BUCKET_ID)
                val id = cursor.getString(columnIndex)
                if (!ids.contains(id)) {
                    val album = AlbumModel()
                    album.id = id

                    columnIndex = cursor
                            .getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                    album.name = cursor.getString(columnIndex)
                    tmpAlbums.add(album)
                    ids.add(album.id)
                } else {
                    val tmpCount = tmpAlbums[ids.indexOf(id)].count + 1
                    tmpAlbums[ids.indexOf(id)].count = tmpCount
                }
            }
            cursor.close()
        }
        for (tmpAlbum in tmpAlbums) {
            if (tmpAlbum.count > 0) {
                listAlbums.add(tmpAlbum)
            }
        }
    }

    fun getAllImage() {
        var listAllImage = ArrayList<ImageModel>()

        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uri, projection, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst()

            do {
                val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                if (path == "" || !File(path).exists()) {
                    continue
                }
                if (listSelected.containsKey(path)) {
                    listAllImage.add(ImageModel(path, true))

                } else {
                    listAllImage.add(ImageModel(path, false))
                }


            } while (cursor.moveToNext())
            cursor.close()
        }
        adapterAllImage?.clearAndAdd(listAllImage)
    }

    override fun onResume() {
        super.onResume()

    }

    fun doGetImageInAlbum(bucketId: String) {
        var listModel = ArrayList<ImageModel>()
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val selection = MediaStore.Images.Media.BUCKET_ID + " = ?"
        val selectionArgs = arrayOf(bucketId)
        val orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                orderBy)
        if (cursor!!.moveToFirst()) {
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            do {
                val path = cursor.getString(dataColumn)
                if (listSelected.containsKey(path)) {
                    listModel.add(ImageModel(path, true))
                } else {
                    listModel.add(ImageModel(path, false))
                }

            } while (cursor.moveToNext())
        }
        cursor.close()
        adapterAllImage?.clearAndAdd(listModel)
    }

}
