package com.example.chuangsys.login;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.chuangsys.R;

/****************************
 * author:cst
 * date:2020.4.20
 */
public class LoginViewImpl extends Activity implements LoginView {
    LoginPresent loginPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    public void returnData(String str) {

    }

    @Override
    public void onFail() {

    }
}
