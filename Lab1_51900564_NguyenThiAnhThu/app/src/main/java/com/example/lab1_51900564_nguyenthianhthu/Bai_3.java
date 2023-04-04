package com.example.lab1_51900564_nguyenthianhthu;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Bai_3 extends AppCompatActivity {
    private CheckBox android,ios,win,rim;
    private Button click_here;
    private TextView results_android,results_ios,results_win,results_rim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);
        AnhXaDoiTuong();
        click_here();
    }
    private void AnhXaDoiTuong(){
        click_here=findViewById(R.id.btn_Click);
        android=findViewById(R.id.android);
        ios=findViewById(R.id.ios);
        win=findViewById(R.id.windows);
        rim=findViewById(R.id.rim);
        results_android=findViewById(R.id.text_results_android);
        results_ios=findViewById(R.id.text_results_ios);
        results_win=findViewById(R.id.text_results_win);
        results_rim=findViewById(R.id.text_results_rim);
    }
    private void click_here(){
        click_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence android_content=android.getText();
                CharSequence ios_content=ios.getText();
                CharSequence win_content=win.getText();
                CharSequence rim_content=rim.getText();
                if(android.isChecked()){
                    results_android.setText(android_content+": true");
                } else{
                    results_android.setText(android_content+": false");
                }
                if (ios.isChecked()) {
                    results_ios.setText(ios_content+": true");
                }
                else {
                    results_ios.setText(ios_content+": false");
                }
                if (win.isChecked()) {
                    results_win.setText(win_content+": true");
                } else {
                    results_win.setText(win_content+": false");
                }  if (rim.isChecked()) {
                    results_rim.setText(rim_content+": true");
                }else{
                    results_rim.setText(rim_content+": false");
                }

            }
        });
    }
}