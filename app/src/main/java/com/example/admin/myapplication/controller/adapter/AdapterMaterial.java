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
import com.example.admin.myapplication.model.object.Material;

import java.util.ArrayList;
import java.util.List;

public class AdapterMaterial extends RecyclerView.Adapter<AdapterMaterial.Viewholor> {
    private ItemTableClick iOnClickSetColor;
    private List<Material> list;
    private List<Material> filter;
    private List<Material> items;
    private Context context;


    public AdapterMaterial(ItemTableClick iOnClickSetColor, List<Material> list, Context context) {
        this.iOnClickSetColor = iOnClickSetColor;
        this.list = list;
        this.items = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material, parent, false);
        return new Viewholor(view);
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull Viewholor holder, final int position) {
        holder.txtName.setText(list.get(position).getName());
        holder.txtAddress.setText(list.get(position).getAdrress());
        holder.txtType.setText(list.get(position).getType());
        holder.txtDate.setText(list.get(position).getTimeBuy());

        holder.ctFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSetColor.iClick("click",list.get(position).getId());
            }
        });

        String image = list.get(position).getImage();
        if (image !=null && image != "") {
            if (image.equals("R.drawable.material1")) {
                Glide.with(context).asBitmap().load(R.drawable.material1).into(holder.imgFood);
            } else if (image.equals("R.drawable.material2")) {
                Glide.with(context).asBitmap().load(R.drawable.material2).into(holder.imgFood);
            } else if (image.equals("R.drawable.material3")) {
                Glide.with(context).asBitmap().load(R.drawable.material3).into(holder.imgFood);
            } else if (image.equals("R.drawable.material4")) {
                Glide.with(context).asBitmap().load(R.drawable.material4).into(holder.imgFood);
            } else if (image.equals("R.drawable.material5")) {
                Glide.with(context).asBitmap().load(R.drawable.material5).into(holder.imgFood);
            } else if (image.equals("R.drawable.material6")) {
                Glide.with(context).asBitmap().load(R.drawable.material6).into(holder.imgFood);
            } else {
                Glide.with(context).asBitmap().load(image).into(holder.imgFood);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (items!=null) {
            return items.size();
        }
        return 0;
    }

    public class Viewholor extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName;
        TextView txtType;
        TextView txtAddress;
        TextView txtDate;
        ConstraintLayout ctFood;

        public Viewholor(View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgPicture);
            txtName = itemView.findViewById(R.id.txtName);
            txtType = itemView.findViewById(R.id.txtType);
            ctFood = itemView.findViewById(R.id.ctMatetial);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }

    @NonNull
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Material> results = new ArrayList<>();
                if (filter == null)
                    filter = items;
                if (constraint != null) {
                    if (filter != null && filter.size() > 0) {
                        for (final Material g : filter) {
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
                items = (List<Material>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
