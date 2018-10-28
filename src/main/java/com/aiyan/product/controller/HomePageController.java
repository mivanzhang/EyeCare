package com.aiyan.product.controller;

import com.aiyan.product.bean.*;
import com.aiyan.product.common.Constants;
import com.aiyan.product.jpa.EyeSightRecordRepository;
import com.aiyan.product.jpa.SchoolRepository;
import com.aiyan.product.jpa.StudentRepository;
import com.aiyan.product.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class HomePageController {


    @Autowired
    protected EyeSightRecordRepository eyeSightRecordRepository;
    @Autowired
    protected StudentRepository studentRepository;
    @Autowired
    protected SchoolRepository schoolRepository;
    @Autowired
    protected UserRepository userRepository;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/")
    public String index(ModelMap map, HttpSession session) {
        // 加入一个属性，用来在模板中读取
        Optional<User> optionalUser = loginValid(session);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            map.addAttribute("name", user.getPhoneNumber() + " 登出");
        } else {
            map.addAttribute("name", "");
        }
        return "index";
    }

    private Optional<User> loginValid(HttpSession session) {
        String userToken = (String) session.getAttribute("token");
        Optional<User> userOptional = userRepository.findUserByToken(userToken);
        return userOptional;
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String query(ModelMap map, @ModelAttribute(value = "query") Query query, HttpSession session) {
        String queryTime = (String) session.getAttribute(Constants.QUERYTIME);
        if (queryTime == null || queryTime.length() < 1) {
            session.setAttribute(Constants.QUERYTIME, "1");
            session.setAttribute(Constants.TIME, System.currentTimeMillis());
        } else {
            int times = 0;
            try {
                times = Integer.parseInt(queryTime);
            } catch (Exception e) {

            }
            if (times <= Constants.MAX_QUERY_TIME) {
                map.put("message", "当日查询次数超过最大，如需查询请联系" + Constants.USER_ROLE_SUPER_MANGER_PHONE);
                return "common/error";
            } else {
                session.setAttribute(Constants.QUERYTIME, times++);
                session.setMaxInactiveInterval(24 * 60 * 60 * 2);
            }
        }
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        Optional<List<Student>> studentOptional = Optional.empty();
        if (query.getSchoolName() != null && query.getSchoolName().length() > 0) {
            Optional<School> optionalSchool = schoolRepository.findSchoolBySchoolName(query.getSchoolName());
            if (optionalSchool.isPresent()) {
                studentOptional = studentRepository.findStudentByNameAndSchool(query.getName(), optionalSchool.get());
            } else {
                map.put("message", "输入有误，学生信息不存在");
                return "common/error";
            }
        }
        //
        if (query.getStudentNo() != null && query.getStudentNo().length() > 0) {
            studentOptional = studentRepository.findStudentByNameAndStudentNo(query.getName(), query.getStudentNo());
        }
        if (query.getBirethday() != null && query.getBirethday().length() > 0) {
            studentOptional = studentRepository.findStudentByNameAndBirthDay(query.getName(), query.getBirethday());
        }
        if (!studentOptional.isPresent()) {
            map.put("message", "输入有误，学生信息不存在");
            return "common/error";
        }
        studentList = studentOptional.get();
        if (studentOptional.get().size() == 1) {
            Optional<EyeSightRecord> eyeSightRecordOptional = eyeSightRecordRepository.findEyeSightRecordByStudentId(studentOptional.get().get(0).getStudentId());
            if (!eyeSightRecordOptional.isPresent()) {
                map.put("message", "学生视力记录不存在");
                return "common/error";
            }
            map.put("record", eyeSightRecordOptional.get());
            map.put("student", studentOptional.get());
            return "show_record";
        } else {
            map.put("students", studentList);
            return "search_student_list";
        }
    }

    private List<Student> studentList = new ArrayList<>();

    @RequestMapping(value = "/searchStudent/{id}", method = RequestMethod.GET)
    public String searchStudent(ModelMap map, @PathVariable int id) {
        Optional<Student> studentOptional = studentRepository.findStudentByStudentId(id);
        // 加入一个属性，用来在模板中读取
        Optional<EyeSightRecord> eyeSightRecordOptional = eyeSightRecordRepository.findEyeSightRecordByStudentId(id);
        if (!eyeSightRecordOptional.isPresent()) {
            map.put("message", "学生视力记录不存在");
            return "common/error";
        }
        map.put("record", eyeSightRecordOptional.get());
        map.put("student", studentOptional.get());
        return "show_record";
    }


    @RequestMapping(value = "/getSmsCode", method = RequestMethod.GET)
    public void sendSMSCode(ModelMap map, String phoneNumber) {
        // 加入一个属性，用来在模板中读取
        System.out.println(phoneNumber);
        // return模板文件的名称，对应src/main/resources/templates/index.html
    }

    @RequestMapping(value = "/user_login", method = RequestMethod.GET)
    public String userLogin(ModelMap map) {
        return "redirect:register";
    }

    @RequestMapping(value = "/school_register", method = RequestMethod.GET)
    public String schoolRegister(ModelMap map) {
        map.put("sendSMS", "获取验证码");
        return "school/school_register_step1";
    }

    @RequestMapping(value = "/hospital_register", method = RequestMethod.GET)
    public String hospitalRegister(ModelMap map) {
        map.put("sendSMS", "获取验证码");
        return "hospital/register_step1";
    }

}
