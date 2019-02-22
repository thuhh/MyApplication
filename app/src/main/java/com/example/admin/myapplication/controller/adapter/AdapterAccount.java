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

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.controller.interfaces.ItemTableClick;
import com.example.admin.myapplication.model.object.Food;

import java.util.ArrayList;
import java.util.List;

public class AdapterAccount extends RecyclerView.Adapter<AdapterAccount.Viewholor> {
    private ItemTableClick iOnClickSetColor;
    private List<Food> list;
    private List<Food> filter;
    private List<Food> items;
    private Context context;


    public AdapterAccount(ItemTableClick iOnClickSetColor, List<Food> list, Context context) {
        this.iOnClickSetColor = iOnClickSetColor;
        this.list = list;
        this.items = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new Viewholor(view);
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull Viewholor holder, final int position) {
        holder.txtName.setText(list.get(position).getName());
        holder.txtMoney.setText(list.get(position).getMoney());
        if (list.get(position).isNewFood()) {
            holder.imgNew.setVisibility(View.VISIBLE);
        }else {
            holder.imgNew.setVisibility(View.GONE);
        }
        holder.ctFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSetColor.iClick("click",position);
//                context.startActivity(new Intent(context,DetailFoodActivity.class).putExtra("id",list.get(position).getId()));
            }
        });
        Glide.with(context)
                .asBitmap()
                .load(list.get(position).getImage())
                .into(holder.imgFood);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholor extends RecyclerView.ViewHolder {
        ImageView imgFood, imgNew;
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
                        for (final Food g : filter) {
                            Log.e("sdsd56",g.getName());
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
}
