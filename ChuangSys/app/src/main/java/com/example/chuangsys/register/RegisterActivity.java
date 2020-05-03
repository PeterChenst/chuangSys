package com.example.chuangsys.register;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import com.example.chuangsys.R;
import com.google.android.material.textfield.TextInputLayout;
import com.mob.MobSDK;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText phoneNumEt,yanzhengEt,userNameEt,passwordEt;  //电话,验证码,用户名，密码框
    private Button yanzhengBtn,registerBtn;  //发送验证码，注册按钮
    private ImageView openImage;  //密码是否可视化
    public static final String TAG = "RegisterActivity";
    String APPKEY = "2f08a194bc81e";
    String APPSECRET = "4d1d45185b5e05d88afd0102e31ad1c9";
    private int i = 60;  //计时器
    private int yanzhengTime = 0;  //验证码验证次数，3次失效

    private boolean isOpenPsw = false; //密码是否可视化

    private TextInputLayout phoneTlayout,yanzhengTlayout,userNameTlayout,passwordTlayout;  //验证框
    private boolean isyanzheng = false;  //验证码是否正确
    private boolean flag1 = false;  //四个输入框格式是否正确
    private boolean flag2 = false;
    private boolean flag3 = false;
    private boolean flag4 = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        initView();
        initMobSDK();
        validateAll();

    }


    private void initView() {  //初始化组件
        phoneNumEt = (EditText) findViewById(R.id.register_phoneNumEt);
        phoneNumEt.clearFocus();
        yanzhengEt = (EditText) findViewById(R.id.register_yanzhengEt);
        yanzhengEt.setLongClickable(false);
        yanzhengEt.setEnabled(false);
        userNameEt = (EditText) findViewById(R.id.register_userNameEt);
        passwordEt = (EditText) findViewById(R.id.register_passwordEt);

        yanzhengBtn = findViewById(R.id.register_yanzhengBtn);
        registerBtn = findViewById(R.id.register_Button);
        yanzhengBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        phoneTlayout = (TextInputLayout) findViewById(R.id.register_phoneTlayout);
        yanzhengTlayout = (TextInputLayout) findViewById(R.id.register_yanzhangTlayout);
        userNameTlayout = (TextInputLayout) findViewById(R.id.register_userNameTlayout);
        passwordTlayout = (TextInputLayout) findViewById(R.id.register_passwordTlayout);

        passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
        passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码不可见

        openImage = (ImageView) findViewById(R.id.register_isOpenImage);
        openImage.setImageResource(R.drawable.closeeye);
        openImage.setOnClickListener(this);
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
                i = 60;
            } else {
                int i = msg.arg1;
                int i1 = msg.arg2;
                Object o = msg.obj;
                if (i1 == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        isyanzheng = true;
                        yanzhengTlayout.setErrorEnabled(false);
                        yanzhengEt.setEnabled(false);
                        Toast.makeText(RegisterActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                    } else if (i == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                        Toast.makeText(RegisterActivity.this, "正在获取验证码", Toast.LENGTH_SHORT).show();
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
                            if (restTime <= 0){
                                yanzhengEt.setEnabled(false);
                                yanzhengEt.setText(null);
                                yanzhengTlayout.setErrorEnabled(false);
                                Toast.makeText(RegisterActivity.this,"验证码失效，请重新发送",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void validateUserName(){
        if (userNameEt.getText().toString().equals("")){
            flag3 = false;
        }else {
            flag3 = true;
        }
        userNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String reg = "^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{1,16}$";//昵称格式：限16个字符，支持中英文、数字、减号或下划线
                Pattern p = Pattern.compile(reg);
                Matcher m = p.matcher(s.toString());
                boolean tem = m.matches();
                if (!tem){
                    userNameTlayout.setError("用户名格式:限16个字符，支持中英文、数字、减号或下划线");
                    userNameTlayout.setErrorEnabled(true);
                    flag3 = false;
                }else {
                    userNameTlayout.setErrorEnabled(false);
                    flag3 = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void validatePassword(){
        if (passwordEt.getText().toString().equals("")){
            flag4 = false;
        }else {
            flag4 = true;
        }
        passwordEt.addTextChangedListener(new TextWatcher() {
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
                    passwordTlayout.setError("密码格式:6-20 位，字母、数字、字符");
                    passwordTlayout.setErrorEnabled(true);
                    flag4 = false;
                }else {
                    passwordTlayout.setErrorEnabled(false);
                    flag4 = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void validateAll(){
        validatePhoneNum();
        validateYanzhang();
        validateUserName();
        validatePassword();
    }
/////////////////////////////////////////////////////输入框验证/////////////////////////////////////////////////


//////////////////////////////////////////////////////点击事件////////////////////////////////////////////////////
    @Override
    public void onClick(View view) {
        String phoneNum = phoneNumEt.getText().toString();
        switch (view.getId()){  //获取验证码
            case R.id.register_yanzhengBtn:
                // 1. 判断手机号是不是11位并且看格式是否合理
                if (!judgePhoneNums(phoneNum)){
                    Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
                    return;
                }

                yanzhengTime = 0;
                yanzhengEt.setEnabled(true);

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

            case R.id.register_Button:  //注册按钮
                validateAll();
                if (flag1&&flag2&&flag3&&flag4&&isyanzheng){
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this,"请将信息正确补全",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.register_isOpenImage:  //密码框是否可视
                if (!isOpenPsw){
                    isOpenPsw = true;
                    openImage.setImageResource(R.drawable.eyeopen);
                    passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEt.setSelection(passwordEt.getText().length());
                }else {
                    isOpenPsw = false;
                    openImage.setImageResource(R.drawable.closeeye);
                    passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEt.setSelection(passwordEt.getText().length());
                }

            default:
                break;
        }
    }
//////////////////////////////////////////////////////点击事件////////////////////////////////////////////////////


    @Override
    public void finish() {  //结束动画
        super.finish();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }
}
