package com.sapthagiri.www.sap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.sapthagiri.www.sap.Faculty_Retrieve;


public class Faculty_Info extends Fragment {

    public static Faculty_Info newInstance() {
        Faculty_Info fragment = new Faculty_Info();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(),Faculty_Retrieve.class);
        startActivity(intent);
    }
}
