package com.aiyan.product.bean;

import com.aiyan.product.common.Constants;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue
    private int userId;
    private String userName;
    @Column(nullable = false)
    private String phoneNumber;
    private String weiXinId;
    @Column(nullable = false)
    private String token;

    @ManyToMany
    @JoinTable(name = "user_watcher_list", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> studentIdList;

    private int role = Constants.USER_ROLE_COMMON_USER;

    public User() {
        token = UUID.randomUUID().toString().substring(0, 16);
    }

    public List<Student> getStudentIdList() {
        return studentIdList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStudentIdList(List<Student> studentIdList) {
        this.studentIdList = studentIdList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWeiXinId() {
        return weiXinId;
    }

    public void setWeiXinId(String weiXinId) {
        this.weiXinId = weiXinId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
