package com.chuangsys_manager.serviceImpl;

import com.chuangsys_manager.dao.CPasswordDao;
import com.chuangsys_manager.entity.User;
import com.chuangsys_manager.service.CpasswordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CPasswordServiceImpl implements CpasswordService {
    @Resource
    CPasswordDao cPasswordDao;

    @Override
    public int changePassword(User user) {
        return cPasswordDao.changePassword(user);
    }
}
