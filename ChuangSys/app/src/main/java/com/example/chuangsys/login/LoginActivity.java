package com.example.chuangsys.login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.transition.Explode;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.chuangsys.R;
import com.example.chuangsys.messagelogin.MessageActivity;
import com.example.chuangsys.register.RegisterActivity;

/****************************
 * author:cst
 * date:2020.4.20
 */
public class LoginActivity extends Activity implements LoginView,View.OnClickListener{
    private EditText phoneNumEt,passwordEt;  //手机，密码框
    private Button loginBtn;  //登录按钮
    private CheckBox rPasswordCb; //记住密码单选框
    private SharedPreferences pref;
    private boolean pswIsRight = false;  //密码是否正确



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initView();
        textViewClick();

    }


    public void initView(){  //组件初始化
        phoneNumEt = (EditText) findViewById(R.id.login_phoneNumEt);
        passwordEt = (EditText) findViewById(R.id.login_passwordEt);
        phoneNumEt.clearFocus();
        loginBtn = (Button) findViewById(R.id.login_Button);
        loginBtn.setOnClickListener(this);
        rPasswordCb = (CheckBox) findViewById(R.id.login_rPassword);
    }


//    public void rPassword(){  //记住密码功能
//        pref = PreferenceManager.getDefaultSharedPreferences(this);//记住密码实现模块1
//        boolean isRemember = pref.getBoolean("remember_password",false);
//        if (isRemember){
//            String phoneNumStr = pref.getString("phoneNumStr","");
//            String passwordStr = pref.getString("passwordStr","");
//            phoneNumEt.setText(phoneNumStr);
//            passwordEt.setText(passwordStr);
//            rPasswordCb.setChecked(true);
//        }
//    }


/////////////////////////////////////////////////////文本点击事件//////////////////////////////////////////////////
    public void textViewClick(){
        String fPasswordStr,registerStr;  //忘记密码，注册TextView文本
        fPasswordStr = "忘记密码？";
        registerStr = "没有账号？点击注册";

        SpannableStringBuilder spannableString1 = new SpannableStringBuilder();  //忘记密码点击事件
        spannableString1.append(fPasswordStr);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MessageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);  //activity跳转动画
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
        spannableString1.setSpan(clickableSpan1,0,fPasswordStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        TextView textView1 = (TextView)findViewById(R.id.login_fPassword);
        textView1.setHighlightColor(getResources().getColor(android.R.color.transparent));
        textView1.setText(spannableString1);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableStringBuilder spannableString2 = new SpannableStringBuilder();  //注册点击事件
        spannableString2.append(registerStr);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);  //activity跳转动画
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
        spannableString2.setSpan(clickableSpan2,0,registerStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        TextView textView2 = (TextView)findViewById(R.id.login_register);
        textView2.setHighlightColor(getResources().getColor(android.R.color.transparent));
        textView2.setText(spannableString2);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
    }
/////////////////////////////////////////////////////文本点击事件//////////////////////////////////////////////////


/////////////////////////////////////////////////////////校验手机号码//////////////////////////////////////////
    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
//////////////////////////////////////////////////////////////校验手机号码///////////////////////////////////////////


    @Override
    public void onClick(View v) {  //点击事件
        switch (v.getId()){
            case R.id.login_Button:
                if (phoneNumEt.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"请输入电话号码",Toast.LENGTH_SHORT).show();
                }else if (!judgePhoneNums(phoneNumEt.getText().toString())){

                }else if (passwordEt.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


///////////////////////////////////////////////////后端数据///////////////////////////////////////////
    @Override
    public void returnData(String str) {  //后台返回成功数据

    }
    @Override
    public void onFail() {  //后台返回失败结果

    }
///////////////////////////////////////////////后端数据///////////////////////////////////////////////


}
