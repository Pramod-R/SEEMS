package com.sapthagiri.www.sap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sapthagiri.www.sap.fragments.Enter_IM_EM;
import com.sapthagiri.www.sap.fragments.Faculty_Info;
import com.sapthagiri.www.sap.fragments.Faculty_Logout;
import com.sapthagiri.www.sap.fragments.Student_Attn_report;
import com.sapthagiri.www.sap.fragments.Take_Attendance;

import static com.sapthagiri.www.sap.fragments.Faculty_Login_Activity.mFacultyProduct;


public class FacultyLandingPageActivity extends AppCompatActivity implements FacultyFragmentDrawer.OnDrawerListener{

    FacultyFragmentDrawer drawerFragment;
    TextView tx0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //intent.getStringExtra("test");
        setContentView(R.layout.faculty_ac_landing_page);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        drawerFragment = (FacultyFragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer1);
        drawerFragment.setUp(R.id.fragment_navigation_drawer1, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setOnDrawerListener(this);
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onDrawerItemClick(String menu, int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = Faculty_Info.newInstance();
                break;
            case 1:
                fragment = Take_Attendance.newInstance();
                break;
            case 2:
                fragment = Student_Attn_report.newInstance();
                break;
            case 3:
                fragment=Enter_IM_EM.newInstance();
                break;
            default:
                fragment = Faculty_Logout.newInstance();
                break;
        }
        switchFragment(fragment);
    }
}
