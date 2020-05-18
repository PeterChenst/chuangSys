package com.example.chuangsys.messagelogin;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.*;
import android.text.*;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import com.example.chuangsys.MainActivity;
import com.example.chuangsys.R;
import com.example.chuangsys.cpassword.CPasswordActivity;
import com.example.chuangsys.login.LoginActivity;
import com.example.chuangsys.register.RegisterActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.mob.MobSDK;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * author:cst
 * date:2020.5.3
 * */

public class MessageActivity extends AppCompatActivity implements View.OnClickListener,MessageLoginView{
    EditText phoneNumEt,yanzhengEt; //电话,验证码框
    Button yanzhengBtn,loginBtn;  //发送验证码，登录按钮
    String APPKEY = "2f08a194bc81e";
    String APPSECRET = "4d1d45185b5e05d88afd0102e31ad1c9";
    private int i = 60;  //计时器
    private String yanzhengStr; //验证码
    private int yanzhengTime = 0;  //验证码验证次数，3次失效
    private boolean isyanzheng = false;  //验证码是否正确
    private TextInputLayout phoneTlayout,yanzhengTlayout;  //验证框
    private boolean flag1 = false;  //两个输入框格式是否正确
    private boolean flag2 = false;
    MessageLoginPresent messageLoginPresent;
    private String phoneNumStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_login);

        initView();
        initMobSDK();
        validateAll();
        textViewClick();
    }

    private void initView(){
        phoneNumEt = (EditText) findViewById(R.id.messageLogin_phoneNumEt);
        phoneNumEt.clearFocus();
        yanzhengEt = (EditText) findViewById(R.id.messageLogin_yanzhengEt);
        yanzhengEt.setLongClickable(false);
        yanzhengEt.setEnabled(false);

        yanzhengBtn = findViewById(R.id.messageLogin_yanzhengBtn);
        loginBtn = findViewById(R.id.messageLogin_Button);
        yanzhengBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        phoneTlayout = (TextInputLayout) findViewById(R.id.message_phoneTLayput);
        yanzhengTlayout = (TextInputLayout) findViewById(R.id.message_yanzhengTLayput);

        messageLoginPresent = new MessageLoginPresentImpl(this);
    }

//////////////////////////////////////////////////////短信验证码////////////////////////////////////////////////////////
    private void initMobSDK(){  //初始化MobSDK
        //如果 targetSdkVersion小于或等于22，可以忽略这一步，如果大于或等于23，需要做权限的动态申请：
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        // 启动短信验证sdk
        MobSDK.init(this, APPKEY, APPSECRET);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int i, int i1, Object o) {
                Message message = new Message();
                message.arg1 = i;
                message.arg2 = i1;
                message.obj = o;
                handler.sendMessage(message);
            }
            //注册回调监听接口
        };
        SMSSDK.registerEventHandler(eventHandler);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == -9) {
                yanzhengBtn.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                yanzhengBtn.setText("获取验证码");
                yanzhengBtn.setClickable(true);
                if (!isyanzheng){
                    phoneNumEt.setEnabled(true);
                }else {
                    yanzhengBtn.setEnabled(false);
                }
                i = 60;
            } else {
                int i = msg.arg1;
                int i1 = msg.arg2;
                Object o = msg.obj;
                if (i1 == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        isyanzheng = true;
                        yanzhengEt.setEnabled(false);
                        yanzhengEt.setText(yanzhengStr);
                        yanzhengTlayout.setErrorEnabled(false);
                        Toast.makeText(MessageActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                    } else if (i == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                        Toast.makeText(MessageActivity.this, "正在获取验证码", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };
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
        //Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
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
    @Override
    protected void onDestroy() {
        //反注册回调监听接口
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
//////////////////////////////////////////////////////短信验证码////////////////////////////////////////////////////////


/////////////////////////////////////////////////////输入框验证/////////////////////////////////////////////////
    private void validatePhoneNum(){
        if (phoneNumEt.getText().toString().equals("")){
            flag1 = false;
        }else{
            flag1 = true;
        }
        phoneNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!judgePhoneNums(s.toString())){
                    phoneTlayout.setError("手机号格式错误");
                    phoneTlayout.setErrorEnabled(true);
                    flag1 = false;
                }else {
                    phoneTlayout.setErrorEnabled(false);
                    flag1 = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void validateYanzhang(){
        if (yanzhengEt.getText().toString().equals("")){
            flag2 = false;
        }else{
            flag2 = true;
        }
        yanzhengEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6){
                    yanzhengTlayout.setError("验证码为6位");
                    yanzhengTlayout.setErrorEnabled(true);
                    flag2 = false;
                }else {
                    yanzhengTlayout.setErrorEnabled(false);
                    flag2 = true;
                }
                if (s.length() == 6){
                    //验证码验证3次失效
                    yanzhengTime++;
                    if (yanzhengTime <= 3){
                        SMSSDK.submitVerificationCode("86", phoneNumEt.getText().toString(), yanzhengEt.getText().toString());
                        if (!isyanzheng){
                            int restTime = 3-yanzhengTime;
                            yanzhengTlayout.setError("验证码错误，还剩"+restTime+"次机会");
                            yanzhengTlayout.setErrorEnabled(true);
                            yanzhengStr = yanzhengEt.getText().toString();
                            if (restTime <= 0){
                                yanzhengEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});  //字数限制
                                yanzhengEt.setText("已失效，请重新发送");
                                yanzhengEt.setEnabled(false);
                            }
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void validateAll(){
        validatePhoneNum();
        validateYanzhang();
    }
/////////////////////////////////////////////////////输入框验证/////////////////////////////////////////////////


    public void textViewClick(){  //文本点击事件
        String cPasswordStr;  //修改密码TextView文本
        cPasswordStr = "输入验证码可修改密码 >";
        SpannableStringBuilder spannableString1 = new SpannableStringBuilder();  //忘记密码点击事件
        spannableString1.append(cPasswordStr);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageActivity.this, CPasswordActivity.class);
                intent.putExtra("phone",phoneNumEt.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);  //activity跳转动画
                finish();
//                if (isyanzheng){
//
//                }else {
//                    Toast.makeText(MessageActivity.this,"请先验证码验证",Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
        spannableString1.setSpan(clickableSpan1,0,cPasswordStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        TextView textView1 = (TextView)findViewById(R.id.messageLogin_cPassword);
        textView1.setHighlightColor(getResources().getColor(android.R.color.transparent));
        textView1.setText(spannableString1);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
    }


//////////////////////////////////////////////////////点击事件////////////////////////////////////////////////////
    @Override
    public void onClick(View view) {
        String phoneNum = phoneNumEt.getText().toString();
        switch (view.getId()){  //获取验证码
            case R.id.messageLogin_yanzhengBtn:
                // 1. 判断手机号是不是11位并且看格式是否合理
                if (!judgePhoneNums(phoneNum)){
                    Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
                    return;
                }

                yanzhengEt.setText("");
                isyanzheng =  false;
                yanzhengTime = 0;
                yanzhengEt.setEnabled(true);
                yanzhengEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                phoneNumEt.setEnabled(false);

                // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86",phoneNum);
                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                yanzhengBtn.setClickable(false);
                yanzhengBtn.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for ( ; i  > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0 ){
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;

            case R.id.messageLogin_Button:  //注册按钮
                phoneNumStr = phoneNumEt.getText().toString();
                messageLoginPresent.postPhone(phoneNumStr);
                validateAll();
//                if (flag1 && flag2 && isyanzheng){
//
//                }else {
//                    Toast.makeText(MessageActivity.this,"请将信息正确补全",Toast.LENGTH_SHORT).show();
//                }
                break;

            default:
                break;
        }
    }
//////////////////////////////////////////////////////点击事件////////////////////////////////////////////////////


    @Override
    public void finish() {  //结束动画
        super.finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
////////////////////////////////////////响应数据////////////////////////////////////////////////////

    @Override
    public void getResult(String response) {
        Looper.prepare();
        if (response.equals("手机号码已注册")){
            finish();
        }else {
            Toast.makeText(MessageActivity.this,"请先注册",Toast.LENGTH_SHORT).show();
        }
        Looper.loop();
    }
}
