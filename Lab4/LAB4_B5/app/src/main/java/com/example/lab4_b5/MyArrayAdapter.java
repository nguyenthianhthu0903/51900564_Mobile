package com.example.lab4_b5;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyArrayAdapter extends RecyclerView.Adapter<MyArrayAdapter.MyHolder>
{
    Context context;
    List<User>user;
    public MyArrayAdapter(Context context,List<User>data)
    {
        this.context=context;
        this.user=data;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.itemlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position)
    {
        final User userItem=user.get(position);
        holder.userName.setText(userItem.getName());
        holder.mailname.setText(userItem.getMail());
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        TextView userName,mailname;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            mailname=itemView.findViewById(R.id.mailUser);
        }
    }
}
