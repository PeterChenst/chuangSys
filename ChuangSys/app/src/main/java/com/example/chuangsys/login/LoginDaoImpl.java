package com.example.chuangsys.login;

import android.content.Context;
import com.android.volley.VolleyError;
import com.example.chuangsys.networking.MyVolley;
import com.example.chuangsys.util.Const;

import java.util.Map;

/****************************
 * author:cst
 * date:2020.4.20
 */
public class LoginDaoImpl implements LoginDao, MyVolley.CallBack {
    LoginDaoListener listener;

    @Override
    public void requestData(Context context, Map<String, String> map, LoginDaoListener listener) {
        String url = Const.cst + "/login";
        this.listener = listener;
        MyVolley myVolley = MyVolley.newMyVolley();
        myVolley.stringRequestPost(context,url,map,this);
    }

    @Override
    public void onStringSuccess(String response) {  //myvolley框架成功响应操作

    }

    @Override
    public void onFailure(VolleyError error) {  //myvolley框架失败响应操作

    }
}
