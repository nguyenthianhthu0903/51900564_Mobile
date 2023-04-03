package com.example.lab4_b4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyHolder>
{
    private Context cxt;
    private List<ToggleList> item;
    public Adapter(Context context,List<ToggleList>data)
    {
        this.cxt=context;
        this.item=data;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(cxt).inflate(R.layout.itemview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position)
    {
        final ToggleList itemitem=item.get(position);
        holder.nameUser.setText(itemitem.getName());
        holder.toggleButton.setOnCheckedChangeListener(null);
        holder.toggleButton.setSelected(itemitem.getStatus());
        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    itemitem.setStatus(true);
                    Toast.makeText(cxt,"Bật trạng thái hoạt đông của "+itemitem.getName(),Toast.LENGTH_SHORT).show();
                }else {
                    itemitem.setStatus(false);
                    Toast.makeText(cxt,"Tắt trạng thái hoạt đông của "+itemitem.getName(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.toggleButton.setChecked(itemitem.getStatus());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        TextView nameUser;
        ToggleButton toggleButton;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            toggleButton=itemView.findViewById(R.id.toggleButton);
            nameUser=itemView.findViewById(R.id.idUser);
        }
    }
}

