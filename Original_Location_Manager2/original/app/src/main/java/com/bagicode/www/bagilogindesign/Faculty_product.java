package com.sapthagiri.www.sap;

public class Faculty_product {
    private int fid;
    private String fullname;
    private String facid;
    private String fdob;
    private String femail;
    private String gender;
    private String pnumber;
    private String password;
    private String confirmpassword;

    public Faculty_product(int fid, String fullname, String facid, String fdob, String femail, String gender, String pnumber) {
        this.fid = fid;
        this.fullname = fullname;
        this.facid = facid;
        this.fdob = fdob;
        this.femail = femail;
        this.gender = gender;
        this.pnumber = pnumber;
        this.password = password;
        this.confirmpassword = confirmpassword;

    }

    public int getFid() {
        return fid;
    }

    public String getFullname() {
        return fullname;
    }

    public String getFacid() {
        return facid;
    }

    public String getFdob() {
        return fdob;
    }

    public String getFemail() {
        return femail;
    }

    public String getGender() {
        return gender;
    }
    public String getPnumber() {
        return pnumber;
    }
    public String getPassword() {
        return password;
    }
    public String getConfirmpassword()
    {
        return confirmpassword;
    }



}