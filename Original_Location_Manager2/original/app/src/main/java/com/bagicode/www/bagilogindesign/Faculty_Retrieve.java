package com.sapthagiri.www.sap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
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

import static com.sapthagiri.www.sap.fragments.Faculty_Login_Activity.mFacultyProduct;

public class Faculty_Retrieve extends AppCompatActivity implements FacultyFragmentDrawer.OnDrawerListener  {
   // Fragment_Drawer drawerFragment;
   FacultyFragmentDrawer drawerFragment;
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS="http://cmpbijnor.navigem.net/SIS_API/faculty_retrieve.php";

    //a list to store all the products
    ArrayList<Faculty_product> productList;

    //the recyclerview
    //RecyclerView recyclerView;
    private ListView rtr;
    private TextView tx1,tx2,tx3,tx4,tx5,tx6,tx7,tx8,tx0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.faculty_retreive);
        tx0=(TextView)findViewById(R.id.txtid);
        tx1=(TextView)findViewById(R.id.txtname);
        tx2=(TextView)findViewById(R.id.txtfid);
        tx3=(TextView)findViewById(R.id.txtdob);
        tx4=(TextView)findViewById(R.id.txtemail);
        tx5=(TextView)findViewById(R.id.txtgen);
        tx6=(TextView)findViewById(R.id.txtphno);
        tx7=(TextView)findViewById(R.id.txtpass);
        tx8=(TextView)findViewById(R.id.txtcon);
        tx0.setText(" "+mFacultyProduct.getFid());
        tx1.setText(mFacultyProduct.getFullname());
        tx2.setText(mFacultyProduct.getFacid());
        tx3.setText(mFacultyProduct.getFdob());
        tx4.setText(mFacultyProduct.getFemail());
        tx5.setText(mFacultyProduct.getGender());
        tx6.setText(mFacultyProduct.getPnumber());
        tx7.setText(mFacultyProduct.getPassword());
        tx8.setText(mFacultyProduct.getConfirmpassword());
        Toolbar mToolbar = findViewById(R.id.toolbar);
        drawerFragment = (FacultyFragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setOnDrawerListener(this);

        //  Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //drawerFragment = (Fragment_Drawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        //drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        //drawerFragment.setOnDrawerListener(this);


        //getting the recyclerview from xml
      //  recyclerView = findViewById(R.id.list1);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //     rtr = findViewById(R.id.list1);
     //   if(getSupportActionBar()!= null){
       //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //}



        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();
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
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Faculty_product(
                                        product.getInt("fid"),
                                        product.getString("fullname"),
                                        product.getString("facid"),
                                        product.getString("fdob"),
                                        product.getString("femail"),
                                        product.getString("gender"),
                                        product.getString("pnumber")
                                        //   product.getString("password"),
                                        // product.getString("confirmpassword")

                                ));
                            }


                            //creating adapter object and setting it to recyclerview
                           Faculty_product_adapter adapter = new Faculty_product_adapter(Faculty_Retrieve.this, productList);
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