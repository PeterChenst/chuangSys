package com.example.chuangsys.register;
/*
 * author:cst
 *
 * */
public class RegisterPresentImpl implements RegisterPresent, RegisterDao.RegisterDaoListener {
    RegisterDao registerDao;
    RegisterView registerView;

    public RegisterPresentImpl(RegisterView registerView) {
        this.registerView = registerView;
        registerDao = new RegisterDaoImpl();
    }

    @Override
    public void getResult(String response) {
        registerView.getResult(response);
    }

    @Override
    public void postRegisterInfo(String phoneNum, String username, String password) {
        registerDao.postRegisterInfo(phoneNum,username,password,this);
    }
}
