package com.example.huangyuwei.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class user_profile extends AppCompatActivity {
    private static user_profile mInstance=null;
    private final String TAG = "user_profile";
    private static final String account_data = "ACCOUNT";
    private final String getUserFailed = "驗證錯誤，請重新登入";
    private final String birthErr = "生日--格式錯誤  ex. 2012-12-21";
    private final String phoneErr = "手機--格式錯誤  ex. 0912345678";
    private final String clevelErr = "期數--格式錯誤  ex. 2";
    private final String cdateErr = "開刀日期--格式錯誤  ex. 2012-12-21";
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private Context context;
    private LinearLayout tab_view;
    private Button btn_logout;
    private Button btn_modify;
    private Button btn_modpw;
    private TextView text_email;
    private EditText edt_name;
    private EditText edt_birth;
    private EditText edt_phone;
    private EditText edt_clevel;
    private EditText edt_cdate;
    private EditText edt_cure;
    private TextView text_errMsg;
    private String username;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        context=this;
        mInstance=this;
        userProfile = new UserProfile();
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_modify = (Button) findViewById(R.id.btn_modify);
        btn_modpw = (Button) findViewById(R.id.btn_modpw);
        text_email = (TextView)findViewById(R.id.text_email_show);
        edt_name = (EditText) findViewById(R.id.edt_name_show);
        edt_birth = (EditText)findViewById(R.id.edt_birth_show);
        edt_phone = (EditText)findViewById(R.id.edt_phone_show);
        edt_clevel = (EditText)findViewById(R.id.edt_clevel_show);
        edt_cdate = (EditText)findViewById(R.id.edt_cdate_show);
        edt_cure = (EditText)findViewById(R.id.edt_cure_show);
        text_errMsg = (TextView) findViewById(R.id.text_errMsg);
        tab_view = (LinearLayout) findViewById(R.id.tab_view);
        tab_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                return false;
            }
        });
        btn_modpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_profile.this,modify_password.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.getSharedPreferences(context).edit().clear().apply();
                Intent intent = new Intent(user_profile.this,MainActivity.class);
                startActivity(intent);
                center.getInstance().finish();
                user_profile.this.finish();
            }
        });
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfile.name = edt_name.getText().toString();
                userProfile.birth = edt_birth.getText().toString();
                userProfile.phone = edt_phone.getText().toString();
                userProfile.c_level = edt_clevel.getText().toString();
                userProfile.c_date = edt_cdate.getText().toString();
                userProfile.cure = edt_cure.getText().toString();
                check_valid();
            }
        });


        new getUserProfile().execute();
    }
    public static user_profile getInstance(){
        return mInstance;
    }
    private void check_valid(){
        boolean birth_valid = check_date(userProfile.birth);
        boolean phone_valid = check_phone();
        boolean clevel_valid = check_clevel();
        boolean cdate_valid = check_date(userProfile.c_date);
        if(birth_valid&&phone_valid&&clevel_valid&&cdate_valid){
            text_errMsg.setVisibility(View.INVISIBLE);
            new modifyUserProfile().execute();
        }
        else{
            String errMsg="";
            if(!birth_valid)
                errMsg+=birthErr+"\n";
            if(!phone_valid)
                errMsg+=phoneErr+"\n";
            if(!clevel_valid)
                errMsg+=clevelErr+"\n";
            if(!cdate_valid)
                errMsg+=cdateErr+"\n";
            text_errMsg.setVisibility(View.VISIBLE);
            text_errMsg.setText(errMsg);
        }
    }
    private boolean check_date(String date){
        boolean date_valid = true;
        //http://ldbjakyo.iteye.com/blog/1604214 含閏年的正規表示式
        Pattern pattern = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
        Matcher matcher = pattern.matcher(date);
        if(!matcher.matches())
            date_valid=false;
        Log.i(TAG, "123  "+date_valid);
        return date_valid;
    }
    private boolean check_phone(){
        boolean birth_valid = true;
        Pattern pattern = Pattern.compile("(09)+[\\d]{8}");
        Matcher matcher = pattern.matcher(userProfile.phone);
        if(!matcher.matches())
            birth_valid=false;
        return birth_valid;
    }
    private boolean check_clevel(){
        boolean clevel_valid = true;
        if(!userProfile.c_level.isEmpty()) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher matcher = pattern.matcher(userProfile.c_level);
            if (!matcher.matches())
                clevel_valid = false;
        }
        else{
            clevel_valid = false;
        }
        return clevel_valid;
    }

    private void JSONanalyse(String result){
        try{
           JSONObject jsonObject = new JSONObject(result);
            userProfile.email = jsonObject.getString("email").equals("null")?"尚未輸入資料":jsonObject.getString("email");
            userProfile.name = jsonObject.getString("name").equals("null")?"尚未輸入資料":jsonObject.getString("name");
            userProfile.birth = jsonObject.getString("birthday").equals("null")?"尚未輸入資料":jsonObject.getString("birthday");
            userProfile.phone = jsonObject.getString("phone").equals("null")?"尚未輸入資料":jsonObject.getString("phone");
            userProfile.c_level = jsonObject.getString("c_level").equals("null")?"尚未輸入資料":jsonObject.getString("c_level");
            userProfile.c_date = jsonObject.getString("c_date").equals("null")?"尚未輸入資料":jsonObject.getString("c_date");
            userProfile.cure = jsonObject.getString("cure").equals("null")?"尚未輸入資料":jsonObject.getString("cure");
            Log.i(TAG, "123"+userProfile.getAll());
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

    }

    private class getUserProfile extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(context);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
            username = UserData.getSharedPreferences(context).getString(account_data, "");
        }
        @Override
        protected String doInBackground(String... params) {
            if(username==null)
                return "getUserFailed";
            else {
                try {
                    // Enter URL address where your php file resides
                    url = new URL("http://13.115.90.58/queryUser.php");
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "exception";
                }
                try {
                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");

                    // setDoInput and setDoOutput method depict handling of both send and receive
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    // Append parameters to URL
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("email", username);
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

                        // Read data sent from server
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        // Pass data to onPostExecute method
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


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            Log.i(TAG, "123123"+result);
            pdLoading.dismiss();

            if(result=="getUserFailed"){
                Toast.makeText(context, getUserFailed, Toast.LENGTH_SHORT).show();
            }
            else{
                JSONanalyse(result);
                text_email.setText(userProfile.email);
                edt_name.setText(userProfile.name);
                edt_birth.setText(userProfile.birth);
                edt_phone.setText(userProfile.phone);
                edt_clevel.setText(userProfile.c_level);
                edt_cdate.setText(userProfile.c_date);
                edt_cure.setText(userProfile.cure);
            }

        }

    }
    private class modifyUserProfile extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(user_profile.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
            userProfile.email = UserData.getSharedPreferences(context).getString(account_data, "");

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://13.115.90.58/updateUser.php");

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
                        .appendQueryParameter("email", userProfile.email)
                        .appendQueryParameter("name", userProfile.name)
                        .appendQueryParameter("birthday", userProfile.birth)
                        .appendQueryParameter("phone", userProfile.phone)
                        .appendQueryParameter("c_level", userProfile.c_level)
                        .appendQueryParameter("c_date", userProfile.c_date)
                        .appendQueryParameter("cure", userProfile.cure);
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

                    return("unsuccessful");
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
            Log.i(TAG, "123123"+result);
            pdLoading.dismiss();
            final AlertDialog d = new AlertDialog.Builder(context)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(user_profile.this,center.class);
                            startActivity(intent);
                            user_profile.this.finish();
                        }
                    })
                    .setMessage("個人資料更新成功")
                    .setTitle("更新")
                    .create();
            d.show();

        }

    }

    class UserProfile{
        String email;
        String name;
        String birth;
        String phone;
        String c_level;
        String c_date;
        String cure;

        String getAll(){
            String all=email+" "+name+" "+birth+" "+phone+" "+c_level+" "+c_date+" "+cure;
            return all;
        }
    }
}
