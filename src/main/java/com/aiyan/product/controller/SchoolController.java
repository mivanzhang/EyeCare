package com.aiyan.product.controller;

import com.aiyan.product.bean.Doctor;
import com.aiyan.product.bean.School;
import com.aiyan.product.bean.Student;
import com.aiyan.product.bean.User;
import com.aiyan.product.common.Constants;
import com.aiyan.product.jpa.DoctorRepository;
import com.aiyan.product.jpa.SchoolRepository;
import com.aiyan.product.jpa.StudentRepository;
import com.aiyan.product.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Controller
public class SchoolController {
    @Autowired
    protected SchoolRepository schoolRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected StudentRepository studentRepository;
    @Autowired
    protected DoctorRepository doctorRepository;
    private School mSchool;


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
            map.addAttribute("managerPhoneNumber", school.getManagerPhoneNumber());
            map.addAttribute("sendSMS", "已发送");
            return "school/school_login";

        }
    }

    @RequestMapping("school_register")
    public String schoolRegister(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("sendSMS", "获取验证码");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "school/school_register_step1";
    }

    @RequestMapping(value = "/saveschool", method = RequestMethod.POST)
    public String saveSchool(ModelMap map, School school, @RequestParam String action, @RequestParam String verifyCode) {

        Optional optionalSchool = schoolRepository.findSchoolByManagerPhoneNumber(school.getManagerPhoneNumber());
        if (optionalSchool.isPresent()) {
            map.put("message", "手机号" + school.getManagerPhoneNumber() + "已经注册，请登陆");
            return "common/success";
        }

        if ("".equals(action) || action.length() < 1) {
            //发送验证码
            map.addAttribute("schoolName", school.getSchoolName());
            map.addAttribute("managerPhoneNumber", school.getManagerPhoneNumber());
            map.addAttribute("sendSMS", "已发送");
            map.addAttribute("managerName", school.getManagerName());
            return "school/school_register_step1";
        }
        //校验验证码
        if (!checkCode(verifyCode)) {
            map.addAttribute("schoolName", school.getSchoolName());
            map.addAttribute("managerPhoneNumber", school.getManagerPhoneNumber());
            map.addAttribute("sendSMS", "验证码错误，重试");
            map.addAttribute("managerName", school.getManagerName());
            return "school/school_register_step1";
        }

        if (userRepository.findUserByPhoneNumber(school.getManagerPhoneNumber()).isPresent()) {

        } else {
            User user = new User();
            user.setPhoneNumber(school.getManagerPhoneNumber());
            user.setRole(Constants.USER_ROLE_SCHOOL_MANAGER);
            userRepository.save(user);
        }
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        school.setStatus(Constants.STATUS_STEP1);
        mSchool = schoolRepository.save(school);
        map.addAttribute("schoolName", school.getSchoolName());
        map.addAttribute("managerPhoneNumber", school.getManagerPhoneNumber());
        map.addAttribute("managerName", school.getManagerName());
        map.addAttribute("sendSMS", "获取验证码");
        return "school/school_register_step2";
    }

    private boolean checkCode(String verifyCode) {
        return true;
    }

//    @RequestMapping("/verifyschool")
//    public String verifyschool(ModelMap map, School school) {
//        // 加入一个属性，用来在模板中读取
//        // return模板文件的名称，对应src/main/resources/templates/index.html
//        school.setStatus(Constants.STATUS_JUDGING);
//        schoolRepository.save(school);
//        return "school/school_register_step2";
//    }


    @RequestMapping("/finishInputSchool")
    public String finishInputSchool(ModelMap map, School school, @RequestParam String action,
                                    @RequestParam("id_card") MultipartFile idCardFile, @RequestParam("prof") MultipartFile prof) {
        String idCardPath = "images/" + school.getManagerPhoneNumber() + "/idCardFile.png";
        if (saveUploadFile(idCardFile, idCardPath)) return "common/fail";

        String authrizePath = "images/"+ school.getManagerPhoneNumber() + "/prof.png";
        if (saveUploadFile(prof, authrizePath)) return "common/fail";


        mSchool.setAuthrize(authrizePath);
        mSchool.setIdCardPath(idCardPath);
        mSchool.setStatus(Constants.STATUS_JUDGING);
        mSchool.setSchoolName(school.getSchoolName());
        mSchool.setManagerPhoneNumber(school.getManagerPhoneNumber());
        mSchool.setManagerName(school.getManagerName());
        mSchool = schoolRepository.save(mSchool);
//        map.put("student", new Student());
//        return "school/school_edite_student";
        map.put("message", "注册成功，等待审核，快速审核电话：" + Constants.USER_ROLE_SUPER_MANGER_PHONE);
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

    @RequestMapping("/edit_student")
    public String editStudent(ModelMap map, @ModelAttribute("id") int id, HttpSession session) {
        Optional<Student> studentOptional = studentRepository.findStudentByStudentId(id);
        Student student = new Student();
        if (studentOptional.isPresent()) {
            student = studentOptional.get();
        }
        map.put("student", student);
        if (schoolValid(map, session)) return "common/error";
        map.put("schoolName", mSchool.getSchoolName());
        return "school/school_edite_student";
    }

    @Transactional
    @RequestMapping("/save_student")
    public String commitStudent(ModelMap map, Student student, @RequestParam int sex, HttpSession session) {
        if (schoolValid(map, session)) return "common/error";
        map.put("student", new Student());
        student.setSex(sex == 1);
        student.setSchool(mSchool);
        studentRepository.save(student);
        List<Student> students = mSchool.getStudentList();
        map.put("students", students);
        return "school/school_student_list";
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteStudent(ModelMap map, @PathVariable int id, HttpSession session) {
        String userToken = (String) session.getAttribute("token");
        Optional<User> userOptional = userRepository.findUserByToken(userToken);
        if (!userOptional.isPresent()) {
            map.put("message", "登陆用户不存在");
            return "common/error";
        }
//        userRepository.deleteUserByUserId(id);
        studentRepository.deleteStudentByStudentId(id);
        map.put("message", "删除成功");
        return "common/success";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateStudent(ModelMap map, @PathVariable Long id, HttpSession session, RedirectAttributes attr) {

        String userToken = (String) session.getAttribute("token");
        if (!userRepository.findUserByToken(userToken).isPresent()) {
            map.put("message", "登陆用户不存在");
            return "common/error";
        }
        attr.addFlashAttribute("id", id);
        return "redirect:/edit_student";
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String managerDoctor(ModelMap map, @PathVariable Long id, HttpSession session, RedirectAttributes attr) {

        String userToken = (String) session.getAttribute("token");
        if (!userRepository.findUserByToken(userToken).isPresent()) {
            map.put("message", "登陆用户不存在");
            return "common/error";
        }
        attr.addFlashAttribute("id", id);
        return "redirect:/edit_student";
    }

    //    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
//    public String schoolPage() {
//        return " school/school_student_list";
//    }
    @RequestMapping("managerdoctor")
    public String managerdoctor(ModelMap map, HttpSession session) {
        if (schoolValid(map, session)) return "common/error";
        map.put("doctors", mSchool.getDoctorList());
        return "school/school_doctor_list";
    }

    private boolean schoolValid(ModelMap map, HttpSession session) {
        String userToken = (String) session.getAttribute("token");
        Optional<User> userOptional = userRepository.findUserByToken(userToken);
        if (!userOptional.isPresent()) {
            map.put("message", "登陆用户不存在");
            return true;
        }
        Optional<School> optionalSchool = schoolRepository.findSchoolByManagerPhoneNumber(userOptional.get().getPhoneNumber());

        if (optionalSchool.isPresent()) {
            mSchool = optionalSchool.get();
        } else {
            map.put("message", "学校不存在");
            return true;
        }

        if (mSchool.getStatus() == Constants.STATUS_STEP1) {
            map.put("message", "等待审核，快速审核电话：" + Constants.USER_ROLE_SUPER_MANGER_PHONE);
            return true;
        } else if (mSchool.getStatus() == Constants.STATUS_JUDGING) {
            map.put("message", "等待审核，快速审核电话：" + Constants.USER_ROLE_SUPER_MANGER_PHONE);
            return true;
        }

        return false;
    }


    @RequestMapping("deleteDoctor/{id}")
    public String deleteDoctor(ModelMap map, @PathVariable int id, HttpSession session) {
        if (schoolValid(map, session)) return "common/error";
        Doctor doctor = new Doctor();
        doctor.setDoctorId(id);
        mSchool.getDoctorList().remove(doctor);
        schoolRepository.save(mSchool);
        map.put("message", "删除成功");
        return "common/success";
    }

    @RequestMapping("addDoctor")
    public String addDoctor() {
        return "school/school_add_doctor";
    }

    @RequestMapping("school_add_doctor")
    public String inputDoctor(ModelMap map, Doctor doctor, HttpSession session) {
        if (schoolValid(map, session)) return "common/error";
        Optional<Doctor> doctorOptional;
        if (doctor.getManagerName() != null) {
            doctorOptional = doctorRepository.findDoctorByManagerName(doctor.getManagerName());
        } else {
            doctorOptional = doctorRepository.findDoctorByManagerPhoneNumber(doctor.getManagerPhoneNumber());
        }

        if (!doctorOptional.isPresent()) {
            map.put("message", "验光师不存在");
            return "common/error";
        }
        mSchool.getDoctorList().add(doctorOptional.get());
        schoolRepository.save(mSchool);
        return "redirect:/managerdoctor";
    }


    @RequestMapping("managerstudent")
    public String managerStudent(ModelMap map, HttpSession session) {
        if (schoolValid(map, session)) return "common/error";
        List<Student> students = mSchool.getStudentList();
        map.put("students", students);
        return "school/school_student_list";
    }

    @RequestMapping("addStudent")
    public String addStudent(ModelMap map, RedirectAttributes attr, HttpSession session) {
        if (schoolValid(map, session)) return "common/error";
        attr.addFlashAttribute("id", -1);
        return "redirect:/edit_student";
    }
}
