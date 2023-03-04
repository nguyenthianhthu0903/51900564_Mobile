package com.example.lab1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //Khai báo
    private Button bai1;
    private Button bai2;
    private Button bai3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bai1=findViewById(R.id.bai1);
        bai2=findViewById(R.id.bai2);
        bai3=findViewById(R.id.bai3);
        bai_1();
        bai_2();
        bai_3();
    }
    private void bai_1(){
        bai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Bai_1.class);
                startActivity(intent);
            }
        });
    }
    private void bai_2(){
        bai2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Bai_2.class);
                startActivity(intent);
            }
        });
    }

    private void bai_3(){
        bai3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, Bai_3.class);
                startActivity(intent);
            }
        });
    }
}