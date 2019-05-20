package com.sapthagiri.www.sap;


public class Student_Result_Product extends Student_Result_Retrieve {
    private int Sid;
    private String SubCode;
    private String Subject;
    private int IM;
    private int EM;
    private int Total;
    private String Grade;

    public Student_Result_Product(int Sid, String SubCode, String Subject, int IM, int EM, int Total, String Grade) {
        this.Sid = Sid;
        this.SubCode = SubCode;
        this.Subject = Subject;
        this.IM = IM;
        this.EM = EM;
        this.Total = Total;
        this.Grade=Grade;
    }

    public int getSid() {
        return Sid;
    }

    public String getSubCode() {
        return SubCode;
    }

    public String getSubject() {
        return Subject;
    }

    public int getIM() {
        return IM;
    }

    public int getEM() {
        return EM;
    }

    public int getTotal() {
        return Total;
    }
    public String getGrade() {
        return Grade;
    }


}