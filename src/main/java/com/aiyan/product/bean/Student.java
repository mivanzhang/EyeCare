package com.aiyan.product.bean;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Student {
    @Id
    @GeneratedValue
    private String studentId;
    @ManyToMany
    @JoinTable(name = "user_watcher_list", joinColumns = @JoinColumn(name = "student_id "), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> watcherList;
    //三年级几班
    @Column(nullable = false)
    private String userName;
    private String gradeNum;
    private String classNum;
    @Column(nullable = false)
    private String studentNo;
    private boolean isWearGlass;
    private String leftEyeDegree;
    private String rightEyeDegree;
    private String birthDay;
    private int age;
    private boolean sex;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getLeftEyeDegree() {
        return leftEyeDegree;
    }

    public void setLeftEyeDegree(String leftEyeDegree) {
        this.leftEyeDegree = leftEyeDegree;
    }

    public String getRightEyeDegree() {
        return rightEyeDegree;
    }

    public void setRightEyeDegree(String rightEyeDegree) {
        this.rightEyeDegree = rightEyeDegree;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
