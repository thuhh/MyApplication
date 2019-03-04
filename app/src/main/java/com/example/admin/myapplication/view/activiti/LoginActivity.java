package com.example.admin.myapplication.view.activiti;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
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
import com.example.admin.myapplication.model.object.Food;
import com.example.admin.myapplication.model.object.Material;
import com.example.admin.myapplication.model.object.TableDinner;
import com.example.admin.myapplication.model.object.User;
import com.example.admin.myapplication.controller.util.MyPreferenceHelper;
import com.example.admin.myapplication.view.activiti.account.AcountActivity;
import com.example.admin.myapplication.view.activiti.food.FoodActivity;
import com.example.admin.myapplication.view.activiti.material.MaterialActivity;
import com.example.admin.myapplication.view.activiti.report.ReportActivity;
import com.example.admin.myapplication.view.activiti.table.TableActivity;

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
        if (MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.remember, this)) {
            chkRemember.setChecked(true);
            edtUserLogin.setText(MyPreferenceHelper.getString(MyPreferenceHelper.userName, this));
            edtPassLogin.setText(MyPreferenceHelper.getString(MyPreferenceHelper.password, this));
            viewFlipper.setDisplayedChild(2);
        } else {
            chkRemember.setChecked(false);
        }
    }

    void loadData(int userId){
        if (!MyPreferenceHelper.getBooleanValue(MyPreferenceHelper.firstData,this)) {
            //add food
            rdbApp.foodDAO().insertAll(new Food(0, "Bạch tuộc xào lăn", "đồ nhậu", "150", true, "R.drawable.food1", "Bạch tuộc", "15%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(1, "Trứng gà non xào nóng", "đồ nhậu", "150", true, "R.drawable.food2", "Trứng gà non", "25%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(2, "Ếch đồng xào sả ớt", "đồ nhậu", "150", true, "R.drawable.food3", "Ếch đồng", "5%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(3, "Gà rang tẩm bột", "đồ nhậu", "150", true, "R.drawable.food4", "Thịt gà", "35%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(4, "Cá đồng kho tàu", "đồ nhậu", "150", true, "R.drawable.food5", "Cá đồng", "5%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(5, "Chân gà rim mật ong", "đồ nhậu", "150", true, "R.drawable.food6", "Chân gà, mật ong", "5%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(6, "Lòng non luộc tới", "đồ nhậu", "150", true, "R.drawable.food7", "Lòng non của lợn", "25%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(7, "Thịt bò đậu tẻ", "đồ nướng", "150", true, "R.drawable.food8", "Thịt bò, đậu bắp", "5%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(8, "Cá trứng nhúng lẩu", "đồ nhậu", "200", true, "R.drawable.food9", "Cá trứng", "15%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(9, "Vịt quay Vân đình", "đồ nhậu", "150", true, "R.drawable.food10", "Vịt cỏ", "5%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(10, "Bạch tuộc xiên", "đồ nướng", "150", true, "R.drawable.food11", "Bạch tuộc", "5%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(11, "Ngao hấp sả ớt", "đồ nhậu", "150", true, "R.drawable.food12", "Ngao biển", "15%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(12, "Thịt trâu gác bếp", "đồ nhậu", "150", true, "R.drawable.food13", "Thịt trâu", "25%",5,0,"món ăn ngon và bổ dưỡng", userId));
            rdbApp.foodDAO().insertAll(new Food(13, "Tôm Hùm nướng", "đồ nướng", "150", true, "R.drawable.food14", "Tôm hùm biển", "5%",5,0,"món ăn ngon và bổ dưỡng", userId));

            MyPreferenceHelper.putBooleanValue(MyPreferenceHelper.firstData,true,this);
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
                    MyPreferenceHelper.putBooleanValue( MyPreferenceHelper.remember, true,LoginActivity.this);
                } else {
                    MyPreferenceHelper.putBooleanValue( MyPreferenceHelper.remember, false,LoginActivity.this);
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
                if (!edtUserLogin.getText().toString().trim().isEmpty() && !edtPassLogin.getText().toString().trim().isEmpty() ) {
                    users = rdbApp.userDAO().getAllUser();
                    if (users.size() > 0) {
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getUsername().equals(edtUserLogin.getText().toString().trim()) && users.get(i).getPassword().equals(edtPassLogin.getText().toString().trim())) {
                                MyPreferenceHelper.setInt(MyPreferenceHelper.idUser, users.get(i).getId(), this);
                                MyPreferenceHelper.setString(LoginActivity.this, MyPreferenceHelper.userName, edtUserLogin.getText().toString());
                                MyPreferenceHelper.setString(LoginActivity.this, MyPreferenceHelper.password, edtPassLogin.getText().toString());
                                loadData(users.get(i).getId());
                                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                                viewFlipper.setDisplayedChild(2);

                                return;
                            }
                        }
                        Toast.makeText(this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Tài khoản này không tồn tại!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu đang để trống!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnSignUp:
                if (users!=null && users.size() > 0) {
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

                //hide soft keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                View view = getCurrentFocus();
                if (view == null) {
                    view = new View(this);
                }
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                nextActivity(3, MaterialActivity.class);
                break;
            case R.id.ctBaoCao:
                nextActivity(4, ReportActivity.class);
                break;
            case R.id.ctCaiDat:
                nextActivity(5, SettingActivity.class);
                break;
            case R.id.ctTaiKhoan:
                nextActivity(6, AcountActivity.class);
                break;

            default:
                break;
        }
    }

    private void insertUser() {
        int id = 0;
        if (users!= null) {
            id = users.size();
        }
        if (!edtUserSignup.getText().toString().trim().isEmpty() && !edtPassSignup.getText().toString().trim().isEmpty() && !edtComfig.getText().toString().trim().isEmpty()&& !edtPinCode.getText().toString().trim().isEmpty() ) {
            if (edtPassSignup.getText().toString().trim().equals(edtComfig.getText().toString().trim())) {
                rdbApp.userDAO().insertAll(new User(id, edtUserSignup.getText().toString().trim(), edtPassSignup.getText().toString().trim(), edtComfig.getText().toString().trim(), edtPinCode.getText().toString().trim()));
                edtUserLogin.setText(edtUserSignup.getText().toString().trim());
                edtPassLogin.setText(edtPassSignup.getText().toString().trim());
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                viewFlipper.setFlipInterval(1);
                viewFlipper.setDisplayedChild(1);
            }else {
                Toast.makeText(this, "Mật khẩu xác nhận không đúng!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Bạn cần điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
    }
}
