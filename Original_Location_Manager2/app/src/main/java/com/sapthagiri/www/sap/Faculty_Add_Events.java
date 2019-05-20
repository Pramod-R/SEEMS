package com.sapthagiri.www.sap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sapthagiri.www.sap.fragments.Faculty_Add_Event;
import com.sapthagiri.www.sap.fragments.Faculty_Info;
import com.sapthagiri.www.sap.fragments.Faculty_Logout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.sapthagiri.www.sap.fragments.Faculty_Login_Activity.mFacultyProduct;

public class Faculty_Add_Events extends AppCompatActivity implements FacultyFragmentDrawer.OnDrawerListener  {
    // Fragment_Drawer drawerFragment;
    FacultyFragmentDrawer drawerFragment;
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work

    //a list to store all the products
    ArrayList<Faculty_product> productList;

    //the recyclerview
    //RecyclerView recyclerView;
    private ListView rtr;
    private TextView bran,tx0,eventdate,eventdesc;
    Button selectDate;
    TextView date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    String semSelected = null;

    int dayOfMonth;
    Calendar calendar;
    Button mOrder;
    TextView mItemSelected;
    String[] mStringArray;
    boolean[] checkedItems;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    //   public static product mStudentProduct;
    //  private EditText etEmail;
    private String res;
    ArrayList<Integer> mUserItems = new ArrayList<>( );
    ImageView image;
    String s1, s2, s3, s4, s5, s6;
    TextView us, tv;
    //protected List<DataObject> spinnerData;
    String url = "http://192.168.10.41/SAS/API/event.php";
    ArrayList<String> semList = new ArrayList<>( );
    ArrayList<String> secList = new ArrayList<>( );



    Spinner sem, sec, sub, time,us1;
    Button LoginButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.faculty_add_event);
        selectDate = findViewById(R.id.btnDate);
        date = findViewById(R.id.eventdate1);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Faculty_Add_Events.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        tx0 = (TextView) findViewById(R.id.text0);
        tx0.setText(mFacultyProduct.getUname());
        //    facu = (Spinner) findViewById(R.id.faculty1);

        bran = (TextView) findViewById(R.id.branch1);
        bran.setText(mFacultyProduct.getBran());

        sem = (Spinner) findViewById(R.id.semester1);
        sec = (Spinner) findViewById(R.id.section1);
        eventdate = (TextView) findViewById(R.id.eventdate1);
        eventdesc = (EditText) findViewById(R.id.eventdesc1);
        //    us1=(Spinner)findViewById(R.id.usnspn);
        LoginButton = (Button) findViewById(R.id.btn3);
        String URL_PRODUCTS="http://192.168.10.116/integrate/eventsem.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(Faculty_Add_Events.this, response, Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = null;
                            jsonObject = new JSONObject(response);
                            JSONArray val = jsonObject.getJSONArray("semister");
                            //   ArrayList secList;
                            semList=new ArrayList();
                            semList.add("semester");
                            for (int i = 0; i < val.length(); i++)
                            {
                                semList.add(val.getString(i));

                            }
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Faculty_Add_Events.this, android.R.layout.simple_spinner_item,semList);
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

        //adding our stringrequest to queue
        Volley.newRequestQueue(Faculty_Add_Events.this).
                add(stringRequest);

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
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//


                int id = 0;
                //  id =Integer.parseInt( id1.getText().toString());


                //   s1 = (String) facu.getSelectedItem( );
                s1 = tx0.getText().toString();
                s2 = bran.getText().toString();

                s3 = (String) sem.getSelectedItem();

                s4 = (String) sec.getSelectedItem();

                s5 = eventdate.getText().toString();
                s6 = eventdesc.getText().toString();


                insertData(s1, s2, s3, s4, s5, s6);


                Intent intent1 = new Intent(Faculty_Add_Events.this, Faculty_Add_Events.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

            }
        });
        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(position!=0){
                    semSelected =semList.get(position);
                    String URL_PRODUCTS="http://192.168.10.116/integrate/eventsection.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Toast.makeText(Faculty_Add_Events.this, response, Toast.LENGTH_SHORT).show();
                                    JSONObject jsonObject = null;
                                    try{
                                        jsonObject = new JSONObject(response);
                                        JSONArray val = jsonObject.getJSONArray("section");

                                        //   ArrayList secList;
                                        secList=new ArrayList();
                                        secList.add("Section");
                                        for (int i = 0; i < val.length(); i++)
                                        {
                                            secList.add(val.getString(i));

                                        }

                                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Faculty_Add_Events.this, android.R.layout.simple_spinner_item,secList);
                                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        sec.setAdapter(spinnerAdapter);
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

                    //adding our stringrequest to queue
                    Volley.newRequestQueue(Faculty_Add_Events.this).
                            add(stringRequest);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //this method will fetch and parse json
        //to display it in recyclerview
      //  loadProducts();
    }
    private void insertData(String fac, String branch, String seme, String sect, String evedat, String evedesc) {


        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject( );
            jsonBody.put("text0", fac);
            jsonBody.put("branch1", branch);
            jsonBody.put("semester1", seme);
            jsonBody.put("section1", sect);
            jsonBody.put("eventdate1", evedat);
            jsonBody.put("eventdesc1", evedesc);


            final String mRequestBody = jsonBody.toString( );

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>( ) {

                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("message");
                        Toast.makeText(Faculty_Add_Events.this, result, Toast.LENGTH_SHORT).show( );
                    } catch (JSONException e) {
                        e.printStackTrace( );
                    }


                }
            }, new Response.ErrorListener( ) {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString( ));
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Nullable
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception e) {

        }

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
                fragment= Faculty_Add_Event.newInstance();
                break;

            default:
                fragment = Faculty_Logout.newInstance();
                break;
        }
        switchFragment(fragment);
    }



}