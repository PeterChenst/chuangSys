package com.example.chuangsys.login;

import android.content.Context;
import com.android.volley.VolleyError;

import java.util.Map;

/****************************
 * author:cst
 * date:2020.4.20
 */
public class LoginPresentImpl implements LoginPresent,LoginDao.LoginDaoListener {
    LoginDao loginDao;
    LoginView loginView;

    public LoginPresentImpl(LoginView loginView) {
        this.loginView = loginView;
        loginDao = new LoginDaoImpl();
    }

    @Override
    public void getResult(String response) {
        loginView.getResult(response);
    }

    @Override
    public void login(String phone) {
        loginDao.login(phone,this);
    }
}
