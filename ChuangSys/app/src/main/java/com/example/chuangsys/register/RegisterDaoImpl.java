package com.example.chuangsys.register;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.example.chuangsys.util.Const;
import okhttp3.*;
/*
 * author:cst
 *
 * */
public class RegisterDaoImpl implements RegisterDao {
    RegisterDaoListener listener;

    @Override
    public void postRegisterInfo(final String phoneNum, final String username,
                                 final String password, final RegisterDaoListener listener) {
        this.listener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("phoneNum",phoneNum)
                            .add("username",username)
                            .add("password",password)
                            .build();
                    Request request = new Request.Builder()
                            .url(Const.cst + "insertUserInfo")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
//                    Log.d("", "run: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+result);
                    listener.getResult(result);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
