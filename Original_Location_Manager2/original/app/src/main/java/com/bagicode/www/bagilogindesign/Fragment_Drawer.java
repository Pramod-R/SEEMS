package com.sapthagiri.www.sap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Fragment_Drawer extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ListView mListView;
    private OnDrawerListener onDrawerListener;

    public Fragment_Drawer() {
        //must need default constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setOnDrawerListener(OnDrawerListener onDrawerListener) {
        this.onDrawerListener = onDrawerListener;
    }

   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fr_navigation_drawer, container, false);
        mListView = (ListView) layout.findViewById(R.id.main_navigation);
        setUpNavigationItems();
        return layout;
    }


    private void setUpNavigationItems() {
        final String[] navigationItems = {"Student Information", "View My Attendence", "View My Results","Logout"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, navigationItems);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String menu = navigationItems[position];
                mDrawerLayout.closeDrawer(Gravity.START, false);
                onDrawerListener.onDrawerItemClick(menu, position);
            }
        });
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }
   // final NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);

    public interface OnDrawerListener {
        void onDrawerItemClick(String menu, int position);
    }
}
