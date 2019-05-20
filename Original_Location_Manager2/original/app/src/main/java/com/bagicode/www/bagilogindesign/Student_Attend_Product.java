package com.sapthagiri.www.sap;


public  class Student_Attend_Product {
    private int aid;
  //  private int rid;
    private String coursecode;
    private String coursename;
    private int classconduct;
    private int classAttend;
    private int percentage;

    public Student_Attend_Product(int aid, String coursecode, String coursename, int classconduct, int classAttend,int percentage) {
       this.aid = aid;
        //this.rid=rid;
        this.coursecode = coursecode;
        this.coursename = coursename;
        this.classconduct = classconduct;
        this.classAttend = classAttend;
       this.percentage = percentage;
    }

   public int getAid() {
        return aid;
    }

//    public int getRid(){
    //    return  rid;
   // }

    public String getCoursecode() {
        return coursecode;
    }

    public String getCoursename() {
        return coursename;
    }

    public int getClassconduct() {
        return classconduct;
    }

    public int getClassAttend() {
        return classAttend;
    }

    public int getPercentage() {
        return percentage;
    }

}