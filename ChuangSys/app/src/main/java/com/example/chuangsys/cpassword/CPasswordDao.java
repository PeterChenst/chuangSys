package com.example.chuangsys.cpassword;

public interface CPasswordDao {
    public void postChangePassword(String password,String phone,CPasswordDaoListener listener);

    interface CPasswordDaoListener{
        public void getResult(String response);
    }
}
