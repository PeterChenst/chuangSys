package com.example.chuangsys.messagelogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chuangsys.R;
import com.example.chuangsys.cpassword.CPasswordActivity;
import com.example.chuangsys.login.LoginActivity;
import com.example.chuangsys.register.RegisterActivity;

public class MessageActivity extends AppCompatActivity {
    EditText phoneNumEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_login);

        phoneNumEt = (EditText) findViewById(R.id.messageLogin_phoneNumEt);
        phoneNumEt.clearFocus();

        textViewClick();
    }

    public void textViewClick(){  //文本点击事件
        String cPasswordStr;  //修改密码TextView文本
        cPasswordStr = "输入验证码可修改密码 >";

        SpannableStringBuilder spannableString1 = new SpannableStringBuilder();  //忘记密码点击事件
        spannableString1.append(cPasswordStr);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageActivity.this, CPasswordActivity.class);
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
        spannableString1.setSpan(clickableSpan1,0,cPasswordStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        TextView textView1 = (TextView)findViewById(R.id.messageLogin_cPassword);
        textView1.setHighlightColor(getResources().getColor(android.R.color.transparent));
        textView1.setText(spannableString1);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
    }





    @Override
    public void finish() {  //结束动画
        super.finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
