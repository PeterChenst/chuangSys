package com.example.chuangsys.messagelogin;

import com.example.chuangsys.register.RegisterDao;

/*
 * author:cst
 *
 * */
public interface MessageLoginDao {
    public void postPhone(String phoneNum,MessageDaoListener listener);

    interface MessageDaoListener{
        public void getResult(String response);
    }
}
