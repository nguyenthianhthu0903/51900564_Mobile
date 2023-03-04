package com.example.bai2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class MainActivity2 extends AppCompatActivity {

    public static final String KEY_FULL_NAME_RESULT = "KEY_FULL_NAME_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent dataIntent = getIntent();
        String username = dataIntent.getStringExtra(MainActivity.KEY_USERNAME_DATA);

        TextView text2 = findViewById(R.id.text2);
        text2.setText(username);
    }

    public void setResultAndGoBack(View v){
        Intent dataResultIntent = new Intent();
        dataResultIntent.putExtra(KEY_FULL_NAME_RESULT, "Nguyen Thi Anh Thu");
        setResult(Activity.RESULT_OK, dataResultIntent);
        finish();
    }
}