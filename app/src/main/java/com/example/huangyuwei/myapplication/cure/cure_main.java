package com.example.huangyuwei.myapplication.cure;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.UserData;
import com.example.huangyuwei.myapplication.center;
import com.example.huangyuwei.myapplication.link.link_economic;
import com.example.huangyuwei.myapplication.link.link_foundation;
import com.example.huangyuwei.myapplication.user_profile;

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

public class cure_main extends AppCompatActivity {
    private static final String TAG = "cure_main";
    private Context context;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private String[] name;
    private String[] urlData;
    private String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cure_main);
        context = this;
        listView = (ListView)findViewById(R.id.listview_cure);
        new getCureDataTask().execute();
    }

    private void JSONanalyse(){
        try{
            JSONObject jsonObject = new JSONObject(data);
            name = new String[jsonObject.length()];
            urlData = new String[jsonObject.length()];
            for(int i=0;i<jsonObject.length();i++) {
                JSONObject json = new JSONObject(jsonObject.getJSONObject(String.valueOf(i)).toString());
                name[i] = json.getString("name");
                urlData[i] = json.getString("url");
            }
            for(int i=0;i<name.length;i++){
                Log.i(TAG, "123"+name[i]+"  "+urlData[i]);
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

    }

    private class getCureDataTask extends AsyncTask<String, String, String>
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
                url = new URL("http://13.115.90.58/query_cure.php");
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

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            Log.i(TAG, "123123"+result);
            pdLoading.dismiss();
            data = result;
            JSONanalyse();

            listAdapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,name);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "你選擇" + name[position], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(cure_main.this,cure_show.class);
                    intent.putExtra("url",urlData[position]);//可放所有基本類別
                    startActivity(intent);

                }
            });

        }

    }

}
