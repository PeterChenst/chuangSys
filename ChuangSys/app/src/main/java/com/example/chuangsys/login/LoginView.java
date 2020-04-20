package com.example.chuangsys.login;

/****************************
 * author:cst
 * date:2020.4.20
 */
public interface LoginView {
    public void returnData(String str); //响应成功返回数据
    public void onFail();
}
