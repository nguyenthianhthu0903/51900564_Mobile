package com.example.lab2_b2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected EditText text;
    protected Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.url);
        btn=findViewById(R.id.buttonOK);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        openUrl(text.getText().toString());
    }

    private void openUrl(String url)
    {
        try {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            String title="Vui lòng chọn trình duyệt";
//            if(intent.resolveActivity(getPackageManager())!=null)
//            {
//                startActivity(intent);
//            }
            Intent chooser=Intent.createChooser(intent,title);
            startActivity(chooser);
        }
        catch (ActivityNotFoundException ex)
        {
            Log.wtf(ex.getClass().getSimpleName(),ex.getMessage());
        }
    }
}

