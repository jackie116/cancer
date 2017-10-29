package com.example.huangyuwei.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class signup_activity extends AppCompatActivity {
    private String TAG = "signup_activity";

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private LinearLayout linear_tab;
    private EditText edt_email;
    private EditText edt_password;
    private EditText edt_confirm;
    private Button btn_signup;

    private boolean email_valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        linear_tab = (LinearLayout)findViewById(R.id.linear_tab_signup);
        edt_email = (EditText)findViewById(R.id.email);
        edt_password = (EditText)findViewById(R.id.password);
        edt_confirm = (EditText)findViewById(R.id.confirm);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        //按螢幕縮鍵盤
        linear_tab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                return false;
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                String confirm = edt_confirm.getText().toString().trim();

                boolean correct = checkInput(email, password, confirm);

                if(!correct&&email_valid){
                    edt_password.setText("");
                    edt_confirm.setText("");
                }
                else if(!correct&&!email_valid){
                    edt_email.setText("");
                    edt_password.setText("");
                    edt_confirm.setText("");
                }
                else{
                    edt_email.setText("");
                    edt_password.setText("");
                    edt_confirm.setText("");
                    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String nowDateTime = format.format(new Date(System.currentTimeMillis()));
                    Log.d(TAG, "123  "+nowDateTime);
                    new AsyncLSignup().execute(email, password, nowDateTime);

                }
            }
        });

    }
    boolean checkInput(String email, String password, String confirm){
        if (TextUtils.isEmpty(email)) {
            email_valid=false;
            Toast.makeText(getApplication(), "Email不可為空", Toast.LENGTH_SHORT).show();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_valid=false;
            Toast.makeText(getApplication(), "Email格式錯誤", Toast.LENGTH_SHORT).show();
        }
        else {
            email_valid=true;
            if (TextUtils.isEmpty(password)||TextUtils.isEmpty(confirm)) {
                Toast.makeText(getApplication(), "密碼不可為空", Toast.LENGTH_SHORT).show();
            }else if(password.length()>12||password.length()<6){
                Toast.makeText(getApplication(), "密碼長度不符", Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confirm)) {
                Toast.makeText(getApplication(), "密碼不符合", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), "註冊成功", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return false;
    }
    private class AsyncLSignup extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(signup_activity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {
                Log.i(TAG, "123123"+"doinbackground");
                // Enter URL address where your php file resides
                url = new URL("http://13.115.90.58/signup.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", params[0])
                        .appendQueryParameter("password", params[1])
                        .appendQueryParameter("jointime", params[2]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
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

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    Log.i(TAG, "123123"+"HTTP_OK");
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful1");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {
            //this method will be running on UI thread
            pdLoading.dismiss();
            Log.i(TAG, "123123  "+result);

            Intent intent = new Intent(signup_activity.this,MainActivity.class);
            startActivity(intent);
            signup_activity.this.finish();

        }

    }
}
