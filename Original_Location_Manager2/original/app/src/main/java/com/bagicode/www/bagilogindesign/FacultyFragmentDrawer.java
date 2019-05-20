package com.sapthagiri.www.sap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.sapthagiri.www.sap.R;

import static com.sapthagiri.www.sap.fragments.Faculty_Login_Activity.mFacultyProduct;

public class FacultyFragmentDrawer extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ListView mListView;
    TextView tx0;
    private OnDrawerListener onDrawerListener;

    public FacultyFragmentDrawer() {
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
        View layout = inflater.inflate(R.layout.faculty_fr_navigation_drawer, container, false);
        mListView = layout.findViewById(R.id.main_navigation);
        //tx0 = (TextView) mListView.findViewById(R.id.txt0);
      //  tx0.setText(mFacultyProduct.getFullname());

        setUpNavigationItems();
        return layout;
    }

    private void setUpNavigationItems() {
        final String[] navigationItems = {"Faculty Information", "Take Attendance", "Student Report","Enter IM and EM","Logout"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, navigationItems);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public interface OnDrawerListener {
        void onDrawerItemClick(String menu, int position);
    }
}
