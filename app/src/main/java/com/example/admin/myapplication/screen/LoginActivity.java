package com.example.admin.myapplication.screen;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.admin.myapplication.database.RDBUser;
import com.example.admin.myapplication.model.User;
import com.example.admin.myapplication.util.MyPreferenceHelper;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //database user
    RDBUser rdbUser;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        checkRemember();
    }

    private void checkRemember() {
        if (MyPreferenceHelper.getString(MyPreferenceHelper.remember,this)!=null && MyPreferenceHelper.getString(MyPreferenceHelper.remember,this).equals("yes")){
            chkRemember.setChecked(true);
            edtUserLogin.setText(MyPreferenceHelper.getString(MyPreferenceHelper.userName,this));
            edtPassLogin.setText(MyPreferenceHelper.getString(MyPreferenceHelper.password,this));
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
            }.start();

        }
        else {
            chkRemember.setChecked(false);
        }
    }

    private void initUI() {
        rdbUser = RDBUser.getAppDatabase(this);
        users = rdbUser.userDAO().getAllApp();
        viewFlipper = findViewById(R.id.viewFlipper);
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
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        txtForget.setOnClickListener(this);
        txtSignup.setOnClickListener(this);
        chkRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MyPreferenceHelper.setString(LoginActivity.this,MyPreferenceHelper.userName,edtUserLogin.getText().toString());
                    MyPreferenceHelper.setString(LoginActivity.this,MyPreferenceHelper.password,edtPassLogin.getText().toString());
                    MyPreferenceHelper.setString(LoginActivity.this,MyPreferenceHelper.remember,"yes");
                }
                else {
                    MyPreferenceHelper.setString(LoginActivity.this,MyPreferenceHelper.remember,"no");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                users = rdbUser.userDAO().getAllApp();
                if (users.size()>0) {
                    int d = 0;
                    for(int i=0; i<users.size(); i++){
                        if (users.get(i).getUsername().equals(edtUserLogin.getText().toString().trim())&&users.get(i).getUsername().equals(edtUserLogin.getText().toString().trim())){
                            d++;
                        }
                    }
                    if (d!=0) {
//                        startActivity(new Intent(this, MainActivity.class));
//                        finish();
                        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                        viewFlipper.setFlipInterval(1);
                        viewFlipper.setDisplayedChild(2);
                    }
                    else {
                        Toast.makeText(this, "Tài khoản hoặc mật khẩu không đúng!",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(this,"Tài khoản này không tồn tại!",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnSignUp:
                if (users.size()>0) {
                    int d = 0;
                    for(int i=0; i<users.size(); i++){
                        if (users.get(i).getUsername().equals(edtUserSignup.getText().toString().trim())){
                            Toast.makeText(this,"Tài khoản đã tồn tại!",Toast.LENGTH_LONG).show();
                            d++;
                        }
                    }
                    if (d==0){
                        insertUser();
                    }
                }
                else {
                    insertUser();
                }
                break;
            case R.id.txtForget:
                txtSignup.setTextColor(Color.BLUE);
                break;
            case R.id.txtSignup:
//                txtSignup.setTextColor(Color.BLUE);
//                rlLogin.setVisibility(View.GONE);
//                rlSignup.setVisibility(View.VISIBLE);
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                viewFlipper.setFlipInterval(1);
                viewFlipper.setDisplayedChild(1);
                break;
            case R.id.imgEye:
                break;
            default:
                break;
        }
    }

    private void insertUser() {
        rdbUser.userDAO().insertAll(new User(edtUserSignup.getText().toString().trim(),edtPassSignup.getText().toString().trim(),edtComfig.getText().toString().trim(),edtPinCode.getText().toString().trim()));
//        rlLogin.setVisibility(View.VISIBLE);
//        rlSignup.setVisibility(View.GONE);
        edtUserLogin.setText(edtUserSignup.getText().toString().trim());
        edtPassLogin.setText(edtPassSignup.getText().toString().trim());
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        viewFlipper.setFlipInterval(1);
        viewFlipper.setDisplayedChild(0);
    }
}
