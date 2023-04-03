package com.example.lab4_b1_b2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> item;
    private RecyclerView recyclerView;
    private MyArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        adapter=new MyArrayAdapter(this,item);



        //hiện layout dạng nào, tham số thứ 3 là có đảo ngược không-không thì false
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        // item không thay đổi size thì true
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        initItem();
        recyclerView.setAdapter(adapter);
    }
    private void initItem()
    {
        item=new ArrayList<>();
        item=new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            item.add(new Item("Item "+i));
        }
    }



}