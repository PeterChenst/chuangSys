package com.example.chuangsys.login;

import android.content.Context;

import java.util.Map;

/****************************
 * author:cst
 * date:2020.4.20
 */
public interface LoginPresent {
    public void requestData(Context ctx, Map<String,String> map); //调用dao层请求数据
}
