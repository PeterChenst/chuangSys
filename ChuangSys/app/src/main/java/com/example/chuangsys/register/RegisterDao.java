package com.example.chuangsys.register;
/*
 * author:cst
 *
 * */
public interface RegisterDao {
    public void postRegisterInfo(String phoneNum,String username,String password,RegisterDaoListener listener);

    interface RegisterDaoListener{
        public void getResult(String response);
    }
}
