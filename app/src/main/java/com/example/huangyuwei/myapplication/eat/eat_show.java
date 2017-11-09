package com.example.huangyuwei.myapplication.eat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huangyuwei.myapplication.R;

public class eat_show extends AppCompatActivity {
    private final String TAG = "eat_show";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_show);
        Intent intent = this.getIntent();
        String url = intent.getStringExtra("url");
        Log.i(TAG, "123  "+url);

        webView = (WebView) findViewById(R.id.webview_cure);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);   // 開啟Java Script 解譯功能
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        //webSettings.setUseWideViewPort(true);// 可任意比例缩放

        // 設定轉址的網頁還是由WebView開啟，不要用外部的瀏覽器。
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+url);
    }
}
