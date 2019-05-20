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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sapthagiri.www.sap.fragments.Student_Info;
import com.sapthagiri.www.sap.fragments.View_Attendence;
import com.sapthagiri.www.sap.fragments.View_My_Results;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.sapthagiri.www.sap.fragments.Student_Login_Activity.mStudentProduct;


public class Student_Retrieve extends AppCompatActivity  implements Fragment_Drawer.OnDrawerListener {
    Fragment_Drawer drawerFragment;
    //this is the JSON Data URL
  //  Fragment_Drawer drawerFragment;
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS="http://cmpbijnor.navigem.net/SIS_API/retrieve.php";

    //a list to store all the products
    ArrayList<product> productList;
    private TextView tx1,tx2,tx3,tx4,tx5,tx6,tx7,tx8,tx0;


    //the recyclerview
   // RecyclerView recyclerView;
    //private ListView rtr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        setContentView(R.layout.student_retrieve);
        tx0=(TextView)findViewById(R.id.txt0);
        tx1=(TextView)findViewById(R.id.txt1);
        tx2=(TextView)findViewById(R.id.txt2);
        tx3=(TextView)findViewById(R.id.txt3);
        tx4=(TextView)findViewById(R.id.txt4);
        tx5=(TextView)findViewById(R.id.txt5);
        tx6=(TextView)findViewById(R.id.txt6);
     //   tx7=(TextView)findViewById(R.id.txt7);
       // tx8=(TextView)findViewById(R.id.txt8);
        tx0.setText(" "+mStudentProduct.getRid());
        tx1.setText(mStudentProduct.getFullname());
        tx2.setText(mStudentProduct.getUsnno());
        tx3.setText(mStudentProduct.getDob());
        tx4.setText(mStudentProduct.getEmail());
        tx5.setText(mStudentProduct.getGender());
        tx6.setText(mStudentProduct.getPhonenumber());
       // tx7.setText(mStudentProduct.getPassword());
        //tx8.setText(mStudentProduct.getConfirmpassword());






        //     Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //   drawerFragment = (Fragment_Drawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        // drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        //drawerFragment.setOnDrawerListener((Fragment_Drawer.OnDrawerListener) this);
        //      Toolbar toolbar = findViewById(R.id.toolbar);
        //        setSupportActionBar(toolbar);


        //getting the recyclerview from xml
        //  recyclerView = findViewById(R.id.list1);
        // recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //     rtr = findViewById(R.id.list1);
        //  if(getSupportActionBar()!= null){
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //   }

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview

        Toolbar mToolbar = findViewById(R.id.toolbar);
        drawerFragment = (Fragment_Drawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
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
                fragment = Student_Info.newInstance();
                loadProducts();
                break;
            case 1:
                fragment = View_Attendence.getInstance("Attendance");
                break;
            default:
                fragment = View_My_Results.newInstance();
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
                                productList.add(new product(
                                        Faculty_Product.getInt("rid"),
                                        Faculty_Product.getString("fullname"),
                                        Faculty_Product.getString("usnno"),
                                        Faculty_Product.getString("dob"),
                                        Faculty_Product.getString("email"),
                                        Faculty_Product.getString("gender"),
                                        Faculty_Product.getString("phonenumber")
                                  //      product.getString("password"),
                                    //    product.getString("confirmpassword")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ProductAdapter adapter = new ProductAdapter(Student_Retrieve.this, productList);
                              // recyclerView.setAdapter(adapter);
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