package com.example.lab4_b3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyArrayAdapter extends RecyclerView.Adapter<MyArrayAdapter.MyHolder>
{
    private MainActivity mainActivity;
    private List<Item>item;
    private ArrayList<Integer> pos;
    boolean isSelectedAll;
    public MyArrayAdapter(MainActivity mainActivity,List<Item> data)
    {
        this.mainActivity=mainActivity;
        this.item=data;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist,parent,false);
        MyHolder myHolder=new MyHolder(v,mainActivity);
        return myHolder;
    }

    public void selectAll(){
        isSelectedAll=true;
        notifyDataSetChanged();
    }
    public void unselectall(){
        isSelectedAll=false;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position)
    {
        final Item itemitem=item.get(position);
        holder.name.setText(itemitem.getName());


        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setSelected(itemitem.getCheck());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    itemitem.setCheck(true);
                }else {
                    itemitem.setCheck(false);
                }
            }
        });
        holder.checkBox.setChecked(itemitem.getCheck());
        if (!isSelectedAll){
            holder.checkBox.setChecked(false);
        }
        else  holder.checkBox.setChecked(true);
    }


    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        TextView name;
        CheckBox checkBox;
        public MyHolder(@NonNull View itemView, MainActivity mainActivity)
        {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.layoutItem);
            name=itemView.findViewById(R.id.phoneName);
            checkBox=itemView.findViewById(R.id.check);
        }

    }
}
