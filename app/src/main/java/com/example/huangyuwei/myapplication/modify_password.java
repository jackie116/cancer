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
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangyuwei.myapplication.R;

import org.w3c.dom.Text;

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

public class modify_password extends AppCompatActivity {
    private static final String TAG = "modify_password";
    private Context context;
    private static final String account_data = "ACCOUNT";
    private static final String password_data = "PASSWORD";
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private TextView text_errMsg;
    private EditText edt_oldPass;
    private EditText edt_newPass;
    private EditText edt_newConfirm;
    private Button btn_changePass;
    private LinearLayout view_tab;
    private String oldPass;
    private String newPass;
    private String newConfirm;
    private String email;
    private String correctOld;
    private String err_input = "輸入錯誤，請重新輸入";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        context=this;
        email = UserData.getSharedPreferences(context).getString(account_data, "");
        correctOld = UserData.getSharedPreferences(context).getString(password_data, "");
        text_errMsg = (TextView) findViewById(R.id.text_errMsg);
        edt_oldPass = (EditText) findViewById(R.id.edt_oldPass);
        edt_newPass = (EditText) findViewById(R.id.edt_newPass);
        edt_newConfirm = (EditText) findViewById(R.id.edt_confirmNew);
        btn_changePass = (Button) findViewById(R.id.btn_changePass);
        view_tab = (LinearLayout)findViewById(R.id.view_tab);
        view_tab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                return false;
            }
        });
        btn_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass = edt_oldPass.getText().toString();
                newPass = edt_newPass.getText().toString();
                newConfirm = edt_newConfirm.getText().toString();
                boolean valid = check_valid();
                if(!valid){
                    edt_oldPass.setText("");
                    edt_newPass.setText("");
                    edt_newConfirm.setText("");
                }
                else{
                    Log.d(TAG, "123  "+"valid");
                    new change_password().execute();
                }
            }
        });
    }
    private boolean check_valid(){
        if(!oldPass.equals(correctOld)){
            text_errMsg.setVisibility(View.VISIBLE);
            text_errMsg.setText(err_input);

        }
        else{
            if(TextUtils.isEmpty(newPass)||TextUtils.isEmpty(newConfirm)){
                text_errMsg.setVisibility(View.VISIBLE);
                text_errMsg.setText(err_input);
            }
            else{
                if(!newPass.equals(newConfirm)){
                    text_errMsg.setVisibility(View.VISIBLE);
                    text_errMsg.setText(err_input);
                }
                else{
                    text_errMsg.setVisibility(View.INVISIBLE);
                    UserData.saveData(context, "", "");
                    return true;
                }
            }
        }
        return false;
    }

    private class change_password extends AsyncTask<String, String, String>
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

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://13.115.90.58/update_password.php");

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
                        .appendQueryParameter("password", newPass);
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
                            Intent intent = new Intent(modify_password.this,MainActivity.class);
                            startActivity(intent);
                            modify_password.this.finish();
                            center.getInstance().finish();
                            user_profile.getInstance().finish();
                        }
                    })
                    .setMessage("密碼變更成功，請重新登入")
                    .setTitle("更改密碼")
                    .create();
            d.show();

        }

    }
}
