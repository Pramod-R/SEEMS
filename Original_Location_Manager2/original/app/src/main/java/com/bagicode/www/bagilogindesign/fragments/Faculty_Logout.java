package com.sapthagiri.www.sap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sapthagiri.www.sap.LoginHome;


public class Faculty_Logout extends Fragment {

    public static Faculty_Logout newInstance() {
        Faculty_Logout fragment = new Faculty_Logout();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), LoginHome.class);
        startActivity(intent);
    }
}
