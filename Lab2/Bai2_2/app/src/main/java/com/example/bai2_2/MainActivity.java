package com.example.bai2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText addressWeb;
    Button showWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressWeb = findViewById(R.id.addressWeb);
        showWeb = findViewById(R.id.showWeb);

        showWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+showWeb.getText().toString()));
                startActivity(myintent);
            }
        });
    }
}