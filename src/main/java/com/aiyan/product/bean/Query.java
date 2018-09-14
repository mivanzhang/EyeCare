package com.aiyan.product.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class Query {
    private String schoolName;
    private String studentNo;
    private String birethday;
    @NotEmpty(message="用户名不能为空")
    private String name;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getBirethday() {
        return birethday;
    }

    public void setBirethday(String birethday) {
        this.birethday = birethday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
