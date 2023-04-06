package com.example.lab03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    TextView txt_title;
    EditText etxt_email;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_title = (TextView) findViewById(R.id.txt_title);
        etxt_email = (EditText) findViewById(R.id.etxt_email);
        btn_login = (Button) findViewById(R.id.btn_login);

        // Lấy Intent từ activity trước đó và hiển thị name lên TextView
        Intent intent2 = getIntent();
        String name = intent2.getStringExtra("name");
        if (name != null) {
            txt_title.setText("Hẹn gặp lại");
            etxt_email.setText(name);
            btn_login.setVisibility(View.INVISIBLE);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondPage.class);
                intent.putExtra("email", etxt_email.getText().toString());
                setResult(RESULT_OK, intent);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
}