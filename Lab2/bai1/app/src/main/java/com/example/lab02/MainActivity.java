package com.example.lab02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public boolean checkCap(String text) {
        boolean checkCap = false;
        boolean checkUnCap = false;
        for(char item: text.toCharArray()) {
            if (item >= 'A' && item <= 'Z') checkCap = true;
            if (item >= 'a' && item <= 'z') checkUnCap = true;
        }
        return checkCap && checkUnCap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        EditText etxt_user = findViewById(R.id.etx_user);
        EditText etxt_pass = findViewById(R.id.etxt_pass);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etxt_user.getText().toString();
                String pass = etxt_pass.getText().toString();

                if (user.equals("admin") && pass.equals("admin1234")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Success to Login", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 300);
                    toast.show();
                } else if(etxt_user.getText().toString().isEmpty() || pass.isEmpty()) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please input your Username or Password", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 300);
                    toast.show();
                } else if(!(pass.length() < 6 && checkCap(pass))) {
                    Toast toast = Toast.makeText(MainActivity.this, "Password does not match the request", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 300);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Fail to Login", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 300);
                    toast.show();
                }
            }
        });

        TextView btn_reset = findViewById(R.id.btn_reset);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etxt_user.getText().toString();

                if (user.equals("admin")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Success to Reset Password", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 300);
                    toast.show();
                } else if(etxt_user.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please input your Username", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 300);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Fail to Login", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 300);
                    toast.show();
                }
            }
        });
    }
}