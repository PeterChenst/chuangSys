package com.example.chuangsys.login;

import android.content.Context;
import com.android.volley.VolleyError;

import java.util.Map;

/****************************
 * author:cst
 * date:2020.4.20
 */
public interface LoginDao {
    public void requestData(Context context, Map<String,String> map, LoginDaoListener listener);//请求用户数据

    public interface LoginDaoListener{
        public void returnData(String response); //响应成功，返回数据
        public void onFail(VolleyError error); //失败
    }
}
