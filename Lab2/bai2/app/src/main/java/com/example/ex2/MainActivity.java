package com.example.ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        Button btn_un = findViewById(R.id.btn_un);
        TextView txt_following = findViewById(R.id.txt_following);
        TextView txt_follower = findViewById(R.id.txt_follower);

        btn_un.setVisibility(View.INVISIBLE);
        int val1 = new Random().nextInt((10000 - 100) + 1) + 100;
        int val2 = new Random().nextInt((10000 - 100) + 1) + 100;

        txt_following.setText(Integer.toString(val1));
        txt_follower.setText(Integer.toString((val2)));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_follower.setText(Integer.toString(Integer.parseInt(txt_follower.getText().toString()) + 1));
                btn_un.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
            }
        });

        btn_un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_follower.setText(Integer.toString(Integer.parseInt(txt_follower.getText().toString()) - 1));
                btn.setVisibility(View.VISIBLE);
                btn_un.setVisibility(View.INVISIBLE);
            }
        });
    }
}