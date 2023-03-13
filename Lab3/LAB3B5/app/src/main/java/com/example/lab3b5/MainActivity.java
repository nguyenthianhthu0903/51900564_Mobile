package com.example.lab3b5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<User> users=new ArrayList<>();
    private ListView listView;
    private UserAdapter adapter;
    private TextView btnAdd, btnRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        btnAdd=findViewById(R.id.addUser);
        btnRemove=findViewById(R.id.removeUser);
        adapter=new UserAdapter(this,R.layout.itemlist,users);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int countadd=5;
                int count=listView.getCount();
                for(int k=count;countadd>0;k++)
                {
                    int index=k+1;
                    String name="User "+index;
                    String mail="user"+1+"@gmail.com";
                    users.add(new User(name,mail));
                    adapter.notifyDataSetChanged();
                    countadd--;
                }
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int countadd=5;
                int count=listView.getCount();
                for(int k=count-1;countadd>0 && count>0;k--)
                {
                    users.remove(users.get(k));
                    adapter.notifyDataSetChanged();
                    countadd--;
                }
                if(count==0)
                    Toast.makeText(MainActivity.this, "List is Empty", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
    }
}