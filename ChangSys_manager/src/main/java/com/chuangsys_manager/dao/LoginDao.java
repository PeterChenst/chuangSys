package com.chuangsys_manager.dao;

import com.chuangsys_manager.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {
    public String getPassword(User user);
}
