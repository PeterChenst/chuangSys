package com.chuangsys_manager.dao;

import com.chuangsys_manager.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CPasswordDao {
    int changePassword(User user);
}
