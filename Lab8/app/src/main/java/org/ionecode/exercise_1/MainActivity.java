package org.ionecode.exercise_1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getRequest(View view) {
        this.startActivity(new Intent(this, GetRequestActivity.class));
    }

    public void postRequest(View view) {
        this.startActivity(new Intent(this, PostRequestActivity.class));
    }

}