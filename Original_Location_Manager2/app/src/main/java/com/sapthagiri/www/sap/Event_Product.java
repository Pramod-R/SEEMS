package com.sapthagiri.www.sap;

public class Event_Product extends Student_Event_Retrieve {
    private String branch;
    private String semester;
    private String section;
    private String eventdate;
    private String eventdesc;

    public Event_Product( String branch,String semester,String section,String eventdate,String eventdesc) {
        this.branch = branch;
        this.semester=semester;
        this.section=section;
        this.eventdate=eventdate;
        this.eventdesc=eventdesc;
    }

    public String getBranch() {
        return branch;
    }
    public String getSemester() {
        return semester;
    } public String getSection() {
        return section;
    } public String getEventdate() {
        return eventdate;
    } public String getEventdesc() {
        return eventdesc;
    }



}
