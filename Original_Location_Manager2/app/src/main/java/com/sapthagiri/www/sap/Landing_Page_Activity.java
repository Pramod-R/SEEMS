package com.sapthagiri.www.sap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sapthagiri.www.sap.activity.SampleActivity;
import com.sapthagiri.www.sap.fragments.Student_Event;
import com.sapthagiri.www.sap.fragments.Student_Info;
import com.sapthagiri.www.sap.fragments.Student_Logout;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class Landing_Page_Activity extends AppCompatActivity implements Fragment_Drawer.OnDrawerListener{

    Fragment_Drawer drawerFragment;
    TextView tx1,tx2;
    ImageView click;
    private SliderLayout sliderLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_landing_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        setTitle("");
        click=(ImageView) findViewById(R.id.checkin);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        drawerFragment = (Fragment_Drawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setOnDrawerListener(this);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Landing_Page_Activity.this, MapsActivity.class);
                startActivity(i);


            }}
        );

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
                fragment = Student_Info.newInstance();
                break;
            case 1:
                fragment=Student_Event.newInstance();
                break;

            default:
                fragment =Student_Logout.newInstance();
                break;
        }
        switchFragment(fragment);
    }

}
