package com.example.huangyuwei.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class user_modify extends AppCompatActivity {
    private String TAG = "user_modify";
    private static final String account_data = "ACCOUNT";
    private Context context;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private String email;

    private EditText edt_name;
    private EditText edt_birth;
    private EditText edt_phone;
    private EditText edt_c_level;
    private EditText edt_c_date;
    private EditText edt_cure;
    private TextView text_errMsg;
    private Button btn_confirm;
    private LinearLayout view_tab;

    private String name, birth, phone, c_level, c_date, cure;
    private String birthErr = "生日--格式錯誤  ex. 2012-12-21";
    private String phoneErr = "手機--格式錯誤  ex. 0912345678";
    private String clevelErr = "期數--格式錯誤  ex. 2";
    private String cdateErr = "開刀日期--格式錯誤  ex. 2012-12-21";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify);
        context=this;
        edt_name = (EditText) findViewById(R.id.edit_name);
        edt_birth = (EditText) findViewById(R.id.edit_birth);
        edt_phone = (EditText) findViewById(R.id.edit_phone);
        edt_c_level = (EditText) findViewById(R.id.edit_clevel);
        edt_c_date = (EditText) findViewById(R.id.edit_cdate);
        edt_cure = (EditText) findViewById(R.id.edit_cure);
        text_errMsg = (TextView) findViewById(R.id.text_errMsg);
        view_tab = (LinearLayout) findViewById(R.id.view_tab);
        view_tab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                return false;
            }
        });
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edt_name.getText().toString();
                birth = edt_birth.getText().toString();
                phone = edt_phone.getText().toString();
                c_level = edt_c_level.getText().toString();
                c_date = edt_c_date.getText().toString();
                cure = edt_cure.getText().toString();
                check_valid();
            }
        });
        //test_date();
    }
    private void test_date(){
        edt_name.setText("ABC");
        edt_phone.setText("0987654321");
        edt_c_level.setText("2");
        edt_cure.setText("eat medicine");
    }
    private void check_valid(){
        boolean birth_valid = check_date(birth);
        boolean phone_valid = check_phone();
        boolean clevel_valid = check_clevel();
        boolean cdate_valid = check_date(c_date);
        if(birth_valid&&phone_valid&&clevel_valid&&cdate_valid){
            text_errMsg.setVisibility(View.INVISIBLE);
            //new modifyUserProfile().execute();
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
        Matcher matcher = pattern.matcher(phone);
        if(!matcher.matches())
            birth_valid=false;
        return birth_valid;
    }
    private boolean check_clevel(){
        boolean clevel_valid = true;
        if(!c_level.isEmpty()) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher matcher = pattern.matcher(c_level);
            if (!matcher.matches())
                clevel_valid = false;
        }
        else{
            clevel_valid = false;
        }
        return clevel_valid;
    }

    private class modifyUserProfile extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(user_modify.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
            email = UserData.getSharedPreferences(context).getString(account_data, "");

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
                        .appendQueryParameter("email", email)
                        .appendQueryParameter("name", name)
                        .appendQueryParameter("birthday", birth)
                        .appendQueryParameter("phone", phone)
                        .appendQueryParameter("c_level", c_level)
                        .appendQueryParameter("c_date", c_date)
                        .appendQueryParameter("cure", cure);
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

            Intent intent = new Intent(user_modify.this,user_profile.class);
            startActivity(intent);
            user_modify.this.finish();

        }

    }
}
