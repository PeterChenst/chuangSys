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
    public void returnData(String response) {
        loginView.returnData(response);
    }

    @Override
    public void onFail(VolleyError error) {

    }

    @Override
    public void requestData(Context context, Map<String, String> map) {
        loginDao.requestData(context,map,this);
    }
}
