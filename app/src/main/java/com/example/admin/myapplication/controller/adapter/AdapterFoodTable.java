package com.example.admin.myapplication.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.controller.interfaces.ItemTableClick;
import com.example.admin.myapplication.model.object.FoodTable;

import java.util.ArrayList;
import java.util.List;

public class AdapterFoodTable extends RecyclerView.Adapter<AdapterFoodTable.Viewholor> {
    private ItemTableClick iClick;
    private List<FoodTable> list;
    private List<FoodTable> filter;
    private List<FoodTable> items;
    private Context context;
    int count;

    public AdapterFoodTable(ItemTableClick iClick, List<FoodTable> list, Context context) {
        this.iClick = iClick;
        this.list = list;
        this.items = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_table, parent, false);
        return new Viewholor(view);
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final Viewholor holder, final int position) {
        holder.txtName.setText(list.get(position).getName());
        holder.txtMoney.setText(list.get(position).getMoney());
        holder.txtCount.setText(String.valueOf(list.get(position).getCount()));
        if (list.get(position).isNewFood()) {
            holder.imgNew.setVisibility(View.VISIBLE);
        } else {
            holder.imgNew.setVisibility(View.GONE);
        }

        String image = list.get(position).getImage();
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

        count = Integer.parseInt(holder.txtCount.getText().toString());
        holder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    count--;
                    holder.txtCount.setText(count+"");
                    iClick.iClick("down",list.get(position).getId());
                } else {
                    Toast.makeText(context, "Số lượng phải lớn hơn 1", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                holder.txtCount.setText(count+"");
                iClick.iClick("plus",list.get(position).getId());
            }
        });
        holder.imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClick.iClick("delete",list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholor extends RecyclerView.ViewHolder {
        ImageView imgFood, imgNew, imgCancel;
        TextView txtName;
        TextView txtMoney;
        TextView txtCount;
        ConstraintLayout ctFood;
        Button btnDown, btnUp;

        public Viewholor(View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            imgNew = itemView.findViewById(R.id.imgNew);
            imgCancel = itemView.findViewById(R.id.imgCancel);
            txtName = itemView.findViewById(R.id.txtName);
            ctFood = itemView.findViewById(R.id.ctFood);
            txtMoney = itemView.findViewById(R.id.txtMoney);
            txtCount = itemView.findViewById(R.id.txtCount);
            btnDown = itemView.findViewById(R.id.btnDown);
            btnUp = itemView.findViewById(R.id.btnUp);
        }
    }

    @NonNull
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<FoodTable> results = new ArrayList<>();
                if (filter == null)
                    filter = items;
                if (constraint != null) {
                    if (filter != null && filter.size() > 0) {
                        for (final FoodTable g : filter) {
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
                items = (List<FoodTable>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
