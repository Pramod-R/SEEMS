package com.sapthagiri.www.sap.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sapthagiri.www.sap.Landing_Page_Activity;
import com.sapthagiri.www.sap.R;
import com.sapthagiri.www.sap.Student_Attend_Product;
import com.sapthagiri.www.sap.Student_Register_Activity;
import com.sapthagiri.www.sap.Student_Result_Product;
import com.sapthagiri.www.sap.product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Student_Login_Activity extends Fragment {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private EditText etEmail;
    private String res;
    private EditText etPassword;
    Fragment someFragment;
    private TextView Tx1;
    private Button mLoginButton;
    TextView tx2;
    private static final String TAG = "Student_Login_Activity";
    public static product mStudentProduct;
    public ArrayList<Student_Attend_Product> mStudentAttendProductList;
    public ArrayList<Student_Result_Product> mStudentResultProductList;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_student_login, container, false);
        mStudentAttendProductList = new ArrayList<>();
        mStudentResultProductList = new ArrayList<>();

        mLoginButton = (Button)rootView.findViewById(R.id.student_login);

        etEmail = (EditText) rootView.findViewById(R.id.username);
        etEmail.setText("pramod");
        etPassword = (EditText) rootView.findViewById(R.id.password);
        etPassword.setText("123456");

        tx2 = (TextView) rootView.findViewById(R.id.txt2);
        tx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(getActivity(), Student_Register_Activity.class);
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // report = (LinearLayout) view.findViewById(R.id.loginfg);
    }

      /*  but.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {
                String  etEmai = etEmail.getText().toString();
                String etpas = etPassword.getText().toString();
                if(etEmai.equals("pramod")&& etpas.equals("123456")){
                    Intent intentAdmin = new Intent(Student_Login_Activity.this, Landing_Page_Activity.class);
                    intentAdmin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentAdmin);
                    Toast.makeText(Student_Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Student_Login_Activity.this, "Failed Login", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });*/

    public void checkLogin (){
        //     someFragment.checkLogin(arg0);

        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        //final int id;
        //final int rid=mStudentProduct.getRid();

        new AsyncLogin().execute(email, password);
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("http://cmpbijnor.navigem.net/SIS_API/lognew1.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                System.out.println(query);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }
        }

        private String readInputStream(InputStream stream) {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException ", e);
            } catch (Exception e) {
                Log.e(TAG, "Exception", e);
            } finally {
                try {
                    if (stream != null)
                        stream.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException", e);
                }
            }
            Log.v(TAG, "Response readInputStream-->" + response.toString());
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("test", "==>>result " + result);
            res = result;
            Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
            pdLoading.dismiss();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(res);
                String val = jsonObject.getString("msg");
                if (val.equals("Success")) {
                    int rid = jsonObject.getInt("rid");
                    String name = jsonObject.getString("fullname");
                    String usn = jsonObject.getString("usnno");
                    String dob = jsonObject.getString("dob");
                    String ema = jsonObject.getString("email");
                    String gen = jsonObject.getString("gender");
                    String phno = jsonObject.getString("phonenumber");
                    //  if(val.equals("Successfull")) {


                    //       int aid=jsonObject.getInt("id");
                    //    String ccode=jsonObject.getString("coursecode");
                    //String cname=jsonObject.getString("coursename");
                    //int cconduct=jsonObject.getInt("classconduct");
                    // int cAttend=jsonObject.getInt("classAttend");
                    //int per=jsonObject.getInt("percentage");
                    //String pass=jsonObject.getString("password");
                    //String con=jsonObject.getString("confirmpassword");
                    mStudentProduct = new product(rid, name, usn, dob, ema, gen, phno);
                    // new AsyncAttend().execute(String.valueOf(rid));
                    //mStudentAttendProduct=new Student_Attend_Product(aid,ccode,cname,cconduct,cAttend,per);
                    Intent intent = new Intent(getActivity(), Landing_Page_Activity.class);
                    startActivity(intent);
                    etEmail.setText("");
                    etPassword.setText("");
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    new AsyncAttend().execute(String.valueOf(rid));
                    new AsyncResult().execute(String.valueOf(rid));
                } else {
                    Toast.makeText(getActivity(), "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Don't have an account?please signup !!!!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class AsyncAttend extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("http://cmpbijnor.navigem.net/SIS_API/duplicate2.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("rid", params[0]);
                String query = builder.build().getEncodedQuery();

                System.out.println(query);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        private String readInputStream(InputStream stream) {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException ", e);
            } catch (Exception e) {
                Log.e(TAG, "Exception", e);
            } finally {
                try {
                    if (stream != null)
                        stream.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException", e);
                }
            }
            Log.v(TAG, "Response readInputStream-->" + response.toString());
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("test", "==>>result " + result);
            res = result;
            Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
            pdLoading.dismiss();
            try {
                JSONArray jArr = null;
                jArr = new JSONArray(res);
                for (int i = 0; i < jArr.length(); i++) {
                    JSONObject obj = jArr.getJSONObject(i);

                    int aid = obj.getInt("aid");
                    String ccode = obj.getString("coursecode");
                    String cname = obj.getString("coursename");
                    int cconduct = obj.getInt("classconduct");
                    int cAttend = obj.getInt("classAttend");
                    int per = obj.getInt("percentage");
                    Student_Attend_Product studentAttendProduct = new Student_Attend_Product(aid, ccode, cname, cconduct, cAttend, per);
                    mStudentAttendProductList.add(studentAttendProduct);
                }
                Intent intent = new Intent(getActivity(), Landing_Page_Activity.class);
                //finishAffinity();
                startActivity(intent);
                //  finish();
                //   Toast.makeText(Student_Login_Activity.this, "Login Attend Successful", Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
                //  Toast.makeText(Student_Login_Activity.this, "Don't have an account?please signup !!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class AsyncResult extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("http://cmpbijnor.navigem.net/SIS_API/result.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("rid", params[0]);
                String query = builder.build().getEncodedQuery();

                System.out.println(query);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        private String readInputStream(InputStream stream) {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException ", e);
            } catch (Exception e) {
                Log.e(TAG, "Exception", e);
            } finally {
                try {
                    if (stream != null)
                        stream.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException", e);
                }
            }
            Log.v(TAG, "Response readInputStream-->" + response.toString());
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("test", "==>>result " + result);
            res = result;
            Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
            pdLoading.dismiss();
            try {
                JSONArray jArr = null;
                jArr = new JSONArray(res);
                for (int i = 0; i < jArr.length(); i++) {
                    JSONObject obj = jArr.getJSONObject(i);

                    int sid = obj.getInt("Sid");
                    String scode = obj.getString("SubCode");
                    String sub = obj.getString("Subject");
                    int im = obj.getInt("IM");
                    int em = obj.getInt("EM");
                    int tot = obj.getInt("Total");
                    String gra = obj.getString("Grade");
                    Student_Result_Product studentResultProduct = new Student_Result_Product(sid, scode, sub, im, em, tot, gra);
                    mStudentResultProductList.add(studentResultProduct);
                }
                Intent intent = new Intent(getActivity(), Landing_Page_Activity.class);
                //finishAffinity();
                startActivity(intent);
                //  finish();
                Toast.makeText(getActivity(), "Login Result Successful", Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
                //  Toast.makeText(Student_Login_Activity.this, "Don't have an account?please signup !!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


