package com.example.huangyuwei.myapplication.link;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.huangyuwei.myapplication.R;

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


public class link_economic extends Fragment {
    class PublicCenter{
        String name;
        String eligibility;
        String url;
        String unit;
        String info;
    }

    class PrivateCenter{
        String name;
        String phone;
        String address;
        String url;
        String info;
    }


    private String TAG = "link_economic";
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    public static final ArrayList<PublicCenter> publicCenters=new ArrayList<>();
    public static final ArrayList<PrivateCenter> privateCenters=new ArrayList<>();

    private String data;

    public link_economic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_link_economic, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new getData().execute();

    }

    private void addPublicTableRow(TableLayout tl,final String name,final String phone,final String address,final String url,final String info){
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
        tr.setClickable(true);

        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SpannableString s;
                if(url!=null && info!=null)
                    s = new SpannableString("電話：" + phone + "\n地址：" + address + "\n\n" + info + "\n\n" + url);
                else if(url!=null)
                    s = new SpannableString("電話：" + phone + "\n地址：" + address + "\n\n" + url);
                else if(info!=null)
                    s = new SpannableString("電話：" + phone + "\n地址：" + address + "\n\n" + info);
                else
                    s = new SpannableString("電話：" + phone + "\n地址：" + address);
                Linkify.addLinks(s, Linkify.WEB_URLS);
                final AlertDialog d = new AlertDialog.Builder(getActivity())
                        .setPositiveButton(android.R.string.ok, null)
                        .setMessage( s )
                        .setTitle(name)
                        .create();
                d.show();

                // Make the textview clickable. Must be called after show()
                ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
//your code...


        tl.addView(tr);
    }

    private void addPrivateTableRow(TableLayout tl,final String name,final String phone,final String address,final String url,final String info){
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
        tr.setClickable(true);

        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SpannableString s;
                if(url!=null && info!=null)
                    s = new SpannableString("電話：" + phone + "\n地址：" + address + "\n\n" + info + "\n\n" + url);
                else if(url!=null)
                    s = new SpannableString("電話：" + phone + "\n地址：" + address + "\n\n" + url);
                else if(info!=null)
                    s = new SpannableString("電話：" + phone + "\n地址：" + address + "\n\n" + info);
                else
                    s = new SpannableString("電話：" + phone + "\n地址：" + address);
                Linkify.addLinks(s, Linkify.WEB_URLS);
                final AlertDialog d = new AlertDialog.Builder(getActivity())
                        .setPositiveButton(android.R.string.ok, null)
                        .setMessage( s )
                        .setTitle(name)
                        .create();
                d.show();

                // Make the textview clickable. Must be called after show()
                ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
//your code...


        tl.addView(tr);
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
                url = new URL("http://13.115.90.58/link_economic.php");

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


            TableLayout ll = (TableLayout) getView().findViewById(R.id.PublicTable);
            TableLayout cl = (TableLayout) getView().findViewById(R.id.PrivateTable);
            for (int i = 0; i <publicCenters.size(); i++)
                addPublicTableRow(ll,publicCenters.get(i).name,publicCenters.get(i).eligibility,publicCenters.get(i).unit,
                        publicCenters.get(i).url,publicCenters.get(i).info);

            for (int i = 0; i <privateCenters.size(); i++)
                addPrivateTableRow(cl,privateCenters.get(i).name,privateCenters.get(i).phone,privateCenters.get(i).address,
                        privateCenters.get(i).url,privateCenters.get(i).info);




        }

    }


    private void JSONanalyse(){
        try{
            publicCenters.clear();
            privateCenters.clear();
            JSONArray jsonArray = new JSONArray(data);
            for(int k=0;k<jsonArray.length();k++) {
                //Log.i(TAG, jsonArray.getString(i));


                JSONObject jsonObject = new JSONObject(jsonArray.getString(k));
                //Log.i(TAG, "length: "+jsonObject.toString());
                if (k == 0) {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        PublicCenter temp = new PublicCenter();
                        String number = data2.getString("number");
                        temp.name = data2.getString("item");
                        temp.eligibility = data2.getString("eligibility");
                        temp.unit = data2.getString("unit");
                        temp.url = data2.getString("url");
                        temp.info = data2.getString("information");
                        publicCenters.add(temp);
                       // Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }
                else if(k==1){
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        PrivateCenter temp = new PrivateCenter();
                        String number = data2.getString("number");
                        temp.name = data2.getString("unit");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        temp.url = data2.getString("url");
                        temp.info = data2.getString("introduction");
                        privateCenters.add(temp);
                        //Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }

            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

    }


}