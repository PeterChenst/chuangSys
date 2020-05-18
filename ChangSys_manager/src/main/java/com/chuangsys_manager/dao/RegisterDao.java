package com.chuangsys_manager.dao;

import com.chuangsys_manager.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterDao {
    int insertUserInfo(User user);
    int checkPhone(User user);  //查询是否有该手机号码
}
