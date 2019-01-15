package com.example.admin.myapplication.view.activiti;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.controller.adapter.CustomAdapter;
import com.example.admin.myapplication.view.fragmnet.Fragment_Food;
import com.example.admin.myapplication.view.fragmnet.Fragment_Report;
import com.example.admin.myapplication.view.fragmnet.Fragment_Table;
import com.example.admin.myapplication.view.fragmnet.Fragment_material;
import com.example.admin.myapplication.view.fragmnet.Fragment_setting;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initView();
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        Fragment_Table fragment_table = Fragment_Table.newInstance();
        Fragment_Food fragment_food = Fragment_Food.newInstance();
        Fragment_material fragment_material = Fragment_material.newInstance();
        Fragment_Report fragment_report = Fragment_Report.newInstance();
        Fragment_setting fragmentSetting = Fragment_setting.newInstance();
        ArrayList<Fragment> array = new ArrayList();
        array.add(fragment_table);
        array.add(fragment_food);
        array.add(fragment_material);
        array.add(fragment_report);
        array.add(fragmentSetting);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(), array));

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF1271FF"));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#FF000000"), Color.parseColor("#FF1271FF"));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Table").setIcon(R.drawable.ic_logo_table_true);
        tabLayout.getTabAt(1).setText("Food").setIcon(R.drawable.ic_logo_food);
        tabLayout.getTabAt(2).setText("Material").setIcon(R.drawable.ic_logo_material);
        tabLayout.getTabAt(3).setText("Report").setIcon(R.drawable.ic_logo_report);
        tabLayout.getTabAt(4).setText("Setting").setIcon(R.drawable.ic_logo_setting);
//
        viewPager.setOffscreenPageLimit(5);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==0){
                    setIconTab();
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_logo_table_true);
                }else if (i==1){
                    setIconTab();
                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_logo_food_true);
                }
                else if (i==2){
                    setIconTab();
                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_logo_material_true);
                }
                else if (i==3){
                    setIconTab();
                    tabLayout.getTabAt(3).setIcon(R.drawable.ic_logo_report_true);
                }
                else if (i==4){
                    setIconTab();
                    tabLayout.getTabAt(4).setIcon(R.drawable.ic_logo_setting_true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setIconTab(){
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_logo_table);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_logo_food);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_logo_material);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_logo_report);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_logo_setting);
    }

    private void initUI() {

    }
}
