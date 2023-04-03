package com.example.lab4_b3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Button removeSelected,removeAll;
    RecyclerView recyclerView;
    ArrayList<Item>items;

    MyArrayAdapter adapter;
    int flag=0;
    MenuItem checkAllIcon,deleteAll;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        removeSelected=findViewById(R.id.removeSelected);
        removeAll=findViewById(R.id.removeAll);
        recyclerView=findViewById(R.id.listRecycler);
        initItem();
        adapter=new MyArrayAdapter(this,items);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.clear();
                adapter.notifyDataSetChanged();
            }
        });

        removeSelected.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                for(int i=items.size()-1; i >= 0; i--)
                {
                    if(items.get(i).getCheck()==true)
                    {
                        items.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
    private void initItem()
    {
        items=new ArrayList<>();
        items=new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            String name="Item "+i;

            items.add(new Item(name,false));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        checkAllIcon=menu.findItem(R.id.checkAll);
        deleteAll=menu.findItem(R.id.deleteSelected);
        final CheckBox sw=(CheckBox) checkAllIcon.getActionView().findViewById(R.id.checkAllMain);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(sw.isChecked())
                {
                    adapter.selectAll();
                    flag=1;
                }
                else
                {
                    flag=0;
                    adapter.unselectall();
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.deleteAll:
                items.clear();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.checkAll:
                return true;
            case R.id.deleteSelected:
                if(flag==1)
                {
                    items.clear();
                    adapter.notifyDataSetChanged();
                }
                for(int i=items.size()-1; i >= 0; i--)
                {
                    if(items.get(i).getCheck()==true)
                    {
                        items.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}