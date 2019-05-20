package com.sapthagiri.www.sap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapthagiri.www.sap.fragments.Student_Info;
import com.sapthagiri.www.sap.fragments.View_Attendence;
import com.sapthagiri.www.sap.fragments.View_My_Results;


import static android.graphics.drawable.ClipDrawable.VERTICAL;
import static com.sapthagiri.www.sap.fragments.Student_Login_Activity.mStudentProduct;

public  class Student_Attend_Retrieve extends AppCompatActivity implements Fragment_Drawer.OnDrawerListener{
    Fragment_Drawer drawerFragment;
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS="http://cmpbijnor.navigem.net/SIS_API/attendretrieve.php";

    //a list to store all the products
    //public  ArrayList<Student_Attend_Product>  mStudentAttendProductList ;
    int cid;
    List<Student_Attend_Product> productList;

    //the recyclerview
    RecyclerView recyclerView;
    private ListView rtr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.student_attend_retrieve);
        // mStudentAttendProductList = new ArrayList<>();

        //getting the recyclerview from xml
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


        //this method will fetch and parse json
        //to display it in recyclerview

        Toolbar mToolbar = findViewById(R.id.toolbar);
        drawerFragment = (Fragment_Drawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setOnDrawerListener(this);
        loadProducts();

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
        //   mStudentProduct.getRid();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object

                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList .add(new Student_Attend_Product(
                                        product.getInt("aid"),
                                        product.getString("coursecode"),
                                        product.getString("coursename"),
                                        product.getInt("classconduct"),
                                        product.getInt("classAttend"),
                                        product.getInt("percentage")
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            Student_Attend_Product_Adapter adapter = new Student_Attend_Product_Adapter(Student_Attend_Retrieve.this, productList);
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
                }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("rid", String.valueOf(mStudentProduct.getRid()));
                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}