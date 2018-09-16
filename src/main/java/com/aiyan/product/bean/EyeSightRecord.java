package com.aiyan.product.bean;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
@Table
public class EyeSightRecord {
    @Id
    @GeneratedValue
    private String recordId;
    private String schoolId;
    @Column(nullable = false)
    private String studentId;
    @Column(nullable = false)
    private String doctorId;
    private String date;
    private String advice;
    //瞳距
    private String IPD;


    private String history;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getIPD() {
        return IPD;
    }

    public void setIPD(String IPD) {
        this.IPD = IPD;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
