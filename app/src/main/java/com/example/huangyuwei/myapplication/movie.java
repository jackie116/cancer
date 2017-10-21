package com.example.huangyuwei.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class movie extends AppCompatActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mWebView = (WebView)findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);   // 開啟Java Script 解譯功能

        // 設定轉址的網頁還是由WebView開啟，不要用外部的瀏覽器。
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("https://youtu.be/TCUt35GANpE");


    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.destroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }
}
