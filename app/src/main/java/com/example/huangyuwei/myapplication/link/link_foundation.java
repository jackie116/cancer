package com.example.huangyuwei.myapplication.link;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;


public class link_foundation extends AppCompatActivity {

    class Center{
        String imageurl;
        String phone;
        String address;
        String url;
    }
    public static final ArrayList<Center> foundations=new ArrayList<>();
    private String TAG = "link_resource";
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private String data;

    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
  
    public link_foundation() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_link_foundation);
        new getData(this).execute();
    }
    
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Center> mData;
        Context mcontext;
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public TextView mPhoneView;
            public ImageView imageView;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.info_text);
                imageView=(ImageView)v.findViewById(R.id.info_img);
                mPhoneView=(TextView)v.findViewById(R.id.info_phonnum);
            }
        }

        public MyAdapter(List<Center> data, Context context) {
            mData = data;
            mcontext=context;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mTextView.setText(foundations.get(position).address);
            holder.mPhoneView.setText(foundations.get(position).phone);
            new DownloadImageTask(holder.imageView)
                    .execute(foundations.get(position).imageurl);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final SpannableString s;
                    if(foundations.get(position).url!=null)
                        s = new SpannableString("電話：" + foundations.get(position).phone + "\n地址：" + foundations.get(position).address + "\n\n" + foundations.get(position).url);
                    else
                        s = new SpannableString("電話：" + foundations.get(position).phone + "\n地址：" + foundations.get(position).address + "\n\n");

                    Linkify.addLinks(s, Linkify.WEB_URLS);
                    final AlertDialog d = new AlertDialog.Builder(mcontext)
                            .setPositiveButton(android.R.string.ok, null)
                            .setMessage( s )
                            .create();
                    d.show();

                    // Make the textview clickable. Must be called after show()
                    ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }




    private class getData extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;
        ProgressDialog pDialog;
        private Context mcontext;

        getData(Context context){
            this.mcontext=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pDialog = new ProgressDialog(mcontext);
                    pDialog.setMessage("Loading..");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();

                }
            });

        }
        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL("http://13.115.90.58/link_foundation.php");

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

            pDialog.dismiss();
            data = result;
            JSONanalyse();
            Log.i(TAG,data);
            mAdapter = new MyAdapter(foundations,mcontext);
            mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);

        }

    }


    private void JSONanalyse(){
        try{
            foundations.clear();

                JSONObject jsonObject = new JSONObject(data);
                //Log.i(TAG, "length: "+jsonObject.toString());
                    for(int i=0;i<jsonObject.length();i++){
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        String number = data2.getString("number");
                        Center temp = new Center();
                        temp.imageurl = data2.getString("logo");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        temp.url = data2.getString("url");
                        foundations.add(temp);
                        Log.i(TAG, "123------ >  "+number+"  "+temp.imageurl+"  "+temp.phone+"  "+temp.address+"  "+temp.url);
                    }




        }
        catch(JSONException e) {
            e.printStackTrace();
        }


    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}