package com.example.lab2_b1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
//    public static final int EXTRA_DATA =100;
    private TextView title;
    private EditText inputName;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle("Main Screen");

        initView();
        initDataMessage();
    }

    private void initDataMessage()
    {
        Intent intent = getIntent();
        final String emailFromDisplay1=intent.getStringExtra("email");
        title.setText(String.format("Xin chào %s, Vui lòng nhập tên",emailFromDisplay1));
    }

    private void initView() {
        title=findViewById(R.id.title);
        inputName=findViewById(R.id.inputName);
        btnSave=findViewById(R.id.buttonSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Login.this,MainActivity.class);
                intent.putExtra("nameInputed",inputName.getText().toString());

                setResult(RESULT_OK,intent);
                finish(); //đóng ac hiện tại trở về main

            }

        });
    }
}