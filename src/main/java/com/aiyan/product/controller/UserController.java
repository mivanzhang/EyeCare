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
    public String requestRegister(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        return "register";
    }

    @RequestMapping("/user_register")
    public String userRegister(ModelMap map, User user, HttpSession session, RedirectAttributes attr) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
//        school.setStatus(Constants.SCHOOL_STATUS_JUDGING);
//        schoolRepository.save(school);
        Optional<User> userOptional = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
        if (!userOptional.isPresent()) {
            user.setRole(Constants.USER_ROLE_COMMON_USER);
            user = userRepository.save(user);
        } else {
            user = userOptional.get();
        }
        session.setAttribute("token", user.getToken());
        switch (user.getRole()) {
            case Constants.USER_ROLE_COMMON_USER:

                break;
            case Constants.USER_ROLE_DOCTOR:
                break;

            case Constants.USER_ROLE_SCHOOL_MANAGER:
                map.put("user", user);
                Optional<School> schoolOptional = schoolRepository.findSchoolByManagerPhoneNumber(user.getPhoneNumber());
                if (schoolOptional.isPresent()) {
                    List<Student> students = schoolOptional.get().getStudentList();
                    if (students == null || students.size() < 1) {
                        attr.addFlashAttribute("id", -1);
                        return "redirect:edit_student";
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


}
