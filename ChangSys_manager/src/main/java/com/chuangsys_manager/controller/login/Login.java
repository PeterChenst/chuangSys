package com.chuangsys_manager.controller.login;

import com.chuangsys_manager.entity.User;
import com.chuangsys_manager.service.LoginService;
import com.chuangsys_manager.service.RegisterService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {
    @Autowired
    LoginService loginService;
    @Autowired
    RegisterService registerService;

    @GetMapping("/getPassword")
    public String getPassword(String phone){
        int isExist = registerService.checkPhone(new User(phone));
        if (isExist <= 0){
            return "手机号未注册";
        }else {
            String password = loginService.getPassword(new User(phone));
            return password;
        }

    }

}
