package com.aiyan.product.bean;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
@Table
public class EyeSightRecord {
    @Id
    @GeneratedValue
    private int recordId;
    private String schoolId;
    @Column(nullable = false)
    private String studentId;
    @Column(nullable = false)
    private String doctorId;
    private String date;
    private String advice;
    //瞳距
    private String IPD;

    private int leftEyeId;
    private int rightEyeId;

    private String previceRecordId;

    private boolean isRedGreenColourBlindness;

    private String history;

    public int getLeftEyeId() {
        return leftEyeId;
    }

    public void setLeftEyeId(int leftEyeId) {
        this.leftEyeId = leftEyeId;
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

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getRightEyeId() {
        return rightEyeId;
    }

    public void setRightEyeId(int rightEyeId) {
        this.rightEyeId = rightEyeId;
    }

    public String getPreviceRecordId() {
        return previceRecordId;
    }

    public void setPreviceRecordId(String previceRecordId) {
        this.previceRecordId = previceRecordId;
    }

    public boolean isRedGreenColourBlindness() {
        return isRedGreenColourBlindness;
    }

    public void setRedGreenColourBlindness(boolean redGreenColourBlindness) {
        isRedGreenColourBlindness = redGreenColourBlindness;
    }
    @Entity
    @Table
    public class Eye {
        @Id
        @GeneratedValue
        private int eyeId;
        //0代表正常，1代表有bug
        private boolean weakSight;
        //斜视
        private boolean strabismus;

        private String sight;
        private String sightWithGlass;
        //球镜
        private String S;
        //柱镜
        private String C;
        //轴位
        private String A;

        private String axiallength;
        //角膜厚度
        private String cornealThickness;
        //        前房深度
        private String chamberDepth;
        //        晶体厚度
        private String crystalThickness;
        //        玻璃体厚度
        private String glassThickness;

        public boolean isWeakSight() {
            return weakSight;
        }

        public void setWeakSight(boolean weakSight) {
            this.weakSight = weakSight;
        }

        public boolean isStrabismus() {
            return strabismus;
        }

        public void setStrabismus(boolean strabismus) {
            this.strabismus = strabismus;
        }

        public String getSight() {
            return sight;
        }

        public void setSight(String sight) {
            this.sight = sight;
        }

        public String getSightWithGlass() {
            return sightWithGlass;
        }

        public void setSightWithGlass(String sightWithGlass) {
            this.sightWithGlass = sightWithGlass;
        }

        public String getS() {
            return S;
        }

        public void setS(String s) {
            S = s;
        }

        public String getC() {
            return C;
        }

        public void setC(String c) {
            C = c;
        }

        public String getA() {
            return A;
        }

        public void setA(String a) {
            A = a;
        }

        public String getAxiallength() {
            return axiallength;
        }

        public void setAxiallength(String axiallength) {
            this.axiallength = axiallength;
        }

        public String getCornealThickness() {
            return cornealThickness;
        }

        public void setCornealThickness(String cornealThickness) {
            this.cornealThickness = cornealThickness;
        }

        public String getChamberDepth() {
            return chamberDepth;
        }

        public void setChamberDepth(String chamberDepth) {
            this.chamberDepth = chamberDepth;
        }

        public String getCrystalThickness() {
            return crystalThickness;
        }

        public void setCrystalThickness(String crystalThickness) {
            this.crystalThickness = crystalThickness;
        }

        public String getGlassThickness() {
            return glassThickness;
        }

        public void setGlassThickness(String glassThickness) {
            this.glassThickness = glassThickness;
        }

        public int getEyeId() {
            return eyeId;
        }

        public void setEyeId(int eyeId) {
            this.eyeId = eyeId;
        }
    }
}
