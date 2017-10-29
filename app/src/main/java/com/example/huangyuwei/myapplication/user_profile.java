package com.example.huangyuwei.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class user_profile extends AppCompatActivity {
    private final String TAG = "user_profile";
    private static final String account_data = "ACCOUNT";
    private final String getUserFailed = "驗證錯誤，請重新登入";
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private Context context;
    private Button btn_logout;
    private Button btn_modify;
    private TextView text_email;
    private TextView text_name;
    private TextView text_birth;
    private TextView text_phone;
    private TextView text_clevel;
    private TextView text_cdate;
    private TextView text_cure;
    private String username;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        context=this;
        userProfile = new UserProfile();
        btn_logout = (Button)findViewById(R.id.btn_logout);
        text_email = (TextView)findViewById(R.id.text_email_show);
        text_name = (TextView)findViewById(R.id.text_name_show);
        text_birth = (TextView)findViewById(R.id.text_birth_show);
        text_phone = (TextView)findViewById(R.id.text_phone_show);
        text_clevel = (TextView)findViewById(R.id.text_clevel_show);
        text_cdate = (TextView)findViewById(R.id.text_cdate_show);
        text_cure = (TextView)findViewById(R.id.text_cure_show);
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

            }
        });

        new getUserProfile().execute();
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
                text_name.setText(userProfile.name);
                text_birth.setText(userProfile.birth);
                text_phone.setText(userProfile.phone);
                text_clevel.setText(userProfile.c_level);
                text_cdate.setText(userProfile.c_date);
                text_cure.setText(userProfile.cure);
            }

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
