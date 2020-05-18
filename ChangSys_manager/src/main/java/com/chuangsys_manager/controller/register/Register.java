package com.chuangsys_manager.controller.register;

import com.chuangsys_manager.entity.User;
import com.chuangsys_manager.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Register {
    @Autowired
    RegisterService registerService;

    @PostMapping("/insertUserInfo")  //用户注册
    public String InsertUserInfo(String phoneNum,String username,String password){
        int isExist = registerService.checkPhone(new User(phoneNum));
        if (isExist > 0){
            return "手机号码已注册";
        }else {
            int result = registerService.insertUserInfo(new User(phoneNum,username,password));
            if (result >= 1) {
                return "添加成功";
            } else {
                return "添加失败";
            }
        }
    }

    @PostMapping("/checkRegister")  //检查用户是否注册
    public String checkRegister(String phoneNum){
        int isExist = registerService.checkPhone(new User(phoneNum));
//        System.out.print("!!!!!!!!!!!"+isExist);
        if (isExist > 0){
            return "手机号码已注册";
        }else {
            return "手机号码还未注册";
        }
    }
}
