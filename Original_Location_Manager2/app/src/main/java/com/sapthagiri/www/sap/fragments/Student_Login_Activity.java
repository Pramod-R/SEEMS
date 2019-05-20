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
import android.widget.Toast;

import com.sapthagiri.www.sap.Event_Product;
import com.sapthagiri.www.sap.Landing_Page_Activity;
import com.sapthagiri.www.sap.R;
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
    String role = "STUDENT";

    private Button mLoginButton;
    private static final String TAG = "Student_Login_Activity";
    public static product mStudentProduct;
    public ArrayList<Event_Product> mStudentResultProductList;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_student_login, container, false);

        mLoginButton = (Button)rootView.findViewById(R.id.student_login);
        mStudentResultProductList = new ArrayList<>();


        etEmail = (EditText) rootView.findViewById(R.id.username);
        etEmail.setText("aaa@gmail.com");
        etPassword = (EditText) rootView.findViewById(R.id.password);
        etPassword.setText("shiva");



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
                url = new URL("http://192.168.10.41/SAS/API/lognew2.php");
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
                        .appendQueryParameter("password", params[1])
                        .appendQueryParameter("role",role);
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
                    String uname = jsonObject.getString("u.user_name");
                    String ema= jsonObject.getString("u.email");
                    int mnum = jsonObject.getInt("u.mobile_num");
                    String dob = jsonObject.getString("u.dob");
                    String gen = jsonObject.getString("u.gender");
                    String bran = jsonObject.getString("u.branch");
                    String sem = jsonObject.getString("s.semister");
                    String sec = jsonObject.getString("s.section");



                    mStudentProduct = new product( uname, ema, mnum,dob,gen,bran,sem,sec);

                    Intent intent = new Intent(getActivity(), Landing_Page_Activity.class);
                    startActivity(intent);
                    etEmail.setText("");
                    etPassword.setText("");
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    new AsyncResult().execute(bran,sem,sec);

                } else {
                    Toast.makeText(getActivity(), "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Please Signup from WebApplication", Toast.LENGTH_SHORT).show();

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
                url = new URL("http://192.168.10.41/SAS/API/Notice_Retrieve.php");
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
                        .appendQueryParameter("branch", params[0])
                        .appendQueryParameter("semister",params[1])
                        .appendQueryParameter("section",params[2]);
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


                    String branch = obj.getString("branch");
                    String semester = obj.getString("semester");
                    String section = obj.getString("section");
                    String eventdate = obj.getString("eventdate");
                    String eventdesc = obj.getString("eventdesc");

                    Event_Product studentResultProduct = new Event_Product(branch,semester,section,eventdate,eventdesc);
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
    private class AsyncAttend extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("http://192.168.10.41/SAS/API/student_table.php");
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
                        .appendQueryParameter("branch", params[0])
                        .appendQueryParameter("semister", params[1])
                        .appendQueryParameter("section", params[2]);
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


                    String branch = obj.getString("branch");
                    String semester = obj.getString("semester");
                    String section = obj.getString("section");
                    String eventdate = obj.getString("eventdate");
                    String eventdesc = obj.getString("eventdesc");

                    Event_Product studentResultProduct = new Event_Product(branch,semester,section,eventdate,eventdesc);
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


