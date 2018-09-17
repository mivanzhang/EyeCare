package com.aiyan.product.controller;

import com.aiyan.product.bean.School;
import com.aiyan.product.common.Constants;
import com.aiyan.product.jpa.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SchoolController {
    @Autowired
    protected SchoolRepository schoolRepository;

    @RequestMapping("school_login")
    public String requestSchoolLogin(ModelMap map) {
        map.addAttribute("sendSMS", "获取验证码");
        return "school/school_login";
    }

    @RequestMapping("try_school_login")
    public String schoolLogin(@RequestParam String action, School school, ModelMap map) {
        //登陆
        if ("login".equals(action)) {
            // 加入一个属性，用来在模板中读取
            Optional<School> optionalSchool = schoolRepository.findSchoolBySchoolName(school.getSchoolName());
            // return模板文件的名称，对应src/main/resources/templates/index.html
            if (optionalSchool.isPresent()) {
                return "common/success";
            }
            return "school/school_login_fail";
        } else if ("login_agreement".equals(action)) {
            return "common/notice";
        } else {
            //发送验证码
            map.addAttribute("schoolName", school.getSchoolName());
            map.addAttribute("phoneNumber", school.getManagerPhoneNumber());
            map.addAttribute("sendSMS", "已发送");
            return "school/school_login";

        }
    }

    @RequestMapping("school_register")
    public String schoolRegister(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "school/school_register_step1";
    }

    @RequestMapping("/saveschool")
    public String saveSchool(ModelMap map, School school) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        school.setStatus(Constants.SCHOOL_STATUS_STEP1);
        schoolRepository.save(school);
        map.addAttribute("schoolName", school.getSchoolName());
        map.addAttribute("managerPhoneNumber", school.getManagerPhoneNumber());
        map.addAttribute("managerName", school.getManagerName());
        return "school/school_register_step2";
    }

    @RequestMapping("/verifyschool")
    public String verifyschool(ModelMap map, School school) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        school.setStatus(Constants.SCHOOL_STATUS_JUDGING);
        schoolRepository.save(school);
        return "school/school_register_step2";
    }

    @RequestMapping("/finishInputSchool")
    public String finishInputSchool(ModelMap map, School school) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        school.setStatus(Constants.SCHOOL_STATUS_JUDGING);
        schoolRepository.save(school);
        return "common/success";
    }
}
