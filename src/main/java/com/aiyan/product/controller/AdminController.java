package com.aiyan.product.controller;

import com.aiyan.product.bean.*;
import com.aiyan.product.common.Constants;
import com.aiyan.product.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected SchoolRepository schoolRepository;
    @Autowired
    protected DoctorRepository doctorRepository;

    @RequestMapping("/admin_home")
    public String index(ModelMap map, HttpSession session) {
        if (!isAdmin(map, session)) {
            return "common/error";
        }
        // 加入一个属性，用来在模板中读取
        List<School> schoolList = new ArrayList<>();
        Optional<List<School>> judgingStatusSchoolList = schoolRepository.findSchoolByStatus(Constants.STATUS_JUDGING);
        Optional<List<School>> step1StatusSchoolList = schoolRepository.findSchoolByStatus(Constants.STATUS_STEP1);
        if (judgingStatusSchoolList.isPresent()) {
            schoolList.addAll(judgingStatusSchoolList.get());
        }
        if (step1StatusSchoolList.isPresent()) {
            schoolList.addAll(step1StatusSchoolList.get());
        }
        map.put("schools", schoolList);

        List<Doctor> doctorList = new ArrayList<>();
        Optional<List<Doctor>> judgingStatusDoctorList = doctorRepository.findDoctorByStatus(Constants.STATUS_JUDGING);
        Optional<List<Doctor>> step1StatusDoctorList = doctorRepository.findDoctorByStatus(Constants.STATUS_STEP1);
        if (judgingStatusDoctorList.isPresent()) {
            doctorList.addAll(judgingStatusDoctorList.get());
        }
        if (step1StatusDoctorList.isPresent()) {
            doctorList.addAll(step1StatusDoctorList.get());
        }
        map.put("doctors", doctorList);
        return "admin/admin_home";
    }

    @RequestMapping(value = "/doctor_approve/{id}", method = RequestMethod.GET)
    public String doctorApprove(ModelMap map, @PathVariable int id, HttpSession session) {
        if (!isAdmin(map, session)) {
            return "common/error";
        }
        Optional<Doctor> doctorOptional = doctorRepository.findDoctorByDoctorId(id);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setStatus(Constants.STATUS_SUCCESS);
            doctorRepository.save(doctor);
        } else {
            map.put("message", "验光师不存在");
            return "common/error";
        }
        map.put("message", "已经通过");
        return "admin/success";
    }

    @RequestMapping(value = "/doctor_decline/{id}", method = RequestMethod.GET)
    public String doctorDecline(ModelMap map, @PathVariable int id, HttpSession session) {
        if (!isAdmin(map, session)) {
            return "common/error";
        }
        Optional<Doctor> doctorOptional = doctorRepository.findDoctorByDoctorId(id);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setStatus(Constants.STATUS_FIAL);
            doctorRepository.save(doctor);
        } else {
            map.put("message", "验光师不存在");
            return "common/error";
        }
        map.put("message", "审核不通过");
        return "admin/success";
    }

    @RequestMapping(value = "/school_approve/{id}", method = RequestMethod.GET)
    public String schoolApprove(ModelMap map, @PathVariable int id, HttpSession session) {
        if (!isAdmin(map, session)) {
            return "common/error";
        }
        Optional<School> schoolOptional = schoolRepository.findSchoolBySchoolId(id);
        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            school.setStatus(Constants.STATUS_SUCCESS);
            schoolRepository.save(school);
        } else {
            map.put("message", "学校不存在");
            return "common/error";
        }
        map.put("message", "审核通过");
        return "admin/success";
    }

    @RequestMapping(value = "/school_decline/{id}", method = RequestMethod.GET)
    public String schoolDecline(ModelMap map, @PathVariable int id, HttpSession session) {
        if (!isAdmin(map, session)) {
            return "common/error";
        }
        Optional<School> schoolOptional = schoolRepository.findSchoolBySchoolId(id);
        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            school.setStatus(Constants.STATUS_FIAL);
            schoolRepository.save(school);
        } else {
            map.put("message", "学校不存在");
            return "common/error";
        }
        map.put("message", "审核不通过");
        return "admin/success";
    }

    private boolean isAdmin(ModelMap map, HttpSession session) {
        String userToken = (String) session.getAttribute("token");
        Optional<User> userOptional = userRepository.findUserByToken(userToken);
        if (!userOptional.isPresent()) {
            map.put("message", "用户不存在");
            return false;
        }
        if (userOptional.get().getRole() == Constants.USER_ROLE_SUPER_MANGER) {
            return true;
        }
        map.put("message", "非法访问，用户不是管理员");
        return false;
    }

}
