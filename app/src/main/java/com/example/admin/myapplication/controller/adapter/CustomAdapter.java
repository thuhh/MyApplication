package com.example.admin.myapplication.controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class CustomAdapter extends FragmentPagerAdapter {
    public CustomAdapter(FragmentManager supportFragmentManager, List<Fragment> list) {
        super(supportFragmentManager);
        this.arrayList = list;
    }


    private List<Fragment> arrayList;


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return arrayList.get(0);
            case 1:
                return arrayList.get(1);
            default:
                return arrayList.get(0);


        }
    }

    @Override
    public int getCount() {

        return arrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
