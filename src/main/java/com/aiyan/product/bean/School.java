package com.aiyan.product.bean;


import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class School {
    @Id
    @GeneratedValue
    private int schoolId;
    @Column(nullable = false)
    private String schoolName;
    @Column(nullable = false)
    private String managerName;
    @Column(nullable = false)
    private String managerPhoneNumber;
    private String authrize;
    private String idCardPath;
    @OneToMany(mappedBy = "school")
    private List<Student> studentList;
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

    private int status;

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
}
