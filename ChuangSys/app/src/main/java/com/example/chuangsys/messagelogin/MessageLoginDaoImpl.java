package com.example.chuangsys.messagelogin;

import com.example.chuangsys.util.Const;
import okhttp3.*;

/*
 * author:cst
 *
 * */
public class MessageLoginDaoImpl implements MessageLoginDao{
    MessageDaoListener listener;
    @Override
    public void postPhone(final String phoneNum, final MessageDaoListener listener) {
        this.listener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("phoneNum",phoneNum)
                            .build();
                    Request request = new Request.Builder()
                            .url(Const.cst + "checkRegister")
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
