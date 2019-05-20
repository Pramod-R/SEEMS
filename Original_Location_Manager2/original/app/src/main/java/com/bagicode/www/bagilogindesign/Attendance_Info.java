package com.sapthagiri.www.sap;

public class Attendance_Info {
    private String subcode;
    private Integer class_attended;
    private Integer class_held;
    private Integer perc;

    public Attendance_Info(String subcode, Integer class_attended, Integer class_held, Integer perc){
        this.subcode = subcode;
        this.class_attended = class_attended;
        this.class_held = class_held;
        this.perc = perc;
    }

    public String getSubCode() {
        return subcode;
    }

    public int getClass_held() {
        return class_held;
    }

    public int getClass_attended() {
        return class_attended;
    }

    public int getPerc() {
        return perc;
    }


}
