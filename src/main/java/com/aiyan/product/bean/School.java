package com.aiyan.product.bean;


import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Transactional
public class School {
    @Id
    @GeneratedValue
    private int schoolId;
    @Column(nullable = false)
    private String schoolName;
    @Column(nullable = false)
    private String managerName;
    @Column(nullable = false, unique = true)
    private String managerPhoneNumber;
    private String authrize;
    private String idCardPath;
    //表示学校的状态
    private int status;
    @OneToMany(mappedBy = "school", fetch = FetchType.EAGER)
    private List<Student> studentList;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "school_doctor_list", joinColumns = @JoinColumn(name = "school_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    private Set<Doctor> doctorList = new LinkedHashSet();


    public String getIdCardPath() {
        return idCardPath;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void setIdCardPath(String idCardPath) {
        this.idCardPath = idCardPath;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhoneNumber() {
        return managerPhoneNumber;
    }

    public void setManagerPhoneNumber(String managerPhoneNumber) {
        this.managerPhoneNumber = managerPhoneNumber;
    }

    public String getAuthrize() {
        return authrize;
    }

    public void setAuthrize(String authrize) {
        this.authrize = authrize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Doctor> getDoctorList() {
        if (doctorList == null) {
            return new HashSet<>();
        }
        return doctorList;
    }

    public void setDoctorList(Set<Doctor> doctorList) {
        this.doctorList = doctorList;
    }
}
