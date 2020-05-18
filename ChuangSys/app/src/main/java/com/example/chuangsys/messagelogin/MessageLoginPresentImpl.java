package com.example.chuangsys.messagelogin;
/*
 * author:cst
 *
 * */
public class MessageLoginPresentImpl implements MessageLoginPresent, MessageLoginDao.MessageDaoListener {
    MessageLoginDao messageLoginDao;
    MessageLoginView messageLoginView;

    public MessageLoginPresentImpl(MessageLoginView messageLoginView) {
        this.messageLoginView = messageLoginView;
        messageLoginDao = new MessageLoginDaoImpl();
    }

    @Override
    public void postPhone(String phoneNum) {
        messageLoginDao.postPhone(phoneNum,this);
    }

    @Override
    public void getResult(String response) {
        messageLoginView.getResult(response);
    }

}
