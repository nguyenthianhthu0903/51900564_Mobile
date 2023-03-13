package com.example.lab3b3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<Item> item;
    private ListView listView;
    private MyArrayAdapter adapter;
    private Button deleteSelected;
    private Button deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteSelected=findViewById(R.id.removeSelected);
        deleteAll=findViewById(R.id.removeAll);

        listView=findViewById(R.id.listView);
        initItem();
        adapter=new MyArrayAdapter(this, R.layout.itemlist,item);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        deleteSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SparseBooleanArray checkItemPosition= listView.getCheckedItemPositions();
                int itemCount = listView.getCount();
                for(int i=itemCount-1; i >= 0; i--){
                    if(checkItemPosition.get(i)){
                        adapter.remove(item.get(i));
                    }
                }
                checkItemPosition.clear();
                adapter.notifyDataSetChanged();
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                adapter.clear();
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void initItem() {
        item=new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            item.add(new Item("Apple " +i));
        }for(int i=0;i<5;i++)
        {
            item.add(new Item("Samsung " +i));
        }
        for(int i=0;i<5;i++)
        {
            item.add(new Item("Oppo " +i));
        }

    }


}