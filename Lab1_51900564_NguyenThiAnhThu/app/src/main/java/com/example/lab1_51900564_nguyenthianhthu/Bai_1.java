package com.example.lab1_51900564_nguyenthianhthu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Bai_1 extends AppCompatActivity {
    private TextView view;
    private Button toast;
    private Button count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);
        AnhXaDoiTuong();
        clickToast();
        clickCount();
    }
    // Hàm ánh xạ đối tượng
    private void AnhXaDoiTuong(){
        view = findViewById(R.id.view);
        toast=findViewById(R.id.toast);
        count=findViewById(R.id.count);
    }
    // Hàm click button Toast
    private void clickToast(){
        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Bai_1.this, "Toast", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private  void clickCount(){
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String view_1=view.getText().toString().trim();
                int i= Integer.parseInt(view_1);
                i=i+1;
                String s=String.valueOf(i);
                view.setText(s);

            }
        });
    }
}
