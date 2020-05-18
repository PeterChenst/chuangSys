package com.example.chuangsys.login;

import android.content.Context;
import android.util.Log;
import com.android.volley.VolleyError;

import com.example.chuangsys.util.Const;
import okhttp3.*;

import java.util.Map;

/****************************
 * author:cst
 * date:2020.4.20
 */
public class LoginDaoImpl implements LoginDao{
    LoginDaoListener listener;

<<<<<<< HEAD
    @Override
    public void requestData(Context context, Map<String, String> map, LoginDaoListener listener) {
        String url = Const.cst + "/login";
        this.listener = listener;
        MyVolley myVolley = MyVolley.newMyVolley();
        myVolley.stringRequestPost(context,url,map,this);
    }

    @Override
    public void onStringSuccess(String response) {  //myvolley框架成功响应操作
        listener.returnData(response);
    }

    @Override
    public void onFailure(VolleyError error) {  //myvolley框架失败响应操作
        listener.onFail(error);
=======

    @Override
    public void login(final String phone, final LoginDaoListener listener) {
        this.listener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Const.cst + "getPassword?phone="+phone)
                            .build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    Log.d("", "run: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+result);
                    listener.getResult(result);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
>>>>>>> cst
    }
}
