package com.sapthagiri.www.sap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.sapthagiri.www.sap.fragments.Faculty_Login_Activity;
import com.sapthagiri.www.sap.fragments.Faculty_Logout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Faculty_Register_Activity extends AppCompatActivity {

    EditText sName,fid,sDob,sPhone,sEmail,sGender,sPassword,sConfirm;
    Button sButton;
    TextView link;
    String url = "http://cmpbijnor.navigem.net/SIS_API/fac_registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_register);
        setTitle("Faculty SignUp");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sEmail = findViewById(R.id.email);
        sName = findViewById(R.id.username);
        sPassword = findViewById(R.id.password);
        fid = findViewById(R.id.facultyid);
        sDob = findViewById(R.id.dob);
        sPhone = findViewById(R.id.phoneno);
        sGender = findViewById(R.id.gender);
        sConfirm = findViewById(R.id.confirmpass);
        sButton = findViewById(R.id.signup);
        link = findViewById(R.id.loginLink);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Faculty_Register_Activity.this, LoginHome.class);
                startActivity(i);
            }
        });

        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = sName.getText().toString();
                String facultyid = fid.getText().toString();
                String dob = sDob.getText().toString();
                String phoneno = sPhone.getText().toString();
                String email = sEmail.getText().toString();
                String gender = sGender.getText().toString();
                String password = sPassword.getText().toString();
                String confirmpass = sConfirm.getText().toString();

                if (username.equals("") || phoneno.equals("") || gender.equals("") || facultyid.equals("") || dob.equals("")) {
                    Toast.makeText(getApplicationContext(), "fields are empty", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (!email.contains("@")) {
                        Toast.makeText(getApplicationContext(), "Invalid Email address", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        if (password.length() < 6) {
                            Toast.makeText(getApplicationContext(), "Password must contain atleast 6 characters", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            if (!password.equals(confirmpass)) {
                                Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_LONG).show();
                                return;
                            } else {

                                insertData(username,facultyid,dob,phoneno,email,gender,password,confirmpass);

                                Intent intent1 = new Intent(Faculty_Register_Activity.this, Faculty_Login_Activity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);

                                sName.setText(null);
                                fid.setText(null);
                                sDob.setText(null);
                                sPhone.setText(null);
                                sEmail.setText(null);
                                sGender.setText(null);
                                sConfirm.setText(null);
                                sPassword.setText(null);
                            }
                        }
                    }
                }
            }
        });
    }

    private void insertData(final String username, final String fid, final String dob,final String phoneno,final String email,
                            final  String gender,final String pass, final String cpass) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user",username);
            jsonBody.put("facultyid", fid);
            jsonBody.put("dob", dob);
            jsonBody.put("email", email);
            jsonBody.put("gender", gender);
            jsonBody.put("phno", phoneno);
            jsonBody.put("password", pass);
            jsonBody.put("confirmpass", cpass);

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("message");
                        Toast.makeText(Faculty_Register_Activity.this, result, Toast.LENGTH_SHORT).show();
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

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
}
