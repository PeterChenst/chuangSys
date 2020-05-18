package com.chuangsys_manager.serviceImpl;

import com.chuangsys_manager.dao.LoginDao;
import com.chuangsys_manager.entity.User;
import com.chuangsys_manager.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    LoginDao loginDao;

    @Override
    public String getPassword(User user) {
        return loginDao.getPassword(user);
    }
}
