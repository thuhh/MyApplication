package com.example.admin.myapplication.controller.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.model.itemEffect.EasySwipeMenuLayout;
import com.example.admin.myapplication.model.object.Food;

import java.util.List;

public class MyAdapter extends BaseQuickAdapter<Food, BaseViewHolder> {
    public MyAdapter(@LayoutRes int layoutResId, @Nullable List<Food> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, Food item) {
        final EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);

        helper.getView(R.id.right_menu_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show();
                easySwipeMenuLayout.resetStatus();
            }
        });
        helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "setOnClickListener", Toast.LENGTH_SHORT).show();
                easySwipeMenuLayout.resetStatus();

            }
        });

    }

}
