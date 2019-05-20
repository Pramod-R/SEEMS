package com.sapthagiri.www.sap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sapthagiri.www.sap.Student_Event_Retrieve;
import com.sapthagiri.www.sap.Student_Retrieve;


public class Student_Event extends Fragment {

    public static Student_Event newInstance() {
        Student_Event fragment = new Student_Event();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(),Student_Event_Retrieve.class);
        startActivity(intent);
    }
}
