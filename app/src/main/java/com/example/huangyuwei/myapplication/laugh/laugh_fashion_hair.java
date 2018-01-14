package com.example.huangyuwei.myapplication.laugh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huangyuwei.myapplication.R;

public class laugh_fashion_hair extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laugh_pdfshow);

        webView = (WebView) findViewById(R.id.webView);
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
        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=http://13.115.90.58/laugh_hair.pdf");
    }
}
