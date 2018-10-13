package com.aiyan.product.bean;


import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Transactional
public class Doctor {
    @Id
    @GeneratedValue
    private int doctorId;
    @Column(nullable = false)
    private String hospitalName;
    @Column(nullable = false)
    private String managerName;
    @Column(nullable = false,unique = true)
    private String managerPhoneNumber;
    //单位证明函
    private String authrize;
    private String doctorDocument;
    private String idCardPath;
    private int status;

    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name = "school_doctor_list", joinColumns = @JoinColumn(name = "doctor_id"), inverseJoinColumns = @JoinColumn(name = "school_id"))
    private Set<School> schoolList;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
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

    public String getDoctorDocument() {
        return doctorDocument;
    }

    public void setDoctorDocument(String doctorDocument) {
        this.doctorDocument = doctorDocument;
    }

    public String getIdCardPath() {
        return idCardPath;
    }

    public void setIdCardPath(String idCardPath) {
        this.idCardPath = idCardPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(Set<School> schoolList) {
        this.schoolList = schoolList;
    }
}
