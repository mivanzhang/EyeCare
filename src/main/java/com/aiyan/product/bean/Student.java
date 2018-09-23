package com.aiyan.product.bean;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Student {
    @Id
    @GeneratedValue
    private int studentId;
    @ManyToMany
    @JoinTable(name = "user_watcher_list", joinColumns = @JoinColumn(name = "student_id "), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> watcherList;
    //三年级几班
    @Column(nullable = false)
    private String name;
    private String gradeNum;
    private String classNum;
    @Column(nullable = false)
    private String studentNo;
    private boolean isWearGlass;
    private String birthDay;
    private String weight;
    private String height;
    private boolean sex;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public List<User> getWatcherList() {
        return watcherList;
    }

    public void setWatcherList(List<User> watcherList) {
        this.watcherList = watcherList;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradeNum() {
        return gradeNum;
    }

    public void setGradeNum(String gradeNum) {
        this.gradeNum = gradeNum;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public boolean isWearGlass() {
        return isWearGlass;
    }

    public void setWearGlass(boolean wearGlass) {
        isWearGlass = wearGlass;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
