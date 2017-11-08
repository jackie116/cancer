package com.example.huangyuwei.myapplication.cure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huangyuwei.myapplication.R;

public class cure_show extends AppCompatActivity {
    private final String TAG = "cure_show";
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cure_show);
        Intent intent = this.getIntent();
        String url = intent.getStringExtra("url");
        Log.i(TAG, "123  "+url);

        webView = (WebView) findViewById(R.id.webview_cure);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
