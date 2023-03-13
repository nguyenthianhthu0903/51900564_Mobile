package com.example.lab3b4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<ToggleList> toggleButtons=new ArrayList<>();
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToggle();
        adapter=new Adapter(this,R.layout.itemgrid,toggleButtons);
        gridView=findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
    }
    public void initToggle()
    {
        ToggleButton tb = null;
        for(int i=0;i<20;i++)
        {
            toggleButtons.add(new ToggleList());
        }
    }
}