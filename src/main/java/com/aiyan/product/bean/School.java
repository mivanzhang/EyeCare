package com.aiyan.product.bean;

import java.util.List;

public class School {
    private String schoolId;
    private String schoolName;
    private List<String> managerList;
    private String authrize;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<String> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<String> managerList) {
        this.managerList = managerList;
    }
}
