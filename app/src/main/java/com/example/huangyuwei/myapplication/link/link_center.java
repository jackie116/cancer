package com.example.huangyuwei.myapplication.link;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangyuwei.myapplication.R;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class link_center extends Fragment {
    class Center{
        String name;
        String phone;
        String address;
    }
    private String TAG = "link_center";
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    public static final ArrayList<Center> north=new ArrayList<>();
    public static final ArrayList<Center> center=new ArrayList<>();
    public static final ArrayList<Center> south=new ArrayList<>();
    public static final ArrayList<Center> east=new ArrayList<>();
    private String data;

    public link_center() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_link_center, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new getData().execute();

    }

    private void JSONanalyse(){
        try{
            north.clear();
            center.clear();
            south.clear();
            east.clear();
            JSONArray jsonArray = new JSONArray(data);
            for(int k=0;k<jsonArray.length();k++) {
                //Log.i(TAG, jsonArray.getString(i));
                if (k == 0)
                    Log.i(TAG, "北區+++++++");
                else if (k == 1)
                    Log.i(TAG, "中區+++++++");
                else if (k == 2)
                    Log.i(TAG, "南區+++++++");
                else if (k == 3)
                    Log.i(TAG, "東區+++++++");
                else
                    Log.i(TAG, "無法識別+++++++");
                JSONObject jsonObject = new JSONObject(jsonArray.getString(k));
                //Log.i(TAG, "length: "+jsonObject.toString());
                if (k == 0) {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        Center temp = new Center();
                        String number = data2.getString("number");
                        temp.name = data2.getString("unit");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        north.add(temp);
                        Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }
                else if(k==1){
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        Center temp = new Center();
                        String number = data2.getString("number");
                        temp.name = data2.getString("unit");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        center.add(temp);
                        Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }
                else if(k==2){
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        Center temp = new Center();
                        String number = data2.getString("number");
                        temp.name = data2.getString("unit");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        south.add(temp);
                        Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }
                else if(k==3){
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        Center temp = new Center();
                        String number = data2.getString("number");
                        temp.name = data2.getString("unit");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        east.add(temp);
                        Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }

            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

    }



    private void addTableRow(TableLayout tl, String name, String phone, String address){

        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row, tl, false);

        // Add First Column
        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Name.setText(name);

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(phone);

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(address);

        tl.addView(tr);
    }

    private class getData extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
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
                url = new URL("http://13.115.90.58/link_center.php");

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
//                Uri.Builder builder = new Uri.Builder()
//                        .appendQueryParameter("username", params[0])
//                        .appendQueryParameter("password", params[1]);
//                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(query);
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

            pdLoading.dismiss();
            data = result;
            JSONanalyse();
            for(int i=0;i<north.size();i++)
                Log.i(TAG,north.get(i).name);

            TableLayout ll = (TableLayout) getView().findViewById(R.id.NorthTable);
            TableLayout cl = (TableLayout) getView().findViewById(R.id.CenterTable);
            TableLayout sl = (TableLayout) getView().findViewById(R.id.SouthTable);
            TableLayout el = (TableLayout) getView().findViewById(R.id.EastTable);
            for (int i = 0; i <north.size(); i++) {
                addTableRow(ll,north.get(i).name,north.get(i).phone,north.get(i).address);
            }
            for (int i = 0; i <center.size(); i++) {
                addTableRow(cl,center.get(i).name,center.get(i).phone,center.get(i).address);
            }
            for (int i = 0; i < south.size(); i++) {
                addTableRow(sl,south.get(i).name,south.get(i).phone,south.get(i).address);
            }
            for (int i = 0; i <east.size(); i++) {
                addTableRow(el,east.get(i).name,east.get(i).phone,east.get(i).address);
            }

        }

    }



}


