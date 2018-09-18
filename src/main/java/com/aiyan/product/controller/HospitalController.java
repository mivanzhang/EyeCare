package com.aiyan.product.controller;

import com.aiyan.product.bean.Doctor;
import com.aiyan.product.bean.School;
import com.aiyan.product.bean.User;
import com.aiyan.product.common.Constants;
import com.aiyan.product.jpa.DoctorRepository;
import com.aiyan.product.jpa.SchoolRepository;
import com.aiyan.product.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HospitalController {
    @Autowired
    protected DoctorRepository doctorRepository;
    @Autowired
    protected UserRepository userRepository;

    @RequestMapping("hospital_login")
    public String requestSchoolLogin(ModelMap map) {
        map.addAttribute("sendSMS", "获取验证码");
        return "hospital/hospital_login";
    }

    @RequestMapping("try_hospital_login")
    public String schoolLogin(@RequestParam String action, ModelMap map, @RequestParam String phoneNumber, @RequestParam String name) {
        //登陆
        if ("login".equals(action)) {
            // 加入一个属性，用来在模板中读取
//            Optional<School> optionalSchool = schoolRepository.findSchoolBySchoolName(school.getSchoolName());
            // return模板文件的名称，对应src/main/resources/templates/index.html
//            if (optionalSchool.isPresent()) {
//                return "common/success";
//            }
            return "hospital/login_fail";
        } else if ("login_agreement".equals(action)) {
            return "common/notice";
        } else {
            //发送验证码
            map.addAttribute("phoneNumber", phoneNumber);
            map.addAttribute("name", name);
            map.addAttribute("sendSMS", "已发送");
            return "hospital/hospital_login";
        }
    }


    @RequestMapping("hospital_register")
    public String schoolRegister(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "hospital/register_step1";
    }

    @RequestMapping("/savehospital")
    public String saveSchool(ModelMap map, Doctor doctor, @RequestParam String action) {
        // 加入一个属性，用来在模板中读取
        if ("".equals(action) || action.length() < 1) {
            //发送验证码
            map.addAttribute("hospitalName", doctor.getHospitalName());
            map.addAttribute("managerPhoneNumber", doctor.getManagerPhoneNumber());
            map.addAttribute("sendSMS", "已发送");
            map.addAttribute("managerName", doctor.getManagerName());
            return "hospital/register_step1";
        }
        if (userRepository.findUserByPhoneNumber(doctor.getManagerPhoneNumber()).isPresent()) {

        } else {
            User user = new User();
            user.setPhoneNumber(doctor.getManagerPhoneNumber());
            userRepository.save(user);
        }
        // return模板文件的名称，对应src/main/resources/templates/index.html
        doctor.setStatus(Constants.SCHOOL_STATUS_STEP1);
        doctorRepository.save(doctor);
        map.addAttribute("hospitalName", doctor.getHospitalName());
        map.addAttribute("managerPhoneNumber", doctor.getManagerPhoneNumber());
        map.addAttribute("managerName", doctor.getManagerName());
        map.addAttribute("sendSMS", "获取验证码");
        return "hospital/register_step2";
    }

    @RequestMapping("/finishInputHospital")
    public String finishInputSchool(ModelMap map, Doctor doctor, @RequestParam String action) {
        if ("".equals(action) || action.length() < 1) {
            //发送验证码
            map.addAttribute("hospitalName", doctor.getHospitalName());
            map.addAttribute("managerPhoneNumber", doctor.getManagerPhoneNumber());
            map.addAttribute("sendSMS", "已发送");
            map.addAttribute("managerName", doctor.getManagerName());
            return "hospital/register_step2";
        }
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        doctor.setStatus(Constants.SCHOOL_STATUS_JUDGING);
        doctorRepository.save(doctor);
        return "common/success";
    }
}
