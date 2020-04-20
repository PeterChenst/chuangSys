package com.example.chuangsys.login;

import android.content.Context;
import com.android.volley.VolleyError;

import java.util.Map;

/****************************
 * author:cst
 * date:2020.4.20
 */
public class LoginPresentImpl implements LoginPresent,LoginDao.LoginDaoListener {
    @Override
    public void returnData(String response) {

    }

    @Override
    public void onFail(VolleyError error) {

    }

    @Override
    public void requestData(Context ctx, Map<String, String> map) {

    }
}
