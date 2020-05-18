package com.chuangsys_manager.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;  //'流水号'
    private String phone;  //'电话号码,唯一'
    private int qqnumber;  //'QQ号，唯一'
    private int score;  //'用户积分'
    private int status;  //'用户状态,0为正常,1为封号'
    private int isprincipal;  //'是否负责人，1是，0否'
    private int school_id;  //'所属学校id，外键'
    private int club_id;  //'所属社团id，外键'
    private String account;  //'用户虚拟姓名'
    private String sex;  //'性别'
    private String name;  //'用户真实姓名'
    private String password;  //'账号密码'
    private String school;  //'学校'
    private String headpicture;  //'用户头像路径'
    private String bf1;  //'备用字段1'
    private String bf2;  //'备用字段2'

    public User(String phone, String account, String password) {
        this.phone = phone;
        this.account = account;
        this.password = password;
    }

    public User(String phone) {
        this.phone = phone;
    }

    public User(String password,String phone) {
        this.phone = phone;
        this.password = password;
    }
}
