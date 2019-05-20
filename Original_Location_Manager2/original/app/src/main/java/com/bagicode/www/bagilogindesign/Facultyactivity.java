package com.sapthagiri.www.sap;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
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
import com.sapthagiri.www.sap.fragments.Enter_IM_EM;
import com.sapthagiri.www.sap.fragments.Faculty_Info;
import com.sapthagiri.www.sap.fragments.Faculty_Logout;
import com.sapthagiri.www.sap.fragments.Student_Attn_report;
import com.sapthagiri.www.sap.fragments.Take_Attendance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sapthagiri.www.sap.fragments.Faculty_Login_Activity.mFacultyProduct;


public class Facultyactivity extends AppCompatActivity  implements FacultyFragmentDrawer.OnDrawerListener{
    FacultyFragmentDrawer drawerFragment;

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
    String url = "http://cmpbijnor.navigem.net/SIS_API/usnnew1.php";


    Spinner  sem, sec, sub, time,us1;
    Button LoginButton;
    private TextView tx1,tx2,tx3,tx4,tx5,tx6,tx7,tx8,tx0;

   // String[] semList=null;
    //String[] sublist=null;
   ArrayList<String> subList = new ArrayList<>( );
    // ArrayList secList=null;
    ArrayList<String> secList = new ArrayList<>( );
    String semSelected = null;
    ArrayList<String> semList = new ArrayList<>( );
    ArrayList<String> usnList = new ArrayList<String>();
    // String[] mStringArray = usnList.toArray(new String[]{});
    // List<String> stockList = new ArrayList<String>();

    // String[] mStringArray = new String[usnList.size()];
    // mStringArray = usnList.toArray(mStringArray);

    //     mStringArray = usnList.toArray(mStringArray);
    String usnSelected=null;
    String secSelected = null;
    String subSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facultyactivity);
//        actionBar.show();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // semList = getResources().getStringArray(R.array.semId);
        //sublist = getResources().getStringArray(R.array.subid);
        tx0 = (TextView) findViewById(R.id.txt0);
        tx0.setText(mFacultyProduct.getFullname());
        //    facu = (Spinner) findViewById(R.id.faculty1);

        sem = (Spinner) findViewById(R.id.semester1);
        sec = (Spinner) findViewById(R.id.section1);
        sub = (Spinner) findViewById(R.id.subcode1);
        time = (Spinner) findViewById(R.id.timings1);
        us = (TextView) findViewById(R.id.usn1);
        //    us1=(Spinner)findViewById(R.id.usnspn);
        LoginButton = (Button) findViewById(R.id.newbtn3);
        mOrder = (Button) findViewById(R.id.newbtn2);
        mItemSelected = (TextView) findViewById(R.id.usn1);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        drawerFragment = (FacultyFragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setOnDrawerListener(this);


        String URL_PRODUCTS="http://cmpbijnor.navigem.net/SIS_API/semester1.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(Facultyactivity.this, response, Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = null;
                            jsonObject = new JSONObject(response);
                            JSONArray val = jsonObject.getJSONArray("student_semester");
                            //   ArrayList secList;
                            semList=new ArrayList();
                            semList.add("Semester");
                            for (int i = 0; i < val.length(); i++)
                            {
                                semList.add(val.getString(i));

                            }
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Facultyactivity.this, android.R.layout.simple_spinner_item,semList);
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
        Volley.newRequestQueue(Facultyactivity.this).
                add(stringRequest);
        //  listItems = getResources( ).getStringArray(R.array.shopping_item);
        checkedItems = new boolean[100];
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Facultyactivity.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(mStringArray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!mUserItems.contains(position)) {
                                mUserItems.add(position);
                            } else {
                                mUserItems.remove(position);

                            }
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            // mUserItems.setItemChecked(i, true);
                            item = item + mStringArray[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ",";
                            }
                        }
                        mItemSelected.setText(item);
                    }
                });
                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");

                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//


                int id = 0;
                //  id =Integer.parseInt( id1.getText().toString());


                //   s1 = (String) facu.getSelectedItem( );
                s1 = tx0.getText().toString();
                s2 = (String) sem.getSelectedItem();

                s3 = (String) sec.getSelectedItem();

                s4 = (String) sub.getSelectedItem();

                s5 = (String) time.getSelectedItem();
                s6 = us.getText().toString();


                insertData(s1, s2, s3, s4, s5, s6);


                Intent intent1 = new Intent(Facultyactivity.this, Facultyactivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

            }
        });
 /*       sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(position!=0){
                    final String semSelected =semList[position];
                  //  final String secSelected=secList[position];
                    String URL_PRODUCTS="http://192.168.1.106/integrate/section.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Toast.makeText(Facultyactivity.this, response, Toast.LENGTH_SHORT).show();
                                        JSONObject jsonObject = null;

                                        jsonObject = new JSONObject(response);
                                        JSONArray val = jsonObject.getJSONArray("usn");
                                        ArrayList usnlist=new ArrayList();
                                        for (int i = 0; i < val.length(); i++)
                                        {
                                            usnlist.add(val.getString(i));

                                        }

                                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Facultyactivity.this, android.R.layout.simple_spinner_item, usnlist);
                                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        us1.setAdapter(spinnerAdapter);
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
                            params.put("semester1", String.valueOf(position));
                          //  params.put("section1", String.valueOf(position));
                            return params;
                        }
                    };

                    //adding our stringrequest to queue
                    Volley.newRequestQueue(Facultyactivity.this).
                            add(stringRequest);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
 sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(position!=0){
                    semSelected =semList.get(position);
                    String URL_PRODUCTS="http://192.168.10.110/integrate/section3.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                        Toast.makeText(Facultyactivity.this, response, Toast.LENGTH_SHORT).show();
                                        JSONObject jsonObject = null;
                                        try{
                                        jsonObject = new JSONObject(response);
                                        JSONArray val = jsonObject.getJSONArray("student_section");

                                        //   ArrayList secList;
                                        secList=new ArrayList();
                                        secList.add("Section");
                                        for (int i = 0; i < val.length(); i++)
                                        {
                                            secList.add(val.getString(i));

                                        }
                                            JSONArray val1 = jsonObject.getJSONArray("subject_code");

                                            //   ArrayList secList;
                                            subList=new ArrayList();
                                            subList.add("Subcode");
                                            for (int i = 0; i < val1.length(); i++)
                                            {
                                                subList.add(val1.getString(i));

                                            }

                                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Facultyactivity.this, android.R.layout.simple_spinner_item,secList);
                                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        sec.setAdapter(spinnerAdapter);
                                        spinnerAdapter.notifyDataSetChanged();
                                            ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(Facultyactivity.this, android.R.layout.simple_spinner_item,subList);
                                            spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            sub.setAdapter(spinnerAdapter1);
                                            spinnerAdapter1.notifyDataSetChanged();

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
                    Volley.newRequestQueue(Facultyactivity.this).
                            add(stringRequest);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(position!=0) {

                    secSelected = secList.get(position);
                    String URL_PRODUCTS = "http://cmpbijnor.navigem.net/SIS_API/usn1.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Toast.makeText(Facultyactivity.this, response, Toast.LENGTH_SHORT).show();
                                        JSONObject jsonObject = null;

                                        jsonObject = new JSONObject(response);
                                        JSONArray val = jsonObject.getJSONArray("student_usn");

                                        usnList = new ArrayList();
                                        //  UsnList.add("Subcode");
                                        for (int i = 0; i < val.length(); i++) {
                                            usnList.add(val.getString(i));
                                        }
                                        //  ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Facultyactivity.this, android.R.layout.simple_spinner_item, usnList);
                                        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        //sub.setAdapter(spinnerAdapter);
                                        //spinnerAdapter.notifyDataSetChanged();
                                        // us.setText(" " + usnlist.toString().replace("[", "").replace("]", ""));
                                        //  text = textList.toString());
                                        mStringArray = new String[usnList.size()];
                                        mStringArray = usnList.toArray(mStringArray);


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
                            }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("semester1", semSelected);
                            params.put("section1", secSelected);
                            return params;
                        }
                    };

                    //adding our stringrequest to queue
                    Volley.newRequestQueue(Facultyactivity.this).
                            add(stringRequest);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
      /*  sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(position!=0) {

                    subSelected = subList.get(position);
                    String URL_PRODUCTS = "http://192.168.10.110/integrate/subcode.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Toast.makeText(Facultyactivity.this, response, Toast.LENGTH_SHORT).show();
                                        JSONObject jsonObject = null;

                                        jsonObject = new JSONObject(response);
                                        JSONArray val = jsonObject.getJSONArray("subcode");

                                         subList = new ArrayList();
                                       // usnlist.add("Usn");
                                        for (int i = 0; i < val.length(); i++) {
                                            subList.add(val.getString(i));
                                        }
                                       ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Facultyactivity.this, android.R.layout.simple_spinner_item, subList);
                                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        sub.setAdapter(spinnerAdapter);
                                        spinnerAdapter.notifyDataSetChanged();
                                       //  us.setText(" " + usnList.toString().replace("[", "").replace("]", ""));
                                        //  text = textList.toString());
                                      // mStringArray = new String[usnList.size()];
                                      //  mStringArray = usnList.toArray(mStringArray);


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
                            }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("semester1", String.valueOf(semSelected));
                            params.put("section1", secSelected);
                           // params.put("subcode1",subSelected);
                            return params;
                        }
                    };

                    //adding our stringrequest to queue
                    Volley.newRequestQueue(Facultyactivity.this).
                            add(stringRequest);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    private void insertData(String fac, String sem, String sec, String sub, String tim, String usi) {


        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject( );
            jsonBody.put("txt0", fac);
            jsonBody.put("semester1", sem);
            jsonBody.put("section1", sec);
            jsonBody.put("subcode1", sub);
            jsonBody.put("timings1", tim);
            jsonBody.put("usn1", usi);


            final String mRequestBody = jsonBody.toString( );

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>( ) {

                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("message");
                        Toast.makeText(Facultyactivity.this, result, Toast.LENGTH_SHORT).show( );
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



