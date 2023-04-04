package com.example.lab1_51900564_nguyenthianhthu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Bai_2 extends AppCompatActivity {
    //Khai báo biến
    private EditText editText;
    private Button btn_click;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);
        AnhXaDoiTuong();
        click_me();
    }
    private void AnhXaDoiTuong(){
        editText=findViewById(R.id.edit_text);
        btn_click=findViewById(R.id.btn_click);
        textView=findViewById(R.id.textView);
    }
    private void click_me(){
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=editText.getText().toString().trim();
                if (content.isEmpty()){
                    Toast.makeText(Bai_2.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(content);
                    editText.getText().clear();
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content=editText.getText().toString().trim();
                if (content.equals("OFF")){
                    btn_click.setEnabled(false);
                } else if(content.equals("ON")){
                    btn_click.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

