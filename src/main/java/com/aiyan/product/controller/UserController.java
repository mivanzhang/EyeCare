package com.aiyan.product.controller;

import com.aiyan.product.bean.School;
import com.aiyan.product.bean.Student;
import com.aiyan.product.bean.User;
import com.aiyan.product.common.Constants;
import com.aiyan.product.jpa.SchoolRepository;
import com.aiyan.product.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected SchoolRepository schoolRepository;

    @RequestMapping("/register")
    public String requestRegister(ModelMap map, HttpSession session) {
        // 加入一个属性，用来在模板中读取
        Optional<User> optionalUser = loginValid(session);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return getNextPage(user, map);
        }
        map.addAttribute("sendSMS", "发送短信");
        return "register";
    }


    private String getNextPage(User user, ModelMap map) {
        switch (user.getRole()) {
            case Constants.USER_ROLE_COMMON_USER:

                break;
            case Constants.USER_ROLE_SUPER_MANGER:
                return "redirect:admin_home";
            case Constants.USER_ROLE_DOCTOR:
                return "redirect:/school_list";

            case Constants.USER_ROLE_SCHOOL_MANAGER:
                map.put("user", user);
                Optional<School> schoolOptional = schoolRepository.findSchoolByManagerPhoneNumber(user.getPhoneNumber());
                if (schoolOptional.isPresent()) {
                    School school = schoolOptional.get();
                    if (school.getStatus() == Constants.STATUS_STEP1) {
                        map.addAttribute("schoolName", school.getSchoolName());
                        map.addAttribute("managerPhoneNumber", school.getManagerPhoneNumber());
                        map.addAttribute("managerName", school.getManagerName());
                        map.addAttribute("sendSMS", "获取验证码");
                        return "school/school_register_step2";
                    } else if (schoolOptional.get().getStatus() == Constants.STATUS_JUDGING) {
                        map.put("message", "注册成功，等待审核，快速审核电话：" + Constants.USER_ROLE_SUPER_MANGER_PHONE);
                        return "common/success";
                    }

                    List<Student> students = schoolOptional.get().getStudentList();
                    if (students == null || students.size() < 1) {
//                        attr.addFlashAttribute("id", -1);
                        return "redirect:addStudent";
                    }
                    map.put("students", students);
                    return "school/school_student_list";
                }
                return "user_register";
            default:
                break;
        }
        return "common/success";
    }

    @RequestMapping("/user_register")
    public String userRegister(ModelMap map, User user, HttpSession session, @RequestParam String action) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
//        school.setStatus(Constants.STATUS_JUDGING);
//        schoolRepository.save(school);
        if ("".equals(action) || action.length() < 1) {
            //发送验证码
            map.addAttribute("phoneNumber", user.getPhoneNumber());
            map.addAttribute("sendSMS", "已发送");
            return "register";
        }

        Optional<User> userOptional = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
        if (!userOptional.isPresent()) {
            user.setRole(Constants.USER_ROLE_COMMON_USER);
            user = userRepository.save(user);
        } else {
            user = userOptional.get();
        }
        session.setAttribute("token", user.getToken());
        return getNextPage(user, map);
    }

    private Optional<User> loginValid(HttpSession session) {
        String userToken = (String) session.getAttribute("token");
        Optional<User> userOptional = userRepository.findUserByToken(userToken);
        return userOptional;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("token");
        session.invalidate();
        return "index";
    }

}
