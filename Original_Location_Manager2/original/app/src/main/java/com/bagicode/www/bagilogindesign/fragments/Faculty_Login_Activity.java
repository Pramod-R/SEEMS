package com.sapthagiri.www.sap.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
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

import com.sapthagiri.www.sap.FacultyLandingPageActivity;
import com.sapthagiri.www.sap.Faculty_Register_Activity;
import com.sapthagiri.www.sap.Faculty_product;
import com.sapthagiri.www.sap.R;

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
import java.util.Calendar;

public class Faculty_Login_Activity extends Fragment {
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    private String res;
    private EditText etPassword;
    private TextView Tx1;

    private Button mLoginButton;
    TextView tx2;
    // int hours = new Time(System.currentTimeMillis()).getHours();
    MediaPlayer mySong,mySong1;
    private static final String TAG = "Faculty_Login_Activity";
    public static Faculty_product mFacultyProduct;
    Calendar c = Calendar.getInstance();
    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_faculty_login, container, false);

        mLoginButton = (Button)rootView.findViewById(R.id.faculty_login);
        etEmail = (EditText) rootView.findViewById(R.id.username);
        etEmail.setText("pooja");
        etPassword = (EditText) rootView.findViewById(R.id.password);
        etPassword.setText("123456");

        tx2 = (TextView) rootView.findViewById(R.id.txt2);
        tx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(getActivity(), Faculty_Register_Activity.class);
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFLogin();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // report = (LinearLayout) view.findViewById(R.id.loginfg);
    }

    public void checkFLogin() {
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        new AsyncLogin().execute(email,password);
        mySong = MediaPlayer.create(getActivity(), R.raw.goodmorning);
        mySong1=MediaPlayer.create(getActivity(),R.raw.goodafternoon);

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
                url = new URL("http://cmpbijnor.navigem.net/SIS_API/faculty_login.php");
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
            Log.i("test","==>>result "+result);
            res = result;
            // Toast.makeText(Faculty_Login_Activity.this,res,Toast.LENGTH_SHORT).show();
            pdLoading.dismiss();

            JSONObject jsonObject= null;

            try {
                jsonObject=new JSONObject(res);
                String val=jsonObject.getString("msg");
                if(val.equals("Success")){
                    int id=jsonObject.getInt("fid");
                    String name=jsonObject.getString("fullname");
                    String fid=jsonObject.getString("facid");
                    String dob=jsonObject.getString("fdob");
                    String ema=jsonObject.getString("femail");
                    String gen=jsonObject.getString("gender");
                    String phno=jsonObject.getString("pnumber");
                    //  String pass=jsonObject.getString("password");
                    //  String con=jsonObject.getString("confirmpass");

                    mFacultyProduct=new Faculty_product( id, name, fid, dob, ema, gen,phno);
                    Intent intent=new Intent(getActivity(),FacultyLandingPageActivity.class);

                    startActivity(intent);
                    etEmail.setText("");
                    etPassword.setText("");
                    if(timeOfDay >= 0 && timeOfDay < 12){
                        mySong.start();
                    }else if(timeOfDay >= 12 && timeOfDay < 16){
                        mySong1.start();
                    }else if(timeOfDay >= 16 && timeOfDay < 21){
                        mySong1.start();
                    }else if(timeOfDay >= 21 && timeOfDay < 24){
                        mySong1.start();
                    }




                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Don't have an account?please signup !!!!", Toast.LENGTH_SHORT).show();


            }}
    }
}