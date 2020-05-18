package com.example.chuangsys.cpassword;

import android.content.Intent;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.smssdk.SMSSDK;
import com.example.chuangsys.R;
import com.example.chuangsys.register.RegisterActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * author:cst
 * date:2020.5.3
 * */

public class CPasswordActivity extends AppCompatActivity implements View.OnClickListener,CPasswordView{
    private EditText passwordEt1,passwordEt2;  //第一，第二次密码框
    private Button button;
    private TextInputLayout pswTLayout1,pswTLayout2;
    private ImageView imageView1,imageView2;
    private boolean isOpenPsw1 = false,isOpenPsw2 = false; //密码是否可视化
    private boolean flag1 = false,flag2 = false;  //两次密码格式是否正确
    CPasswordPresent cPasswordPresent;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpassword);

        initView();
        validateAll();

    }

    public void initView(){
        passwordEt1 = (EditText) findViewById(R.id.cPassword_passwordEt);
        passwordEt2 = (EditText) findViewById(R.id.cPassword_passwordEt2);
        passwordEt1.clearFocus();
        button = (Button) findViewById(R.id.cPassword_Button);
        button.setOnClickListener(this);
        pswTLayout1 = (TextInputLayout) findViewById(R.id.cpasswrod_pswTLayout1);
        pswTLayout2 = (TextInputLayout) findViewById(R.id.cpasswrod_pswTLayout2);
        imageView1 = (ImageView) findViewById(R.id.cPassword_isOpenImage1);
        imageView1.setImageResource(R.drawable.closeeye);
        imageView1.setOnClickListener(this);
        imageView2 = (ImageView) findViewById(R.id.cPassword_isOpenImage2);
        imageView2.setImageResource(R.drawable.closeeye);
        imageView2.setOnClickListener(this);
        cPasswordPresent = new CPasswordPresentImpl(this);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {  //点击事件
        switch (v.getId()){
            case R.id.cPassword_Button:
                String psw1 = passwordEt1.getText().toString();
                String psw2 = passwordEt2.getText().toString();
                if (!psw1.equals("")&&!psw2.equals("")){
                    if (psw1.equals(psw2) && flag1 && flag2){
                        cPasswordPresent.postChangePassword(psw1,phone);

                    }else {
                        Toast.makeText(CPasswordActivity.this,"两次输入密码要相同且格式正确",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CPasswordActivity.this,"请填写两次密码",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.cPassword_isOpenImage1:  //密码框是否可视
                if (!isOpenPsw1){
                    isOpenPsw1 = true;
                    imageView1.setImageResource(R.drawable.eyeopen);
                    passwordEt1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEt1.setSelection(passwordEt1.getText().length());
                }else {
                    isOpenPsw1 = false;
                    imageView1.setImageResource(R.drawable.closeeye);
                    passwordEt1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEt1.setSelection(passwordEt1.getText().length());
                }
                break;

            case R.id.cPassword_isOpenImage2:  //密码框是否可视
                if (!isOpenPsw2){
                    isOpenPsw2 = true;
                    imageView2.setImageResource(R.drawable.eyeopen);
                    passwordEt2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEt2.setSelection(passwordEt2.getText().length());
                }else {
                    isOpenPsw2 = false;
                    imageView2.setImageResource(R.drawable.closeeye);
                    passwordEt2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEt2.setSelection(passwordEt2.getText().length());
                }
                break;
            default:
                break;
        }
    }

/////////////////////////////////////////////////////输入框验证/////////////////////////////////////////////////
    private void validatePassword1(){
        passwordEt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //6-20 位，字母、数字、字符
                String reg= "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){6,20}$";
                Pattern w = Pattern.compile(reg);
                Matcher wk = w.matcher(s.toString());
                boolean pw = wk.matches();
                if (!pw){
                    pswTLayout1.setError("密码格式:6-20 位，字母、数字、字符");
                    pswTLayout1.setErrorEnabled(true);
                    flag1 = false;
                }else {
                    pswTLayout1.setErrorEnabled(false);
                    flag1 = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void validatePassword2(){
        passwordEt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //6-20 位，字母、数字、字符
                String reg= "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){6,20}$";
                Pattern w = Pattern.compile(reg);
                Matcher wk = w.matcher(s.toString());
                boolean pw = wk.matches();
                if (!pw){
                    pswTLayout2.setError("密码格式:6-20 位，字母、数字、字符");
                    pswTLayout2.setErrorEnabled(true);
                    flag2 = false;
                }else {
                    pswTLayout2.setErrorEnabled(false);
                    flag2 = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void validateAll(){
        validatePassword1();
        validatePassword2();
    }
/////////////////////////////////////////////////////输入框验证/////////////////////////////////////////////////

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

/////////////////////////////////////////////////////响应数据/////////////////////////////////////////////
    @Override
    public void getResult(String response) {
        Looper.prepare();
        if (response.equals("修改成功")){
            Toast.makeText(CPasswordActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(CPasswordActivity.this,"修改密码是失败",Toast.LENGTH_SHORT).show();
        }
        Looper.loop();
    }
}
