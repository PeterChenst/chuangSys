package com.example.chuangsys.cpassword;

import com.example.chuangsys.util.Const;
import okhttp3.*;

public class CPasswordDaoImpl implements CPasswordDao {
    CPasswordDaoListener listener;

    @Override
    public void postChangePassword(final String password, final String phone, final CPasswordDaoListener listener) {
        this.listener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("password",password)
                            .add("phone",phone)
                            .build();
                    Request request = new Request.Builder()
                            .url(Const.cst + "changePassword")
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
