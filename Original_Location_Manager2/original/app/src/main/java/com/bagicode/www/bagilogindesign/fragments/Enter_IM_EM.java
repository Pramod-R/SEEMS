package com.sapthagiri.www.sap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.sapthagiri.www.sap.Student_Details;


public class Enter_IM_EM extends Fragment {

    public static Enter_IM_EM newInstance() {
        Enter_IM_EM comingSoonFragment = new Enter_IM_EM();
        Bundle bundle = new Bundle();
        comingSoonFragment.setArguments(bundle);
        return comingSoonFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(),Student_Details.class);
        startActivity(intent);
    }
}
