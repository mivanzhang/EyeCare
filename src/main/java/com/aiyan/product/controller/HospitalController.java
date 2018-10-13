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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

@Controller
public class HospitalController {
    @Autowired
    protected DoctorRepository doctorRepository;
    @Autowired
    protected UserRepository userRepository;
    private Doctor mDoctor;

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
        map.addAttribute("sendSMS", "发送验证码");
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
            user.setRole(Constants.USER_ROLE_DOCTOR);
            user.setPhoneNumber(doctor.getManagerPhoneNumber());
            userRepository.save(user);
        }
        // return模板文件的名称，对应src/main/resources/templates/index.html
        doctor.setStatus(Constants.SCHOOL_STATUS_STEP1);
        mDoctor = doctorRepository.save(doctor);
        map.addAttribute("hospitalName", doctor.getHospitalName());
        map.addAttribute("managerPhoneNumber", doctor.getManagerPhoneNumber());
        map.addAttribute("managerName", doctor.getManagerName());
        map.addAttribute("sendSMS", "获取验证码");
        return "hospital/register_step2";
    }


    @RequestMapping("/finishInputHospital")

    public String finishInputSchool(Doctor doctor, @RequestParam("id_card") MultipartFile idCardFile, @RequestParam("prof") MultipartFile prof) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html

        String idCardPath = Constants.DIRECTORY + mDoctor.getManagerPhoneNumber() + "/idCardFile.png";
        if (saveUploadFile(idCardFile, idCardPath)) return "common/fail";

        String authrizePath = Constants.DIRECTORY + mDoctor.getManagerPhoneNumber() + "/prof.png";
        if (saveUploadFile(prof, authrizePath)) return "common/fail";
        mDoctor.setStatus(Constants.SCHOOL_STATUS_JUDGING);
        mDoctor.setAuthrize(authrizePath);
        mDoctor.setDoctorDocument(idCardPath);
        doctorRepository.save(mDoctor);

        return "common/success";
    }

//    @RequestMapping("/input_record")
//    public String inputEyeRecord() {
//        return "hospital/input_record";
//    }

//    @RequestMapping("/finishInputRecord")
//    public String finishInputRecord() {
//
//    }

    private boolean saveUploadFile(MultipartFile prof, String path) {
        if (!prof.isEmpty()) {
            try {
                File file = new File(path);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                }
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(file));
                out.write(prof.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        } else {
            return true;
        }
        return false;
    }


}
