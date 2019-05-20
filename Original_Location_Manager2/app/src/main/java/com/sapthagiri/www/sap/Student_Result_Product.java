package com.sapthagiri.www.sap;


public class Student_Result_Product extends Student_Result_Retrieve {
    private String branch;

    public Student_Result_Product( String branch) {
        this.branch = branch;
       }

    public String getBranch() {
        return branch;
    }
}