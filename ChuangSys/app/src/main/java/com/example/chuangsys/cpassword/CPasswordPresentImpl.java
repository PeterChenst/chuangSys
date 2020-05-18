package com.example.chuangsys.cpassword;

public class CPasswordPresentImpl implements CPasswordPresent, CPasswordDao.CPasswordDaoListener {
    CPasswordDao cPasswordDao;
    CPasswordView cPasswordView;

    public CPasswordPresentImpl(CPasswordView cPasswordView) {
        this.cPasswordView = cPasswordView;
        cPasswordDao = new CPasswordDaoImpl();
    }

    @Override
    public void getResult(String response) {
        cPasswordView.getResult(response);
    }

    @Override
    public void postChangePassword(String password,String phone) {
        cPasswordDao.postChangePassword(password,phone,this);
    }
}
