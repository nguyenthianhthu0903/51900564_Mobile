package com.example.lab03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondPage extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    TextView txt_titleName;
    EditText etxt_name;
    Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);

        txt_titleName = (TextView) findViewById(R.id.txt_titleName);
        etxt_name = (EditText) findViewById(R.id.etxt_name);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        // Lấy Intent từ activity trước đó và hiển thị email lên TextView
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        txt_titleName.setText("Xin chào, " + email + ". Vui lòng nhập tên");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SecondPage.this, MainActivity.class);
                intent2.putExtra("name", etxt_name.getText().toString());
                setResult(RESULT_OK, intent2);
                startActivityForResult(intent2, REQUEST_CODE);
            }
        });
    }
}
