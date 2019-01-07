package com.example.admin.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.screen.LoginActivity;
import com.example.admin.myapplication.util.MyPreferenceHelper;

public class Fragment_setting extends Fragment implements View.OnClickListener {

    RelativeLayout rlTaikhoan, rlMusic, rlNgonngu, rlShare, rlNoti;
    ImageView imgTaikhoan, imgMusic, imgNgonngu, imgShare;
    CardView logoTaikhoan, logoMusic, logoNgonngu, logoShare, logoNoti;
    SwitchCompat swtNoti;

    public static Fragment_setting instance;

    public static Fragment_setting newInstance() {
        if (instance == null) {
            instance = new Fragment_setting();
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
        View view = inflater.inflate(R.layout.frag_setting, container, false);

        rlTaikhoan = view.findViewById(R.id.rlTaikhoan);
        rlMusic = view.findViewById(R.id.rlMusic);
        rlNgonngu = view.findViewById(R.id.rlNgonNgu);
        rlNoti = view.findViewById(R.id.rlNoti);
        rlShare = view.findViewById(R.id.rlShare);
        imgTaikhoan = view.findViewById(R.id.imgTaikhoan);
        imgMusic = view.findViewById(R.id.imgMusic);
        imgNgonngu = view.findViewById(R.id.imgNgonngu);
        imgShare = view.findViewById(R.id.imgShare);
        logoMusic = view.findViewById(R.id.logoMusic);
        logoNgonngu = view.findViewById(R.id.logoNgonNgu);
        logoNoti = view.findViewById(R.id.logoNoti);
        logoTaikhoan = view.findViewById(R.id.logoTaikhoan);
        logoShare = view.findViewById(R.id.logoShare);
        swtNoti = view.findViewById(R.id.swtNoti);

        rlTaikhoan.setOnClickListener(this);
        rlMusic.setOnClickListener(this);
        rlNgonngu.setOnClickListener(this);
        rlShare.setOnClickListener(this);
        rlNoti.setOnClickListener(this);
        imgTaikhoan.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgNgonngu.setOnClickListener(this);
        imgMusic.setOnClickListener(this);
        logoTaikhoan.setOnClickListener(this);
        logoShare.setOnClickListener(this);
        logoNoti.setOnClickListener(this);
        logoNgonngu.setOnClickListener(this);
        logoMusic.setOnClickListener(this);

        return view;
    }

    private void initUI(View view) {

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
            case R.id.rlTaikhoan: case R.id.imgTaikhoan : case  R.id.logoTaikhoan:
                MyPreferenceHelper.setString(getContext(),MyPreferenceHelper.remember,"no");
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        super.onPause();

    }

}
