package com.chuangsys_manager.controller.cpassword;

import com.chuangsys_manager.dao.CPasswordDao;
import com.chuangsys_manager.entity.User;
import com.chuangsys_manager.service.CpasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CPassword {
    @Autowired
    CpasswordService cpasswordService;

    @PostMapping("/changePassword")
    public String changePassword(String password,String phone){
        int result = cpasswordService.changePassword(new User(password,phone));
        if (result > 0){
            return "修改成功";
        }else {
            return "修改失败";
        }
    }
}
