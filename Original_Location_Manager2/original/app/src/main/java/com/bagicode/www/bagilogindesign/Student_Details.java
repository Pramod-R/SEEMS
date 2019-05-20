package com.sapthagiri.www.sap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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



public class Student_Details extends AppCompatActivity  implements FacultyFragmentDrawer.OnDrawerListener {

    //this is the JSON Data URL
    FacultyFragmentDrawer drawerFragment;

    //  Fragment_Drawer drawerFragment;
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS="http://192.168.10.110/merit/details.php";

    //a list to store all the products
    ArrayList<product_details> productList;
   // public static product mStudentProduct;
   RecyclerView recyclerView;



    //the recyclerview
    // RecyclerView recyclerView;
    //private ListView rtr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        setContentView(R.layout.student_details);




        //     Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //   drawerFragment = (Fragment_Drawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        // drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        //drawerFragment.setOnDrawerListener((Fragment_Drawer.OnDrawerListener) this);
        //      Toolbar toolbar = findViewById(R.id.toolbar);
        //        setSupportActionBar(toolbar);


        //getting the recyclerview from xml
         recyclerView = findViewById(R.id.list1);
         recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //     rtr = findViewById(R.id.list1);
        //  if(getSupportActionBar()!= null){
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //   }

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        Toolbar mToolbar = findViewById(R.id.toolbar);
        drawerFragment = (FacultyFragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer1);
        drawerFragment.setUp(R.id.fragment_navigation_drawer1, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setOnDrawerListener(this);
         loadProducts();

    }
    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }


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

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject Faculty_Product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new product_details(

                                        Faculty_Product.getString("fullname"),
                                        Faculty_Product.getString("usnno")//      product.getString("password"),
                                        //    product.getString("confirmpassword")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            Details_Product_Adapter adapter = new Details_Product_Adapter(Student_Details.this, productList);
                             recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("error");
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
