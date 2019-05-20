package com.sapthagiri.www.sap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sapthagiri.www.sap.Student_Report;


public class Student_Attn_report extends Fragment {

    public static Student_Attn_report newInstance() {
       Student_Attn_report fragment = new Student_Attn_report();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), Student_Report.class);
        //mStudentAttendProductList.clear();
        startActivity(intent);

    }
}
