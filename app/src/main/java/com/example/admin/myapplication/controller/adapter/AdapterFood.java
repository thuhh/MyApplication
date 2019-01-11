package com.example.admin.myapplication.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.controller.interfaces.IOnClick;
import com.example.admin.myapplication.model.database.RDBFood;
import com.example.admin.myapplication.model.object.Food;

import java.util.ArrayList;
import java.util.List;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.Viewholor> {
    private IOnClick iOnClickSetColor;
    private ArrayList<Food> list;
    private Context context;


    public AdapterFood(IOnClick iOnClickSetColor, ArrayList<Food> list, Context context) {
        this.iOnClickSetColor = iOnClickSetColor;
        this.list = list;
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
        Bitmap bitmap = BitmapFactory.decodeByteArray(list.get(position).getImage(), 0, list.get(position).getImage().length);
       holder.imgFood.setImageBitmap(bitmap);
        holder.ctFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSetColor.iClick("");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholor extends RecyclerView.ViewHolder {
       ImageView imgFood, imgNew ;
       TextView txtName;
       ConstraintLayout ctFood;

        public Viewholor(View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            imgNew = itemView.findViewById(R.id.imgNew);
            txtName = itemView.findViewById(R.id.txtName);
            ctFood = itemView.findViewById(R.id.ctFood);
        }
    }
}
