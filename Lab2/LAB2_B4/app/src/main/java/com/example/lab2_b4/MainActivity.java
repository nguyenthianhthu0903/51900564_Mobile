package com.example.lab2_b4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("TAG","On Create");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.e("TAG","On Start");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG","On Ressume");
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.e("TAG","On Pause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG","On Stop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TAG","On Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG","On Destroy");
    }

}