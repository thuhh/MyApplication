package com.example.admin.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.myapplication.R;

public class Fragment_material extends Fragment implements View.OnClickListener {

    public static Fragment_material instance;

    public static Fragment_material newInstance() {
        if (instance == null) {
            instance = new Fragment_material();
        }
        return instance;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @SuppressLint({"NewApi"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_material, container, false);

        return view;
    }


    @SuppressLint("NewApi")
    @Override
    public void onStart() {
        super.onStart();

    }

    @SuppressLint("NewApi")
    @Override
    public void onStop() {
        super.onStop();
    }


    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        super.onPause();

    }

}
