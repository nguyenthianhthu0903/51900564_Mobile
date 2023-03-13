package com.example.lab3_b1b2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<Item> item;
    private ListView listview;
    private MyArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview=findViewById(R.id.listView);
        initItem();
        adapter=new MyArrayAdapter(this, android.R.layout.simple_list_item_1,item);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {

    }
    public void deleteOk(final int position)
    {
        Toast.makeText(this,"Delete Success",Toast.LENGTH_SHORT).show();
        item.remove(position);
        adapter.notifyDataSetChanged();
    }
}