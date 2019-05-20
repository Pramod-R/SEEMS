package com.sapthagiri.www.sap;

public class product_details {
    private String fullname;
    private String usnno;


    public product_details( String fullname, String usnno) {
        this.fullname = fullname;
        this.usnno = usnno;

    }



    public String getFullname() {
        return fullname;
    }

    public String getUsnno() {
        return usnno;
    }

}
