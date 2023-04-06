package com.example.ex3;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // Lấy URL được gửi từ Intent
        String url = getIntent().getStringExtra("url");

        webView = findViewById(R.id.webview);
        // Cho phép JavaScript chạy trên WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Cấu hình WebViewClient để mở URL trong WebView
        webView.setWebViewClient(new WebViewClient());
        // Load trang web được chỉ định
        webView.loadUrl(url);
//        Log.d("hello", url);
    }

    @Override
    public void onBackPressed() {
        // Nếu người dùng ấn nút Back trên điện thoại, kiểm tra xem có thể quay lại trang trước đó trên WebView không
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
