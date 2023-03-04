package com.example.bai2_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    WebView myweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myweb = findViewById(R.id.myweb);
        Intent myintent = getIntent();
        Uri mylink = myintent.getData();
        try {
            myweb.loadUrl(mylink.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}