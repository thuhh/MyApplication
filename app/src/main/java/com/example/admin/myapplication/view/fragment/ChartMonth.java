package com.example.admin.myapplication.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.model.chart.ScrollBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartMonth extends Fragment {



    private Calendar calendar;
    private SimpleDateFormat formatDay = new SimpleDateFormat("MMM dd", Locale.US);
    private String date;
    private int day, month, year;
    private String sum = "";

    private List<Float> verticalList;
    private List<String> horizontalList;
    private ScrollBar barChart;
    private Random random;

    private void currentDay() {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
    }


    public ChartMonth() {
        // Required empty public constructor
    }

    public static ChartMonth instance;

    public static ChartMonth newInstance() {
        if (instance == null) {
            instance = new ChartMonth();
        }
        return instance;
    }

    private String dayInText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendar = Calendar.getInstance();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        barChart = view.findViewById(R.id.chartMonth);

        verticalList = new ArrayList<>();
        horizontalList = new ArrayList<>();


        for (int i = 0; i < 31; i++) {
            horizontalList.add("" + i);
        }

        random = new Random();
        while (verticalList.size() < 31) {
            int randomInt = random.nextInt(1000);
            verticalList.add((float) randomInt);
        }

        barChart.setHorizontalList(horizontalList);
        barChart.setVerticalList(verticalList);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy", Locale.US);

}
