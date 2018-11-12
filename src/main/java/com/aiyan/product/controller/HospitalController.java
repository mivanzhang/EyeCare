package com.aiyan.product.controller;

import com.aiyan.product.bean.*;
import com.aiyan.product.common.Constants;
import com.aiyan.product.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class HospitalController {
    @Autowired
    protected DoctorRepository doctorRepository;
    @Autowired
    protected UserRepository userRepository;
    private Doctor mDoctor;
    @Autowired
    protected SchoolRepository schoolRepository;
    @Autowired
    protected StudentRepository studentRepository;

    @Autowired
    protected ResourceLoader resourceLoader;


    @Autowired
    protected EyeSightRecordRepository eyeSightRecordRepository;

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
        doctor.setStatus(Constants.STATUS_STEP1);
        mDoctor = doctorRepository.save(doctor);
        map.addAttribute("hospitalName", doctor.getHospitalName());
        map.addAttribute("managerPhoneNumber", doctor.getManagerPhoneNumber());
        map.addAttribute("managerName", doctor.getManagerName());
        map.addAttribute("sendSMS", "获取验证码");
        return "hospital/register_step2";
    }


    @RequestMapping("/finishInputHospital")

    public String finishInputSchool(ModelMap map, Doctor doctor, HttpServletRequest request, @RequestParam("id_card") MultipartFile idCardFile, @RequestParam("prof") MultipartFile prof
            , @RequestParam("doctorid") MultipartFile doctorDocument) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        String idCardPath = "images/" + mDoctor.getManagerPhoneNumber() + "/idCardFile.png";
        if (saveUploadFile(idCardFile, idCardPath)) return "common/fail";

        String authrizePath = "images/" + mDoctor.getManagerPhoneNumber() + "/prof.png";
        if (saveUploadFile(prof, authrizePath)) return "common/fail";

        String doctorDocumentPath = "images/" + mDoctor.getManagerPhoneNumber() + "/doctorDocument.png";
        if (saveUploadFile(doctorDocument, doctorDocumentPath)) return "common/fail";

        mDoctor.setStatus(Constants.STATUS_JUDGING);
        mDoctor.setAuthrize(authrizePath);
        mDoctor.setIdCardPath(idCardPath);
        mDoctor.setDoctorDocument(doctorDocumentPath);
        doctorRepository.save(mDoctor);
        map.put("message", "等待审核，快速审核电话：" + Constants.USER_ROLE_SUPER_MANGER_PHONE);
        return "common/success";
    }


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

    @RequestMapping("/school_list")
    public String inputEyeRecord(ModelMap map, HttpSession session) {
        if (loginValid(map, session)) return "common/error";
        map.put("schools", mDoctor.getSchoolList());
        return "hospital/school_list";
    }

    private boolean loginValid(ModelMap map, HttpSession session) {
        String userToken = (String) session.getAttribute("token");
        Optional<User> userOptional = userRepository.findUserByToken(userToken);
        Optional<Doctor> optionalDoctor = doctorRepository.findDoctorByManagerPhoneNumber(userOptional.get().getPhoneNumber());

        if (optionalDoctor.isPresent()) {
            mDoctor = optionalDoctor.get();

        } else {
            map.put("message", "验光师不存在");
            return true;
        }

        if (mDoctor.getStatus() <= Constants.STATUS_JUDGING) {
            map.put("message", "验光师还在审核，暂时无法录入信息，快速审核电话：" + Constants.USER_ROLE_SUPER_MANGER_PHONE);
            return true;
        }

        if (!userOptional.isPresent() || mDoctor == null) {
            map.put("message", "登陆用户不存在");
            return true;
        }
        return false;
    }

    @RequestMapping("/studentlist/{id}")
    public String schoolDetai(@PathVariable int id, ModelMap map, HttpSession session) {
        if (loginValid(map, session)) return "common/error";
        Optional<School> schoolOptional = schoolRepository.findSchoolBySchoolId(id);
        if (!schoolOptional.isPresent()) {
            map.put("message", "学校不存在");
            return "common/error";
        }
        School school = schoolOptional.get();
        map.put("students", school.getStudentList());
        map.put("schoolName", school.getSchoolName());
        map.put("schoolId", school.getSchoolId());
        return "hospital/student_list";

    }

    @RequestMapping("/edite_record/{id}/{schoolId}")
    public String editeRecord(@PathVariable int id, ModelMap map, HttpSession session, @PathVariable int schoolId) {
        if (loginValid(map, session)) return "common/error";

        Optional<School> schoolOptional = schoolRepository.findSchoolBySchoolId(schoolId);
        if (!schoolOptional.isPresent()) {
            map.put("message", "学校不存在");
            return "common/error";
        }

        map.put("schoolName", schoolOptional.get().getSchoolName());
        map.put("doctorName", mDoctor.getManagerName());


        Optional<Student> optionalStudent = studentRepository.findStudentByStudentId(id);
        if (!optionalStudent.isPresent()) {
            map.put("message", "学生不存在");
            return "common/error";
        }
        session.setAttribute("schoolId", schoolId);
        session.setAttribute("studentid", id);
        map.put("student", optionalStudent.get());
        Optional<EyeSightRecord> optionalEyeSightRecord = eyeSightRecordRepository.findEyeSightRecordByStudentId(optionalStudent.get().getStudentId());
        if (optionalEyeSightRecord.isPresent()) {
            map.put("record", optionalEyeSightRecord.get());
            session.setAttribute("recordid", optionalEyeSightRecord.get().getRecordId());
        } else {
            map.put("record", new EyeSightRecord());
            session.setAttribute("recordid", -1);
        }
        map.put("student", optionalStudent.get());
        return "hospital/input_record";
    }


    @RequestMapping("/input_record")
    public String inputEyeRecord() {
        return "hospital/input_record";
    }

    @RequestMapping("/finishInputRecord")
    public String finishInputRecord(ModelMap map, HttpSession session, EyeSightRecord eyeSightRecord) {
        if (loginValid(map, session)) return "common/error";
        int previceRecordId = (Integer) session.getAttribute("recordid");
        if (previceRecordId != -1) {
            eyeSightRecord.setPreviceRecordId(previceRecordId);
            Optional<EyeSightRecord> eyeSightRecord1 = eyeSightRecordRepository.findEyeSightRecordByRecordId(previceRecordId);
            eyeSightRecord1.get().setStudentId(-1);
            eyeSightRecordRepository.save(eyeSightRecord1.get());
        }
        eyeSightRecord.setStudentId((Integer) session.getAttribute("studentid"));
        eyeSightRecord.setSchoolId((Integer) session.getAttribute("schoolId"));
        eyeSightRecord.setDoctorId((Integer) mDoctor.getDoctorId());
        eyeSightRecordRepository.save(eyeSightRecord);
        int schoolid = (Integer) session.getAttribute("schoolId");
        return "redirect:/studentlist/" + schoolid;
    }

}
