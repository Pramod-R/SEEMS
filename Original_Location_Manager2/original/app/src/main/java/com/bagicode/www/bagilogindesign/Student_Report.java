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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sapthagiri.www.sap.fragments.Enter_IM_EM;
import com.sapthagiri.www.sap.fragments.Faculty_Info;
import com.sapthagiri.www.sap.fragments.Faculty_Logout;
import com.sapthagiri.www.sap.fragments.Student_Attn_report;
import com.sapthagiri.www.sap.fragments.Take_Attendance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Student_Report extends AppCompatActivity implements FacultyFragmentDrawer.OnDrawerListener{
    public static final int CONNECTION_TIMEOUT = 10000;
    FacultyFragmentDrawer drawerFragment;

    public static final int READ_TIMEOUT = 15000;
    private String res;
    Button btn;
    ArrayList<Integer> mUserItems = new ArrayList<>( );
    ImageView image;
    List<Attendance_Info> productList;


    //the recyclerview
    RecyclerView recyclerView;
    Spinner  sem, usn, sub, time,us1;
    ArrayList<String> usnList = new ArrayList<>( );
    String semSelected = null;
    String usnSelected=null;
    ArrayList<String> semList = new ArrayList<>( );
    //public static ArrayList<String> subcodeList = new ArrayList<String>();
    //public static ArrayList<Integer> classheldList = new ArrayList<Integer>();
    // public static ArrayList<Integer> classattendList = new ArrayList<Integer>();
    //  public static ArrayList<Integer> classperc = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_report);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        String URL_PRODUCTS="http://192.168.10.110/integrate/semester1.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(Student_Report.this, response, Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = null;
                            jsonObject = new JSONObject(response);
                            JSONArray val = jsonObject.getJSONArray("student_semester");
                            semList=new ArrayList();
                            semList.add("Semester");
                            for (int i = 0; i < val.length(); i++)
                            {
                                semList.add(val.getString(i));

                            }
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Student_Report.this, android.R.layout.simple_spinner_item,semList);
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sem.setAdapter(spinnerAdapter);
                            spinnerAdapter.notifyDataSetChanged();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("error");
                    }
                }) {

        };
        Volley.newRequestQueue(Student_Report.this).
                add(stringRequest);
        sem = (Spinner) findViewById(R.id.semester1);
        usn = (Spinner) findViewById(R.id.usn1);
        //btn=(Button)findViewById(R.id.btn1);
        recyclerView = findViewById(R.id.list1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //     rtr = findViewById(R.id.list1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);



        //initializing the productlist`11
        productList = new ArrayList<>();
        Toolbar mToolbar = findViewById(R.id.toolbar);
        drawerFragment = (FacultyFragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setOnDrawerListener(this);

        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(position!=0){
                    semSelected =semList.get(position);
                    String URL_PRODUCTS="http://192.168.10.110/integrate/studentusn.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Toast.makeText(Student_Report.this, response, Toast.LENGTH_SHORT).show();
                                        JSONObject jsonObject = null;
                                        jsonObject = new JSONObject(response);
                                        JSONArray val = jsonObject.getJSONArray("student_usn");
                                        usnList=new ArrayList();
                                        usnList.add("USN");
                                        for (int i = 0; i < val.length(); i++)
                                        {
                                            usnList.add(val.getString(i));

                                        }
                                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Student_Report.this, android.R.layout.simple_spinner_item,usnList);
                                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        usn.setAdapter(spinnerAdapter);
                                        spinnerAdapter.notifyDataSetChanged();
                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.print("error");
                                }
                            }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("semester1", semSelected);
                            return params;
                        }
                    };

                    Volley.newRequestQueue(Student_Report.this).
                            add(stringRequest);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        usn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                if(position!=0){
                    usnSelected =usnList.get(position);
                    String URL_PRODUCTS="http://192.168.10.110/integrate/studentreport.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JSONObject JObject= null;
                                    try {
                                        JObject=new JSONObject(response);
                                        Toast.makeText(Student_Report.this, response, Toast.LENGTH_SHORT).show();
                                        //JsonArray array = new JSONArray(response);
                                        JSONArray subcode = JObject.getJSONArray("subcode");
                                        ArrayList<String> subcodeList = new ArrayList();
                                        for (int i = 0; i < subcode.length(); i++) {
                                            subcodeList.add(subcode.getString(i));

                                        }
                                        JSONArray class_held = JObject.getJSONArray("class_held");
                                        ArrayList<Integer> classheldList = new ArrayList();
                                        for (int i = 0; i < class_held.length(); i++) {
                                            classheldList.add(class_held.getInt(i));

                                        }
                                        JSONArray class_attended = JObject.getJSONArray("class_attended");
                                        ArrayList<Integer> classattendList = new ArrayList();
                                        for (int i = 0; i < class_attended.length(); i++) {
                                            classattendList.add(class_attended.getInt(i));

                                        }
                                        JSONArray perc = JObject.getJSONArray("perc");
                                        ArrayList<Integer> classperc = new ArrayList();
                                        for (int i = 0; i < perc.length(); i++) {
                                            classperc.add(perc.getInt(i));

                                        }

                                        ArrayList<Attendance_Info> attendance_infoArrayList = new ArrayList<>();
                                        for(int i = 0;i<perc.length();i++){
                                            attendance_infoArrayList.add(new Attendance_Info(subcodeList.get(i),classattendList.get(i),classheldList.get(i),classperc.get(i)));
                                        }
                                        Student_Report_Adapter adapter = new Student_Report_Adapter(Student_Report.this, attendance_infoArrayList);
                                        recyclerView.setAdapter(adapter);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.print("error");
                                }
                            }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("semester1", semSelected);
                            params.put("usn1",usnSelected);
                            return params;
                        }
                    };

                    Volley.newRequestQueue(Student_Report.this).
                            add(stringRequest);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            default:
                fragment = Faculty_Logout.newInstance();
                break;
        }
        switchFragment(fragment);
    }


}


