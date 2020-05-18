package com.chuangsys_manager.service;

import com.chuangsys_manager.entity.User;

public interface RegisterService {
    int insertUserInfo(User user);
    int checkPhone(User user);
}
