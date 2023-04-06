package com.example.ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_BROWSER = 1;

    EditText etxt_url;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etxt_url = findViewById(R.id.etxt_url);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etxt_url.getText().toString();

                if (url.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addCategory(Intent.CATEGORY_BROWSABLE);

                // Take all Browser Installed
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);

                if (activities.size() > 0) {
                    startActivityForResult(Intent.createChooser(intent, "Choose browser"), REQUEST_BROWSER);
                } else {
                    Intent webViewIntent = new Intent(MainActivity.this, WebViewActivity.class);
                    webViewIntent.putExtra("url", url);
                    startActivity(webViewIntent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_BROWSER && resultCode == RESULT_OK) {
            Intent webViewIntent = new Intent(MainActivity.this, WebViewActivity.class);
            webViewIntent.putExtra("url", data.getData().toString());
            startActivity(webViewIntent);
        }
    }
}