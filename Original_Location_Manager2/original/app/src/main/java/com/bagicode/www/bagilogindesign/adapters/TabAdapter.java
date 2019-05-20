package com.sapthagiri.www.sap.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sapthagiri.www.sap.fragments.Faculty_Login_Activity;
import com.sapthagiri.www.sap.fragments.Student_Login_Activity;

public class TabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    private String title[] = {"Student", "Faculty"};

    public TabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               Student_Login_Activity tab1 = new Student_Login_Activity();
                return tab1;
            case 1:
                Faculty_Login_Activity tab2 = new Faculty_Login_Activity();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}