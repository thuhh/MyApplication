package com.example.admin.myapplication.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.controller.interfaces.ItemTableClick;
import com.example.admin.myapplication.model.object.Food;

import java.util.ArrayList;
import java.util.List;

public class AdapterFoodClick extends RecyclerView.Adapter<AdapterFoodClick.Viewholor> {
    private ItemTableClick iOnClickSetColor;
    private List<Food> filter;
    private List<Food> items;
    private Context context;


    public AdapterFoodClick(ItemTableClick iOnClickSetColor, List<Food> list, Context context) {
        this.iOnClickSetColor = iOnClickSetColor;
        this.filter = list;
        this.items = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_click, parent, false);
        return new Viewholor(view);
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull Viewholor holder, final int position) {
        holder.txtName.setText(items.get(position).getName());
        holder.txtMoney.setText(items.get(position).getMoney()+"K");
        if (items.get(position).isNewFood()) {
            holder.imgNew.setVisibility(View.VISIBLE);
        }else {
            holder.imgNew.setVisibility(View.GONE);
        }
        holder.ctFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSetColor.iClick("click",items.get(position).getId());
            }
        });
        String image = items.get(position).getImage();
        if (image !=null && image != ""){
            if (image.equals("R.drawable.food1")) {
                Glide.with(context).asBitmap().load(R.drawable.food1).into(holder.imgFood);
            }else if (image.equals("R.drawable.food11")) {
                Glide.with(context).asBitmap().load(R.drawable.food11).into(holder.imgFood);
            }else if (image.equals("R.drawable.food2")) {
                Glide.with(context).asBitmap().load(R.drawable.food2).into(holder.imgFood);
            }else if (image.equals("R.drawable.food3")) {
                Glide.with(context).asBitmap().load(R.drawable.food3).into(holder.imgFood);
            }else if (image.equals("R.drawable.food4")) {
                Glide.with(context).asBitmap().load(R.drawable.food4).into(holder.imgFood);
            }else if (image.equals("R.drawable.food5")) {
                Glide.with(context).asBitmap().load(R.drawable.food5).into(holder.imgFood);
            }else if (image.equals("R.drawable.food6")) {
                Glide.with(context).asBitmap().load(R.drawable.food6).into(holder.imgFood);
            }else if (image.equals("R.drawable.food7")) {
                Glide.with(context).asBitmap().load(R.drawable.food7).into(holder.imgFood);
            }else if (image.equals("R.drawable.food8")) {
                Glide.with(context).asBitmap().load(R.drawable.food8).into(holder.imgFood);
            }else if (image.equals("R.drawable.food9")) {
                Glide.with(context).asBitmap().load(R.drawable.food9).into(holder.imgFood);
            }else if (image.equals("R.drawable.food10")) {
                Glide.with(context).asBitmap().load(R.drawable.food10).into(holder.imgFood);
            }else if (image.equals("R.drawable.food12")) {
                Glide.with(context).asBitmap().load(R.drawable.food12).into(holder.imgFood);
            }else if (image.equals("R.drawable.food13")) {
                Glide.with(context).asBitmap().load(R.drawable.food13).into(holder.imgFood);
            }else if (image.equals("R.drawable.food14")) {
                Glide.with(context).asBitmap().load(R.drawable.food14).into(holder.imgFood);
            }else {
                Glide.with(context).asBitmap().load(image).into(holder.imgFood);
            }
        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholor extends RecyclerView.ViewHolder {
        ImageView imgFood;
        ImageView imgNew;
        TextView txtName;
        TextView txtMoney;
        ConstraintLayout ctFood;

        public Viewholor(View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            imgNew = itemView.findViewById(R.id.imgNew);
            txtName = itemView.findViewById(R.id.txtName);
            ctFood = itemView.findViewById(R.id.ctFood);
            txtMoney = itemView.findViewById(R.id.txtMoney);
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
                        for (Food g : filter) {
                            if (g.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                results.add(g);
                                Log.e("sdsdsdsd",g.getName());
                            }
                        }
                    }
                    oReturn.values = results;
                    oReturn.count = results.size();
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<Food>) results.values;
                notifyDataSetChanged();
                Toast.makeText(context,"số món tìm kiếm được: "+ items.size(),Toast.LENGTH_LONG).show();
            }
        };
    }
}
