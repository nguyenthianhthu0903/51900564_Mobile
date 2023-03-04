package com.example.bai2_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView text1;
    public static String KEY_USERNAME_DATA = "KEY_USERNAME_DATA";
    public static int CODE_REQUEST_GET_FULL_NAME= 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = findViewById(R.id.text1);
    }

    public void navigationToActivity2(View v){
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra(KEY_USERNAME_DATA, "anhthu0903@gmail.com");
        startActivityForResult(intent, CODE_REQUEST_GET_FULL_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE_REQUEST_GET_FULL_NAME && resultCode == Activity.RESULT_OK){
            if(data != null){
                text1.setText(data.getStringExtra(MainActivity2.KEY_FULL_NAME_RESULT));
            }
        }
    }

}