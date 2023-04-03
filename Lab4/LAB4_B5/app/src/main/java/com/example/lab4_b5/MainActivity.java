package com.example.lab4_b5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    TextView btnAdd,btnDelete;
    ArrayList<User> users=new ArrayList<>();
    MyArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        btnAdd=findViewById(R.id.addUser);
        btnDelete=findViewById(R.id.removeUser);
        adapter=new MyArrayAdapter(this,users);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int countadd=5;
                int count=recyclerView.getChildCount();
                for(int k=count;countadd>0;k++)
                {
                    int index=k+1;
                    String name="User "+index;
                    String mail="user"+index+"@gmail.com";
                    users.add(new User(name,mail));
                    adapter.notifyDataSetChanged();
                    countadd--;
                }
            }
        });
        //remove
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int countadd=5;
                int count=recyclerView.getChildCount();
                for(int k=count-1;countadd>0 && count>0;k--)
                {
                    users.remove(users.get(k));
                    adapter.notifyDataSetChanged();
                    countadd--;
                }
                if(count==0)
                    Toast.makeText(MainActivity.this, "List is Empty", Toast.LENGTH_SHORT).show();
//                adapter.notifyDataSetChanged();
            }
        });
    }
}