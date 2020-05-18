package com.chuangsys_manager.serviceImpl;

import com.chuangsys_manager.dao.RegisterDao;
import com.chuangsys_manager.entity.User;
import com.chuangsys_manager.service.RegisterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    RegisterDao registerDao;

    @Override
    public int insertUserInfo(User user) {
        return registerDao.insertUserInfo(user);
    }

    @Override
    public int checkPhone(User user) {
        return registerDao.checkPhone(user);
    }
}
