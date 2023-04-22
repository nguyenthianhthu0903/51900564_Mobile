package com.example.lab2_b1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private EditText input;
    private Button login;
    private TextView header;
    private static final int REQUEST_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initDataMessage() {

    }

    private void initView() {
        input = findViewById(R.id.textInput);
        login = findViewById(R.id.login);
        header = findViewById(R.id.title);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                    Intent intent = new Intent(MainActivity.this, Login.class);
                    intent.putExtra("email", input.getText().toString());
                    startActivityForResult(intent,REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==Login.RESULT_OK)
        {
            final String name = data.getStringExtra("nameInputed");
            header.setText("Hẹn gặp lại");
            input.setEnabled(false);
            input.setText(name);
            login.setVisibility(View.INVISIBLE);
        }
    }

}

