package com.aiyan.product.controller;

import com.aiyan.product.bean.School;
import com.aiyan.product.bean.User;
import com.aiyan.product.common.Constants;
import com.aiyan.product.jpa.SchoolRepository;
import com.aiyan.product.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    protected UserRepository userRepository;

    @RequestMapping("/register")
    public String requestRegister(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        return "register";
    }

    @RequestMapping("/user_register")
    public String userRegister(ModelMap map, User user) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
//        school.setStatus(Constants.SCHOOL_STATUS_JUDGING);
//        schoolRepository.save(school);
        if (!userRepository.findUserByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            user.setRole(Constants.USER_ROLE_COMMON_USER);
            userRepository.save(user);
        }
        return "common/success";
    }
}
