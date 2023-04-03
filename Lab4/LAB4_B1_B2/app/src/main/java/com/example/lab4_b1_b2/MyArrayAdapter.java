package com.example.lab4_b1_b2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyArrayAdapter extends RecyclerView.Adapter<MyArrayAdapter.MyHolder>
{
    private Context context;
    private List<Item> item;
    public MyArrayAdapter(Context context,List<Item> data)
    {
        this.context=context;
        this.item=data;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.itemlist,parent,false));
    }

    @Override
    //đổ dữ liệu lên view
    public void onBindViewHolder(@NonNull MyHolder holder, int position)
    {
        final Item itemItem=item.get(position);
        holder.textView.setText(itemItem.getItem());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDetail(itemItem);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog=new AlertDialog.Builder(context)
                        .setTitle(itemItem.getItem()+" will be deleted")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                item.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                Toast.makeText(context,"Delete successed",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
            }
        });
    }
    public void onClickDetail(Item item)
    {
        Toast.makeText(context,"Hello"+item.getItem(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount()
    {
        return item.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        LinearLayout linearLayout;
        TextView textView;
        Button btnDelete;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            textView=itemView.findViewById(R.id.title);
            btnDelete=itemView.findViewById(R.id.delete);
            linearLayout=itemView.findViewById(R.id.layoutItem);
        }
    }

}

