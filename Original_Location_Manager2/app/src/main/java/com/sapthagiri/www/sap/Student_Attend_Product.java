package com.sapthagiri.www.sap;


public  class Student_Attend_Product {
     private String semister;
    private String section;

    public Student_Attend_Product( String semister, String section) {
        this.semister = semister;
        this.section = section;
         }
    public String getSemister() {
        return semister;
    }

    public String getSection() {
        return section;
    }


}