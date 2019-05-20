package com.sapthagiri.www.sap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sapthagiri.www.sap.Faculty_Add_Events;
import com.sapthagiri.www.sap.Faculty_Retrieve;


public class Faculty_Add_Event extends Fragment {

    public static Faculty_Add_Event newInstance() {
        Faculty_Add_Event fragment = new Faculty_Add_Event();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(),Faculty_Add_Events.class);
        startActivity(intent);
    }
}
