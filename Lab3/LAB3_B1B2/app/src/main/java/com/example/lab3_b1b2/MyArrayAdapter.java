package com.example.lab3_b1b2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Item> {
    private MainActivity ctx;
    private List<Item> data;
    public MyArrayAdapter(MainActivity context, int resource, List<Item> objects) {
        super(context, resource, objects);
        this.ctx=context;
        this.data=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item=data.get(position);
        LayoutInflater inflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.itemlistview,null);
        TextView title=convertView.findViewById(R.id.title);
        Button btnDelete=convertView.findViewById(R.id.delete);
        title.setText(item.getItem());

//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog dialog=new AlertDialog.Builder(ctx)
//                        .setTitle(title.getText().toString()+" will be deleted!")
//                        .setMessage("Are you sure?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i)
//                            {
//                                ctx.deleteOk(position);
//                            }
//                        })
//                        .setNegativeButton("Cancel",null)//không đồng ý
//                        .create();
//                dialog.show();
//            }
//        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }
}
