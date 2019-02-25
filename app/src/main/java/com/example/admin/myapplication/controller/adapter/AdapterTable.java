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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.controller.interfaces.ItemTableClick;
import com.example.admin.myapplication.model.object.TableDinner;

import java.util.ArrayList;
import java.util.List;

public class AdapterTable extends RecyclerView.Adapter<AdapterTable.Viewholor> {
    private ItemTableClick iOnClickSetColor;
    private List<TableDinner> list;
    private List<TableDinner> filter;
    private List<TableDinner> items;
    private Context context;


    public AdapterTable(ItemTableClick iOnClickSetColor, List<TableDinner> list, Context context) {
        this.iOnClickSetColor = iOnClickSetColor;
        this.list = list;
        this.items = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new Viewholor(view);
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull Viewholor holder, final int position) {
        holder.txtName.setText(list.get(position).getName());
        holder.txtMember.setText(list.get(position).getMember()+"");
        if (list.get(position).isStatus()) {
            holder.imgTable.setImageResource(R.drawable.ic_logo_table_true);
        }else {
            holder.imgTable.setImageResource(R.drawable.ic_logo_table);
        }
        holder.ctTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickSetColor.iClick("detail",list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Viewholor extends RecyclerView.ViewHolder {
        ImageView imgTable;
        TextView txtName;
        TextView txtMember;
        LinearLayout ctTable;

        public Viewholor(View itemView) {
            super(itemView);
            imgTable = itemView.findViewById(R.id.imgTable);
            txtName = itemView.findViewById(R.id.txtName);
            txtMember = itemView.findViewById(R.id.txtMember);
            ctTable = itemView.findViewById(R.id.ctTable);
        }
    }

    @NonNull
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<TableDinner> results = new ArrayList<>();
                if (filter == null)
                    filter = items;
                if (constraint != null) {
                    if (filter != null && filter.size() > 0) {
                        for (final TableDinner g : filter) {
                            if (g.getName().toLowerCase().contains(constraint.toString())) {
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
                items = (List<TableDinner>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
