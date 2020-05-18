package com.example.chuangsys.login;

import android.content.Context;
import com.android.volley.VolleyError;

import java.util.Map;

/****************************
 * author:cst
 * date:2020.4.20
 */
public interface LoginDao {
    public void login(String phone,LoginDaoListener listener); //请求用户数据

    public interface LoginDaoListener{
        public void getResult(String response);
    }
}
