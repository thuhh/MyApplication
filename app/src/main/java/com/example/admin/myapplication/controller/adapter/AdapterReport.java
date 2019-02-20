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
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.controller.interfaces.ItemTableClick;
import com.example.admin.myapplication.model.object.Report;

import java.util.ArrayList;
import java.util.List;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.Viewholor> {
    private ItemTableClick iOnClickSetColor;
    private List<Report> list;
    private List<Report> filter;
    private List<Report> items;
    private Context context;


    public AdapterReport(ItemTableClick iOnClickSetColor, List<Report> list, Context context) {
        this.iOnClickSetColor = iOnClickSetColor;
        this.list = list;
        this.items = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new Viewholor(view);
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull Viewholor holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtName.setText(list.get(position).getName());
        holder.txtMoney.setText(list.get(position).getMoney());
        holder.txtTime.setText(list.get(position).getTime());
        holder.txtDate.setText(list.get(position).getDate());

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
        TextView txtName;
        TextView txtMoney;
        TextView txtTime;
        TextView txtDate;
        ConstraintLayout ctTable;

        public Viewholor(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtMoney = itemView.findViewById(R.id.txtMoney);
            txtName = itemView.findViewById(R.id.txtNameReport);
            ctTable = itemView.findViewById(R.id.ctTable);
        }
    }

    @NonNull
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Report> results = new ArrayList<>();
                if (filter == null)
                    filter = items;
                if (constraint != null) {
                    if (filter != null && filter.size() > 0) {
                        for (final Report g : filter) {
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
                items = (List<Report>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
