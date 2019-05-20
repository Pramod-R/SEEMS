package com.sapthagiri.www.sap;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.sapthagiri.www.sap.R;
import com.sapthagiri.www.sap.adapters.TabAdapter;

public class LoginHome extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.addTab(tabLayout.newTab().setText("Student"));
        tabLayout.addTab(tabLayout.newTab().setText("Faculty"));

        viewPager = (ViewPager) findViewById(R.id.viewpager1);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs1);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
