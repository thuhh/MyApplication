package com.example.admin.myapplication.controller.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.controller.interfaces.ItemTableClick;
import com.example.admin.myapplication.model.itemEffect.EasySwipeMenuLayout;
import com.example.admin.myapplication.model.object.Food;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseQuickAdapter<Food, BaseViewHolder> {

    private ItemTableClick itemCLick;
    private List<Food> list;
    private List<Food> filter;
    private List<Food> items;
    private Context context;

    public MyAdapter(@LayoutRes int layoutResId, @Nullable List<Food> data, Context context, ItemTableClick itemTableClick) {
        super(layoutResId, data);
        this.context = context;
        this.itemCLick = itemTableClick;
        this.items = data;
        this.list = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Food item) {
        final EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);

        TextView txtName = helper.getView(R.id.txtName);
        TextView txtMoney = helper.getView(R.id.txtMoney);
        helper.getView(R.id.right_menu_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show();
                itemCLick.iClick("delete",item.getId());
                easySwipeMenuLayout.resetStatus();
            }
        });
        helper.getView(R.id.right_menu_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "edit", Toast.LENGTH_SHORT).show();
                itemCLick.iClick("edit",item.getId());
                easySwipeMenuLayout.resetStatus();
            }
        });
        helper.getView(R.id.right_menu_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "like", Toast.LENGTH_SHORT).show();
                itemCLick.iClick("like",item.getId());
                easySwipeMenuLayout.resetStatus();
            }
        });
        helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCLick.iClick("click",item.getId());
                easySwipeMenuLayout.resetStatus();

            }
        });
        txtName.setText(item.getName());
        txtMoney.setText(item.getMoney()+"K");
        ImageView imgNew = helper.getView(R.id.imgNew);
        if (item.isNewFood()) {
            imgNew.setVisibility(View.VISIBLE);
        }else {
            imgNew.setVisibility(View.GONE);
        }

        ImageView imageView = helper.getView(R.id.imgFood);
        String image = item.getImage();
        if (image !=null && image != ""){
            if (image.equals("R.drawable.food1")) {
                Glide.with(context).asBitmap().load(R.drawable.food1).into(imageView);
            }else if (image.equals("R.drawable.food11")) {
                Glide.with(context).asBitmap().load(R.drawable.food11).into(imageView);
            }else if (image.equals("R.drawable.food2")) {
                Glide.with(context).asBitmap().load(R.drawable.food2).into(imageView);
            }else if (image.equals("R.drawable.food3")) {
                Glide.with(context).asBitmap().load(R.drawable.food3).into(imageView);
            }else if (image.equals("R.drawable.food4")) {
                Glide.with(context).asBitmap().load(R.drawable.food4).into(imageView);
            }else if (image.equals("R.drawable.food5")) {
                Glide.with(context).asBitmap().load(R.drawable.food5).into(imageView);
            }else if (image.equals("R.drawable.food6")) {
                Glide.with(context).asBitmap().load(R.drawable.food6).into(imageView);
            }else if (image.equals("R.drawable.food7")) {
                Glide.with(context).asBitmap().load(R.drawable.food7).into(imageView);
            }else if (image.equals("R.drawable.food8")) {
                Glide.with(context).asBitmap().load(R.drawable.food8).into(imageView);
            }else if (image.equals("R.drawable.food9")) {
                Glide.with(context).asBitmap().load(R.drawable.food9).into(imageView);
            }else if (image.equals("R.drawable.food10")) {
                Glide.with(context).asBitmap().load(R.drawable.food10).into(imageView);
            }else if (image.equals("R.drawable.food12")) {
                Glide.with(context).asBitmap().load(R.drawable.food12).into(imageView);
            }else if (image.equals("R.drawable.food13")) {
                Glide.with(context).asBitmap().load(R.drawable.food13).into(imageView);
            }else if (image.equals("R.drawable.food14")) {
                Glide.with(context).asBitmap().load(R.drawable.food14).into(imageView);
            }else {
                Glide.with(context).asBitmap().load(image).into(imageView);
            }
        }
    }

    @NonNull
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Food> results = new ArrayList<>();
                if (filter == null)
                    filter = items;
                if (constraint != null) {
                    if (filter != null && filter.size() > 0) {
                        for (final Food g : filter) {
                            if (g.getName().toLowerCase().contains(constraint.toString()) ||
                                    g.getType().toLowerCase().contains(constraint.toString())) {
                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<Food>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
