package com.example.chuangsys.cpassword;

import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.chuangsys.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPasswordActivity extends AppCompatActivity {
    EditText passwordEt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpassword);

        passwordEt1 = (EditText) findViewById(R.id.cPassword_passwordEt);
        passwordEt1.clearFocus();
    }

    public void initView(){
        
    }


/////////////////////////////////////////////////////输入框验证/////////////////////////////////////////////////
//    /**
//     * 显示错误提示，并获取焦点
//     * @param textInputLayout
//     * @param error
//     */
//    private void showError(TextInputLayout textInputLayout, String error){
//        textInputLayout.setError(error);
//        textInputLayout.getEditText().setFocusable(true);
//        textInputLayout.getEditText().setFocusableInTouchMode(true);
//        textInputLayout.getEditText().requestFocus();
//    }
//    private boolean validatePhoneNum(String str){
//        if(str.equals("")){
//            showError(phoneTlayout,"手机号不能为空");
//            return false;
//        }else if (!judgePhoneNums(str)){
//            showError(phoneTlayout,"手机号格式错误");
//            return false;
//        }
//        return true;
//    }
//    private boolean validateYanzhang(String str){
//        if (str.equals("")){
//            showError(yanzhengTlayout,"验证码不能为空");
//            return false;
//        }
//        return true;
//    }
//    private boolean validateUserName(String str){
//        String reg = "^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{1,16}$";//昵称格式：限16个字符，支持中英文、数字、减号或下划线
//        Pattern p = Pattern.compile(reg);
//        Matcher m = p.matcher(str.trim());
//        boolean tem = m.matches();
//        if(str.equals("")){
//            showError(userNameTlayout,"用户名不能为空");
//            return false;
//        }else if (!tem){
//            showError(userNameTlayout,"用户名格式:限16个字符，支持中英文、数字、减号或下划线");
//            return false;
//        }
//        return true;
//    }
//    private boolean validatePassword(String str){
//        //6-20 位，字母、数字、字符
//        String reg= "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){6,20}$";
//        Pattern w = Pattern.compile(reg);
//        Matcher wk = w.matcher(str);
//        boolean pw = wk.matches();
//        if(str.equals("")){
//            showError(passwordTlayout,"密码不能为空");
//            return false;
//        }else if (!pw){
//            showError(passwordTlayout,"密码格式:6-20 位，字母、数字、字符");
//            return false;
//        }
//        return true;
//    }
//    private boolean validateAll(){
//        String phoneNumStr = phoneTlayout.getEditText().getText().toString();
//        phoneTlayout.setErrorEnabled(false);
//        String yanzhengStr = yanzhengTlayout.getEditText().getText().toString();
//        yanzhengTlayout.setErrorEnabled(false);
//        String userNameStr = userNameTlayout.getEditText().getText().toString();
//        userNameTlayout.setErrorEnabled(false);
//        String passwordStr = passwordTlayout.getEditText().getText().toString();
//        passwordTlayout.setErrorEnabled(false);
//        boolean flag1 = validatePhoneNum(phoneNumStr);
//        boolean flag2 = validateYanzhang(yanzhengStr);
//        boolean flag3 = validateUserName(userNameStr);
//        boolean flag4 = validatePassword(passwordStr);
//        if (flag1&&flag2&&flag3&&flag4){
//            return true;
//        }
//        return false;
//    }
/////////////////////////////////////////////////////输入框验证/////////////////////////////////////////////////


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }
}
