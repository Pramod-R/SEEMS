package com.sapthagiri.www.sap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sapthagiri.www.sap.Student_Attend_Product;
import com.sapthagiri.www.sap.Student_Attend_Retrieve;

//import static com.bagicode.www.bagilogindesign.Student_Login_Activity.mStudentAttendProductList;


public class View_Attendence extends Fragment {
    public static View_Attendence getInstance(String title) {
        View_Attendence fragment = new View_Attendence();

        Bundle args = new Bundle();
        args.putString("Attendance",title);
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(),Student_Attend_Retrieve.class);
       // mStudentAttendProduct.clear();
        startActivity(intent);
      //  intent.cle
    }
}
