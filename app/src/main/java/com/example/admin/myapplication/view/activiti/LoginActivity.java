package com.example.admin.myapplication.view.activiti;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.model.database.RDBApp;
import com.example.admin.myapplication.model.object.User;
import com.example.admin.myapplication.controller.util.MyPreferenceHelper;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //database user
    RDBApp rdbApp;
    List<User> users;

    Button btnLogin;
    EditText edtUserLogin, edtPassLogin;
    CheckBox chkRemember;
    TextView txtForget, txtSignup;
    ImageView btnEye;

    Button btnSignup;
    EditText edtUserSignup, edtPassSignup, edtComfig, edtPinCode;

    RelativeLayout rlLogin, rlSignup;
    ViewFlipper viewFlipper;

    ConstraintLayout ctBanAn, ctFood, ctMaterial, ctReport, ctUser, ctSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        checkRemember();
        if (getIntent()!=null) {
            if (getIntent().getIntExtra("menu", -1) > -1) {
                viewFlipper.setDisplayedChild(2);
            }
        }
    }

    private void checkRemember() {
        if (MyPreferenceHelper.getString(MyPreferenceHelper.remember, this) != null && MyPreferenceHelper.getString(MyPreferenceHelper.remember, this).equals("yes")) {
            chkRemember.setChecked(true);
            edtUserLogin.setText(MyPreferenceHelper.getString(MyPreferenceHelper.userName, this));
            edtPassLogin.setText(MyPreferenceHelper.getString(MyPreferenceHelper.password, this));
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }.start();

        } else {
            chkRemember.setChecked(false);
        }
    }

    private void initUI() {
        try {
            rdbApp = RDBApp.getAppDatabase(this);
            users = rdbApp.userDAO().getAllUser();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        viewFlipper = findViewById(R.id.viewFlipper);
        viewFlipper.setDisplayedChild(1);
        rlLogin = findViewById(R.id.rlLogin);
        rlSignup = findViewById(R.id.rlSignup);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);
        btnEye = findViewById(R.id.imgEye);
        edtUserLogin = findViewById(R.id.edtUser);
        edtPassLogin = findViewById(R.id.edtPass);
        edtUserSignup = findViewById(R.id.edtUserSignup);
        edtPassSignup = findViewById(R.id.edtPassSignUp);
        edtComfig = findViewById(R.id.edtComfigPassSignUp);
        edtPinCode = findViewById(R.id.edtPin);
        txtForget = findViewById(R.id.txtForget);
        txtSignup = findViewById(R.id.txtSignup);
        chkRemember = findViewById(R.id.chkRemember);
        ctBanAn = findViewById(R.id.ctBanAn);
        ctFood = findViewById(R.id.ctDoAn);
        ctMaterial = findViewById(R.id.ctNguyenLieu);
        ctReport = findViewById(R.id.ctBaoCao);
        ctSetting = findViewById(R.id.ctCaiDat);
        ctUser = findViewById(R.id.ctTaiKhoan);
        ctBanAn.setOnClickListener(this);
        ctFood.setOnClickListener(this);
        ctMaterial.setOnClickListener(this);
        ctReport.setOnClickListener(this);
        ctUser.setOnClickListener(this);
        ctSetting.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        txtForget.setOnClickListener(this);
        txtSignup.setOnClickListener(this);
        chkRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MyPreferenceHelper.setString(LoginActivity.this, MyPreferenceHelper.userName, edtUserLogin.getText().toString());
                    MyPreferenceHelper.setString(LoginActivity.this, MyPreferenceHelper.password, edtPassLogin.getText().toString());
                    MyPreferenceHelper.setString(LoginActivity.this, MyPreferenceHelper.remember, "yes");
                } else {
                    MyPreferenceHelper.setString(LoginActivity.this, MyPreferenceHelper.remember, "no");
                }
            }
        });

    }

    private void nextActivity(int value, Class<?> activity) {
        MyPreferenceHelper.setInt(MyPreferenceHelper.keyNext, value, this);
        startActivity(new Intent(LoginActivity.this, activity));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                try {
                    users = rdbApp.userDAO().getAllUser();
                    if (users.size() > 0) {
                        int d = 0;
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getUsername().equals(edtUserLogin.getText().toString().trim()) && users.get(i).getPassword().equals(edtPassLogin.getText().toString().trim())) {
                                d++;
                            }
                        }
                        if (d != 0) {
                            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                            viewFlipper.setDisplayedChild(2);
                        } else {
                            Toast.makeText(this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "Tài khoản này không tồn tại!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnSignUp:
                if (users.size() > 0) {
                    int d = 0;
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getUsername().equals(edtUserSignup.getText().toString().trim())) {
                            Toast.makeText(this, "Tài khoản đã tồn tại!", Toast.LENGTH_LONG).show();
                            d++;
                        }
                    }
                    if (d == 0) {
                        insertUser();
                    }
                } else {
                    insertUser();
                }
                break;
            case R.id.txtForget:
                txtSignup.setTextColor(Color.BLUE);
                break;
            case R.id.txtSignup:
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                viewFlipper.setDisplayedChild(0);
                break;
            case R.id.imgEye:
                break;
            case R.id.ctBanAn:
                nextActivity(1, TableActivity.class);
                break;
            case R.id.ctDoAn:
                nextActivity(2, FoodActivity.class);
                break;
            case R.id.ctNguyenLieu:
                nextActivity(3, FoodActivity.class);
                break;
            case R.id.ctBaoCao:
                nextActivity(3, FoodActivity.class);
                break;
            case R.id.ctCaiDat:
                nextActivity(3, FoodActivity.class);
                break;
            case R.id.ctTaiKhoan:
                nextActivity(3, FoodActivity.class);
                break;

            default:
                break;
        }
    }

    private void insertUser() {
        rdbApp.userDAO().insertAll(new User(edtUserSignup.getText().toString().trim(), edtPassSignup.getText().toString().trim(), edtComfig.getText().toString().trim(), edtPinCode.getText().toString().trim()));
        edtUserLogin.setText(edtUserSignup.getText().toString().trim());
        edtPassLogin.setText(edtPassSignup.getText().toString().trim());
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        viewFlipper.setFlipInterval(1);
        viewFlipper.setDisplayedChild(1);
    }
}
