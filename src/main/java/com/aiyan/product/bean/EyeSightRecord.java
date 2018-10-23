package com.aiyan.product.bean;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
@Table
public class EyeSightRecord {
    @Id
    @GeneratedValue
    private int recordId;
    private int schoolId;
    @Column(nullable = false)
    private int studentId;
    @Column(nullable = false)
    private int doctorId;
    private String doctorName;
    private String date;
    private String advice;
    //瞳距
    private String IPD;

    private int isWearGlass;

    private int previceRecordId;
    private int nextRecordId;

    private int isRedGreenColourBlindness;


    //0代表正常，1代表有bug
    private int leftweakSight;
    //斜视
    private int leftstrabismus;

    private String leftsight;
    private String leftsightWithGlass;
    //球镜
    private String leftS;
    //柱镜
    private String leftC;
    //轴位
    private String leftA;

    private String leftaxiallength;
    //角膜厚度
    private String leftcornealThickness;
    //        前房深度
    private String leftchamberDepth;
    //        晶体厚度
    private String leftcrystalThickness;
    //        玻璃体厚度
    private String leftglassThickness;



    //0代表正常，1代表有bug
    private int rightweakSight;
    //斜视
    private int rightstrabismus;

    private String rightsight;
    private String rightsightWithGlass;
    //球镜
    private String rightS;
    //柱镜
    private String rightC;
    //轴位
    private String rightA;

    private String rightaxiallength;
    //角膜厚度
    private String rightcornealThickness;
    //        前房深度
    private String rightchamberDepth;
    //        晶体厚度
    private String rightcrystalThickness;
    //        玻璃体厚度
    private String rightglassThickness;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
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


    public int getIsWearGlass() {
        return isWearGlass;
    }

    public void setIsWearGlass(int isWearGlass) {
        this.isWearGlass = isWearGlass;
    }

    public int getPreviceRecordId() {
        return previceRecordId;
    }

    public void setPreviceRecordId(int previceRecordId) {
        this.previceRecordId = previceRecordId;
    }

    public int getIsRedGreenColourBlindness() {
        return isRedGreenColourBlindness;
    }

    public void setIsRedGreenColourBlindness(int isRedGreenColourBlindness) {
        this.isRedGreenColourBlindness = isRedGreenColourBlindness;
    }

    public int getLeftweakSight() {
        return leftweakSight;
    }

    public void setLeftweakSight(int leftweakSight) {
        this.leftweakSight = leftweakSight;
    }

    public int getLeftstrabismus() {
        return leftstrabismus;
    }

    public void setLeftstrabismus(int leftstrabismus) {
        this.leftstrabismus = leftstrabismus;
    }

    public String getLeftsight() {
        return leftsight;
    }

    public void setLeftsight(String leftsight) {
        this.leftsight = leftsight;
    }

    public String getLeftsightWithGlass() {
        return leftsightWithGlass;
    }

    public void setLeftsightWithGlass(String leftsightWithGlass) {
        this.leftsightWithGlass = leftsightWithGlass;
    }

    public String getLeftS() {
        return leftS;
    }

    public void setLeftS(String leftS) {
        this.leftS = leftS;
    }

    public String getLeftC() {
        return leftC;
    }

    public void setLeftC(String leftC) {
        this.leftC = leftC;
    }

    public String getLeftA() {
        return leftA;
    }

    public void setLeftA(String leftA) {
        this.leftA = leftA;
    }

    public String getLeftaxiallength() {
        return leftaxiallength;
    }

    public void setLeftaxiallength(String leftaxiallength) {
        this.leftaxiallength = leftaxiallength;
    }

    public String getLeftcornealThickness() {
        return leftcornealThickness;
    }

    public void setLeftcornealThickness(String leftcornealThickness) {
        this.leftcornealThickness = leftcornealThickness;
    }

    public String getLeftchamberDepth() {
        return leftchamberDepth;
    }

    public void setLeftchamberDepth(String leftchamberDepth) {
        this.leftchamberDepth = leftchamberDepth;
    }

    public String getLeftcrystalThickness() {
        return leftcrystalThickness;
    }

    public void setLeftcrystalThickness(String leftcrystalThickness) {
        this.leftcrystalThickness = leftcrystalThickness;
    }

    public String getLeftglassThickness() {
        return leftglassThickness;
    }

    public void setLeftglassThickness(String leftglassThickness) {
        this.leftglassThickness = leftglassThickness;
    }


    public int getRightstrabismus() {
        return rightstrabismus;
    }

    public void setRightstrabismus(int rightstrabismus) {
        this.rightstrabismus = rightstrabismus;
    }

    public String getRightsight() {
        return rightsight;
    }

    public void setRightsight(String rightsight) {
        this.rightsight = rightsight;
    }

    public String getRightsightWithGlass() {
        return rightsightWithGlass;
    }

    public void setRightsightWithGlass(String rightsightWithGlass) {
        this.rightsightWithGlass = rightsightWithGlass;
    }

    public String getRightS() {
        return rightS;
    }

    public void setRightS(String rightS) {
        this.rightS = rightS;
    }

    public String getRightC() {
        return rightC;
    }

    public void setRightC(String rightC) {
        this.rightC = rightC;
    }

    public String getRightA() {
        return rightA;
    }

    public void setRightA(String rightA) {
        this.rightA = rightA;
    }

    public String getRightaxiallength() {
        return rightaxiallength;
    }

    public void setRightaxiallength(String rightaxiallength) {
        this.rightaxiallength = rightaxiallength;
    }

    public String getRightcornealThickness() {
        return rightcornealThickness;
    }

    public void setRightcornealThickness(String rightcornealThickness) {
        this.rightcornealThickness = rightcornealThickness;
    }

    public String getRightchamberDepth() {
        return rightchamberDepth;
    }

    public void setRightchamberDepth(String rightchamberDepth) {
        this.rightchamberDepth = rightchamberDepth;
    }

    public String getRightcrystalThickness() {
        return rightcrystalThickness;
    }

    public void setRightcrystalThickness(String rightcrystalThickness) {
        this.rightcrystalThickness = rightcrystalThickness;
    }

    public String getRightglassThickness() {
        return rightglassThickness;
    }

    public void setRightglassThickness(String rightglassThickness) {
        this.rightglassThickness = rightglassThickness;
    }

    public int getRightweakSight() {
        return rightweakSight;
    }

    public void setRightweakSight(int rightweakSight) {
        this.rightweakSight = rightweakSight;
    }

    public int getNextRecordId() {
        return nextRecordId;
    }

    public void setNextRecordId(int nextRecordId) {
        this.nextRecordId = nextRecordId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
